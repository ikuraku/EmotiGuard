package com.example.emotiguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetActivity extends AppCompatActivity {
    private Button btnB,res;
    private EditText fe;
    private FirebaseAuth firebaseAuth;
    private static final String EMAIL_REGEX =
            "^[_A-Za_z0-9-\\+]+(\\.[_A-Za_z0-9-]+)*@" + "[_A-Za_z0-9-\\+]+(\\.[_A-Za_z0-9-]+)*(\\.[_A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        btnB = findViewById(R.id.btnB);
        fe = findViewById(R.id.fe);
        res = findViewById(R.id.res);
        firebaseAuth = FirebaseAuth.getInstance();

        btnB.setOnClickListener(v -> {
            startActivity(new Intent(ForgetActivity.this,SignInActivity.class));
        });

        fe.setOnClickListener(v -> {
            if(!isValidEmail(res.getText().toString().trim())){
                res.setError("Please enter a valid email address !");
            }else {
                firebaseAuth.sendPasswordResetEmail(res.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgetActivity.this, "password reset email sent !", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(ForgetActivity.this,SignInActivity.class));
                        }else {
                            Toast.makeText(ForgetActivity.this, "ERROR !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }


    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}