package com.pttk.linh.DAO;

import com.pttk.linh.model.Lophoc;
import com.pttk.linh.model.Member;
import com.pttk.linh.model.Registration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationDAO {
    DAO dao = new DAO();
    Connection con = dao.getConnection();
    private final LophocDAO lophocDAO;
    private final MemberDAO memberDAO;

    public static final String SELECT_REGISTRATION_BY_MEMBER_ID = "SELECT * FROM registration WHERE member_id = ?";
    public static final String INSERT_REGISTRATION = "INSERT INTO registration (date, time, member_id, lophoc_id) VALUES (?, ?, ?, ?)";
    public static final String SELECT_REGISTRATION_BY_ID = "SELECT * FROM registration WHERE id = ?";
    public static final String SELECT_REGISTRATION_BY_LOPHOC_ID_AND_MEMBER_ID = "SELECT * FROM registration WHERE lophoc_id = ? AND member_id = ?";

    public List<Registration> findByMemberId(long memberId) {
        List<Registration> registrations = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement(SELECT_REGISTRATION_BY_MEMBER_ID);
            ps.setLong(1, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Registration registration = new Registration();
                registration.setId(rs.getInt("id"));
                Member member = this.memberDAO.findById(rs.getInt("member_id"));
                registration.setMember(member);
                Lophoc lophoc = this.lophocDAO.findById(rs.getInt("lophoc_id"));
                registration.setLophoc(lophoc);
                registration.setDate(rs.getDate("date"));
                registrations.add(registration);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return registrations;

    }

    public boolean InsertRegistration(Registration registration) {
        try {
            PreparedStatement ps = con.prepareStatement(INSERT_REGISTRATION);
            ps.setDate(1, (Date) registration.getDate());
            ps.setTime(2, registration.getTime());
            ps.setLong(3, registration.getMember().getId());
            ps.setLong(4, registration.getLophoc().getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Registration findById(long id) {
        try{
            PreparedStatement ps = con.prepareStatement(SELECT_REGISTRATION_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Registration registration = new Registration();
                registration.setId(rs.getInt("id"));
                Member member = this.memberDAO.findById(rs.getInt("member_id"));
                registration.setMember(member);
                Lophoc lophoc = this.lophocDAO.findById(rs.getInt("lophoc_id"));
                registration.setLophoc(lophoc);
                registration.setDate(rs.getDate("date"));
                return registration;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Registration findByMemberIDAndLophocID(long memberId, long lophocId) {
        try{
            PreparedStatement ps = con.prepareStatement(SELECT_REGISTRATION_BY_LOPHOC_ID_AND_MEMBER_ID);
            ps.setLong(1, lophocId);
            ps.setLong(2, memberId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Registration registration = new Registration();
                registration.setId(rs.getInt("id"));
                Member member = this.memberDAO.findById(rs.getInt("member_id"));
                registration.setMember(member);
                Lophoc lophoc = this.lophocDAO.findById(rs.getInt("lophoc_id"));
                registration.setLophoc(lophoc);
                registration.setDate(rs.getDate("date"));
                return registration;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}