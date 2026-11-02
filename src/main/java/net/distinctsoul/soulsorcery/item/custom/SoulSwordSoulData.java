package net.distinctsoul.soulsorcery.item.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record SoulSwordSoulData(int count, SoulType soulType) {

    public static final int MAX_SOULS = 3;

    public enum SoulType {
        NONE, // No souls stored (empty state)
        PURE, // Passive and Neutral mobs
        TAINTED; // Hostile mobs

        /** Display name shown in the tooltip. */
        public String getDisplayName() {
            return switch (this) {
                case NONE -> "Souls";
                case PURE -> "Pure Souls";
                case TAINTED -> "Tainted Souls";
            };
        }

        /**
         * ARGB bar fill colour for the soul HUD bar.
         * The 0xFF alpha prefix is added when rendering.
         */
        public int getBarColor() {
            return switch (this) {
                case NONE    -> 0x888888; // grey  – shown when bar is empty
                case PURE -> 0x008CFF; // sky blue
                case TAINTED -> 0xFA50FF; // lavender
            };
        }
    }

    /** Canonical empty value used as a default. */
    public static final SoulSwordSoulData EMPTY = new SoulSwordSoulData(0, SoulType.NONE);

    // ── Codec (disk persistence) ───────────────────────────────────────────
    public static final Codec<SoulSwordSoulData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("count").forGetter(SoulSwordSoulData::count),
                    // Store the enum as its ordinal so the codec stays simple
                    Codec.INT.fieldOf("soul_type_ordinal").forGetter(d -> d.soulType().ordinal())
            ).apply(instance, (count, ordinal) -> new SoulSwordSoulData(count, SoulType.values()[ordinal]))
        );

    // ── StreamCodec (network sync) ────────────────────────────────────────
    public static final StreamCodec<ByteBuf, SoulSwordSoulData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SoulSwordSoulData::count,
            ByteBufCodecs.INT, d -> d.soulType().ordinal(),
            (count, ordinal) -> new SoulSwordSoulData(count, SoulType.values()[ordinal])
    );

    // ── Helpers ────────────────────────────────────────────────────────────

    public boolean isFull() {
        return count >= MAX_SOULS;
    }

    public boolean isEmpty() {
        return count == 0 || soulType == SoulType.NONE;
    }

    /**
     * Returns true if a soul of the given type can be added without
     * violating the "no mixing" rule or exceeding capacity.
     */
    public boolean canAccept(SoulType incoming) {
        if (isFull()) return false;
        // Empty sword accepts any type; non-empty requires a match
        return isEmpty() || soulType == incoming;
    }
}
