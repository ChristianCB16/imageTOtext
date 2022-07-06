package com.example.textdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.textdemo.Model.translateData;
import com.example.textdemo.historialAdapter.historialAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class RememberActivity extends AppCompatActivity {
    //Intent intent = getIntent();
    //String correo = intent.getStringExtra("email_key");
    String correo;
    historialAdapter mAdapter;
    RecyclerView mRecycler;
    FirebaseFirestore mFirestore;
    Query query;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        correo = user.getEmail();
        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recyclerTranslations);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        query = mFirestore.collection("Traduccion").whereEqualTo("Email", correo);


        FirestoreRecyclerOptions<translateData> FirestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<translateData>().setQuery(query, translateData.class).build();
        mAdapter = new historialAdapter(FirestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}