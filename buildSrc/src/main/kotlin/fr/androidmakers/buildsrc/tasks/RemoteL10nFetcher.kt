package fr.androidmakers.buildsrc.tasks

import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.overriding
import fr.androidmakers.buildsrc.plugins.RemoteL10nExtension
import fr.androidmakers.buildsrc.plugins.VariantL10n
import java.io.File

class RemoteL10nFetcher
    : Fetcher {

    lateinit var remoteHost : String
    lateinit var basePath : String
    lateinit var downloader : Downloader

    override fun fetchStrings(downloader: Downloader,
                              projectBasePath: String,
                              configuration: RemoteL10nExtension,
                              authenticate: Boolean) {

        remoteHost = configuration.remoteHost
        basePath = projectBasePath
        this.downloader = downloader

        val authentication = if (authenticate) getAuthenticationString() else ""
        val variants = configuration.variants ?: return

        for (variant in variants) {
            fetchStringsForVariant(variant, authentication)
        }
    }

    private fun getAuthenticationString(): String {
        val gradleProperties = File(System.getProperty("user.home") + "/.gradle/gradle.properties")
        val config = EnvironmentVariables() overriding ConfigurationProperties.fromFile(gradleProperties)
        return "${config[DownloadStrings.user.identifier]}:${config[DownloadStrings.user.password]}@"
    }

    private fun fetchStringsForVariant(variant: VariantL10n, authentication: String) {
        for (language in variant.languages) {
            fetchStringsForVariantAndLanguage(authentication, variant, language)
        }
    }

    private fun fetchStringsForVariantAndLanguage(authentication: String,
                                                  variant: VariantL10n,
                                                  language: String) {

        val path = "http://$authentication$remoteHost/${variant.name}/$language/strings.xml"
        val file = File("$basePath/src/${variant.name}/res/values-$language/strings.xml")
        file.parentFile.mkdirs() // ensure the folder exists

        downloader.downloadFile(path,
                file,
                { println(" ✓ Downloaded strings for ${variant.name} in values-$language") },
                { System.err.println(" ✗ Error downloading $path") })
    }

}