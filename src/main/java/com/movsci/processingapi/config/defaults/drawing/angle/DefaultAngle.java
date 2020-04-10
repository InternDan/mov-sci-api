package com.movsci.processingapi.config.defaults.drawing.angle;

import org.opencv.core.Scalar;
import org.springframework.beans.factory.annotation.Value;


public abstract class DefaultAngle {
    @Value("${drawing.point.trailing.color.r}")
    int anglePointR;
    @Value("${drawing.point.trailing.color.g}")
    int anglePointG;
    @Value("${drawing.point.trailing.color.b}")
    int anglePointB;
    @Value("${drawing.point.trailing.color.a}")
    int anglePointA;
    @Value("${drawing.point.trailing.radius}")
    int anglePointRadius;
    @Value("${drawing.point.trailing.lineType}")
    int anglePointLineType;
    @Value("${drawing.point.trailing.thickness}")
    int anglePointThickness;
    Scalar anglePointColor;
    @Value("${drawing.angle.line.color.r}")
    int angleLineR;
    @Value("${drawing.angle.line.color.g}")
    int angleLineG;
    @Value("${drawing.angle.line.color.b}")
    int angleLineB;
    @Value("${drawing.angle.line.color.a}")
    int angleLineA;
    @Value("${drawing.angle.line.thickness}")
    int angleLineThickness;
    @Value("${drawing.angle.line.type}")
    int angleLineType;
    Scalar angleLineColor;
    @Value("${drawing.angle.string.font.hscale}")
    int angleFontHscale;
    @Value("${drawing.angle.string.font.vscale}")
    int angleFontVscale;
    @Value("${drawing.angle.string.font.shear}")
    int angleFontShear;
    @Value("${drawing.angle.string.font.lineType}")
    int angleFontLineType;
    @Value("${drawing.angle.string.fontFace}")
    int angleFontFace;
    @Value("${drawing.angle.string.color.r}")
    int angleFontR;
    @Value("${drawing.angle.string.color.g}")
    int angleFontG;
    @Value("${drawing.angle.string.color.b}")
    int angleFontB;
    @Value("${drawing.angle.string.color.a}")
    int angleFontA;
    Scalar angleFontColor;

    public DefaultAngle(){
        this.angleFontColor = new Scalar(angleFontB,angleFontG,angleFontR,angleFontA);
        this.angleLineColor = new Scalar(angleLineB,angleLineG,angleLineR,angleLineA);
        this.anglePointColor = new Scalar(anglePointB,anglePointG,anglePointR,anglePointA);
    }

}
