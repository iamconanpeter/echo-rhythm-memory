package com.iamconanpeter.echorhythm

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SequenceGeneratorTest {
    @Test fun `deterministic for same seed`() {
        val a = SequenceGenerator(42L).next(10, 4)
        val b = SequenceGenerator(42L).next(10, 4)
        assertEquals(a, b)
    }
    @Test fun `different seeds differ`() {
        val a = SequenceGenerator(1L).next(20, 4)
        val b = SequenceGenerator(2L).next(20, 4)
        assertTrue(a != b)
    }
    @Test fun `echo reverses sequence`() {
        val s = listOf(0, 3, 1, 2)
        assertEquals(listOf(2, 1, 3, 0), SequenceGenerator(0L).echo(s))
    }
    @Test fun `values in range`() {
        val s = SequenceGenerator(7L).next(50, 4)
        assertTrue(s.all { it in 0..3 })
    }
}
