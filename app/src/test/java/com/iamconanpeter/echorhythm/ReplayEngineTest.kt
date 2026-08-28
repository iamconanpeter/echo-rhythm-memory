package com.iamconanpeter.echorhythm

import org.junit.Assert.assertEquals
import org.junit.Test

class ReplayEngineTest {
    @Test fun `complete when all correct`() {
        val r = ReplayEngine().evaluate(listOf(0,1,2), listOf(0,1,2))
        assertEquals(ReplayEngine.Result.COMPLETE, r)
    }
    @Test fun `wrong on mismatch`() {
        assertEquals(ReplayEngine.Result.WRONG, ReplayEngine().evaluate(listOf(0,1,2), listOf(0,2)))
    }
    @Test fun `in progress when partial`() {
        assertEquals(ReplayEngine.Result.IN_PROGRESS, ReplayEngine().evaluate(listOf(0,1,2), listOf(0,1)))
    }
    @Test fun `score scales with steps`() {
        assertEquals(50, ReplayEngine().score(5, 0))
        assertEquals(20, ReplayEngine().score(5, 10))
    }
}
