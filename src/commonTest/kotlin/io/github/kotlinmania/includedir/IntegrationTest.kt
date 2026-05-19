// port-lint: source tests/integration_test.rs
package io.github.kotlinmania.includedir

import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal val parentDir = Dir(
    "",
    listOf(
        DirEntry.File(File("Cargo.toml", "rust-version = \"1.64\"\n".encodeToByteArray())),
        DirEntry.Dir(
            Dir(
                "src",
                listOf(
                    DirEntry.File(File("src/lib.rs", "pub use crate::dir::Dir;\n".encodeToByteArray())),
                ),
            ),
        ),
        DirEntry.Dir(
            Dir(
                "tests",
                listOf(
                    DirEntry.File(File("tests/integration_test.rs", "use include_dir::{include_dir, Dir};\n".encodeToByteArray())),
                ),
            ),
        ),
    ),
)

class IntegrationTest {
    @Test
    fun includedAllFilesInTheIncludeDirCrate() {
        val root = listOf(
            "Cargo.toml",
            "src",
            "src/lib.rs",
            "tests",
            "tests/integration_test.rs",
        )

        validateIncluded(parentDir, root)
        assertTrue(parentDir.contains("src/lib.rs"))
    }

    @Test
    fun extractAllFiles() {
        val root = Path("build/tmp/include-dir-kotlin/integration-test")
        removeExtracted(root)
        SystemFileSystem.createDirectories(root)

        parentDir.extract(root)

        validateExtracted(parentDir, root)
        assertEquals(
            "pub use crate::dir::Dir;\n",
            SystemFileSystem.source(Path(root.toString(), "src/lib.rs")).buffered().use { it.readString() },
        )
    }
}

internal fun validateIncluded(dir: Dir, paths: List<String>) {
    for (entry in paths) {
        assertTrue(dir.contains(entry), "Can't find $entry")

        val dirEntry = dir.getEntry(entry)
        assertNotNull(dirEntry)
        val childDir = dirEntry.asDir()
        if (childDir != null) {
            validateIncluded(
                childDir,
                paths.filter { it.startsWith("${childDir.path}/") },
            )
        }
    }
}

private fun validateExtracted(dir: Dir, path: Path) {
    for (subdir in dir.dirs()) {
        val subdirPath = Path(path.toString(), dir.path)
        assertTrue(SystemFileSystem.exists(subdirPath))
        validateExtracted(subdir, subdirPath)
    }

    for (file in dir.files()) {
        val filePath = Path(path.toString(), file.path)
        assertTrue(SystemFileSystem.exists(filePath))
    }
}

private fun removeExtracted(root: Path) {
    SystemFileSystem.delete(Path(root.toString(), "src/lib.rs"), mustExist = false)
    SystemFileSystem.delete(Path(root.toString(), "tests/integration_test.rs"), mustExist = false)
    SystemFileSystem.delete(Path(root.toString(), "Cargo.toml"), mustExist = false)
    SystemFileSystem.delete(Path(root.toString(), "src"), mustExist = false)
    SystemFileSystem.delete(Path(root.toString(), "tests"), mustExist = false)
    SystemFileSystem.delete(root, mustExist = false)
}
