package com.ressort.horrormod.sanity;

/** Runtime state for one connected player. Persistence can be added once gameplay values are tuned. */
public final class PlayerHorrorData {
    private final SanityState sanity = new SanityState();
    private long lastNoiseTick = Long.MIN_VALUE;
    private long lastHudTick = Long.MIN_VALUE;
    private long lastHallucinationTick = Long.MIN_VALUE;

    public SanityState sanity() {
        return sanity;
    }

    public boolean canEmitNoise(long gameTick, int interval) {
        if (gameTick - lastNoiseTick < interval) {
            return false;
        }
        lastNoiseTick = gameTick;
        return true;
    }

    public boolean shouldRefreshHud(long gameTick) {
        if (gameTick - lastHudTick < 20) {
            return false;
        }
        lastHudTick = gameTick;
        return true;
    }

    public boolean canTriggerHallucination(long gameTick, int interval) {
        if (gameTick - lastHallucinationTick < interval) {
            return false;
        }
        lastHallucinationTick = gameTick;
        return true;
    }
}
