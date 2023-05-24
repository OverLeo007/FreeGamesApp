package ru.paskal.freegamesapp.ui.favorite

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import ru.paskal.freegamesapp.events.GamesGotFromDbEvent
import ru.paskal.freegamesapp.events.GamesParsedEvent
import ru.paskal.freegamesapp.events.LikeStateChangedEvent
import ru.paskal.freegamesapp.model.Game
import ru.paskal.freegamesapp.model.db_model.DbAccessable
import ru.paskal.freegamesapp.model.db_model.DatabaseModel

/**
 * FavoriteViewModel - ViewModel для работы со списком избранных игр.
 */
class FavoriteViewModel(
    application: Application,
    override val dbModel: DatabaseModel
) : AndroidViewModel(application), DbAccessable {

    /**
     * Список игр в избранном.
     */
    lateinit var gamesList: MutableList<Game>

    /**
     * Инициализация ViewModel.
     */
    init {
        getGames()
    }

    /**
     * Получает список игр из базы данных.
     */
    fun getGames() {
        dbModel.getAllGames(this)
    }

    /**
     * Обработчик получения списка игр из базы данных.
     */
    override fun onGotGames(gameList: MutableList<Game>) {
        this.gamesList = gameList
        EventBus.getDefault().post(GamesGotFromDbEvent(this.gamesList))
    }

    /**
     * Обновляет список игр.
     */
    fun refresh() {
        gamesList.clear()
        getGames()
    }

    /**
     * Удаляет игру из базы данных.
     */
    override fun removeGameFromLiked(game: Game) {
        dbModel.deleteGame(game)
    }
}
