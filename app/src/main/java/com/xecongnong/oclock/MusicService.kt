package com.xecongnong.oclock


import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.os.Vibrator
import androidx.core.app.NotificationCompat


class MusicService: Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.gagay)
        mediaPlayer!!.isLooping = true
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val isCancel=intent.getBooleanExtra("CANCEL",false)
        if(isCancel)
        {
            stopForeground(true)
            stopSelf(1)
            mediaPlayer!!.stop()
            vibrator!!.cancel()
        }
        else{
            val notificationIntent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
            val alarmTitle = "Báo thức đây"

            val cancelIntent = Intent(this, MusicService::class.java)
            cancelIntent.putExtra("CANCEL", true)
            val servicePendingIntent = PendingIntent.getService(
                this, 0, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            val notification: Notification = NotificationCompat.Builder(this,"oclockid")
                .setContentTitle(alarmTitle)
                .setContentText("Ring Ring .. Ring Ring")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_launcher_foreground,"tắt",servicePendingIntent)
                .build()
            mediaPlayer!!.start()
            val pattern = longArrayOf(0, 100, 1000)
            vibrator!!.vibrate(pattern, 0)
            startForeground(1, notification)
            Thread.sleep(20000)
            stopSelf(1)
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.stop()
        vibrator!!.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

}