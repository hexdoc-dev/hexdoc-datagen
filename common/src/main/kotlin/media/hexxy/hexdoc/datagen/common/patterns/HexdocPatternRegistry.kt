package media.hexxy.hexdoc.datagen.common.patterns

import at.petrak.hexcasting.api.spell.Action
import at.petrak.hexcasting.api.spell.math.HexPattern
import net.minecraft.resources.ResourceLocation

object HexdocPatternRegistry {
    /**
     * namespace -> patterns
     */
    private val patternLookup = mutableMapOf<String, MutableSet<RegistryPatternInfo>>()

    /**
     * Must be called AFTER registering all patterns.
     */
    fun getPatterns(namespace: String): Set<RegistryPatternInfo> {
        return patternLookup[namespace] ?: throw RuntimeException("No patterns found with namespace $namespace")
    }

    @JvmStatic
    fun addPattern(pattern: HexPattern, id: ResourceLocation, action: Action, isPerWorld: Boolean) {
        val patterns = patternLookup.getOrPut(id.namespace) { mutableSetOf() }
        patterns += RegistryPatternInfo(pattern, id, action, isPerWorld)
    }
}
