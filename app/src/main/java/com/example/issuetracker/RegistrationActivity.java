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

public class RegistrationActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int SECRET_KEY = 99;
    private static final String PREF_KEY = Objects.requireNonNull(MainActivity.class.getPackage()).toString();

    private FirebaseAuth mAuth;

    EditText userNameEditText;
    EditText userEmailEditText;
    EditText passwordEditText;
    EditText passwordAgainEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);
        if (secret_key != SECRET_KEY) {
            finish();
        }

        SharedPreferences preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();

        userNameEditText = findViewById(R.id.editTextUserNameReg);
        userEmailEditText = findViewById(R.id.editTextEmailReg);
        passwordEditText = findViewById(R.id.editTextPasswordReg);
        passwordAgainEditText = findViewById(R.id.editTextPasswordAgainReg);


        String sharedUsername = preferences.getString("userName", "");
        String sharedPassword = preferences.getString("password", "");

        userNameEditText.setText(sharedUsername);
        passwordEditText.setText(sharedPassword);
    }

    public void register(View view) {
        String userNameStr = userNameEditText.getText().toString();
        String emailStr = userEmailEditText.getText().toString().trim();
        String passwordStr = passwordEditText.getText().toString();
        String passwordAgainStr = passwordAgainEditText.getText().toString();

        if (!passwordStr.equals(passwordAgainStr)) {
            Log.e(LOG_TAG, "register: passwords do not match");
        }

        Log.i(LOG_TAG, "Registration: " + userNameStr +emailStr +passwordStr +passwordAgainStr);
        mAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.d(LOG_TAG, "User created");
                startIssueTracking();
            } else {
                Log.d(LOG_TAG, "Failed to create user");
                Toast.makeText(RegistrationActivity.this, "User could not be created: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startIssueTracking() {
        Intent intent = new Intent(this, IssueTrackingActivity.class);
        startActivity(intent);
    }

    public void back(View view) {
        finish();
    }

}