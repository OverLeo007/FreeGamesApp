package ru.paskal.freegamesapp.model

/**
 * GameListParsingListener - Интерфейс слушателя для обработки результатов парсинга списка игр.
 */
interface GameListParsingListener {
    /**
     * Вызывается при успешном завершении парсинга списка игр.
     *
     * @param gamesList Список игр, полученных в результате парсинга.
     */
    fun onGamesItemsParsed(gamesList: MutableList<Game>)
}

