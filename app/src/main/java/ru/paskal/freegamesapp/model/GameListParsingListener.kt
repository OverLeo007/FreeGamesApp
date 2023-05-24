package ru.paskal.freegamesapp.model

interface GameListParsingListener {
    fun onGamesItemsParsed(gamesList: MutableList<Game>)

}

