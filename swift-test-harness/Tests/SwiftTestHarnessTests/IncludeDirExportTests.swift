import Testing
import IncludeDir

@Suite("IncludeDir Export Tests")
struct IncludeDirExportTests {
    @Test("Swift module loads and imports cleanly")
    func swiftModuleLoads() {
        #expect(Bool(true))
    }
}
