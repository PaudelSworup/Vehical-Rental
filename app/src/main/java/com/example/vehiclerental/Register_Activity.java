package com.example.vehiclerental;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register_Activity extends AppCompatActivity {
ImageView img;
FloatingActionButton camera;
Uri imgUri;
ProgressDialog pd;
EditText name,email,phone,password;
TextView txtLogin;
ActivityResultLauncher<String> imgContent;
Button Register;
 StorageReference ref;
 FirebaseFirestore fstore;
 FirebaseAuth fAuth;
 String userID;
 String newUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create an Account");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.register) + "</font>"));

       fAuth =FirebaseAuth.getInstance();
       fstore = FirebaseFirestore.getInstance();



        img = findViewById(R.id.firebaseImg);
        camera = findViewById(R.id.floatingActionButton);
        name = findViewById(R.id.et_name);
        txtLogin = findViewById(R.id.textLogin);
        email = findViewById(R.id.et_email);
        phone = findViewById(R.id.et_phone);
        password = findViewById(R.id.et_password);
        Register = findViewById(R.id.register1);

        imgContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result != null){
                    img.setImageURI(result);
                    imgUri = result;
                }
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

       Register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               uploadData();
           }
       });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(in);
            }
        });

    }

    private void uploadData() {

        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPhone = phone.getText().toString();
        String userPassword = password.getText().toString();
        String email_pattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        boolean check_email = userEmail.matches(email_pattern);


        if(imgUri == null){
            Context context = getApplicationContext();
            CharSequence text = "Please select User image ";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }


        if(userName.isEmpty() || userName.length() < 3 ){
            name.setError("Name is Required and must have atleast 3 character");
            return;
        }else name.setError(null);

        if(userEmail.isEmpty()){
            email.setError("Email is Required");
            return;
        }else email.setError(null);

        if(!check_email){
          email.setError("Please enter Valid Email Address");
            return;
        }else email.setError(null);

        if(userPhone.isEmpty()){
            phone.setError("Number is Required");
            return;
        }else phone.setError(null);

        if(phone.length() != 10){
            phone.setError("Phone Number must be of 10 digits");
            return;
        }else phone.setError(null);

        if(userPassword.isEmpty() ){
            password.setError("Please Enter your Password");
            return;
        }else password.setError(null);

        pd = new ProgressDialog(this);
        pd.setTitle("Uploading Data...");
        pd.show();
        ref = FirebaseStorage.getInstance().getReference().child(userName);
        ref.putFile(imgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Objects.requireNonNull(task.getResult()).getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG,"uri is " + uri);
                        newUri = uri.toString();
                        Log.d(TAG, "onSuccess: " + newUri);
                        if (pd.isShowing()){
                            pd.dismiss();
                        }

                        fAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener((task) ->{
                            if(task.isSuccessful()){
                                Toast.makeText(Register_Activity.this, "User Created",Toast.LENGTH_SHORT).show();
                                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                DocumentReference documentReference = fstore.collection("users").document(userID);
                                Map<String,Object> user = new HashMap<>();
                                user.put("fName",userName);
                                user.put("email",userEmail);
                                user.put("phone",userPhone);
                                user.put("password",userPassword);
                                user.put("imageLink",newUri);
                                name.setText("");
                                email.setText("");
                                phone.setText("");
                                password.setText("");
                                img.setImageBitmap(null);
                                img.destroyDrawingCache();
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, " OnSuccess User is created for" + userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "User is failed to create");
                                    }
                                });
                                goNext();
                            }
                        });

                    }
                });
            }
        });

    }

    private void selectImage() {
        imgContent.launch("image/*");
    }

    private void goNext(){
        Intent in = new Intent(getApplicationContext(), Login_Activity.class);
        startActivity(in);
    }
}