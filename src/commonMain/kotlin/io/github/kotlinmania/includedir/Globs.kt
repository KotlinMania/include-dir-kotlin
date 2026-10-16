// port-lint: source globs.rs
package io.github.kotlinmania.includedir

import io.github.kotlinmania.glob.Pattern

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

            if (pattern.matches(item.path())) {
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
