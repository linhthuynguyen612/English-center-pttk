package com.pttk.linh.DAO;

import com.pttk.linh.model.Lesson;
import com.pttk.linh.model.Level;
import com.pttk.linh.model.Lophoc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LophocDAO {

    private final LevelDAO levelDAO;
    private final LessonDAO lessonDAO;
    DAO dao = new DAO();
    Connection con = dao.getConnection();


    public static final String SELECT_LOPHOC_BY_LEVEL_ID = "SELECT * FROM lophoc WHERE level_id = ?";
    public static final String SELECT_LOPHOC_BY_ID = "SELECT * FROM lophoc WHERE id = ?";
    public static final String SELECT_LOPHOC_BY_NAME = "SELECT * FROM lophoc WHERE name = ?";

    public List<Lophoc> findByLevelId(long id) {
        List<Lophoc> lophocs = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement(SELECT_LOPHOC_BY_LEVEL_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Lophoc lophoc = new Lophoc();
                lophoc.setId(rs.getInt("id"));
                lophoc.setName(rs.getString("name"));
                lophoc.setCurrentStudent(rs.getInt("current_student"));
                lophoc.setDescription(rs.getString("description"));
                lophoc.setMaxStudent(rs.getInt("max_student"));
                lophoc.setStartDate(rs.getDate("start_date"));
                lophoc.setFee(rs.getDouble("fee"));
                Level level = this.levelDAO.findById(rs.getInt("level_id"));
                List<Lesson> lessons = this.lessonDAO.findByLophocId(lophoc.getId());
                lophoc.setLessons(lessons);
                lophoc.setLevel(level);
                lophocs.add(lophoc);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lophocs;
    }

    public Lophoc findById(long id) {
        try{
            PreparedStatement ps = con.prepareStatement(SELECT_LOPHOC_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Lophoc lophoc = new Lophoc();
                lophoc.setId(rs.getInt("id"));
                lophoc.setName(rs.getString("name"));
                lophoc.setCurrentStudent(rs.getInt("current_student"));
                lophoc.setDescription(rs.getString("description"));
                lophoc.setMaxStudent(rs.getInt("max_student"));
                lophoc.setStartDate(rs.getDate("start_date"));
                lophoc.setFee(rs.getDouble("fee"));
                Level level = this.levelDAO.findById(rs.getInt("level_id"));
                lophoc.setLevel(level);
                List<Lesson> lessons = this.lessonDAO.findByLophocId(lophoc.getId());
                lophoc.setLessons(lessons);
                return lophoc;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Lophoc findByName(String name) {
        try{
            PreparedStatement ps = con.prepareStatement(SELECT_LOPHOC_BY_NAME);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Lophoc lophoc = new Lophoc();
                lophoc.setId(rs.getInt("id"));
                lophoc.setName(rs.getString("name"));
                lophoc.setCurrentStudent(rs.getInt("current_student"));
                lophoc.setDescription(rs.getString("description"));
                lophoc.setMaxStudent(rs.getInt("max_student"));
                lophoc.setFee(rs.getDouble("fee"));
                lophoc.setStartDate(rs.getDate("start_date"));
                Level level = this.levelDAO.findById(rs.getInt("level_id"));
                lophoc.setLevel(level);
                List<Lesson> lessons = this.lessonDAO.findByLophocId(lophoc.getId());
                lophoc.setLessons(lessons);
                return lophoc;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
