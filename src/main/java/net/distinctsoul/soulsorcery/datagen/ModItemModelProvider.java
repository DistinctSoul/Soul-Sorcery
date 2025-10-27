package net.distinctsoul.soulsorcery.datagen;

import net.distinctsoul.soulsorcery.SoulSorcery;
import net.distinctsoul.soulsorcery.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SoulSorcery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.FALIUM_FRAGMENTS.get());
        basicItem(ModItems.FALIUM.get());
        basicItem(ModItems.CORRUPTED_SOUL.get());
        basicItem(ModItems.MIGHTY_PURIFIED_SOUL.get());
    }
}
