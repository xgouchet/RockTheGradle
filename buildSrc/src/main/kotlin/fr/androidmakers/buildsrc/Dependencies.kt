package fr.androidmakers.buildsrc

object Dependencies {

    object Versions {
        const val Kotlin = "1.2.30"
        const val AndroidSupportLib = "27.1.1"
        const val RxJava = "2.1.8"
        const val RxAndroid = "2.0.1"
        const val JUnit4 = "4.12"

    }

    object Libraries {

        const val KotlinJre7 = "org.jetbrains.kotlin:kotlin-stdlib-jre7:${Versions.Kotlin}"

        @JvmField
        val SupportLibs = arrayOf("com.android.support:appcompat-v7:${Versions.AndroidSupportLib}",
                "com.android.support:design:${Versions.AndroidSupportLib}",
                "com.android.support:support-annotations:${Versions.AndroidSupportLib}")

        @JvmField
        val Rx = arrayOf("io.reactivex.rxjava2:rxjava:${Versions.RxJava}",
                "io.reactivex.rxjava2:rxandroid:${Versions.RxAndroid}")

        const val JUnit4 = "junit:junit:${Versions.JUnit4}"
    }

}