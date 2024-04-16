package and.parser

import and.parser.webScheme.toMatch

fun main() {
    val playerId = 403153826
    Parser.getMatchEntries(playerId)
        .mapNotNull {
            try {
                it.toMatch()
            } catch (exception: Exception) {
                println(exception)
                null
            }
        }
        .forEach { println(it) }
}