package com.movsci.processingapi.service;

import com.microsoft.azure.storage.StorageException;
import com.movsci.processingapi.model.DrawingInformation;
import com.movsci.processingapi.dto.TrackVideoRequest;
import com.movsci.processingapi.dto.TrackVideoResponse;
import com.movsci.processingapi.helpers.MathHelpers;
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

    public TrackVideoResponse trackVideo(TrackVideoRequest trackVideoRequest){

        String blobName =trackVideoRequest.getBlobName();
        String pointDefs = trackVideoRequest.getPointDefs();
        DrawingInformation drawingInformation = new DrawingInformation(MathHelpers.parseTriplet(trackVideoRequest.getColorTrailingPoint(),0)
                ,MathHelpers.parseTriplet(trackVideoRequest.getColorText(),0)
                , MathHelpers.parseTriplet(trackVideoRequest.getColorAngleText(),0)
                ,Integer.valueOf(trackVideoRequest.getSizeTrailingPoint())));;

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
            return new TrackVideoResponse(null);
        }else{
            log.info("Video file " + downloadedFile.getName() + " opened.");
        }

        boolean success = VideoHelpers.trackVideo(cap,pointDefs,drawingInformation,downloadedFile);

        if(success) {
            String trackedName = "Tracked-" + downloadedFile.getName();
            try {
                uploadFile(trackedName);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (StorageException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new TrackVideoResponse(trackedName);
        }else{
            return new TrackVideoResponse(null);
        }
    }
}
