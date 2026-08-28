// SequenceGenerator.kt — produces deterministic sequences (forward + echo reverse)
package com.iamconanpeter.echorhythm

import java.util.Random

class SequenceGenerator(seed: Long) {
    private val rng = Random(seed)
    fun next(size: Int, colorCount: Int): List<Int> {
        require(size >= 1 && colorCount >= 2)
        return List(size) { rng.nextInt(colorCount) }
    }
    fun echo(forward: List<Int>): List<Int> = forward.reversed()
}
