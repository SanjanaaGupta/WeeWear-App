package com.example.weewear.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.weewear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {
    EditText name,address,city,postalCode,phoneNumber;
    Toolbar toolbar;
   private Button addAddressBtn;
   FirebaseAuth auth;
   FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_address);
        toolbar=findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = findViewById(R.id.ad_name);
        address = findViewById(R.id.ad_address);
        city = findViewById(R.id.ad_city);
        phoneNumber = findViewById(R.id.ad_phone);
        postalCode = findViewById(R.id.ad_code);
        addAddressBtn = findViewById(R.id.ad_add_address);
        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = name.getText().toString();
                String userCity = name.getText().toString();
                String userAddress = name.getText().toString();
                String userCode = name.getText().toString();
                String userNumber = name.getText().toString();
                String final_address = "";
                if(!userName.isEmpty()){
                    final_address+=userName;
                }
                if(!userCity.isEmpty()){
                    final_address+=userCity;
                }
                if(!userAddress.isEmpty()){
                    final_address+=userAddress;
                }
                if(userCode.isEmpty()){
                    final_address+=userCode;
                }
                if(userNumber.isEmpty()){
                    final_address+=userNumber;
                }
                if(!userName.isEmpty() && !userCity.isEmpty() &&  !userAddress.isEmpty() && !userCode.isEmpty() && !userNumber.isEmpty()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("userAddress", final_address);
                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AddAddressActivity.this, "Address Added", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AddAddressActivity.this,DetailedActivity.class));
                                        finish();

                                    }
                                }
                            });


                }else{
                    Toast.makeText(AddAddressActivity.this,"Kindly Fill All Field",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

}