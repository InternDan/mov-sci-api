package com.movsci.processingapi.config.defaults.drawing.point.angle;

import lombok.Getter;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.springframework.beans.factory.annotation.Value;

@Getter
public abstract class DefaultAnglePoint {
    @Value("${drawing.angle.point.color.r}")
    public static int pointR;
    @Value("${drawing.angle.point.color.g}")
    public static int pointG;
    @Value("${drawing.angle.point.color.b}")
    public static int pointB;
    @Value("${drawing.angle.point.color.a}")
    public static int pointA;
    @Value("${drawing.angle.point.radius}")
    public static int pointRadius;
    @Value("${drawing.angle.point.lineType}")
    public static int pointLineType;
    @Value("${drawing.angle.point.thickness}")
    public static int pointThickness;
    public static Scalar pointColor;

    public DefaultAnglePoint(){
        this.pointColor = new Scalar(pointB,pointG,pointR,pointA);
    }
}
