package fr.androidmakers.buildsrc.plugins

import com.android.build.gradle.internal.tasks.AppPreBuildTask
import fr.androidmakers.buildsrc.tasks.StrikerManifestTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class Striker : Plugin<Project> {


    companion object {
        const val TASK_NAME = "generateManifest"
    }

    override fun apply(project: Project?) {
        if (project == null) return

        val genDir = "${project.projectDir.path}/src/main/AndroidManifest.xml"
        val task = project.tasks.create(TASK_NAME, StrikerManifestTask::class.java).apply {
            buildDirPath = genDir
        }

        project.afterEvaluate {
            it.tasks.withType(AppPreBuildTask::class.java) {
                it.dependsOn(TASK_NAME)
            }
        }
    }
}


