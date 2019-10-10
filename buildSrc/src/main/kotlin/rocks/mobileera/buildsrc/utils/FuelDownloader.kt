package rocks.mobileera.buildsrc.utils

import com.github.kittinunf.fuel.Fuel
import java.io.File

class FuelDownloader : Downloader {

    override fun download(url: String, path: String) {
        println("Downloading $url to $path")
        Fuel.download(url)
                .destination { response, url ->
                    val file = File(path)
                    file.parentFile.mkdirs()
                    return@destination file
                }
                .response { req, res, result ->
                    // Do nothing
                }
    }

}
