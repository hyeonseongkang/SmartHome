package com.example.android.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainRepository {

   private DatabaseReference ledRef;
   private DatabaseReference temperatureRef;
   private DatabaseReference doorRef;
   private DatabaseReference oledRef;

   private MutableLiveData<String> ledValue;
   private MutableLiveData<String> temperatureValue;
   private MutableLiveData<Boolean> doorValue;
   private MutableLiveData<String> oledMessageValue;

   public MainRepository() {
      ledRef = FirebaseDatabase.getInstance().getReference("led");
      temperatureRef = FirebaseDatabase.getInstance().getReference("temperature");
      doorRef = FirebaseDatabase.getInstance().getReference("door");
      oledRef = FirebaseDatabase.getInstance().getReference("oled");
      ledValue = new MutableLiveData<>();
      temperatureValue = new MutableLiveData<>();
      doorValue = new MutableLiveData<>();
      oledMessageValue = new MutableLiveData<>();
   }

   public MutableLiveData<String> getLedLiveData() {
      return ledValue;
   }

   public MutableLiveData<String> getTemperatureLiveData() { return  temperatureValue; }

   public MutableLiveData<Boolean> getDoorLiveData() { return doorValue; }

   public MutableLiveData<String> getOLEDMessageLiveData() {
      return oledMessageValue;
   }

   public void getLed() {
      ledRef.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            String led = snapshot.getValue(String.class);
            ledValue.setValue(led);
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });
   }

   public void getTemperature() {
      temperatureRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            String temperature = snapshot.getValue(String.class);
            temperatureValue.setValue(temperature);
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });
   }

   public void getDoor() {
      doorRef.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            Boolean doorState = snapshot.getValue(Boolean.class);
            doorValue.setValue(doorState);
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });
   }

   public void readMessageOLED() {
      oledRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            String message = snapshot.getValue(String.class);
            oledMessageValue.setValue(message);
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });
   }
}
