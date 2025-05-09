package dev.hogoshi.animations.core;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for animation executors that manage the execution of animations.
 * Implementations of this interface are responsible for updating animations
 * and managing their lifecycle.
 */
public interface AnimationExecutor {
    /**
     * Executes the given animation.
     *
     * @param animation the animation to execute
     */
    void execute(@NotNull AbstractAnimation animation);

    /**
     * Stops all running animations and cleans up resources.
     */
    void stop();
} 