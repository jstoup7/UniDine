package com.example.unidine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener {
    private static FirebaseAuth mAuth;
    //FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Button btnLoginMain = findViewById(R.id.btnLoginMain);
        btnLoginMain.setOnClickListener(this);
        Button btnCreateAccountMain = findViewById(R.id.btnCreateAccountMain);
        btnCreateAccountMain.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {

            case R.id.btnLoginMain:
                loginSwitch();
                break;

            case R.id.btnCreateAccountMain:
                createAccountSwitch();
                break;

            default:
                break;
        }
    }

    //Switches to LoginActivity
    public void loginSwitch() {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
    }

    //Switches to CreateAccountActivity
    public void createAccountSwitch() {
        Intent intentCreateAccount1 = new Intent(this, CreateAccount1Activity.class);
        startActivity(intentCreateAccount1);
    }
}

