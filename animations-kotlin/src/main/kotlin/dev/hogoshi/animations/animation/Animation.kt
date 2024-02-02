package dev.hogoshi.animations.animation

import dev.hogoshi.animations.easing.Easing
import dev.hogoshi.animations.easing.Easings

/**
 * Basic animation
 * @author hogoshi
 */
class Animation : AbstractAnimation() {

    fun animate(valueTo: Double, duration: Double) = animate(valueTo, duration, Easings.LINEAR)

    fun animate(valueTo: Double, duration: Double, easing: Easing) = animate(valueTo, duration, easing, false)

    fun animate(valueTo: Double, duration: Double, easing: Easing, debug: Boolean) {
        this.debug = debug
        if (isAlive() && (valueTo == from || valueTo == to || valueTo == value)) {
            if (debug) println("Animate cancelled due to valueTo equals fromValue")
            return
        }

        this.easing = easing
        this.duration = duration * 1000
        this.start = System.currentTimeMillis()
        this.from = value
        this.to = valueTo

        if (debug) println(
            """#animate {
                to value: $to
                from value: $value
                duration: $duration
            }"""
        )
    }

}