package me.kaloyankys.sizematters;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SizeMod implements ModInitializer {
    public static Item ENLARGEMENT_PILL;
    public static Item SHRINKING_PILL;

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("sizematters", "enlargement_pill"), ENLARGEMENT_PILL = new Item(
                new Item.Settings()
                        .group(ItemGroup.MISC)
                        .maxCount(16)));
        Registry.register(Registry.ITEM, new Identifier("sizematters", "big_problems_pill"), SHRINKING_PILL = new Item(
                new Item.Settings()
                        .group(ItemGroup.MISC)
                        .maxCount(16)));
    }
}
