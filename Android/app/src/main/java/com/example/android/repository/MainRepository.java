package com.example.android.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.android.model.Led;
import com.example.android.model.Out;
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
   private DatabaseReference outRef;

   private MutableLiveData<Led> ledValue;
   private MutableLiveData<String> temperatureValue;
   private MutableLiveData<Boolean> doorValue;
   private MutableLiveData<String> oledMessageValue;
   private MutableLiveData<Out> outValue;

   public MainRepository() {
      ledRef = FirebaseDatabase.getInstance().getReference("led");
      temperatureRef = FirebaseDatabase.getInstance().getReference("temperature");
      doorRef = FirebaseDatabase.getInstance().getReference("door");
      oledRef = FirebaseDatabase.getInstance().getReference("oled");
      outRef = FirebaseDatabase.getInstance().getReference("out");
      ledValue = new MutableLiveData<>();
      temperatureValue = new MutableLiveData<>();
      doorValue = new MutableLiveData<>();
      oledMessageValue = new MutableLiveData<>();
      outValue = new MutableLiveData<>();
   }

   public MutableLiveData<Led> getLedLiveData() {
      return ledValue;
   }

   public MutableLiveData<String> getTemperatureLiveData() { return  temperatureValue; }

   public MutableLiveData<Boolean> getDoorLiveData() { return doorValue; }

   public MutableLiveData<String> getOLEDMessageLiveData() {
      return oledMessageValue;
   }

   public MutableLiveData<Out> getOutLiveData() { return outValue; }

   public void getLed() {
      ledRef.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            Led led = snapshot.getValue(Led.class);
            ledValue.setValue(led);
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });
   }

   public void getTemperature() {
      temperatureRef.child("value").addListenerForSingleValueEvent(new ValueEventListener() {
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

   public void getSafeMode() {
      outRef.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            Out out = snapshot.getValue(Out.class);
            outValue.setValue(out);
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });
   }
}
