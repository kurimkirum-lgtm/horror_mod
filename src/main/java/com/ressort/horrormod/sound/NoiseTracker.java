package com.ressort.horrormod.sound;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Bounded recent-sound buffer. This class is deliberately independent of Minecraft APIs
 * so its behaviour can be tested without starting a game client.
 */
public final class NoiseTracker {
    private static final int DEFAULT_CAPACITY = 128;

    private final Deque<NoiseEvent> events = new ArrayDeque<>();
    private final int capacity;

    public NoiseTracker() {
        this(DEFAULT_CAPACITY);
    }

    public NoiseTracker(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.capacity = capacity;
    }

    public void record(NoiseEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("event cannot be null");
        }

        while (events.size() >= capacity) {
            events.removeFirst();
        }
        events.addLast(event);
    }

    public List<NoiseEvent> recentEvents() {
        return List.copyOf(events);
    }

    public List<NoiseEvent> audibleAt(double x, double y, double z, float minimumAudibility) {
        List<NoiseEvent> result = new ArrayList<>();
        for (NoiseEvent event : events) {
            if (event.audibilityAt(x, y, z) >= minimumAudibility) {
                result.add(event);
            }
        }
        return List.copyOf(result);
    }

    public void discardOlderThan(long currentTick, long maxAgeTicks) {
        if (maxAgeTicks < 0) {
            throw new IllegalArgumentException("maxAgeTicks must be non-negative");
        }
        while (!events.isEmpty()
                && currentTick - events.peekFirst().createdAtTick() > maxAgeTicks) {
            events.removeFirst();
        }
    }

    public void clear() {
        events.clear();
    }

    public int size() {
        return events.size();
    }
}
