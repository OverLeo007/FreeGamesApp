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

class FavoriteViewModel(
    application: Application, override val dbModel: DatabaseModel
) : AndroidViewModel(application), DbAccessable
{

    lateinit var gamesList: MutableList<Game>


    init {
         //EventBus.getDefault().register(this)
         getGames()
     }

    fun getGames() {
        dbModel.getAllGames(this)
    }

    override fun onGotGames(gameList: MutableList<Game>) {
        this.gamesList = gameList
        EventBus.getDefault().post(GamesGotFromDbEvent(this.gamesList))
        Log.d("debug", "Games Sent In Favorite ${this.gamesList.size}")

    }

    fun refresh() {
        gamesList.clear()
        getGames()
    }

    override fun removeGameFromLiked(game: Game) {
        dbModel.deleteGame(game)
    }

    override fun onCleared() {
        super.onCleared()
        //EventBus.getDefault().unregister(this)
    }


}
