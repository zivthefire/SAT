package org.example;

import java.awt.*;
import java.awt.geom.Point2D;

public class GeoUtil {



    public static double PointDistanceSegment(Point2D.Double p,Point2D.Double a,Point2D.Double b) {


        Vector2D ab = new Edge(b, a).toVector();

        Vector2D ap = new Edge(p, a).toVector();


        double proj = Vector2D.dot(ap, ab);

        double abLenSq = ab.getLengthSq();

        double d = proj / abLenSq;


        return Math.pow(d,2);
    }





  public static Vector2D pointCPSegment(Vector2D p,Vector2D a,Vector2D b)  {



       Vector2D CP;

        Vector2D ab = b.getSubtracted(a);

        Vector2D ap = p.getSubtracted(a);



        double proj = Vector2D.dot(ap,ab);

        double abLenSq = ab.getLengthSq();

        double d = proj/abLenSq;


        if(d <= 0) {

            CP = a;

        } else if(d >= 1) {

            CP = b;

        } else {
            CP = a.getAdded(ab.getMultiplied(d));
        }


      return CP;
  }




}
