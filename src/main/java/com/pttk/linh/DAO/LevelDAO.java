package com.pttk.linh.DAO;

import com.pttk.linh.model.Course;
import com.pttk.linh.model.Level;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LevelDAO {
    private final CourseDAO courseDAO;
    DAO dao = new DAO();
    Connection con = dao.getConnection();
    public static final String SELECT_LEVEL_BY_NAME = "SELECT * FROM level WHERE name = ?";
    public static final String SELECT_LEVEL_BY_COURSE_ID = "SELECT * FROM level WHERE course_id = ?";
    public static final String SELECT_LEVEL_BY_ID = "SELECT * FROM level WHERE id = ?";

    public Level findByName(String name) {
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_LEVEL_BY_NAME);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Level level = new Level();
                level.setId(rs.getInt("id"));
                level.setName(rs.getString("name"));
                Course course = this.courseDAO.findById(rs.getInt("course_id"));
//                List<Lophoc> lophocs = lophocDAO.findByLevelId(level.getId());
//                level.setLophocs(lophocs);
                level.setCourse(course);
                return level;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Level> findByCourseId(long id) {
        List<Level> levels = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement(SELECT_LEVEL_BY_COURSE_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Level level = new Level();
                level.setId(rs.getInt("id"));
                level.setName(rs.getString("name"));
                Course course = this.courseDAO.findById(rs.getInt("course_id"));
//                List<Lophoc> lophocs = lophocDAO.findByLevelId(level.getId());
//                level.setLophocs(lophocs);
                level.setCourse(course);
                levels.add(level);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return levels;
    }

    public Level findById(long id) {
        try{
            PreparedStatement ps = con.prepareStatement(SELECT_LEVEL_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Level level = new Level();
                level.setId(rs.getInt("id"));
                level.setName(rs.getString("name"));
                Course course = this.courseDAO.findById(rs.getInt("course_id"));
                level.setCourse(course);
                return level;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
