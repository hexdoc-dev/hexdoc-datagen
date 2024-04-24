package media.hexxy.hexdoc.datagen.fabric

import at.petrak.hexcasting.api.PatternRegistry
import at.petrak.hexcasting.api.spell.Action
import at.petrak.hexcasting.api.spell.iota.NullIota
import at.petrak.hexcasting.api.spell.math.HexDir
import at.petrak.hexcasting.api.spell.math.HexPattern
import media.hexxy.hexdoc.datagen.fabric.casting.OpWeed
import media.hexxy.hexdoc.datagen.patterns.HexdocPatternsGenerator
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

        gen.addProvider(HexdocPatternsGenerator(gen, "hexcasting"))
    }
}
