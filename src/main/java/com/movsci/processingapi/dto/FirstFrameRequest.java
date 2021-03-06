package com.movsci.processingapi.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FirstFrameRequest {
    private String videoFileName;
    private String secretKey;
}
