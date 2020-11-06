package com.r.cardtc;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.r.cardtc.Adapter.Adapter;
import com.r.cardtc.Model.CarList;

import java.util.ArrayList;
import java.util.List;

public class IndiaFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference,vDatabaseReference;
    private ValueEventListener valueEventListener;

    RecyclerView recyclerView;
    Adapter adapter;
    List<CarList> carLists = new ArrayList<>();

    public IndiaFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_india, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Car DTC").child("India").child("Car");
        //------------------------------------------------------------------//
        //RecyclerView & Adapter----------------------------------
        adapter = new Adapter(getContext(),carLists);

        recyclerView = view.findViewById(R.id.indianRecycelrView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Car List");
        progressDialog.setMessage("Please wait............");
        progressDialog.show();

        //Receive Data From DataBase....................................//
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                carLists.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    CarList carList = dataSnapshot1.getValue(CarList.class);
                    carList.setmKey(dataSnapshot1.getKey());
                    carLists.add(carList);
                    progressDialog.dismiss();
                }

                //List Reverss Function..................
//                Collections.reverse(carLists);
                //-----------------------------------//

                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getContext(), "Error "+databaseError.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}