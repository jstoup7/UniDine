package com.example.unidine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import java.util.HashMap;

public class CreateMeeting2Activity extends MainActivity {
    final CreateMeeting2Activity self = this;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<String> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createmeeting2);
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        myRef.addListenerForSingleValueEvent(postListener);
        Button btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);
        Button btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {
            case R.id.btnCreate:
                createMeeting();
                break;

            case R.id.btnExit:
                welcomeSwitch();
                break;

            default:
                addUser(view.getTag().toString());
                if (((ColorDrawable) view.getBackground()).getColor() == Color.RED) {
                    view.setBackgroundColor(Color.GRAY);
                } else {
                    view.setBackgroundColor(Color.RED);
                }
                break;
        }
    }

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            LinearLayout myLinearLayout = findViewById(R.id.layoutMeeting2);
            for (Object key : ((HashMap)dataSnapshot.getValue()).keySet()) {
                if (!key.equals(mAuth.getCurrentUser().getUid())) {
                    final Button rowButton = new Button(self);
                    rowButton.setText(dataSnapshot.child(key.toString()).child("name").getValue().toString());
                    rowButton.setTag(key.toString());
                    rowButton.setOnClickListener(self);
                    rowButton.setBackgroundColor(Color.GRAY);
                    myLinearLayout.addView(rowButton);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    public void createMeeting() {
        String id = Meeting.makeMeeting(getIntent().getStringExtra("date"), getIntent().getStringExtra("time"), getIntent().getStringExtra("location"), users, mAuth.getUid());
        Intent intentMeetingConfirmation = new Intent(this, MeetingConfirmationActivity.class);
        intentMeetingConfirmation.putExtra("ID", id);
        startActivity(intentMeetingConfirmation);
    }

    public void welcomeSwitch() {
        Intent intentWelcome = new Intent(this, WelcomeActivity.class);
        startActivity(intentWelcome);
    }

    public void addUser(String ID) {
        for (int i = 0; i <  users.size(); i++) {
            if (users.get(i).equals(ID)) {
                users.remove(i);
                return;
            }
        }
        users.add(ID);
    }
}
