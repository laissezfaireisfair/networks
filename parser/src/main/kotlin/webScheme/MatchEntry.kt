package and.parser.webScheme

import and.parser.models.*
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

data class MatchEntry(
    val hero: String,
    val rank: String,
    val result: String,
    val matchmakingType: String,
    val gameType: String,
    val duration: String,
    val role: String,
    val lane: String,
    val kda: String
)

fun MatchEntry.toMatch(): Match {
    return Match(hero = hero,
        rank = rank,
        result = when (result) {
            "Поражение" -> MatchResult.Loss
            "Победа" -> MatchResult.Win
            "Покинул игру" -> MatchResult.Abandoned
            "Не засчитано" -> MatchResult.NotScored
            else -> throw IllegalArgumentException("Unknown result: $result")
        },
        matchmakingType = when (matchmakingType) {
            "Рейтинговый" -> MatchmakingType.Ranked
            "Обычный" -> MatchmakingType.Unranked
            else -> throw IllegalArgumentException("Unknown matchmaking type: $matchmakingType")
        },
        gameType = when (gameType) {
            "All Pick" -> GameType.AllPick
            "Turbo" -> GameType.Turbo
            "Single draft" -> GameType.SingleDraft
            else -> GameType.Other
        },
        duration = with(duration.split(":")) { this[0].toInt().minutes + this[1].toInt().seconds },
        role = when (role) {
            "Роль поддержки" -> Role.Support
            "Ключевая роль" -> Role.Core
            else -> null
        },
        lane = when (lane) {
            "Сложная линия" -> Lane.Offlane
            "Легкая линия" -> Lane.Easy
            "Центральная линия" -> Lane.Middle
            else -> null
        },
        kda = with(kda.split("/")) {
            Kda(kills = this[0].toInt(), deaths = this[1].toInt(), assists = this[2].toInt())
        })
}