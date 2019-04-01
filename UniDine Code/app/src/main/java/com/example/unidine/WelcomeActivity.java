package com.example.unidine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import co.chatsdk.core.session.InterfaceManager;

public class WelcomeActivity extends MainActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAuth = FirebaseAuth.getInstance();
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);
        Button btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        btnUpdateProfile.setOnClickListener(this);
        Button btnViewUserList = findViewById(R.id.btnViewUserList);
        btnViewUserList.setOnClickListener(this);
        Button btnViewMessaging = findViewById(R.id.btnViewMessaging);
        btnViewMessaging.setOnClickListener(this);
        DatabaseReference myRef = database.getReference("Users/" + mAuth.getCurrentUser().getUid() + "/name");
        myRef.addListenerForSingleValueEvent(postListener);
    }

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            TextView welcomeText = findViewById(R.id.welcomeText);
            String text = "Welcome " + dataSnapshot.getValue();
            welcomeText.setText(text);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {

            case R.id.btnLogout:
                logout();
                break;

            case R.id.btnUpdateProfile:
                updateProfileSwitch();
                break;

            case R.id.btnViewUserList:
                userListSwitch();
                break;

            case R.id.btnViewMessaging:
                Context context = getApplicationContext();
                InterfaceManager.shared().a.startLoginActivity(context, true);
                break;

            default:
                break;
        }
    }

    public void logout() {
        mAuth.signOut();
        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);
    }

    public void updateProfileSwitch() {
        Intent intentUpdateProfile = new Intent(this, UpdateProfileActivity.class);
        startActivity(intentUpdateProfile);
    }

    public void userListSwitch() {
        Intent intentUserList = new Intent(this, ViewUserListActivity.class);
        startActivity(intentUserList);
    }
}
