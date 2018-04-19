package fr.androidmakers.buildsrc.plugins

import com.android.build.gradle.tasks.GenerateResValues
import fr.androidmakers.buildsrc.tasks.DownloadStrings

import org.gradle.api.Plugin
import org.gradle.api.Project

class RemoteL10n : Plugin<Project> {

    companion object {
        const val EXTENSION_NAME = "remoteL10n"
        const val TASK_NAME = "downloadRemoteStrings"
    }

    override fun apply(project: Project?) {
        if (project == null) return

        val remoteL10nExtension = project.extensions.create(EXTENSION_NAME, RemoteL10nExtension::class.java)
        remoteL10nExtension.variants = project.container(VariantL10n::class.java)

        val downloadTask = project.tasks.create(TASK_NAME, DownloadStrings::class.java)
                .apply {
                    projectBasePath = project.projectDir.path
                    configuration = remoteL10nExtension
                }

        project.afterEvaluate {
            it.tasks.withType(GenerateResValues::class.java) {
                it.dependsOn(TASK_NAME)
            }
        }
    }
}