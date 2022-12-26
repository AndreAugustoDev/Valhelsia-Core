package net.valhelsia.valhelsia_core.core.registry.helper.block;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.valhelsia.valhelsia_core.common.block.StrippableRotatedPillarBlock;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaStandingSignBlock;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaWallSignBlock;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.MappedRegistryHelper;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Block Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.block.BlockRegistryHelper
 *
 * @author Valhelsia Team
 * @since 2020-11-18
 */
public class BlockRegistryHelper extends MappedRegistryHelper<Block> {

    private final List<BlockRegistryObject<?>> blockRegistryObjects = new ArrayList<>();

    public final List<BlockRegistryObject<? extends SignBlock>> signBlocks = new ArrayList<>();

    private final FlammableHelper flammableHelper = new FlammableHelper();
    private final CompostableHelper compostableHelper = new CompostableHelper();

    public BlockRegistryHelper(ResourceKey<? extends Registry<Block>> registry, String modId, ImmutableList<Supplier<RegistryClass>> registryClasses) {
        super(registry, modId, registryClasses);
    }

    public Optional<MappedRegistryHelper<Item>> getItemRegistryHelper() {
        return Optional.ofNullable(ValhelsiaCore.REGISTRY_MANAGERS.get(this.getModId()).getItemHelper());
    }

    public FlammableHelper getFlammableHelper() {
        return this.flammableHelper;
    }

    public CompostableHelper getCompostableHelper() {
        return this.compostableHelper;
    }

    public <T extends Block> BlockRegistryObject<T> create(String name, Supplier<T> block) {
        BlockRegistryObject<T> registryObject = BlockRegistryObject.of(name, this.register(name, block));

        this.blockRegistryObjects.add(registryObject);

        return registryObject;
    }

    public List<BlockRegistryObject<? extends Block>> getBlockRegistryObjects() {
        return this.blockRegistryObjects;
    }

    public BlockRegistryObject<StrippableRotatedPillarBlock> createLogBlock(String name, Supplier<RotatedPillarBlock> strippedBlock, MaterialColor topColor, MaterialColor barkColor) {
        return this.create(name, () -> new StrippableRotatedPillarBlock(strippedBlock, Block.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).strength(2.0F).sound(SoundType.WOOD))).withItem();
    }

    public BlockRegistryObject<RotatedPillarBlock> createStrippedLogBlock(String name, MaterialColor topColor, MaterialColor barkColor) {
        return this.create(name, () -> new RotatedPillarBlock(Block.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).strength(2.0F).sound(SoundType.WOOD))).withItem();
    }

    public Pair<BlockRegistryObject<ValhelsiaStandingSignBlock>, BlockRegistryObject<ValhelsiaWallSignBlock>> createSignBlock(String name, MaterialColor color, WoodType woodType) {
        BlockRegistryObject<ValhelsiaStandingSignBlock> standing = this.create(name + "_sign", () -> new ValhelsiaStandingSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), woodType));
        BlockRegistryObject<ValhelsiaWallSignBlock> wall = this.create(name + "_wall_sign", () -> new ValhelsiaWallSignBlock(Block.Properties.of(Material.WOOD, color).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(standing), woodType));
        this.signBlocks.add(standing);
        this.signBlocks.add(wall);

        standing.withItem(registryObject -> new SignItem(new Item.Properties().stacksTo(16), registryObject.get(), wall.get()));

        return Pair.of(standing, wall);
    }

    public <T extends Block> EnumMap<DyeColor, BlockRegistryObject<T>> createColorVariants(String name, Function<DyeColor, T> function) {
        return Arrays.stream(DyeColor.values())
                .map(color -> Pair.of(color, this.create(color.getName() + "_" + name, () -> function.apply(color))))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond, (o, o2) -> o, () -> new EnumMap<>(DyeColor.class)));
    }

    public <T extends Block> EnumMap<DyeColor, BlockRegistryObject<T>> createColorVariants(String name, Function<DyeColor, T> function, UnaryOperator<BlockRegistryObject<T>> unaryOperator) {
        return Arrays.stream(DyeColor.values())
                .map(color -> Pair.of(color, unaryOperator.apply(this.create(color.getName() + "_" + name, () -> function.apply(color)))))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond, (o, o2) -> o, () -> new EnumMap<>(DyeColor.class)));
    }

    public <T extends Block> EnumMap<DyeColor, BlockRegistryObject<T>> createColorVariants(UnaryOperator<String> name, Function<DyeColor, T> function) {
        return Arrays.stream(DyeColor.values())
                .map(color -> Pair.of(color, this.create(name.apply(color.getName()), () -> function.apply(color))))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond, (o, o2) -> o, () -> new EnumMap<>(DyeColor.class)));
    }

    public <T extends Block> EnumMap<DyeColor, BlockRegistryObject<T>> createColorVariants(UnaryOperator<String> name, Function<DyeColor, T> function, UnaryOperator<BlockRegistryObject<T>> unaryOperator) {
        return Arrays.stream(DyeColor.values())
                .map(color -> Pair.of(color, unaryOperator.apply(this.create(name.apply(color.getName()), () -> function.apply(color)))))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond, (o, o2) -> o, () -> new EnumMap<>(DyeColor.class)));
    }
}
