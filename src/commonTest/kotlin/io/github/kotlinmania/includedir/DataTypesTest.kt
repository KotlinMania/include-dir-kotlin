// port-lint: ignore
// Additional smoke tests for the ported data types.
package io.github.kotlinmania.includedir

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class DataTypesTest {
    private fun sampleTree(): Dir {
        val libRs = File("src/lib.rs", "fn main() {}".encodeToByteArray())
        val readme = File("README.md", "# example".encodeToByteArray())
        val srcDir = Dir("src", listOf(DirEntry.File(libRs)))
        return Dir(
            "",
            listOf(
                DirEntry.Dir(srcDir),
                DirEntry.File(readme),
            ),
        )
    }

    @Test
    fun getEntryFindsTopLevelFile() {
        val root = sampleTree()
        val entry = root.getEntry("README.md")
        assertNotNull(entry)
        assertEquals("README.md", entry.path())
    }

    @Test
    fun getEntryRecursesIntoSubdirectories() {
        val root = sampleTree()
        val entry = root.getEntry("src/lib.rs")
        assertNotNull(entry)
        assertEquals("src/lib.rs", entry.path())
    }

    @Test
    fun getFileAndGetDirNarrowResults() {
        val root = sampleTree()
        assertNotNull(root.getFile("src/lib.rs"))
        assertNull(root.getFile("src"))
        assertNotNull(root.getDir("src"))
        assertNull(root.getDir("src/lib.rs"))
    }

    @Test
    fun containsAgreesWithGetEntry() {
        val root = sampleTree()
        assertTrue(root.contains("src/lib.rs"))
        assertTrue(root.contains("README.md"))
        assertFalse(root.contains("missing.txt"))
    }

    @Test
    fun filesAndDirsSeparateDirectChildren() {
        val root = sampleTree()
        val files = root.files().toList().map { it.path }
        val dirs = root.dirs().toList().map { it.path }
        assertEquals(listOf("README.md"), files)
        assertEquals(listOf("src"), dirs)
    }

    @Test
    fun contentsUtf8RoundTripsValidUtf8() {
        val file = File("greeting.txt", "héllo".encodeToByteArray())
        assertEquals("héllo", file.contentsUtf8())
    }

    @Test
    fun contentsUtf8ReturnsNullForInvalidBytes() {
        val invalid = byteArrayOf(0xC3.toByte(), 0x28.toByte())
        val file = File("bad.bin", invalid)
        assertNull(file.contentsUtf8())
        assertContentEquals(invalid, file.contents)
    }

    @Test
    fun withMetadataAttachesMetadataWithoutMutating() {
        val original = File("a.txt", "x".encodeToByteArray())
        val md = Metadata.new(1.seconds, 2.seconds, 3.seconds)
        val updated = original.withMetadata(md)
        assertNull(original.metadata)
        assertEquals(md, updated.metadata)
        assertEquals(original.path, updated.path)
        assertContentEquals(original.contents, updated.contents)
    }

    @Test
    fun dirEntryChildrenExposesDirEntriesAndEmptyForFiles() {
        val root = sampleTree()
        val srcEntry = root.getEntry("src")
        assertNotNull(srcEntry)
        assertEquals(1, srcEntry.children().size)

        val readmeEntry = root.getEntry("README.md")
        assertNotNull(readmeEntry)
        assertTrue(readmeEntry.children().isEmpty())
    }

    @Test
    fun metadataConvertsDurationsRelativeToEpoch() {
        val md = Metadata.new(10.seconds, 20.seconds, 30.seconds)
        assertEquals(10L, md.accessed().epochSeconds)
        assertEquals(20L, md.created().epochSeconds)
        assertEquals(30L, md.modified().epochSeconds)
    }
}
