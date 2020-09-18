package com.example.locationtrail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
//import java.util.Set;

public class PairedDevices extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_ENABLE_BT =1 ;
    BluetoothAdapter myBluetoothAdapter;
    ListView discoverListView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    Button scan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paired_devices);
        myBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        discoverListView= findViewById(R.id.listDevices);
        scan= findViewById(R.id.scan);
        scan.setOnClickListener(this);
        arrayList=new ArrayList<String>();
        checkBluetoothEnabled();
        discoverDevices();
    }

    private void checkBluetoothEnabled(){
        if(!myBluetoothAdapter.isEnabled())
        {
            Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,REQUEST_ENABLE_BT);
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


//    private void listPairedDevices(){
//        Set<BluetoothDevice> bluetoothDevices=myBluetoothAdapter.getBondedDevices();
//        String[] devices=new String[bluetoothDevices.size()];
//        int index=0;
//
//        if (bluetoothDevices.size()>0)
//        {
//            for (BluetoothDevice device : bluetoothDevices)
//            {
//                devices[index]=device.getName();
//                index++;
//            }
//            arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,devices);
//            pairedListView.setAdapter(arrayAdapter);
//        }
//    }

    private void discoverDevices(){
        myBluetoothAdapter.startDiscovery();
        IntentFilter intentFilter=new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(myReceiver, intentFilter);
        arrayAdapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        discoverListView.setAdapter(arrayAdapter);
    }
    BroadcastReceiver myReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action= intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action))
            {
                BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                arrayList.add(device.getName());
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onClick(View v) {
//        discoverDevices();
//        Toast.makeText(this, "Scan Successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MapsActivity2.class));


    }
}
