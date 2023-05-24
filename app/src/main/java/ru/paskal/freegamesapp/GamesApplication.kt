package ru.paskal.freegamesapp

import android.app.Application
import ru.paskal.freegamesapp.model.db_model.GamesRoomDatabase

/**
 * GamesApplication - класс приложения FreeGamesApp, расширяющий класс Application.
 * Он предоставляет доступ к базе данных GamesRoomDatabase.
 */
class GamesApplication : Application() {

    /**
     * Ленивое свойство для доступа к базе данных GamesRoomDatabase.
     */
    val database: GamesRoomDatabase by lazy { GamesRoomDatabase.getDatabase(this) }
}