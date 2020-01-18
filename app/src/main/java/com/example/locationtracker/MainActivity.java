package com.example.locationtracker;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);
        Button button = findViewById(R.id.getLocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    return;
                }
                client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener() {

                    TextView textView;
                    @Override
                    public void onSuccess(Object o) {
                        if(o !=null){
                            textView = findViewById(R.id.location);

                            String[] lat=o.toString().split(" ");
                            if(lat.length!=0){
                                String[] a=lat[1].split(",");
                                String latitude=a[0];
                                String longitude=a[1];
                                textView.setText("\n"+latitude+", "+longitude);

                            }
                        }


                    }

//                    @Override
//                    public void onSuccess(Location location) {
//                        if (location != null) {
//                            TextView textView = findViewById(R.id.location);
//                            textView.setText(location.toString());
//                        }
//                    }


                });
            }
        });
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }
}
