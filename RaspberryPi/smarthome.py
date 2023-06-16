import pyrebase
import Rpi.GPIO as GPIO
import time
import Adafruit_BMP.BMP085 as BMP085
import adafruit_ssd1306
from PIL import Image, ImageDraw, ImageFont
import board
import busio
import digitalio
import subprocess

# OLED 초기화
oled_reset = digitalio.DigitalInOut(board.D4)

WIDTH = 128
HEIGHT = 64
BORDER = 5

LOOPTIME = 1.0

i2c = board.I2C()
oled = adafruit_ssd1306.SSD1306_I2C(WIDTH, HEIGHT, i2c, addr=0x3C, reset=oled_reset)
oled.fill(0)
oled.show()

font = ImageFont.load_default()

# BMP085 센서 설정
sensor = BMP085.BMP085(busnum=1)

# Firebase 구성
config = {
    # API KEY

}

firebase = pyrebase.initialize_app(config)

# Firebase 데이터베이스에 접근
db = firebase.database()

red_led = 17
blue_led = 27
SERVO_PIN = 18
detection = 23

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

GPIO.setup(red_led, GPIO.OUT)
GPIO.setup(blue_led, GPIO.OUT)
GPIO.setup(SERVO_PIN, GPIO.OUT)
GPIO.setup(detection, GPIO.OUT)

# 서보 모터를 제어하기 위해 PWM 설정
door = GPIO.PWM(SERVO_PIN, 50)
door.start(0)

# LED 제어 함수
def turn_on_red_led():
    GPIO.output(red_led, GPIO.HIGH)

def turn_off_red_led():
    GPIO.output(red_led, GPIO.LOW)

def turn_on_blue_led():
    GPIO.output(blue_led, GPIO.HIGH)

def turn_off_blue_led():
    GPIO.output(blue_led, GPIO.LOW)

# Firebase Realtime Database 변경 이벤트 핸들러
def blue_led_value_changed(event):
    if event["event"] == "put":
        blue_value = event["data"]
        if blue_value:
            turn_on_blue_led()
        else:
            turn_off_blue_led()

def red_led_value_changed(event):
    if event["event"] == "put":
        red_value = event["data"]
        if red_value:
            turn_on_red_led()
        else:
            turn_off_red_led()

def door_value_changed(event):
    if event["event"] == "put":
        door_value = event["data"]
        if door_value:
            door.ChangeDutyCycle(7.5)
        else:
            door.ChangeDutyCycle(2.5)

def get_temperature():
    temp = sensor.read_temperature()
    temperature = "{:.2f}".format(temp)
    temperature_symbol = f"{temperature} *C"
    return temperature_symbol

def temperature_value_changed(event):
    if event["event"] == "put":
        temperature_value = event["data"]
        if temperature_value:
            data = {
                "value": get_temperature(),
                "update": False
            }
            db.child("temperature").update(data)

out_mode = False
def out_mode_value_changed(event):
    if event["event"] == "put":
        out_mode_value = event["data"]
        global out_mode
        if out_mode_value:
            out_mode = True
        else:
            out_mode = False

def write_message(text):
    image = Image.new("1", (oled.width, oled.height))  # 새로운 이미지 생성
    draw = ImageDraw.Draw(image)  # 이미지를 그리기 위한 도구 생성
    draw.text((36, 32), text, font=font, fill=255)  # 텍스트 그리기
    oled.image(image)  # OLED에 이미지 표시
    oled.show()  # OLED에 표시된 이미지 갱신

def oled_value_changed(event):
    if event["event"] == "put":
        oled_value = event["data"]
        if oled_value == "":
            write_message(get_temperature())  # 온도 정보를 표시
        else:
            write_message(oled_value)  # 전달받은 텍스트를 표시

try:
    # Firebase Realtime Database의 변경 이벤트를 추적하는 스트림 생성
    blue_stream = db.child("led").child("blue").stream(blue_led_value_changed)
    red_stream = db.child("led").child("red").stream(red_led_value_changed)
    door_stream = db.child("door").stream(door_value_changed)
    temperature_stream = db.child("temperature").child("update").stream(temperature_value_changed)
    out_stream = db.child("out").child("mode").stream(out_mode_value_changed)
    oled_stream = db.child("oled").stream(oled_value_changed)

    while True:
        if out_mode:
            if GPIO.input(detection) == True:
                val = db.child("out").child("detection").get().val()
                if val == False:
                    data = {
                        "detection": True,
                        "mode": True
                    }
                    db.child("out").update(data)
        pass

except KeyboardInterrupt:
    GPIO.cleanup()
