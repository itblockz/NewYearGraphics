import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class NewYear extends JPanel {
    private static final int width = 600;
    private static final int height = 600;
    
    public static void main(String[] args) {
        NewYear m = new NewYear();
        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("New Year");
        f.setSize(width, height);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.RED);
        int[] x, y;
        line(g, 0, 0, 600, 600);
        x = new int[] { 50, 100, 200, 350 };
        y = new int[] { 200, 50, 300, 200 };
        curve(g, x, y);
        x = new int[] { 300, 400, 500 };
        y = new int[] { 500, 300, 500 };
        polygon(g, x, y);
    }

    private void line(Graphics g, int x1, int y1, int x2, int y2) {
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

    private void curve(Graphics g, int[] x, int[] y) {
        Graphics2D g2d = (Graphics2D) g;

        // Create a cubic Bezier curve
        Path2D path = new Path2D.Double();
        path.moveTo(x[0], y[0]); // Starting point
        path.curveTo(x[1], y[1], x[2], y[2], x[3], y[3]); // Control points and end point

        // Draw the Bezier curve
        g2d.draw(path);
    }

    private void polygon(Graphics g, int[] x, int[] y) {
        g.fillPolygon(x, y, 3);
    }

    public void plot(Graphics g, int x, int y) {
        g.fillRect(x, y, 1, 1);
    }
}