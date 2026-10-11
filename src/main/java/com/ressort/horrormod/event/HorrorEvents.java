package com.ressort.horrormod.event;

import com.ressort.horrormod.sanity.PlayerHorrorData;
import com.ressort.horrormod.sound.NoiseEvent;
import com.ressort.horrormod.sound.NoiseSystem;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** Server-authoritative event bridge for the first playable sound/sanity prototype. */
public final class HorrorEvents {
    private static final Map<UUID, PlayerHorrorData> PLAYERS = new ConcurrentHashMap<>();

    private HorrorEvents() {
    }

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || player.isSpectator()) {
            return;
        }

        long tick = player.level().getGameTime();
        PlayerHorrorData data = PLAYERS.computeIfAbsent(player.getUUID(), ignored -> new PlayerHorrorData());
        double horizontalSpeed = player.getDeltaMovement().horizontalDistanceSqr();

        if (player.onGround() && horizontalSpeed > 0.0025) {
            boolean sprinting = player.isSprinting();
            int interval = sprinting ? 6 : 11;
            if (data.canEmitNoise(tick, interval)) {
                NoiseSystem.emit(player.serverLevel(), new NoiseEvent(
                        player.getX(), player.getY(), player.getZ(),
                        sprinting ? 0.95f : 0.28f,
                        sprinting ? 14.0 : 6.0,
                        sprinting ? NoiseEvent.Type.SPRINT : NoiseEvent.Type.FOOTSTEP,
                        tick
                ));
                data.sanity().addStress(sprinting ? 0.18f : 0.025f);
            }
        }

        data.sanity().tick(player.level().isDay() && player.level().canSeeSky(player.blockPosition()),
                player.isSprinting(), player.getHealth() < player.getMaxHealth() * 0.5f, false);

        if (data.shouldRefreshHud(tick)) {
            player.displayClientMessage(Component.literal(String.format(
                    "§7ПСИХИКА §f%.0f%%  §cПУЛЬС §f%.0f",
                    data.sanity().sanity(), data.sanity().pulse()
            )), true);
        }
    }

    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) {
            return;
        }
        NoiseSystem.emit(player.serverLevel(), new NoiseEvent(
                event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
                0.8f, 12.0, NoiseEvent.Type.BLOCK_BREAK, player.level().getGameTime()
        ));
        PLAYERS.computeIfAbsent(player.getUUID(), ignored -> new PlayerHorrorData())
                .sanity().addStress(0.08f);
    }

    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        PLAYERS.remove(event.getEntity().getUUID());
    }

    public static void emitRadioNoise(ServerPlayer player) {
        long tick = player.level().getGameTime();
        NoiseSystem.emit(player.serverLevel(), new NoiseEvent(
                player.getX(), player.getY(), player.getZ(),
                1.0f, 24.0, NoiseEvent.Type.RADIO, tick
        ));
        PLAYERS.computeIfAbsent(player.getUUID(), ignored -> new PlayerHorrorData())
                .sanity().addStress(0.25f);
    }
}
