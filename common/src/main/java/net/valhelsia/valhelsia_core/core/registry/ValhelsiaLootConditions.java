package net.valhelsia.valhelsia_core.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.loot.condition.DateCondition;
import net.valhelsia.valhelsia_core.api.loot.condition.EntityTagCondition;
import net.valhelsia.valhelsia_core.api.loot.condition.MatchBlockCondition;
import net.valhelsia.valhelsia_core.api.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.registry.helper.DefaultRegistryHelper;

/**
 * @author Valhelsia Team
 * @since 2023-05-03
 */
public class ValhelsiaLootConditions implements RegistryClass {

    public static final DefaultRegistryHelper<LootItemConditionType> LOOT_CONDITION_TYPES = ValhelsiaCore.REGISTRY_MANAGER.getHelper(Registries.LOOT_CONDITION_TYPE);

    public static final RegistryEntry<LootItemConditionType> MATCH_BLOCK = LOOT_CONDITION_TYPES.register("match_block", () -> new LootItemConditionType(new MatchBlockCondition.Serializer()));
    public static final RegistryEntry<LootItemConditionType> DATE = LOOT_CONDITION_TYPES.register("date", () -> new LootItemConditionType(new DateCondition.Serializer()));
    public static final RegistryEntry<LootItemConditionType> ENTITY_TAG = LOOT_CONDITION_TYPES.register("entity_tag", () -> new LootItemConditionType(new EntityTagCondition.Serializer()));
}
