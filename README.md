# Echo Rhythm Memory

Lightweight audio-visual memory game for Android. MVP scope.

## Run
- Open in Android Studio (Giraffe+) or run `./gradlew test assembleDebug` with a local Android SDK.
- Unit tests cover SequenceGenerator, ReplayEngine, GameState.

## Architecture
- SequenceGenerator: deterministic seeded sequence + echo reverse.
- ReplayEngine: validates taps, computes score.
- GameState: round/streak/best/listen-tokens.

## Codex CLI evidence
- Plan-mode artifacts produced via `codex exec` (see docs/).
- Implementation scaffolded for compilation.
