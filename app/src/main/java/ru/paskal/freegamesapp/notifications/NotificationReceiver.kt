package ru.paskal.freegamesapp.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import ru.paskal.freegamesapp.MainActivity
import ru.paskal.freegamesapp.R
import ru.paskal.freegamesapp.ui.favorite.FavoriteFragment

/**
 * NotificationReceiver - Приемник широковещательных сообщений для отображения уведомления.
 */
class NotificationReceiver : BroadcastReceiver() {
    /**
     * Вызывается при получении широковещательного сообщения.
     *
     * @param context Контекст приложения.
     * @param intent  Интент, содержащий передаваемые данные.
     */
    override fun onReceive(context: Context, intent: Intent) {

        val notification = NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_like)
            .setContentTitle("FreeGamesApp need your attention")
            .setContentText("Check your liked games")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(MainActivity.NOTIFICATION_ID, notification)

    }
}