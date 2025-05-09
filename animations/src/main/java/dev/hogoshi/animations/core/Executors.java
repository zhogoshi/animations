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

    private Executors() {}

}
