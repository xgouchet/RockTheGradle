package fr.androidmakers.buildsrc.plugins

import groovy.lang.Closure
import org.gradle.api.NamedDomainObjectContainer

open class RemoteL10nExtension {
    var remoteHost: String = "127.0.0.1"
    var variants: NamedDomainObjectContainer<VariantL10n>? = null

    fun variants(configureClosure: Closure<VariantL10n>) {
        variants?.configure(configureClosure)
    }
}


open class VariantL10n(val name: String) {
    var languages: Array<String> = arrayOf()
}