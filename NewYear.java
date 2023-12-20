import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import component.*;

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
        // Head h = new Head();
        // h.draw(g);
        // Eye e = new Eye();
        // e.draw(g);
        Body d = new Body();
        d.draw(g);
    }
}