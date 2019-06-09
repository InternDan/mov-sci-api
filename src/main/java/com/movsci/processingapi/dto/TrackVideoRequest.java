package com.movsci.processingapi.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrackVideoRequest {
    String videoFileName;
    String secretKey;
    String pointDefs;
}
