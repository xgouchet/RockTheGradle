package rocks.mobileera.buildsrc.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import rocks.mobileera.buildsrc.utils.Downloader
import rocks.mobileera.buildsrc.utils.FuelDownloader
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


open class GetStringsTask : DefaultTask() {

    init {
        group = "mobileEra"
        description = "Downloads strings.xml from a remote server."
    }

    val downloader: Downloader = FuelDownloader()

    var root = ""
    var extension = GetStringsExtension()

    @Input
    fun getLanguagesInputs(): List<String> {
        return extension.variants.orEmpty()
                .flatMap { v -> v.languages.map { "${v.name}/$it" } }
                .toList()
    }

    @Input
    fun getBaseUrlInputs(): String {
        return extension.baseUrl
    }

    @OutputFiles
    fun getTaskOutputs(): List<File> {
        return extension.variants.orEmpty()
                .flatMap { v -> v.languages.map { File("$root/${v.name}/res/values-$it/strings.xml") } }
                .toList()
    }

    @Input
    fun getDateInput(): String {
        val formatter = DateTimeFormatter.ISO_DATE
        return LocalDateTime.now().format(formatter)
    }


    @TaskAction fun performTask() {
        for (v in extension.variants.orEmpty()) {
            for (l in v.languages) {
                val url = "${extension.baseUrl}/$l/strings.xml"
                val path = "$root/${v.name}/res/values-$l/strings.xml"
                downloader.download(url, path)
            }
        }
    }
}
