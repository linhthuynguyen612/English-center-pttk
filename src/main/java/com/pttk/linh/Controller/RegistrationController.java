package com.pttk.linh.Controller;

import com.pttk.linh.DAO.CourseDAO;
import com.pttk.linh.DAO.RegistrationDAO;
import com.pttk.linh.model.Course;
import com.pttk.linh.model.Member;
import com.pttk.linh.model.Registration;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationDAO registrationDAO;
    private final CourseDAO courseDAO;


    @GetMapping("/student/registration")
    public String registration(Model model, HttpSession session) {
        Member currentMember = (Member) session.getAttribute("currentMember");
        if (currentMember == null) {
            return "redirect:/login";
        }

        List<Registration> registrations = this.registrationDAO.findByMemberId(currentMember.getId());
        List<Course> courses = this.courseDAO.findAll();

        model.addAttribute("registrations", registrations);
        model.addAttribute("courses", courses);
        model.addAttribute("currentMember", currentMember);
        return "client/Course/registrationstudy";
    }

    @GetMapping("/student/confirm-registration")
    public String confirmRegistration(Model model, HttpSession session) {
        Member currentMember = (Member) session.getAttribute("currentMember");
        if (currentMember == null) {
            return "redirect:/login";
        }

        List<Registration> registrations =(List<Registration>) session.getAttribute("confirm-registration");
//        List<Course> courses = this.courseDAO.findAll();

        model.addAttribute("registrations", registrations);
//        model.addAttribute("courses", courses);
        model.addAttribute("currentMember", currentMember);
        return "confirmRegistration";
    }
}
