package dev.hogoshi.animations.core;

/**
 * Predefined animation executors for common use cases.
 */
public class Executors {

    /**
     * Executor that uses Swing's event dispatch thread for animation updates.
     */
    public static final SwingAnimationExecutor SWING = new SwingAnimationExecutor();
    /**
     * Basic single-threaded animation executor.
     */
    public static final SimpleAnimationExecutor SIMPLE = new SimpleAnimationExecutor();
    /**
     * Multi-threaded animation executor that processes animations in parallel.
     */
    public static final SimpleAnimationExecutor PARALLEL = new SimpleAnimationExecutor().parallelProcessing(true);
    /**
     * Render animation executor that you should finish (call update method inside of frame rendering).
     */
    public static final SimpleAnimationExecutor RENDER = new RenderAnimationExecutor();
    /**
     * Render multi-threaded animation executor that you should finish (call update method inside of frame rendering).
     */
    public static final SimpleAnimationExecutor RENDER_PARALLEL = new RenderAnimationExecutor().parallelProcessing(true);

    private Executors() {}

}
