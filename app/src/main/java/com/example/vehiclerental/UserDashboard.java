package com.example.vehiclerental;

import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
//import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

//import java.util.Objects;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth firebaseAuth;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        firebaseAuth = FirebaseAuth.getInstance();

        navigationView = findViewById(R.id.navigationview);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//         item.setChecked(true);
        drawerLayout.closeDrawers();
        int id = item.getItemId();
        if(id == R.id.home_menu){
            Toast.makeText(UserDashboard.this, "home is clicked",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),UserPage.class));
        }

        if(id == R.id.dashboard_menu){
            Toast.makeText(UserDashboard.this, "Dashboard is clicked",Toast.LENGTH_SHORT).show();
        }

        if(id == R.id.list_menu){
            Toast.makeText(UserDashboard.this, "home is clicked",Toast.LENGTH_SHORT).show();

        }

        if(id == R.id.logout_menu){
            Toast.makeText(UserDashboard.this, "logout is clicked",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(),Login_Activity.class));
        }
        return true;
    }
}