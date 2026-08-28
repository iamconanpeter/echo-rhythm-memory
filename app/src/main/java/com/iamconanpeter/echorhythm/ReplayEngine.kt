// ReplayEngine.kt — validates player taps against target sequence
package com.iamconanpeter.echorhythm

class ReplayEngine {
    enum class Result { IN_PROGRESS, COMPLETE, WRONG, PARTIAL }

    fun evaluate(target: List<Int>, taps: List<Int>): Result {
        if (taps.size > target.size) return Result.WRONG
        for (i in taps.indices) if (taps[i] != target[i]) return Result.WRONG
        return if (taps.size == target.size) Result.COMPLETE else Result.IN_PROGRESS
    }

    fun score(steps: Int, mistakes: Int): Int = (steps * 10) - (mistakes * 3)
}
