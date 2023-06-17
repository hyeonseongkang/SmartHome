package com.example.android.model;

public class DeviceController {
   Led led;
   Out out;
   String temperature;
   String oledMessage;
   String door;

   public DeviceController() {}

   public DeviceController(Led led, Out out, String temperature, String oledMessage, String door) {
      this.led = led;
      this.out = out;
      this.temperature = temperature;
      this.oledMessage = oledMessage;
      this.door = door;
   }

   public Led getLed() {
      return led;
   }

   public void setLed(Led led) {
      this.led = led;
   }

   public Out getOut() {
      return out;
   }

   public void setOut(Out out) {
      this.out = out;
   }

   public String getTemperature() {
      return temperature;
   }

   public void setTemperature(String temperature) {
      this.temperature = temperature;
   }

   public String getOledMessage() {
      return oledMessage;
   }

   public void setOledMessage(String oledMessage) {
      this.oledMessage = oledMessage;
   }

   public String getDoor() {
      return door;
   }

   public void setDoor(String door) {
      this.door = door;
   }
}
