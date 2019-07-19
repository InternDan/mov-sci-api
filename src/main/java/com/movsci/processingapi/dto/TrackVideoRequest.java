package com.movsci.processingapi.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrackVideoRequest {
    String blobName;
    String secretKey;
    String pointDefs;
}
