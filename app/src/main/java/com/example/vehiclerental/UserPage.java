package com.example.vehiclerental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserPage extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Vehicle> vehicles;
    private String JSON_URL = "http://192.168.1.70/api/get.php";
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        recyclerView = findViewById(R.id.vehicleList);
        vehicles = new ArrayList<>();
        // yaha setAdapter gara with empty data, yaha nabhayera error aako
        extractData();
    }

    public  void extractData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", "onSuccessResponse:" + response.toString());
                try {
                    JSONArray vec = response.getJSONArray("results");
                    for (int i=0; i<vec.length(); i++){
                        try {
                            JSONObject vehicleObject = (JSONObject) vec.get(i);
                            Vehicle vech = new Vehicle();
                            vech.setName(vehicleObject.getString("name").toString());
                            vech.setImage("http://192.168.1.70/api/images/" + vehicleObject.getString("image"));
                            vehicles.add(vech);

                         }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // yaha chai adapter lai data change bhayo bhanera notify matra gare pugxa
                LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llm);
                adapter = new Adapter(getApplicationContext(),vehicles);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse:" + error.getMessage());
            }
        });
        queue.add(jsonObjectRequest);
    }
}

