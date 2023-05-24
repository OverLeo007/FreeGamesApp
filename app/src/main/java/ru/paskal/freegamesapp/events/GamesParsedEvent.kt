package ru.paskal.freegamesapp.events

import ru.paskal.freegamesapp.model.Game

/**
 * GamesParsedEvent - Событие парсинга игр.
 *
 * @property gameList Список игр, спаршенных из источника данных.
 */
class GamesParsedEvent(val gameList: MutableList<Game>)