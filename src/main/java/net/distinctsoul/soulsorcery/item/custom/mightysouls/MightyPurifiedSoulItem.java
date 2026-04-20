package net.distinctsoul.soulsorcery.item.custom.mightysouls;

import net.distinctsoul.soulsorcery.attachment.ModAttachments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MightyPurifiedSoulItem extends Item {
    public MightyPurifiedSoulItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE)
                .food(new FoodProperties.Builder()
                .nutrition(0)
                .saturationModifier(0F)
                .alwaysEdible()
                .build()));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {

        if (!world.isClientSide && entity instanceof Player player) {

            if (!player.getData(ModAttachments.PLAYER_UPGRADE.get())) {
                player.setData(ModAttachments.PLAYER_UPGRADE.get(), true);
                world.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 0.5f, world.random.nextFloat() * 0.1f + 0.9f);
                player.displayClientMessage(Component.translatable("item.soulsorcery.consumed_soul")
                        .withStyle(ChatFormatting.ITALIC)
                        .withStyle(ChatFormatting.DARK_PURPLE), false);

            }
        }
        return super.finishUsingItem(stack, world, entity);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.getData(ModAttachments.PLAYER_UPGRADE.get())) {

            if (!level.isClientSide) {
                player.displayClientMessage(Component.translatable("item.soulsorcery.already_used_soul")
                        .withStyle(ChatFormatting.RED), false);
            }
            return InteractionResultHolder.fail(stack);
        }
        return super.use(level, player, hand);
    }
}
