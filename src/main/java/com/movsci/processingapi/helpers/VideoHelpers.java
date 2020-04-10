package com.movsci.processingapi.helpers;

import com.movsci.processingapi.model.DrawingInformation;
import com.movsci.processingapi.model.MovSciPoint;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.opencv.core.TermCriteria.COUNT;

@Slf4j
public class VideoHelpers {

    public static Boolean trackVideo(VideoCapture cap, String pointDefs, DrawingInformation drawingInformation, File downloadedFile){
        //set output vid properties
        Size frameSize = new Size((int) cap.get(Videoio.CAP_PROP_FRAME_WIDTH), (int) cap.get(Videoio.CAP_PROP_FRAME_HEIGHT));
        double fps = cap.get(Videoio.CAP_PROP_FPS);
        double numFrames = cap.get(Videoio.CAP_PROP_FRAME_COUNT);

        //initialize stuff needed for tracking
        ArrayList<ArrayList<MovSciPoint>> points = new ArrayList<>();
        ArrayList<MovSciPoint> initialPoints = new ArrayList<>();
        ArrayList<MovSciPoint> nextPoints = new ArrayList<>();

        //parse input pointDefs - iterate through by number of inputs per point
        List<String> str = Arrays.asList(pointDefs.replaceAll("\\(","").replaceAll("\\)","").split(","));
        List<Integer> numbersInt = new ArrayList<>();
        for (int i=0; i<str.size(); i=str.size()) {
            MovSciPoint pt = new MovSciPoint();
            pt.setType(str.get(i));
            pt.setColor(new Scalar(Integer.parseInt(str.get(i+1)),Integer.parseInt(str.get(i+2)),Integer.parseInt(str.get(i+3)),0));
            pt.setTrailingPoints(Integer.parseInt(str.get(i+4)));
            pt.setRadius(Integer.parseInt(str.get(i+5)));
            pt.setPoint(new Point(Double.parseDouble(str.get(i+6)),Double.parseDouble(str.get(i+7))));
            initialPoints.add(pt);
        }
        points.add(initialPoints);
        int numPoints = points.size();

        //set output video

        String outPath = downloadedFile.getParent();
        String outName = "Tracked-" + downloadedFile.getName();
        VideoWriter videoWriter = new VideoWriter(outPath + "\\" + outName,VideoWriter.fourcc('x','2','6','4'),fps,frameSize,true);
        //read frames and track
        Mat nextMat = new Mat();
        Mat prevMat = new Mat();
        int frame = 0;
        while(cap.read(nextMat)){
            if(frame == 0){
                //draw stuff on nextMat with initialPoints and then
                try {
                    FrameHelpers.DrawOnFrame(nextMat,points,drawingInformation,frame);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Write(nextMat, videoWriter);
                prevMat = nextMat.clone();
                frame++;
            }else{
                //track points, initial to next
                try {
                    nextPoints = advanceTrackedFrame(prevMat,nextMat,initialPoints);
                    points.add(nextPoints);
                    initialPoints.clear();
                    initialPoints.addAll(nextPoints);
                    FrameHelpers.DrawOnFrame(nextMat,points,drawingInformation,frame);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Write(nextMat,videoWriter);
                prevMat = nextMat.clone();
                frame++;
            }
            double percent = frame/numFrames;
            log.info(Double.toString(percent));
        }
        videoWriter.release();
        cap.release();
        log.info("Capture released");
        return true;
    }

    private static ArrayList<MovSciPoint> advanceTrackedFrame(Mat prevMat, Mat nextMat, ArrayList<MovSciPoint> currPoints){
        //initialize required objects
        Mat nextGray = new Mat(prevMat.rows(), prevMat.cols(), CvType.CV_8UC3);
        Mat prevGray = new Mat(prevMat.rows(), prevMat.cols(), CvType.CV_8UC3);
        MatOfPoint2f prevFeatures = new MatOfPoint2f();
        MatOfPoint2f nextFeatures = new MatOfPoint2f();
        MatOfByte status = new MatOfByte();
        MatOfFloat err = new MatOfFloat();
        Size winSize = new Size(50, 50);//can be customized
        double epsilon = 0.001;
        TermCriteria termCrit = new TermCriteria(COUNT, 20, epsilon);
        int maxLevel = 3;
        double eigenThreshold = 0.001;
        Imgproc.cvtColor(nextMat,nextGray, Imgproc.COLOR_RGBA2GRAY);
        Imgproc.cvtColor(prevMat,prevGray, Imgproc.COLOR_RGBA2GRAY);
        ArrayList<Point> points = new ArrayList<>();
        List<Point> nextPointsP = new ArrayList<>();
        ArrayList<MovSciPoint> nextPointsM = new ArrayList<>();
        for(MovSciPoint p : currPoints){
            points.add(p.getPoint());
        }
        prevFeatures.fromList(points);
        nextFeatures.fromArray(prevFeatures.toArray());//helps initial estimate

        Video.calcOpticalFlowPyrLK(prevGray, nextGray, prevFeatures, nextFeatures, status, err, winSize, maxLevel, termCrit, 0, eigenThreshold);
        nextPointsP = nextFeatures.toList();
        for(int i=0; i < currPoints.size(); i++){
            MovSciPoint pt = new MovSciPoint(currPoints.get(i).getType()
                    ,currPoints.get(i).getTrailingPoints()
                    ,currPoints.get(i).getColor()
                    ,currPoints.get(i).getThickness()
                    ,currPoints.get(i).getLineType()
                    ,currPoints.get(i).getRadius()
                    ,nextPointsP.get(i));
            nextPointsM.add(pt);
        }

        return nextPointsM;//change to movscipoints
    }


    private static void Write(Mat frame, VideoWriter videoWriter) {
        if(videoWriter.isOpened()==false){
            videoWriter.release();
            throw new IllegalArgumentException("Video Writer Exception: VideoWriter not opened,"
                    + "check parameters.");
        }
        //Write video
        videoWriter.write(frame);
    }
}
