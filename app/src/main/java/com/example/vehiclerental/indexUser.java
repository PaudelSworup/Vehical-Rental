package com.example.vehiclerental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class indexUser extends AppCompatActivity {

    private TextView user;
    private Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indexuser);
        user = findViewById(R.id.userName);
        logout = findViewById(R.id.logout);

        NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        manager.cancelAll();

        if(getIntent().hasExtra("yes")){
            user.setText("Your request is accepted");
            user.setTextColor(Color.GREEN);
        }else  if(getIntent().hasExtra("no")){
            user.setText("you reject the request");

            user.setTextColor(Color.RED);
        }



    }
}