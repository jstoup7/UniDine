package com.example.unidine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MeetingConfirmationActivity extends MainActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String temp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetingconfirmation);
        mAuth = FirebaseAuth.getInstance();
        Button btnSeeAll = findViewById(R.id.btnSeeAll);
        btnSeeAll.setOnClickListener(this);
        Button btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);
        DatabaseReference myRef = database.getReference("Meetings/" + getIntent().getStringExtra("ID"));
        myRef.addListenerForSingleValueEvent(postListener);
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {

            case R.id.btnSeeAll:
                seeAllSwitch();
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

            EditText date = findViewById(R.id.dateInfo);
            EditText time = findViewById(R.id.timeInfo);
            EditText location = findViewById(R.id.locationInfo);

            date.setText(dataSnapshot.child("date").getValue().toString());
            time.setText(dataSnapshot.child("time").getValue().toString());
            location.setText(dataSnapshot.child("location").getValue().toString());

            temp = " and " + (((ArrayList)dataSnapshot.child("attendees").getValue()).size() - 1) + " other";
            if ((((ArrayList)dataSnapshot.child("attendees").getValue()).size() - 1) > 1) {
                temp += "s";
            }
            DatabaseReference tempRef = database.getReference("Users/" + dataSnapshot.child("attendees/0").getValue().toString());
            tempRef.addListenerForSingleValueEvent(postListener2);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    ValueEventListener postListener2 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            EditText attendees = findViewById(R.id.attendeesInfo);
            attendees.setText(dataSnapshot.child("name").getValue().toString() + temp);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    public void seeAllSwitch() {
        Intent intentAttendeeList = new Intent(this, AttendeesListActivity.class);
        intentAttendeeList.putExtra("ID", getIntent().getStringExtra("ID"));
        startActivity(intentAttendeeList);
    }

    //Switches to WelcomeActivity
    public void welcomeSwitch() {
        Intent intentWelcome = new Intent(this, WelcomeActivity.class);
        startActivity(intentWelcome);
    }

}
