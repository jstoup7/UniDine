package com.example.unidine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class UpdateProfileActivity extends MainActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);
        mAuth = FirebaseAuth.getInstance();
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        Button btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);
        System.out.println(mAuth.getCurrentUser());
        DatabaseReference myRef = database.getReference("Users/" + mAuth.getCurrentUser().getUid());
        myRef.addListenerForSingleValueEvent(postListener);
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {

            case R.id.btnSave:
                updateUserInfo();
                break;

            case R.id.btnExit:
                welcomeSwitch();
                break;

            default:
                break;
        }
    }

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            EditText name = findViewById(R.id.userName);
            EditText age = findViewById(R.id.userAge);
            EditText radius = findViewById(R.id.userRadius);
            EditText email = findViewById(R.id.userEmail);
            EditText food = findViewById(R.id.userFoodPreference);

            name.setHint(dataSnapshot.child("name").getValue().toString());
            age.setHint(dataSnapshot.child("age").getValue().toString());
            radius.setHint(dataSnapshot.child("radius").getValue().toString());
            email.setHint(dataSnapshot.child("email").getValue().toString());
            food.setHint(dataSnapshot.child("food").getValue().toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    public void updateUserInfo() {

        EditText name = findViewById(R.id.userName);
        EditText age = findViewById(R.id.userAge);
        EditText radius = findViewById(R.id.userRadius);
        EditText food = findViewById(R.id.userFoodPreference);

        Map<String, Object> childUpdates = new HashMap<>();
        if (name.getText().toString().length() > 0){
            childUpdates.put("/name/", name.getText().toString());
        }
        if (age.getText().toString().length() > 0) {
            childUpdates.put("/age/", age.getText().toString());
        }
        if (radius.getText().toString().length() > 0) {
            childUpdates.put("/radius/", radius.getText().toString());
        }
        if (food.getText().toString().length() > 0) {
            childUpdates.put("/food/", food.getText().toString());
        }

        database.getReference("Users/" + mAuth.getCurrentUser().getUid()).updateChildren(childUpdates);

        welcomeSwitch();
    }

    //Switches to WelcomeActivity
    public void welcomeSwitch() {
        Intent intentWelcome = new Intent(this, WelcomeActivity.class);
        startActivity(intentWelcome);
    }

}
