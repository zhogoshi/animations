package dev.hogoshi.animations.core;

/**
 * On-render implementation of SimpleAnimationExecutor that runs animations in a dedicated thread if parallel processing enabled.
 * Supports both single-threaded and parallel processing modes.
 * Use it if you want to update animations in frame render, instead of starting pretty slow lifecycle and waiting.
 */
public class RenderAnimationExecutor extends SimpleAnimationExecutor {
    
    @Override
    protected void start() {
        isRunning = true;
    }

    /**
     * Important method. You should call this in frame render if you want to use this executor.
     */
    public void update() {
        if(!isRunning) return;
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
    }
    
}