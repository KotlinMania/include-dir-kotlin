# include-dir-kotlin in Kotlin

[![GitHub link](https://img.shields.io/badge/GitHub-KotlinMania%2Finclude--dir--kotlin-blue.svg)](https://github.com/KotlinMania/include-dir-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/include-dir-kotlin)](https://central.sonatype.com/artifact/io.github.kotlinmania/include-dir-kotlin)
[![Build status](https://img.shields.io/github/actions/workflow/status/KotlinMania/include-dir-kotlin/ci.yml?branch=main)](https://github.com/KotlinMania/include-dir-kotlin/actions)

This is a Kotlin Multiplatform line-by-line transliteration port of [`Michael-F-Bryan/include_dir`](https://github.com/Michael-F-Bryan/include_dir).

**Original Project:** This port is based on [`Michael-F-Bryan/include_dir`](https://github.com/Michael-F-Bryan/include_dir). All design credit and project intent belong to the upstream authors; this repository is a faithful port to Kotlin Multiplatform with no behavioural changes intended.

### Porting status

This is an **in-progress port**. The goal is feature parity with the upstream Rust crate while providing a native Kotlin Multiplatform API. Every Kotlin file carries a `// port-lint: source <path>` header naming its upstream Rust counterpart so the AST-distance tool can track provenance.

---

## Upstream README — `Michael-F-Bryan/include_dir`

> The text below is reproduced and lightly edited from [`https://github.com/Michael-F-Bryan/include_dir`](https://github.com/Michael-F-Bryan/include_dir). It is the upstream project's own description and remains under the upstream authors' authorship; links have been rewritten to absolute upstream URLs so they continue to resolve from this repository.

## include_dir

[![Continuous Integration](https://github.com/Michael-F-Bryan/include_dir/actions/workflows/main.yml/badge.svg)](https://github.com/Michael-F-Bryan/include_dir/actions/workflows/main.yml)
[![license](https://img.shields.io/github/license/Michael-F-Bryan/include_dir.svg)](./LICENSE)
[![Crates.io](https://img.shields.io/crates/v/include_dir.svg)](https://crates.io/crates/include_dir)
[![Docs.rs](https://docs.rs/include_dir/badge.svg)](https://docs.rs/include_dir/)

An evolution of the `include_str!()` and `include_bytes!()` macros for embedding
an entire directory tree into your binary.

Rendered Documentation:

- [master](https://michael-f-bryan.github.io/include_dir)
- [Latest Release](https://docs.rs/include_dir/)

## Getting Started

The `include_dir!()` macro works very similarly to the normal `include_str!()`
and `include_bytes!()` macros. You pass the macro a file path and assign the
returned value to some `static` variable.

```rust
use include_dir::{include_dir, Dir};

static PROJECT_DIR: Dir = include_dir!("$CARGO_MANIFEST_DIR");

// of course, you can retrieve a file by its full path
let lib_rs = PROJECT_DIR.get_file("src/lib.rs").unwrap();

// you can also inspect the file's contents
let body = lib_rs.contents_utf8().unwrap();
assert!(body.contains("SOME_INTERESTING_STRING"));

// you can search for files (and directories) using glob patterns
#[cfg(feature = "glob")]
{
    let glob = "**/*.rs";
    for entry in PROJECT_DIR.find(glob).unwrap() {
        println!("Found {}", entry.path().display());
    }
}
```

## Features

- Embed a directory tree into your binary at compile time
- Find a file in the embedded directory
- Search for files using a glob pattern (requires the `globs` feature)
- File metadata (requires the `metadata` feature)

To-Do list:

- Compression?

---

## About this Kotlin port

### Installation

```kotlin
dependencies {
    implementation("io.github.kotlinmania:include-dir-kotlin:0.1.0-SNAPSHOT")
}
```

### Building

```bash
./gradlew build
./gradlew test
```

### Targets

- macOS arm64
- Linux x64
- Windows mingw-x64
- iOS arm64 / simulator-arm64 (Swift export + XCFramework)
- JS (browser + Node.js)
- Wasm-JS (browser + Node.js)
- Android (API 24+)

### Porting guidelines

See [AGENTS.md](AGENTS.md) and [CLAUDE.md](CLAUDE.md) for translator discipline, port-lint header convention, and Rust → Kotlin idiom mapping.

### License

This Kotlin port is distributed under the same MIT license as the upstream [`Michael-F-Bryan/include_dir`](https://github.com/Michael-F-Bryan/include_dir). See [LICENSE](LICENSE) (and any sibling `LICENSE-*` / `NOTICE` files mirrored from upstream) for the full text.

Original work copyrighted by the include_dir authors.  
Kotlin port: Copyright (c) 2026 Sydney Renee and The Solace Project.

### Acknowledgments

Thanks to the [`Michael-F-Bryan/include_dir`](https://github.com/Michael-F-Bryan/include_dir) maintainers and contributors for the original Rust implementation. This port reproduces their work in Kotlin Multiplatform; bug reports about upstream design or behavior should go to the upstream repository.
