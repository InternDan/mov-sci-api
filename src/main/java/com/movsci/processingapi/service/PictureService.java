package com.movsci.processingapi.service;

import com.microsoft.azure.storage.StorageException;
import com.movsci.processingapi.dto.FirstFrameResponse;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

@Service
@Slf4j
public class PictureService extends SharedService {
    public FirstFrameResponse createFirstFrame(String blobName){
        //TODO: load video
        File downloadedFile = null;
        try {
            downloadedFile = new File(downloadFile(blobName));
        } catch (IOException | URISyntaxException | InvalidKeyException | StorageException e) {
            e.printStackTrace();
            return null;
        }
        //create first frame
        VideoCapture cap = new VideoCapture();
        cap.open(downloadedFile.getName());
        if(!cap.isOpened()){
            log.error("Could not open video file");
            return new FirstFrameResponse("failed to open file",null);
        }else{
            log.info("Video file " + downloadedFile.getName() + " opened.");
        }
        Mat mat = new Mat();
        cap.read(mat);
        log.info(("width, height = "+mat.cols()+", "+mat.rows()));
        //convert to bitmap
        //use blob filename plus -firstframe.png
        String tmpName = "first-frame-" + downloadedFile.getName() + ".png";
        Imgcodecs.imwrite(tmpName,mat);

        //TODO: Upload first frame
        try {
            Boolean success = uploadFile(tmpName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Create response
        return new FirstFrameResponse("success",tmpName);
    }


}
