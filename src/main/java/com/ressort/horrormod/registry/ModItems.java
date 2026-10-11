package com.ressort.horrormod.registry;

import com.ressort.horrormod.HorrorMod;
import com.ressort.horrormod.item.OldRadioItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    private static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(HorrorMod.MOD_ID);

    public static final DeferredItem<Item> OLD_RADIO = ITEMS.register(
            "old_radio",
            () -> new OldRadioItem(new Item.Properties().stacksTo(1))
    );

    private ModItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
