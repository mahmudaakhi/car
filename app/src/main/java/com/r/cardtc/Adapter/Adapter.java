package com.r.cardtc.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.r.cardtc.DetailsActivity;
import com.r.cardtc.MainActivity;
import com.r.cardtc.Model.CarList;
import com.r.cardtc.Model.Data;
import com.r.cardtc.R;

import java.util.List;

public class Adapter  extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference,vDatabaseReference;
    private StorageReference mStorageRef;
    private FirebaseStorage mStorage;

    private ValueEventListener valueEventListener;

    Context context;
    List<CarList> carLists;

    public Adapter(Context context, List<CarList> carLists) {
        this.context = context;
        this.carLists = carLists;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ro_data,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final CarList carList = carLists.get(position);

        holder.textViewName.setText(carList.getCarName());
        Log.e("url",carList.getCarImage());
        Glide.with(context).load(carList.getCarImage())
                .fitCenter()
                .centerCrop()
                .placeholder(R.drawable.car)
                .into(holder.movieImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id",carList.getmKey());
                intent.putExtra("title",carList.getCarName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return carLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView movieImage;
        TextView textViewName;
        TextView textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.imageview);
            textViewName = itemView.findViewById(R.id.textName);

            //Firebase Instance ..........

            mStorage = FirebaseStorage.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();
//            databaseReference = FirebaseDatabase.getInstance().getReference().child("Car DTC").child("America").child("Car");
//            mStorageRef = FirebaseStorage.getInstance().getReference().child("Car DTC").child("Car");

        }
    }
}
