package fr.androidmakers.buildsrc.tasks

import com.github.kittinunf.fuel.Fuel
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.PropertyGroup
import com.natpryce.konfig.getValue
import com.natpryce.konfig.overriding
import com.natpryce.konfig.stringType
import fr.androidmakers.buildsrc.plugins.RemoteL10nExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.net.HttpURLConnection

open class DownloadStrings : DefaultTask() {

    var projectBasePath = "."
    var configuration: RemoteL10nExtension = RemoteL10nExtension()

    object user : PropertyGroup() {
        val identifier by stringType
        val password by stringType
    }

    @TaskAction
    fun doIt() {
        val gradleProperties = File(System.getProperty("user.home") + "/.gradle/gradle.properties")
        val config = EnvironmentVariables() overriding ConfigurationProperties.fromFile(gradleProperties)
        val authentication = "${config[user.identifier]}:${config[user.password]}@"

        val variants = configuration.variants ?: return
        for (variant in variants) {
            for (language in variant.languages) {
                val path = "http://$authentication@${configuration.remoteHost}/${variant.name}/$language/strings.xml"
                Fuel.download(path)
                        .destination { response, url ->
                            val file = File("$projectBasePath/src/${variant.name}/res/values-$language/strings.xml")
                            file.parentFile.mkdirs() // ensure the folder exists
                            return@destination file
                        }
                        .response { req, res, result ->
                            if (res.statusCode != HttpURLConnection.HTTP_OK) {
                                System.err.println(" ✗ Error downloading $path")
                            } else {
                                println(" ✓ Downloaded strings for ${variant.name} in values-$language")
                            }
                        }
            }
        }
    }
}