package ru.paskal.freegamesapp.events

import ru.paskal.freegamesapp.model.Game

/**
 * GamesGotFromDbEvent - Событие получения игр из базы данных.
 *
 * @property gameList Список игр, полученных из базы данных.
 */
class GamesGotFromDbEvent(val gameList: MutableList<Game>)