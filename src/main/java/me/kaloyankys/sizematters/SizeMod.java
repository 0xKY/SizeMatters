package me.kaloyankys.sizematters;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SizeMod implements ModInitializer {
    public static final Item ENLARGEMENT_PILL = new Item(
            new Item.Settings()
                    .group(ItemGroup.MISC)
                    .maxCount(16)
                    .food(new FoodComponent.Builder().build())
    );
    public static final Item SHRINKING_PILL = new Item(
            new Item.Settings()
                    .group(ItemGroup.MISC)
                    .maxCount(16)
                    .food(new FoodComponent.Builder().build()));

    public static final Item ROTATION_PILL = new Item(
            new Item.Settings()
            .group(ItemGroup.MISC)
            .maxCount(16));

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("sizematters", "enlargement_pill"), ENLARGEMENT_PILL);
        Registry.register(Registry.ITEM, new Identifier("sizematters", "big_problems_pill"), SHRINKING_PILL);
        Registry.register(Registry.ITEM, new Identifier("sizematters", "wonky_pill"), ROTATION_PILL);
    }
}
