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


class GamesViewModel(application: Application, override val dbModel: DatabaseModel) : AndroidViewModel(application),
    GameListParsingListener, DbAccessable {

    private val parser = FeedParser()
    lateinit var gamesList: MutableList<Game>

    init {
        parseGames()
    }

    fun parseGames() {
        parser.parse("https://www.alphabetagamer.com/feed/", this)
    }

    fun refresh() {
        gamesList.clear()
        parseGames()
    }

    override fun onGamesItemsParsed(gamesList: MutableList<Game>) {
        this.gamesList = gamesList
        dbModel.getAllGames(this)
    }


    override fun onGotGames(gameList: MutableList<Game>) {
        for (game in this.gamesList) {
            if (game in gameList) {
                game.isLiked = true
            }
        }
        EventBus.getDefault().post(GamesParsedEvent(this.gamesList))
    }

}
