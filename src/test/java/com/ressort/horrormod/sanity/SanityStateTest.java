package com.ressort.horrormod.sanity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SanityStateTest {
    @Test
    void clampsSanityAndStress() {
        SanityState state = new SanityState();
        state.setSanity(150);
        state.setStress(-10);
        assertEquals(100, state.sanity());
        assertEquals(0, state.stress());
    }

    @Test
    void stressRaisesPulseOverTime() {
        SanityState state = new SanityState();
        state.setStress(100);
        for (int i = 0; i < 40; i++) {
            state.tick(false, false, false, true);
        }
        assertTrue(state.pulse() > 60);
    }

    @Test
    void sustainedStressReducesSanity() {
        SanityState state = new SanityState();
        state.setStress(100);
        float initialSanity = state.sanity();

        for (int i = 0; i < 200; i++) {
            state.tick(false, false, false, true);
        }

        assertTrue(state.sanity() < initialSanity);
    }
}
