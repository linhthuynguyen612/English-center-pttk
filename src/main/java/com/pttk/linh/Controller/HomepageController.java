package com.pttk.linh.Controller;

import com.pttk.linh.DAO.MemberDAO;
import com.pttk.linh.DAO.RegistrationDAO;
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
public class HomepageController {
    private final MemberDAO memberDAO;
    private final RegistrationDAO registrationDAO;
//    private final

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        Member currentMember = (Member) session.getAttribute("currentMember");
        if (currentMember == null) {
            return "redirect:/login";
        }
        List<Registration> registrations = this.registrationDAO.findByMemberId(currentMember.getId());
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("registrations", registrations);
        return "client/homepage/show";
    }

    @GetMapping("/receptionist")
    public String receptionist(Model model) {
        return "client/receptionist/show";
    }
}
