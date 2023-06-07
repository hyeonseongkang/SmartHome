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

   private MutableLiveData<String> ledValue;
   private MutableLiveData<String> temperatureValue;

   public MainRepository() {
      ledRef = FirebaseDatabase.getInstance().getReference("led");
      temperatureRef = FirebaseDatabase.getInstance().getReference("temperature");
      ledValue = new MutableLiveData<>();
      temperatureValue = new MutableLiveData<>();
   }

   public MutableLiveData<String> getLedLiveData() {
      return ledValue;
   }

   public MutableLiveData<String> getTemperatureLiveData() { return  temperatureValue; }

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
}
