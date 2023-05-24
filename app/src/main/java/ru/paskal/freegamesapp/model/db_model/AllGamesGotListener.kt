package ru.paskal.freegamesapp.model.db_model

import ru.paskal.freegamesapp.model.Game

interface AllGamesGotListener {
    fun onGotGames(gameList: MutableList<Game>)
}