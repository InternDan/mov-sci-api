package com.movsci.processingapi.config.defaults.drawing.point.current;

import lombok.Getter;
import org.opencv.core.Scalar;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class DefaultCurrentPoint {
    @Value("${drawing.point.current.color.r}")
    public static int pointR;
    @Value("${drawing.point.current.color.g}")
    public static int pointG;
    @Value("${drawing.point.current.color.b}")
    public static int pointB;
    @Value("${drawing.point.current.color.a}")
    public static int pointA;
    @Value("${drawing.point.current.radius}")
    public static int pointRadius;
    @Value("${drawing.point.current.lineType}")
    public static int pointLineType;
    @Value("${drawing.point.current.thickness}")
    public static int pointThickness;
    @Value("${drawing.point.current.trailingPoints}")
    public static int pointTrailingPoints;
    public static Scalar pointColor;

    public DefaultCurrentPoint(){
        this.pointColor = new Scalar(pointB,pointG,pointR,pointA);
    }
}
