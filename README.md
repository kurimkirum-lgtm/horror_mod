# DON'T MAKE A SOUND — Horror Mod

A Minecraft horror mod built around sound-driven entities, sanity, an analog radio, and adaptive events.

## Current development baseline

- Minecraft: 1.21.1
- Java: 21
- Mod loader: NeoForge 21.1.x
- Mod ID: `horror_mod`
- Build system: Gradle with NeoForge ModDevGradle

## Current foundation

- Mod entry point and item registration.
- Unique `old_radio` item.
- First right-click interaction for the radio.
- English and Russian item translations.
- Resource model placeholder.
- Offline-first design: external AI is not required for core gameplay.

## Build

Install JDK 21 and a compatible Gradle installation, then run:

```bash
gradle build
```

For development runs:

```bash
gradle runClient
gradle runServer
```

A Gradle wrapper has not been added yet.

## Planned architecture

- `sound`: sound event capture, noise levels, and detection helpers.
- `sanity`: sanity, stress, pulse, and hallucination state.
- `radio`: tuning, transmissions, interference, and story channel.
- `entity`: monster state machines and individual behaviours.
- `world`: world events and story locations.
- `ai`: optional Grok and LM Studio adapters, validated actions, and fallback AI.
- `client`: HUD, radio screen, and audio presentation.

## Development principles

1. Core gameplay must work without a network connection or language model.
2. AI providers may choose only from validated, predefined actions.
3. Never block the Minecraft game thread while waiting for a model response.
4. Server-authoritative world state; client-side presentation where appropriate.
5. Do not collect microphone input unless the player explicitly enables it.
6. Add tests for sound detection, sanity transitions, and monster state machines as those systems are implemented.

## Status

This is the initial scaffold, not a complete playable horror mod. The radio currently prints a placeholder transmission. A custom texture, radio GUI, sound engine, sanity system, monster entities, and AI integrations remain to be implemented.
