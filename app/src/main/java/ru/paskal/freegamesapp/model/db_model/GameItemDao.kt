package ru.paskal.freegamesapp.model.db_model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.paskal.freegamesapp.model.Game

/**
 * GameItemDao - Интерфейс доступа к данным понравившихся игр в базе данных.
 */
@Dao
interface GameItemDao {
    /**
     * insert - Вставляет понравившуюся игру в базу данных.
     *
     * @param gameItem Понравившаяся игра для вставки.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gameItem: GameItem)

    /**
     * delete - Удаляет понравившуюся игру из базы данных.
     *
     * @param gameItem Понравившаяся игра для удаления.
     */
    @Delete
    suspend fun delete(gameItem: GameItem)

    /**
     * getAll - Получает все понравившиеся игры из базы данных.
     *
     * @return Flow, содержащий список понравившихся игр.
     */
    @Query("SELECT * from liked_games")
    fun getAll(): Flow<List<GameItem>>
}