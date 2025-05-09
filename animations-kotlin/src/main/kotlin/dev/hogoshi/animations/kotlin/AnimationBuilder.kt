package dev.hogoshi.animations.kotlin

import dev.hogoshi.animations.core.Animation
import dev.hogoshi.animations.core.AnimationExecutor
import dev.hogoshi.animations.easing.Easing
import dev.hogoshi.animations.model.AnimationConfig
import java.util.function.Consumer

/**
 * Builder class for creating animations with a fluent DSL.
 * Provides methods to configure animation properties, keyframes, and callbacks.
 */
class AnimationBuilder {
    private var config = AnimationConfig()
    private var fromValue: Double = 0.0
    private var valueTo: Double = 0.0
    private var onUpdate: Consumer<Double>? = null
    private var onComplete: Runnable? = null

    /**
     * Configures the animation properties using a DSL block.
     *
     * @param block configuration block that sets duration, delay, and easing
     */
    fun config(block: ConfigBuilder.() -> Unit) {
        val configBuilder = ConfigBuilder()
        configBuilder.block()
        config = configBuilder.build()
    }

    /**
     * Sets the animation configuration directly.
     *
     * @param config the animation configuration to use
     */
    fun config(config: AnimationConfig) {
        this.config = config
    }

    /**
     * Sets a callback function to be called on each animation update.
     *
     * @param block callback function that receives the current animation value
     */
    fun onUpdate(block: (Double) -> Unit) {
        onUpdate = Consumer { block(it) }
    }

    /**
     * Sets a callback function to be called when the animation completes.
     *
     * @param block callback function to execute on completion
     */
    fun onComplete(block: () -> Unit) {
        onComplete = Runnable { block() }
    }

    /**
     * Builds and returns a new animation instance with the configured properties.
     *
     * @return a new Animation instance
     */
    fun build(): Animation {
        val animation = Animation(config, fromValue, valueTo)
        onUpdate?.let { animation.onUpdate(it) }
        onComplete?.let { animation.onComplete(it) }
        return animation
    }

    /**
     * Builds and executes the animation using the specified executor.
     *
     * @param executor the animation executor to use
     */
    fun execute(executor: AnimationExecutor) {
        executor.execute(build())
    }

    fun from(value: Double): AnimationBuilder {
        fromValue = value
        return this
    }

    fun to(value: Double): AnimationBuilder {
        valueTo = value
        return this
    }
}

/**
 * Builder class for configuring animation properties.
 */
class ConfigBuilder {
    private var duration: Double = 1.0
    private var delay: Double = 0.0
    private var easing: Easing? = null

    /**
     * Sets the duration of the animation in seconds.
     *
     * @param value duration in seconds
     */
    fun duration(value: Double) {
        duration = value
    }

    /**
     * Sets the delay before the animation starts in seconds.
     *
     * @param value delay in seconds
     */
    fun delay(value: Double) {
        delay = value
    }

    /**
     * Sets the easing function for the animation.
     *
     * @param value easing function to apply
     */
    fun easing(value: Easing) {
        easing = value
    }

    /**
     * Builds and returns a new animation configuration.
     *
     * @return a new AnimationConfig instance
     */
    fun build(): AnimationConfig {
        return AnimationConfig()
            .duration(duration)
            .delay(delay)
            .easing(easing)
    }
}

/**
 * Creates a new animation configuration using a DSL block.
 *
 * @param block configuration block for the animation config
 * @return a new AnimationConfig instance
 */
fun config(block: ConfigBuilder.() -> Unit): AnimationConfig {
    val builder = ConfigBuilder()
    builder.block()
    return builder.build()
}

/**
 * Creates a new animation using a DSL block.
 *
 * @param block configuration block for the animation
 * @return a new Animation instance
 */
fun animation(block: AnimationBuilder.() -> Unit): Animation {
    val builder = AnimationBuilder()
    builder.block()
    return builder.build()
}

/**
 * Creates and executes a new animation using the specified executor.
 *
 * @param executor the animation executor to use
 * @param block configuration block for the animation
 */
fun animation(executor: AnimationExecutor, block: AnimationBuilder.() -> Unit) {
    val builder = AnimationBuilder()
    builder.block()
    builder.execute(executor)
} 