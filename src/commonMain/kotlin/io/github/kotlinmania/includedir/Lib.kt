// port-lint: source lib.rs
package io.github.kotlinmania.includedir

/**
 * Embedded directory tree in binary.
 *
 * An extension to embed an entire directory tree into your binary, paired
 * with an entry point that walks a directory and produces a [Dir]
 * structure. The runtime API surface is the [Dir], [DirEntry],
 * and [File] data types declared alongside this file.
 *
 * # Examples
 *
 * Once a [Dir] tree has been built, it can be queried like a virtual filesystem:
 *
 * ```kotlin
 * val projectDir: Dir = // construct or load
 *
 * // retrieve a file by its full path
 * val libRs = projectDir.getFile("src/lib.rs") ?: error("missing")
 *
 * // inspect the file contents
 * val body = libRs.contentsUtf8() ?: error("not utf-8")
 * check("SOME_INTERESTING_STRING" in body)
 * ```
 *
 * # Features
 *
 * - glob: search for files using glob patterns
 * - metadata: include basic filesystem metadata like last modified time
 */
fun checkReadmeExamples() {
    // Upstream doc-test anchor for README validation.
}
