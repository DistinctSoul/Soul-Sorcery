package net.distinctsoul.soulsorcery.item;

import net.distinctsoul.soulsorcery.SoulSorcery;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SoulSorcery.MOD_ID);

    public static final DeferredItem<Item> FALIUM_FRAGMENTS = ITEMS.register("falium_fragments",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FALIUM = ITEMS.register("falium",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
