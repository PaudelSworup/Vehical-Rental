package com.example.vehiclerental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yinglan.shadowimageview.ShadowImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class Detail extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;
    String [] items = {"Pokhara", "Mustang" ,"Rara" ,"Nagarkot", "Namche Bazar" ,
            "Bardiya National Park", "Sarangkot" ," Bandipur",
            "Koshi tapu", "Chandragiri", "Janaki Temple","Bhote Koshi", "Illam", "Khumbu Valley", "Nuwakot"};
    ImageView img;
    AutoCompleteTextView autoCompleteTextView;
    TextView txtName , txtDescription, txtType, txtPhoneNum, titleDetail;
    RatingBar txtRating;
    String title, description, imageUrl, rating, type, phone;
    Button addToList;
    PlacesClient placesClient;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        img = findViewById(R.id.imageView);
        txtName = findViewById(R.id.txtName);
        addToList = findViewById(R.id.addToList);
        autoCompleteTextView = findViewById(R.id.destination);
        titleDetail = findViewById(R.id.detailTitle);
        txtRating = findViewById(R.id.rating_star);
        txtPhoneNum = findViewById(R.id.txtPhoneNum);
        txtType = findViewById(R.id.txtType);
        txtDescription = findViewById(R.id.txtDesc);

        ArrayList<String> vehicles = getIntent().getExtras().getStringArrayList("vehicles_Rental");
        title = vehicles.get(0);
        description = vehicles.get(2);
        imageUrl = vehicles.get(1);
        rating = vehicles.get(3);
        type = vehicles.get(4);
        phone = vehicles.get(5);
        Picasso.get().load(imageUrl).into(img);
        txtName.setText(title);
        txtRating.setRating(Float.parseFloat(rating));
        titleDetail.setText("Detail");
        txtDescription.setText(description);
        txtType.setText("Vehicle Type : " + type);
        txtPhoneNum.setText("Contact Number : " + phone);


        addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Wait for admin Approval", Toast.LENGTH_SHORT).show();
            }
        });

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,items);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),0);
            }
        });








    }
}