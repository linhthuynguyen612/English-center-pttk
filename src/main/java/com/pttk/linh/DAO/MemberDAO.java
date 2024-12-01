package com.pttk.linh.DAO;

import com.pttk.linh.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
// @RequiredArgsConstructor
public class MemberDAO {
    public MemberDAO() {

    }

    private DAO dao = new DAO();
    Connection con = dao.getConnection();
    public static final String searchMemberByUsernameAndPassword = "SELECT * FROM member WHERE username = ? AND password = ?";
    public static final String searchMemberById = "SELECT * FROM member WHERE id = ?";
    public static final String updateMember = "UPDATE member SET name = ?, address = ?, phone = ?, dob = ?, description = ? WHERE id = ?";
    public static final String searchAllMember = "SELECT * FROM member";

    public Member findByUsernameAndPassword(String username, String password) {
        try {
            PreparedStatement ps = con.prepareStatement(searchMemberByUsernameAndPassword);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setUsername(rs.getString("username"));
                member.setPassword(rs.getString("password"));
                member.setRole(rs.getString("role"));
                member.setAddress(rs.getString("address"));
                member.setDescription(rs.getString("description"));
                member.setDob(rs.getDate("dob"));
                member.setName(rs.getString("name"));
                member.setPhone(rs.getString("phone"));
                return member;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public Member findById(long id) {
        try {
            PreparedStatement ps = con.prepareStatement(searchMemberById);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setUsername(rs.getString("username"));
                member.setPassword(rs.getString("password"));
                member.setRole(rs.getString("role"));
                member.setAddress(rs.getString("address"));
                member.setDescription(rs.getString("description"));
                member.setDob(rs.getDate("dob"));
                member.setName(rs.getString("name"));
                member.setPhone(rs.getString("phone"));
                return member;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public boolean save(Member member) {
        try {
            PreparedStatement ps = con.prepareStatement(updateMember);
            ps.setString(1, member.getName());
            ps.setString(2, member.getAddress());
            ps.setString(3, member.getPhone());
            // ps.setDate(4, () member.getDob());
            ps.setDate(4, new java.sql.Date(member.getDob().getTime()));
            ps.setString(5, member.getDescription());
            ps.setLong(6, member.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(searchAllMember);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setUsername(rs.getString("username"));
                member.setPassword(rs.getString("password"));
                member.setRole(rs.getString("role"));
                member.setAddress(rs.getString("address"));
                member.setDescription(rs.getString("description"));
                members.add(member);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return members;
    }

}
