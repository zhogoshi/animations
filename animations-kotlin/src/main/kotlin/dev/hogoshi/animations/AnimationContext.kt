package dev.hogoshi.animations

import dev.hogoshi.animations.animation.ContextAnimation
import dev.hogoshi.animations.easing.Easing
import dev.hogoshi.animations.easing.Easings
import java.util.*

/**
 * Context for animating a lot of animations in 1 class
 * @author hogoshi
 */
class AnimationContext {

    /**
     * Duration with which every ContextAnimation#animate calls
     */
    var duration = 1.0

    /**
     * Easing with which every ContextAnimation#animate calls
     */
    var easing = Easings.LINEAR

    /**
     * Printing all #animate calls
     */
    var debug = false

    /**
     * All current animations (you'll need to clear it urself if needed)
     */
    val animations = LinkedList<ContextAnimation>()

    /**
     * Get or create animation by name and add it to animations list
     * @param name unique name
     * @return New or already added to animations list ContextAnimation by name with ignoring case
     */
    fun getAnimation(name: String): ContextAnimation {
        var found = animations.firstOrNull { it.name == name }
        if (found == null) {
            found = ContextAnimation(name, this)
            animations.add(found)
        }

        return found
    }

    /**
     * Setting up all fields and make animation with these args
     * @param name animation name
     * @param duration animation duration
     * @param value value to which animation will animate
     * @param easing easing/bezier for animation
     * @param debug debugging helper which will print all #animation calls
     * @return AnimationContext with added animation to animations list
     */
    fun animate(
        name: String,
        value: Double,
        duration: Double,
        easing: Easing = Easings.LINEAR,
        debug: Boolean = false
    ): AnimationContext {
        this.duration = duration
        this.easing = easing
        this.debug = debug
        getAnimation(name).animate(value)
        return this
    }

    /**
     * Call this if u want to make 1-line instead of calling #animate(name, ...)
     * @param duration animation duration
     * @param consumer this consumer will invoke after all setting fields
     * @return Current AnimationContext
     */
    fun animate(duration: Double, consumer: AnimationContext.() -> Unit, debug: Boolean = false): AnimationContext {
        this.duration = duration
        this.debug = debug
        this.easing = Easings.LINEAR
        consumer.invoke(this)
        return this
    }

    /**
     * Call this if u want to make 1-line instead of calling #animate(name, ...)
     * @param duration animation duration
     * @param easing easing/bezier
     * @param consumer this consumer will invoke after all setting fields
     * @return Current AnimationContext
     */
    fun animate(duration: Double, easing: Easing, consumer: AnimationContext.() -> Unit): AnimationContext {
        this.duration = duration
        this.easing = easing
        consumer.invoke(this)
        return this
    }

    /**
     * Call this if u want to make 1-line instead of calling #animate(name, ...)
     * @param duration animation duration
     * @param easing easing/bezier
     * @param consumer this consumer will invoke after all setting fields
     * @param debug debugging helper which will print all #animation calls
     * @return Current AnimationContext
     */
    fun animate(
        duration: Double,
        easing: Easing,
        consumer: AnimationContext.() -> Unit,
        debug: Boolean
    ): AnimationContext {
        this.duration = duration
        this.easing = easing
        this.debug = debug
        consumer.invoke(this)
        return this
    }

    /**
     * Use it before rendering element which uses animations
     * @return boolean which means if any animation is alive
     */
    fun update(): Boolean {
        animations.forEach(ContextAnimation::update)
        return animations.any(ContextAnimation::isAlive)
    }

}