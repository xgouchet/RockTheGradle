package rocks.mobileera.buildsrc.plugin

import com.android.build.gradle.tasks.GenerateResValues
import org.gradle.api.Plugin
import org.gradle.api.Project

class GetStringsPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        val ext = project.extensions
                .create(EXTENSION_NAME, GetStringsExtension::class.java)
        ext.variants =  project.container(VariantLanguages::class.java)

        val task = project.tasks
                .create(TASK_NAME, GetStringsTask::class.java)

        task.apply {
            root = "${project.projectDir.path}/src"
            extension = ext
        }

        project.afterEvaluate { p ->
            p.tasks.withType(GenerateResValues::class.java) {
                it.dependsOn(task)
            }
        }
    }

    companion object {
        internal const val EXTENSION_NAME = "getStrings"
        internal const val TASK_NAME = "getStrings"
    }

}
