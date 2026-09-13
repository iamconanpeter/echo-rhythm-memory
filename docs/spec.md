# Echo Rhythm Memory - Spec (codex plan-mode)
Q&A (assumed):
- Core fantasy: Audio-visual memory training with a rhythmic pulse
- 10-second hook: Colorful rings pulse with distinct tones — player must remember and replay the sequence
- Daily/weekly loop: Streak-based progression with daily seeded sequences and unlockable echo modes
- Session length: 30s–2m bursts per round
- Skill vs luck: Skill-based (memory + timing), no RNG in scoring; seed-based daily patterns for replayability
- Fail-state fairness: "Listen again" token available once per round to replay the sequence
- Difficulty ramp: Sequence length increases each round (2→3→4→5+ taps); echo mode adds reverse replay
- Distinctive mechanic: Bidirectional recall (forward + echo reverse) doubles cognitive depth vs. standard Simon clones
- Art/animation scope: Minimal — colored rings with pulse animation, soft gradient background
- Audio/feedback plan: Distinct tones per ring, haptic feedback on tap, soft chime on success
- Monetization-safe design: Optional ad after session complete; no dark patterns; premium feel without pay-to-win
- Technical constraints: Target API 34, minSdk 26; 60fps ring animations; <50MB APK size
USP: "Remember the beat — build the echo."

## Differentiation
- 3 differentiators: (1) Echo/reverse mode doubles cognitive depth, (2) Daily seeded sequences for endless replay, (3) Listen-again token reduces frustration

## Retention
- 3 retention hooks: (1) Streak counter with daily reset, (2) Unlock echo/skip variants at milestones, (3) Best score leaderboard

## Quality bars
- Juice/feedback: Satisfying tone chime + ring pulse animation on correct tap
- Readability: Large colored rings with clear labels (1–8), high contrast
- Smoothness: 60fps animations, <100ms input latency
