package com.example.android.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.android.R;
import com.example.android.model.DeviceController;
import com.example.android.model.Led;
import com.example.android.databinding.ActivityMainBinding;
import com.example.android.model.Out;
import com.example.android.util.RxAndroidUtils;
import com.example.android.viewmodel.MainViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    DeviceController deviceController;

    MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
        initObserve();
        initListener();
        initUtil();
    }

    void init(){
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getLed();
        viewModel.getDoor();
        viewModel.readMessageOLED();
        viewModel.getTemperature();
        viewModel.getSafeMode();

        deviceController = new DeviceController();
    }

    void initObserve() {
        viewModel.getLedLiveData().observe(this, new Observer<Led>() {
            @Override
            public void onChanged(Led led) {
                String on = "ON";
                String off = "OFF";
                if (led.isRed()) {
                    binding.redLed.setText(on);
                    led.setRed(true);
                } else {
                    binding.redLed.setText(off);
                    led.setRed(false);
                }

                if (led.isBlue()) {
                    binding.blueLed.setText(on);
                    led.setBlue(true);
                } else {
                    binding.blueLed.setText(off);
                    led.setBlue(false);
                }

                deviceController.setLed(led);
            }
        });

        viewModel.getDoorLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                String open = "OPEN";
                String close = "CLOSE";
                if (aBoolean) {
                    binding.doorTextView.setText(open);
                    binding.doorState.setText(open);
                    deviceController.setDoor(open);
                } else {
                    binding.doorTextView.setText(close);
                    binding.doorState.setText(close);
                    deviceController.setDoor(close);
                }


            }
        });

        viewModel.getTemperatureLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.temperature.setText(s);
                deviceController.setTemperature(s);
            }
        });

        viewModel.getOLEDMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

        viewModel.getOutLiveData().observe(this, new Observer<Out>() {
            @Override
            public void onChanged(Out out) {
                String yes = "YES";
                String no = "NO";
                if (out.isMode()) {
                    binding.outText.setText(yes);
                    binding.outState.setText(yes);
                    out.setMode(true);
                } else {
                    binding.outText.setText(no);
                    binding.outState.setText(no);
                    out.setMode(false);
                }
                if (out.isDetection()) {
                    viewModel.showNotification();
                }
                deviceController.setOut(out);
            }
        });
    }

    void initListener() {
        binding.light1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setBlueLed();
            }
        });

        binding.light2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setRedLed();
            }
        });

        binding.door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setDoor();
            }
        });

        binding.out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setSafeMode();
            }
        });


    }

    @SuppressLint("CheckResult")
    void initUtil() {
        Observable<String> editTextObservable = RxAndroidUtils.getInstance().getEditTextObservable(binding.message);
        editTextObservable
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.d(RxAndroidUtils.getInstance().getTag(), s);
                    String inputText = binding.message.getText().toString();
                    viewModel.writeMessageOLED(inputText);
                });
    }


}