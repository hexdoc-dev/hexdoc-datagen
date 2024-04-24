package media.hexxy.hexdoc.datagen.fabric

import at.petrak.hexcasting.api.PatternRegistry
import at.petrak.hexcasting.api.spell.math.HexDir
import at.petrak.hexcasting.api.spell.math.HexPattern
import media.hexxy.hexdoc.datagen.fabric.casting.OpWeed
import media.hexxy.hexdoc.datagen.fabric.casting.OpWeed2
import media.hexxy.hexdoc.datagen.api.HexdocPatternsProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.resources.ResourceLocation

class HexdocDatagenFabric : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        PatternRegistry.mapPattern(
            HexPattern.fromAngles("weed", HexDir.EAST),
            ResourceLocation("hexdoc_datagen", "weed"),
            OpWeed,
        )
        PatternRegistry.mapPattern(
            HexPattern.fromAngles("weedweed", HexDir.EAST),
            ResourceLocation("hexdoc_datagen", "weedweed"),
            OpWeed2,
        )

        gen.addProvider(HexdocPatternsProvider(gen, "hexdoc_datagen"))
    }
}
