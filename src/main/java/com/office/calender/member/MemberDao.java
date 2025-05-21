package com.office.calender.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class MemberDao {

	final private JdbcTemplate jdbcTemplate;
	final private PasswordEncoder passwordEncoder;
	
	public MemberDao(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
		this.jdbcTemplate = jdbcTemplate;
		this.passwordEncoder = passwordEncoder;
		
	}

	public boolean isMember(String m_id) {
		log.info("isMember()");
		
		String sql = "SELECT COUNT(*) FROM TBL_MEMBER WHERE M_ID = ?";
		
		boolean isMember = false;
		
		try {
			
			int result = jdbcTemplate.queryForObject(sql, Integer.class, m_id);
			if (result > 0) isMember = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return isMember;
		
	}

	public int insertMember(MemberDto memberDto) {
		log.info("insertMember()");
		
		String sql =  "INSERT INTO TBL_MEMBER(M_ID, M_PW, M_MAIL, M_PHONE) "
					+ "VALUES(?, ?, ?, ?)";
		
		int result = MemberService.DATABASE_COMMUNICATION_TROUBLE;
		
		try {
			
			result = jdbcTemplate.update(sql, 
											memberDto.getM_id(), 
											passwordEncoder.encode(memberDto.getM_pw()),
											memberDto.getM_mail(),
											memberDto.getM_phone());
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return result;
		
	}

	public MemberDto selectMemberForLogin(MemberDto memberDto) {
		log.info("selectMemberForLogin()");
		
		String sql = "SELECT * FROM TBL_MEMBER WHERE M_ID = ?";
		
		List<MemberDto> memberDtos = new ArrayList<>();
		
		try {
			
			RowMapper<MemberDto> rowMapper =
					BeanPropertyRowMapper.newInstance(MemberDto.class);
			memberDtos = jdbcTemplate.query(sql, rowMapper, memberDto.getM_id());
			
//			if (!passwordEncoder.matches(memberDto.getM_pw(), memberDtos.get(0).getM_pw()))
//				memberDtos.clear();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return memberDtos.size() > 0 ? memberDtos.get(0) : null;
		
	}

	public MemberDto getLoginedMemberByMId(String loginedMemberID) {
		log.info("getLoginedMemberByMId()");
		
		String sql = "SELECT * FROM TBL_MEMBER WHERE M_ID = ?";
		
		List<MemberDto> memberDtos = new ArrayList<>();
		
		try {
			
			RowMapper<MemberDto> rowMapper =
					BeanPropertyRowMapper.newInstance(MemberDto.class);
			memberDtos = jdbcTemplate.query(sql, rowMapper, loginedMemberID);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return memberDtos.size() > 0 ? memberDtos.get(0) : null;
		
	}

	public int updateMemberForModify(MemberDto memberDto) {
		log.info("updateMemberForModify()");
		
		String sql =  "UPDATE "
					+ "TBL_MEMBER "
					+ "SET M_MAIL = ?, M_PHONE= ?, M_MOD_DATE = NOW() "
					+ "WHERE M_NO = ?";
		
		int result = -1;
		try {
			result = jdbcTemplate.update(sql, 
											memberDto.getM_mail(), 
											memberDto.getM_phone(), 
											memberDto.getM_no());
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return result;
		
	}

	public int deleteMemberByMId(String loginedMemberID) {
		log.info("deleteMemberByMId()");
		
		String sql = "DELETE FROM TBL_MEMBER WHERE M_ID = ?";
		
		int result = -1;
		try {
			result = jdbcTemplate.update(sql, loginedMemberID);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return result;
		
	}
	
}
