package com.example.unidine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MeetingsListActivity extends MainActivity {
    final MeetingsListActivity self = this;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    HashMap<String, String> meetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetingslist);
        mAuth = FirebaseAuth.getInstance();
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        DatabaseReference myRef = database.getReference("Users/" + mAuth.getUid() + "/Meetings");
        myRef.addListenerForSingleValueEvent(postListener);
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {

            case R.id.btnBack:
                welcomeSwitch();
                break;

            default:
                meetingSwitch(view.getTag().toString());
                break;
        }
    }

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            meetings = (HashMap) dataSnapshot.getValue();
            DatabaseReference myRef = database.getReference("Meetings");
            myRef.addListenerForSingleValueEvent(postListener2);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    ValueEventListener postListener2 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            LinearLayout myLinearLayout = findViewById(R.id.layoutMeetingsList);
            for (Object key : meetings.keySet()) {
                final Button rowButton = new Button(self);
                rowButton.setText(dataSnapshot.child(meetings.get(key)).child("time").getValue().toString() + " on " + dataSnapshot.child(meetings.get(key)).child("date").getValue().toString());
                rowButton.setTag(meetings.get(key));
                rowButton.setOnClickListener(self);
                myLinearLayout.addView(rowButton);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    //Switches to WelcomeActivity
    public void welcomeSwitch() {
        Intent intentWelcome = new Intent(this, WelcomeActivity.class);
        startActivity(intentWelcome);
    }

    public void meetingSwitch(String ID) {
        Intent intentMeetingConfirmation = new Intent(this, MeetingConfirmationActivity.class);
        intentMeetingConfirmation.putExtra("ID", ID);
        startActivity(intentMeetingConfirmation);
    }
}
