package com.example.emotiguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {
    private TextView sign,fp;
    private Button lo,go;
    private EditText em,pwd;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private static final String EMAIL_REGEX =
            "^[_A-Za_z0-9-\\+]+(\\.[_A-Za_z0-9-]+)*@" + "[_A-Za_z0-9-\\+]+(\\.[_A-Za_z0-9-]+)*(\\.[_A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        sign = findViewById(R.id.sign);
        fp  = findViewById(R.id.fp);
        lo  = findViewById(R.id.lo);
        go  = findViewById(R.id.go);
        em  = findViewById(R.id.em);
        pwd = findViewById(R.id.pwd);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);



        lo.setOnClickListener(v -> {
          if (!isValidEmail(em.getText().toString().trim())){
                em.setError("email is invalid !");
                em.requestFocus();
            }else if(pwd.getText().toString().isEmpty() || pwd.getText().toString().length()<8){
              pwd.setError("password is invalid !");
              pwd.requestFocus();
          }else {
              validate(em.getText().toString(),pwd.getText().toString());
          }


        });

        fp.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this,ForgetActivity.class));
        });
        sign.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
        });
    }

    private void validate(String emValidation, String pwdValidation) {
        progressDialog.setMessage("Please wait...!");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(emValidation,pwdValidation).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    checkEmailValidation();

                }else {
                    Toast.makeText(SignInActivity.this,"Please verify that your data is correct  !",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

            }
        });
    }

    private void checkEmailValidation() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        boolean isEmailFlag = firebaseUser.isEmailVerified();
        if(isEmailFlag){
            finish();
            startActivity(new Intent(SignInActivity.this,PrinActivity.class));
        }else {
            Toast.makeText(SignInActivity.this,"Please check your email !",Toast.LENGTH_SHORT).show();
            firebaseUser.sendEmailVerification();
            firebaseAuth.signOut();
        }
    }

    public static boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}