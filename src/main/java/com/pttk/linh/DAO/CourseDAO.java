package com.pttk.linh.DAO;

import com.pttk.linh.model.Branch;
import com.pttk.linh.model.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseDAO {
    private final BranchDAO branchDAO;
    DAO dao = new DAO();
    Connection con = dao.getConnection();

    public static final String SELECT_ALL_COURSE = "SELECT * FROM course";
    public static final String SELECT_COURSE_BY_NAME = "SELECT * FROM course WHERE name = ?";
    public static final String SELECT_COURSE_BY_ID = "SELECT * FROM course WHERE id = ?";

    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement(SELECT_ALL_COURSE);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                int branhchId = rs.getInt("branch_id");
                Branch branch = new Branch();
                branch.setId(branhchId);
                course.setBranch(branch);
                courses.add(course);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return courses;
    }

    public Course findByName(String name) {
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_COURSE_BY_NAME);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                int branhchId = rs.getInt("branch_id");
                Branch branch = new Branch();
                branch.setId(branhchId);
                course.setBranch(branch);
//                List<Level> levels = levelDAO.findByCourseId(course.getId());
//                course.setLevels(levels);
                return course;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Course findById(long id){
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_COURSE_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                int branhchId = rs.getInt("branch_id");
                Branch branch = new Branch();
                branch.setId(branhchId);
                course.setBranch(branch);
//                List<Level> levels = levelDAO.findByCourseId(course.getId());
//                course.setLevels(levels);
                return course;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
