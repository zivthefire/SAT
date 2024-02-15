package org.example;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseWheelControls implements MouseWheelListener {

    Display connectedDisplay;

    int oldRot = 0;

    public MouseWheelControls(Display connectedDisplay) {
        this.connectedDisplay = connectedDisplay;
    }


    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

//        int dir =0;
//        if(e.getWheelRotation() > oldRot) {
//            dir = 1;
//        } else if(e.getWheelRotation() < oldRot) {
//            dir = -1;
//        }
//
//        connectedDisplay.displayScaleY += (float)(e.getWheelRotation())/10.0f;
//        connectedDisplay.displayScaleX += (float)(e.getWheelRotation())/10.0f;
//        oldRot = e.getWheelRotation();
//
//
//        connectedDisplay.displaySPX = e.getX();
//        connectedDisplay.displaySPY = e.getY();

    }
}
