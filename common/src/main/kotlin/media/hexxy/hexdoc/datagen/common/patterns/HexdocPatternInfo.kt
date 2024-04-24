package media.hexxy.hexdoc.datagen.common.patterns

import com.google.gson.annotations.SerializedName
import java.nio.file.Path

/**
 * Should match the schema of `hexdoc_hexcasting.utils.pattern.PatternInfo`.
 */
data class HexdocPatternInfo(
    val id: String,
    val startdir: String,
    val signature: String,
    @SerializedName("is_per_world")
    val isPerWorld: Boolean,
    val classname: String,
    @SerializedName("source_path")
    val sourcePath: String?,
) {
    constructor(info: RegistryPatternInfo, rootDir: Path, srcDirs: Iterable<Path>) : this(
        id = info.id.toString(),
        startdir = info.pattern.startDir.name,
        signature = info.pattern.anglesSignature(),
        isPerWorld = info.isPerWorld,
        classname = info.classname,
        sourcePath = info.getSourcePath(rootDir, srcDirs)
    )
}
