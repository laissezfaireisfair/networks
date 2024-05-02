package and.networks.api

import and.networks.models.Match
import and.networks.parser.Parser
import and.networks.parser.toMatch
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/matches")
class MatchesController {
    @GetMapping("get/{playerId}/{page}")
    fun getMatches(@PathVariable playerId: Int, @PathVariable page: Int): List<Match> {
        return Parser.getMatchEntries(playerId, page).mapNotNull {
                try {
                    it.toMatch()
                } catch (exception: Exception) {
                    println(exception)
                    null
                }
            }
    }
}