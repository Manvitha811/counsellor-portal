package com.miniproject.sevice;

import com.miniproject.dto.EnqFilterRequestDto;
import com.miniproject.dto.EnquiryDto;
import com.miniproject.entity.Counsellor;
import com.miniproject.entity.Course;
import com.miniproject.entity.Enquiry;
import com.miniproject.repo.CounsellorRepo;
import com.miniproject.repo.CourseRepo;
import com.miniproject.repo.EnquiryRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnquiryServiceImpl implements EnquiryService {
    private EnquiryRepo enquiryRepo;
    private CounsellorRepo counsellorRepo;
    private CourseRepo courseRepo;

    public EnquiryServiceImpl(EnquiryRepo enquiryRepo, CounsellorRepo counsellorRepo, CourseRepo courseRepo) {
        this.enquiryRepo = enquiryRepo;
        this.counsellorRepo = counsellorRepo;
        this.courseRepo = courseRepo;
    }

    @Override
    public boolean addEnquiry(EnquiryDto enqDto, Integer counsellorId) {
        Enquiry enquiry = new Enquiry();
        BeanUtils.copyProperties(enqDto, enquiry);

        Course course = courseRepo.findById(enqDto.getCourseId()).get();
        Counsellor counsellor = counsellorRepo.findById(counsellorId).get();

        enquiry.setCourse(course);
        enquiry.setCounsellor(counsellor);

        try {
            enquiryRepo.save(enquiry);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public List<Enquiry> getAllEnquiries(Integer counsellorId) {
        return enquiryRepo.findByCounsellorCounsellorId(counsellorId);
    }

    @Override
    public List<Enquiry> getEnquiriesWithFilters(EnqFilterRequestDto reqDto, Integer counsellorId) {
        Enquiry enqEntity = new Enquiry();

        if (reqDto.getClassMode() != null)
            enqEntity.setClassMode(reqDto.getClassMode());

        if (reqDto.getEnqStatus() != null)
            enqEntity.setEnqStatus(reqDto.getEnqStatus());

        if (reqDto.getCourseId() != null) {
            Course course = courseRepo.findById(reqDto.getCourseId()).get();
            enqEntity.setCourse(course);
        }

        Counsellor counsellor = counsellorRepo.findById(counsellorId).get();
        enqEntity.setCounsellor(counsellor);

        return enquiryRepo.findAll(Example.of(enqEntity));
    }

    @Override
    public EnquiryDto editEnquiry(Integer enqId) {
        Enquiry enquiry = enquiryRepo.findById(enqId).get();
        EnquiryDto enquiryDto = new EnquiryDto();
        BeanUtils.copyProperties(enquiry, enquiryDto);
        enquiryDto.setCourseId(enquiry.getCourse().getCourseId());
        return enquiryDto;
    }

    @Override
    public boolean updateEnquiry(EnquiryDto enquiryDto) {
        Enquiry enquiry = enquiryRepo.findById(enquiryDto.getEnqId()).get();
        enquiry.setClassMode(enquiryDto.getClassMode());
        enquiry.setEnqStatus(enquiryDto.getEnqStatus());
        enquiry.setStuName(enquiryDto.getStuName());
        enquiry.setStuPhno(enquiryDto.getStuPhno());
        Course course = courseRepo.findById(enquiryDto.getCourseId()).get();
        enquiry.setCourse(course);
        enquiryRepo.save(enquiry);
        return true;
    }
}
