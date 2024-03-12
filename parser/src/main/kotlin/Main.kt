package and.parser

import and.parser.webScheme.MatchEntry
import and.parser.webScheme.toMatch

fun main() {
    listOf<MatchEntry>().map { it.toMatch() }.forEach { println(it) }
}