package ru.paskal.freegamesapp.model.db_model

import ru.paskal.freegamesapp.model.Game

/**
 * AllGamesGotListener - Интерфейс слушателя для получения списка всех игр.
 */
interface AllGamesGotListener {

    /**
     * Вызывается при получении списка всех игр.
     *
     * @param gameList Список всех игр.
     */
    fun onGotGames(gameList: MutableList<Game>)
}