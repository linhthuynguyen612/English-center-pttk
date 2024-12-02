package com.pttk.linh.DAO;

import com.pttk.linh.model.Lophoc;
import com.pttk.linh.model.Member;
import com.pttk.linh.model.Registration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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
    public static final String SELECT_ALL_REGISTRATION = "SELECT * FROM registration";
    public static final String SELECT_REGISTRATION_BY_BILL_ID = "SELECT * FROM registration WHERE bill_id = ?";
    public static final String UPDATE_REGISTRATION_BILL_ID = "UPDATE registration SET bill_id = ? WHERE id = ?";

    public List<Registration> findByMemberId(long memberId) {
        List<Registration> registrations = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_REGISTRATION_BY_MEMBER_ID);
            ps.setLong(1, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_REGISTRATION_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_REGISTRATION_BY_LOPHOC_ID_AND_MEMBER_ID);
            ps.setLong(1, lophocId);
            ps.setLong(2, memberId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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

    public List<Registration> findAll() {
        List<Registration> registrations = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_ALL_REGISTRATION);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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

    public static final String SELECT_UNPAID_REGISTRATIONS = "SELECT r.* FROM registration r LEFT JOIN bill b ON r.id = b.registration_id WHERE b.id IS NULL";

    public static final String SELECT_REMAINING_FEE_BY_REGISTRATION = "SELECT r.id, " +
            "CASE WHEN r.bill_id IS NOT NULL THEN 0 ELSE l.fee END as remaining_fee " +
            "FROM registration r " +
            "INNER JOIN lophoc l ON r.lophoc_id = l.id " +
            "WHERE r.id = ?";

    public double getRemainingFeeByRegistration(long registrationId) {
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_REMAINING_FEE_BY_REGISTRATION);
            ps.setLong(1, registrationId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("remaining_fee");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0.0;
    }

    public static final String SELECT_REGISTRATIONS_BY_MEMBER_NAME_OR_PHONE = "SELECT r.* FROM registration r " +
            "INNER JOIN member m ON r.member_id = m.id " +
            "WHERE m.name LIKE ? OR m.phone LIKE ?";

    public List<Registration> findByMemberNameOrPhone(String keyword) {
        List<Registration> registrations = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_REGISTRATIONS_BY_MEMBER_NAME_OR_PHONE);
            String searchTerm = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, searchTerm);
            ps.setString(2, searchTerm);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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

    public List<Registration> findByBillId(long billId) {
        List<Registration> registrations = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_REGISTRATION_BY_BILL_ID);
            ps.setLong(1, billId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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

    public boolean updateBillId(long registrationId, long billId) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_REGISTRATION_BILL_ID)) {
            ps.setLong(1, billId);
            ps.setLong(2, registrationId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update registration with bill ID: " + e.getMessage());
        }
    }
}
