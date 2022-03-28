package com.example.vehiclerental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class indexUser extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    //    private FirebaseFirestore firestore;
    private TextView user;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indexuser);
        user = findViewById(R.id.userName);
        logout = findViewById(R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),Login_Activity.class));
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            user.setText(firebaseUser.getEmail());
        }else{
            startActivity(new Intent(this,Login_Activity.class));
            finish();
        }
    }

//    public void Logout(View view){
//        firebaseAuth.signOut();
//        startActivity(new Intent(getApplicationContext(),Login_Activity.class));
//    }
}