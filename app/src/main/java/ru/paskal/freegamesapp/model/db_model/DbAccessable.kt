package ru.paskal.freegamesapp.model.db_model

import ru.paskal.freegamesapp.model.Game

interface DbAccessable : AllGamesGotListener {
    val dbModel: DatabaseModel
    fun addGameToLiked(game: Game) {
        dbModel.insertGame(game)
    }
    fun removeGameFromLiked(game: Game) {
        dbModel.deleteGame(game)

    }
}