package ru.paskal.freegamesapp.events

import ru.paskal.freegamesapp.model.Game

class GamesParsedEvent(val gameList: MutableList<Game>)