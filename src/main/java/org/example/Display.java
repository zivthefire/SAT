package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Display extends JPanel implements Runnable{



    ArrayList<OBB> blocks;
    ArrayList<OBB> objects;

    OBB player;





    public float displayScaleX = 1;
    public float displayScaleY = 1;
    public int displaySPX = 0;
    public int displaySPY = 0;



    public Display() {

        this.setSize(600,600);
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        MouseWheelControls mwc = new MouseWheelControls(this);

        this.addMouseWheelListener(mwc);


        blocks = new ArrayList<>();
        objects = new ArrayList<>();

        player = new OBB(48,100,100);



blocks.add(new OBB(1000,64,500,this.getHeight()-64,true));


    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

if(displayScaleX < 0) {

    displayScaleX*=-1;

    displayScaleX/=10;

}
        if(displayScaleY < 0) {

            displayScaleY*=-1;

            displayScaleY/=10;

        }

//        g2d.translate(displaySPX,displaySPY);
//        g2d.scale(displayScaleX,displayScaleY);
//        g2d.translate(-displaySPX,-displaySPY);





        for(OBB block : blocks) {


            block.render(g2d);

        }

        player.render(g2d);



    }

    @Override
    public void run() {




        while(true) {



            tick();
            repaint();






            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public void tick() {




player.tick();

for(OBB block :  blocks) {


    player.isColliding(block);

}


    }
}
