package com.tm.calemicore.util.helper;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * Use this class for help with registering ores.
 */
@Mod.EventBusSubscriber
public class WorldGenHelper {

    public static final ArrayList<Holder<PlacedFeature>> oresOverworld = new ArrayList<>();
    public static final ArrayList<Holder<PlacedFeature>> oresNether = new ArrayList<>();
    public static final ArrayList<Holder<PlacedFeature>> oresEnd = new ArrayList<>();

    /**
     * Creates and registers an ore.
     * @param name The name of the ore. Will already apply "ore_" at the beginning.
     * @param oreBlock The ore Block.
     * @param deepslateBlock The deepslate variant of the ore Block. Set this to the same block if the ore does not have a deepslate variant.
     * @param veinSize The size of the ore vein.
     * @param veinsPerChunk The amount of veins that will appear in one chunk.
     * @param minY The minimum y value that the vein can generate at.
     * @param maxY The maximum y value that the vein can generate at.
     * @param oreList The list to add your ore in. Will determine the dimension to spawn the vein in. Use one of the 3 from this class.
     */
    public static void registerOre(String name, Block oreBlock, Block deepslateBlock, int veinSize, int veinsPerChunk, int minY, int maxY, ArrayList<PlacedFeature> oreList) {

        List<OreConfiguration.TargetBlockState> ORE_TARGET_LIST = List.of(
                OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, oreBlock.defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, deepslateBlock.defaultBlockState()));

        Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_FEATURE = FeatureUtils.register(name, Feature.ORE, new OreConfiguration(ORE_TARGET_LIST, veinSize));

        List<PlacementModifier> placementModifiers = List.of(
                CountPlacement.of(veinsPerChunk),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)), BiomeFilter.biome());

        Holder<PlacedFeature> ORE_PLACED = PlacementUtils.register("ore_" + name, ORE_FEATURE, placementModifiers);

        oreList.add(ORE_PLACED.value());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void generateOres(BiomeLoadingEvent event) {

        BiomeGenerationSettingsBuilder generation = event.getGeneration();

        if (!event.getCategory().equals(Biome.BiomeCategory.NETHER) && !event.getCategory().equals(Biome.BiomeCategory.THEEND)) {

            for (Holder<PlacedFeature> ore : oresOverworld) {
                if (ore != null) event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(ore);
            }
        }

        else if (event.getCategory().equals(Biome.BiomeCategory.NETHER)) {

            for (Holder<PlacedFeature> ore : oresNether) {
                if (ore != null) event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(ore);
            }
        }

        else if (event.getCategory().equals(Biome.BiomeCategory.THEEND)) {

            for (Holder<PlacedFeature> ore : oresEnd) {
                if (ore != null) event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(ore);
            }
        }
    }
}
