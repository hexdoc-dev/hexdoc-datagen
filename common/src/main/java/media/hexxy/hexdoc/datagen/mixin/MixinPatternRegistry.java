package media.hexxy.hexdoc.datagen.mixin;

import at.petrak.hexcasting.api.PatternRegistry;
import at.petrak.hexcasting.api.spell.Action;
import at.petrak.hexcasting.api.spell.math.HexPattern;
import media.hexxy.hexdoc.datagen.common.patterns.HexdocPatternRegistry;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PatternRegistry.class)
public class MixinPatternRegistry {
    @Inject(at = @At("TAIL"), remap = false, method = "mapPattern(Lat/petrak/hexcasting/api/spell/math/HexPattern;Lnet/minecraft/resources/ResourceLocation;Lat/petrak/hexcasting/api/spell/Action;Z)V")
    private static void yoinkPatterns(HexPattern pattern, ResourceLocation id, Action action, boolean isPerWorld, CallbackInfo ci) {
        HexdocPatternRegistry.addPattern(pattern, id, action, isPerWorld);
    }
}
