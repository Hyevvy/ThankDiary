package com.examplepackage.thankdiary;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channelD = "channelD";
    public static final String channelNm = "channelNm";

    private NotificationManager notiManager;

    public NotificationHelper(Context base) {
        super(base);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannels();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels(){
        NotificationChannel channel1 = new NotificationChannel(channelD, channelNm, NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.caldroid_333);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel1);
    }

    public NotificationManager getManager(){
        if(notiManager == null){
            notiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notiManager;
    }

    public NotificationCompat.Builder getChannelNotification(){
        return new NotificationCompat.Builder(getApplicationContext(), channelD)
                .setContentTitle("매일을 소중히")
                .setContentText("오늘도 감사 일기와 함께 하루를 채워보세요")
                .setSmallIcon(R.drawable.ic_people_heart);
    }
}
