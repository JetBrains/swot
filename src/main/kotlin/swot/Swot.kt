package swot

import java.io.File
import java.net.URI

fun isAcademic(email: String): Boolean {
    val parts = domainParts(email)
    return !isStoplisted(parts) && (isUnderTLD(parts) || findSchoolNames(parts).isNotEmpty())
}

fun findSchoolNames(emailOrDomain: String): List<String> {
    return findSchoolNames(domainParts(emailOrDomain))
}

fun isUnderTLD(parts: List<String>): Boolean {
    return checkSet(Resources.tlds, parts)
}

fun isStoplisted(parts: List<String>): Boolean {
    return checkSet(Resources.stoplist, parts)
}

private object Resources {
    val tlds = readList("/tlds.txt") ?: error("Cannot find /tlds.txt")
    val stoplist = readList("/stoplist.txt") ?: error("Cannot find /stoplist.txt")

    fun readList(resource: String) : Set<String>? {
        return File("lib/domains/$resource").takeIf { it.exists() }?.bufferedReader()?.lineSequence()?.toHashSet()
    }
}

private fun findSchoolNames(parts: List<String>): List<String> {
    val resourcePath = StringBuilder("")
    for (part in parts) {
        resourcePath.append('/').append(part)
        val school = Resources.readList("${resourcePath}.txt")
        if (school != null) {
            return school.toList()
        }
    }

    return arrayListOf()
}

private fun domainParts(emailOrDomain: String): List<String> {
    val input = emailOrDomain.trim().lowercase()
    val domain = if ("://" in input) {
        // Only the host identifies a URL's domain; paths and queries may contain '@'.
        runCatching { URI(input).host }.getOrNull().orEmpty()
    } else {
        input.substringAfter('@').substringBefore(':')
    }
    return domain.split('.').reversed()
}

internal fun checkSet(set: Set<String>, parts: List<String>): Boolean {
    val subj = StringBuilder()
    for (part in parts) {
        subj.insert(0, part)
        if (set.contains(subj.toString())) return true
        subj.insert(0 ,'.')
    }
    return false
}
