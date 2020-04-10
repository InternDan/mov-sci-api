package com.movsci.processingapi.model;

import com.movsci.processingapi.config.PointTypes;
import com.movsci.processingapi.config.defaults.drawing.point.current.DefaultCurrentPoint;
import lombok.*;
import org.opencv.core.Point;
import org.springframework.beans.factory.annotation.Value;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovSciPoint extends DefaultCurrentPoint {
    Point point;
    String pointType;
}
