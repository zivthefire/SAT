package org.example;

public class AngleMath {



public static double addDegrees(double angle,double addition) {


    double result = angle+addition;

    if(result > 360) {

        double delta = result-360;

        return 0+delta;
    }


    return result;



}





}
