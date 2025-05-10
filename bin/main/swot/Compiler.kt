package swot

import java.io.File

/**
 * @author max
 */

object CompilationState {
    val stoplist = File("lib/domains/stoplist.txt").readLines().toHashSet()
    val domains = File("lib/domains/tlds.txt").readLines().toHashSet()
}

fun main() {
    val root = File("lib/domains")
    root.walkTopDown().forEach {
        if (it.isFile) {
            val parts = it.toRelativeString(root).replace('\\', '/').removeSuffix(".txt").split('/').toList()
            if (!checkSet(CompilationState.stoplist, parts) && !checkSet(CompilationState.domains, parts)) {
                CompilationState.domains.add(parts.reversed().joinToString("."))
            }
        }
    }

    val stoplist = CompilationState.stoplist.map { "-$it" }.sorted().joinToString("\n")
    File("out/artifacts").mkdirs()
    File("out/artifacts/swot.txt").writeText(stoplist + "\n" + CompilationState.domains.sorted().joinToString("\n"))
}
