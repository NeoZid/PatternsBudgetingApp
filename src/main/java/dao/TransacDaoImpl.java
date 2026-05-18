package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import database.DBConnection;

import model.Transactions;

public class TransacDaoImpl implements TransactionDAO{

	@Override
	public Optional<Transactions> getTransactionById(int id) {
		String sql = "SELECT * FROM transacs WHERE id = ?";
		try (Connection conn = DBConnection.getInstance().connect()){
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				Transactions t = new Transactions(
                        rs.getInt("id"),
						rs.getDouble("amount"),
						rs.getString("date"),
						rs.getString("description"),
						rs.getString("type"),
						rs.getInt("user_id"),
						rs.getInt("category_id")
						);
				return Optional.of(t);
						
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return Optional.empty();
	}

	@Override
	public List<Transactions> getTransactionByUser(int userId) {
		List<Transactions> list = new ArrayList<>();
        String sql = "SELECT * FROM transacs WHERE user_id=?";

        try(Connection conn = DBConnection.getInstance().connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,userId);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                list.add(new Transactions(
                        rs.getInt("id"),
						rs.getDouble("amount"),
						rs.getString("date"),
						rs.getString("description"),
						rs.getString("type"),
						rs.getInt("user_id"),
						rs.getInt("category_id")
						));
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return list;
	}

	@Override
	public void saveTransaction(Transactions t) {
		String sql = "INSERT INTO transacs (amount, date, description, type, user_id, category_id) VALUES (?,?,?,?,?,?)";
		
		try (Connection conn = DBConnection.getInstance().connect()){
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setDouble(1, t.getAmount());
			pstmt.setString(2, t.getDate());
			pstmt.setString(3, t.getDescription());
			pstmt.setString(4, t.getType());
			pstmt.setInt(5, t.getUserId());
			
			if(t.getCategoryId() != null) {
	            pstmt.setInt(6, t.getCategoryId());
			} else {
	            pstmt.setNull(6, java.sql.Types.INTEGER);
			}
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void updateTransaction(Transactions t) {
		String sql = "UPDATE transacs SET amount=?, date =?,description=?, type=?, category_id=? WHERE id=?";
		
		try (Connection conn = DBConnection.getInstance().connect()){
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setDouble(1, t.getAmount());
			pstmt.setString(2, t.getDate());
            pstmt.setString(3, t.getDescription());
            pstmt.setString(4, t.getType());
			if(t.getCategoryId() != null)
	            pstmt.setInt(5, t.getCategoryId());
	        else
	            pstmt.setNull(5, java.sql.Types.INTEGER);
			pstmt.setInt(6, t.getTransacId());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void deleteTransaction(int id) {
		String sql = "DELETE FROM transacs WHERE id=?";
		
		try (Connection conn = DBConnection.getInstance().connect()){
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
