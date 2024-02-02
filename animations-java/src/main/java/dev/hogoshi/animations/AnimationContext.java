package dev.hogoshi.animations;

import dev.hogoshi.animations.animation.AbstractAnimation;
import dev.hogoshi.animations.animation.ContextAnimation;
import dev.hogoshi.animations.easing.Easing;
import dev.hogoshi.animations.easing.Easings;
import lombok.Data;

import java.util.LinkedList;

/**
 * Context for animating a lot of animations in 1 class
 *
 * @author hogoshi
 */
@Data
public class AnimationContext {

    /**
     * All current animations (you'll need to clear it urself if needed)
     */
    private final LinkedList<ContextAnimation> animations = new LinkedList<>();
    /**
     * Duration with which every ContextAnimation#animate calls
     */
    private double duration = 1.0;
    /**
     * Easing with which every ContextAnimation#animate calls
     */
    private Easing easing = Easings.LINEAR;
    /**
     * Printing all #animate calls
     */
    private boolean debug = false;

    /**
     * Get or create animation by name and add it to animations list
     *
     * @param name unique name
     * @return New or already added to animations list ContextAnimation by name with ignoring case
     */
    public ContextAnimation getAnimation(String name) {
        var optional = animations.stream().filter((it) -> it.getName().equalsIgnoreCase(name)).findFirst();
        ContextAnimation animation;
        if (optional.isEmpty()) {
            animation = new ContextAnimation(name, this);
            animations.add(animation);
        } else {
            animation = optional.get();
        }

        return animation;
    }

    /**
     * Setting up all fields and make animation with these args
     *
     * @param name     animation name
     * @param duration animation duration
     * @param value    value to which animation will animate
     * @return AnimationContext with added animation to animations list
     */
    public AnimationContext animate(String name, double value, double duration) {
        return animate(name, value, duration, Easings.LINEAR);
    }

    /**
     * Setting up all fields and make animation with these args
     *
     * @param name     animation name
     * @param duration animation duration
     * @param value    value to which animation will animate
     * @param easing   easing/bezier for animation
     * @return AnimationContext with added animation to animations list
     */
    public AnimationContext animate(String name, double value, double duration, Easing easing) {
        return animate(name, value, duration, easing, false);
    }

    /**
     * Setting up all fields and make animation with these args
     *
     * @param name     animation name
     * @param duration animation duration
     * @param value    value to which animation will animate
     * @param easing   easing/bezier for animation
     * @param debug    debugging helper which will print all #animation calls
     * @return AnimationContext with added animation to animations list
     */
    private AnimationContext animate(String name, double value, double duration, Easing easing, boolean debug) {
        setDuration(duration);
        setEasing(easing);
        setDebug(debug);
        getAnimation(name).setValue(value);
        return this;
    }

    /**
     * Call this if u want to make 1-line instead of calling #animate(name, ...)
     *
     * @param duration animation duration
     * @param runnable this consumer will invoke after all setting fields
     * @return Current AnimationContext
     */
    public AnimationContext animate(double duration, Runnable runnable) {
        return animate(duration, easing, runnable);
    }

    /**
     * Call this if u want to make 1-line instead of calling #animate(name, ...)
     *
     * @param duration animation duration
     * @param easing   easing/bezier
     * @param runnable this consumer will invoke after all setting fields
     * @return Current AnimationContext
     */
    public AnimationContext animate(double duration, Easing easing, Runnable runnable) {
        return animate(duration, easing, false, runnable);
    }

    /**
     * Call this if u want to make 1-line instead of calling #animate(name, ...)
     *
     * @param duration animation duration
     * @param easing   easing/bezier
     * @param runnable this runnable will invoke after all setting fields
     * @param debug    debugging helper which will print all #animation calls
     * @return Current AnimationContext
     */
    private AnimationContext animate(double duration, Easing easing, boolean debug, Runnable runnable) {
        setDuration(duration);
        setEasing(easing);
        setDebug(debug);
        runnable.run();
        return this;
    }

    /**
     * Use it before rendering element which uses animations
     *
     * @return boolean which means if any animation is alive
     */
    public boolean update() {
        getAnimations().forEach(ContextAnimation::update);
        return getAnimations().stream().anyMatch(AbstractAnimation::isAlive);
    }

}
