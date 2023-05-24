package ru.paskal.freegamesapp.model.db_model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.paskal.freegamesapp.model.Game

@Dao
interface GameItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gameItem: GameItem)

    @Delete
    suspend fun delete(gameItem: GameItem)

    @Query("SELECT * from liked_games")
    fun getAll() : Flow<List<GameItem>>
}