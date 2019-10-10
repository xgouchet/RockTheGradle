package rocks.mobileera.buildsrc.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import rocks.mobileera.buildsrc.utils.Downloader
import rocks.mobileera.buildsrc.utils.FuelDownloader


open class GetStringsTask : DefaultTask() {

    init {
        group = "mobileEra"
        description = "Downloads strings.xml from a remote server."
    }

    val downloader: Downloader = FuelDownloader()

    var root = ""
    var extension = GetStringsExtension()


    @TaskAction fun performTask() {
        for (l in extension.languages) {
            val url = "${extension.baseUrl}/$l/strings.xml"
            val path = "$root/res/values-$l/strings.xml"
            downloader.download(url, path)
        }
    }
}
