package media.hexxy.hexdoc.datagen.api

import net.minecraft.data.DataGenerator
import net.minecraft.data.DataGenerator.PathProvider
import net.minecraft.data.DataProvider

abstract class HexdocDataProvider(gen: DataGenerator, vararg subpath: String) : DataProvider {
    protected val pathProvider: PathProvider = gen.createPathProvider(
        DataGenerator.Target.DATA_PACK,
        listOf("hexdoc", *subpath).joinToString("/"),
    )
}
