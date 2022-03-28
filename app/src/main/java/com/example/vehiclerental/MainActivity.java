package com.example.vehiclerental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
Button Register, Login;
Window win;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Register = findViewById(R.id.register);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Login = findViewById(R.id.login);
        if(Build.VERSION.SDK_INT>=21){
            win = this.getWindow();
            win.setStatusBarColor(this.getResources().getColor(R.color.black));
        }
        
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("success", "CLicked: ");
                Intent in = new Intent(MainActivity.this, Register_Activity.class);
                startActivity(in);

            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, Login_Activity.class);
                startActivity(in);

            }
        });
    }
}