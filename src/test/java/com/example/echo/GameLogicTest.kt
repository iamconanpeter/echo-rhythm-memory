package com.example.echo
import org.junit.Assert.*
import org.junit.Test
class GameLogicTest {
    @Test fun sequenceLengthInRange() {
        val seq = EchoSequence(List(5) { it % 4 })
        assertTrue(seq.rings.size in 3..8)
    }
    @Test fun echoReversesSequence() {
        val seq = EchoSequence(List(4) { it })
        val echo = seq.echo()
        assertEquals(seq.rings.reversed(), echo.rings)
    }
    @Test fun replayEngineValidatesCorrect() {
        val engine = ReplayEngine()
        assertTrue(engine.validate(List(4) { it % 4 }, List(4) { it % 4 }))
    }
    @Test fun replayEngineDetectsMismatch() {
        val engine = ReplayEngine()
        assertFalse(engine.validate(List(3) { 1 }, List(3) { 0 }))
    }
    @Test fun gameStateTracksBest() {
        val state = GameState()
        state.addScore(10)
        assertEquals(10, state.best)
        state.addScore(5)
        assertEquals(10, state.best)
    }
}
