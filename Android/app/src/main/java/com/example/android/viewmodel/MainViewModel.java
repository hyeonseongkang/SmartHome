package com.example.android.viewmodel;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.R;
import com.example.android.model.Led;
import com.example.android.model.Out;
import com.example.android.repository.MainRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainViewModel extends AndroidViewModel {

   Application application;
   private MainRepository repository;

   private DatabaseReference ledRef;

   private DatabaseReference doorRef;

   private DatabaseReference oledRef;

   private DatabaseReference outRef;

   private LiveData<Led> ledValue;
   private LiveData<String> temperatureValue;
   private LiveData<Boolean> doorValue;
   private LiveData<String> oledMessageValue;
   private LiveData<Out> outValue;


   public MainViewModel(@NonNull Application application) {
      super(application);
      this.application = application;
      repository = new MainRepository();
      ledRef = FirebaseDatabase.getInstance().getReference("led");
      doorRef = FirebaseDatabase.getInstance().getReference("door");
      oledRef = FirebaseDatabase.getInstance().getReference("oled");
      outRef = FirebaseDatabase.getInstance().getReference("out");
      ledValue = repository.getLedLiveData();
      temperatureValue = repository.getTemperatureLiveData();
      doorValue = repository.getDoorLiveData();
      oledMessageValue = repository.getOLEDMessageLiveData();
      outValue = repository.getOutLiveData();

   }

   public LiveData<Led> getLedLiveData() {
      return ledValue;
   }

   public LiveData<String> getTemperatureLiveData() {return  temperatureValue; }

   public LiveData<Boolean> getDoorLiveData() { return doorValue; }

   public LiveData<String> getOLEDMessageLiveData() { return  oledMessageValue; }

   public LiveData<Out> getOutLiveData() { return outValue; }

   public void setRedLed() {
      ledRef.child("red").setValue(ledValue.getValue().isRed() ? false : true);
   }

   public void setBlueLed() {
      ledRef.child("blue").setValue(ledValue.getValue().isBlue() ? false : true);
   }

   public void getLed() {
      repository.getLed();
   }

   public void setDoor() {
      doorRef.setValue(!doorValue.getValue());
   }

   public void setSafeMode() {
      Out safeMode = outValue.getValue();
      safeMode.setMode(!safeMode.isMode());
      safeMode.setDetection(false);
      outRef.setValue(safeMode);
   }


   public void getDoor() {
      repository.getDoor();
   }


   public void writeMessageOLED(String message) {
      oledRef.setValue(message);
   }

   public void readMessageOLED   () {
      repository.readMessageOLED();
   }

   public void getTemperature() {
      repository.getTemperature();
   }

   public void getSafeMode() {
      repository.getSafeMode();
   }

   public void showNotification() {
      try {
         NotificationManager notificationManager = (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);
         NotificationCompat.Builder builder = null;
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelID = "channel_01";
            String channelName = "MyChannel01";

            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(channel);

            builder = new NotificationCompat.Builder(application, channelID);
         }

         builder.setSmallIcon(android.R.drawable.ic_menu_view);

         builder.setContentTitle("집안에 누군가 있습니다");
         builder.setContentText("112 연결");
         Bitmap bm = BitmapFactory.decodeResource(application.getResources(), R.drawable.safe);
         builder.setLargeIcon(bm);

         // 전화 연결하는 화면으로 이동하는 PendingIntent 생성
         Intent intent = new Intent(Intent.ACTION_DIAL);
         intent.setData(Uri.parse("tel:112"));
         PendingIntent pendingIntent = PendingIntent.getActivity(application, 0, intent, PendingIntent.FLAG_IMMUTABLE);

         builder.setContentIntent(pendingIntent); // PendingIntent 설정

         Notification notification = builder.build();
         notificationManager.notify(1, notification);
      } catch (Exception e) {

      }

   }


}
