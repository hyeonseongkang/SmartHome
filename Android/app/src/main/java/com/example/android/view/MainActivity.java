package com.example.android.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.android.model.Led;
import com.example.android.databinding.ActivityMainBinding;
import com.example.android.util.RxAndroidUtils;
import com.example.android.viewmodel.MainViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

    }

    void initObserve() {
        viewModel.getLedLiveData().observe(this, new Observer<Led>() {
            @Override
            public void onChanged(Led led) {
                String on = "ON";
                String off = "OFF";
                if (led.getRedLedState()) {
                    binding.light1State.setText(on);
                } else {
                    binding.light1State.setText(off);
                }

                if (led.getBlueLedState()) {
                    binding.light2State.setText(on);
                } else {
                    binding.light2State.setText(off);
                }
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
                } else {
                    binding.doorTextView.setText(close);
                    binding.doorState.setText(close);
                }
            }
        });

        viewModel.getTemperatureLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

        viewModel.getOLEDMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.OLEDText.setText(s);
            }
        });
    }

    void initListener() {
        binding.light1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setLed("Red");
            }
        });

        binding.light2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setLed("Blue");
            }
        });

        binding.door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setDoor();
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
                    Log.d(RxAndroidUtils.getInstance().getTag() + "!@!@", inputText);
                    viewModel.writeMessageOLED(inputText);
                });
    }


}