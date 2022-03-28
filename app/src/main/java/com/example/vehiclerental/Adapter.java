package com.example.vehiclerental;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
    List<Vehicle> movie;
    Context context;


    public Adapter(Context context, List<Vehicle> movie){
        this.inflater = LayoutInflater.from(context);
        this.movie = movie;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vehicle mov = movie.get(position);
        holder.vehicleName.setText(movie.get(position).getName());
        Picasso.get().load(movie.get(position).getImage()).into(holder.vehicleImage);
    }

    @Override
    public int getItemCount() {
        return movie.size();
    }



    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView vehicleName;
        ImageView vehicleImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vehicleName = itemView.findViewById(R.id.txtTitle);
            vehicleImage = itemView.findViewById(R.id.image);

        }


    }
}

