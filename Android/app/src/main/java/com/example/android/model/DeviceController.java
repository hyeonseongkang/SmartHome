package com.example.android.model;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

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

   @BindingAdapter("outMode")
   public static void setOutModeText(TextView textView, Out out) {
      if (out.isMode()) {
         textView.setText("YES");

      } else {
         textView.setText("NO");
      }
   }

   @BindingAdapter("door")
   public static void setDoorStateText(TextView textView, String doorState) {
      textView.setText(doorState);
   }

   @BindingAdapter("redLed")
   public static void setRedLedStateText(TextView textView, Led led) {
      if (led.isRed()) {
         textView.setText("ON");
      } else {
         textView.setText("OFF");
      }
   }

   @BindingAdapter("blueLed")
   public static void setBlueLedStateText(TextView textView, Led led) {
      if (led.isBlue()) {
         textView.setText("ON");
      } else {
         textView.setText("OFF");
      }
   }
}
