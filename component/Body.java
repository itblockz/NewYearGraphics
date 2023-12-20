package component;
import java.awt.Graphics;

import drawer.Drawer;

public class Body extends Drawer {
    @Override
    public void draw(Graphics g) {
        line(g, 0 , 0, 100,520);
        
    }
}
