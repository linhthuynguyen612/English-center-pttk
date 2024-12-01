package com.pttk.linh.Controller;

import com.pttk.linh.DAO.MemberDAO;
import com.pttk.linh.model.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberDAO memberDAO;

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        if(session.getAttribute("currentMember") != null) {
            return "redirect:/";
        }
        model.addAttribute("member", new Member());
        return "client/auth/login";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute Member member, HttpSession session) {
        Member currentMember = memberDAO.findByUsernameAndPassword(member.getUsername(), member.getPassword());
        if(currentMember != null) {
            if (currentMember.getRole().equals("receptionist")) {
                session.setAttribute("currentMember", currentMember);
                return "redirect:/receptionist";
            } else if (currentMember.getRole().equals("student")) {
                session.setAttribute("currentMember", currentMember);
                return "redirect:/";
            }
        }
        model.addAttribute("member", member);
        model.addAttribute("error", "Tài khoản và mật khẩu chưa đúng");
        return "client/auth/login";
    }
}