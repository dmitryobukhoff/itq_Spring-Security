package com.example.jwtdemo.exception.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDetail {
    private String title;
    private int status;
    private String detail;
    private String instance;
    private String description;
}
