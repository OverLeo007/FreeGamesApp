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

class DatabaseModel(private val gameItemDao: GameItemDao) : ViewModel() {

    private var listener: AllGamesGotListener? = null

    private fun convertGameToItem(game: Game) : GameItem {
        return GameItem(
            title = game.title!!,
            description = Html.toHtml(game.description, HtmlCompat.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL),
            imgUrl = game.imgUrl,
            readMoreUrl = game.readMoreUrl
        )
    }

    private fun convertItemToGame(gameItem: GameItem): Game {
        return Game(
            gameItem.title,
            Html.fromHtml(gameItem.description, HtmlCompat.FROM_HTML_MODE_LEGACY),
            gameItem.imgUrl,
            gameItem.readMoreUrl,
            true
        )
    }

    fun insertGame(game: Game) {
        viewModelScope.launch {
            gameItemDao.insert(convertGameToItem(game))
        }
    }

    fun deleteGame(game: Game) {
        viewModelScope.launch {
            gameItemDao.delete(convertGameToItem(game))
        }
    }

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