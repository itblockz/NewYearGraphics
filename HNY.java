import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.Database;
import engine.GraphicsEngine;

public class HNY extends JPanel {
    private static final int width = 600;
    private static final int height = 600;
    
    public static void main(String[] args) {
        HNY m = new HNY();
        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Happy New Year 2024");
        f.setSize(width, height);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int start = 0;
        int end = 20;
        Set<String> layersToDraw = new HashSet<>();
        for (int i = start; i <= end; i++) {
            layersToDraw.add(String.valueOf(i));
        }
        start = 55;
        end = 75;
        for (int i = start; i <= end; i++) {
            layersToDraw.add(String.valueOf(i));
        }
        
        Database db = new Database();
        db.createTable("./data/lines.csv", "Lines");
        db.createTable("./data/elements.csv", "Elements");
        List<Map<String, String>> lines = db.getTable("Lines");
        List<Map<String, String>> elements = db.getTable("Elements");
        List<Map<String, String>> joined = db.getJoinedTable(lines, elements, "ELEMENT_ID");
        Map<String, List<Map<String, String>>> layerMap = db.getGroupedTable(joined, "LAYER");
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparing(Integer::parseInt));
        for (String str : layerMap.keySet()) {
            queue.add(str);
        }

        while (!queue.isEmpty()) {
            String layer = queue.poll();
            List<Map<String, String>> list = layerMap.get(layer);
            if (!layersToDraw.contains(layer)) continue;
            BufferedImage buffer = new BufferedImage(width+1, height+1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = buffer.createGraphics();
            for (Map<String,String> data : list) {
                draw(g2, data);
            }
            Map<String, String> sample = list.get(0);
            String colorFill = sample.get("COLOR_FILL");
            if (!colorFill.isEmpty()) {
                Color color = Color.decode(colorFill);
                int seedX = Integer.parseInt(sample.get("SEED_X"));
                int seedY = Integer.parseInt(sample.get("SEED_Y"));
                GraphicsEngine.fill(buffer, seedX, seedY, color);
            }
            g.drawImage(buffer, 0, 0, null);
        }
    }

    static void draw(Graphics2D g2, Map<String, String> data) {
        String type = data.get("TYPE");
        int n;
        int[] x, y;
        String color;
        switch (type) {
            case "line":
                int x1 = Integer.parseInt(data.get("X1"));
                int y1 = Integer.parseInt(data.get("Y1"));
                int x2 = Integer.parseInt(data.get("X2"));
                int y2 = Integer.parseInt(data.get("Y2"));
                color = data.get("COLOR_BOUND");
                if (!data.get("COLOR").isEmpty())
                    color = data.get("COLOR");
                g2.setColor(Color.decode(color));
                GraphicsEngine.line(g2, x1, y1, x2, y2);
                break;
            case "curve":
                n = 4;
                x = new int[n];
                y = new int[n];
                for (int i = 0; i < n; i++) {
                    x[i] = Integer.parseInt(data.get("X"+(i+1)));
                    y[i] = Integer.parseInt(data.get("Y"+(i+1)));
                }
                color = data.get("COLOR_BOUND");
                if (!data.get("COLOR").isEmpty())
                    color = data.get("COLOR");
                g2.setColor(Color.decode(color));
                GraphicsEngine.curve(g2, x, y);
                break;
            case "polygon":
                n = 3;
                x = new int[n];
                y = new int[n];
                for (int i = 0; i < n; i++) {
                    x[i] = Integer.parseInt(data.get("X"+(i+1)));
                    y[i] = Integer.parseInt(data.get("Y"+(i+1)));
                }
                color = data.get("COLOR");
                g2.setColor(Color.decode(color));
                GraphicsEngine.polygon(g2, x, y);
                break;
        }
    }
}