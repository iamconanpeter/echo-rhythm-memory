package com.iamconanpeter.echorhythm

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GameStateTest {
    @Test fun `streak tracks completions and fails`() {
        val g = GameState()
        g.onComplete(10); g.onComplete(20)
        assertEquals(2, g.streak)
        assertEquals(20, g.best)
        g.onFail()
        assertEquals(0, g.streak)
    }
    @Test fun `listen tokens decrement and exhaust`() {
        val g = GameState()
        assertTrue(g.useToken())
        assertTrue(g.useToken())
        assertFalse(g.useToken())
    }
    @Test fun `next round resets tokens`() {
        val g = GameState()
        g.useToken(); g.nextRound()
        assertEquals(2, g.listenTokens)
    }
}
