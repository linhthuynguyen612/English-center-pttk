//package com.pttk.linh.Controller;
//
//import com.pttk.linh.model.*;
//import com.pttk.linh.repository.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//
//@Controller
//@RequiredArgsConstructor
//public class LessonController {
//
//    private final MemberRepository memberRepository;
//    private final RoomRepository roomRepository;
//    private final SkillRepository skillRepository;
//    private final ShiftRepository shiftRepository;
//    private final LopHocRepository lopHocRepository;
//    private final LessonRepository lessonRepository;
//
//    @PostMapping("/lesson/class/{classid}/generate")
//    public List<Lesson> generateLesson(Model model, @PathVariable("classid") int classid){
//        List<Lesson> lessons = new ArrayList<>();
//
//        Lophoc lophoc = this.lopHocRepository.findById(classid);
//
//        Random random = new Random();
//        long roomId = random.nextInt(7);
//        Room room = this.roomRepository.findById(roomId);
//
//        long shiftId = random.nextInt(6);
//        Shift shift = this.shiftRepository.findById(shiftId);
//
//        for(int i = 0; i < 30; i++){
//            Lesson lesson = new Lesson();
//            lesson.setRoom(room);
//            lesson.setLophoc(lophoc);
//            lesson.setShift(shift);
//            long skillId = i % 4 +1;
//            Skill skill = this.skillRepository.findById(skillId);
//            lesson.setSkill(skill);
//            Date date = lophoc.getStartDate();
//            // 1 tuần học 2 buổi
//            date.setDate(date.getDate() + 3);
//            int year = date.getYear() + 1900;
//            int month = date.getMonth() + 1;
//            int day = date.getDate();
//            lesson.setDay(day + "-" + month + "-" + year);
//            lesson.setName("Lesson " + (i + 1));
//            lessons.add(lesson);
//        }
//
//        lessons = this.lessonRepository.saveAll(lessons);
//
//        return lessons;
//    }
//}
