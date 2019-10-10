package rocks.mobileera.buildsrc.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import rocks.mobileera.buildsrc.utils.Downloader
import rocks.mobileera.buildsrc.utils.FuelDownloader


open class GetStringsTask : DefaultTask() {

    val downloader: Downloader = FuelDownloader()

    var languages: Array<String> = arrayOf()
    var root = ""
    var baseUrl = "http://127.0.0.1"

    @TaskAction fun performTask() {
        for (l in languages) {
            val url = "$baseUrl/$l/strings.xml"
            val path = "$root/res/values-$l/strings.xml"
            downloader.download(url, path)
        }
    }
}
