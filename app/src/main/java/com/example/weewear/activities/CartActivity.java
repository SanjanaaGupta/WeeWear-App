package com.example.weewear.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.weewear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import adapters.MyCartAdapter;
import model.MyCartModel;
import model.ShowAllModel;

public class CartActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<MyCartModel>cartModelList;
    MyCartAdapter cartAdapter;
    TextView overAllAmount;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
      auth = FirebaseAuth.getInstance();
      firestore = FirebaseFirestore.getInstance();
      toolbar = findViewById(R.id.my_cart_toolbar);
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
      });
      //get data from my cart adapter
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver,new IntentFilter("MyTotalAmount"));
        overAllAmount =findViewById(R.id.textView3);
        recyclerView = findViewById(R.id.cart_rec);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      cartModelList = new ArrayList<>();
      cartAdapter = new MyCartAdapter(this,cartModelList);
      recyclerView.setAdapter(cartAdapter);
      firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
              .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<QuerySnapshot> task) {
                      if (task.isSuccessful()) {
                          for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                              MyCartModel myCartModel = doc.toObject(MyCartModel.class);
                              cartModelList.add(myCartModel);
                              cartAdapter.notifyDataSetChanged();
                          }
                      }
                  }
              });
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill = intent.getIntExtra("totalAmount",0);
            overAllAmount.setText("Total Amount:" +totalBill+"$");

        }
    };


}