package ru.paskal.freegamesapp.events

import ru.paskal.freegamesapp.model.Game

/**
 * LikeStateChangedEvent - Событие изменения состояния понравившейся игры.
 *
 * @property game Игра, состояние которой изменилось.
 */
class LikeStateChangedEvent(val game: Game)