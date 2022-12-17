package net.valhelsia.valhelsia_core.core.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.init.ValhelsiaTags;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

/**
 * Valhelsia Block Tags Provider <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.data.tags.ValhelsiaBlockTagsProvider
 *
 * @author Valhelsia Team
 * @since 2022-04-14
 */
public class ValhelsiaBlockTagsProvider extends BlockTagsProvider {

    public ValhelsiaBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ValhelsiaCore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider provider) {
        this.empty(ValhelsiaTags.Blocks.OFFSET_RENDERING);
    }

    @SafeVarargs
    public final void empty(TagKey<Block>... tags) {
        for (TagKey<Block> tag : tags) {
            this.tag(tag);
        }
    }
}
