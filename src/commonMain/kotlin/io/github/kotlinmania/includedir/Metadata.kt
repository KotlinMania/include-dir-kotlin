// port-lint: source src/metadata.rs
package io.github.kotlinmania.includedir

import kotlin.time.Duration
import kotlin.time.Instant

/**
 * Basic metadata for a file.
 */
data class Metadata(
    private val accessedSinceEpoch: Duration,
    private val createdSinceEpoch: Duration,
    private val modifiedSinceEpoch: Duration,
) {
    /**
     * Get the time this file was last accessed.
     */
    fun accessed(): Instant = EPOCH + accessedSinceEpoch

    /**
     * Get the time this file was created.
     */
    fun created(): Instant = EPOCH + createdSinceEpoch

    /**
     * Get the time this file was last modified.
     */
    fun modified(): Instant = EPOCH + modifiedSinceEpoch

    companion object {
        private val EPOCH: Instant = Instant.fromEpochSeconds(0L)

        /**
         * Create a new [Metadata] using the number of seconds since the Unix epoch.
         */
        fun new(accessed: Duration, created: Duration, modified: Duration): Metadata =
            Metadata(accessed, created, modified)
    }
}
