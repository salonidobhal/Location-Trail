package com.example.locationtrail;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.locationtrail.App.CHANNEL_ID;


public class ServiceClass extends Service{

    public ServiceClass(){ }
    @Override
    public void onCreate() {

        Intent notificationIntent=new Intent(this, MapsActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,notificationIntent, 0);

        Notification notification=new NotificationCompat.Builder( this, CHANNEL_ID)
                .setContentTitle("Location Trail")
                .setContentText("is tracking your location")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1,notification);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}






