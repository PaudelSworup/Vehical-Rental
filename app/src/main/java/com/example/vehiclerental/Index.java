package com.example.vehiclerental;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Index extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView img;
    FirebaseFirestore dbRoot;
    DocumentReference documentReference;
    FloatingActionButton camera;
    private FirebaseAuth firebaseAuth;
    EditText vehicleName , carDetail, vehicleRating, vehicleType, contactNumber;
    ActivityResultLauncher<String> imgContent;
    Uri imgUri;
    Button Submit,logout;
    Bitmap bitmap;
    String imageEncode;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        img = findViewById(R.id.firebaseImg);
        camera = findViewById(R.id.floatingActionButton);
        vehicleName = findViewById(R.id.vehicleName);
        vehicleRating = findViewById(R.id.vehicleRating);
        vehicleType = findViewById(R.id.vehicleType);
        contactNumber = findViewById(R.id.contact);
        carDetail = findViewById(R.id.detail);
        Submit = findViewById(R.id.submit);
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_SHORT).show();



        navigationView = findViewById(R.id.navigationview);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);



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


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//         item.setChecked(true);
        drawerLayout.closeDrawers();
        int id = item.getItemId();
        if(id == R.id.home_menu){
            Toast.makeText(Index.this, "home is clicked",Toast.LENGTH_SHORT).show();
        }

        if(id == R.id.dashboard_menu){
            Toast.makeText(Index.this, "dash is clicked",Toast.LENGTH_SHORT).show();
        }

        if(id == R.id.list_menu){
            Toast.makeText(Index.this, "list is clicked",Toast.LENGTH_SHORT).show();

        }

        if(id == R.id.logout_menu){
            Toast.makeText(Index.this, "logout is clicked",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(),Login_Activity.class));
        }
        return true;
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
        String vehicle_rating = vehicleRating.getText().toString();
        String vehicle_type = vehicleType.getText().toString();
        String contact_vehicle = contactNumber.getText().toString();
        String contact_regex = "^[0-9]+$";



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

        if(vehicle_type.isEmpty()){
            vehicleType.setError("please enter the seat type");
            return;
        }

        if(vehicle_detail.isEmpty()){
            carDetail.setError("Please enter some detail");
            return;
        }

        if(vehicle_rating.isEmpty()){
            vehicleRating.setError("please enter rating");
            return;
        }

        if(vehicle_detail.length() < 6){
            carDetail.setError("Please enter detail related to vehicle");
            return;
        }

        if(contact_vehicle.isEmpty()){
            contactNumber.setError("please enter a contact Number");
            return;
        }

        if(!contact_vehicle.matches(contact_regex)){
            contactNumber.setError("please enter only 10 digits number");
            return;
        }




        String url = "http://192.168.1.69/api/post.php";
//         String url = "http://192.168.88.243/api/api.php";
//        String url = "http://10.0.2.2/api/api.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Index.this,response.trim(),Toast.LENGTH_LONG).show();
                vehicleName.setText("");
                vehicleRating.setText("");
                vehicleType.setText("");
                contactNumber.setText("");
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
                params.put("rating",vehicle_rating);
                params.put("phone", contact_vehicle);
                params.put("type",vehicle_type);
                params.put("detail",vehicle_detail);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Index.this);
        requestQueue.add(request);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_baseline_car_rental_24)
                .setLargeIcon(bitmap)
                .setTicker("Hearty365")
                //     .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle(vehicle_name)
                .setContentText(vehicle_detail)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap))
                .setContentInfo("Info");

        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }
}
