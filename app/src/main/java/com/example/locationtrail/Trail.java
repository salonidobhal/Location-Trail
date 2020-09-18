package com.example.locationtrail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Trail extends AppCompatActivity {
    DatabaseReference databaseReference;
    ListView listViewLocation;
    List<Loc> locationList;
    TextView textView;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail);

        fetchData();
        databaseReference= FirebaseDatabase.getInstance().getReference(number);
        listViewLocation=findViewById(R.id.listView);
        locationList= new ArrayList<>();
        textView=findViewById(R.id.mn);
        textView.setText(number);

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        Loc location= ds.getValue(Loc.class);
                        locationList.add(location);
                    }
                    LocationList adapter=new LocationList(Trail.this, locationList);
                    listViewLocation.setAdapter(adapter);
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void fetchData() {
        SharedPreferences prefs = getSharedPreferences("Mobile num", MODE_PRIVATE);
        number = prefs.getString("number", "0");
    }
}
