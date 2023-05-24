package ru.paskal.freegamesapp.model

import android.text.Html
import android.util.Log
import android.util.Xml
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import java.io.StringReader
import java.net.URL

/**
 * FeedParser - Класс для парсинга игровых данных из XML-ленты.
 */
class FeedParser {

    /**
     * Слушатель для обработки результатов.
     */
    private var listener: GameListParsingListener? = null

    /**
     * Метод для парсинга XML-ленты с игровыми данными.
     *
     * @param xmlUrl URL-адрес XML-ленты.
     * @param listener Слушатель для обработки результатов парсинга.
     */
    fun parse(xmlUrl: String, listener: GameListParsingListener) {
        this.listener = listener
        CoroutineScope(Dispatchers.IO).launch {
            val gameList = listFromFeed(xmlUrl)
            withContext(Dispatchers.Main) {
                listener.onGamesItemsParsed(gameList)
            }
        }
    }

    private suspend fun listFromFeed(xmlUrl: String): MutableList<Game> {
        val gameList = mutableListOf<Game>()
        val document = URL(xmlUrl).readText()
        val parser = Xml.newPullParser()

        parser.setInput(StringReader(document))
        var eventType = parser.eventType
        var currentGame: Game? = null
        var insideItem = false


        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "item" -> {
                            currentGame = Game(null, null,
                                null, null, false)
                            insideItem = true
                        }

                        "title" -> {
                            currentGame?.title = parser.nextText()
                        }

                        "description" -> {
                            if (insideItem) {
                                val description = parser.nextText()
                                // Find the imgUrl in the <video> tag and poster attribute.
                                val pattern = "<video.*?poster=\"(.*?)\".*?>".toRegex()
                                val matchResult = pattern.find(description)
                                val imgUrl = matchResult?.groupValues?.get(1)
                                currentGame?.imgUrl = imgUrl
                                val pPattern = "<p>(?:(?!<video).)*?</p>".toRegex()
                                val descText = pPattern.findAll(description).map { it.value }.toList().joinToString("")
                                val noLinksText = descText
                                    .replace(Regex("<a.*?>"), "")
                                    .replace("</a>", "")
                                    .replace(Regex("Read More"), "")
                                    .replace("  </p>", "</p>")
                                val readMoreLinkRegex = Regex("<a.*?>Read More </a>")
                                val readMoreLinkMatch = readMoreLinkRegex.find(descText)
                                currentGame?.readMoreUrl = readMoreLinkMatch?.value?.let {
                                    val hrefRegex = Regex("href=\"(.*?)\"")

                                    val hrefMatch = hrefRegex.find(it)
                                    hrefMatch?.groups?.get(1)?.value
                                }

                                currentGame?.description = Html.fromHtml(noLinksText)
                            }
                        }
                    }
                }

                XmlPullParser.END_TAG -> {
                    if (parser.name == "item") {
                        insideItem = false
                        currentGame?.let {
                            gameList.add(it)
                        }
                        currentGame = null
                    }
                }
            }
            eventType = parser.next()
        }

        return gameList
    }
}