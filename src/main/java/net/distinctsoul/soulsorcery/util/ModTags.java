package net.distinctsoul.soulsorcery.util;

import net.distinctsoul.soulsorcery.SoulSorcery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_FALIUM_TOOL = createTag("needs_falium_tool");
        public static final TagKey<Block> INCORRECT_FOR_FALIUM_TOOL = createTag("incorrect_for_falium_tool");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(SoulSorcery.MOD_ID, name));
        }
    }
}
