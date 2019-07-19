package com.movsci.processingapi.helpers;

import com.movsci.processingapi.Model.MovSciPoint;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoHelpers {

    public static Boolean trackVideo(VideoCapture cap, String pointDefs, String outPath){
        //set output vid properties
        Size frameSize = new Size((int) cap.get(Videoio.CAP_PROP_FRAME_WIDTH), (int) cap.get(Videoio.CAP_PROP_FRAME_HEIGHT));
        double fps = cap.get(Videoio.CAP_PROP_FPS);

        //set output video
        VideoWriter videoWriter = new VideoWriter(outPath,VideoWriter.fourcc('x','2','6','4'),fps,frameSize,true);

        //initialize stuff needed for tracking
        ArrayList<MovSciPoint> initialPoints = new ArrayList<>();
        ArrayList<MovSciPoint> trailingPoints = new ArrayList<>();
        ArrayList<MovSciPoint> nextPoints = new ArrayList<>();

        //parse input pointDefs - iterate through by number of inputs per point
        List<String> str = Arrays.asList(pointDefs.split(","));
        List<Integer> numbersInt = new ArrayList<>();
        for (String number : str) {//convert string to int
            //float tmp = Float.parseFloat(number.trim());
            //int tmp2 = (int) tmp;
            //numbersInt.add(tmp2);
        }
        int numPoints = initialPoints.size();

        //read frames and track
        Mat nextMat = new Mat();
        int frame = 0;
        while(cap.read(nextMat)){
            if(frame == 0){
                //draw stuff on nextMat with initialPoints and then
                Write(nextMat,videoWriter);
            }else{
                //track points, initial to next
                //draw stuff on nextMat with nextPoints
                try {
                    FrameHelpers.DrawOnFrame(nextMat,nextPoints);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Write(nextMat,videoWriter);
            }
        }
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
