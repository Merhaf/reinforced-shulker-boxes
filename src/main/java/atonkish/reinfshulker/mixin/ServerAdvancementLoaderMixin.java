package atonkish.reinfshulker.mixin;

import java.util.ArrayList;
import java.util.Map;

import com.google.gson.JsonElement;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.resource.ResourceManager;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import atonkish.reinfshulker.ReinforcedShulkerBoxesMod;

@Mixin(ServerAdvancementLoader.class)
public class ServerAdvancementLoaderMixin {
    @Inject(method = "apply", at = @At("HEAD"))
    private void removeMissingIdentifier(Map<Identifier, JsonElement> map, ResourceManager resourceManager,
            Profiler profiler, CallbackInfo info) {
        if (ReinforcedShulkerBoxesMod.IS_REINFCHEST_LOADED) {
            return;
        }

        ArrayList<Identifier> missingIdentifiers = new ArrayList<>();
        for (Identifier id : map.keySet()) {
            if (!id.getNamespace().equals(ReinforcedShulkerBoxesMod.MOD_ID)) {
                continue;
            }

            if (id.getPath().contains("chest")) {
                missingIdentifiers.add(id);
            }
        }

        missingIdentifiers.forEach(map::remove);
    }

}
