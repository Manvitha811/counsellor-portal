package com.miniproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter@NoArgsConstructor
public class EnqFilterRequestDto {
    private String classMode;
    private Integer courseId;
    private String enqStatus;
}

