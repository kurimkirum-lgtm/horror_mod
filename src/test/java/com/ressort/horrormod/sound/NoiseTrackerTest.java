package com.ressort.horrormod.sound;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NoiseTrackerTest {
    @Test
    void keepsOnlyNewestEventsWhenAtCapacity() {
        NoiseTracker tracker = new NoiseTracker(2);
        tracker.record(new NoiseEvent(0, 0, 0, 1, 5, NoiseEvent.Type.FOOTSTEP, 1));
        tracker.record(new NoiseEvent(1, 0, 0, 1, 5, NoiseEvent.Type.FOOTSTEP, 2));
        tracker.record(new NoiseEvent(2, 0, 0, 1, 5, NoiseEvent.Type.FOOTSTEP, 3));
        assertEquals(2, tracker.size());
        assertEquals(2, tracker.recentEvents().get(0).createdAtTick());
    }

    @Test
    void removesExpiredEvents() {
        NoiseTracker tracker = new NoiseTracker();
        tracker.record(new NoiseEvent(0, 0, 0, 1, 5, NoiseEvent.Type.FOOTSTEP, 1));
        tracker.discardOlderThan(10, 5);
        assertEquals(0, tracker.size());
    }
}
