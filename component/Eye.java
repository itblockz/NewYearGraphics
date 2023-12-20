package component;

import java.awt.Graphics;

import drawer.Drawer;

public class Eye extends Drawer {
    @Override
    public void draw(Graphics g) {
        int[] x = new int[] { 100, 200, 300, 400 };
        int[] y = new int[] { 200, 100, 400, 300 };
        curve(g, x, y);
    }
}
