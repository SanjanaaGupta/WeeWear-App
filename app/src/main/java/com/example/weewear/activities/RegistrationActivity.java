package com.example.weewear.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.weewear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import androidx.appcompat.widget.Toolbar;

public class RegistrationActivity extends AppCompatActivity {
    EditText editName, editEmail, editPass;
    private FirebaseAuth auth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        // Ensure you are using getSupportActionBar()
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            finish();
        }

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPass = findViewById(R.id.editPass);

        sharedPreferences = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);

        if (isFirstTime) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime", false);
            editor.apply();
            Intent intent = new Intent(RegistrationActivity.this, OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void signup(View view) {
        String userName = editName.getText().toString();
        String userEmail = editEmail.getText().toString();
        String userPass = editPass.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Enter Email Address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPass)) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPass.length() < 6) {
            Toast.makeText(this, "Password Too short, Enter minimum 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                            finish(); // Close the current activity after successful registration
                        } else {
                            String errorMessage = "Registration failed";
                            if (task.getException() != null) {
                                errorMessage += ": " + task.getException().getMessage();  // Add the actual error message
                            }
                            Toast.makeText(RegistrationActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signin(View view) {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
    }

    public static class SliderAdapter extends PagerAdapter {
        Context context;
        LayoutInflater layoutInflater;

        public SliderAdapter(Context context) {
            this.context = context;
        }

        int[] imagesArray = {
                R.drawable.onboarding1,
                R.drawable.onboarding2,
                R.drawable.onboarding3
        };

        int[] headingArray = {
                R.string.first_slide,
                R.string.second_slide,
                R.string.third_slide
        };

        int[] descriptionArray = {
                R.string.description,
                R.string.description,
                R.string.description
        };

        @Override
        public int getCount() {
            return headingArray.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == (ConstraintLayout) object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.sliding_layout, container, false);
            ImageView imageView = view.findViewById(R.id.slider_img);
            TextView heading = view.findViewById(R.id.heading);
            TextView description = view.findViewById(R.id.description);
            imageView.setImageResource(imagesArray[position]);
            heading.setText(headingArray[position]);
            description.setText(descriptionArray[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ConstraintLayout) object);
        }
    }
}
