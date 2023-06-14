package com.example.android.model;

public class Led {
   String redLed;
   boolean redLedState;

   String blueLed;
   boolean blueLedState;

   public Led(){}

   public Led(String redLed, boolean redLedState, String blueLed, boolean blueLedState) {
      this.redLed = redLed;
      this.redLedState = redLedState;
      this.blueLed = blueLed;
      this.blueLedState = blueLedState;
   }

   public String getRedLed() {
      return redLed;
   }

   public void setRedLed(String redLed) {
      this.redLed = redLed;
   }

   public boolean getRedLedState() {
      return redLedState;
   }

   public void setRedLedState(boolean redLedState) {
      this.redLedState = redLedState;
   }

   public String getBlueLed() {
      return blueLed;
   }

   public void setBlueLed(String blueLed) {
      this.blueLed = blueLed;
   }

   public boolean getBlueLedState() {
      return blueLedState;
   }

   public void setBlueLedState(boolean blueLedState) {
      this.blueLedState = blueLedState;
   }
}
