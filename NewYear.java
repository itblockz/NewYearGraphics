import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import graphicsEngine.GraphicsEngine;

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
        super.paint(g);
        try (Scanner input = new Scanner(Paths.get("data.csv"))) {
            input.nextLine();
            while (input.hasNextLine()) {
                String row = input.nextLine();
                String[] dataFields = row.split(",");
                String type = dataFields[0];
                if (type.equals("line")) {
                    int x1 = Integer.parseInt(dataFields[1]);
                    int y1 = Integer.parseInt(dataFields[2]);
                    int x2 = Integer.parseInt(dataFields[3]);
                    int y2 = Integer.parseInt(dataFields[4]);
                    GraphicsEngine.line(g, x1, y1, x2, y2);
                } else if (type.equals("curve")) {
                    int n = 4;
                    int[] x = new int[n];
                    int[] y = new int[n];
                    for (int i = 0; i < n; i++) {
                        x[i] = Integer.parseInt(dataFields[i*2 + 1]);
                        y[i] = Integer.parseInt(dataFields[i*2 + 2]);
                    }
                    GraphicsEngine.curve(g, x, y);
                } else if (type.equals("polygon")) {
                    int n = 3;
                    int[] x = new int[n];
                    int[] y = new int[n];
                    for (int i = 0; i < n; i++) {
                        x[i] = Integer.parseInt(dataFields[i*2 + 1]);
                        y[i] = Integer.parseInt(dataFields[i*2 + 2]);
                    }
                    String color = dataFields[7];
                    g.setColor(Color.decode(color));
                    GraphicsEngine.polygon(g, x, y);
                }
            }
        } catch (IOException e) { }
    }
}