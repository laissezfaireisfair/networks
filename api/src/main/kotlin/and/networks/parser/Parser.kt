package and.networks.parser

import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.extractIt
import it.skrape.fetcher.skrape

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
                            entries.body.add(MatchEntry(hero = it.findFirst("td.cell-large a") { text },
                                rank = it.findFirst("td.cell-large div") { text },
                                result = it.findByIndex(3, "td") { findFirst("a") { text } },
                                matchmakingType = it.findByIndex(4, "td") { ownText },
                                gameType = it.findFirst("td.r-none-mobile div") { text },
                                duration = it.findByIndex(5, "td") { ownText },
                                role = try {
                                    it.findFirst(".role-icon") { attribute("title") }
                                } catch (_: Exception) {
                                    ""
                                },
                                lane = try {
                                    it.findFirst(".lane-icon") { attribute("title") }
                                } catch (_: Exception) {
                                    ""
                                },
                                kda = it.findFirst(".kda-record") { text })
                            )
                        }

                    }
                }
            }.body
        }
    }
}