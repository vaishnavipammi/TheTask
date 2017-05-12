package com.example.thetask;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private static final LatLng PERTH = new LatLng(-31.952854, 115.857342);
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);
    private Marker mPerth;
    private Marker mSydney;
    private Marker mine;
    private LatLng mylatlang;

    ArrayList<String> myList;
    private Marker mBrisbane;
    Integer qwerty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        myList = (ArrayList<String>) getIntent().getSerializableExtra("locationlist");

        qwerty = myList.size();
        Log.e(TAG, "i am happy and qwerty is  " + qwerty);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Float f = new Float("20.75f");
        float x = f.parseFloat(myList.get(1));
        float y = f.parseFloat(myList.get(0));

        int abcd;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(x, y);
        mMap.addMarker(new MarkerOptions().position(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        for (abcd = 2; abcd <= qwerty - 2; abcd = abcd + 2) {
            x = f.parseFloat(myList.get(abcd + 1));
            y = f.parseFloat(myList.get(abcd));
            Log.e(TAG, "i am happy and x is  " + x);
            Log.e(TAG, "i am happy and y is  " + y);


            mylatlang = new LatLng(x, y);
            mine = mMap.addMarker(new MarkerOptions().position(mylatlang));
            mine.setTag(0);
        }
     /*   mPerth = mMap.addMarker(new MarkerOptions()
                .position(PERTH)
                .title("Perth"));
        mPerth.setTag(0);

        mSydney = mMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .title("Sydney"));
        mSydney.setTag(0);

        mBrisbane = mMap.addMarker(new MarkerOptions()
                .position(BRISBANE)
                .title("Brisbane"));
        mBrisbane.setTag(0);*/
    }
}
