package com.dragonize.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dragonize.notifications.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    object Notif {
        val CHANNEL_ID = "#123"
        val CHANNEL_NAME = "my notification"
        val CHANNEL_DESCRIPTION = "Test"
        var id: Int = 0
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Notif.CHANNEL_ID,
                Notif.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = Notif.CHANNEL_DESCRIPTION
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        binding.apply {
            tvBtn.setOnClickListener {
                showNotification()
            }
        }
    }


    private fun showNotification() {
        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, Notif.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle("Hello, attention!")
            .setContentText("Here's the notification you were looking for!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        val mNotificationManager = NotificationManagerCompat.from(this)
        mNotificationManager.notify(Notif.id++, mBuilder.build())
    }
}