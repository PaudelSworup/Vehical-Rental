package com.example.vehiclerental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Detail extends AppCompatActivity {
    ImageView img;
    TextView txtName , txtDescription;
    String title, description, imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        img = findViewById(R.id.imageView);
        txtName = findViewById(R.id.txtName);
        txtDescription = findViewById(R.id.txtDesc);

        ArrayList<String> movies = getIntent().getExtras().getStringArrayList("tmdbMovies");
        title = movies.get(0);
        description = movies.get(1);
        imageUrl = movies.get(2);
        Picasso.get().load(imageUrl).into(img);
        txtName.setText(title);
        txtDescription.setText(description);

    }
}