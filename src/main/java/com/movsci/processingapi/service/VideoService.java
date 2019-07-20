package com.movsci.processingapi.service;

import com.microsoft.azure.storage.StorageException;
import com.movsci.processingapi.dto.FirstFrameResponse;
import com.movsci.processingapi.dto.TrackVideoResponse;
import com.movsci.processingapi.helpers.VideoHelpers;
import lombok.extern.slf4j.Slf4j;
import org.opencv.videoio.VideoCapture;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

@Service
@Slf4j
public class VideoService extends SharedService {

    public TrackVideoResponse trackVideo(String blobName, String pointDefs){

        File downloadedFile = null;
        try {
            downloadedFile = new File(downloadFile(blobName));
        } catch (IOException | URISyntaxException | InvalidKeyException | StorageException e) {
            e.printStackTrace();
            return null;
        }

        VideoCapture cap = new VideoCapture();
        cap.open(downloadedFile.getName());
        if(!cap.isOpened()){
            log.error("Could not open video file");
            return new TrackVideoResponse("failed to open file",null);
        }else{
            log.info("Video file " + downloadedFile.getName() + " opened.");
        }

        boolean success = VideoHelpers.trackVideo(cap,pointDefs,downloadedFile);

        return new TrackVideoResponse();
    }
}
