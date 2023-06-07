package com.example.android.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.repository.MainRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainViewModel extends AndroidViewModel {

   private MainRepository repository;

   private DatabaseReference ledRef;

   private LiveData<String> ledValue;
   private LiveData<String> temperatureValue;


   public MainViewModel(@NonNull Application application) {
      super(application);
      repository = new MainRepository();
      ledRef = FirebaseDatabase.getInstance().getReference("led");
      ledValue = repository.getLedLiveData();
      temperatureValue = repository.getTemperatureLiveData();

   }

   public LiveData<String> getLedLiveData() {
      return ledValue;
   }

   public LiveData<String> getTemperatureLiveData() {return  temperatureValue; }

   public void turnOnLed(String led) {
      ledRef.setValue(led);
   }

   public void turnOffLed(String led) {
      ledRef.setValue(led);
   }

   public void getLed() {
      repository.getLed();
   }
}
