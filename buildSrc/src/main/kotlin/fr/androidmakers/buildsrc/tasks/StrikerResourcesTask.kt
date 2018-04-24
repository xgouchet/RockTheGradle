package fr.androidmakers.buildsrc.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

open class StrikerResourcesTask : DefaultTask() {


    @OutputDirectory
    var genPath : String = ""

    @TaskAction
    fun generateManifest() {
        val folder = File("$genPath/layout/")
        folder.mkdirs()
        val customXml = File(folder, "test.xml")
        customXml.writeText("""
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
  android:orientation="vertical"
  android:layout_width="fill_parent"
  android:layout_height="fill_parent" >

  <Button
    android:id="@+id/premier"
    android:layout_width="wrap_content"
    android:layout_height="fill_parent"
    android:text="Premier bouton" />

  <Button
    android:id="@+id/second"
    android:layout_width="wrap_content"
    android:layout_height="fill_parent"
    android:text="Second bouton" />
</LinearLayout>

        """.trimIndent())
    }
}