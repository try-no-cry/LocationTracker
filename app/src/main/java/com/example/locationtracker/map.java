package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class map extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap gMap;
    double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        latitude=getIntent().getDoubleExtra("latitude",0);
        longitude=getIntent().getDoubleExtra("longitude",0);

        SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap=googleMap;
        float zoomLevel = (float) 16.0;
        LatLng coords=new LatLng(latitude,longitude);
        gMap.addMarker(new MarkerOptions().position(coords).title("Current location of vehicle.."));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords,zoomLevel));
    }
}
