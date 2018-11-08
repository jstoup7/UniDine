package com.example.unidine;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        Intent intent = getIntent();

    }

    public void createAccount(android.view.View view) {
        EditText email = (EditText) findViewById(R.id.userEmail);
        EditText password = (EditText) findViewById(R.id.userPassword);
        final EditText name = (EditText) findViewById(R.id.userName);
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(mAuth.getCurrentUser().getEmail());
                    user.setName(name.getText().toString());
                    DatabaseReference myRef = database.getReference("Users/" + mAuth.getCurrentUser().getUid());
                }
            }
        });
    }
}
