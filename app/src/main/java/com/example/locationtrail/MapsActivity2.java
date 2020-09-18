package com.example.locationtrail;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String number;
    DatabaseReference databaseReference;
    String databaseLatitude;
    String databaseLongitude;
    List<LatLng> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        fetchData();

        databaseReference = FirebaseDatabase.getInstance().getReference(number);
        ValueEventListener valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        Loc location = ds.getValue(Loc.class);
                        databaseLatitude = location.getLatitude();
                        databaseLongitude = location.getLongitude();
                        String time = location.getTime();
                        list.add(new LatLng((Double.parseDouble(databaseLatitude)), Double.parseDouble(databaseLongitude)));
//                        LatLng latLng = new LatLng((Double.parseDouble(databaseLatitude)), Double.parseDouble(databaseLongitude));
//                        mMap.addMarker(new MarkerOptions().position(latLng).title(databaseLatitude + " , " + databaseLongitude+" , "+time).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));

                    }

                    mMap.addPolyline(new PolylineOptions().color(Color.BLUE).color(Color.BLUE).geodesic(true)
                            .addAll(list));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }



    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
//    private void fetchData() {
//        SharedPreferences prefs = getSharedPreferences("Mobile num", MODE_PRIVATE);
//        number = prefs.getString("number", "0");
//    }
}
