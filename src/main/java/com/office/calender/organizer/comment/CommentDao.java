package com.office.calender.organizer.comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.office.calender.member.MemberDto;
import com.office.calender.organizer.OrganizerDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class CommentDao {

	final private JdbcTemplate jdbcTemplate;
	
	public CommentDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		
	}

	public int insertComment(Map<String, String> msgMap) {
		log.info("insertComment()");
		
		String sql = "INSERT INTO TBL_COMMENT(P_ORI_NO, M_ID, C_TXT) VALUES(?, ?, ?)";
		
		int result = -1;
		try {
			
			result = jdbcTemplate.update(sql, 
											msgMap.get("p_ori_no"),
											msgMap.get("m_id"),
											msgMap.get("comment"));
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return result;
		
	}

	public List<CommentDto> getComments(Map<String, String> msgMap) {
		log.info("getComments()");
		
		String sql = "SELECT * FROM TBL_COMMENT WHERE P_ORI_NO = ? ORDER BY C_NO DESC";
		
		List<CommentDto> commentDtos = new ArrayList<>();
		try {
			commentDtos = jdbcTemplate.query(sql, new RowMapper<CommentDto>() {

				@Override
				public CommentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					CommentDto commentDto = new CommentDto();
					
					commentDto.setC_no(rs.getInt("C_NO"));
					
					OrganizerDto organizerDto = new OrganizerDto();
					organizerDto.setP_ori_no(rs.getInt("P_ORI_NO"));
					commentDto.setOrganizerDto(organizerDto);
					
					MemberDto memberDto = new MemberDto();
					memberDto.setM_id(rs.getString("M_ID"));
					commentDto.setMemberDto(memberDto);
					
					commentDto.setC_txt(rs.getString("C_TXT"));
					commentDto.setC_reg_date(rs.getString("C_REG_DATE"));
					commentDto.setC_mod_date(rs.getString("C_MOD_DATE"));
					
					return commentDto;
					
				}
				
			}, msgMap.get("p_ori_no"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return commentDtos;
		
	}
	
}
