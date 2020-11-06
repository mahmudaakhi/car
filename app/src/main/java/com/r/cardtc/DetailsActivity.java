package com.r.cardtc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class DetailsActivity extends AppCompatActivity {
    private AdView mAdView;
    ImageButton btnShare,back;
    Button findCode;
    EditText code ;
    ConstraintLayout resultLayout;
    TextView defination,cause;
    String c ;
    // Firebase Database........................//
    private FirebaseAuth firebaseAuth;
    private DatabaseReference foodDatabase,foodList;
    //-----------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        findCode = findViewById(R.id.button);
        code = findViewById(R.id.Code);
        resultLayout = findViewById(R.id.constraintLayoutResult);
        resultLayout.setVisibility(View.GONE);
        defination = findViewById(R.id.defination);
        cause = findViewById(R.id.causes);
        btnShare =  findViewById(R.id.btnShare);
        back =  findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(DetailsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareSubject = "";
                String shareBody = "";
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent,"Share via"));
            }
        });

        //receive data----------
        Intent i = getIntent();
        final String selectedKey = i.getStringExtra("id");
        String title = i.getStringExtra("title");

        //Firebase Instance.........................................//
        firebaseAuth = FirebaseAuth.getInstance();
        foodDatabase = FirebaseDatabase.getInstance().getReference().child("Car DTC").child("Car Details").child(selectedKey);

        findCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultLayout.setVisibility(View.VISIBLE);
                c = code.getText().toString().trim();
                if (c.isEmpty())
                {
                    defination.setText("P0000 SAE Reserved \n-Usage not allowed except as padding in DTC");
                }
                else
                {
                    Query query = foodDatabase.child("Details").orderByChild("code").equalTo(c);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                            {
                                String Codec = dataSnapshot1.child("code").getValue(String.class);
                                String Details = dataSnapshot1.child("details").getValue(String.class);
                                String Cause = dataSnapshot1.child("cause").getValue(String.class);
                                Log.e("Code",Codec);
                                Log.e("Details",Details);
                                defination.setText(Codec+" "+Details);
//                                cause.setText(Cause);
//                    addFood.setKey(dataSnapshot1.getKey());
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(DetailsActivity.this , "Error "+databaseError.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    });

                }



                closeKeyBord();

            }
        });
    }

    private void closeKeyBord() {

        View view = this.getCurrentFocus();

        if (view!=null)
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }


    }

