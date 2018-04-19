package fr.androidmakers.buildsrc.tasks

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argThat
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import fr.androidmakers.buildsrc.plugins.RemoteL10nExtension
import fr.androidmakers.buildsrc.plugins.VariantL10n
import org.gradle.api.NamedDomainObjectContainer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class RemoteL10nFetcherTest {

    @Rule @JvmField val tempFolder = TemporaryFolder()

    lateinit var fakeOutputFolder: File
    lateinit var fakeConfiguration: RemoteL10nExtension
    val fakeRemoteHost = "test.not"

    lateinit var mockVariants: NamedDomainObjectContainer<VariantL10n>
    lateinit var variantsSet: MutableSet<VariantL10n>


    lateinit var mockDownloader: Downloader
    lateinit var testedFetcher: RemoteL10nFetcher

    @Before
    fun setUp() {
        mockDownloader = mock()
        fakeConfiguration = RemoteL10nExtension()
        fakeOutputFolder = tempFolder.newFolder()
        mockVariants = mock()

        fakeConfiguration.remoteHost = fakeRemoteHost
        fakeConfiguration.variants = mockVariants

        testedFetcher = RemoteL10nFetcher()
    }


    @Test
    fun doesNothingWithEmptyConfiguration() {
        fakeConfiguration.variants = null

        testedFetcher.fetchStrings(mockDownloader,
                fakeOutputFolder.path,
                fakeConfiguration,
                false)

        verifyZeroInteractions(mockDownloader)
        assert(fakeOutputFolder.listFiles().isEmpty())
    }

    @Test
    fun downloadSingleVariantFile() {
        variantsSet = mutableSetOf(VariantL10n("free").apply { languages = arrayOf("en", "fr", "jp") })
        whenever(mockVariants.iterator()) doReturn variantsSet.iterator()

        testedFetcher.fetchStrings(mockDownloader,
                fakeOutputFolder.path,
                fakeConfiguration,
                false)

        verify(mockDownloader).downloadFile(eq("http://$fakeRemoteHost/free/en/strings.xml"),
                argThat { path.endsWith("/src/free/res/values-en/strings.xml") },
                any(), any())
        verify(mockDownloader).downloadFile(eq("http://$fakeRemoteHost/free/fr/strings.xml"),
                argThat { path.endsWith("/src/free/res/values-fr/strings.xml") },
                any(), any())
        verify(mockDownloader).downloadFile(eq("http://$fakeRemoteHost/free/jp/strings.xml"),
                argThat { path.endsWith("/src/free/res/values-jp/strings.xml") },
                any(), any())
    }
}