package com.ressort.horrormod.sound;

/**
 * A lightweight, game-independent representation of a sound that monsters may investigate.
 * Volume and radius are gameplay values, not physical decibels.
 */
public record NoiseEvent(
        double x,
        double y,
        double z,
        float intensity,
        double radius,
        Type type,
        long createdAtTick
) {
    public NoiseEvent {
        if (intensity < 0.0f || radius < 0.0) {
            throw new IllegalArgumentException("Intensity and radius must be non-negative");
        }
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
    }

    public enum Type {
        FOOTSTEP,
        SPRINT,
        JUMP_LAND,
        BLOCK_BREAK,
        BLOCK_PLACE,
        DOOR,
        COMBAT,
        RADIO,
        EXPLOSION,
        OTHER
    }

    /**
     * Returns an estimated audibility score at the given position.
     * Obstacles and creature-specific hearing should be applied by the caller.
     */
    public float audibilityAt(double targetX, double targetY, double targetZ) {
        if (radius <= 0.0) {
            return 0.0f;
        }

        double dx = x - targetX;
        double dy = y - targetY;
        double dz = z - targetZ;
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        if (distance >= radius) {
            return 0.0f;
        }

        double falloff = 1.0 - (distance / radius);
        return (float) (intensity * falloff * falloff);
    }
}
