package com.example.emotiguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private Button edit, btnLo;
    private EditText nameE,emailE,pasE;

    private FirebaseUser user;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private static final String EMAIL_REGEX =
            "^[_A-Za_z0-9-\\+]+(\\.[_A-Za_z0-9-]+)*@" + "[_A-Za_z0-9-\\+]+(\\.[_A-Za_z0-9-]+)*(\\.[_A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        edit = findViewById(R.id.edit);
        btnLo = findViewById(R.id.btnLo);
        nameE = findViewById(R.id.nameE);
        emailE = findViewById(R.id.emailE);
        pasE = findViewById(R.id.pasE);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = firebaseAuth.getCurrentUser();
        reference = firebaseDatabase.getReference().child("Users").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nameSt = snapshot.child("name").getValue().toString();
                nameE.setText("nameSt");
                String emailSt = snapshot.child("email").getValue().toString();
                emailE.setText("emailSt");
                String pasSt = snapshot.child("pas").getValue().toString();
                pasE.setText("pasSt");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Error !", Toast.LENGTH_SHORT).show();
            }
        });

        btnLo.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("checkBox",MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember","false");
            editor.apply();
            firebaseAuth.signOut();
            startActivity(new Intent(ProfileActivity.this,SignInActivity.class));
            Toast.makeText(this,"Log out successfully !",Toast.LENGTH_SHORT).show();
            finish();
        });

    }
}