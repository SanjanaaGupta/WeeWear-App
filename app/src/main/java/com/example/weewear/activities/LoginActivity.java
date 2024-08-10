package com.example.weewear.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weewear.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText editEmail,editPass;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        auth=FirebaseAuth.getInstance();
        editEmail = findViewById(R.id.editEmail);
        editPass= findViewById(R.id.editPass);
        getActionBar().hide();

    }
    public void signin(View view){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
    public void signup(View view){
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));

    }
}