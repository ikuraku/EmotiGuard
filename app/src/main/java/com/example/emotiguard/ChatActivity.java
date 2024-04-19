package com.example.emotiguard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.emotiguard.adapter.MessageAdapter;
import com.example.emotiguard.databinding.ActivityMainBinding;
import com.example.emotiguard.model.MessageM;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    List<MessageM>  messageModels ;
    MessageAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());


        setContentView(binding.getRoot());


        messageModels = new ArrayList<>();
         // initialize adapter class
        adapter = new MessageAdapter(messageModels);










    }




}