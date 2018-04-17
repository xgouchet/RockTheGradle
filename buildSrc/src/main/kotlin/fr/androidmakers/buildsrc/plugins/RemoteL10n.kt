package fr.androidmakers.buildsrc.plugins

import com.android.build.gradle.tasks.GenerateResValues
import fr.androidmakers.buildsrc.tasks.DownloadStrings

import org.gradle.api.Plugin
import org.gradle.api.Project

class RemoteL10n : Plugin<Project> {

    override fun apply(project: Project?) {
        if (project == null) return

        val remoteL10nExtension = project.extensions.create("remoteL10n", RemoteL10nExtension::class.java)

        val downloadTask = project.tasks.create("downloadRemoteStrings", DownloadStrings::class.java).apply {
            projectBasePath = project.projectDir.path
            configuration = remoteL10nExtension
        }

        project.afterEvaluate {
            it.tasks.withType(GenerateResValues::class.java) {
                it.dependsOn("downloadRemoteStrings")
            }
        }
    }
}


