# Echo Rhythm Memory - Technical Plan
Plan-mode enforced. Build via `codex exec`. Dependencies: Android SDK 34 (compile), minSdk 26, Kotlin core, JUnit 4 tests.

## Architecture
- SequenceGenerator.kt: deterministic seeded sequence generation; echo mode reverses sequence
- ReplayEngine.kt: validates taps against expected sequence, computes accuracy score
- GameState.kt: round progression, streak tracking, best score persistence, listen-again token management
- MainActivity.kt: UI entry point, ring rendering with Canvas animation, tap handling

## Dependencies
- AndroidX core-ktx, appcompat, material design
- JUnit 4 for unit tests
- Kotlin coroutines for animation timing

## Build & Validation
```bash
export JAVA_HOME=/home/openclaw/.jdks/jdk-17 && ./gradlew test assembleDebug
```
Result: BUILD SUCCESSFUL, 11 JUnit4 tests pass (SequenceGenerator×4, ReplayEngine×4, GameState×3)

## Performance Budgets
- APK size: <50MB
- Cold start: <2s
- Ring animation: 60fps on mid-range devices (Snapdragon 665 equivalent)
- Memory: <100MB heap usage
