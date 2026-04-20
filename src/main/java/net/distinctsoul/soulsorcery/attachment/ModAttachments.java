package net.distinctsoul.soulsorcery.attachment;

import com.mojang.serialization.Codec;
import net.distinctsoul.soulsorcery.SoulSorcery;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, SoulSorcery.MOD_ID);

    // The registry object is created automatically by NeoForgeRegistries
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> PLAYER_UPGRADE =
            ATTACHMENTS.register("player_upgrade", () ->
                    AttachmentType.builder(holder -> false)
                            .serialize(Codec.BOOL.fieldOf("player_upgrade").codec())  // CORRECT: Use MapCodec for persistence
                            .sync(StreamCodec.of(
                                    FriendlyByteBuf::writeBoolean,
                                    FriendlyByteBuf::readBoolean
                            ))
                            .copyOnDeath()
                            .build()
            );
}
