# DON'T MAKE A SOUND — Horror Mod

A Minecraft horror mod built around sound-driven entities, sanity, an analog radio, and adaptive events.

## Development baseline

- Minecraft: 1.21.1
- Java: 21
- Mod loader: NeoForge 21.1.x
- Mod ID: `horror_mod`
- Build system: Gradle with NeoForge ModDevGradle

## Implemented foundation

- NeoForge mod entry point and item registration.
- Old Radio item; using it emits a loud server-side radio noise event.
- Server-side player movement noise: walking and sprinting use different radii and intensity.
- Block breaking emits a sound event.
- Per-dimension bounded sound-event buffers with age-based cleanup.
- Audibility falloff and a helper for selecting the loudest audible sound for future monster AI.
- Runtime per-player sanity, stress, and pulse state.
- Prototype action-bar display for sanity and pulse.
- JUnit tests for sound falloff, tracker capacity/expiry, and sanity bounds.
- GitHub Actions build workflow.
- Offline-first: external AI is not required for core gameplay.

## Try it in a development client

1. Install JDK 21 and Gradle 8.10.2 or compatible.
2. Run `gradle runClient`.
3. Create a world and use the **Old Radio** item if you have it in your inventory (or obtain it with `/give @s horror_mod:old_radio`).
4. Walk, sprint, break blocks, and use the radio. The action bar currently displays a prototype sanity/pulse readout.

Build and tests:

```bash
gradle test
gradle build
```

A Gradle wrapper is not included yet. The CI job installs Gradle directly.

## Architecture

- `sound`: noise events, per-dimension tracking, falloff, and detection queries.
- `sanity`: sanity, stress, pulse, and per-player runtime data.
- `item`: old radio item.
- `event`: server-side bridge from player actions to sound/sanity systems.
- Planned: `radio` tuning/transmissions, `entity` monster state machines, `client` custom HUD and radio screen, `ai` optional model adapters.

## Known limitations

- Sanity data is runtime-only and resets when a player disconnects or the server restarts.
- The action-bar readout is a temporary HUD, not a custom overlay.
- Walking/sprinting noise is simulated from movement state; it does not use real microphone input.
- Noise events are ready for monster AI, but no monster currently consumes them.
- Radio tuning, story transmissions, custom radio texture, monster entities, and hallucinations are not implemented yet.
- The current version has not been manually playtested. CI/build results should be checked before treating this as a working release.

## Design constraints

1. Core gameplay must work without a network connection or language model.
2. AI providers may choose only from validated, predefined actions.
3. Never block the Minecraft game thread while waiting for a model response.
4. Keep world state server-authoritative and presentation client-side where practical.
5. Never collect microphone input unless the player explicitly enables it.
6. Add tests as sound detection, sanity transitions, and monster state machines evolve.
