package media.hexxy.hexdoc.datagen.api

import com.google.gson.Gson
import media.hexxy.hexdoc.datagen.common.Properties
import media.hexxy.hexdoc.datagen.common.patterns.HexdocPatternInfo
import media.hexxy.hexdoc.datagen.common.patterns.HexdocPatternRegistry
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataGenerator
import net.minecraft.data.DataProvider
import net.minecraft.resources.ResourceLocation

class HexdocPatternsProvider(
    gen: DataGenerator,
    private val namespace: String,
) : HexdocDataProvider(gen, "hexcasting") {
    private val location = ResourceLocation(namespace, "patterns")

    override fun run(output: CachedOutput) {
        val patterns = HexdocPatternRegistry.getPatterns(namespace)
            .sortedBy { it.id } // avoid inconsistent ordering / unnecessary diffs
            .map { HexdocPatternInfo(it, Properties.rootDir, Properties.srcDirs) }

        val json = Gson().toJsonTree(patterns)
        val path = pathProvider.json(location)
        DataProvider.saveStable(output, json, path)
    }

    override fun getName() = "hexdoc patterns generator for $namespace"
}
