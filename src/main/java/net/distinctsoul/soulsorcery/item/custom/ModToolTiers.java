package net.distinctsoul.soulsorcery.item.custom;

import net.distinctsoul.soulsorcery.item.ModItems;
import net.distinctsoul.soulsorcery.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier FALIUM = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_FALIUM_TOOL,
            100, 6.0f, 2.0f, 22, () -> Ingredient.of(ModItems.FALIUM));
}
