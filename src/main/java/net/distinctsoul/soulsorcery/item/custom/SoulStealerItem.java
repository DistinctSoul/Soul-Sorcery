package net.distinctsoul.soulsorcery.item.custom;

import net.distinctsoul.soulsorcery.component.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.*;

import java.util.List;

public class SoulStealerItem extends SwordItem {
    public SoulStealerItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    /**
     * Appends the soul tooltip line, e.g.
     *   "Hostile Souls: 2/3"  or  "Souls: 0/3"  when empty.
     */
    public void appendHoverText(ItemStack stack,
                                Item.TooltipContext context,
                                List<Component> tooltip,
                                TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        SoulSwordSoulData data = stack.getOrDefault(ModDataComponents.SOUL_DATA.get(), SoulSwordSoulData.EMPTY);

        // Pick a chat colour matching the soul type
        int colour = data.soulType().getBarColor();
        Style style = Style.EMPTY.withColor(colour);

        String line = data.soulType().getDisplayName() + ": " + data.count() + "/" + SoulSwordSoulData.MAX_SOULS;

        tooltip.add(Component.literal(line).withStyle(style));
    }
}
