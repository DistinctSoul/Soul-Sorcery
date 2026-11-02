package net.distinctsoul.soulsorcery.event;

import net.distinctsoul.soulsorcery.component.ModDataComponents;
import net.distinctsoul.soulsorcery.item.custom.SoulStealerItem;
import net.distinctsoul.soulsorcery.item.custom.SoulSwordSoulData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * Server-side handler.
 * When a mob is killed with the Soul Sword, one soul is absorbed into the
 * sword's DataComponent — provided the type matches any already stored souls
 * and capacity has not been reached.
 */

public class SoulSwordEventHandler {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity deceased = event.getEntity();

        // Players' souls are not collectible
        if (deceased instanceof Player) return;

        // The killer must be a player
        Entity killerEntity = event.getSource().getEntity();
        if (!(killerEntity instanceof Player player)) return;

        // The weapon must be a Soul Sword
        ItemStack weapon = player.getMainHandItem();
        if (!(weapon.getItem() instanceof SoulStealerItem)) return;

        SoulSwordSoulData.SoulType incomingType = classifyMob(deceased);

        SoulSwordSoulData current = weapon.getOrDefault(ModDataComponents.SOUL_DATA.get(), SoulSwordSoulData.EMPTY);

        if (!current.canAccept(incomingType)) return;

        // Determine the stored type: keep existing or use the new mob's type
        SoulSwordSoulData.SoulType storedType = current.isEmpty() ? incomingType : current.soulType();

        weapon.set(
                ModDataComponents.SOUL_DATA.get(),
                new SoulSwordSoulData(current.count() + 1, storedType)
        );
    }


    private static SoulSwordSoulData.SoulType classifyMob(LivingEntity entity) {

        if (entity.getType().getCategory() == MobCategory.MONSTER) {
            return SoulSwordSoulData.SoulType.TAINTED;
        }
        return SoulSwordSoulData.SoulType.PURE;
    }
}
