package graphicsEngine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

public abstract class GraphicsEngine {
    public static void line(Graphics g, int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        boolean isSwap = false;

        if (dy > dx) {
            int tmp = dx;
            dx = dy;
            dy = tmp;
            isSwap = true;
        }

        int D = 2 * dy - dx;

        int x = x1;
        int y = y1;
        for (int i = 1; i <= dx; i++) {
            plot(g, x, y);

            if (D >= 0) {
                if (isSwap)
                    x += sx;
                else
                    y += sy;
                D -= 2 * dx;
            }
            if (isSwap)
                y += sy;
            else
                x += sx;

            D += 2 * dy;
        }
    }

    public static void curve(Graphics g, int[] x, int[] y) {
        Graphics2D g2d = (Graphics2D) g;

        // Create a cubic Bezier curve
        Path2D path = new Path2D.Double();
        path.moveTo(x[0], y[0]); // Starting point
        path.curveTo(x[1], y[1], x[2], y[2], x[3], y[3]); // Control points and end point

        // Draw the Bezier curve
        g2d.draw(path);
    }

    public static void polygon(Graphics g, int[] x, int[] y) {
        g.fillPolygon(x, y, 3);
    }

    private static void plot(Graphics g, int x, int y) {
        g.fillRect(x, y, 1, 1);
    }
}
