package net.valhelsia.valhelsia_core.init;

import net.minecraft.loot.LootConditionType;
import net.valhelsia.valhelsia_core.loot.conditions.DateCondition;
import net.valhelsia.valhelsia_core.loot.conditions.EntityTagCondition;
import net.valhelsia.valhelsia_core.loot.conditions.MatchBlockCondition;

/**
 * Valhelsia Loot Conditions
 * Valhelsia Core - net.valhelsia.valhelsia_core.init.ValhelsiaLootConditions
 *
 * @author Valhelsia Team
 * @version 16.0.7
 * @since 2021-05-05
 */
public class ValhelsiaLootConditions {

    public static final LootConditionType MATCH_BLOCK = new LootConditionType(new MatchBlockCondition.Serializer());
    public static final LootConditionType DATE = new LootConditionType(new DateCondition.Serializer());
    public static final LootConditionType ENTITY_TAG = new LootConditionType(new EntityTagCondition.Serializer());
}
