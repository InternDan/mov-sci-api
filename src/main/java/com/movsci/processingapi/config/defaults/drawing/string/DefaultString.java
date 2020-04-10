package com.movsci.processingapi.config.defaults.drawing.string;

import org.opencv.core.Scalar;
import org.springframework.beans.factory.annotation.Value;

public class DefaultString {
    @Value("${drawing.string.font.hscale}")
    int stringFontHscale;
    @Value("${drawing.string.font.vscale}")
    int stringFontVscale;
    @Value("${drawing.string.font.shear}")
    int stringFontShear;
    @Value("${drawing.string.font.lineType}")
    int stringFontLineType;
    @Value("${drawing.string.fontFace}")
    int stringFontFace;
    @Value("${drawing.string.color.r}")
    int stringFontR;
    @Value("${drawing.string.color.g}")
    int stringFontG;
    @Value("${drawing.string.color.b}")
    int stringFontB;
    @Value("${drawing.string.color.a}")
    int stringFontA;
    @Value("${drawing.string.fontScale}")
    int stringFontScale;
    @Value("${drawing.string.thickness}")
    int stringThickness;
    Scalar stringColor;

    public DefaultString(){
        this.stringColor = new Scalar(stringFontB,stringFontG,stringFontR,stringFontA);
    }
}
