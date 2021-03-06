package com.exmple.firebase.pushnotification.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.exmple.firebase.pushnotification.MainActivity;
import com.exmple.firebase.pushnotification.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        System.out.println("FCM: " + remoteMessage.getFrom());
        openStore(this, remoteMessage);
//        sendNotification("Amitesh");
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        String newToken = s;
        System.out.println("FCM Token = " + newToken);
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Nio Reader")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notificationBuilder.build());
    }

    public void openStore(Context context, RemoteMessage remoteMessage) {
        Intent intent;
        Map<String, String> data = remoteMessage.getData();
        for (int i = 0; i < data.size(); i++) {
            System.out.println("Map Data = " + data.toString());
        }
        System.out.println("remoteMessage.getData().containsKey" + remoteMessage.getData().containsKey("appPackageName"));
        if (remoteMessage.getData().containsKey("appPackageName")) {
//            final String appPackageName = remoteMessage.getData().get("appPackageName"); // getPackageName()
//            try {
//                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
//            } catch (android.content.ActivityNotFoundException anfe) {
//                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
//            }
            Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));

            int notificaionId = 1;
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.BigTextStyle bigTextNotiStyle = null;
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Amitesh"/* + remoteMessage.getData().get("title")*/)
                    .setContentText("Maurya" /*+ remoteMessage.getData().get("desc")*/)
                    .setStyle(bigTextNotiStyle)
                    .setAutoCancel(true)
                    .setColor(Color.BLUE)
                    .setContentIntent(pIntent)
                    .setLights(Color.RED, 3000, 3000);
            notificationManager.notify(notificaionId, mBuilder.build());
        }
    }

}
