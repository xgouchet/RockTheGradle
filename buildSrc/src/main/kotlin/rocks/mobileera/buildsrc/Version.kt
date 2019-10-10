package rocks.mobileera.buildsrc

data class Version(
        val major: Int,
        val minor: Int,
        val hotfix: Int,
        val build: Int,
        val suffix: String = ""
) {

    init {
        require(major < MAX_MAJOR) { "The minor component must be smaller than $MAX_MAJOR" }
        require(minor < MAX_MINOR) { "The minor component must be smaller than $MAX_MINOR" }
        require(hotfix < MAX_HOTFIX) { "The hotfix component must be smaller than $MAX_HOTFIX" }
        require(build < MAX_BUILD) { "The build component must be smaller than $MAX_BUILD" }
    }

    val name: String
        get() {
            return if (suffix.isBlank()) {
                "$major.$minor.$hotfix.$build"
            } else {
                "$major.$minor.$hotfix.$build-$suffix"
            }
        }


    val code: Int
        get() {
            val hfPart = hotfix * MAX_BUILD
            val minPart = minor * MAX_HOTFIX * MAX_BUILD
            val majPart = major * MAX_MINOR * MAX_HOTFIX * MAX_BUILD

            return build + hfPart + minPart + majPart
        }

    companion object {
        internal const val MAX_BUILD = 10
        internal const val MAX_HOTFIX = 10
        internal const val MAX_MINOR = 100
        internal const val MAX_MAJOR = 100
    }
}

