package com.example.emotiguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {
    private TextView sign,fp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        sign = findViewById(R.id.sign);
        fp  = findViewById(R.id.fp);

        fp.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this,ForgetActivity.class));
        });
        sign.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
        });
    }
}