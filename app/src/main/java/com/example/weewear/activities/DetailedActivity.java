package com.example.weewear.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.weewear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import model.NewProductModel;
import model.PopularProductModel;
import model.ShowAllModel;

public class DetailedActivity extends AppCompatActivity {
    ImageView detailedImg;
    TextView rating,name,description,price,quantity;
    Button addToCart,buyNow;
    ImageView addItems,removeItem;
    Toolbar toolbar;
    int totalQuantity = 1;
    int totalPrice =0;

    //New Product Model
    NewProductModel newProductModel=null;
    //Popular Product
    PopularProductModel popularProductModel = null;
    //Show All
    ShowAllModel showAllModel =null;
    FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed);
        toolbar = findViewById(R.id.detailed_tool);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        firestore=FirebaseFirestore.getInstance();
        final Object obj = getIntent().getSerializableExtra("detailed");
        if(obj instanceof NewProductModel){
            newProductModel = (NewProductModel) obj;
        }else if(obj instanceof PopularProductModel){
            popularProductModel = (PopularProductModel) obj;
        }
        else if(obj instanceof ShowAllModel){
            showAllModel = (ShowAllModel) obj;

        }
        detailedImg = findViewById(R.id.detailed_img);
        quantity = findViewById(R.id.quantity);
        name = findViewById(R.id.detailed_name);
        rating = findViewById(R.id.rating_text);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);
        addToCart = findViewById(R.id.add_to_cart_button);
        buyNow = findViewById(R.id.buy_now);
        addItems = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);

        //New Product
        if(newProductModel != null){
            Glide.with(getApplicationContext()).load(newProductModel.getImg_url()).into(detailedImg);
            name.setText(newProductModel.getName());
            rating.setText(newProductModel.getRating());
            description.setText(newProductModel.getDescription());
            price.setText(String.valueOf(newProductModel.getPrice()));
            name.setText(newProductModel.getName());
            name.setText(newProductModel.getName());
            totalPrice = newProductModel.getPrice()* totalQuantity;
        }


        //Popular product
        if(popularProductModel!= null){
            Glide.with(getApplicationContext()).load(popularProductModel.getImg_url()).into(detailedImg);
            name.setText(popularProductModel.getName());
            rating.setText(popularProductModel.getRating());
            description.setText(popularProductModel.getDescription());
            price.setText(String.valueOf(popularProductModel.getPrice()));
            name.setText(popularProductModel.getName());
            name.setText(popularProductModel.getName());
            totalPrice = popularProductModel.getPrice()* totalQuantity;
        }
        if(showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));
            name.setText(showAllModel.getName());
            name.setText(showAllModel.getName());
            totalPrice = showAllModel.getPrice()* totalQuantity;
        }
        //Buy Now
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedActivity.this,AddressActivity.class);
                if(newProductModel!=null){
                    intent.putExtra("item",newProductModel);
                }
                if(popularProductModel!=null){
                    intent.putExtra("item",popularProductModel);

                }
                if(showAllModel!=null){
                    intent.putExtra("item",showAllModel);

                }
                startActivity(intent);
            }
        });
        //Add to cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();

            }
        });
        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    if(newProductModel!=null){
                        totalPrice = newProductModel.getPrice()* totalQuantity;

                    }
                    if(popularProductModel!=null){
                        totalPrice = popularProductModel.getPrice()* totalQuantity;
                    }
                    if(showAllModel!=null){
                        totalPrice = showAllModel.getPrice()* totalQuantity;

                    }
                }

            }
        });
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity<10){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }

            }
        });

    }
    private void addToCart(){
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());
        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("productName",name.getText().toString());
        cartMap.put("productName",name.getText().toString());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);
        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this,"Added to Cart",Toast.LENGTH_SHORT).show();
                        finish();

                    }
                });


    }
}