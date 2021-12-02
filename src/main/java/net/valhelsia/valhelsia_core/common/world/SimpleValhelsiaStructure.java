package net.valhelsia.valhelsia_core.common.world;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;

/**
 * Simple Valhelsia Structure <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.world.ValhelsiaStructure
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-05-27
 */
public abstract class SimpleValhelsiaStructure<C extends FeatureConfiguration> extends StructureFeature<C> implements IValhelsiaStructure {

    private final String name;

    public SimpleValhelsiaStructure(Codec<C> codec, PieceGeneratorSupplier<C> pieceGeneratorSupplier, String name) {
        super(codec, pieceGeneratorSupplier);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public StructureFeature<?> getStructure() {
        return this;
    }

    @Override
    public abstract StructureFeatureConfiguration getFeatureConfiguration();
}
