package com.example.unidine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class LoginActivity extends MainActivity {

    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    int RC_SIGN_IN = 10;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        SignInButton googleSignIn = findViewById(R.id.sign_in_button);
        googleSignIn.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("194598114809-8spiphe257umohq4cl42973uc56kg572.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onStart() {
        super.onStart();
        account = GoogleSignIn.getLastSignedInAccount(this);
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {

            case R.id.sign_in_button:
                signIn();
                break;

            default:
                break;
        }
    }

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            System.out.println("TEST3");
            if (dataSnapshot.child("isAccountSetup").getValue().toString() == "true") {
                func();
            } else {
                func2();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    private void signIn() {
        System.out.println("SIGN IN");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            firebaseAuthWithGoogle(task.getResult());
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        final LoginActivity self = this;
        final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.fetchSignInMethodsForEmail(acct.getEmail()).addOnCompleteListener(this, new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.isSuccessful() && task.getResult().getSignInMethods().size() > 0) {
                    mAuth.signInWithCredential(credential)
                            .addOnCompleteListener(self, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        DatabaseReference myRef = database.getReference("Users/" + mAuth.getCurrentUser().getUid());
                                        myRef.addListenerForSingleValueEvent(postListener);
                                    } else {
                                        System.out.println("FAILURE");
                                    }
                                }
                            });
                } else {
                    Intent intentMain = new Intent(self, MainActivity.class);
                    startActivity(intentMain);
                }
            }
        });
    }

    public void func() {
        Intent intentWelcome = new Intent(this, WelcomeActivity.class);
        startActivity(intentWelcome);
    }

    public void func2() {
        Intent intentCreateAccount2 = new Intent(this, CreateAccount2Activity.class);
        startActivity(intentCreateAccount2);
    }
}
