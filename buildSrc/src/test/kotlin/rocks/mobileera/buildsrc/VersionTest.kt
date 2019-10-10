package rocks.mobileera.buildsrc

import org.junit.Test

class VersionTest {

    @Test(expected = IllegalArgumentException::class)
    fun checkMajorInRange() {
        Version(Version.MAX_MAJOR, 0, 0, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun checkMinorInRange() {
        Version(0, Version.MAX_MINOR, 0, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun checkHotfixInRange() {
        Version(0, 0, Version.MAX_HOTFIX, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun checkBuildInRange() {
        Version(0, 0, 0, Version.MAX_BUILD)
    }

    @Test
    fun computesName() {
        val name = Version(3, 12, 7, 4).name
        assert(name == "3.12.7.4")
    }

    @Test
    fun computesNameWithSuffix() {
        val name = Version(3, 12, 7, 4, "rc").name
        assert(name == "3.12.7.4-rc")
    }

    @Test
    fun computesCode() {
        val code = Version(3, 12, 7, 4).code

        assert(code == 31274) { "expected code to be 31274 but was $code" }
    }


    @Test
    fun ensureCodeSequenceBuild() {
        val code = Version(3, 12, 7, Version.MAX_BUILD - 1).code
        val next = Version(3, 12, 8, 0).code

        assert(code == next - 1) { "expected code to be next - 1 = ${next - 1} but was $code (@next:$next)" }
    }

    @Test
    fun ensureCodeSequenceHotfix() {
        val code = Version(3, 12, Version.MAX_HOTFIX - 1, Version.MAX_BUILD - 1).code
        val next = Version(3, 13, 0, 0).code

        assert(code == next - 1) { "expected code to be next - 1 = ${next - 1} but was $code (@next:$next)" }
    }

    @Test
    fun ensureCodeSequenceMinor() {
        val code = Version(3, Version.MAX_MINOR - 1, Version.MAX_HOTFIX - 1, Version.MAX_BUILD - 1).code
        val next = Version(4, 0, 0, 0).code

        assert(code == next - 1) { "expected code to be next - 1 = ${next - 1} but was $code (@next:$next)" }
    }
}
