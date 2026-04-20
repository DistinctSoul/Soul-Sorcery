package net.distinctsoul.soulsorcery.event;

import net.distinctsoul.soulsorcery.attachment.ModAttachments;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import javax.annotation.Nullable;

public class ModEvents {

    // Helper to determine the primary tool class for a block
    @Nullable
    private static Class<? extends TieredItem> getRequiredToolClass(BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE)) return PickaxeItem.class;
        if (state.is(BlockTags.MINEABLE_WITH_AXE)) return AxeItem.class;
        if (state.is(BlockTags.MINEABLE_WITH_SHOVEL)) return ShovelItem.class;
        if (state.is(BlockTags.MINEABLE_WITH_HOE)) return HoeItem.class; // Optional
        return null;
    }

    // Helper to get held tier only if it matches the required tool class
    @Nullable
    private static Tier getHeldTier(ItemStack stack, Class<? extends TieredItem> requiredClass) {
        if (stack.getItem() instanceof TieredItem tiered && requiredClass.isInstance(tiered)) {
            return tiered.getTier();
        }
        return null;
    }

    // Helper for required tier (only meaningful for pickaxes; 0 for others)
    private static int getRequiredTier(BlockState state, Class<? extends TieredItem> toolClass) {
        if (toolClass == PickaxeItem.class) {
            if (state.is(BlockTags.NEEDS_DIAMOND_TOOL)) return 3;
            if (state.is(BlockTags.NEEDS_IRON_TOOL)) return 2;
            if (state.is(BlockTags.NEEDS_STONE_TOOL)) return 1;
        }
        return 0;
    }

    // Helper to get dummy tool stack for speed calculation
    private static ItemStack getDummyTool(Class<? extends TieredItem> toolClass, int tier) {
        Item item = Items.AIR;
        if (toolClass == PickaxeItem.class) {
            item = switch (tier) {
                case 0 -> Items.WOODEN_PICKAXE;
                case 1 -> Items.STONE_PICKAXE;
                case 2 -> Items.IRON_PICKAXE;
                case 3 -> Items.DIAMOND_PICKAXE;
                case 4 -> Items.NETHERITE_PICKAXE;
                default -> Items.AIR;
            };
        } else if (toolClass == AxeItem.class) {
            item = switch (tier) {
                case 0 -> Items.WOODEN_AXE;
                case 1 -> Items.STONE_AXE;
                case 2 -> Items.IRON_AXE;
                case 3 -> Items.DIAMOND_AXE;
                case 4 -> Items.NETHERITE_AXE;
                default -> Items.AIR;
            };
        } else if (toolClass == ShovelItem.class) {
            item = switch (tier) {
                case 0 -> Items.WOODEN_SHOVEL;
                case 1 -> Items.STONE_SHOVEL;
                case 2 -> Items.IRON_SHOVEL;
                case 3 -> Items.DIAMOND_SHOVEL;
                case 4 -> Items.NETHERITE_SHOVEL;
                default -> Items.AIR;
            };
        } else if (toolClass == HoeItem.class) { // Optional
            item = switch (tier) {
                case 0 -> Items.WOODEN_HOE;
                case 1 -> Items.STONE_HOE;
                case 2 -> Items.IRON_HOE;
                case 3 -> Items.DIAMOND_HOE;
                case 4 -> Items.NETHERITE_HOE;
                default -> Items.AIR;
            };
        }
        return item != Items.AIR ? new ItemStack(item) : ItemStack.EMPTY;
    }

    @SubscribeEvent
    public static void onHarvestCheck(PlayerEvent.HarvestCheck event) {
        Player player = event.getEntity();
        if (!player.getData(ModAttachments.PLAYER_UPGRADE.get())) return;

        BlockState state = event.getTargetBlock();
        ItemStack tool = player.getMainHandItem();

        Class<? extends TieredItem> requiredClass = getRequiredToolClass(state);
        if (requiredClass == null) return;

        Tier heldTier = getHeldTier(tool, requiredClass);
        int required = getRequiredTier(state, requiredClass);

        int effectiveTier;
        if (heldTier != null) {
            // Correct tool: +1 tier
            effectiveTier = getTierLevel(heldTier) + 1;
        } else {
            // Fist, wrong tool, or non-tiered: wooden tier of the correct tool type
            effectiveTier = 0;
        }

        if (effectiveTier >= required) {
            event.setCanHarvest(true);
        }
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (!player.getData(ModAttachments.PLAYER_UPGRADE.get())) return;

        BlockState state = event.getState();
        ItemStack tool = player.getMainHandItem();

        Class<? extends TieredItem> requiredClass = getRequiredToolClass(state);
        if (requiredClass == null) return;

        Tier heldTier = getHeldTier(tool, requiredClass);

        int upgradeTier;
        if (heldTier != null) {
            // Correct tool: +1 tier speed
            upgradeTier = getTierLevel(heldTier) + 1;
        } else {
            // Fist, wrong tool, or non-tiered: wooden tier speed of the correct tool type
            upgradeTier = 0;
        }

        ItemStack dummy = getDummyTool(requiredClass, upgradeTier);
        if (!dummy.isEmpty()) {
            float upgradedSpeed = dummy.getDestroySpeed(state);
            if (upgradedSpeed > 1.0F) { // Only apply if meaningful
                event.setNewSpeed(Math.max(event.getNewSpeed(), upgradedSpeed));
            }
        }
    }

    private static int getTierLevel(@Nullable Tier tier) {
        if (tier == null) return -1;
        return switch (tier) {
            case Tiers.WOOD -> 0;
            case Tiers.STONE -> 1;
            case Tiers.IRON -> 2;
            case Tiers.DIAMOND -> 3;
            case Tiers.NETHERITE -> 4;
            default -> -1;
        };
    }
}