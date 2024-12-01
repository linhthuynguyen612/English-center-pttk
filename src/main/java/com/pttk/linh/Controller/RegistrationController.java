package com.pttk.linh.Controller;

import com.pttk.linh.DAO.BillDAO;
import com.pttk.linh.DAO.CourseDAO;
import com.pttk.linh.DAO.MemberDAO;
import com.pttk.linh.DAO.RegistrationDAO;
import com.pttk.linh.model.Bill;
import com.pttk.linh.model.Course;
import com.pttk.linh.model.Member;
import com.pttk.linh.model.Registration;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationDAO registrationDAO;
    private final CourseDAO courseDAO;
    private final BillDAO billDAO;
    private final MemberDAO memberDAO;

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

        List<Registration> registrations = (List<Registration>) session.getAttribute("confirm-registration");
        // List<Course> courses = this.courseDAO.findAll();
        double total = 0;
        for (Registration registration : registrations) {
            total += registration.getLophoc().getFee();
        }
        model.addAttribute("registrations", registrations);
        // model.addAttribute("courses", courses);
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("total", total);
        return "confirmRegistration";
    }

    @GetMapping("/receptionist/search")
    public String SearchRegistration(Model model, HttpSession session) {
        Member currentMember = (Member) session.getAttribute("currentMember");
        if (currentMember == null) {
            return "redirect:/login";
        }
        List<Member> members = this.memberDAO.findAll();
        model.addAttribute("members", members);
        model.addAttribute("currentMember", currentMember);
        List<Registration> registrations = this.registrationDAO.findAll();
        model.addAttribute("registrations", registrations);
        Map<Registration, Double> remainingFees = new HashMap<>();
        for (Registration registration : registrations) {
            double remainingFee = this.registrationDAO.getRemainingFeeByRegistration(registration.getId());
            remainingFees.put(registration, remainingFee);
        }
        model.addAttribute("remainingFees", remainingFees);

        return "searchRegistration";
    }

    @GetMapping("/receptionist/detail-registration")
    public String getMethodName(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("currentMember");
        if (member == null) {
            return "redirect:/login";
        }
        model.addAttribute("member", member);
        List<Registration> selectedRegistrations = (List<Registration>) session.getAttribute("selectedRegistrations");
        model.addAttribute("selectedRegistrations", selectedRegistrations);
        List<Bill> bills = this.billDAO.findByMemberId(member.getId());
        model.addAttribute("bills", bills);
        return "detailRegistration";
    }

}
