package com.example.unidine;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends MainActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);
        mAuth = FirebaseAuth.getInstance();
        Button btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {

            case R.id.btnCreateAccount:
                createAccount();
                break;

            default:
                break;
        }
    }

    public void createAccount() {
        EditText email = findViewById(R.id.userEmail);
        EditText password = findViewById(R.id.userPassword);
        EditText passwordCheck = findViewById(R.id.passwordCheck);
        final EditText name = findViewById(R.id.userName);
        if (password.getText().toString().equals(passwordCheck.getText().toString())) {
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        User user = new User(mAuth.getCurrentUser().getEmail());
                        user.setName(name.getText().toString());
                        DatabaseReference myRef = database.getReference("Users/" + mAuth.getCurrentUser().getUid());
                        myRef.setValue(user);
                        func2();
                    }
                }
            });
        } else {
            Intent intentMain = new Intent(this, MainActivity.class);
            startActivity(intentMain);
        }
    }

    public void func2() {
        Intent intentWelcome = new Intent(this, WelcomeActivity.class);
        startActivity(intentWelcome);
    }
}
