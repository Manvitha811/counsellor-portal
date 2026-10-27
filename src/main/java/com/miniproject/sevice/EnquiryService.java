package com.miniproject.sevice;

import com.miniproject.dto.EnqFilterRequestDto;
import com.miniproject.dto.EnquiryDto;
import com.miniproject.entity.Enquiry;

import java.util.List;

public interface EnquiryService {
    public boolean addEnquiry(EnquiryDto enq, Integer counsellorId);

    public List<Enquiry> getAllEnquiries(Integer counsellorId);

    public List<Enquiry> getEnquiriesWithFilters(EnqFilterRequestDto reqDto, Integer counsellorId);

    public EnquiryDto editEnquiry(Integer enqId);

    public boolean updateEnquiry(EnquiryDto enquiryDto);
}
