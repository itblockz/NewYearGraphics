import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

import csvjoin.CSVJoin;
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
        BufferedImage buffer = new BufferedImage(width+1, height+1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);
        
        g2.setColor(Color.BLACK);
        List<List<Map<String, String>>> layers = getLayers();
        for (List<Map<String,String>> list : layers) {
            for (Map<String,String> data : list) {
                draw(g2, data);
            }
            Map<String, String> sample = list.get(0);
            String strColorFill = sample.get("COLOR_FILL");
            if (!strColorFill.isEmpty()) {
                Color color = Color.decode(strColorFill);
                int i = 1;
                while (sample.containsKey("EX" + i) && !sample.get("EX" + i).isEmpty()) {
                    int x = Integer.parseInt(sample.get("EX" + i));
                    int y = Integer.parseInt(sample.get("EY" + i));
                    GraphicsEngine.fill(buffer, x, y, color);
                    i++;
                }
            }
        }
        g.drawImage(buffer, 0, 0, null);
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
        g2.setColor(Color.BLACK);
    }

    static List<List<Map<String, String>>> getLayers() {
        try {
            List<Map<String, String>> list = CSVJoin.join("./csv/data.csv", "./csv/element.csv", "ELEMENT_ID");
            int layers = (int) list.stream()
                .map(data -> data.get("LAYER"))
                .distinct()
                .count();
            List<List<Map<String, String>>> layerList = new ArrayList<>();
            IntStream.range(0, layers)
                .forEach(i -> layerList.add(new ArrayList<>()));
            list.stream()
                .forEach(data -> {
                    int idx = Integer.parseInt(data.get("LAYER"));
                    layerList.get(idx).add(data);
                });
            return layerList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}