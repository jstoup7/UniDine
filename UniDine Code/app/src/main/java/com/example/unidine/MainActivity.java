package com.example.unidine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

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
        });
    }

    public void createAccount(android.view.View view) {
        EditText email = (EditText)findViewById(R.id.userEmail);
        EditText password = (EditText)findViewById(R.id.userPassword);
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
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

    public void func() {
        setContentView(R.layout.activity_welcome);
        TextView welcomeText = (TextView)findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome " + mAuth.getCurrentUser().getEmail());
    }
}

