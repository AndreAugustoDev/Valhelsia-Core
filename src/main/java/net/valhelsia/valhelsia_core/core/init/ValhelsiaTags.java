package net.valhelsia.valhelsia_core.core.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;

/**
 * Valhelsia Tags <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.init.ValhelsiaTags
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.1
 * @since 2022-04-14
 */
public class ValhelsiaTags {

    public static class Blocks {

        public static final TagKey<Block> OFFSET_RENDERING = modTag("offset_rendering");

        private static TagKey<Block> modTag(String name) {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ValhelsiaCore.MOD_ID, name));
        }
    }
}
