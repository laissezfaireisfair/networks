package and.parser

import and.parser.webScheme.toMatch

fun main() {
    val playerId = 403153826
    Parser.getMatchEntries(playerId).forEach { println(it) }
}