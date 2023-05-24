package ru.paskal.freegamesapp

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import ru.paskal.freegamesapp.databinding.ActivityMainBinding
import ru.paskal.freegamesapp.notifications.NotificationReceiver
import java.util.Calendar

/**
 * MainActivity - главная активность приложения FreeGamesApp.
 * Она отвечает за создание основного пользовательского интерфейса, навигацию и управление уведомлениями.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Configuration для app bar и navigation drawer.
     */
    private lateinit var appBarConfiguration: AppBarConfiguration

    /**
     * Binding экземпляр для layout активности.
     */
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "favoriteId"
        const val CHANNEL_NAME = "Уведомление об избранных играх"
        const val CHANNEL_DESCRIPTION = "Уведомление об избранных играх в 18:00"
    }

    /**
     * Создает канал уведомлений.
     */
    private fun createNotificationChannel() {
        val name = CHANNEL_NAME
        val descriptionText = CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    /**
     * Планирует уведомление.
     */
    private fun scheduleNotification() {
        val intent = Intent(applicationContext, NotificationReceiver::class.java)
        intent.putExtra("channelId", CHANNEL_ID)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

    /**
     * Возвращает время для уведомления.
     *
     * @return Время в миллисекундах.
     */
    private fun getTime(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(18, 0, 0)
//        return calendar.timeInMillis
        return System.currentTimeMillis()
    }

    /**
     * Вызывается при создании активности.
     *
     * @param savedInstanceState Сохраненное состояние активности.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_games, R.id.nav_news, R.id.nav_favorite
            ), drawerLayout
        )

        createNotificationChannel()
        scheduleNotification()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    /**
     * Вызывается при нажатии на кнопку "Назад" на панели приложения или настроек навигации.
     *
     * @return `true`, если навигация обработана, иначе `false`.
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}