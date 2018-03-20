package com.whphy.enoticeboard.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.whphy.enoticeboard.DBAdapter;
import com.whphy.enoticeboard.models.NotifModel;
import com.whphy.enoticeboard.R;
import com.whphy.enoticeboard.activities.MainActivity;
import com.whphy.enoticeboard.utility.Utils;

/**
 * Created by Amrish on 22-01-2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String TAG ="FIrebase Msg";
    DBAdapter dbAdapter;
    NotifModel notifModel;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "onMessageReceived: "+remoteMessage.getData() );
        if(Utils.getUserId(getApplicationContext()).equals("null")){

        }else{
            createNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"));

            dbAdapter = new DBAdapter(getApplicationContext());
            notifModel = new NotifModel(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"));
            dbAdapter.addNotif(notifModel);

        }
    }
    private void createNotification(String title, String body) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo));/*Notification icon image*/

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
