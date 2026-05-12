// port-lint: source src/metadata.rs
package io.github.kotlinmania.includedir

import kotlin.time.Duration
import kotlin.time.Instant

/**
 * Basic metadata for a file.
 *
 * Upstream Rust derived `Debug, Copy, Clone, PartialEq, Eq` on this type.
 */
data class Metadata(
    /** Time the file was last accessed, as an [Instant] since the unix epoch. */
    val accessed: Instant,
    /** Time the file was created, as an [Instant] since the unix epoch. */
    val created: Instant,
    /** Time the file was last modified, as an [Instant] since the unix epoch. */
    val modified: Instant,
) {
    /**
     * Create a new [Metadata] using the [Duration] elapsed since the unix epoch.
     */
    constructor(
        accessed: Duration,
        created: Duration,
        modified: Duration,
    ) : this(
        EPOCH + accessed,
        EPOCH + created,
        EPOCH + modified,
    )

    private companion object {
        private val EPOCH: Instant = Instant.fromEpochSeconds(0L)
    }
}
