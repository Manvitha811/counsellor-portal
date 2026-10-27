package com.miniproject.controller;


import com.miniproject.dto.EnqFilterRequestDto;
import com.miniproject.dto.EnquiryDto;
import com.miniproject.entity.Enquiry;
import com.miniproject.sevice.CourseService;
import com.miniproject.sevice.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
public class EnquiryContoller {
    @Autowired
    private EnquiryService enquiryService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/enquiry")
    public String returnEnqForm(Model model) {
        model.addAttribute("enquiryDto", new EnquiryDto());
        model.addAttribute("courses", courseService.getCourses());
        return "add-enq";
    }

    @PostMapping("/enquiry")
    public String addEnquiry(EnquiryDto enqDto, Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("cid");

        boolean isSaved = enquiryService.addEnquiry(enqDto, counsellorId);

        if(isSaved) {
            model.addAttribute("success", "Enquiry Added");
        }
        else {
            model.addAttribute("error", "Failed to add enquiry");
        }
        model.addAttribute("enquiryDto", new EnquiryDto());
        model.addAttribute("courses", courseService.getCourses());
        return "add-enq";
    }

    @GetMapping("/view-enquiries")
    public String viewEnquiries(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("cid");

        List<Enquiry> allEnquiries = enquiryService.getAllEnquiries(counsellorId);
        model.addAttribute("enqs", allEnquiries);
        model.addAttribute("filter", new EnqFilterRequestDto());
        model.addAttribute("courses", courseService.getCourses());

        return "view-enqs";
    }

    @PostMapping("/filter-enqs")
    public String filterEnquiries(EnqFilterRequestDto filter, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("cid");

        List<Enquiry> allEnquiries = enquiryService.getEnquiriesWithFilters(filter,counsellorId);
        model.addAttribute("enqs", allEnquiries);
        model.addAttribute("filter", new EnqFilterRequestDto());
        model.addAttribute("courses", courseService.getCourses());

        return "view-enqs";
    }

    @GetMapping("/edit-enq")
    public String editEnquiry(@RequestParam Integer enqId, Model model) {
        EnquiryDto enquiryDto = enquiryService.editEnquiry(enqId);
        model.addAttribute("enquiryDto", enquiryDto);
        model.addAttribute("courses", courseService.getCourses());
        return "edit-enq";
    }
}
