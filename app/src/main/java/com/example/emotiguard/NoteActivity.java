package com.example.emotiguard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class NoteActivity extends AppCompatActivity {

    FloatingActionButton addButton;
    RecyclerView recyclerView;
    ImageButton menuBtn;
    NoteAd noteAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        addButton = findViewById(R.id.add);
        recyclerView = findViewById(R.id.recycle_view);
        menuBtn = findViewById(R.id.menu_btn);

        setupRecyclerView();
        addButton.setOnClickListener((v)-> startActivity(new Intent(NoteActivity.this,NoteDetailActivity.class)) );
        setupRecyclerView();


    }




     void setupRecyclerView() {
         Query query  = Utility.getCollectionReferenceForNotes().orderBy("timestamp", Query.Direction.DESCENDING);
         FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                 .setQuery(query,Note.class).build();

         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         noteAd = new NoteAd(options,this);
         recyclerView.setAdapter(noteAd);
    }

    protected void onStart() {
        super.onStart();
        noteAd.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAd.stopListening();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        noteAd.notifyDataSetChanged();
    }


}