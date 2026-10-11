package com.ressort.horrormod.sanity;

/**
 * Per-player psychological state. Values are gameplay abstractions, not medical metrics.
 * The class contains no Minecraft dependencies so transitions can be tested in isolation.
 */
public final class SanityState {
    public static final float MIN = 0.0f;
    public static final float MAX = 100.0f;

    private float sanity = MAX;
    private float stress;
    private float pulse = 60.0f;

    public float sanity() {
        return sanity;
    }

    public float stress() {
        return stress;
    }

    public float pulse() {
        return pulse;
    }

    public void setSanity(float value) {
        sanity = clamp(value, MIN, MAX);
    }

    public void addSanity(float amount) {
        setSanity(sanity + amount);
    }

    public void setStress(float value) {
        stress = clamp(value, 0.0f, 100.0f);
    }

    public void addStress(float amount) {
        setStress(stress + amount);
    }

    /**
     * Advances the abstract state by one game tick.
     * Callers should supply whether the player is currently in a safe environment.
     */
    public void tick(boolean safe, boolean sprinting, boolean injured, boolean frightened) {
        if (safe) {
            addSanity(0.015f);
            addStress(-0.04f);
        } else {
            addStress(frightened ? 0.12f : -0.005f);
        }

        float targetPulse = 60.0f
                + stress * 0.65f
                + (sprinting ? 22.0f : 0.0f)
                + (injured ? 15.0f : 0.0f);
        pulse += (targetPulse - pulse) * 0.04f;
        pulse = clamp(pulse, 45.0f, 180.0f);
    }

    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
