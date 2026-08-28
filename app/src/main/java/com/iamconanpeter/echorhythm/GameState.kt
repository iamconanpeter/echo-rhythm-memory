// GameState.kt — current round state
package com.iamconanpeter.echorhythm

class GameState {
    var round: Int = 1
    var streak: Int = 0
    var best: Int = 0
    var echoMode: Boolean = false
    var listenTokens: Int = 2
    fun nextRound() { round += 1; listenTokens = 2 }
    fun onComplete(score: Int) { streak += 1; if (score > best) best = score }
    fun onFail() { streak = 0; listenTokens = 2 }
    fun useToken(): Boolean = if (listenTokens > 0) { listenTokens -= 1; true } else false
}
