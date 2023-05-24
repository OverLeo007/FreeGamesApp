package ru.paskal.freegamesapp.model.db_model

import android.text.Html
import android.util.Log
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.paskal.freegamesapp.model.Game

/**
 * DatabaseModel - Модель базы данных для доступа к понравившимся играм.
 *
 * @property gameItemDao DAO для операций с таблицей понравившихся игр.
 */
class DatabaseModel(private val gameItemDao: GameItemDao) : ViewModel() {

    private var listener: AllGamesGotListener? = null

    /**
     * Преобразует объект Game в объект GameItem.
     *
     * @param game Игра для преобразования.
     * @return Преобразованный объект GameItem.
     */
    private fun convertGameToItem(game: Game) : GameItem {
        return GameItem(
            title = game.title!!,
            description = Html.toHtml(game.description, HtmlCompat.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL),
            imgUrl = game.imgUrl,
            readMoreUrl = game.readMoreUrl
        )
    }

    /**
     * Преобразует объект GameItem в объект Game.
     *
     * @param gameItem Объект GameItem для преобразования.
     * @return Преобразованный объект Game.
     */
    private fun convertItemToGame(gameItem: GameItem): Game {
        return Game(
            gameItem.title,
            Html.fromHtml(gameItem.description, HtmlCompat.FROM_HTML_MODE_LEGACY),
            gameItem.imgUrl,
            gameItem.readMoreUrl,
            true
        )
    }

    /**
     * Добавляет игру в базу данных.
     *
     * @param game Игра для добавления.
     */
    fun insertGame(game: Game) {
        viewModelScope.launch {
            gameItemDao.insert(convertGameToItem(game))
        }
    }

    /**
     * Удаляет игру из базы данных.
     *
     * @param game Игра для удаления.
     */
    fun deleteGame(game: Game) {
        viewModelScope.launch {
            gameItemDao.delete(convertGameToItem(game))
        }
    }

    /**
     * Плдучает все игры из базы данных.
     *
     * @param listener Слушатель куда придет список игр.
     */
    fun getAllGames(listener: AllGamesGotListener) {
        viewModelScope.launch {
            val games = mutableListOf<Game>()
            gameItemDao.getAll().take(1).collect { gameItems ->
                gameItems.mapTo(games) { convertItemToGame(it) }
            }
            listener.onGotGames(games)
        }
    }
}


class DatabaseModelFactory(private val itemDao: GameItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DatabaseModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DatabaseModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}