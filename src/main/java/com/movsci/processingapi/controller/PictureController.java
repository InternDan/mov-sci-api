package com.movsci.processingapi.controller;

import com.movsci.processingapi.Config.SecurityConfig;
import com.movsci.processingapi.dto.FirstFrameRequest;
import com.movsci.processingapi.dto.FirstFrameResponse;
import com.movsci.processingapi.service.PictureService;
import com.movsci.processingapi.service.VideoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/picture")
public class PictureController {

    private PictureService pictureService;
    private VideoService videoService;
    @Value("${secretKey}")
    private String secretKey;

    @Autowired
    public void PictureController(
        PictureService pictureService,
        VideoService videoService
    )
    {
        this.pictureService = pictureService;
        this.videoService = videoService;
    }

    @ApiOperation(value = "Test GET Method", response = String.class)
    @GetMapping
    public String working(){
        //for testing only
        FirstFrameResponse firstFrameResponse = pictureService.createFirstFrame("1edaa588-1d09-49ee-b795-0995ab9aca4c.mp4");
        return "Working!";
    }

    @ApiOperation(value = "Create first frame of video",response = FirstFrameResponse.class)
    @PostMapping(path = "/FirstFrame", consumes="application/json",produces = "application/json")
    public ResponseEntity<?> createFirstFrameOfVideo(@RequestBody FirstFrameRequest firstFrameRequest){
        if(firstFrameRequest.getSecretKey().contentEquals(secretKey)){
            FirstFrameResponse firstFrameResponse = pictureService.createFirstFrame(firstFrameRequest.getVideoFileName());
            return ResponseEntity.ok(firstFrameResponse);
        }
        else{
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

}
