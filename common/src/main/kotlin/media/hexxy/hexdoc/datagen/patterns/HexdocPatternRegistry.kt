package media.hexxy.hexdoc.datagen.patterns

import at.petrak.hexcasting.api.spell.Action
import at.petrak.hexcasting.api.spell.math.HexPattern
import net.minecraft.resources.ResourceLocation
import java.nio.file.Paths
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.invariantSeparatorsPathString
import kotlin.io.path.relativeToOrNull
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.jvmName

object HexdocPatternRegistry {
    /**
     * namespace -> patterns
     */
    private val patternLookup = mutableMapOf<String, MutableSet<HexdocPatternInfo>>()

    private val rootDir = Path(System.getProperty("hexdoc-datagen.root-dir"))
    private val srcDirs = System.getProperty("hexdoc-datagen.src-dirs").split(";")

    /**
     * Must be called AFTER registering all patterns.
     */
    fun getPatterns(namespace: String): Set<HexdocPatternInfo> {
        return patternLookup[namespace] ?: throw RuntimeException("No patterns found with namespace $namespace")
    }

    @JvmStatic
    fun addPattern(pattern: HexPattern, id: ResourceLocation, action: Action, isPerWorld: Boolean) {
        val patterns = patternLookup.getOrPut(id.namespace) { mutableSetOf() }
        patterns += HexdocPatternInfo(
            id = id.toString(),
            startdir = pattern.startDir.name,
            signature = pattern.anglesSignature(),
            isPerWorld = isPerWorld,
            classname = action::class.run { qualifiedName ?: jvmName },
            sourcePath = getSourcePath(action)
        )
    }

    // https://github.com/Cypher121/hexbound/blob/dd1e93bb95/src/main/kotlin/coffee/cypher/hexbound/docgen/Docgen.kt#L35
    fun getSourcePath(action: Action): String? {
        val sourceFile = action::class.declaredMemberFunctions.first().let {
            val args = Array<Any?>(it.parameters.size - 1) { null }

            val stackTrace = try {
                it.isAccessible = true
                it.call(action, *args)
                null //should never happen
            } catch (e: Throwable) {
                var rootException = e
                while (rootException.cause != null) {
                    rootException = rootException.cause!!
                }

                rootException.stackTrace
            }

            stackTrace!!

            stackTrace[0].fileName!!
        }

        // don't try to find classes from external sources
//        val codeSource = action::class.java.protectionDomain.codeSource.location.file
//        if (codeSource.endsWith(".jar")) return null

        val sourcePackage = action::class.java.packageName.replace(".", "/")

        return srcDirs
            .map { Paths.get(it, sourcePackage, sourceFile) }
            .firstOrNull { it.exists() }
            ?.relativeToOrNull(rootDir)
            ?.invariantSeparatorsPathString
    }
}
