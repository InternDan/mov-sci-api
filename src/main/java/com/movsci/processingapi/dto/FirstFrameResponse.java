package com.movsci.processingapi.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FirstFrameResponse {
    private String success;
    private String fileName;
}
