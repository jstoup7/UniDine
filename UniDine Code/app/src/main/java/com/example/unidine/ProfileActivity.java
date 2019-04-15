package com.example.unidine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends MainActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        DatabaseReference myRef = database.getReference("Users/" + getIntent().getStringExtra("userID"));
        myRef.addListenerForSingleValueEvent(postListener);
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {

            case R.id.btnBack:
                userListSwitch();
                break;

            default:
                break;
        }
    }

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            TextView name = findViewById(R.id.userNameView);
            TextView age = findViewById(R.id.userAgeView);
            TextView radius = findViewById(R.id.LocationLabel);
            TextView food = findViewById(R.id.userFoodPreferenceView);

            name.setText("Name: " + dataSnapshot.child("name").getValue().toString());
            age.setText("Age: " + dataSnapshot.child("age").getValue().toString());
            radius.setText("Radius: " + dataSnapshot.child("radius").getValue().toString());
            food.setText("Food preference: " + dataSnapshot.child("food").getValue().toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    //Switches to WelcomeActivity
    public void userListSwitch() {
        Intent intentUserList = new Intent(this, ViewUserListActivity.class);
        startActivity(intentUserList);
    }
}
