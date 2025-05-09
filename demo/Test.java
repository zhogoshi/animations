package dev.hogoshi.animations;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dev.hogoshi.animations.easing.Easing;
import dev.hogoshi.animations.easing.Easings;
import dev.hogoshi.animations.easing.KeyFramesEasing;
import dev.hogoshi.animations.easing.bezier.Beziers;
import dev.hogoshi.animations.model.KeyFrame;

public class Test extends JFrame {
    private final List<Cube> cubes = new ArrayList<>();
    private final List<String> easingNames = new ArrayList<>();
    private boolean isAnimating = false;
    private long startTime;
    private static final int ANIMATION_DURATION = 2000;
    private static final int COLUMN_WIDTH = 400;
    private static final int ANIMATION_DISTANCE = 300;

    public Test() {
        setTitle("Easing Animations Demo");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupCubes();
        setupKeyListener();

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (isAnimating) {
                    long currentTime = System.currentTimeMillis();
                    double progress = Math.min(1.0, (double) (currentTime - startTime) / ANIMATION_DURATION);
                    
                    for (int i = 0; i < cubes.size(); i++) {
                        Cube cube = cubes.get(i);
                        Easing easing = cube.easing;
                        double easedProgress = easing.ease(progress);
                        cube.x = cube.startX + (int) (easedProgress * ANIMATION_DISTANCE);
                        cube.draw(g2d);
                        
                        g2d.setColor(Color.BLACK);
                        FontMetrics fm = g2d.getFontMetrics();
                        String text = easingNames.get(i);
                        int textWidth = fm.stringWidth(text);
                        g2d.drawString(text, cube.x - textWidth/2, cube.y - 20);
                    }

                    if (progress < 1.0) {
                        repaint();
                    } else {
                        isAnimating = false;
                    }
                } else {
                    for (int i = 0; i < cubes.size(); i++) {
                        Cube cube = cubes.get(i);
                        cube.draw(g2d);
                        
                        g2d.setColor(Color.BLACK);
                        FontMetrics fm = g2d.getFontMetrics();
                        String text = easingNames.get(i);
                        int textWidth = fm.stringWidth(text);
                        g2d.drawString(text, cube.x - textWidth/2, cube.y - 20);
                    }
                }
            }
        };
        add(panel);
    }

    private void setupCubes() {
        int y = 100;
        int spacing = 60;
        int column1X = 200;
        int column2X = column1X + COLUMN_WIDTH;

        // Standard Easings - Column 1
        addCube(column1X, y, Easings.LINEAR, "Linear");
        y += spacing;
        addCube(column1X, y, Easings.QUAD_IN, "Quad In");
        y += spacing;
        addCube(column1X, y, Easings.QUAD_OUT, "Quad Out");
        y += spacing;
        addCube(column1X, y, Easings.QUAD_BOTH, "Quad Both");
        y += spacing;
        addCube(column1X, y, Easings.CUBIC_IN, "Cubic In");
        y += spacing;
        addCube(column1X, y, Easings.CUBIC_OUT, "Cubic Out");
        y += spacing;
        addCube(column1X, y, Easings.CUBIC_BOTH, "Cubic Both");
        y += spacing;
        addCube(column1X, y, Easings.ELASTIC_IN, "Elastic In");
        y += spacing;
        addCube(column1X, y, Easings.ELASTIC_OUT, "Elastic Out");
        y += spacing;
        addCube(column1X, y, Easings.ELASTIC_BOTH, "Elastic Both");
        y += spacing;
        addCube(column1X, y, Easings.BOUNCE_IN, "Bounce In");
        y += spacing;
        addCube(column1X, y, Easings.BOUNCE_OUT, "Bounce Out");
        y += spacing;
        addCube(column1X, y, Easings.BOUNCE_BOTH, "Bounce Both");

        // Reset y for second column
        y = 100;

        // Bezier Easings - Column 2
        addCube(column2X, y, Beziers.SINE_IN, "Bezier Sine In");
        y += spacing;
        addCube(column2X, y, Beziers.SINE_OUT, "Bezier Sine Out");
        y += spacing;
        addCube(column2X, y, Beziers.SINE_BOTH, "Bezier Sine Both");
        y += spacing;
        addCube(column2X, y, Beziers.QUAD_IN, "Bezier Quad In");
        y += spacing;
        addCube(column2X, y, Beziers.QUAD_OUT, "Bezier Quad Out");
        y += spacing;
        addCube(column2X, y, Beziers.QUAD_BOTH, "Bezier Quad Both");
        y += spacing;
        addCube(column2X, y, Beziers.CUBIC_IN, "Bezier Cubic In");
        y += spacing;
        addCube(column2X, y, Beziers.CUBIC_OUT, "Bezier Cubic Out");
        y += spacing;
        addCube(column2X, y, Beziers.CUBIC_BOTH, "Bezier Cubic Both");
        y += spacing;
        addCube(column2X, y, Beziers.BACK_IN, "Bezier Back In");
        y += spacing;
        addCube(column2X, y, Beziers.BACK_OUT, "Bezier Back Out");
        y += spacing;
        addCube(column2X, y, Beziers.BACK_BOTH, "Bezier Back Both");
        y += spacing;

        // KeyFrames Easing - Column 2
        List<KeyFrame> keyframes = new ArrayList<>();
        keyframes.add(new KeyFrame(0.0, 0.0));
        keyframes.add(new KeyFrame(0.1, 0.6));
        keyframes.add(new KeyFrame(0.2, 1.1));
        keyframes.add(new KeyFrame(0.3, 0.85));
        keyframes.add(new KeyFrame(0.4, 1.05));
        keyframes.add(new KeyFrame(0.5, 0.95));
        keyframes.add(new KeyFrame(0.6, 1.02));
        keyframes.add(new KeyFrame(0.7, 0.98));
        keyframes.add(new KeyFrame(0.8, 1.01));
        keyframes.add(new KeyFrame(0.9, 0.99));
        keyframes.add(new KeyFrame(1.0, 1.0));
        addCube(column2X, y, new KeyFramesEasing(keyframes), "KeyFrames BACK_BOTH Easing");
        y += spacing;

        // KeyFrames Easing - Column 2
        keyframes = new ArrayList<>();
        keyframes.add(new KeyFrame(0.0, 0.0));
        keyframes.add(new KeyFrame(0.1, 0.6, Easings.QUAD_BOTH));
        keyframes.add(new KeyFrame(0.2, 1.1, Easings.QUAD_BOTH));
        keyframes.add(new KeyFrame(0.3, 0.85, Easings.QUAD_BOTH));
        keyframes.add(new KeyFrame(0.4, 1.05, Easings.QUAD_BOTH));
        keyframes.add(new KeyFrame(0.5, 0.95, Easings.QUAD_BOTH));
        keyframes.add(new KeyFrame(0.6, 1.02, Easings.QUAD_BOTH));
        keyframes.add(new KeyFrame(0.7, 0.98, Easings.QUAD_BOTH));
        keyframes.add(new KeyFrame(0.8, 1.01, Easings.QUAD_BOTH));
        keyframes.add(new KeyFrame(0.9, 0.99, Easings.QUAD_BOTH));
        keyframes.add(new KeyFrame(1.0, 1.0));
        addCube(column2X, y, new KeyFramesEasing(keyframes), "KeyFrames BACK_BOTH+QUAD_BOTH Easing");
    }

    private void addCube(int x, int y, Easing easing, String name) {
        cubes.add(new Cube(x, y, easing));
        easingNames.add(name);
    }

    private void setupKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F && !isAnimating) {
                    isAnimating = true;
                    startTime = System.currentTimeMillis();
                    repaint();
                }
            }
        });
    }

    private static class Cube {
        int x, y;
        final int startX;
        final Easing easing;
        static final int SIZE = 30;

        Cube(int x, int y, Easing easing) {
            this.x = x;
            this.startX = x;
            this.y = y;
            this.easing = easing;
        }

        void draw(Graphics2D g) {
            g.setColor(new Color(70, 130, 180));
            g.fillRect(x - SIZE/2, y - SIZE/2, SIZE, SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(x - SIZE/2, y - SIZE/2, SIZE, SIZE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Test test = new Test();
            test.setVisible(true);
        });
    }
}
