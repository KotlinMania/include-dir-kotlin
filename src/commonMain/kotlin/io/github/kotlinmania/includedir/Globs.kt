// port-lint: source globs.rs
package io.github.kotlinmania.includedir

/**
 * Type alias matching upstream Iterator::Item.
 */
typealias Item = DirEntry

/**
 * Search for a file or directory with a glob pattern.
 */
fun Dir.find(glob: String): Sequence<DirEntry> {
    val pattern = Pattern.new(glob)
    return Sequence { Globs.new(pattern, this) }
}

/**
 * An iterator over directory entries matching a glob [Pattern].
 */
class Globs internal constructor(
    private val stack: MutableList<DirEntry>,
    private val pattern: Pattern,
) : Iterator<DirEntry> {
    private var nextItem: DirEntry? = null

    override fun hasNext(): Boolean {
        if (nextItem != null) return true
        nextItem = computeNext()
        return nextItem != null
    }

    override fun next(): DirEntry {
        if (!hasNext()) throw NoSuchElementException()
        val item = nextItem ?: throw NoSuchElementException()
        nextItem = null
        return item
    }

    private fun computeNext(): DirEntry? {
        while (stack.isNotEmpty()) {
            val item = stack.removeAt(stack.size - 1)
            stack.addAll(item.children())

            if (pattern.matchesPath(item.path())) {
                return item
            }
        }
        return null
    }

    companion object {
        fun new(pattern: Pattern, root: Dir): Globs {
            val stack = root.entries().toMutableList()
            return Globs(stack, pattern)
        }
    }
}

/**
 * A glob pattern error.
 */
class PatternError(
    val pos: Int,
    val msg: String,
) : IllegalArgumentException("Pattern syntax error near position $pos: $msg")

/**
 * A compiled Unix shell style pattern for matching paths.
 */
class Pattern internal constructor(
    private val original: String,
    private val regex: Regex,
) {
    /** Access the original glob pattern. */
    fun asStr(): String = original

    /** Returns whether the given [path] matches this [Pattern]. */
    fun matchesPath(path: String): Boolean {
        val normalized = path.replace('\\', '/')
        return regex.matches(normalized)
    }

    override fun toString(): String = original

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Pattern) return false
        return original == other.original
    }

    override fun hashCode(): Int = original.hashCode()

    companion object {
        /**
         * Compiles a Unix shell style glob pattern.
         */
        fun new(pattern: String): Pattern {
            val regexStr = buildRegex(pattern)
            return Pattern(pattern, Regex(regexStr))
        }

        private fun buildRegex(pattern: String): String {
            val sb = StringBuilder()
            sb.append("^")
            var i = 0
            val len = pattern.length
            while (i < len) {
                val c = pattern[i]
                when (c) {
                    '*' -> {
                        if (i + 1 < len && pattern[i + 1] == '*') {
                            // ** recursive wildcard
                            var j = i + 2
                            while (j < len && pattern[j] == '*') j++
                            if (j - i > 2) {
                                throw PatternError(i + 2, "wildcards are either regular `*` or recursive `**`")
                            }
                            val isAtStartOrSlash = (i == 0 || pattern[i - 1] == '/' || pattern[i - 1] == '\\')
                            val isAtEndOrSlash = (j == len || pattern[j] == '/' || pattern[j] == '\\')
                            if (!isAtStartOrSlash || !isAtEndOrSlash) {
                                throw PatternError(i, "recursive wildcards must form a single path component")
                            }
                            if (j < len && (pattern[j] == '/' || pattern[j] == '\\')) {
                                sb.append("(?:.+/)?")
                                i = j + 1
                            } else {
                                sb.append(".*")
                                i = j
                            }
                        } else {
                            sb.append("[^/]*")
                            i++
                        }
                    }
                    '?' -> {
                        sb.append("[^/]")
                        i++
                    }
                    '[' -> {
                        val closeIdx = pattern.indexOf(']', i + 1)
                        if (closeIdx == -1) {
                            throw PatternError(i, "invalid range pattern")
                        }
                        val inner = pattern.substring(i + 1, closeIdx)
                        if (inner.startsWith("!")) {
                            sb.append("[^").append(Regex.escape(inner.substring(1))).append("]")
                        } else {
                            sb.append("[").append(Regex.escape(inner)).append("]")
                        }
                        i = closeIdx + 1
                    }
                    '/', '\\' -> {
                        sb.append("/")
                        i++
                    }
                    else -> {
                        sb.append(Regex.escape(c.toString()))
                        i++
                    }
                }
            }
            sb.append("$")
            return sb.toString()
        }
    }
}
