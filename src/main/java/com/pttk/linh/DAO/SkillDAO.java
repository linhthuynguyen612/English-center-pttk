package com.pttk.linh.DAO;

import com.pttk.linh.model.Skill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Service
public class SkillDAO {

    DAO dao = new DAO();
    Connection con = dao.getConnection();

    public static final String SELECT_SKILL_BY_ID = "SELECT * FROM skill WHERE id = ?";

    public Skill findById(long id) {
        Skill skill = new Skill();
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_SKILL_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                skill.setId(rs.getInt("id"));
                skill.setName(rs.getString("name"));
                return skill;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
