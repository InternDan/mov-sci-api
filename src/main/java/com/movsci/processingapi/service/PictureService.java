package com.movsci.processingapi.service;

import com.movsci.processingapi.dto.FirstFrameResponse;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PictureService extends SharedService {
    public FirstFrameResponse createFirstFrame(){
        //TODO: load video
        //String filePath = downloadFile();
        //create first frame
        //for testing
        String filePath = "C:\\Users\\buffa\\Google Drive\\Google Photos\\VID_20181225_155323140.mp4";
        VideoCapture cap = new VideoCapture();
        cap.open(filePath);
        if(!cap.isOpened()){
            log.error("Could not open video file");
            return new FirstFrameResponse("failed to open file",null);
        }else{
            log.info("Video file" + filePath + "opened.");
        }
        Mat mat = new Mat();
        cap.read(mat);
        log.info(("width, height = "+mat.cols()+", "+mat.rows()));


        //TODO: Upload first frame
        Boolean success = uploadFile();
        //Create response

        return new FirstFrameResponse();
    }


}
