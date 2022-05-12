package ru.hogoshi.type;

import ru.hogoshi.Animation;
import ru.hogoshi.util.ColorMath;
import ru.hogoshi.util.Easing;
import ru.hogoshi.util.Easings;

import java.awt.*;

/**
 * Use if u need to animation the color value
 */
public class ColorAnimation {

    private final Animation red = new Animation();
    private final Animation green = new Animation();
    private final Animation blue = new Animation();
    private final Animation alpha = new Animation();

    /**
     * Main method, use to animate color
     * @return returns method performed
     */
    public boolean animate(int colorTo, double duration) {
        return animate(colorTo, duration, false);
    }

    /**
     * Main method, use to animate color
     * @return returns method performed
     */
    public boolean animate(int colorTo, double duration, boolean safe) {
        return animate(colorTo, duration, Easings.NONE, safe);
    }

    /**
     * Main method, use to animate color
     * @return returns method performed
     */
    public boolean animate(int colorTo, double duration, Easing easing, boolean safe) {
        return getRed().animate(ColorMath.extractRed(colorTo), duration, easing, safe) &&
                getGreen().animate(ColorMath.extractGreen(colorTo), duration, easing, safe) &&
                getBlue().animate(ColorMath.extractBlue(colorTo), duration, easing, safe) &&
                getAlpha().animate(ColorMath.extractAlpha(colorTo), duration, easing, safe);
    }

    /**
     * Main method, use to animate color
     * @return returns method performed
     */
    public boolean animate(Color colorTo, double duration, Easing easing, boolean safe) {
        return animate(colorTo.getRGB(), duration, easing, safe);
    }

    /**
     * Main method, use to animate color
     * @return returns method performed
     */
    public boolean animate(Color colorTo, double duration, boolean safe) {
        return animate(colorTo.getRGB(), duration, Easings.NONE, safe);
    }

    /**
     * Main method, use to animate color
     * @return returns method performed
     */
    public boolean animate(Color colorTo, double duration) {
        return animate(colorTo.getRGB(), duration, false);
    }

    /**
     * Updates all colors
     * @return all colors alive
     */
    public boolean update() {
        return ((getRed().update() &&
                getGreen().update() &&
                getBlue().update()) &&
                getAlpha().update())
                ||
                (getRed().update() &&
                        getGreen().update() &&
                        getBlue().update())
                ;
    }

    /**
     * @return all colors alive
     */
    public boolean isAlive() {
        return ((getRed().isAlive() && getGreen().isAlive() && getBlue().isAlive()) && getAlpha().isAlive()) || (getRed().isAlive() && getGreen().isAlive() && getBlue().isAlive());
    }

    /**
     * @return all colors done
     */
    public boolean isDone() {
        return !isAlive();
    }

    /**
     * Build java.awt.Color
     * @return java.awt.Color
     */
    public Color getColor() {
        return new Color((int) getRed().getValue(), (int) getGreen().getValue(), (int) getBlue().getValue(), (int) getAlpha().getValue());
    }

    /**
     * return RGB color code
     * @return java.awt.Color#getRGB()
     */
    public int getHex() {
        return getColor().getRGB();
    }

    public Animation getRed() {
        return red;
    }

    public Animation getGreen() {
        return green;
    }

    public Animation getBlue() {
        return blue;
    }

    public Animation getAlpha() {
        return alpha;
    }
}