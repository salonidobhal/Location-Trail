package com.example.locationtrail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button findLoc, locationTrail, newBtn;
    private EditText mobileNumber;
    private Spinner spinner;
    private String number;
    private  String phonenumber;
    final private int REQUEST_ENABLE_BT=1;
    BluetoothAdapter myBluetoothAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mobileNumber = findViewById(R.id.number);
        newBtn = findViewById(R.id.otp);
        findLoc = findViewById(R.id.find);
        locationTrail = findViewById(R.id.trail);
        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
        spinner.setSelection(79);
        accessPermission();
        redirectMethod();
        registerListener();
        bluetoothOnMethod();

    }

    private void redirectMethod() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            mobileNumber.setEnabled(false);
            newBtn.setEnabled(false);
            newBtn.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            mobileNumber.setText("User Verified");
            spinner.setVisibility(View.GONE);
        }
        else
        {
            findLoc.setEnabled(false);
            locationTrail.setEnabled(false);
            findLoc.setVisibility(View.INVISIBLE);
            locationTrail.setVisibility(View.INVISIBLE);
            accessPermission();

        }
    }

    private void accessPermission() {
        ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                PackageManager.PERMISSION_GRANTED);
    }

    public void registerListener() {
        findLoc.setOnClickListener(this);
        locationTrail.setOnClickListener(this);
        newBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.otp: {
                    String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                    number= mobileNumber.getText().toString();

                    if (number.isEmpty() || number.length() < 10) {
                        mobileNumber.setError("Valid number is required");
                        mobileNumber.requestFocus();
                        return;
                    }

                    phonenumber = "+" + code + number;
                    Intent intent = new Intent(this, Otp_Activity.class);
                    intent.putExtra("phonenumber", phonenumber);
                    startActivity(intent);
                }
                break;

                case R.id.find: {
                    startService();
                    Intent intent = new Intent(this, MapsActivity.class);
                    startActivity(intent);
                }
                break;

                case R.id.trail: {
                    Intent intent = new Intent(this, NearbyDevices.class);
                    startActivity(intent);
                }
                break;
            }
    }
    public void startService() {
        Intent serviceIntent = new Intent(this, ServiceClass.class);
        startService(serviceIntent);
    }

    private void bluetoothOnMethod(){
         myBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();

        if (myBluetoothAdapter== null){
            Toast.makeText(getApplicationContext(), " This device does not support Bluetooth", Toast.LENGTH_LONG).show();;
        }
        else{
            if(!myBluetoothAdapter.isEnabled())
            {
                Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent,REQUEST_ENABLE_BT);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK)
            {
                Toast.makeText(this, "Bluetooth is Enabled", Toast.LENGTH_LONG).show();
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "Bluetooth Enabling Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void bluetoothOffMethod(){
        if(myBluetoothAdapter.isEnabled()){
            myBluetoothAdapter.disable();
        }
    }

}

