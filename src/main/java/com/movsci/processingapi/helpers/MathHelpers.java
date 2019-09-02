package com.movsci.processingapi.helpers;

import com.movsci.processingapi.Model.MovSciPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import java.util.ArrayList;

public class MathHelpers {

    public static double twoPointGlobalAngle(ArrayList<MovSciPoint> movSciPoints){
        //retuns an angle relative to the top of the screen in degrees
        for (int i = 0; i < movSciPoints.size(); i = i + 2){
            Point p1 = movSciPoints.get(i).getPoint();
            Point p2 = movSciPoints.get(i+1).getPoint();
            double dy = p2.y - p1.y;
            double dx = p2.x - p1.x;
            double result = Math.toDegrees(Math.abs(Math.atan2(dy,dx)));
            result = Math.abs(result);
            if (result >= 180) {
                return (360 - result);
            }else {
                return result;
            }
        }
        return Double.parseDouble(null);
    }

    public static double threePointSegmentAngle(ArrayList<MovSciPoint> movSciPoints) {
        for (int i = 0; i < movSciPoints.size(); i = i + 3){
            Point p1 = movSciPoints.get(i).getPoint();
            Point p2 = movSciPoints.get(i+1).getPoint();
            Point p3 = movSciPoints.get(i+2).getPoint();
            double result = Math.toDegrees(Math.atan2(p3.y - p1.y, p3.x - p1.x) - Math.atan2(p2.y - p1.y, p2.x - p1.x));
            result = Math.abs(result);
            if (result >= 180) {
                return (360 - result);
            }else {
                return result;
            }

        }
        return Double.parseDouble(null);
    }

    public static double fourPointSegmentAngle(ArrayList<MovSciPoint> movSciPoints) {
        for (int i = 0; i < movSciPoints.size(); i = i + 4){
            Point p1 = movSciPoints.get(i).getPoint();
            Point p2 = movSciPoints.get(i+1).getPoint();
            Point p3 = movSciPoints.get(i+2).getPoint();
            Point p4 = movSciPoints.get(i+3).getPoint();
            double angle1 = Math.atan2(p3.y - p4.y, p3.x - p4.x);
            double angle2 = Math.atan2(p1.y - p2.y, p1.x - p2.x);
            double result = Math.toDegrees(Math.abs(angle1-angle2));
            if (result >= 180) {
                return Math.abs(360 - result);
            }else {
                return Math.abs(result);
            }

        }
        return Double.parseDouble(null);
    }

    public double sampen(ArrayList<Float> y){
        // standardize
        //find mean
        float sum = 0;
        for (Float ys : y){
            sum += ys;
        }
        float yMean = sum / y.size();
        //subtract mean
        int c=0;
        for (Float ys : y){
            float t = ys - yMean;
            y.set(c,t);
            c++;
        }
        //find mean square value
        float ct=0;
        for (Float ys : y){
            float t = ys * ys;
            ct = ct + t;
        }
        ct=ct / y.size();
        float s = (float) Math.sqrt((double) ct);
        //divide by mean square
        c=0;
        for (Float ys : y){
            y.set(c,ys/s);
        }

        int wlen = 3;
        float r = (float) 0.2;
        int A = 0;
        int B = 0;
        int i,j,k;
        double m;
        int numSamples = y.size();
        int shift = 1;

        for (i=0; i < numSamples-wlen*shift-shift; i += shift) {
            /* compare to all following windows > i */
            for (j=i+shift; j < numSamples-wlen*shift-shift; j+=shift) {
                m = 0; /* maximum so far */
                /* get max cheb. distance */
                for (k=0; k < wlen; k++){
                    m = Math.max(m, Math.abs(y.get(i + k * shift) - y.get(j + k * shift)));
                }
                /* first case, distance lower in first wlen positions */
                if (m < r){
                    B++;
                }
                /* Second case, distance lower if we add the next element */
                if (Math.max(m, Math.abs(y.get(i+wlen*shift)-y.get(j+wlen*shift))) < r){
                    A++;
                }
            }
        }
        /* return -log A/B */
        if (A>0 && B >0)
            return (-1 * Math.log(((double) A) / ((double) B)));
        else
            return 0;
    }

    public static Scalar parseTriplet(String triplet, double fourth){
        String trip = triplet.substring(1,triplet.length()-1);
        String[] trips = trip.split(",");
        Scalar scalar = new Scalar(Double.valueOf(trips[0]),Double.valueOf(trips[1]),Double.valueOf(trips[2]), fourth);
        return scalar;
    }
}
