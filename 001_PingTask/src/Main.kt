import java.io.File

val domains = listOf(
    "google.com",
    "ya.ru",
    "vk.com",
    "gitlab.com",
    "nsu.ru",
    "notion.so",
    "yandex.ru",
    "trello.com",
    "github.com",
    "longstoryshort.app"
)

fun pingOutput(domain: String) =
    Runtime.getRuntime().exec(arrayOf("ping", "-c", "1", domain)).inputReader().lineSequence().toList()

data class PingResult(
    val domain:String, val loss: Double, val minTimeMs: Double, val avgTimeMs: Double, val maxTimeMs: Double
) {
    companion object {
        private val headerLineRegex = """PING ([\w.]+) """.toRegex()
        private val lossStatsLineRegex = """(\d+)% packet loss""".toRegex()
        private val timeStatsLineRegex = """([\d.]+)/([\d.]+)/([.\d]+)/[\d.]+ ms""".toRegex()

        fun parse(pingOutputLines: List<String>): PingResult {
            val domain = pingOutputLines.firstNotNullOf { headerLineRegex.find(it) }.groups[1]!!.value
            val loss = pingOutputLines.firstNotNullOf { lossStatsLineRegex.find(it) }.groups[1]!!.value.toDouble()
            val timeStatsMatch = pingOutputLines.firstNotNullOf { timeStatsLineRegex.find(it) }
            return PingResult(
                domain = domain,
                loss = loss,
                minTimeMs = timeStatsMatch.groups[1]!!.value.toDouble(),
                avgTimeMs = timeStatsMatch.groups[2]!!.value.toDouble(),
                maxTimeMs = timeStatsMatch.groups[3]!!.value.toDouble(),
            )
        }

        fun listToCsv(results: Sequence<PingResult>): Sequence<String> = sequence {
            yield("domain, loss, minTimeMs, avgTimeMs, maxTimeMs")
            yieldAll(results.map { "${it.domain}, ${it.loss}%, ${it.minTimeMs}, ${it.avgTimeMs}, ${it.maxTimeMs}" })
        }
    }
}

fun main() {
    with (File("pingResults.csv")) {
        writeText("")
        domains.asSequence().map { PingResult.parse(pingOutput(it)) }.let { PingResult.listToCsv(it) }
            .forEach { appendText("$it\n") }
    }
}