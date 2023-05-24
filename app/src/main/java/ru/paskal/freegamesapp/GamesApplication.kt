package ru.paskal.freegamesapp

import android.app.Application
import ru.paskal.freegamesapp.model.db_model.GamesRoomDatabase

class GamesApplication: Application() {
    val database: GamesRoomDatabase by lazy { GamesRoomDatabase.getDatabase(this) }
}