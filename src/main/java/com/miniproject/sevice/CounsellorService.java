package com.miniproject.sevice;

import com.miniproject.dto.DashboardResponseDto;
import com.miniproject.entity.Counsellor;

public interface CounsellorService {
    public boolean saveCounsellor(Counsellor counsellor);
    public Counsellor login(String email, String password);
    public DashboardResponseDto getDashboardInfo(Integer counsellorId);
}
