package media.hexxy.hexdoc.datagen.common.patterns

import at.petrak.hexcasting.api.spell.Action
import at.petrak.hexcasting.api.spell.math.HexPattern
import net.minecraft.resources.ResourceLocation
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassReader.SKIP_CODE
import org.objectweb.asm.ClassReader.SKIP_FRAMES
import org.objectweb.asm.ClassVisitor
import org.spongepowered.asm.util.asm.ASM
import java.nio.file.Path
import kotlin.io.path.div
import kotlin.io.path.exists
import kotlin.io.path.invariantSeparatorsPathString
import kotlin.io.path.relativeToOrNull
import kotlin.reflect.jvm.jvmName

/**
 * Arguments passed to `PatternRegistry#mapPattern`.
 */
data class RegistryPatternInfo(
    val pattern: HexPattern,
    val id: ResourceLocation,
    val action: Action,
    val isPerWorld: Boolean,
) {
    val classname get() = action::class.run { qualifiedName ?: jvmName }

    override fun equals(other: Any?) = other is RegistryPatternInfo && id == other.id
    override fun hashCode() = id.hashCode()

    // https://github.com/Cypher121/hexbound/blob/dd1e93bb95/src/main/kotlin/coffee/cypher/hexbound/docgen/Docgen.kt#L35
    fun getSourcePath(rootDir: Path, srcDirs: Iterable<Path>): String? {
        // don't try to find classes from external sources
        val codeSource = action::class.java.protectionDomain.codeSource.location.file
        if (codeSource.endsWith(".jar")) return null

        val sourceFile = getSourceFile() ?: return null

        val sourcePackage = action::class.java.packageName.replace(".", "/")

        return srcDirs
            .map { it / sourcePackage / sourceFile }
            .firstOrNull { it.exists() }
            ?.relativeToOrNull(rootDir)
            ?.invariantSeparatorsPathString
    }

    // thanks june :3
    private fun getSourceFile() = SourceFileVisitor().let {
        ClassReader(action::class.java.name).accept(it, SKIP_CODE or SKIP_FRAMES)
        it.source
    }

    private class SourceFileVisitor : ClassVisitor(ASM.API_VERSION) {
        var source: String? = null

        override fun visitSource(source: String?, debug: String?) {
            this.source = source ?: return
        }
    }
}
