package com.example.android.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.model.Led;
import com.example.android.repository.MainRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainViewModel extends AndroidViewModel {

   private MainRepository repository;

   private DatabaseReference ledRef;

   private DatabaseReference doorRef;

   private DatabaseReference oledRef;

   private LiveData<Led> ledValue;
   private LiveData<String> temperatureValue;
   private LiveData<Boolean> doorValue;
   private LiveData<String> oledMessageValue;


   public MainViewModel(@NonNull Application application) {
      super(application);
      repository = new MainRepository();
      ledRef = FirebaseDatabase.getInstance().getReference("led");
      doorRef = FirebaseDatabase.getInstance().getReference("door");
      oledRef = FirebaseDatabase.getInstance().getReference("oled");
      ledValue = repository.getLedLiveData();
      temperatureValue = repository.getTemperatureLiveData();
      doorValue = repository.getDoorLiveData();
      oledMessageValue = repository.getOLEDMessageLiveData();

   }

   public LiveData<Led> getLedLiveData() {
      return ledValue;
   }

   public LiveData<String> getTemperatureLiveData() {return  temperatureValue; }

   public LiveData<Boolean> getDoorLiveData() { return doorValue; }

   public LiveData<String> getOLEDMessageLiveData() { return  oledMessageValue; }

   public void setLed(String led) {
      Led ledList = ledValue.getValue();
      if (ledList.getRedLed().equals(led)) {
         if (ledList.getRedLedState()) {
            ledList.setRedLedState(false);
         } else {
            ledList.setRedLedState(true);
         }
      } else {
         if (ledList.getBlueLedState()) {
            ledList.setBlueLedState(false);
         } else {
            ledList.setBlueLedState(true);
         }
      }
      ledRef.setValue(ledList);
   }


   public void getLed() {
      repository.getLed();
   }

   public void setDoor() {
      boolean doorState = doorValue.getValue();
      if (doorState) {
         doorState = false;
      } else {
         doorState = true;
      }
      doorRef.setValue(doorState);
   }

   public void getDoor() {
      repository.getDoor();
   }


   public void writeMessageOLED(String message) {
      oledRef.setValue(message);
   }

   public void readMessageOLED() {
      repository.readMessageOLED();
   }


}
