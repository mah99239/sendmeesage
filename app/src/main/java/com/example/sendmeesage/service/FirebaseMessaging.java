package com.example.sendmeesage.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.sendmeesage.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class FirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        SharedPreferences sp = getSharedPreferences("SP_USER",MODE_PRIVATE);

        String saveCurrentUser = sp.getString("Current_USERID","none");
        String sent = remoteMessage.getData().get("sent");
        String user= remoteMessage.getData().get("user");
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        if(fUser!=null && sent.equals(fUser.getUid())){
            if(!saveCurrentUser.equals(user)){
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                    sendOAndAboveNotification(remoteMessage);
                }else{
                    sendNormalNotification(remoteMessage);
                }
            }

        }

    }

    private void sendNormalNotification(RemoteMessage remoteMessage) {
        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title  = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        int i  = Integer.parseInt(user.replaceAll("[\\D]",""));
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("hasUid",user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this ,i, intent,PendingIntent.FLAG_ONE_SHOT);
        Uri defUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(Integer.parseInt(icon))
                .setContentText(body)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setSound(defUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        int j = 0;
        if(i>0){
            j=i;
        }
        notificationManager.notify(j , builder.build());
    }

    public void sendOAndAboveNotification(RemoteMessage remoteMessage) {
        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title  = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int i  = Integer.parseInt(user.replaceAll("[\\D]",""));
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("hasUid",user);

        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this ,i, intent,PendingIntent.FLAG_ONE_SHOT);
        Uri defUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
      OreoAndAboveNotification notification1  = new OreoAndAboveNotification(this);
        Notification.Builder builder = notification1.gerNotification(title , body , pendingIntent,defUri , icon);

        int j = 0;
        if(i>0){
            j=i;
        }
        notification1.getManager().notify(j , builder.build());
    }
}


