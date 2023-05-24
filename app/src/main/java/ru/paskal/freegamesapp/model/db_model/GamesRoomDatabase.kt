package ru.paskal.freegamesapp.model.db_model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameItem::class], version = 2, exportSchema = false)
abstract class GamesRoomDatabase : RoomDatabase() {
    abstract fun gameItemDao(): GameItemDao

    companion object {
        @Volatile
        private var INSTANCE: GamesRoomDatabase? = null

        fun getDatabase(context: Context): GamesRoomDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance =  Room.databaseBuilder(
                    context.applicationContext,
                    GamesRoomDatabase::class.java,
                    "liked_games_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}