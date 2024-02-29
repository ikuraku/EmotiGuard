package com.example.emotiguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
private EditText name,email,cp,pas;
private String nameSt,emailSt,cpSt,pasSt;
private TextView log;
private FirebaseAuth firebaseAuth;
private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        log = findViewById(R.id.log);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        cp = findViewById(R.id.cp);
        pas = findViewById(R.id.pas);
        register = findViewById(R.id.register);
        firebaseAuth = FirebaseAuth.getInstance();


        log.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
        });
    }

    private boolean validate() {
        boolean result = false;
        nameSt = name.getText().toString();
        emailSt = email.getText().toString();
        cpSt = cp.getText().toString();
        pasSt = pas.getText().toString();

        if (nameSt.isEmpty() || nameSt.length() <= 7) {
            name.setError("name is invalid !");
        } else if (emailSt.isEmpty() || !emailSt.contains("@") || !emailSt.contains(".")) {
            email.setError("email is invalid !");
        } else if (pasSt.isEmpty() || pasSt.length()!=8) {
            pas.setError("password is invalid !");
        } else if (cpSt.isEmpty() || cpSt.length()!=8) {
            cp.setError("confirm password is invalid ! ");
        } else {
            result = true;
        }
        return  result ;
    }

    private void onClick(View v) {
        if (validate()) {
            String user_email = email.getText().toString().trim();
            String user_password = pas.getText().toString().trim();
            firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this,"register done !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                    }else {
                        Toast.makeText(SignUpActivity.this,"register failed !", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }
}