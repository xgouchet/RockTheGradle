package fr.androidmakers.buildsrc.tasks

import com.natpryce.konfig.PropertyGroup
import com.natpryce.konfig.getValue
import com.natpryce.konfig.stringType
import fr.androidmakers.buildsrc.plugins.RemoteL10nExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


open class DownloadStrings : DefaultTask() {


    init {
        group = "makers"
        description = "Downloads strings.xml from a remote server"
    }


    var projectBasePath = "."
    var configuration: RemoteL10nExtension = RemoteL10nExtension()

    object user : PropertyGroup() {
        val identifier by stringType
        val password by stringType
    }

    @TaskAction
    fun doIt() {
        val fetcher = RemoteL10nFetcher()
        fetcher.fetchStrings(FuelDownloader(), projectBasePath, configuration)
    }

    @Input
    fun getDateInput() : String {
        val formatter = DateTimeFormatter.ISO_DATE
        return LocalDateTime.now().format(formatter)
    }

    @Input
    fun getHostInput(): String {
        return configuration.remoteHost
    }

    @Input
    fun getExtInputs(): List<String> {
        val inputs = mutableListOf<String>()
        configuration.variants?.forEach { v ->
            v.languages.forEach { l ->
                inputs.add("${v.name}/$l")
            }
        }
        return inputs
    }

    @OutputFiles
    fun getTaskOOutputs(): List<File> {
        val outputs = mutableListOf<File>()
        configuration.variants?.forEach { v ->
            v.languages.forEach { l ->
                outputs.add(File("$projectBasePath/src/${v.name}/res/values-$l/strings.xml"))
            }
        }
        return outputs
    }
}