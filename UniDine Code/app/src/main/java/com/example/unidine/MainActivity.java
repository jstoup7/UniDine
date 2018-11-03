package com.example.unidine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.signOut();
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

    public void createAccount(android.view.View view) {
        EditText email = (EditText)findViewById(R.id.userEmail);
        EditText password = (EditText)findViewById(R.id.userPassword);
        final EditText name = (EditText)findViewById(R.id.userName);
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(mAuth.getCurrentUser().getEmail());
                    user.setName(name.getText().toString());
                    DatabaseReference myRef = database.getReference("Users/" + mAuth.getCurrentUser().getUid());
                    myRef.setValue(user);
                    func();
                }
            }
        });
    }

    public void createAccountSwitch(android.view.View view) {
        setContentView(R.layout.activity_createaccount);
    }

    public void loginSwitch(android.view.View view) {
        setContentView(R.layout.activity_login);
    }

    public void logout(android.view.View view) {
        mAuth.signOut();
        setContentView(R.layout.activity_main);
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
}

