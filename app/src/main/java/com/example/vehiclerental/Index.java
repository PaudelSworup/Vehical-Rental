package com.example.vehiclerental;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Index extends AppCompatActivity {
    ImageView img;
    FloatingActionButton camera;
    private FirebaseAuth firebaseAuth;
    EditText vehicleName , carDetail;
    ActivityResultLauncher<String> imgContent;
    Uri imgUri;
    Button Submit,logout;
    Bitmap bitmap;
    String imageEncode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        img = findViewById(R.id.firebaseImg);
        camera = findViewById(R.id.floatingActionButton);
        vehicleName = findViewById(R.id.vehicleName);
        carDetail = findViewById(R.id.detail);
        Submit = findViewById(R.id.submit);
        logout = findViewById(R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),Login_Activity.class));
            }
        });


        imgContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result != null){
                    img.setImageURI(result);
                    imgUri = result;
                    encodeImage(imgUri);

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

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();
            }
        });

    }
    private void selectImage() {
        imgContent.launch("image/*");
    }
    private void encodeImage(Uri uri){
        try {
            ContentResolver cr = getBaseContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] byteofimages =  baos.toByteArray();
            imageEncode = android.util.Base64.encodeToString(byteofimages, Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void uploadData(){
        String vehicle_name = vehicleName.getText().toString();
        String vehicle_detail = carDetail.getText().toString();


        if(imgUri == null){
            Context context = getApplicationContext();
            CharSequence text = "Please select User image ";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        if(vehicle_name.isEmpty()){
            vehicleName.setError("Name is Required");
            return;
        }

        if(vehicle_name.length() < 5){
            vehicleName.setError("Please enter correct Vehicle name ");
            return;
        }

        if(vehicle_detail.isEmpty()){
            carDetail.setError("Please enter some detail");
            return;
        }

        if(vehicle_detail.length() < 6){
            carDetail.setError("Please enter detail related to vehicle");
            return;
        }


        String url = "http://192.168.1.70/api/post.php";
//         String url = "http://192.168.88.243/api/api.php";
//        String url = "http://10.0.2.2/api/api.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Index.this,response.trim(),Toast.LENGTH_LONG).show();
                vehicleName.setText("");
                carDetail.setText("");
                img.setImageURI(null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Index.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{

                Map<String ,String> params = new HashMap<>();
                params.put("name", vehicle_name );
                params.put("pic" , imageEncode);
                params.put("detail",vehicle_detail);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Index.this);
        requestQueue.add(request);
    }
}
