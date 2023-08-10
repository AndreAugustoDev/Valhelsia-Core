package net.valhelsia.valhelsia_core.core.registry;

import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryCollector;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-09
 */
public class ModForgeRegistryCollector extends RegistryCollector {

    public ModForgeRegistryCollector(String modId) {
        super(modId);
    }

    @Override
    protected void collectHelpers() {
        this.addMappedHelper(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ValhelsiaBiomeModifiers::new);
    }
}
