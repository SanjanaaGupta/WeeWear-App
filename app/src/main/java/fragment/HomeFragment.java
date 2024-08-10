package fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.weewear.R;
import com.example.weewear.activities.ShowAllActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import adapters.CategoryAdapter;
import adapters.NewProductAdapter;
import adapters.PopularProductAdapter;
import adapters.SliderAdapter;
import model.CategoryModel;
import model.NewProductModel;
import model.PopularProductModel;


public class HomeFragment extends Fragment {
    TextView catShowAll,popularShowAll,newProductShowAll;
    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    RecyclerView catRecyclerview,newProductRecyclerview,popularRecyclerview;
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;
    // New product Recycler
    NewProductAdapter newProductAdapter;
    List<NewProductModel> newProductModelList;
    private static final String TAG = "MyActivity";

    //Popular Product
    PopularProductAdapter popularProductAdapter;

    List<PopularProductModel>popularProductModelList;
    //fireStore
    FirebaseFirestore db;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        catRecyclerview = root.findViewById(R.id.rec_category);
        progressDialog=new ProgressDialog(getActivity());
        newProductRecyclerview = root.findViewById(R.id.new_product_rec);
        popularRecyclerview = root.findViewById(R.id.popular_rec);
        catShowAll = root.findViewById(R.id.category_see_all);
        popularShowAll = root.findViewById(R.id.popular_see_all);
        newProductShowAll = root.findViewById(R.id.newProducts_see_all);
        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ShowAllActivity.class);
                startActivity(intent);
            }
        });
       newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ShowAllActivity.class);
                startActivity(intent);
            }
        });
        //Firebase
        db = FirebaseFirestore.getInstance();
        linearLayout = root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);

        //Image Slider
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.rainclothes, "Discount on Rain Clothes", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.winterclothes, "Discount on Winter Clothes", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.summerclothes, "Discount on Summer Clothes", ScaleTypes.CENTER_CROP));

        progressDialog.setTitle("Welcome to WeeWear App");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        imageSlider.setImageList(slideModels);
        //SliderAdapter sliderAdapter = new SliderAdapter(getContext(), slideModels);
        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryModelList);
        catRecyclerview.setAdapter(categoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();
                                linearLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }

                        } else {
                            Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //New Product
        newProductRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductModelList = new ArrayList<>();
        newProductAdapter = new NewProductAdapter(getContext(),newProductModelList);
        newProductRecyclerview.setAdapter(newProductAdapter);


            db.collection("NewProduct")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    NewProductModel newProductModel = document.toObject(NewProductModel.class);
                                    newProductModelList.add(newProductModel);
                                    newProductAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            //Popular Product
        popularRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));
        popularProductModelList = new ArrayList<>();
        popularProductAdapter = new PopularProductAdapter(getContext(),popularProductModelList);
        popularRecyclerview.setAdapter(popularProductAdapter);
        db.collection("All Product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularProductModel popularProductModel = document.toObject(PopularProductModel.class);
                                popularProductModelList.add(popularProductModel);
                                popularProductAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    return root;
}
}
