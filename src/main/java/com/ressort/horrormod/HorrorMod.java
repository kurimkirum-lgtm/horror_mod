package com.ressort.horrormod;

import com.ressort.horrormod.event.HorrorEvents;
import com.ressort.horrormod.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(HorrorMod.MOD_ID)
public final class HorrorMod {
    public static final String MOD_ID = "horror_mod";

    public HorrorMod(IEventBus modEventBus) {
        ModItems.register(modEventBus);
        NeoForge.EVENT_BUS.addListener(HorrorEvents::onPlayerTick);
        NeoForge.EVENT_BUS.addListener(HorrorEvents::onBlockBreak);
        NeoForge.EVENT_BUS.addListener(HorrorEvents::onLogout);
    }
}
