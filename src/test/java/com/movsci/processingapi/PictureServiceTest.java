package com.movsci.processingapi;

import com.movsci.processingapi.dto.FirstFrameResponse;
import com.movsci.processingapi.service.PictureService;
import org.junit.Test;

public class PictureServiceTest {
    private PictureService pictureService;

    public PictureServiceTest(PictureService pictureService){
        this.pictureService = pictureService;
    }

    /*@Test
    private void getFirstFrameYieldsImage(){
        FirstFrameResponse firstFrameResponse = pictureService.createFirstFrame();

    }*/
}
