package and.parser.models

import kotlin.time.Duration

enum class MatchResult { Win, Loss, NotScored, Abandoned }

enum class MatchmakingType { Ranked, Unranked }

enum class GameType { AllPick, Turbo, SingleDraft, Other }

enum class PartyStatus(val members: Int) {
    SoloQueue(1), DuoQueue(2), Party3(3), Party4(4), FullStack(5)
}

enum class Role { Core, Support }

enum class Lane { Top, Middle, Bottom }

data class Kda(val kills: Int, val deaths: Int, val assists: Int)

data class Match(
    val hero: String,
    val rank: String,
    val result: MatchResult,
    val matchmakingType: MatchmakingType,
    val gameType: GameType,
    val duration: Duration,
    val partyStatus: PartyStatus,
    val role: Role?,
    val lane: Lane?,
    val kda: Kda
)