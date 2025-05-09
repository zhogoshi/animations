package dev.hogoshi.animations.easing;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import dev.hogoshi.animations.model.KeyFrame;
import dev.hogoshi.animations.utility.Validator;

public class KeyFramesEasing implements Easing {
    private final List<KeyFrame> keyframes;

    public KeyFramesEasing(@NotNull List<KeyFrame> keyframes) {
        Validator.requireNonNull(keyframes, "Keyframes cannot be null");
        Validator.requireNonEmpty(keyframes, "Keyframes cannot be empty");
        Validator.requireValidKeyFrames(keyframes);
        this.keyframes = new ArrayList<>(keyframes);
    }

    @Override
    public double ease(double time) {
        if (time <= 0) return keyframes.get(0).getValue();
        if (time >= 1) return keyframes.get(keyframes.size() - 1).getValue();

        for (int i = 0; i < keyframes.size() - 1; i++) {
            KeyFrame current = keyframes.get(i);
            KeyFrame next = keyframes.get(i + 1);

            if (time >= current.getTime() && time <= next.getTime()) {
                double segmentTime = (time - current.getTime()) / (next.getTime() - current.getTime());
                if (current.getEasing() != null) {
                    segmentTime = current.getEasing().ease(segmentTime);
                }
                return current.getValue() + (next.getValue() - current.getValue()) * segmentTime;
            }
        }

        return keyframes.get(keyframes.size() - 1).getValue();
    }

    public List<KeyFrame> getKeyframes() {
        return new ArrayList<>(keyframes);
    }
} 