package com.example.android.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.android.R;
import com.example.android.databinding.ActivityMainBinding;
import com.example.android.viewmodel.MainViewModel;

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
    }

    void init(){
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

    }

    void initObserve() {
        viewModel.getLedLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

        viewModel.getDoorLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

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

            }
        });
    }

    void initListener() {
        binding.light1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.light2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}