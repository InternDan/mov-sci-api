package com.movsci.processingapi.config.defaults.drawing.point.trailing;

import lombok.Getter;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class DefaultTrailingPoint {
    @Value("${drawing.point.trailing.color.r}")
    public static int pointR;
    @Value("${drawing.point.trailing.color.g}")
    public static int pointG;
    @Value("${drawing.point.trailing.color.b}")
    public static int pointB;
    @Value("${drawing.point.trailing.color.a}")
    public static int pointA;
    @Value("${drawing.point.trailing.radius}")
    public static int pointRadius;
    @Value("${drawing.point.trailing.lineType}")
    public static int pointLineType;
    @Value("${drawing.point.trailing.thickness}")
    public static int pointThickness;
    @Value("${drawing.point.trailing.trailingPoints}")
    public static Scalar pointColor;

    public DefaultTrailingPoint(){
        this.pointColor = new Scalar(pointB,pointG,pointR,pointA);
    }

}
