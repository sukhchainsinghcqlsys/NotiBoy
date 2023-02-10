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

    class NotifChannel {
        val CHANNEL_ID = "#123"
        val CHANNEL_NAME = "my notification"
        val CHANNEL_DESCRIPTION = "Test"
    }
    class Notif {
        val SMALL_ICON = R.drawable.ic_notifications
        val TITLE = "Hello, attention!"
        val DESCRIPTION = "Here's the notification you were looking for!"
        var id: Int = 0
    }

    lateinit var binding: ActivityMainBinding
    lateinit var nc: NotificationChannel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val notifChannel = NotifChannel()
        nc = createNotifChannel(notifChannel)

        binding.apply {
            tvBtn.setOnClickListener {
                showNotification(notifChannel, Notif())
            }
        }
    }

    private fun createNotifChannel(n: NotifChannel): NotificationChannel {
        lateinit var channel: NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = NotificationChannel(
                n.CHANNEL_ID,
                n.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = n.CHANNEL_DESCRIPTION
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
        return channel
    }

    private fun showNotification(nc: NotifChannel, n: Notif) {
        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, nc.CHANNEL_ID)
            .setSmallIcon(n.SMALL_ICON)
            .setContentTitle(n.TITLE)
            .setContentText(n.DESCRIPTION)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        val mNotificationManager = NotificationManagerCompat.from(this)
        mNotificationManager.notify(n.id++, mBuilder.build())
    }
}