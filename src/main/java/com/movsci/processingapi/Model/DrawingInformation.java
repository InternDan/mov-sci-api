package com.movsci.processingapi.Model;

import lombok.*;
import org.opencv.core.Scalar;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DrawingInformation {
    private Scalar colorTrailingPoint = new Scalar(0,255,0,0);//duh
    private Scalar colorText = new Scalar(0,0,0,0);//duh
    private Scalar colorAngleText = new Scalar(0,0,0,0);//duh
    private int trailingPointSize = 20;
}
