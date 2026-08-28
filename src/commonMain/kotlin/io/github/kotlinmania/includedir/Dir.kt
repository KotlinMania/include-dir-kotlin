// port-lint: source dir.rs
package io.github.kotlinmania.includedir

import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.write

/**
 * A directory.
 */
data class Dir(
    val path: String,
    val entries: List<DirEntry>,
) {
    /** Get a list of the files in this directory. */
    fun files(): Sequence<File> =
        entries.asSequence().mapNotNull { it.asFile() }

    /** Get a list of the sub-directories inside this directory. */
    fun dirs(): Sequence<Dir> =
        entries.asSequence().mapNotNull { it.asDir() }

    /** Recursively search for a [DirEntry] with a particular path. */
    fun getEntry(path: String): DirEntry? {
        for (entry in entries) {
            if (entry.path() == path) {
                return entry
            }

            if (entry is DirEntry.Dir) {
                val nested = entry.value.getEntry(path)
                if (nested != null) {
                    return nested
                }
            }
        }

        return null
    }

    /** Look up a file by name. */
    fun getFile(path: String): File? = getEntry(path)?.asFile()

    /** Look up a dir by name. */
    fun getDir(path: String): Dir? = getEntry(path)?.asDir()

    /** Does this directory contain `path`? */
    fun contains(path: String): Boolean = getEntry(path) != null

    /**
     * Create directories and extract all files to real filesystem.
     *
     * Creates parent directories of `basePath` if they do not already exist.
     * Fails if some files already exist.
     * In case of error, partially extracted directory may remain on the filesystem.
     */
    fun extract(basePath: Path) {
        for (entry in entries) {
            val target = Path(basePath.toString(), entry.path())

            when (entry) {
                is DirEntry.Dir -> {
                    SystemFileSystem.createDirectories(target)
                    entry.value.extract(basePath)
                }
                is DirEntry.File -> {
                    target.parent?.let { SystemFileSystem.createDirectories(it) }
                    SystemFileSystem.sink(target).buffered().use { sink ->
                        sink.write(entry.value.contents)
                    }
                }
            }
        }
    }

    companion object {
        /** Create a new [Dir]. */
        fun new(path: String, entries: List<DirEntry>): Dir = Dir(path, entries)
    }
}
