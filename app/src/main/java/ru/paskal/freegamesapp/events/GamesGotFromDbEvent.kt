package ru.paskal.freegamesapp.events

import ru.paskal.freegamesapp.model.Game

class GamesGotFromDbEvent(val gameList: MutableList<Game>)