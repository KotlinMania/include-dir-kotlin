// port-lint: source dir_entry.rs
package io.github.kotlinmania.includedir

/**
 * A directory entry, roughly analogous to a filesystem directory entry.
 */
sealed class DirEntry {
    /** A directory. */
    data class Dir(
        val value: io.github.kotlinmania.includedir.Dir,
    ) : DirEntry()

    /** A file. */
    data class File(
        val value: io.github.kotlinmania.includedir.File,
    ) : DirEntry()

    /** The [DirEntry]'s full path. */
    fun path(): String =
        when (this) {
            is Dir -> value.path()
            is File -> value.path()
        }

    /** Try to get this as a [Dir][io.github.kotlinmania.includedir.Dir], if it is one. */
    fun asDir(): io.github.kotlinmania.includedir.Dir? = (this as? Dir)?.value

    /** Try to get this as a [File][io.github.kotlinmania.includedir.File], if it is one. */
    fun asFile(): io.github.kotlinmania.includedir.File? = (this as? File)?.value

    /** Get this item's sub-items, if it has any. */
    fun children(): List<DirEntry> =
        when (this) {
            is Dir -> value.entries()
            is File -> emptyList()
        }
}
