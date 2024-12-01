package com.pttk.linh.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pttk.linh.model.Bill;
import com.pttk.linh.model.Member;
import com.pttk.linh.model.Registration;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillDAO {
    private final RegistrationDAO registrationDAO;
    private final MemberDAO memberDAO;
    private final LophocDAO lophocDAO;
    DAO dao = new DAO();
    Connection con = dao.getConnection();
    public static final String SELECT_BILL_BY_MEMBER_ID = "SELECT * FROM bill WHERE member_id = ?";
    public static final String INSERT_BILL = "INSERT INTO bill (amount, payment_date, payment_type, member_id) VALUES (?, ?, ?, ?)";

    public List<Bill> findByMemberId(Long memberId) {
        List<Bill> bills = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_BILL_BY_MEMBER_ID);
            ps.setLong(1, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setAmount(rs.getDouble("amount"));
                bill.setPaymentDate(rs.getDate("payment_date"));
                bill.setPaymentType(rs.getString("payment_type"));
                Member member = this.memberDAO.findById(rs.getInt("member_id"));
                bill.setMember(member);
                List<Registration> registrations = this.registrationDAO.findByBillId(rs.getInt("id"));
                bill.setRegistrations(registrations);
                bills.add(bill);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bills;
    }

    public long save(Bill bill) {
        try {
            PreparedStatement ps = con.prepareStatement(INSERT_BILL, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, bill.getAmount());
            ps.setDate(2, (java.sql.Date) bill.getPaymentDate());
            ps.setString(3, bill.getPaymentType());
            ps.setLong(4, bill.getMember().getId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                long generatedId = rs.getLong(1);
                bill.setId(generatedId);
                return generatedId;
            }
            throw new RuntimeException("Failed to get generated bill ID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
