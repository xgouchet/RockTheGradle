package fr.androidmakers.buildsrc.tasks

import java.io.File

interface Downloader {

    fun downloadFile(path: String,
                     destination: File,
                     onSuccess: () -> Unit = {},
                     onError: (Int) -> Unit = {})

}