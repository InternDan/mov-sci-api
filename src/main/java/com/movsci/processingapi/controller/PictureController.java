package com.movsci.processingapi.controller;

import com.movsci.processingapi.Config.SecurityConfig;
import com.movsci.processingapi.dto.FirstFrameRequest;
import com.movsci.processingapi.dto.FirstFrameResponse;
import com.movsci.processingapi.service.PictureService;
import com.movsci.processingapi.service.VideoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/picture")
public class PictureController {

    private PictureService pictureService;
    private VideoService videoService;

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
        return "Working!";
    }

    @ApiOperation(value = "Create first frame of video",response = FirstFrameResponse.class)
    @PostMapping(path = "/FirstFrame", consumes="application/json")
    public ResponseEntity<?> createFirstFrameOfVideo(@RequestBody FirstFrameRequest firstFrameRequest){
        FirstFrameResponse firstFrameResponse = new FirstFrameResponse();
        if(firstFrameRequest.getSecretKey().contentEquals(SecurityConfig.secretKey)){
            firstFrameResponse = pictureService.createFirstFrame();
            return ResponseEntity.ok(firstFrameResponse);
        }
        else{
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }


    }

}