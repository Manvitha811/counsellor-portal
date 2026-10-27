package com.miniproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DashboardResponseDto {
    private Integer totalEnqs;
    private Integer newEnqs;
    private Integer enrolledEnqs;
    private Integer lostEnqs;
}
