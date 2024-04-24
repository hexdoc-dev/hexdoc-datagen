package media.hexxy.hexdoc.datagen.patterns

import com.google.gson.annotations.SerializedName

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
    override fun equals(other: Any?): Boolean {
        if (other is HexdocPatternInfo) {
            return id == other.id
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}