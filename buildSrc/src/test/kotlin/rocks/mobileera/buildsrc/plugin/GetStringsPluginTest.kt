package rocks.mobileera.buildsrc.plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.tasks.GenerateResValues
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class GetStringsPluginTest {

    @Rule
    @JvmField
    val tempFolder = TemporaryFolder()

    private lateinit var fakeProject: Project
    private lateinit var fakeProjectDir: File

    @Before
    fun setUp() {
        fakeProjectDir = tempFolder.newFolder()

        fakeProject = ProjectBuilder.builder()
                .withProjectDir(fakeProjectDir)
                .build()

        fakeProject.plugins.apply(AppPlugin::class.java)
        fakeProject.tasks.create("FakeGenRes", GenerateResValues::class.java)
        fakeProject.plugins.apply(GetStringsPlugin::class.java)
        fakeProject.evaluate()
    }

    @Test
    fun addsExtension() {
        val extension = fakeProject.extensions.getByName(GetStringsPlugin.EXTENSION_NAME)

        assert(extension is GetStringsExtension)
    }


    @Test
    fun addsTask() {
        val task = fakeProject.tasks.getByName(GetStringsPlugin.TASK_NAME)

        assert(task is GetStringsTask)
    }

    @Test
    fun configureTask() {
        val extension = fakeProject.extensions.getByName(GetStringsPlugin.EXTENSION_NAME)
        val task = fakeProject.tasks.getByName(GetStringsPlugin.TASK_NAME)
        // A weird bug on MacOs make the canonicalFile from tempFolder different.
        // https://stackoverflow.com/questions/43808602/temporaryfolder-and-filegetcanonicalfile-on-mac
        val expectedRoot = File(fakeProjectDir.canonicalFile, "src").path

        val getStringsTask = task as? GetStringsTask ?: return

        assert(getStringsTask.root == expectedRoot)
        assert(getStringsTask.extension == extension)
    }

    @Test
    fun setupTaskDependency() {
        val task = fakeProject.tasks.getByName(GetStringsPlugin.TASK_NAME)
        val generateResTasks = fakeProject.tasks.withType(GenerateResValues::class.java)

        generateResTasks.forEach {
            assert(it.dependsOn.contains(task))
        }

    }

    private fun Project.evaluate() {
        try {
            val method = javaClass.methods
                    .firstOrNull { it.name == "evaluate" && it.parameterCount == 0 }
                    ?: return

            method.invoke(this)
        } catch (e: Exception) {
            // nevermind
        }
    }
}