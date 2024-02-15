package org.example;

import java.awt.*;
import java.awt.geom.Point2D;

public class Edge {

    public Point2D.Double startPoint;
    public Point2D.Double endPoint;

    public Edge(Point2D.Double startPoint, Point2D.Double endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Point2D.Double getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point2D.Double startPoint) {
        this.startPoint = startPoint;
    }

    public Point2D.Double getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point2D.Double endPoint) {
        this.endPoint = endPoint;
    }

    public void render(Graphics2D g2d) {

        g2d.setColor(Color.blue);
        g2d.drawLine((int) startPoint.x, (int) startPoint.y, (int) endPoint.x, (int) endPoint.y);

    }

    public Vector2D toVector() {
        double vectorX = endPoint.x - startPoint.x;
        double vectorY = endPoint.y - startPoint.y;
        return new Vector2D(vectorX, vectorY);
    }

    public  Point2D.Double getMid() {
        double midX = (startPoint.x + endPoint.x) / 2.0;
        double midY = (startPoint.y + endPoint.y) / 2.0;
        return new Point2D.Double(midX, midY);
    }

}
