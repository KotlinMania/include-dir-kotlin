// port-lint: source src/lib.rs
package io.github.kotlinmania.includedir

/*
 * Crate-level documentation translated from upstream src/lib.rs.
 *
 * An extension to embed an entire directory tree into your binary, paired
 * with a compile-time entry point that walks a directory and emits a [Dir]
 * literal at build time. The runtime API surface is the [Dir], [DirEntry],
 * and [File] data types declared alongside this file. The compile-time
 * generator that produces those literals is a separate concern from this
 * crate (upstream Rust delegates it to a procedural macro
 * `include_dir_macros::include_dir!`).
 *
 * # Examples
 *
 * Once a [Dir] tree has been built (either in code or by an external
 * generator), it can be queried like a virtual filesystem:
 *
 * ```kotlin
 * val projectDir: Dir = /* construct or load */
 *
 * // retrieve a file by its full path
 * val libRs = projectDir.getFile("src/lib.rs") ?: error("missing")
 *
 * // inspect the file's contents
 * val body = libRs.contentsUtf8() ?: error("not utf-8")
 * check("SOME_INTERESTING_STRING" in body)
 * ```
 *
 * # Compile time considerations
 *
 * Embedding a large number of files or files which are particularly big may
 * cause the compiler/build tool to use large amounts of RAM and to spend a
 * long time parsing the generated literal expressions.
 */

// Re-exports from upstream src/lib.rs:
//   pub use crate::dir::Dir;
//   pub use crate::dir_entry::DirEntry;
//   pub use crate::file::File;
//   pub use crate::metadata::Metadata;            // upstream feature = "metadata"
//   pub use include_dir_macros::include_dir;      // procedural macro, not yet ported
//
// In Kotlin these symbols already live in io.github.kotlinmania.includedir;
// callers import them directly from this package and no central typealias is
// required.
//
// Callers migrated:
//   (none yet)

// This file tracks the upstream src/lib.rs module structure.
// Upstream re-exports are satisfied by direct imports from this package.
internal const val LIB_MODULE = "include_dir"
