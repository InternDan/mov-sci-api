package com.movsci.processingapi.Model;

import lombok.*;
import org.opencv.core.Point;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovSciPoint {
    String type;
    String color;
    String size;
    Point point;
}
