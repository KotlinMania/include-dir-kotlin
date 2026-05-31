# port-lint Proposed Changes

**Generated:** 2026-05-19
**Source:** tmp/include_dir/src
**Target:** src/commonMain/kotlin/io/github/kotlinmania/includedir

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonMain/kotlin/io/github/kotlinmania/includedir/Metadata.kt` | `// port-lint: source src/metadata.rs` | `// port-lint: source metadata.rs` | `metadata.rs` | `port-lint provenance header matched only after fallback normalization: 'src/metadata.rs' vs expected 'metadata.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/includedir/File.kt` | `// port-lint: source src/file.rs` | `// port-lint: source file.rs` | `file.rs` | `port-lint provenance header matched only after fallback normalization: 'src/file.rs' vs expected 'file.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/includedir/Dir.kt` | `// port-lint: source src/dir.rs` | `// port-lint: source dir.rs` | `dir.rs` | `port-lint provenance header matched only after fallback normalization: 'src/dir.rs' vs expected 'dir.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/includedir/Lib.kt` | `// port-lint: source src/lib.rs` | `// port-lint: source lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'src/lib.rs' vs expected 'lib.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/includedir/DirEntry.kt` | `// port-lint: source src/dir_entry.rs` | `// port-lint: source dir_entry.rs` | `dir_entry.rs` | `port-lint provenance header matched only after fallback normalization: 'src/dir_entry.rs' vs expected 'dir_entry.rs'` |
