package fr.androidmakers.buildsrc.tasks

import fr.androidmakers.buildsrc.plugins.RemoteL10nExtension

interface Fetcher {

    fun fetchStrings(downloader: Downloader,
                     projectBasePath: String,
                     configuration: RemoteL10nExtension,
                     authenticate: Boolean = true)
}