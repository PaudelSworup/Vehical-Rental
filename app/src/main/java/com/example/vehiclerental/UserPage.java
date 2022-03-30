package com.example.vehiclerental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserPage extends AppCompatActivity {
//    http://image.tmdb.org/t/p/w185
    RecyclerView recyclerView;
    List<Vehicle> vehicles;
    private String JSON_URL = "http://192.168.1.70/api/get.php";
//    private String JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=caa4226c251747a5c3bf3d6bc23b2d18";

    Adapter adapter;
    LinearLayoutManager llm ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        recyclerView = findViewById(R.id.vehicleList);
        vehicles = new ArrayList<>();
        adapter = new Adapter(getApplicationContext(),vehicles);
        llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        extractData();
    }

    public void extractData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                Log.d("Success",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Success object",jsonObject.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    if(jsonArray.length()>0){
                        for(int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObject1  = jsonArray.getJSONObject(i);
                            Log.d("value of i", "i is" + i);
                            Vehicle vec = new Vehicle();
                            vec.setName(jsonObject1.getString("name").toString());
                            vec.setImage("http://192.168.1.70/api/images/" + jsonObject1.getString("image"));
                            vehicles.add(vec);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
            }
        });
        queue.add(request);


    }
}



