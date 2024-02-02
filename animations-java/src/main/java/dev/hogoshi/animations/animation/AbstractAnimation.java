package dev.hogoshi.animations.animation;

import dev.hogoshi.animations.easing.Easing;
import lombok.Data;

/**
 * Animation basic abstraction
 *
 * @author hogoshi
 */
@Data
public abstract class AbstractAnimation {

    private double value;
    private long start;
    private double duration;
    private double from;
    private double to;
    private Easing easing;
    private boolean debug;

    /**
     * Use it before rendering element which uses animation's value
     *
     * @return boolean which means if animation is alive
     */
    public boolean update() {
        boolean alive = isAlive();

        if (alive) {
            this.value = interpolate(getFrom(), getTo(), getEasing().ease(calculatePart()));
        } else {
            setStart(0);
            this.value = getTo();
        }

        return alive;
    }

    /**
     * @return boolean which means if animation is alive
     */
    public boolean isAlive() {
        return !isDone();
    }

    /**
     * @return boolean which means if animation is done
     */
    public boolean isDone() {
        return calculatePart() >= 1.0;
    }

    /**
     * @return calculates current x from 0 to 1 for calculating easing's current value
     */
    public double calculatePart() {
        return (double) (System.currentTimeMillis() - getStart()) / getDuration();
    }

    /**
     * @return linear interpolation
     */
    public double interpolate(double start, double end, double pct) {
        return start + (end - start) * pct;
    }

}