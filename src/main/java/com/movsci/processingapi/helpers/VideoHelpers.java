package com.movsci.processingapi.helpers;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class VideoHelpers {

    public static Boolean trackVideo(VideoCapture cap, String pointDefs){
        Mat mat = new Mat();
        cap.read(mat);
        

        return true;
    }

}
