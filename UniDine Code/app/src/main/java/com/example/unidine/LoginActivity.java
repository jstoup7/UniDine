package com.example.unidine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
    }

    public void login(android.view.View view) {
        EditText email = (EditText)findViewById(R.id.userEmail);
        EditText password = (EditText)findViewById(R.id.userPassword);
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    func();
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                System.out.println(exception.getMessage());
            }
        });
    }

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            setContentView(R.layout.activity_welcome);
            TextView welcomeText = (TextView)findViewById(R.id.welcomeText);
            welcomeText.setText("Welcome " + dataSnapshot.getValue());
            // ...
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };

    public void func() {
        DatabaseReference myRef = database.getReference("Users/" + mAuth.getCurrentUser().getUid() + "/name");
        myRef.addListenerForSingleValueEvent(postListener);
    }

    public void mainSwitch(android.view.View view) {

    }
}
