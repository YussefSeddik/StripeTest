package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotifyButton.setOnClickListener {
            createNotification("DDD", "DDDD", PendingIntent.getActivity(
                    this, 222,
                    Intent(), PendingIntent.FLAG_ONE_SHOT
            ), "JHJ")
        }
    }

    fun createNotification(
            title: String?,
            msg: String?,
            pendingIntent: PendingIntent,
            groupName: String? = ""
    ) {
        val channelId = "${this.packageName}-MyApplication2"
        val soundUri = Uri.parse("android.resource://${packageName}/${R.raw.notification_sound}")
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(
                        BitmapFactory.decodeResource(
                                this.resources,
                                R.drawable.ic_launcher_background
                        )
                )
                .setContentTitle(title)
                .setContentText(msg)
                .setStyle(NotificationCompat.BigTextStyle().bigText(msg))
                .setTicker(msg)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSound(soundUri)
                .setGroup(groupName)

        val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()
            val channel = NotificationChannel(
                    channelId, channelId,
                    NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = Color.DKGRAY
                setShowBadge(true)
                enableVibration(true)
                setSound(soundUri, audioAttributes)
                vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

            }
            notificationManager.createNotificationChannel(channel)
        }

        NotificationManagerCompat.from(this).notify(222, notificationBuilder.build())
    }
}