package ru.paskal.freegamesapp.model
import android.text.Spanned
import android.util.Log
import org.w3c.dom.Node
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.HttpURLConnection
import java.net.URL


/**
 * Game - Класс, представляющий игру.
 *
 * @property title Название игры.
 * @property description Описание игры в формате Spanned.
 * @property imgUrl URL-адрес изображения игры.
 * @property readMoreUrl URL-адрес для получения дополнительной информации об игре.
 * @property isLiked Флаг, указывающий на то, является ли игра избранной (понравившейся).
 */
data class Game(
    var title: String?,
    var description: Spanned?,
    var imgUrl: String?,
    var readMoreUrl: String?,
    var isLiked: Boolean
) {
    /**
     * Переопределение метода equals для сравнения объектов класса Game.
     *
     * @param other Другой объект для сравнения.
     * @return true, если объекты равны, иначе false.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Game) return false
        return this.title == other.title && this.readMoreUrl == other.readMoreUrl
    }

    /**
     * Переопределение метода hashCode для вычисления хэш-кода объекта класса Game.
     *
     * @return Хэш-код объекта.
     */
    override fun hashCode(): Int {
        var result = title?.hashCode() ?: 0
        result = 31 * result + (description?.hashCode() ?: 0)
        return result
    }
}
