package com.ressort.horrormod;

import com.ressort.horrormod.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(HorrorMod.MOD_ID)
public final class HorrorMod {
    public static final String MOD_ID = "horror_mod";

    public HorrorMod(IEventBus modEventBus) {
        ModItems.register(modEventBus);
    }
}
