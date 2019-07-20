package com.movsci.processingapi.helpers;

import com.movsci.processingapi.Model.MovSciPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FrameHelpers {

    private static DecimalFormat df = new DecimalFormat("###.#");

    public static Mat DrawOnFrame(Mat frame, ArrayList<MovSciPoint> movSciPoints) throws Exception {

        int ptCount = 0;
        while(ptCount < movSciPoints.size()){
            MovSciPoint point = movSciPoints.get(ptCount);
            ArrayList<MovSciPoint> in = new ArrayList<>();
            if(point.getType().equals("pt")) {
                frame = drawPoint(frame, movSciPoints.get(ptCount));
                ptCount = ptCount + 1;
            }else if(point.getType().equals("ln")) {
                in.add(movSciPoints.get(ptCount));
                in.add(movSciPoints.get(ptCount + 1));
                frame = drawLine(frame, in);
                ptCount = ptCount + 2;
            }else if(point.getType().equals("ang2")) {
                in.add(movSciPoints.get(ptCount));
                in.add(movSciPoints.get(ptCount + 1));
                frame = draw2PointAngle(frame, in);
                ptCount = ptCount + 2;
            }else if(point.getType().equals("ang3")) {
                in.add(movSciPoints.get(ptCount));
                in.add(movSciPoints.get(ptCount + 1));
                in.add(movSciPoints.get(ptCount + 2));
                frame = draw3PointAngle(frame, in);
                ptCount = ptCount + 3;
            }else if(point.getType().equals("ang4")) {
                in.add(movSciPoints.get(ptCount));
                in.add(movSciPoints.get(ptCount + 1));
                in.add(movSciPoints.get(ptCount + 2));
                in.add(movSciPoints.get(ptCount + 3));
                frame = draw4PointAngle(frame, in);
                ptCount = ptCount + 4;
            }else{
                //todo: put in better exception
                throw new Exception();
            }
        }
        return frame;
    }

    private static Mat drawPoint(Mat frame, MovSciPoint movSciPoint){
        Imgproc.circle(frame,
                movSciPoint.getPoint(),
                movSciPoint.getRadius(),
                movSciPoint.getColor(),
                movSciPoint.getThickness(),
                movSciPoint.getLineType());
        return frame;
    }

    private static Mat drawLine(Mat frame, ArrayList<MovSciPoint> movSciPoints){
        frame = drawPoint(frame,movSciPoints.get(0));
        frame = drawPoint(frame,movSciPoints.get(1));
        //todo: get properties of line
        Imgproc.line(frame,movSciPoints.get(0).getPoint(),movSciPoints.get(1).getPoint(),new Scalar(150,150,150,0),8 );
        return frame;
    }

    private static Mat draw2PointAngle(Mat frame, ArrayList<MovSciPoint> movSciPoints){
        frame = drawLine(frame, movSciPoints);
        double ang = MathHelpers.twoPointGlobalAngle(movSciPoints);
        Imgproc.putText(frame,df.format(ang),movSciPoints.get(0).getPoint(),3,3,new Scalar(200,200,200,0),2,1,false);
        return frame;
    }

    private static Mat draw3PointAngle(Mat frame, ArrayList<MovSciPoint> movSciPoints){
        ArrayList<MovSciPoint> firstLeg = new ArrayList<>();
        firstLeg.add(movSciPoints.get(0));
        firstLeg.add(movSciPoints.get(1));
        frame = drawLine(frame, firstLeg);
        ArrayList<MovSciPoint> secondLeg = new ArrayList<>();
        secondLeg.add(movSciPoints.get(0));
        secondLeg.add(movSciPoints.get(2));
        frame = drawLine(frame, secondLeg);
        double ang = MathHelpers.threePointSegmentAngle(movSciPoints);
        Imgproc.putText(frame,df.format(ang),movSciPoints.get(0).getPoint(),3,3,new Scalar(200,200,200,0),2,1,false);
        return frame;
    }

    private static Mat draw4PointAngle(Mat frame, ArrayList<MovSciPoint> movSciPoints){
        ArrayList<MovSciPoint> firstLeg = new ArrayList<>();
        firstLeg.add(movSciPoints.get(0));
        firstLeg.add(movSciPoints.get(1));
        frame = drawLine(frame, firstLeg);
        ArrayList<MovSciPoint> secondLeg = new ArrayList<>();
        secondLeg.add(movSciPoints.get(2));
        secondLeg.add(movSciPoints.get(3));
        frame = drawLine(frame, secondLeg);
        double ang = MathHelpers.fourPointSegmentAngle(movSciPoints);
        Imgproc.putText(frame,df.format(ang),movSciPoints.get(0).getPoint(),3,3,new Scalar(200,200,200,0),2,1,false);
        return frame;
    }

    private static Mat trackPoints(Mat prevFrame, Mat currFrame, ArrayList<MovSciPoint> movSciPointsInitial){

        return currFrame;
    }
    //todo: draw other shapes

}
