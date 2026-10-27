package com.miniproject.sevice;

import com.miniproject.dto.DashboardResponseDto;
import com.miniproject.entity.Counsellor;
import com.miniproject.entity.Enquiry;
import com.miniproject.repo.CounsellorRepo;
import com.miniproject.repo.EnquiryRepo;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CounsellorServiceImpl implements CounsellorService{
    private CounsellorRepo counsellorRepo;
    private EnquiryRepo enquiryRepo;
    public CounsellorServiceImpl(CounsellorRepo counsellorRepo,EnquiryRepo enquiryRepo){
        this.counsellorRepo=counsellorRepo;
        this.enquiryRepo=enquiryRepo;
    }
    @Override
    public  boolean saveCounsellor(Counsellor counsellor){
        try{
            counsellorRepo.save(counsellor);
            return true;
        }
        catch(Exception e){
            return false;
        }

    }
    @Override
    public Counsellor login(String email,String pwd){
        Optional<Counsellor> opt=counsellorRepo.findByEmailAndPwd(email,pwd);
        return opt.orElse(null);
    }
    @Override
    public DashboardResponseDto getDashboardInfo(Integer counsellorId){
        List<Enquiry> enquiryList = enquiryRepo.findByCounsellorCounsellorId(counsellorId);
        int totalEnqsCount = enquiryList.size();

        Map<String, Long> statusWiseCountMap = enquiryList.stream()
                .collect(Collectors.groupingBy(Enquiry::getEnqStatus, Collectors.counting()));

        int newEnqsCount = statusWiseCountMap.getOrDefault("NEW",0L).intValue();
        int enrolledEnqsCount = statusWiseCountMap.getOrDefault("ENROLLED",0L).intValue();
        int lostEnqsCount = statusWiseCountMap.getOrDefault("LOST", 0L).intValue();

        DashboardResponseDto dto = new DashboardResponseDto();
        dto.setTotalEnqs(totalEnqsCount);
        dto.setEnrolledEnqs(enrolledEnqsCount);
        dto.setNewEnqs(newEnqsCount);
        dto.setLostEnqs(lostEnqsCount);
        return dto;
    }
}
