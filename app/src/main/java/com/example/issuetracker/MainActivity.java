package com.example.issuetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int SECRET_KEY = 99;
    private static final String PREF_KEY = Objects.requireNonNull(MainActivity.class.getPackage()).toString();

    EditText usernameET;
    EditText passwordET;
    private FirebaseAuth mAuth;


    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameET = findViewById(R.id.editTextUserName);
        passwordET = findViewById(R.id.editTextPassword);
        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
    }

    public void login(View view) {
        String userNameStr = usernameET.getText().toString();
        String passStr = passwordET.getText().toString();

        mAuth.signInWithEmailAndPassword(userNameStr, passStr).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.i(LOG_TAG, "Successful user login");
                startIssueTracking();
            } else {
                Log.d(LOG_TAG, "Failed to log in user");
                Toast.makeText(MainActivity.this, "User login failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void signup(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    private void startIssueTracking() {
        Intent intent = new Intent(this, IssueTrackingActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName", usernameET.getText().toString());
        editor.putString("password", passwordET.getText().toString());
        editor.apply();
    }

}