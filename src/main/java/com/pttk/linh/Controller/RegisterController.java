package com.pttk.linh.Controller;

import com.pttk.linh.DAO.*;
import com.pttk.linh.model.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final CourseDAO courseDAO;
    private final LophocDAO lophocDAO;
    private final LevelDAO levelDAO;
    private final LessonDAO lessonDAO;
    private final RegistrationDAO registrationDAO;

    @GetMapping("/api/course/{name}/level")
    public ResponseEntity<List<Level>> getListLevel(@PathVariable String name) {
        Course course = this.courseDAO.findByName(name);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Level> levels = this.levelDAO.findByCourseId(course.getId());
        return ResponseEntity.ok(levels);
    }

    @GetMapping("/api/course/{name}/{level}/class")
    public ResponseEntity<List<Lophoc>> getListClass(@PathVariable String name, @PathVariable String level) {
        Course course = this.courseDAO.findByName(name);
        Level level1 = this.levelDAO.findByName(level);
        List<Lophoc> lophocs = this.lophocDAO.findByLevelId(level1.getId());
        return ResponseEntity.ok(lophocs);
    }

    @GetMapping("/api/lesson/cal/{className}")
    public ResponseEntity<List<Lesson>> getListLesson(@PathVariable String className) {
        Lophoc lophoc = this.lophocDAO.findByName(className);
        List<Lesson> lessons = this.lessonDAO.findByLophocId(lophoc.getId());
        return ResponseEntity.ok(lessons);
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<List<Lophoc>> addToCart(@RequestBody Map<String, List<String>> payload, HttpSession session) {
        Member member = (Member) session.getAttribute("currentMember");
        long memberId = member.getId();
        List<Registration> registrations = this.registrationDAO.findByMemberId(memberId);
        List<String> classes = payload.get("classes");
        List<Lophoc> lophocAddToCart = new ArrayList<>();
        for(String className : classes) {
            Lophoc lophoc = this.lophocDAO.findByName(className);
            lophocAddToCart.add(lophoc);
        }

        // check xem lịch bắt đầu học của lophocAddToCart đã bắt đầu trước hiện tại chưa
        for (Lophoc lophoc : lophocAddToCart) {
            if (lophoc.getStartDate().getTime() < System.currentTimeMillis()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        }
        for (Registration registration : registrations) {
            for (Lophoc lophoc : lophocAddToCart) {
                for (Lesson lessonAddToCart : lophoc.getLessons()) {
                    for (Lesson registeredLesson : registration.getLophoc().getLessons()) {
                        if (lessonAddToCart.getDay().equals(registeredLesson.getDay()) &&
                                lessonAddToCart.getShift().getStartTime().equals(registeredLesson.getShift().getStartTime()) &&
                                lessonAddToCart.getShift().getEndTime().equals(registeredLesson.getShift().getEndTime())) {
                            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
                        }
                    }
                }
            }
        }


        session.setAttribute("class-add-to-cart", lophocAddToCart);

        // Xử lý logic
        return ResponseEntity.ok(lophocAddToCart);
    }

    @PostMapping("/register-now")
    public ResponseEntity<List<Registration>> registerNow(@RequestBody Map<String, List<String>> payload, HttpSession session) {
        Member member = (Member) session.getAttribute("currentMember");
        long memberId = member.getId();
        List<Registration> registrations = this.registrationDAO.findByMemberId(memberId);
        List<String> classes = payload.get("classes");
        List<Lophoc> lophocAddToCart = new ArrayList<>();
        for(String className : classes) {
            Lophoc lophoc = this.lophocDAO.findByName(className);
            lophocAddToCart.add(lophoc);
        }

        // check xem lịch bắt đầu học của lophocAddToCart đã bắt đầu trước hiện tại chưa
        for (Lophoc lophoc : lophocAddToCart) {
            if (lophoc.getStartDate().getTime() < System.currentTimeMillis()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        }
        for (Registration registration : registrations) {
            for (Lophoc lophoc : lophocAddToCart) {
                for (Lesson lessonAddToCart : lophoc.getLessons()) {
                    for (Lesson registeredLesson : registration.getLophoc().getLessons()) {
                        if (lessonAddToCart.getDay().equals(registeredLesson.getDay()) &&
                                lessonAddToCart.getShift().getStartTime().equals(registeredLesson.getShift().getStartTime()) &&
                                lessonAddToCart.getShift().getEndTime().equals(registeredLesson.getShift().getEndTime())) {
                            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
                        }
                    }
                }
            }
        }

        List<Registration> newRegistrations = new ArrayList<>();
        for(Lophoc lophoc : lophocAddToCart) {
            Registration registration = new Registration();
            registration.setMember(member);
            registration.setLophoc(lophoc);
//            registration.setId();
            registration.setDate(new Date(System.currentTimeMillis()));
            registration.setTime(new Time(System.currentTimeMillis()));
            newRegistrations.add(registration);
//            this.registrationDAO.InsertRegistration(registration);

        }
        session.setAttribute("confirm-registration", newRegistrations);
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/api/class-add-to-cart")
    public ResponseEntity<List<Lophoc>> getClassAddToCart(HttpSession session) {
        List<Lophoc> lophocAddToCart = (List<Lophoc>) session.getAttribute("class-add-to-cart");
        if (lophocAddToCart == null) {
            lophocAddToCart = new ArrayList<>();
        }
        return ResponseEntity.ok(lophocAddToCart);
    }

    @DeleteMapping("/api/registration/delete/{index}")
    public ResponseEntity<List<Registration>> deleteRegistration(@PathVariable long index, HttpSession session) {
        Member member = (Member) session.getAttribute("currentMember");
        List<Registration> registrations = (List<Registration>) session.getAttribute("confirm-registration");
        registrations.remove((int) index - 1);
        session.setAttribute("confirm-registration", registrations);
        return ResponseEntity.ok(registrations);
    }

    @PostMapping("/api/registration")
    public ResponseEntity<List<Registration>> confirmRegistration(@RequestBody List<Registration> registrations) {
        for (Registration registration : registrations) {
            this.registrationDAO.InsertRegistration(registration);
        }
        return ResponseEntity.ok(registrations);
    }
}
