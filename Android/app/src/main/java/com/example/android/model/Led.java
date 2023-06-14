package com.example.android.model;

public class Led {
   boolean red;
   boolean blue;


   public Led(){}

   public Led(boolean red, boolean blue, String blueLed, boolean blueLedState) {
      this.red = red;
      this.blue = blue;

   }

   public boolean isRed() {
      return red;
   }

   public void setRed(boolean red) {
      this.red = red;
   }

   public boolean isBlue() {
      return blue;
   }

   public void setBlue(boolean blue) {
      this.blue = blue;
   }
}
