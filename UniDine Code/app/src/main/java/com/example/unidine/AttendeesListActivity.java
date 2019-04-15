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

import java.util.ArrayList;

public class AttendeesListActivity extends MainActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    AttendeesListActivity self = this;
    ArrayList<String> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendeelist);
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        DatabaseReference myRef = database.getReference("Meetings/" + getIntent().getStringExtra("ID") + "/attendees");
        myRef.addListenerForSingleValueEvent(postListener);
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {

            case R.id.btnBack:
                confirmationSwitch();
                break;
            default:
                break;
        }
    }

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            users = (ArrayList)dataSnapshot.getValue();
            DatabaseReference tempRef = database.getReference("Users");
            tempRef.addListenerForSingleValueEvent(postListener2);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    ValueEventListener postListener2 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            LinearLayout myLinearLayout = findViewById(R.id.layoutAttendeesList);
            for (Object key : users) {
                final Button rowButton = new Button(self);
                rowButton.setText(dataSnapshot.child(key.toString()).child("name").getValue().toString());
                rowButton.setTag(key.toString());
                myLinearLayout.addView(rowButton);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    public void confirmationSwitch() {
        Intent intentMeetingConfirmation = new Intent(this, MeetingConfirmationActivity.class);
        intentMeetingConfirmation.putExtra("ID", getIntent().getStringExtra("ID"));
        startActivity(intentMeetingConfirmation);
    }
}
