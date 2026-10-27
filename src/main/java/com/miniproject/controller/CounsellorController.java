package com.miniproject.controller;

import com.miniproject.dto.DashboardResponseDto;
import com.miniproject.entity.Counsellor;
import com.miniproject.sevice.CounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CounsellorController {
    @Autowired
    private CounsellorService counsellorService;

    @GetMapping("/")
    public String loginForm(Model model) {
        model.addAttribute("counsellor", new Counsellor());
        return "login";
    }

    @PostMapping("/login")
    public String loginHandle(Counsellor counsellor, Model model, HttpServletRequest request) {
        Counsellor loggedInCounsellor = counsellorService.login(counsellor.getEmail(), counsellor.getPwd());
        if (loggedInCounsellor != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("cid", loggedInCounsellor.getCounsellorId());
            return "redirect:dashboard";
        }
        else {
            model.addAttribute("error", "Invalid Credentials");
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("counsellor",new Counsellor());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegistration(Counsellor counsellor, Model model) {
        boolean isSaved = counsellorService.saveCounsellor(counsellor);
        if(isSaved) {
            model.addAttribute("success", "Registration Successful");
        }
        else {
            model.addAttribute("error", "Registration Failed");
        }
        return "register";
    }

    @GetMapping(value = "/dashboard")
    public String buildDashboard(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Integer counsellorId = (Integer) session.getAttribute("cid");

        DashboardResponseDto dashboardInfo = counsellorService.getDashboardInfo(counsellorId);

        model.addAttribute("dashboardInfo", dashboardInfo);

        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/";
    }
}
