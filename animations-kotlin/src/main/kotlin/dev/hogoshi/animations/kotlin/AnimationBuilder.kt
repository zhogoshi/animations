package dev.hogoshi.animations.kotlin

import dev.hogoshi.animations.core.Animation
import dev.hogoshi.animations.core.AnimationExecutor
import dev.hogoshi.animations.easing.Easing
import dev.hogoshi.animations.model.AnimationConfig
import dev.hogoshi.animations.model.KeyFrame
import java.util.function.Consumer

/**
 * Builder class for creating animations with a fluent DSL.
 * Provides methods to configure animation properties, keyframes, and callbacks.
 */
class AnimationBuilder {
    private var config = AnimationConfig()
    private val keyFrames = mutableListOf<KeyFrame>()
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
     * Defines keyframes for the animation using a DSL block.
     *
     * @param block keyframes block that defines time-value pairs
     */
    fun keyframes(block: KeyFramesBuilder.() -> Unit) {
        val keyFramesBuilder = KeyFramesBuilder()
        keyFramesBuilder.block()
        keyFrames.addAll(keyFramesBuilder.build())
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
        val animation = Animation(config, keyFrames)
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
 * Builder class for creating animation keyframes.
 */
class KeyFramesBuilder {
    private val frames = mutableListOf<KeyFrame>()

    /**
     * Adds a keyframe at the specified time with the given value.
     *
     * @param time time point in the animation (0.0 to 1.0)
     * @param value value at this keyframe
     */
    fun keyframe(time: Double, value: Double) {
        frames.add(KeyFrame().apply {
            this.time = time
            this.value = value
        })
    }

    /**
     * Builds and returns the list of keyframes.
     *
     * @return list of configured keyframes
     */
    fun build(): List<KeyFrame> = frames
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