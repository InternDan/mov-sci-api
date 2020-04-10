package com.movsci.processingapi.helpers;

import com.movsci.processingapi.model.DrawingInformation;
import com.movsci.processingapi.model.MovSciPoint;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static org.opencv.imgcodecs.Imgcodecs.imwrite;

public class FrameHelpers {

    private static DecimalFormat df = new DecimalFormat("###.#");

    public static Mat DrawOnFrame(Mat frame, ArrayList<ArrayList<MovSciPoint>> movSciPointsArray, DrawingInformation drawingInformation, int frameCount) throws Exception {

        ArrayList<MovSciPoint> movSciPoints = movSciPointsArray.get(movSciPointsArray.size()-1);//TODO: maybe not -1
        //TODO: fix not drawing most recent point
        frame = drawTrailingPoints(frame,movSciPointsArray,drawingInformation,frameCount);
        //imwrite("c:\\users\\buffa\\desktop\\myImg.tif",frame);
        int ptCount = 0;
        while (ptCount < movSciPoints.size()) {
            MovSciPoint point = movSciPoints.get(ptCount);
            ArrayList<MovSciPoint> in = new ArrayList<>();
            if (point.getType().equals("pt")) {
                frame = drawPoint(frame, movSciPoints.get(ptCount));
                imwrite("c:\\users\\buffa\\desktop\\myImg.tif",frame);
                ptCount = ptCount + 1;
            } else if (point.getType().equals("ln")) {
                in.add(movSciPoints.get(ptCount));
                in.add(movSciPoints.get(ptCount + 1));
                frame = drawLine(frame, in);
                ptCount = ptCount + 2;
            } else if (point.getType().equals("ang2")) {
                in.add(movSciPoints.get(ptCount));
                in.add(movSciPoints.get(ptCount + 1));
                frame = draw2PointAngle(frame, in);
                ptCount = ptCount + 2;
            } else if (point.getType().equals("ang3")) {
                in.add(movSciPoints.get(ptCount));
                in.add(movSciPoints.get(ptCount + 1));
                in.add(movSciPoints.get(ptCount + 2));
                frame = draw3PointAngle(frame, in);
                ptCount = ptCount + 3;
            } else if (point.getType().equals("ang4")) {
                in.add(movSciPoints.get(ptCount));
                in.add(movSciPoints.get(ptCount + 1));
                in.add(movSciPoints.get(ptCount + 2));
                in.add(movSciPoints.get(ptCount + 3));
                frame = draw4PointAngle(frame, in);
                ptCount = ptCount + 4;
            } else {
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

    //todo: read in trailing point color
    private static Mat drawTrailingPoints(Mat frame, ArrayList<ArrayList<MovSciPoint>> movSciPointArray, DrawingInformation drawingInformation, int frameCount){
        for(int i = 1; i <= frameCount; i++){
            for(int j = 1; j < movSciPointArray.size(); j++){
                ArrayList<MovSciPoint> movSciPoints = movSciPointArray.get(movSciPointArray.size()-(j));
                for(int k=0; k < movSciPoints.size(); k++){
                    MovSciPoint pt = new MovSciPoint(movSciPoints.get(k));
                    if(pt.getType().equals("pt") && i < pt.getTrailingPoints() && j < pt.getTrailingPoints()){
                        pt.setColor(drawingInformation.getColorTrailingPoint());
                        pt.setRadius(drawingInformation.getTrailingPointSize());
                        frame = drawPoint(frame,pt);
                    }
                }
            }
        }
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
    //todo: draw other shapes

}
