package com.example.emotiguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView iconMenu;
    private Button cln,chat,face,note;



    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;




    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        drawerLayout = findViewById(R.id.drawer_home);
        navigationView = findViewById(R.id.navigation_view_home);
        iconMenu = findViewById(R.id.icon_home);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();

        cln = findViewById(R.id.cln);
        face = findViewById(R.id.face);
        note = findViewById(R.id.note);
        chat = findViewById(R.id.chat);


        cln.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, CalendarActivity.class));
        });
        chat.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ChatActivity.class));
        });
        face.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this,FaceAnalyserActivity.class));
        });
        note.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this,NoteActivity.class));
        });

        navigationDrawer();
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.mode:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(HomeActivity.this, ModeActivity.class));
                    break;
                case R.id.out:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
            }


          return true ;
        });





    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.profile);
        iconMenu.setOnClickListener(v -> {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }else  drawerLayout.openDrawer(GravityCompat.START);
        });
        drawerLayout.setScrimColor(getResources().getColor(R.color.white));

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }


    public  void  onBackPressed() {
        super.onBackPressed();
        if (drawerLayout.isDrawerVisible(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }

}