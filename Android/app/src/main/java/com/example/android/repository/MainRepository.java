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

   private MutableLiveData<String> ledValue;

   public MainRepository() {
      ledRef = FirebaseDatabase.getInstance().getReference("led");
      ledValue = new MutableLiveData<>();
   }

   public MutableLiveData<String> getLedLiveData() {
      return ledValue;
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
}
