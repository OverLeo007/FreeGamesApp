package ru.paskal.freegamesapp.model
import android.text.Spanned
import android.util.Log
import org.w3c.dom.Node
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.HttpURLConnection
import java.net.URL


data class Game(
    var title: String?,
    var description: Spanned?,
    var imgUrl: String?,
    var readMoreUrl: String?,
    var isLiked: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Game) return false
        return this.title == other.title && this.readMoreUrl == other.readMoreUrl
    }

    override fun hashCode(): Int {
        var result = title?.hashCode() ?: 0
        result = 31 * result + (description?.hashCode() ?: 0)
        return result
    }
}

//
//class GamesService(xmlFilePath: String) {
//    private var games = mutableListOf<Game>()
//
//
//    private suspend fun loadFromXml(xmlFilePath: String) {
//        withContext(Dispatchers.IO) {
//            val factory = DocumentBuilderFactory.newInstance()
//            val builder = factory.newDocumentBuilder()
//            val input = InputSource(xmlFilePath)
//            // парсим документ и получаем список элементов game
//            val doc = builder.parse(input)
//            val gamesList = doc.getElementsByTagName("game")
//            // проходим по списку игр и добавляем их в список games
//            for (i in 0 until gamesList.length) {
//                val game = gamesList.item(i)
//                if (game.nodeType == Node.ELEMENT_NODE) {
//                    val title = game.childNodes.item(0).textContent
//                    val description = game.childNodes.item(1).textContent
//                    val newGame = Game(title, description)
//                    games.add(newGame)
//                }
//            }
//        }
//    }
//}