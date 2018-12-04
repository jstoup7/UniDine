package com.example.unidine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ViewUserListActivity extends MainActivity {
    final ViewUserListActivity self = this;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        myRef.addListenerForSingleValueEvent(postListener);
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {

            default:
                profileSwitch(view.getTag().toString());
                break;
        }
    }

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            LinearLayout myLinearLayout = findViewById(R.id.layoutUserView);
            for (Object key : ((HashMap)dataSnapshot.getValue()).keySet()) {
                if (!key.equals(mAuth.getCurrentUser().getUid())) {
                    final Button rowButton = new Button(self);
                    rowButton.setText(dataSnapshot.child(key.toString()).child("name").getValue().toString());
                    rowButton.setTag(key.toString());
                    rowButton.setOnClickListener(self);
                    myLinearLayout.addView(rowButton);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    public void profileSwitch(String ID) {
        Intent intentProfile = new Intent(this, ProfileActivity.class);
        intentProfile.putExtra("userID", ID);
        startActivity(intentProfile);
    }
}
