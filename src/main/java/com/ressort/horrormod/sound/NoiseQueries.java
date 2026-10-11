package com.ressort.horrormod.sound;

import java.util.Comparator;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;

/** Query helpers intended for monster AI; they do not spawn or move entities themselves. */
public final class NoiseQueries {
    private NoiseQueries() {
    }

    public static Optional<NoiseEvent> loudestAudible(
            ServerLevel level, double x, double y, double z, float threshold
    ) {
        return NoiseSystem.audibleAt(level, x, y, z, threshold).stream()
                .max(Comparator.comparingDouble(event -> event.audibilityAt(x, y, z)));
    }
}
