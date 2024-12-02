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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final CourseDAO courseDAO;
    private final LophocDAO lophocDAO;
    private final LevelDAO levelDAO;
    private final LessonDAO lessonDAO;
    private final RegistrationDAO registrationDAO;
    private final MemberDAO memberDAO;
    private final BillDAO billDAO;

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
    public ResponseEntity<?> addToCart(@RequestBody Map<String, List<String>> payload, HttpSession session) {
        Member member = (Member) session.getAttribute("currentMember");
        long memberId = member.getId();
        List<Registration> registrations = this.registrationDAO.findByMemberId(memberId);
        List<String> classes = payload.get("classes");
        List<Lophoc> lophocAddToCart = new ArrayList<>();
        for (String className : classes) {
            Lophoc lophoc = this.lophocDAO.findByName(className);
            lophocAddToCart.add(lophoc);
        }

        // Kiểm tra thời gian bắt đầu khóa học
        for (Lophoc lophoc : lophocAddToCart) {
            if (lophoc.getStartDate().getTime() < System.currentTimeMillis()) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Khóa học đã bắt đầu, không thể đăng ký");
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(response);
            }
        }

        // Kiểm tra trùng lịch học
        for (Registration registration : registrations) {
            for (Lophoc lophoc : lophocAddToCart) {
                for (Lesson lessonAddToCart : lophoc.getLessons()) {
                    for (Lesson registeredLesson : registration.getLophoc().getLessons()) {
                        if (lessonAddToCart.getDay().equals(registeredLesson.getDay()) &&
                                lessonAddToCart.getShift().getStartTime()
                                        .equals(registeredLesson.getShift().getStartTime())
                                &&
                                lessonAddToCart.getShift().getEndTime()
                                        .equals(registeredLesson.getShift().getEndTime())) {
                            Map<String, String> response = new HashMap<>();
                            response.put("error", "Trùng lịch học với khóa học đã đăng ký");
                            return ResponseEntity
                                    .status(HttpStatus.CONFLICT)
                                    .body(response);
                        }
                    }
                }
            }
        }

        session.setAttribute("class-add-to-cart", lophocAddToCart);
        return ResponseEntity.ok(lophocAddToCart);
    }

    @PostMapping("/register-now")
    public ResponseEntity<?> registerNow(@RequestBody Map<String, List<String>> payload,
            HttpSession session) {
        Member member = (Member) session.getAttribute("currentMember");
        long memberId = member.getId();
        List<Registration> registrations = this.registrationDAO.findByMemberId(memberId);
        List<String> classes = payload.get("classes");
        List<Lophoc> lophocAddToCart = new ArrayList<>();
        for (String className : classes) {
            Lophoc lophoc = this.lophocDAO.findByName(className);
            lophocAddToCart.add(lophoc);
        }

        // Kiểm tra thời gian bắt đầu khóa học
        for (Lophoc lophoc : lophocAddToCart) {
            if (lophoc.getStartDate().getTime() < System.currentTimeMillis()) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Khóa học đã bắt đầu, không thể đăng ký");
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(response);
            }
        }

        // Kiểm tra trùng lịch học
        for (Registration registration : registrations) {
            for (Lophoc lophoc : lophocAddToCart) {
                for (Lesson lessonAddToCart : lophoc.getLessons()) {
                    for (Lesson registeredLesson : registration.getLophoc().getLessons()) {
                        if (lessonAddToCart.getDay().equals(registeredLesson.getDay()) &&
                                lessonAddToCart.getShift().getStartTime()
                                        .equals(registeredLesson.getShift().getStartTime())
                                &&
                                lessonAddToCart.getShift().getEndTime()
                                        .equals(registeredLesson.getShift().getEndTime())) {
                            Map<String, String> response = new HashMap<>();
                            response.put("error", "Trùng lịch học với khóa học đã đăng ký");
                            return ResponseEntity
                                    .status(HttpStatus.CONFLICT)
                                    .body(response);
                        }
                    }
                }
            }
        }

        List<Registration> newRegistrations = new ArrayList<>();
        for (Lophoc lophoc : lophocAddToCart) {
            Registration registration = new Registration();
            registration.setMember(member);
            registration.setLophoc(lophoc);
            registration.setDate(new Date(System.currentTimeMillis()));
            registration.setTime(new Time(System.currentTimeMillis()));
            newRegistrations.add(registration);
        }
        session.setAttribute("confirm-registration", newRegistrations);
        return ResponseEntity.ok(newRegistrations);
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
    public ResponseEntity<List<Registration>> confirmRegistration(HttpSession session) {
        Member member = (Member) session.getAttribute("currentMember");
        List<Registration> registrationsToConfirm = (List<Registration>) session.getAttribute("confirm-registration");

        if (registrationsToConfirm == null || registrationsToConfirm.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            for (Registration registration : registrationsToConfirm) {
                registration.setMember(member);
                registration.setDate(new Date(System.currentTimeMillis()));
                registration.setTime(new Time(System.currentTimeMillis()));
                this.registrationDAO.InsertRegistration(registration);
            }

            // Clear the session after successful registration
            session.removeAttribute("confirm-registration");
            session.removeAttribute("class-add-to-cart");

            return ResponseEntity.ok(registrationsToConfirm);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/api/receptionist/search")
    public ResponseEntity<List<Map<String, Object>>> searchRegistration(@RequestParam String param) {
        List<Registration> registrations = this.registrationDAO.findByMemberNameOrPhone(param);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Registration registration : registrations) {
            Map<String, Object> item = new HashMap<>();
            item.put("registration", registration);
            item.put("remainingFee", this.registrationDAO.getRemainingFeeByRegistration(registration.getId()));
            result.add(item);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/detail-registrations")
    public ResponseEntity<?> saveSelectedRegistrations(@RequestBody List<Long> selectedIds, HttpSession session) {
        try {
            List<Registration> selectedRegistrations = new ArrayList<>();
            for (Long id : selectedIds) {
                Registration registration = registrationDAO.findById(id);
                if (registration != null) {
                    selectedRegistrations.add(registration);
                }
            }

            // Lưu vào session với tên không có dấu gạch ngang
            session.setAttribute("selectedRegistrations", selectedRegistrations);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/api/student-info/edit/{memberId}")
    public ResponseEntity<Member> updateMemberInfo(@PathVariable Long memberId,
            @RequestBody Map<String, String> formData,
            HttpSession session) {
        // Xử lý dữ liệu từ formData và cập nhật thông tin member
        Member member = this.memberDAO.findById(memberId);
        if (member != null) {
            member.setName(formData.get("name"));
            member.setPhone(formData.get("phone"));
            member.setAddress(formData.get("address"));
            member.setDob(Date.valueOf(formData.get("dob")));
            this.memberDAO.save(member);
            session.setAttribute("currentMember", member);
        }
        return ResponseEntity.ok(member);
    }

    @PostMapping("/api/confirm-payment")
    public ResponseEntity<Bill> doConfirmPayment(@RequestBody Map<String, Object> payload) {
        try {
            List<Integer> registrationIds = (List<Integer>) payload.get("registrationIds");
            String paymentMethod = (String) payload.get("paymentMethod");

            if (registrationIds == null || registrationIds.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // Tạo bill mới
            Bill bill = new Bill();
            List<Registration> registrations = new ArrayList<>();
            double total = 0;

            // Lấy registration đầu tiên để set member cho bill
            Registration firstRegistration = null;

            // Tính tổng tiền và lấy danh sách registrations
            for (Integer id : registrationIds) {
                Registration registration = this.registrationDAO.findById(Long.parseLong(id.toString()));
                if (registration == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }

                if (firstRegistration == null) {
                    firstRegistration = registration;
                }

                total += registration.getLophoc().getFee();
                registrations.add(registration);
            }

            // Set thông tin cho bill
            bill.setPaymentType(paymentMethod);
            bill.setPaymentDate(new Date(System.currentTimeMillis()));
            bill.setAmount(total);
            bill.setMember(firstRegistration.getMember());

            // Lưu bill và lấy ID
            long billId = this.billDAO.save(bill);

            // Kiểm tra xem bill đã được lưu thành công chưa
            if (billId <= 0) {
                throw new RuntimeException("Failed to save bill");
            }

            // Cập nhật bill_id cho các registration
            for (Registration registration : registrations) {
                boolean updated = this.registrationDAO.updateBillId(registration.getId(), billId);
                if (!updated) {
                    throw new RuntimeException("Failed to update registration with bill ID");
                }
            }

            // Set lại danh sách registrations cho bill để trả về
            bill.setRegistrations(registrations);

            return ResponseEntity.ok(bill);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error details: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
