package fr.androidmakers.buildsrc.plugins

import com.android.build.gradle.AppPlugin
import fr.androidmakers.buildsrc.tasks.DownloadStrings
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

class RemoteL10nTest {

    lateinit var fakeProject: Project

    @Before
    fun setUp() {
        fakeProject = ProjectBuilder.builder().build()

        fakeProject.plugins.apply(AppPlugin::class.java)
        fakeProject.plugins.apply(RemoteL10n::class.java)
        fakeProject.evaluate()
    }

    @Test
    fun addsExtension() {
        val extension = fakeProject.extensions.getByName(RemoteL10n.EXTENSION_NAME)

        assert(extension is RemoteL10nExtension)
    }

    @Test
    fun addsTask() {
        val task = fakeProject.tasks.getByName(RemoteL10n.TASK_NAME)

        assert(task is DownloadStrings)
    }

    private fun Project.evaluate() {
        try {
            val method = javaClass.methods
                    .filter { it.name == "evaluate" && it.parameterCount == 0 }
                    .firstOrNull() ?: return

            method.invoke(this)
        } catch (e: Exception) {
            // nevermind
        }
    }
}