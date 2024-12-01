package com.pttk.linh.Controller;

import com.pttk.linh.DAO.MemberDAO;
import com.pttk.linh.model.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class InfoController {

    private final MemberDAO memberDAO;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/student/info")
    public String studentInfo(Model model, HttpSession session) {
        Member currentMember = (Member) session.getAttribute("currentMember");
        if (currentMember == null) {
            return "redirect:/login";
        }
        model.addAttribute("currentMember", currentMember);

        return "client/info/ManageStudentInfo";
    }

    @GetMapping("/student/info/edit")
    public String editStudentInfo(Model model, HttpSession session) {
        Member currentMember = (Member) session.getAttribute("currentMember");
        if (currentMember == null) {
            return "redirect:/login";
        }
        model.addAttribute("currentMember", currentMember);

        return "client/info/UpdateInfo";
    }

    @PostMapping("/student/info/edit")
    public String updateStudentInfo(Model model, @ModelAttribute("currentMember") Member member, HttpSession session){
        Member memberLogin = (Member) session.getAttribute("currentMember");
        model.addAttribute("currentMember", memberLogin);
        if (memberLogin == null) {
            return "redirect:/login";
        }

        Member currentMember = this.memberDAO.findById(member.getId());
        if(currentMember != null){
            currentMember.setName(member.getName());
            currentMember.setAddress(member.getAddress());
            currentMember.setPhone(member.getPhone());
            currentMember.setDob(member.getDob());
            currentMember.setDescription(member.getDescription());
            this.memberDAO.save(currentMember);
            session.setAttribute("currentMember", currentMember);
        }
        return "redirect:/student/info";
    }
}

