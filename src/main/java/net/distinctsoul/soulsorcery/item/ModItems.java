package net.distinctsoul.soulsorcery.item;

import net.distinctsoul.soulsorcery.SoulSorcery;
import net.distinctsoul.soulsorcery.item.custom.CorruptedSoulItem;
import net.distinctsoul.soulsorcery.item.custom.MightyPurifiedSoulItem;
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

    public static final DeferredItem<Item> CORRUPTED_SOUL = ITEMS.register("corrupted_soul",
            () -> new CorruptedSoulItem(new Item.Properties().stacksTo(5)));
    public static final DeferredItem<Item> MIGHTY_PURIFIED_SOUL = ITEMS.register("mighty_purified_soul",
            () -> new MightyPurifiedSoulItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
