package net.distinctsoul.soulsorcery.datagen;

import net.distinctsoul.soulsorcery.block.ModBlocks;
import net.distinctsoul.soulsorcery.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FALIUM_BLOCK.get())
                .pattern("FFF")
                .pattern("FFF")
                .pattern("FFF")
                .define('F', ModItems.FALIUM.get())
                .unlockedBy("has_falium", has(ModItems.FALIUM)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FALIUM.get(), 9)
                .requires(ModBlocks.FALIUM_BLOCK)
                .unlockedBy("has_falium_block", has(ModBlocks.FALIUM_BLOCK)).save(recipeOutput);
    }
}
