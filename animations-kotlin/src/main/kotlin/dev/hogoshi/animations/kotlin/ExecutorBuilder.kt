package dev.hogoshi.animations.kotlin

import dev.hogoshi.animations.core.AbstractAnimation
import dev.hogoshi.animations.core.AnimationExecutor
import dev.hogoshi.animations.core.SimpleAnimationExecutor
import dev.hogoshi.animations.core.SwingAnimationExecutor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Predefined animation executors for common use cases.
 */
object Executors {
    /**
     * Executor that uses Swing's event dispatch thread for animation updates.
     */
    val SWING = SwingAnimationExecutor()

    /**
     * Basic single-threaded animation executor.
     */
    val SIMPLE = SimpleAnimationExecutor()

    /**
     * Multi-threaded animation executor that processes animations in parallel.
     */
    val PARALLEL = SimpleAnimationExecutor().enableParallelProcessing()
}

/**
 * Builder class for creating custom animation executors.
 * Allows configuration of update callbacks, parallel processing, and FPS settings.
 */
class ExecutorBuilder {
    private var onUpdate: ((List<AbstractAnimation>, Double) -> Unit)? = null
    private var onStart: (() -> Unit)? = null
    private var onStop: (() -> Unit)? = null
    private var parallel = false
    private var fps = 60.0

    /**
     * Sets the callback to be executed on each animation update.
     *
     * @param block callback function that receives the list of active animations and delta time
     */
    fun onUpdate(block: (List<AbstractAnimation>, Double) -> Unit) {
        onUpdate = block
    }

    /**
     * Sets the callback to be executed when animation execution starts.
     *
     * @param block callback function to execute on start
     */
    fun onStart(block: () -> Unit) {
        onStart = block
    }

    /**
     * Sets the callback to be executed when animation execution stops.
     *
     * @param block callback function to execute on stop
     */
    fun onStop(block: () -> Unit) {
        onStop = block
    }

    /**
     * Enables or disables parallel processing of animations.
     *
     * @param value true to enable parallel processing, false otherwise
     */
    fun parallel(value: Boolean = true) {
        parallel = value
    }

    /**
     * Sets the target frames per second for the animation executor.
     *
     * @param value the target FPS (must be positive)
     * @throws IllegalArgumentException if the FPS value is not positive
     */
    fun fps(value: Double) {
        require(value > 0) { "FPS must be positive" }
        fps = value
    }

    /**
     * Builds and returns a configured animation executor.
     *
     * @return a new AnimationExecutor instance with the configured settings
     */
    fun build(): AnimationExecutor {
        val updateRate = (1000.0 / fps).toLong()

        return object : AnimationExecutor {
            private val animations = mutableListOf<AbstractAnimation>()
            private var executor: ScheduledExecutorService? = null
            private var isRunning = false
            private var lastUpdateTime = System.nanoTime()

            override fun execute(animation: AbstractAnimation) {
                animations.add(animation)
                if (!isRunning) {
                    start()
                }
            }

            private fun start() {
                isRunning = true
                lastUpdateTime = System.nanoTime()
                onStart?.invoke()

                executor = if (parallel) {
                    Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors())
                } else {
                    Executors.newSingleThreadScheduledExecutor()
                }

                executor?.scheduleAtFixedRate({
                    val currentTime = System.nanoTime()
                    val deltaTime = ((currentTime - lastUpdateTime) / 1_000_000_000.0).coerceAtMost(0.1)
                    lastUpdateTime = currentTime

                    onUpdate?.invoke(animations, deltaTime)
                    animations.forEach { it.update(deltaTime) }
                    animations.removeAll { it.isFinished() }

                    if (animations.isEmpty()) {
                        stop()
                    }
                }, 0, updateRate, TimeUnit.MILLISECONDS)
            }

            override fun stop() {
                isRunning = false
                onStop?.invoke()
                executor?.shutdownNow()
                executor = null
                animations.clear()
            }
        }
    }
}

/**
 * Creates a new animation executor with the specified configuration.
 *
 * @param block configuration block for the executor
 * @return a new configured AnimationExecutor instance
 */
fun executor(block: ExecutorBuilder.() -> Unit): AnimationExecutor {
    val builder = ExecutorBuilder()
    builder.block()
    return builder.build()
} 