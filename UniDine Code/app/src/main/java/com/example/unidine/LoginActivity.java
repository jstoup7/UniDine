package com.example.unidine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends MainActivity {

    private FirebaseAuth mAuth;
   // FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {

            case R.id.btnLogin:
                login();
                break;

            default:
                break;
        }
    }

    public void login() {
        EditText email = findViewById(R.id.userEmail);
        EditText password = findViewById(R.id.userPassword);
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

    public void func() {
        Intent intentWelcome = new Intent(this, WelcomeActivity.class);
        startActivity(intentWelcome);
    }
}
