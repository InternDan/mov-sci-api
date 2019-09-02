package com.movsci.processingapi.Model;

import lombok.*;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovSciPoint {
    String type;//pt,ln,ang2,ang3,ang4
    int trailingPoints = 0;
    Scalar color = new Scalar(0,0,0,0);//duh
    int thickness = 5;//duh
    int lineType = 8;
    int radius = 0;//radius
    Point point;//x,y

    public MovSciPoint(MovSciPoint movSciPoint){
        this.type = movSciPoint.getType();
        this.trailingPoints = movSciPoint.getTrailingPoints();
        this.color = movSciPoint.getColor();
        this.thickness = movSciPoint.getThickness();
        this.lineType = movSciPoint.getLineType();
        this.radius = movSciPoint.getRadius();
        this.point = movSciPoint.getPoint();
    }
}
