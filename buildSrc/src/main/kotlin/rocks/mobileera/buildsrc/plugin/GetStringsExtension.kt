package rocks.mobileera.buildsrc.plugin

import groovy.lang.Closure
import org.gradle.api.NamedDomainObjectContainer

open class GetStringsExtension {
    var baseUrl: String = "http://127.0.0.1"
    var variants: NamedDomainObjectContainer<VariantLanguages>? = null

    fun variants(configureClosure: Closure<VariantLanguages>) {
        variants?.configure(configureClosure)
    }
}

open class VariantLanguages(val name: String) {
    var languages: Array<String> = arrayOf()
}
