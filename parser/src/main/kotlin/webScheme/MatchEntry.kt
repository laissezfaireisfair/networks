package and.parser.webScheme

import and.parser.models.Match

data class MatchEntry(
    val hero: String,
    val rank: String,
    val result: String,
    val matchmakingType: String,
    val gameType: String,
    val duration: String,
    val partyStatus: String,
    val role: String,
    val late: String,
    val kda: String
)

fun MatchEntry.toMatch(): Match {
    TODO("Implement")
}