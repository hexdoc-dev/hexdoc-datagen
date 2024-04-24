package media.hexxy.hexdoc.datagen.patterns

import com.google.gson.Gson
import media.hexxy.hexdoc.datagen.HexdocDatagen
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataGenerator
import net.minecraft.data.DataProvider
import net.minecraft.resources.ResourceLocation

class HexdocPatternsGenerator(
    gen: DataGenerator,
    private val namespace: String,
) : DataProvider {
    private val pathProvider = gen.createPathProvider(DataGenerator.Target.DATA_PACK, "hexdoc")
    private val location = ResourceLocation(namespace, "hexcasting_patterns")

    override fun run(output: CachedOutput) {
        // sort to avoid inconsistent ordering / unnecessary diffs
        val patterns = HexdocPatternRegistry.getPatterns(namespace).sortedBy { it.id }
        val json = Gson().toJsonTree(patterns)
        val path = pathProvider.json(location)
        DataProvider.saveStable(output, json, path)
    }

    override fun getName() = "hexdoc patterns generator for $namespace"
}
