package com.movsci.processingapi.model;

import lombok.*;
import org.opencv.core.Scalar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DrawingInformation {
    private Scalar colorTrailingPoint = new Scalar(0,0,0,0);//duh
    private Scalar colorText = new Scalar(0,0,0,0);//duh
    private Scalar colorAngleText = new Scalar(0,0,0,0);//duh
    private int trailingPointSize = 20;
}
