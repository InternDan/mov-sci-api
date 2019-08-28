package com.movsci.processingapi.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrackVideoRequest {
    String colorTrailingPoint;
    String colorText;
    String colorAngleText;
    String sizeTrailingPoint;
    String blobName;
    String secretKey;
    String pointDefs;
}
