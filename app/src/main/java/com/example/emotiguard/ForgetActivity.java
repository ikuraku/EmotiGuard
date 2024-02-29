package com.example.emotiguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ForgetActivity extends AppCompatActivity {
    private Button btnB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        btnB = findViewById(R.id.btnB);

        btnB.setOnClickListener(v -> {
            startActivity(new Intent(ForgetActivity.this,SignInActivity.class));
        });
    }
}