package org.example;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class OBB {
    private double width;
    private double height;
    private double x;
    private double y;
    private double angle;




    private Vector2D velocity = new Vector2D(0,0);
    private Vector2D accleration = new Vector2D(0,-0.3);
    private Vector2D localvelocity = new Vector2D(0,0);
    private Vector2D totalvelocity;

    private Vector2D xAxisRelative;
    private Vector2D yAxisRelative;






    private Vector2D[] normals = new Vector2D[4];


    private Point2D.Double minPoint;
    private Point2D.Double maxPoint;


    private Point2D.Double[] vertices;


    private boolean hasTexture = false;

    private Image texture;
    private boolean Static = false;

    private double vectorAngle = 0;

    public OBB(double width,double height, double x, double y,boolean Static) {




        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.angle = 0;

        this.Static = Static;

        // Draw the corner points
        int cornerSize = 9; // Adjust the size of the corner points as needed



        //This is used to get the vertices of the cube and the min and max
        vertices = calcCorners();




        Edge[] edges = calcEdges(vertices);


        for (int i = 0; i < edges.length; i++) {

            Edge edge = edges[i];



            Vector2D v = edge.toVector();


            if (i == 0) {


                yAxisRelative = v;

            } else if (i == 1) {
                v.reverse();

                xAxisRelative = v;


            }
            Vector2D par = v.getPerp();

            par.normalize();
            Point2D.Double mid = edge.getMid();


            normals[i] = par;



        }
    }
    public OBB(double size, double x, double y,boolean Static) {
        this.width = size;
        this.height = size;
        this.x = x;
        this.y = y;
        this.angle = 0;

        this.Static = Static;

        // Draw the corner points
        int cornerSize = 9; // Adjust the size of the corner points as needed



        //This is used to get the vertices of the cube and the min and max
        vertices = calcCorners();




        Edge[] edges = calcEdges(vertices);


        for (int i = 0; i < edges.length; i++) {

            Edge edge = edges[i];



            Vector2D v = edge.toVector();


            if (i == 0) {


                yAxisRelative = v;

            } else if (i == 1) {
                v.reverse();

                xAxisRelative = v;


            }
            Vector2D par = v.getPerp();

            par.normalize();
            Point2D.Double mid = edge.getMid();


            normals[i] = par;



        }
    }
    public OBB(double size, double x, double y) {
        this.width = size;
        this.height = size;
        this.x = x;
        this.y = y;
        this.angle = 0;

        // Draw the corner points
        int cornerSize = 9; // Adjust the size of the corner points as needed



        //This is used to get the vertices of the cube and the min and max
        vertices = calcCorners();




        Edge[] edges = calcEdges(vertices);


        for (int i = 0; i < edges.length; i++) {

            Edge edge = edges[i];



            Vector2D v = edge.toVector();


            if (i == 0) {


                yAxisRelative = v;

            } else if (i == 1) {
                v.reverse();

                xAxisRelative = v;


            }
            Vector2D par = v.getPerp();

            par.normalize();
            Point2D.Double mid = edge.getMid();


            normals[i] = par;



        }
    }
    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void rotate(double angle) {
        // Convert angle to radians
        angle = Math.toRadians(angle);

        // Rotation matrix
        double[][] rotationMatrix = {
                {Math.cos(angle), -Math.sin(angle)},
                {Math.sin(angle), Math.cos(angle)}
        };

        // Apply the rotation to the coordinates
        double[] coordinates = matrixMultiply(rotationMatrix, new double[]{x, y});

        // Update the object's coordinates
        x = coordinates[0];
        y = coordinates[1];
    }

    private static double[] matrixMultiply(double[][] matrix, double[] vector) {
        int mRows = matrix.length;
        int mCols = matrix[0].length;

        double[] result = new double[mRows];

        for (int i = 0; i < mRows; i++) {
            for (int j = 0; j < mCols; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }

        return result;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public void render(Graphics2D g2d) {


        // Store the original transform to restore it later
        AffineTransform originalTransform = g2d.getTransform();






        // Draw the corner points
        int cornerSize = 9;

        g2d.setColor(Color.GREEN);

        g2d.setColor(Color.RED);





        g2d.setColor(Color.RED);


        Edge[] edges = calcEdges(vertices);


        for (int i = 0; i < edges.length; i++) {

            Edge edge = edges[i];


            edge.render(g2d);

            Vector2D v = edge.toVector();


        }





        g2d.setColor(Color.BLUE);

        Point2D.Double last = vertices[vertices.length - 1];
        Point2D.Double current = vertices[0];

        g2d.drawLine((int) current.x, (int) current.y, (int) last.x, (int) last.y);

        g2d.setColor(Color.RED);
        if(hasTexture) {

            AffineTransform matrix = g2d.getTransform();
            g2d.translate(x,y);
            g2d.rotate(angle);
            g2d.drawImage(texture, (int) (0-width/2), (int) (0-height/2), (int) width, (int) height,null);
            g2d.setTransform(matrix);
        }


    }


    private Vector2D[] getPerpNormalVectors(Edge[] edges) {


        Vector2D[] vectors = new Vector2D[edges.length];

        for (int i = 0; i < edges.length; i++) {

            Edge edge = edges[i];

            Vector2D v = edge.toVector();
            v.reverse();

            Vector2D par = v.getPerp();
            par.reverse();
            par.normalize();

            vectors[i] = par;

        }

        return vectors;

    }


    public boolean isColliding(OBB box2) {



        Vector2D AxesToTest[] = {new Vector2D(0, 1), new Vector2D(1, 0), yAxisRelative.getNormalized(), xAxisRelative.getNormalized()};
        Vector2D AxesToTest2[] = {new Vector2D(0, 1), new Vector2D(1, 0), box2.yAxisRelative.getNormalized(), box2.xAxisRelative.getNormalized()};

        ArrayList<Vector2D> awc = new ArrayList<>();
        for (int i = 0; i < AxesToTest.length; i++) {

            if (!isOverlapOnAxis(box2, AxesToTest[i])) {
                return false;
            } else {
                awc.add(AxesToTest[i]);
            }


        }
        for (int i = 0; i < AxesToTest2.length; i++) {

            if (!isOverlapOnAxis(box2, AxesToTest2[i])) {
                return false;
            } else {
                awc.add(AxesToTest2[i]);
            }


        }



        Vector2D shortest = awc.get(0);

        for (Vector2D axis : awc) {



          double overlap =  OverlapOnAxis(box2,axis);


          velocity.x = 0;
          velocity.y = 0;


          axis.multiply(overlap);



          if(axis.getLength() < shortest.getLength()) {

              shortest = axis;

          }


        }
        Point2D.Double[] collisionP = calculateCollisionPoints(box2,shortest);

        double dotProduct = shortest.dot(box2.vertices[0].x - this.x, box2.vertices[0].y - this.y);
        if (dotProduct > 0) {
            shortest.reverse(); // Reverse the direction of MTV
        }
        int additionX = (shortest.x == 0) ? 0 : (shortest.x > 0) ? 1 : -1;
        int additionY = (shortest.y == 0) ? 0 : (shortest.y > 0) ? 1 : -1;
        this.x += shortest.x;
        this.y += shortest.y;
        this.localvelocity = box2.velocity;
        this.localvelocity =box2.velocity;









        return true;


    }



    public Point2D.Double[] calculateCollisionPoints(OBB other, Vector2D mtv) {
        double displacementX = mtv.x / 2;
        double displacementY = mtv.y / 2;
        Point2D.Double collisionPointThis = new Point2D.Double(this.getX() + displacementX, this.getY() + displacementY);
        double displacementXOther = displacementX;
        double displacementYOther = displacementY;
        Point2D.Double collisionPointOther = new Point2D.Double(other.getX() + displacementXOther, other.getY() + displacementYOther);
        return new Point2D.Double[] {collisionPointThis, collisionPointOther};
    }





    private double calculateOverlap(OBB box2, Vector2D axis) {
        double projection1 = projectOntoAxis(axis);
        double projection2 = box2.projectOntoAxis(axis);

        double min1 = projection1 - width / 2;
        double max1 = projection1 + height / 2;
        double min2 = projection2 - box2.width / 2;
        double max2 = projection2 + box2.height / 2;

        // Calculate overlap
        double overlap = Math.min(max1, max2) - Math.max(min1, min2);
        if (overlap <= 0) {
            return 0; // No overlap
        } else {
            return overlap;
        }
    }

    private double projectOntoAxis(Vector2D axis) {
        double xAxisProjection = xAxisRelative.dot(axis);
        double yAxisProjection = yAxisRelative.dot(axis);

        double projection = Math.abs(xAxisProjection) * width / 2 + Math.abs(yAxisProjection) * height / 2;
        return projection;
    }
    public ArrayList<Vector2D> findContactPoints(OBB box) {

        ArrayList<Vector2D> contactpoints = new ArrayList<>();


        contactpoints.add(new Vector2D(0,0));
        contactpoints.add(new Vector2D(0,0));



        double minD = Double.MAX_VALUE;

        for(Point2D.Double vertex :vertices) {


        Vector2D vp = new Vector2D(vertex.x,vertex.y);

        Point2D.Double p = vertex;








            for(int i=0; i<box.vertices.length;i++) {


                Point2D.Double a = box.vertices[i];
                Point2D.Double b = box.vertices[(i + 1) % box.vertices.length];




                Vector2D va = Vector2D.fromPoint(a);
                Vector2D vb = Vector2D.fromPoint(b);

                    Vector2D CP = GeoUtil.pointCPSegment(vp,va,vb);


                    double d = GeoUtil.PointDistanceSegment(p,a,b);


                        if(d==minD) {


                            if(CP != contactpoints.get(0))

                            contactpoints.set(1,CP);


                        }
                        else if(d<minD) {

                        minD = d;


                     contactpoints.set(0,CP);


                    }









            }


        }
        for(Point2D.Double vertex :box.vertices) {


            Vector2D vp = new Vector2D(vertex.x,vertex.y);

            Point2D.Double p = vertex;








            for(int i=0; i<vertices.length;i++) {


                Point2D.Double a = vertices[i];
                Point2D.Double b = vertices[(i + 1) % vertices.length];




                Vector2D va = Vector2D.fromPoint(a);
                Vector2D vb = Vector2D.fromPoint(b);

                Vector2D CP = GeoUtil.pointCPSegment(vp,va,vb);


                double d = GeoUtil.PointDistanceSegment(p,a,b);


                if(d==minD) {


                    if(CP != contactpoints.get(0))

                        contactpoints.set(1,CP);


                }
                else if(d<minD) {

                    minD = d;


                    contactpoints.set(0,CP);


                }









            }


        }

        return contactpoints;
    }





    public boolean  isOverlapOnAxis(OBB box2,Vector2D axis) {

        Vector2D interval1 = getInterval(axis);

        Vector2D interval2 = box2.getInterval(axis);



        double overlap = Math.abs(interval2.x-interval1.y);

        return( (interval2.x <=interval1.y) && (interval1.x <= interval2.y));


    }

    public double OverlapOnAxis(OBB box2, Vector2D axis) {
        Vector2D interval1 = getInterval(axis);
        Vector2D interval2 = box2.getInterval(axis);

        // Check for overlap
        if (interval1.y < interval2.x || interval2.y < interval1.x) {
            // No overlap
            return 0.0;
        } else {
            // Calculate overlap
            double overlap1 = interval2.y - interval1.x;
            double overlap2 = interval1.y - interval2.x;
            return Math.min(overlap1, overlap2);
        }
    }







    public Vector2D getInterval(Vector2D axis) {


        Vector2D result = new Vector2D(0,0);

        Vector2D[] avertices = new Vector2D[4];


        for(int i=0;i<vertices.length;i++) {

            Point2D.Double point = vertices[i];


            avertices[i] = new Vector2D(point.x,point.y);



        }


        result.x = axis.dot(avertices[0]);
        result.y = result.x;
        for(int i=0;i<4;i++) {


            double projection = axis.dot(avertices[i]);


            if(projection < result.x) {

                result.x = projection;

            }
            if(projection > result.y) {

                result.y = projection;

            }
        }







        return result;
    }

private Edge[] calcEdges(Point2D.Double[] points) {
    Edge[] edges = new Edge[4];
    int edgenum = 0;
        for(int i=1;i<points.length;i+=1) {

        Point2D.Double current = points[i];
        Point2D.Double last = points[i-1];

edges[i] = new Edge(current,last);

    }

    Point2D.Double current = points[0];
    Point2D.Double last = points[points.length-1];

    edges[0] = new Edge(current,last);

    return edges;
}


    private Point2D.Double[] calcCorners() {
        Point2D.Double[] corners = new Point2D.Double[4];

        // Calculate the half-length of the square
        double halfLengthX = this.width / 2.0;
        double halfLengthY = this.height / 2.0;

        // Calculate the coordinates of the corners before rotation
        double[] originalX = {-halfLengthX, halfLengthX, halfLengthX, -halfLengthX};
        double[] originalY = {-halfLengthY, -halfLengthY, halfLengthY, halfLengthY};


        // 0 is the id for min 3 is the id for max

        // Rotate the corners
        for (int i = 0; i < 4; i++) {
            double rotatedX = x + (originalX[i] * Math.cos(angle) - originalY[i] * Math.sin(angle));
            double rotatedY = y + (originalX[i] * Math.sin(angle) + originalY[i] * Math.cos(angle));
            corners[i] = new Point2D.Double(rotatedX, rotatedY);
        }


        minPoint = corners[1];
        maxPoint = corners[3];

        return corners;
    }


  public void setPosition(double x,double y) {
        this.x = x;
        this.y = y;

  }


  public void tick() {





      // Draw the corner points
      int cornerSize = 9; // Adjust the size of the corner points as needed



      //This is used to get the vertices of the cube and the min and max
      vertices = calcCorners();




      Edge[] edges = calcEdges(vertices);


      for (int i = 0; i < edges.length; i++) {

          Edge edge = edges[i];



          Vector2D v = edge.toVector();


          if (i == 0) {


              yAxisRelative = v;

          } else if (i == 1) {
              v.reverse();

              xAxisRelative = v;


          }
          Vector2D par = v.getPerp();

          par.normalize();
          Point2D.Double mid = edge.getMid();


          normals[i] = par;



      }



if(!Static) {

    velocity.add(accleration);


    totalvelocity = Vector2D.add(velocity,localvelocity);

    x += totalvelocity.x;
    y -= totalvelocity.y;

}

  }







    public void setTexture(Image texture) {
        this.texture = texture;
        hasTexture = true;
    }

    public Image getTexture() {
        return texture;
    }

    public boolean HasTexture() {
        return hasTexture;
    }

    public void setAccleration(Vector2D accleration) {
        this.accleration = accleration;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }
}
