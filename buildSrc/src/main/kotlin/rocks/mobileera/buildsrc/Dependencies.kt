package rocks.mobileera.buildsrc

object Dependencies {


    object Versions {
        const val Kotlin = "1.3.50"

        const val AndroidX = "1.1.0"
        const val AndroidXRecyclerView = "1.0.0"
        const val AndroidToolsPlugin = "3.5.1"

        const val RxJava = "2.2.12"
        const val RxAndroid = "2.1.1"
        const val JUnit4 = "4.12"

    }

    object Libraries {

        const val Kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Kotlin}"

        @JvmField
        val AndroidX = arrayOf(
                "androidx.appcompat:appcompat:${Versions.AndroidX}",
                "androidx.annotation:annotation:${Versions.AndroidX}",
                "androidx.recyclerview:recyclerview:${Versions.AndroidXRecyclerView}"
        )

        @JvmField
        val Rx = arrayOf(
                "io.reactivex.rxjava2:rxjava:${Versions.RxJava}",
                "io.reactivex.rxjava2:rxandroid:${Versions.RxAndroid}"
        )

        const val JUnit4 = "junit:junit:${Versions.JUnit4}"
    }

    object ClassPaths {
        const val AndroidTools = "com.android.tools.build:gradle:${Versions.AndroidToolsPlugin}"
        const val Kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin}"
    }

}
