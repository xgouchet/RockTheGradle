package fr.androidmakers.buildsrc.tasks

import com.github.kittinunf.fuel.Fuel
import java.io.File
import java.net.HttpURLConnection

class FuelDownloader : Downloader {

    override fun downloadFile(path: String,
                              destination: File,
                              onSuccess: () -> Unit,
                              onError: (Int) -> Unit) {
        Fuel.download(path)
                .destination { response, url ->
                    return@destination destination
                }
                .response { req, res, result ->
                    if (res.statusCode == HttpURLConnection.HTTP_OK) {
                        onSuccess()
                    } else {
                        onError(res.statusCode)
                    }
                }
    }
}