package com.example.android.model;

public class Out {
   private boolean detection;
   boolean mode;

   public Out() {}

   public Out(boolean detection, boolean mode) {
      this.detection = detection;
      this.mode = mode;
   }

   public boolean isDetection() {
      return detection;
   }

   public void setDetection(boolean detection) {
      this.detection = detection;
   }

   public boolean isMode() {
      return mode;
   }

   public void setMode(boolean mode) {
      this.mode = mode;
   }
}
