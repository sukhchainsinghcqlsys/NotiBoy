package com.dragonize.notifications

import android.app.*
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.dragonize.notifications.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    class NotifChannel {
        companion object {
            var id: Int = 0
        }
        val CHANNEL_ID = "#123"+(id++)
        val CHANNEL_NAME = "my notification"
        val CHANNEL_DESCRIPTION = "Test"
    }
    class Notif(var SMALL_ICON:Int, var TITLE:String, var DESCRIPTION:String, var GROUP:String) {
        companion object {
            var id: Int = 0
        }
        val ID:Int = id++
    }

    lateinit var binding: ActivityMainBinding
    lateinit var nc: NotificationChannel
    lateinit var mNotificationManager: NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mNotificationManager = NotificationManagerCompat.from(this@MainActivity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannelGroup(NotificationChannelGroup("NotiBoy", "Noti"))
        }
    }

    override fun onStart() {
        super.onStart()
        val notifChannel = NotifChannel()
        nc = createNotifChannel(notifChannel)


        val list = ArrayList<Notif>()
        list.add(Notif(R.drawable.ic_notifications,
            "Hello, attention!",
            "Here's the notification you were looking for!",
            "NotiBoy"))
        list.add(Notif(R.drawable.ic_notifications,
            "Hello, attention!",
            "Here's the notification you were looking for!",
            "NotiBoy"))
        list.add(Notif(R.drawable.ic_notifications,
            "Hello, attention!",
            "Here's the notification you were looking for!",
            "NotiBoy"))
        list.add(Notif(R.drawable.ic_notifications,
            "Hello, attention!",
            "Here's the notification you were looking for!",
            "NotiBoy"))

        binding.apply {
            tvBtn.setOnClickListener {
                showNotification(notifChannel, list[list.size-1])
//                showNotifications(notifChannel, list)
                Log.i("NotifChannel1 :",""+
                    mNotificationManager.notificationChannelGroups.count())
            }
            tvBtn2.setOnClickListener {
                list.lastOrNull()?.let { it1 ->
                    mNotificationManager.cancel(
                        it1.ID)
                }
                list.removeLastOrNull()
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

    private fun showNotification(nc: NotifChannel, n: Notif) {// Get the layouts to use in the custom notification
//        val notificationLayout = RemoteViews(packageName, R.layout.notification_basic)
//        val notificationLayoutExpanded = RemoteViews(packageName, R.layout.notification_expanded)

        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, nc.CHANNEL_ID)
            .setSmallIcon(n.SMALL_ICON)
            .setContentTitle(n.TITLE)
            .setContentText(n.DESCRIPTION)
            .setGroup(n.GROUP).setGroupSummary(true)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_notification_background)
                    ?.let { drawableToBitmap(it) })
                .bigLargeIcon(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_notifications)
                    ?.let { drawableToBitmap(it) }))
            .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_ALL)
//            .setCustomContentView(notificationLayout)
//            .setCustomBigContentView(notificationLayoutExpanded)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setAutoCancel(false)


        mNotificationManager.notify(n.ID, mBuilder.build())
    }

    private fun showNotifications(nc: NotifChannel, ns: ArrayList<Notif>) {
        ns.forEach { it ->
            val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, nc.CHANNEL_ID)
                .setSmallIcon(it.SMALL_ICON)
                .setContentTitle(it.TITLE)
                .setContentText(it.DESCRIPTION)
                .setGroup(it.GROUP)
                .setGroupSummary(true)
                .setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_notification_background)
                        ?.let { drawableToBitmap(it) }))
                .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_ALL)
//            .setCustomContentView(notificationLayout)
//            .setCustomBigContentView(notificationLayoutExpanded)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            if (ns.last().ID == it.ID)
                mBuilder.setGroupSummary(true)

            mNotificationManager.notify(it.ID, mBuilder.build())
        }
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap? {
        var bitmap: Bitmap? = null
        if (drawable is BitmapDrawable) {
            val bitmapDrawable = drawable
            if (bitmapDrawable.bitmap != null) {
                return bitmapDrawable.bitmap
            }
        }
        bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
            ) // Single color bitmap will be created of 1x1 pixel
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}