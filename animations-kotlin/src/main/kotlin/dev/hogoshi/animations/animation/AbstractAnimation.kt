package dev.hogoshi.animations.animation

import dev.hogoshi.animations.easing.Easings

/**
 * Animation basic abstraction
 * @author hogoshi
 */
abstract class AbstractAnimation {

    var value = 0.0
    var start = 0L
    var duration = 0.0
    var from = 0.0
    var to = 0.0
    var easing = Easings.LINEAR
    var debug = false

    /**
     * Use it before rendering element which uses animation's value
     * @return boolean which means if animation is alive
     */
    fun update(): Boolean {
        val alive = isAlive()

        if (alive) {
            value = interpolate(from, to, easing.ease(calculatePart()))
        } else {
            start = 0
            value = to
        }

        return alive
    }

    /**
     * @return boolean which means if animation is alive
     */
    fun isAlive() = !isDone()

    /**
     * @return boolean which means if animation is done
     */
    fun isDone() = calculatePart() >= 1.0

    /**
     * @return calculates current x from 0 to 1 for calculating easing's current value
     */
    private fun calculatePart() = (System.currentTimeMillis() - start).toDouble() / duration

    /**
     * @return linear interpolation
     */
    private fun interpolate(start: Double, end: Double, pct: Double) = start + (end - start) * pct

}