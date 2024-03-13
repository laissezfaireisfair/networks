package and.parser

import and.parser.webScheme.MatchEntry
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.extractIt
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.a
import it.skrape.selects.html5.body

class Parser {
    class Entries {
        val body = mutableListOf<MatchEntry>()
    }

    companion object {
        fun getMatchEntries(playerId: Int): List<MatchEntry> {
            return skrape(HttpFetcher) {
                request { url = "https://ru.dotabuff.com/players/$playerId/matches" }

                extractIt<Entries> { entries ->
                    htmlDocument {
                        val rows = findLast("article table tbody") {
                            findAll("tr") { this }
                        }

                        rows.forEach {
                            val hero = it.findFirst("td.cell-large a") { text }
                            val rank = it.findFirst("td.cell-large div") { text }
                            val result = it.findByIndex(3, "td") { findFirst("a") { text } }
                            val matchmakingType = it.findByIndex(4, "td") { ownText }
                            val gameType = it.findFirst("td.r-none-mobile div") { text }

                            entries.body.add(
                                MatchEntry(
                                    hero = hero,
                                    rank = rank,
                                    result = result,
                                    matchmakingType = matchmakingType,
                                    gameType = gameType,
                                    duration = "",
                                    partyStatus = "",
                                    role = "",
                                    lane = "",
                                    kda = ""
                                )
                            )
                        }

                    }
                }
            }.body
        }
    }
}