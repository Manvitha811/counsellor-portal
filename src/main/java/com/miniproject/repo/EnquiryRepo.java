package com.miniproject.repo;

import com.miniproject.entity.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnquiryRepo extends JpaRepository<Enquiry, Integer> {
    public List<Enquiry> findByCounsellorCounsellorId(Integer counsellorId);

}
