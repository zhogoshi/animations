package dev.hogoshi.animations.animation

import dev.hogoshi.animations.AnimationContext

/**
 * Animation which stores as this class/object inside of context
 * @author hogoshi
 */
class ContextAnimation(
    val name: String,
    private val context: AnimationContext
) : AbstractAnimation() {

    /**
     * Use for animating animation inside AnimationContext's animates runnable
     */
    fun animate(value: Double) {
        this.debug = context.debug
        if (isAlive() && (value == from || value == to || value == this.value)) {
            if (debug) println("Animating $name cancelled due to valueTo equals fromValue")
            return
        }

        this.easing = context.easing
        this.duration = context.duration * 1000
        this.start = System.currentTimeMillis()
        this.from = this.value
        this.to = value

        if (debug)
            println(
                """$name#animate {
                    to value: $to
                    from value: $from
                    duration: $duration
                }"""
            )
    }

}