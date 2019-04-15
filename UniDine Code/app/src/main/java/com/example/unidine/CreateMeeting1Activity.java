package com.example.unidine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class CreateMeeting1Activity extends MainActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createmeeting1);
        mAuth = FirebaseAuth.getInstance();
        Button btnSave = findViewById(R.id.btnNext);
        btnSave.setOnClickListener(this);
        Button btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {

            case R.id.btnNext:
                selectAttendees();
                break;

            case R.id.btnExit:
                welcomeSwitch();
                break;

            default:
                break;
        }
    }

    public void selectAttendees() {

        EditText date = findViewById(R.id.enteredDate);
        EditText time = findViewById(R.id.enteredTime);
        EditText location = findViewById(R.id.enteredLocation);

        Intent intentCreateMeeting2 = new Intent(this, CreateMeeting2Activity.class);
        intentCreateMeeting2.putExtra("date", date.getText().toString());
        intentCreateMeeting2.putExtra("time", time.getText().toString());
        intentCreateMeeting2.putExtra("location", location.getText().toString());
        startActivity(intentCreateMeeting2);
    }

    //Switches to WelcomeActivity
    public void welcomeSwitch() {
        Intent intentWelcome = new Intent(this, WelcomeActivity.class);
        startActivity(intentWelcome);
    }

}
