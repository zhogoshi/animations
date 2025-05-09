package dev.hogoshi.animations.core;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.hogoshi.animations.easing.Easings;
import dev.hogoshi.animations.model.AnimationConfig;
import dev.hogoshi.animations.model.KeyFrame;

class AnimationTest {
    private Animation animation;
    private AnimationConfig config;
    private List<KeyFrame> keyFrames;

    @BeforeEach
    void setUp() {
        config = new AnimationConfig()
            .duration(1.0)
            .easing(Easings.LINEAR);
        
        keyFrames = List.of(
            new KeyFrame(0.0, 0.0),
            new KeyFrame(1.0, 100.0)
        );
        
        animation = new Animation(config, keyFrames);
    }

    @Test
    void testInitialState() {
        assertFalse(animation.isRunning());
        assertEquals(0.0, animation.getCurrentValue());
        assertFalse(animation.isFinished());
    }

    @Test
    void testAnimationUpdate() {
        animation.update(0.5);
        assertEquals(50.0, animation.getCurrentValue(), 0.001);
    }

    @Test
    void testAnimationCompletion() {
        animation.update(1.0);
        assertTrue(animation.isFinished());
        assertEquals(100.0, animation.getCurrentValue(), 0.001);
    }

    @Test
    void testOnUpdateCallback() {
        AtomicReference<Double> lastValue = new AtomicReference<>(0.0);
        animation.onUpdate(value -> {
            assertTrue(value >= lastValue.get());
            lastValue.set(value);
        });
        
        animation.update(0.5);
        assertEquals(50.0, lastValue.get(), 0.001);
    }

    @Test
    void testOnCompleteCallback() {
        AtomicBoolean completed = new AtomicBoolean(false);
        animation.onComplete(() -> completed.set(true));
        
        animation.update(1.0);
        assertTrue(completed.get());
    }

    @Test
    void testAnimationReset() {
        animation.update(0.5);
        animation.reset();
        
        assertFalse(animation.isRunning());
        assertEquals(0.0, animation.getCurrentValue());
        assertFalse(animation.isFinished());
    }

    @Test
    void testAnimationWithDelay() {
        AnimationConfig config = new AnimationConfig()
            .duration(1.0)
            .delay(0.5)
            .easing(Easings.QUAD_BOTH);

        List<KeyFrame> keyFrames = List.of(
            new KeyFrame(0.0, 0.0),
            new KeyFrame(1.0, 100.0)
        );

        Animation animation = new Animation(config, keyFrames);
        AtomicReference<Double> lastValue = new AtomicReference<>(0.0);
        AtomicBoolean completed = new AtomicBoolean(false);

        animation.onUpdate(value -> {
            assertTrue(value >= lastValue.get());
            lastValue.set(value);
        });

        animation.onComplete(() -> completed.set(true));

        SimpleAnimationExecutor executor = new SimpleAnimationExecutor();
        executor.execute(animation);

        assertTrue(completed.get());
        assertEquals(100.0, lastValue.get(), 0.001);
    }

    @Test
    void testAnimationWithEasing() {
        config.easing(Easings.QUAD_BOTH);
        animation = new Animation(config, keyFrames);
        
        animation.update(0.25);
        double value1 = animation.getCurrentValue();
        
        animation.update(0.5);
        double value2 = animation.getCurrentValue();
        
        animation.update(0.75);
        double value3 = animation.getCurrentValue();
        
        assertTrue(value2 - value1 != value3 - value2, "Easing should produce non-linear values");
    }

    @Test
    void testAnimationExecution() {
        AnimationConfig config = new AnimationConfig()
            .duration(1.0)
            .easing(Easings.QUAD_BOTH);

        List<KeyFrame> keyFrames = List.of(
            new KeyFrame(0.0, 0.0),
            new KeyFrame(1.0, 100.0)
        );

        Animation animation = new Animation(config, keyFrames);
        AtomicReference<Double> lastValue = new AtomicReference<>(0.0);
        AtomicBoolean completed = new AtomicBoolean(false);

        animation.onUpdate(value -> {
            assertTrue(value >= lastValue.get());
            lastValue.set(value);
        });

        animation.onComplete(() -> completed.set(true));

        SimpleAnimationExecutor executor = new SimpleAnimationExecutor();
        executor.execute(animation);

        assertTrue(completed.get());
        assertEquals(100.0, lastValue.get(), 0.001);
    }
} 