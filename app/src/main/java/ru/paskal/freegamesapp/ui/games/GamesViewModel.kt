package ru.paskal.freegamesapp.ui.games

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import ru.paskal.freegamesapp.model.FeedParser
import ru.paskal.freegamesapp.model.Game
import ru.paskal.freegamesapp.model.GameListParsingListener
import org.greenrobot.eventbus.EventBus
import ru.paskal.freegamesapp.events.GamesParsedEvent
import ru.paskal.freegamesapp.model.db_model.DbAccessable
import ru.paskal.freegamesapp.model.db_model.DatabaseModel

/**
 * GamesViewModel - ViewModel для отображения списка игр.
 *
 * @param application Приложение, на основе которого создается ViewModel.
 * @param dbModel Модель базы данных для доступа к данным игр.
 */
class GamesViewModel(application: Application, override val dbModel: DatabaseModel) : AndroidViewModel(application),
    GameListParsingListener, DbAccessable {

    /**
     * Парсер игр.
     */
    private val parser = FeedParser()

    /**
     * Список с играми.
     */
    lateinit var gamesList: MutableList<Game>

    init {
        parseGames()
    }

    /**
     * Выполняет парсинг списка игр.
     */
    fun parseGames() {
        parser.parse("https://www.alphabetagamer.com/feed/", this)
    }

    /**
     * Обновляет список игр.
     */
    fun refresh() {
        gamesList.clear()
        parseGames()
    }

    /**
     * Обратный вызов, вызывается после успешного парсинга списка игр.
     *
     * @param gamesList Список игр, полученный в результате парсинга.
     */
    override fun onGamesItemsParsed(gamesList: MutableList<Game>) {
        this.gamesList = gamesList
        dbModel.getAllGames(this)
    }

    /**
     * Обратный вызов, вызывается после получения всех игр из базы данных.
     *
     * @param gameList Список игр из базы данных.
     */
    override fun onGotGames(gameList: MutableList<Game>) {
        for (game in this.gamesList) {
            if (game in gameList) {
                game.isLiked = true
            }
        }
        EventBus.getDefault().post(GamesParsedEvent(this.gamesList))
    }

}
