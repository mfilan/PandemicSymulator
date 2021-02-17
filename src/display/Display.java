package display;

import javax.swing.*;
import java.awt.*;


public class Display extends JFrame {

    private final JPanel bottomPane;
    private int height;
    private int width;
    private Renderer renderer;
    private Container container;


    public Display(int width, int height){
        this.width = width;
        this.height = height;
        setTitle("Pandemic symulator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width,height+32));
        bottomPane = new JPanel();

        bottomPane.setLayout(new BorderLayout());

    }


    public void addElements(Component component,String alignment){
        bottomPane.add(component,alignment);
    }

    public void draw(){
        add(bottomPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        repaint();
    }


}

