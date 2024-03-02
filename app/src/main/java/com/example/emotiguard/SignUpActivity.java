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

import com.example.emotiguard.Models.UserProfile;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseRegistrar;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText name, email, cp, pas;
    private String nameSt, emailSt, cpSt, pasSt;
    private TextView log;
    private FirebaseAuth firebaseAuth;
    private Button register;
    private static final String EMAIL_REGEX =
            "^[_A-Za_z0-9-\\+]+(\\.[_A-Za_z0-9-]+)*@" + "[_A-Za_z0-9-\\+]+(\\.[_A-Za_z0-9-]+)*(\\.[_A-Za-z]{2,})$";



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
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            Toast.makeText(this, "go to SignIn activity ", Toast.LENGTH_SHORT).show();
        });
        register.setOnClickListener(v -> {
            if (validate()){
                String user_email = email.getText().toString().trim();
                String user_password = pas.getText().toString().trim();
                firebaseAuth.createUserWithEmailAndPassword(emailSt,pasSt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           senEmailVerification();
                        }else {
                            Toast.makeText(SignUpActivity.this,"register failed !",Toast.LENGTH_SHORT).show();
                        }

                    }
                } );
            }
        });


    }

    private void senEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null ){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                     if (task.isSuccessful()){
                         sendUserData();
                         Toast.makeText(SignUpActivity.this,"register done . Please check your email address!",Toast.LENGTH_SHORT).show();
                         firebaseAuth.signOut();
                         finish();
                         startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                     }else {
                         Toast.makeText(SignUpActivity.this,"registration failed",Toast.LENGTH_SHORT).show();
                     }
                 }
            });
        }
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("user");
        UserProfile userProfile = new UserProfile(nameSt,emailSt,pasSt);
        myRef.child(""+firebaseAuth.getUid()).setValue(userProfile);
    }


    private boolean validate() {
      boolean res = false;
      nameSt = name.getText().toString().trim();
      emailSt = email.getText().toString().trim();
      cpSt = cp.getText().toString().trim();
      pasSt = pas.getText().toString().trim();

        if (nameSt.isEmpty() || nameSt.length()<7){
            name.setError(" name is invalid !");
        } else if (cpSt.length() != 8) {
            cp.setError("CIN is invalid!");
        }else if (pasSt.length() != 8) {
            pas.setError("Phone is invalid!");
        } else if (isValidEmail(emailSt)) {
            email.setError("email is invalid !");
        } else {
            res = true;
        }

      return res;
    }

    public static boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }




}

