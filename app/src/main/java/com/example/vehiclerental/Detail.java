package com.example.vehiclerental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yinglan.shadowimageview.ShadowImageView;

import java.util.ArrayList;

public class Detail extends AppCompatActivity {
    ImageView img;
    TextView txtName , txtDescription, txtType;
    RatingBar txtRating;
    String title, description, imageUrl, rating, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        img = findViewById(R.id.imageView);
        txtName = findViewById(R.id.txtName);
        txtRating = findViewById(R.id.rating_star);
        txtType = findViewById(R.id.txtType);
        txtDescription = findViewById(R.id.txtDesc);

        ArrayList<String> vehicles = getIntent().getExtras().getStringArrayList("vehicles_Rental");
        title = vehicles.get(0);
        description = vehicles.get(2);
        imageUrl = vehicles.get(1);
        rating = vehicles.get(3);
        type = vehicles.get(4);
        Picasso.get().load(imageUrl).into(img);
        txtName.setText(title);
        txtRating.setRating(Float.parseFloat(rating));
        txtDescription.setText("Detail: " + description);
        txtType.setText("Vehicle Type: " + type);

    }
}