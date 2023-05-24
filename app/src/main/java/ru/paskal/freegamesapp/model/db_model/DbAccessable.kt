package ru.paskal.freegamesapp.model.db_model

import ru.paskal.freegamesapp.model.Game

/**
 * DbAccessable - Интерфейс для доступа к базе данных.
 *
 * Реализующие этот интерфейс классы имеют возможность добавлять и удалять понравившиеся игры из базы данных.
 *
 * @property dbModel Модель базы данных.
 */
interface DbAccessable : AllGamesGotListener {
    val dbModel: DatabaseModel

    /**
     * Добавляет игру в список понравившихся игр в базе данных.
     *
     * @param game Игра для добавления.
     */
    fun addGameToLiked(game: Game) {
        dbModel.insertGame(game)
    }

    /**
     * Удаляет игру из списка понравившихся игр в базе данных.
     *
     * @param game Игра для удаления.
     */
    fun removeGameFromLiked(game: Game) {
        dbModel.deleteGame(game)
    }
}
