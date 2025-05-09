package dev.hogoshi.animations.core;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import org.jetbrains.annotations.NotNull;

import dev.hogoshi.animations.utility.Validator;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Implementation of AnimationExecutor that uses Swing's Timer for animation updates.
 * This executor is suitable for animations that need to update Swing components.
 */
@Getter
@Accessors(chain = true)
public class SwingAnimationExecutor implements AnimationExecutor {
    /**
     * List of currently running animations.
     */
    private final List<AbstractAnimation> animations = new ArrayList<>();

    /**
     * Swing Timer used for scheduling animation updates.
     */
    private final Timer timer;

    /**
     * Whether the executor is currently running.
     */
    private boolean isRunning = false;

    /**
     * Timestamp of the last animation update.
     */
    private long lastUpdateTime;

    /**
     * Creates a new SwingAnimationExecutor with a 1ms update interval.
     */
    public SwingAnimationExecutor() {
        timer = new Timer(1, e -> update());
        timer.setRepeats(true);
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
     * Starts the animation timer.
     */
    private void start() {
        isRunning = true;
        lastUpdateTime = System.nanoTime();
        timer.start();
    }

    /**
     * Updates all running animations with the elapsed time since last update.
     */
    private void update() {
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastUpdateTime) / 1_000_000_000.0;
        lastUpdateTime = currentTime;

        if (deltaTime > 0.1) {
            deltaTime = 0.1;
        }

        double finalDeltaTime = deltaTime;
        animations.forEach(animation -> animation.update(finalDeltaTime));
        animations.removeIf(animation -> animation.isFinished() && !animation.isRunning());

        if (animations.isEmpty()) {
            stop();
        }
    }

    /**
     * Stops all running animations and cleans up resources.
     */
    @Override
    public void stop() {
        isRunning = false;
        timer.stop();
        animations.clear();
    }
} 