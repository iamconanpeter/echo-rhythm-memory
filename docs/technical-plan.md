# Technical Plan — Echo Rhythm Memory

Stack: Android (minSdk 24), single Activity, Canvas view for ring grid, MediaPlayer for tones.
Architecture: SequenceGenerator (seeded) -> ReplayEngine -> ScoreTracker. Unit testable pure logic for sequence/replay/echo.
Performance budget: 60 FPS, <5 MB install, 4 tone assets <1 MB, no network.
Test strategy: JUnit4 for SequenceGenerator + ReplayEngine; build via `./gradlew test assembleDebug`.
