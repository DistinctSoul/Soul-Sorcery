package net.distinctsoul.soulsorcery.component;

import net.distinctsoul.soulsorcery.SoulSorcery;
import net.distinctsoul.soulsorcery.item.custom.SoulSwordSoulData;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {

    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, SoulSorcery.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SoulSwordSoulData>> SOUL_DATA =
            DATA_COMPONENT_TYPES.registerComponentType(
                    "soul_data",
                    builder -> builder
                            .persistent(SoulSwordSoulData.CODEC)          // saved to disk
                            .networkSynchronized(SoulSwordSoulData.STREAM_CODEC) // synced to client
            );

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
