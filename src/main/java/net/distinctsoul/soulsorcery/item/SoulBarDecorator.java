package net.distinctsoul.soulsorcery.item;

import net.distinctsoul.soulsorcery.component.ModDataComponents;
import net.distinctsoul.soulsorcery.item.custom.SoulSwordSoulData;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.IItemDecorator;

public class SoulBarDecorator implements IItemDecorator {

    public static final SoulBarDecorator INSTANCE = new SoulBarDecorator();

    // Vanilla durability bar occupies y+13 to y+15 (2px tall, 13px wide).
    // We draw our soul bar immediately above it at y+11 to y+12.
    private static final int BAR_WIDTH  = 13;

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {

        SoulSwordSoulData data = stack.getOrDefault(ModDataComponents.SOUL_DATA.get(), SoulSwordSoulData.EMPTY);

        if (data.isEmpty()) return false;

        // Always draw the bar so the player knows the sword has this feature.
        // Bar origin: 2px from left edge of the item slot, 11px from the top.
        int barX = xOffset + 2;
        int fillY = yOffset + 12;

        // When the durability bar is NOT visible (sword undamaged): background
        // grows to 2px tall — one row below fillY plus fillY itself
        boolean durabilityShowing = stack.isBarVisible();
        int bgH = durabilityShowing ? 1 : 2;

        // ── Black background ────────────────────────────────────────────
        // fill(x1, y1, x2, y2, ARGB)  — vanilla GuiGraphics method
        guiGraphics.fill(RenderType.guiOverlay(),
                barX,             fillY,
                barX + BAR_WIDTH, fillY + bgH,
                0xFF000000);

        // ── Coloured fill ───────────────────────────────────────────────
        int fillWidth = Math.round(
                (data.count() / (float) SoulSwordSoulData.MAX_SOULS) * BAR_WIDTH);

        if (fillWidth > 0) {
            int colour = 0xFF000000 | data.soulType().getBarColor();
            guiGraphics.fill(RenderType.guiOverlay(),
                    barX,             fillY,
                    barX + fillWidth, fillY + 1,
                    colour);
        }
        return false;
    }
}
