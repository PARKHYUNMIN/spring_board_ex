package com.phm.spring_board_pjt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.phm.spring_board_pjt.dto.BDto;
import com.phm.spring_board_pjt.util.Constant;

public class BDao {
	// db에 접근하여 작업하는 클래스

	DataSource dataSource;
	JdbcTemplate template;

	public BDao() {
//		try {
//			Context context = new InitialContext();
//			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
		template = Constant.template;
	}
	
	public ArrayList<BDto> list() {
		
		String query = "SELECT bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent "
				+ "FROM mvc_board order by bGroup desc, bStep asc";
		
		return (ArrayList<BDto>) template.query(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
		
//		ArrayList<BDto> dtos = new ArrayList<BDto>();
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		String query = "SELECT bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent "
//				+ "FROM mvc_board order by bGroup desc, bStep asc";
//
//		try {
//			con = dataSource.getConnection();
//			pstmt = con.prepareStatement(query);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				int bId = rs.getInt("bId");
//				String bName = rs.getString("bName");
//				String bTitle = rs.getString("bTitle");
//				String bContent = rs.getString("bContent");
//				Timestamp bDate = rs.getTimestamp("bDate");
//				int bHit = rs.getInt("bHit");
//				int bGroup = rs.getInt("bGroup");
//				int bStep = rs.getInt("bStep");
//				int bIndent = rs.getInt("bIndent");
//
//				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
//				dtos.add(dto);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return dtos;
		
	}

	public void write(final String bName, final String bTitle, final String bContent) {
		
		
		template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String query = "INSERT INTO mvc_board(bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) "
						+ "VALUES(mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0)";
				
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1, bName);
				pstmt.setString(2, bTitle);
				pstmt.setString(3, bContent);
				
				return pstmt;
			}
		});
		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		String query = "INSERT INTO mvc_board(bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) "
//				+ "VALUES(mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0)";
//
//		try {
//			con = dataSource.getConnection();
//			pstmt = con.prepareStatement(query);
//			pstmt.setString(1, bName);
//			pstmt.setString(2, bTitle);
//			pstmt.setString(3, bContent);
//			int rn = pstmt.executeUpdate();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					pstmt.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
	}

	public BDto contentView(String strId) {

		upHit(strId);
		
		String query = "SELECT * FROM mvc_board WHERE bId=" + strId;
		return template.queryForObject(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
//		BDto dto = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		String query = "SELECT * FROM mvc_board WHERE bId=?";
//
//		try {
//			con = dataSource.getConnection();
//			pstmt = con.prepareStatement(query);
//			pstmt.setInt(1, Integer.parseInt(strId));
//			rs = pstmt.executeQuery();
//
//			if (rs.next()) {
//				int bId = rs.getInt("bId");
//				String bName = rs.getString("bName");
//				String bTitle = rs.getString("bTitle");
//				String bContent = rs.getString("bContent");
//				Timestamp bDate = rs.getTimestamp("bDate");
//				int bHit = rs.getInt("bHit");
//				int bGroup = rs.getInt("bGroup");
//				int bStep = rs.getInt("bStep");
//				int bIndent = rs.getInt("bIndent");
//				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return dto;
	}

	private void upHit(final String strId) {
		// bId를 통해 해당 게시글 bHit select
		// bHit값 1증가 후 업데이트
		
		String query = "UPDATE mvc_board set bHit = bHit + 1 WHERE bId = ?";
		
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, Integer.parseInt(strId));
			}
		});
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		String query = "UPDATE mvc_board set bHit = bHit + 1 WHERE bId = ?";
//
//		try {
//			con = dataSource.getConnection();
//			pstmt = con.prepareStatement(query);
//			pstmt.setString(1, strId);
//
//			int rn = pstmt.executeUpdate();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
	}

	public void modify(final String bId, final String bName, final String bTitle, final String bContent) {
		
		String query = "UPDATE mvc_board SET bName = ?, bTitle = ?, bContent = ? WHERE bId = ?";
		
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				// TODO Auto-generated method stub
				pstmt.setString(1, bName);
				pstmt.setString(2, bTitle);
				pstmt.setString(3, bContent);
				pstmt.setInt(4, Integer.parseInt(bId));
				
			}
		});
		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		String query = "UPDATE mvc_board SET bName = ?, bTitle = ?, bContent = ? WHERE bId = ?";
//
//		try {
//			con = dataSource.getConnection();
//			pstmt = con.prepareStatement(query);
//
//			pstmt.setString(1, bName);
//			pstmt.setString(2, bTitle);
//			pstmt.setString(3, bContent);
//			pstmt.setInt(4, Integer.parseInt(bId));
//
//			int rn = pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}

	}

	public void delete(final String bId) {
		String query = "DELETE FROM mvc_board WHERE bId = ?";
		
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, bId);
			}
		});
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		String query = "DELETE FROM mvc_board WHERE bId = ?";
//
//		try {
//			con = dataSource.getConnection();
//			pstmt = con.prepareStatement(query);
//			pstmt.setInt(1, Integer.parseInt(bId));
//			int rn = pstmt.executeUpdate();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
	}

	public void reply(final String bId, final String bName, final String bTitle, final String bContent, final String bGroup, final String bStep,
			final String bIndent) {
		
		replyShape(bGroup, bStep);
		
		String query = "INSERT INTO mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) "
				+ "values (mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
		
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, bName);
				pstmt.setString(2, bTitle);
				pstmt.setString(3, bContent);
				pstmt.setInt(4, Integer.parseInt(bGroup));
				pstmt.setInt(5, Integer.parseInt(bStep) + 1);
				pstmt.setInt(6, Integer.parseInt(bIndent) + 1);
				
			}
		});
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		
//		String query = "INSERT INTO mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) "
//				+ "values (mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
//		
//		try{
//			con = dataSource.getConnection();
//			pstmt = con.prepareStatement(query);
//			
//			pstmt.setString(1, bName);
//			pstmt.setString(2, bTitle);
//			pstmt.setString(3, bContent);
//			pstmt.setInt(4, Integer.parseInt(bGroup));
//			pstmt.setInt(5, Integer.parseInt(bStep) + 1);
//			pstmt.setInt(6, Integer.parseInt(bIndent) + 1);
//			
//			int rn = pstmt.executeUpdate();
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			try{
//				if(con != null) con.close();
//				if(pstmt != null) pstmt.close();
//			}catch(Exception e2){
//				e2.printStackTrace();
//			}
//		}
	}

	private void replyShape(final String bGroup, final String bStep) {
		
		String query = "UPDATE mvc_board SET bStep = bStep + 1 WHERE bGroup = ? AND bStep > ?";
		
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setInt(1, Integer.parseInt(bGroup));
				pstmt.setInt(2, Integer.parseInt(bStep));
			}
		});
		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		String query = "UPDATE mvc_board SET bStep = bStep + 1 WHERE bGroup = ? AND bStep > ?";
//		
//		try{
//			con = dataSource.getConnection();
//			pstmt = con.prepareStatement(query);
//			
//			pstmt.setInt(1, Integer.parseInt(bGroup));
//			pstmt.setInt(2, Integer.parseInt(bStep));
//			
//			int rn = pstmt.executeUpdate();
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			try{
//				if(con != null) con.close();
//				if(pstmt != null) pstmt.close();
//			}catch(Exception e2){
//				e2.printStackTrace();
//			}
//		}
		
		
	}

	public BDto reply_view(String strId) {
		String query = "SELECT * FROM mvc_board WHERE bId =" + strId;
		return template.queryForObject(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//			
//		String query = "SELECT * FROM mvc_board WHERE bId = ?";
//		
//		BDto dto = null;
//
//		try {
//			con = dataSource.getConnection();
//			pstmt = con.prepareStatement(query);
//			pstmt.setInt(1, Integer.parseInt(strId));
//			
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				int bId = rs.getInt("bId");
//				String bName = rs.getString("bName");
//				String bTitle = rs.getString("bTitle");
//				String bContent = rs.getString("bContent");
//				Timestamp bDate = rs.getTimestamp("bDate");
//				int bHit = rs.getInt("bHit");
//				int bGroup = rs.getInt("bGroup");
//				int bStep = rs.getInt("bStep");
//				int bIndent = rs.getInt("bIndent");
//				
//				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(rs != null) rs.close();
//				if(pstmt != null) pstmt.close();
//				if(con != null) con.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//
//		return dto;
	}

}
