package com.pttk.linh.DAO;

import com.pttk.linh.model.Course;
import com.pttk.linh.model.Lesson;
import com.pttk.linh.model.Shift;
import com.pttk.linh.model.Skill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonDAO {

    private final ShiftDAO shiftDAO;
    private final SkillDAO skillDAO;

    DAO dao = new DAO();
    Connection con = dao.getConnection();

    public static final String SELECT_ALL_LESSON = "SELECT * FROM lesson";
    public static final String SELECT_LESSON_BY_NAME = "SELECT * FROM lesson WHERE name = ?";
    public static final String SELECT_LESSON_BY_ID = "SELECT * FROM lesson WHERE id = ?";
    public static final String SELECT_LESSON_BY_LOPHOC_ID = "SELECT * FROM lesson WHERE lophoc_id = ?";

    public List<Lesson> findAll() {
        List<Lesson> lessons = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_ALL_LESSON);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setName(rs.getString("name"));
                lesson.setDescription(rs.getString("description"));
                lesson.setDay(rs.getString("day"));
                Shift shift = this.shiftDAO.findById(rs.getInt("shift_id"));
                lesson.setShift(shift);
                Skill skill = this.skillDAO.findById(rs.getInt("skill_id"));
                lesson.setSkill(skill);
                lessons.add(lesson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lessons;
    }

    public List<Lesson> findByLophocId(long id) {
        List<Lesson> lessons = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_LESSON_BY_LOPHOC_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setName(rs.getString("name"));
                lesson.setDescription(rs.getString("description"));
                lesson.setDay(rs.getString("day"));
                Shift shift = this.shiftDAO.findById(rs.getInt("shift_id"));
                lesson.setShift(shift);
                Skill skill = this.skillDAO.findById(rs.getInt("skill_id"));
                lesson.setSkill(skill);
                lessons.add(lesson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lessons;
    }
}
