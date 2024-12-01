package com.pttk.linh.DAO;

import com.pttk.linh.model.Shift;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Service
public class ShiftDAO {

    DAO dao = new DAO();
    Connection con = dao.getConnection();

    public static final String SELECT_SHIFT_BY_ID = "SELECT * FROM shift WHERE id = ?";

    public Shift findById(long id) {
        Shift shift = new Shift();
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_SHIFT_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                shift.setId(rs.getInt("id"));
                shift.setStartTime(rs.getTime("start_time"));
                shift.setEndTime(rs.getTime("end_time"));
                shift.setName(rs.getString("name"));
                return shift;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
