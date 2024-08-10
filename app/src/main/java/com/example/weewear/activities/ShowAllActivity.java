package com.example.weewear.activities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weewear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;

import adapters.ShowAllAdapter;
import model.ShowAllModel;

public class ShowAllActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ShowAllAdapter showAllAdapter;
    List<ShowAllModel> showAllModelsList;
    Toolbar toolbar;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_all);
        toolbar = findViewById(R.id.show_all_tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String type = getIntent().getStringExtra("type");
        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.show_all_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        showAllModelsList = new ArrayList<>();
        //wrong hai  showAllAdapter = new ShowAllAdapter(this,showAllModelsList);
        recyclerView.setAdapter(showAllAdapter);
        if (type == null || type.isEmpty()) {
            firestore.collection("ShowAll")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                    ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                    showAllModelsList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
            if (type != null && type.equalsIgnoreCase("Full Set")) {
                firestore.collection("ShowAll").whereEqualTo("type", "Girls")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                        ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                        showAllModelsList.add(showAllModel);
                                        showAllAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        });
            }
            if (type != null && type.equalsIgnoreCase("Winter Stylish Jacket")) {
                firestore.collection("ShowAll").whereEqualTo("type", "Girls")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            }

    });
            }
            if (type != null && type.equalsIgnoreCase("Floral Skirt")) {
                firestore.collection("ShowAll").whereEqualTo("type", "Girls")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                        ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                        showAllModelsList.add(showAllModel);
                                        showAllAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        });
            }
            if (type != null && type.equalsIgnoreCase("White Top and pant")) {
                firestore.collection("ShowAll").whereEqualTo("type", "Girls")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                        ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                        showAllModelsList.add(showAllModel);
                                        showAllAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        });
            }
            if (type != null && type.equalsIgnoreCase("American Pastel Pista")) {
                firestore.collection("ShowAll").whereEqualTo("type", "Girls")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                        ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                        showAllModelsList.add(showAllModel);
                                        showAllAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        });
            }
            if (type != null && type.equalsIgnoreCase("Jeans & Top")) {
                firestore.collection("ShowAll").whereEqualTo("type", "Girls")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                        ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                        showAllModelsList.add(showAllModel);
                                        showAllAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        });
            }
    }
}