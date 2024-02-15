package org.example;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {



    public Window() {
Display display = new Display();
        this.setSize(600,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBackground(Color.black);
        this.add(display);
        this.setVisible(true);
        display.run();
    }



}
