package com.movsci.processingapi.controller;

import com.movsci.processingapi.Model.DrawingInformation;
import com.movsci.processingapi.dto.FirstFrameRequest;
import com.movsci.processingapi.dto.FirstFrameResponse;
import com.movsci.processingapi.dto.TrackVideoRequest;
import com.movsci.processingapi.dto.TrackVideoResponse;
import com.movsci.processingapi.helpers.MathHelpers;
import com.movsci.processingapi.service.VideoService;
import io.swagger.annotations.ApiOperation;
import org.opencv.core.Scalar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Value("${secretKey}")
    private String secretKey;

    @ApiOperation(value = "Track points in video",response = TrackVideoRequest.class)
    @PostMapping(path = "/TrackVideo", consumes="application/json",produces = "application/json")
    public ResponseEntity<?> trackVideo(@RequestBody TrackVideoRequest trackVideoRequest){
        if(trackVideoRequest.getSecretKey().contentEquals(secretKey)){
            TrackVideoResponse trackVideoResponse = videoService.trackVideo(trackVideoRequest);

            return ResponseEntity.ok(trackVideoResponse);
        }
        else{
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }
}

