package dev.hogoshi.animations.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jetbrains.annotations.NotNull;

import dev.hogoshi.animations.utility.Validator;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Simple implementation of AnimationExecutor that runs animations in a dedicated thread.
 * Supports both single-threaded and parallel processing modes.
 */
@Getter
@Accessors(chain = true, fluent = true)
public class SimpleAnimationExecutor implements AnimationExecutor {
    /**
     * List of currently running animations.
     */
    private final List<AbstractAnimation> animations = new ArrayList<>();

    /**
     * Whether animations should be processed in parallel.
     */
    private boolean parallelProcessing = false;

    /**
     * Thread pool for parallel animation processing.
     */
    private ExecutorService executorService;

    /**
     * Whether the executor is currently running.
     */
    private boolean isRunning = false;

    /**
     * Timestamp of the last animation update.
     */
    private long lastUpdateTime;

    /**
     * Enables parallel processing of animations using a thread pool.
     *
     * @param enabled whether to enable parallel processing
     * @return this executor instance for method chaining
     */
    public @NotNull SimpleAnimationExecutor parallelProcessing(boolean enabled) {
        this.parallelProcessing = enabled;
        if (enabled) {
            this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        } else if (this.executorService != null) {
            this.executorService.shutdown();
            this.executorService = null;
        }
        return this;
    }

    /**
     * Executes the given animation.
     *
     * @param animation the animation to execute
     * @throws IllegalArgumentException if animation is null
     */
    @Override
    public void execute(@NotNull AbstractAnimation animation) {
        Validator.requireNonNull(animation, "Animation cannot be null");
        animations.add(animation);
        if (!isRunning) {
            start();
        }
    }

    /**
     * Starts the animation loop in a dedicated thread.
     */
    private void start() {
        isRunning = true;
        lastUpdateTime = System.nanoTime();

        while (isRunning && !animations.isEmpty()) {
            long currentTime = System.nanoTime();
            double deltaTime = (currentTime - lastUpdateTime) / 1_000_000_000.0;
            lastUpdateTime = currentTime;

            if (deltaTime > 0.1) {
                deltaTime = 0.1;
            }

            final double finalDeltaTime = deltaTime;
            if (parallelProcessing) {
                animations.forEach(animation ->
                        executorService.submit(() -> animation.update(finalDeltaTime)));
            } else {
                animations.forEach(animation -> animation.update(finalDeltaTime));
            }

            animations.removeIf(animation -> animation.isFinished() && !animation.isRunning());

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        if (executorService != null) {
            executorService.shutdown();
        }
        isRunning = false;
    }

    /**
     * Stops all running animations and cleans up resources.
     */
    @Override
    public void stop() {
        isRunning = false;
        animations.clear();
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }
}