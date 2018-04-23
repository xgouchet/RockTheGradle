package fr.androidmakers.buildsrc.tasks

import com.natpryce.konfig.PropertyGroup
import com.natpryce.konfig.getValue
import com.natpryce.konfig.stringType
import fr.androidmakers.buildsrc.plugins.RemoteL10nExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction


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
}