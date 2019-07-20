package com.movsci.processingapi.helpers;

import com.movsci.processingapi.Model.MovSciPoint;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class VideoHelpers {

    public static Boolean trackVideo(VideoCapture cap, String pointDefs, File downloadedFile){
        //set output vid properties
        Size frameSize = new Size((int) cap.get(Videoio.CAP_PROP_FRAME_WIDTH), (int) cap.get(Videoio.CAP_PROP_FRAME_HEIGHT));
        double fps = cap.get(Videoio.CAP_PROP_FPS);

        //initialize stuff needed for tracking
        ArrayList<MovSciPoint> initialPoints = new ArrayList<>();
        ArrayList<MovSciPoint> trailingPoints = new ArrayList<>();
        ArrayList<MovSciPoint> nextPoints = new ArrayList<>();

        //parse input pointDefs - iterate through by number of inputs per point
        List<String> str = Arrays.asList(pointDefs.replaceAll("\\(","").replaceAll("\\)","").split(","));
        List<Integer> numbersInt = new ArrayList<>();
        for (int i=0; i<str.size(); i=i+7) {
            MovSciPoint pt = new MovSciPoint();
            pt.setType(str.get(i));
            pt.setColor(new Scalar(Integer.valueOf(str.get(i+1)),Integer.valueOf(str.get(i+2)),Integer.valueOf(str.get(i+3)),0));
            pt.setRadius(Integer.valueOf(str.get(i+4)));
            pt.setPoint(new Point(Double.valueOf(str.get(i+5)),Double.valueOf(str.get(i+6))));
            initialPoints.add(pt);
        }
        int numPoints = initialPoints.size();

        //set output video

        String outPath = downloadedFile.getParent();
        String outName = "Tracked-" + downloadedFile.getName();
        VideoWriter videoWriter = new VideoWriter(outPath + "\\" + outName,VideoWriter.fourcc('x','2','6','4'),fps,frameSize,true);
        //read frames and track
        Mat nextMat = new Mat();
        int frame = 0;
        while(cap.read(nextMat)){
            if(frame == 0){
                //draw stuff on nextMat with initialPoints and then
                try {
                    FrameHelpers.DrawOnFrame(nextMat,initialPoints);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Write(nextMat, videoWriter);
            }else{
                //track points, initial to next
                try {
                    FrameHelpers.DrawOnFrame(nextMat,initialPoints);
                    //FrameHelpers.DrawOnFrame(nextMat,nextPoints);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Write(nextMat,videoWriter);
            }
        }
        videoWriter.release();
        cap.release();
        return true;
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
