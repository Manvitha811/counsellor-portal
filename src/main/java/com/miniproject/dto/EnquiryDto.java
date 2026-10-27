package com.miniproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnquiryDto {
    private Integer enqId;
    private String stuName;
    private String stuPhno;
    private String classMode;
    private Integer courseId;
    private String enqStatus;
}
