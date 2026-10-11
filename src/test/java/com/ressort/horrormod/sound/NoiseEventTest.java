package com.ressort.horrormod.sound;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NoiseEventTest {
    @Test
    void soundIsStrongestAtSourceAndFadesWithDistance() {
        NoiseEvent event = new NoiseEvent(0, 0, 0, 1.0f, 10.0, NoiseEvent.Type.FOOTSTEP, 1);
        assertEquals(1.0f, event.audibilityAt(0, 0, 0), 0.0001f);
        assertEquals(0.25f, event.audibilityAt(5, 0, 0), 0.0001f);
        assertEquals(0.0f, event.audibilityAt(10, 0, 0), 0.0001f);
    }

    @Test
    void rejectsNegativeRadius() {
        assertThrows(IllegalArgumentException.class,
                () -> new NoiseEvent(0, 0, 0, 1.0f, -1.0, NoiseEvent.Type.OTHER, 0));
    }
}
