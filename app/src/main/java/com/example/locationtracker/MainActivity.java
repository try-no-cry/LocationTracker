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

import com.example.locationtracker.Retrofit.IRetrofit;
import com.example.locationtracker.Retrofit.RetrofitApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.JsonObject;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
 

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity {
    Button startSendingCoords,stopSendingCoords,showInMap;
    double latitude=-1;
    double longitude=-1;
    Timer timer;

    private FusedLocationProviderClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);
        startSendingCoords= findViewById(R.id.startSendingCoords);
        stopSendingCoords=findViewById(R.id.stopSendingCoords);
        showInMap=findViewById(R.id.showMap);


       stopSendingCoords.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view)
           {

               timer.cancel();

           }
       });

        
        
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

        startSendingCoords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    return;
                }
                timer=new Timer();
                TimerTask minuteTask=new TimerTask() {
                    @Override
                    public void run() {
                        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener() {

                            @Override
                            public void onSuccess(Object o) { setLatLong(o);  }

                        });

                    }
                };
                timer.schedule(minuteTask,0l,1000*60);  //after every one minute
                
                
            }
        });
        
        
    }

    private  void updateLocation(JsonObject object)
    {
//        Toast.makeText(this,object.toString(),Toast.LENGTH_SHORT).show();

        RetrofitApiClient.getClient().create(IRetrofit.class).update_location(object).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,"Response: "+response.body().toString(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
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

                JsonObject object=new JsonObject();
                object.addProperty("id","5ef7096349cc7e2fbc2db420");
                object.addProperty("latitude",ltd);
                object.addProperty("longitude",longtude);

                updateLocation(object);


                textView.setText("\n"+latitude+", "+longitude);

            }
        }
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }
}
