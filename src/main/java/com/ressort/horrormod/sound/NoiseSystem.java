package com.ressort.horrormod.sound;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

/**
 * Stores short-lived sound events separately for each dimension.
 * This is gameplay sound simulation; it does not record real microphone audio.
 */
public final class NoiseSystem {
    private static final int MAX_AGE_TICKS = 100;
    private static final Map<ResourceKey<Level>, NoiseTracker> TRACKERS = new ConcurrentHashMap<>();

    private NoiseSystem() {
    }

    public static void emit(ServerLevel level, NoiseEvent event) {
        NoiseTracker tracker = TRACKERS.computeIfAbsent(level.dimension(), ignored -> new NoiseTracker());
        tracker.record(event);
        tracker.discardOlderThan(level.getGameTime(), MAX_AGE_TICKS);
    }

    public static java.util.List<NoiseEvent> audibleAt(
            ServerLevel level, double x, double y, double z, float minimumAudibility
    ) {
        NoiseTracker tracker = TRACKERS.get(level.dimension());
        if (tracker == null) {
            return java.util.List.of();
        }
        tracker.discardOlderThan(level.getGameTime(), MAX_AGE_TICKS);
        return tracker.audibleAt(x, y, z, minimumAudibility);
    }

    public static void clearDimension(ResourceKey<Level> dimension) {
        NoiseTracker tracker = TRACKERS.remove(dimension);
        if (tracker != null) {
            tracker.clear();
        }
    }
}
