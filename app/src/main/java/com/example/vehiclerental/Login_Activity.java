package com.example.vehiclerental;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Login_Activity extends AppCompatActivity {
    TextView textView;
    Button button;
    EditText email,password;
    FirebaseAuth fAuth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Objects.requireNonNull(getSupportActionBar()).setTitle("");
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
//        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.login) + "</font>"));
        Objects.requireNonNull(getSupportActionBar()).hide();

        textView = findViewById(R.id.textView3);
        button = findViewById(R.id.Login1);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);

        fAuth =FirebaseAuth.getInstance();


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), Register_Activity.class);
                startActivity(in);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthenticationLogin();
            }
        });
    }


    private void AuthenticationLogin(){
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String email_pattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        boolean check_email = userEmail.matches(email_pattern);


        if(userEmail.isEmpty()){
            email.setError("Email is Required");
            return;
        }else email.setError(null);

        if(!check_email){
            email.setError("Please enter Valid Email Address");
            return;
        }else email.setError(null);

        if(userPassword.isEmpty() ){
            password.setError("Please Enter your Password");
            return;
        }else password.setError(null);


        fAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener((task) ->{
            pd = new ProgressDialog(this);
            pd.setTitle("Logging in...");
            pd.show();
            if(task.isSuccessful()){
                if(userEmail.equals("subhadra@mail.com")){
                    Intent intent = new Intent(getApplicationContext(), Index.class);
                    startActivity(intent);
                }else {
                    Intent in = new Intent(getApplicationContext(), UserPage.class);
                    startActivity(in);
                }
                Toast.makeText(Login_Activity.this, "Login Successful",Toast.LENGTH_SHORT).show();
            }else  Toast.makeText(Login_Activity.this, "Email or password is incorrect",Toast.LENGTH_SHORT).show();

        });







    }

}

