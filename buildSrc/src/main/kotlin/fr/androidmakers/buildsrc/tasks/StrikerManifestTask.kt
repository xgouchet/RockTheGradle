package fr.androidmakers.buildsrc.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

open class StrikerManifestTask : DefaultTask() {

    @OutputFile
    var buildDirPath : String = ""

    @TaskAction
    fun generateManifest() {
        File(buildDirPath).writeText("""
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="fr.androidmakers.rockthegradle">

    <application android:allowBackup="false"
                 android:label="@string/app_name"
                 android:icon="@mipmap/ic_launcher"
                 android:roundIcon="@mipmap/ic_launcher_round"
                 android:supportsRtl="true"
                 android:theme="@style/AppTheme"/>
</manifest>

        """.trimIndent())
    }
}