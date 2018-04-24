package fr.androidmakers.buildsrc.plugins

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.internal.tasks.AppPreBuildTask
import fr.androidmakers.buildsrc.tasks.StrikerManifestTask
import fr.androidmakers.buildsrc.tasks.StrikerResourcesTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class Striker : Plugin<Project> {


    companion object {
        const val MANIFEST_TASK_NAME = "generateManifest"
        const val LAYOUT_TASK_NAME = "generateLayout"
    }


    override fun apply(project: Project?) {
        if (project == null) return

        val manifestFilePath = "${project.projectDir.path}/src/main/AndroidManifest.xml"
        project.tasks.create(MANIFEST_TASK_NAME, StrikerManifestTask::class.java).apply {
            manifestPath = manifestFilePath
        }

        val genResDirPath = "${project.buildDir.path}/generated/res/striker/"
        project.tasks.create(LAYOUT_TASK_NAME, StrikerResourcesTask::class.java).apply {
            genPath = genResDirPath
        }


        project.afterEvaluate {
            it.tasks.withType(AppPreBuildTask::class.java) {
                it.dependsOn(MANIFEST_TASK_NAME)
                it.dependsOn(LAYOUT_TASK_NAME)
            }

            addResSrcDir(project, genResDirPath)
        }
    }

    private fun addResSrcDir(project: Project, srcDir: String) {
        val extension = project.plugins.findPlugin(AppPlugin::class.java)?.extension
                ?: project.plugins.findPlugin(AppPlugin::class.java)?.extension
                ?: throw IllegalStateException("Hey ! This is not an Android application or library project !")

        extension.sourceSets
                .findByName("main")
                ?.res
                ?.srcDirs(srcDir)
    }
}


