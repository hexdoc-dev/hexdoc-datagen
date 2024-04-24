package media.hexxy.hexdoc.datagen.common

import kotlin.io.path.Path

object Properties {
    val rootDir by getProperty("root-dir", ::Path)
    val srcDirs by getProperty("src-dirs") { it.split(";").map(::Path) }

    private fun getProperty(name: String) = getProperty(name) { it }

    private fun <T> getProperty(name: String, action: (String) -> T): Lazy<T> = lazy {
        action(System.getProperty("hexdoc-datagen.$name"))
    }
}
