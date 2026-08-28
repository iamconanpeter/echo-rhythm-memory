package com.example.echo
class GameState(var round: Int = 1, var score: Int = 0, var best: Int = 0, var listenTokens: Int = 1) { fun addScore(n: Int) { score += n; if (score > best) best = score } }
