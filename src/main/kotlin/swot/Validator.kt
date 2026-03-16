/**
 * Validates domain files under `lib/domains/`.
 *
 * Usage:
 * ```
 *   ./gradlew validate -PvalidateFiles=lib/domains/edu/stanford.txt
 *   ./gradlew validate -PvalidateFiles=lib/domains/edu/stanford.txt,lib/domains/uk/ac/strath.txt
 *   ./gradlew validate -PvalidateFiles=@changed_files.txt
 * ```
 *
 * Prefix with `@` to read paths from a file (one per line).
 * Exits with code 1 if any violations are found.
 */
package swot

import java.io.File
import java.nio.ByteBuffer
import java.nio.charset.CodingErrorAction
import kotlin.system.exitProcess

private const val DOMAIN_ROOT = "lib/domains"
private val BLOCKLISTS = listOf("stoplist.txt", "abused.txt")
private val CONTROL_FILES = setOf("tlds.txt") + BLOCKLISTS

data class Violation(val file: String, val reason: String)

private fun pathToDomain(path: String): String =
    path.removePrefix("$DOMAIN_ROOT/")
        .removeSuffix(".txt")
        .split("/")
        .reversed()
        .joinToString(".")

private fun loadBlocklist(): Set<String> =
    BLOCKLISTS
        .flatMap { File("$DOMAIN_ROOT/$it").takeIf { f -> f.exists() }?.readLines().orEmpty() }
        .map { it.trim().lowercase() }
        .filter { it.isNotEmpty() }
        .toSet()

private fun decode(file: File): String? = runCatching {
    Charsets.UTF_8.newDecoder()
        .onMalformedInput(CodingErrorAction.REPORT)
        .onUnmappableCharacter(CodingErrorAction.REPORT)
        .decode(ByteBuffer.wrap(file.readBytes()))
        .toString()
}.getOrNull()

private fun validate(path: String, blocklist: Set<String>): List<Violation> {
    val file = File(path)
    if (!file.exists() || !file.name.endsWith(".txt") || file.name in CONTROL_FILES)
        return emptyList()

    val domain = pathToDomain(path).lowercase()
    if (domain in blocklist)
        return listOf(Violation(path, "Domain '$domain' is blocked (stoplist/abused)"))

    val content = decode(file)
        ?: return listOf(Violation(path, "Invalid UTF-8 encoding"))

    return listOfNotNull(
        Violation(path, "Filename '${file.name}' must be lowercase")
            .takeIf { file.name != file.name.lowercase() },
        Violation(path, "File must contain at least one institution name")
            .takeIf { content.lines().none(String::isNotBlank) },
        Violation(path, "File is not cleanly trimmed (no leading/trailing blank lines or whitespace)")
            .takeIf { content.removeSuffix("\n") != content.removeSuffix("\n").trim() },
    )
}

fun main(args: Array<String>) {
    val paths = args.flatMap { arg ->
        if (arg.startsWith("@")) File(arg.drop(1)).readLines().filter(String::isNotBlank)
        else listOf(arg)
    }

    if (paths.isEmpty()) {
        println("No files to validate.")
        return
    }

    val blocklist = loadBlocklist()
    val violations = paths.flatMap { validate(it, blocklist) }

    violations.forEach { println("::error file=${it.file}::${it.reason}") }

    if (violations.isNotEmpty()) {
        println("\n${violations.size} violation(s) found.")
        exitProcess(1)
    }

    println("All ${paths.size} file(s) passed validation.")
}
