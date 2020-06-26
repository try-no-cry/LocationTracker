package com.example.locationtracker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity {
    Button button,showInMap;
    double latitude=-1;
    double longitude=-1;

    private FusedLocationProviderClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);
        button= findViewById(R.id.getLocation);
        showInMap=findViewById(R.id.showMap);

        showInMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(latitude==-1 || longitude==-1)
                {
                    Toast.makeText(MainActivity.this,"Failed to get Coordinates..",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Intent intent=new Intent(MainActivity.this,map.class);
                    intent.putExtra("latitude",latitude);
                    intent.putExtra("longitude",longitude);

                    startActivity(intent);
                }





            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    return;
                }
                client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener() {


                    @Override
                    public void onSuccess(Object o) {

                        setLatLong(o);


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

    private void setLatLong(Object o) {
        TextView textView;

        if(o !=null){
            textView = findViewById(R.id.location);

            String[] lat=o.toString().split(" ");
            if(lat.length!=0){
                String[] a=lat[1].split(",");
                String ltd=a[0];
                String longtude=a[1];

                latitude= Double.parseDouble(ltd);
                longitude= Double.parseDouble(longtude);

                textView.setText("\n"+latitude+", "+longitude);

            }
        }
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }
}
