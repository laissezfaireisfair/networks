package and.parser.webScheme

import and.parser.models.*
import kotlin.time.Duration.Companion.minutes

data class MatchEntry(
    val hero: String,
    val rank: String,
    val result: String,
    val matchmakingType: String,
    val gameType: String,
    val duration: String,
    val partyStatus: String,
    val role: String,
    val lane: String,
    val kda: String
)

fun MatchEntry.toMatch(): Match {
    return Match(  // TODO: Implement
        hero = hero,
        rank = rank,
        result = MatchResult.Loss,
        matchmakingType = MatchmakingType.Ranked,
        gameType = GameType.AllPick,
        duration = 45.minutes,
        partyStatus = PartyStatus.SoloQueue,
        role = Role.Support,
        lane = Lane.Top,
        kda = Kda(kills = 0, deaths = 100, assists = 0)
    )
}