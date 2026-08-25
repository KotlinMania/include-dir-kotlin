// port-lint: source file.rs
package io.github.kotlinmania.includedir

/**
 * A file with its contents stored in a [ByteArray].
 */
class File(
    /**
     * The full path for this [File], relative to the directory passed to
     * the embedding entry point.
     */
    val path: String,
    /** The file's raw contents. */
    val contents: ByteArray,
    /**
     * The [Metadata] associated with this [File], if available.
     */
    val metadata: Metadata? = null,
) {
    /** The file's contents interpreted as a string. */
    fun contentsUtf8(): String? =
        try {
            contents.decodeToString(throwOnInvalidSequence = true)
        } catch (_: CharacterCodingException) {
            null
        }

    /** Set the [Metadata] associated with a [File]. */
    fun withMetadata(metadata: Metadata): File = File(path, contents, metadata)

    /** Formats debug output for [File]. */
    fun fmt(): String = toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is File) return false
        return path == other.path &&
            contents.contentEquals(other.contents) &&
            metadata == other.metadata
    }

    override fun hashCode(): Int {
        var result = path.hashCode()
        result = 31 * result + contents.contentHashCode()
        result = 31 * result + (metadata?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String =
        "File(path=$path, contents=<${contents.size} bytes>, metadata=$metadata)"

    companion object {
        /** Create a new [File]. */
        fun new(path: String, contents: ByteArray): File = File(path, contents)
    }
}
