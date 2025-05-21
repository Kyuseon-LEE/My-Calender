package com.office.calender.organizer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.office.calender.member.MemberDto;
import com.office.calender.organizer.config.OrganizerConfig;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class OrganizerDao {
	
	final private JdbcTemplate jdbcTemplate;
	
	public OrganizerDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		
	}
	
	public int insertNewPlan(OrganizerDto organizerDto) {
		log.info("insertNewPlan()");
		
		String sql =  "INSERT INTO "
					+ "TBL_PLAN("
					+ 	"P_ORI_OWNER_ID, "
					+ 	"M_ID, "
					+ 	"P_YEAR, "
					+ 	"P_MONTH, "
					+ 	"P_DATE, "
					+ 	"P_TITLE, "
					+ 	"P_BODY, "
					+ 	"P_IMG_NAME)"
					+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		
		int result = -1;
		try {
			
			result = jdbcTemplate.update(sql, 
											organizerDto.getM_id(), 
											organizerDto.getM_id(),
											organizerDto.getP_year(),
											organizerDto.getP_month(),
											organizerDto.getP_date(),
											organizerDto.getP_title(),
											organizerDto.getP_body(),
											organizerDto.getP_img_name());
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return result;
		
	}

	public int getMaxPNo() {
		log.info("getMaxPNo()");
		
		String sql = "SELECT MAX(P_NO) FROM TBL_PLAN";
		
		int maxPNo = 0;
		try {
			
			maxPNo = jdbcTemplate.queryForObject(sql, Integer.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return maxPNo;
		
	}

	public int updateOriNo(int maxPNo) {
		log.info("updateOriNo()");
		
		String sql = "UPDATE TBL_PLAN SET P_ORI_NO = ? WHERE P_NO = ?";
		
		int result = -1;
		try {
			
			result = jdbcTemplate.update(sql, maxPNo, maxPNo);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return result;
		
	}

	public List<OrganizerDto> selectPlansByMNo(Map<String, String> msgMap) {
		log.info("selectPlansByMNo()");
		
		String sql =  "SELECT * FROM TBL_PLAN "
					+ "WHERE P_YEAR = ? AND P_MONTH = ? AND M_ID = ?";
		
		List<OrganizerDto> organizerDtos = new ArrayList<>();
		
		try {
			
			RowMapper<OrganizerDto> rowMapper = BeanPropertyRowMapper.newInstance(OrganizerDto.class);
			organizerDtos = jdbcTemplate.query(sql, rowMapper,
															msgMap.get("year"), 
															msgMap.get("month"), 
															msgMap.get("m_id"));
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return organizerDtos;
		
	}

	public int getLastInsertedId() {
		log.info("getLastInsertedId()");
		
		String sql = "SELECT LAST_INSERT_ID()";
		
		int lastInsertedId = 0;
		try {
			
			lastInsertedId = jdbcTemplate.queryForObject(sql, Integer.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return lastInsertedId;
		
	}

	public OrganizerDto selectPlanByPNo(Map<String, String> msgMap) {
		log.info("selectPlanByPNo()");
		
		String sql = "SELECT * FROM TBL_PLAN WHERE P_NO = ?";
		
		List<OrganizerDto> organizerDtos = new ArrayList<>();
		
		try {
			
			RowMapper<OrganizerDto> rowMapper =
					BeanPropertyRowMapper.newInstance(OrganizerDto.class);
			organizerDtos = jdbcTemplate.query(sql, rowMapper, msgMap.get("p_no"));
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return organizerDtos.size() > 0 ? organizerDtos.get(0) : null;
		
	}

	public int deletePlanByPNo(Map<String, String> msgMap) {
		log.info("deletePlanByPNo()");
		
		String sql = "DELETE FROM TBL_PLAN WHERE P_ORI_NO = ?";
		
		int result = -1;
		try {
			
			result = jdbcTemplate.update(sql, msgMap.get("p_no"));
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return result;
		
	}

	public int updatePlanByPNo(OrganizerDto organizerDto) {
		log.info("updatePlanByPNo()");
		
		List<String> args = new ArrayList<>();
		
		String sql =  "UPDATE TBL_PLAN SET "
					+ 	"P_YEAR = ? "
					+ 	", P_MONTH = ? "
					+ 	", P_DATE = ? "
					+ 	", P_TITLE = ? "
					+ 	", P_BODY = ? ";
		
		args.add(String.valueOf(organizerDto.getP_year()));
		args.add(String.valueOf(organizerDto.getP_month()));
		args.add(String.valueOf(organizerDto.getP_date()));
		args.add(organizerDto.getP_title());
		args.add(organizerDto.getP_body());
		
		if (organizerDto.getP_img_name() != null) {
			sql += ", P_IMG_NAME = ? ";
			args.add(organizerDto.getP_img_name());
			
		}
		
		sql += ", P_MOD_DATE = NOW() ";
		
		sql += "WHERE P_ORI_NO = ?";
		args.add(String.valueOf(organizerDto.getP_no()));
		
		int result = -1;
		try {
			
			result = jdbcTemplate.update(sql, args.toArray());
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return result;
		
	}

	public List<MemberDto> selectSearchFriends(String friendID) { 
		log.info("selectSearchFriends()");
		
		String sql =  "SELECT M_NO, M_ID FROM TBL_MEMBER "
					+ "WHERE M_ID LIKE ? "
					+ "ORDER BY M_ID ASC";
		
		List<MemberDto> memberDtos = new ArrayList<>();
		try {
			
			memberDtos = jdbcTemplate.query(sql, new RowMapper<MemberDto>() {

				@Override
				public MemberDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					MemberDto dto = new MemberDto();
					
					dto.setM_no(rs.getInt("M_NO"));
					dto.setM_id(rs.getString("M_ID"));
					
					return dto;
					
				}
				
			}, "%" + friendID + "%");
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return memberDtos;
		
	}

	public int sharePlan(OrganizerDto organizerDto, String m_id) {
		log.info("sharePlan()");
		
		String sql =  "INSERT INTO TBL_PLAN("
					+ 	"P_ORI_NO, "
					+ 	"P_ORI_OWNER_ID, "
					+ 	"M_ID, "
					+ 	"P_YEAR, "
					+ 	"P_MONTH, "
					+ 	"P_DATE, "
					+ 	"P_TITLE, "
					+ 	"P_BODY, "
					+ 	"P_IMG_NAME) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		int result = -1;
		try {
			
			result = jdbcTemplate.update(sql, 
											organizerDto.getP_ori_no(), 
											organizerDto.getP_ori_owner_id(),
											m_id,
											organizerDto.getP_year(),
											organizerDto.getP_month(),
											organizerDto.getP_date(),
											organizerDto.getP_title(),
											organizerDto.getP_body(),
											organizerDto.getP_img_name());
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return result > 0 ? OrganizerConfig.SHARE_PLAN_SUCCESS : OrganizerConfig.SHARE_PLAN_FAIL;
		
	}

	public boolean isSharePlan(OrganizerDto organizerDto, String m_id) {
		log.info("isSharePlan()");
		
		String sql =  "SELECT COUNT(*) FROM TBL_PLAN "
					+ "WHERE P_ORI_NO = ? AND M_ID = ?";
		
		boolean isSharePlan = false;
		try {
			int result = jdbcTemplate.queryForObject(sql, 
														Integer.class, 
														organizerDto.getP_ori_no(), m_id);
			if (result > 0)
				isSharePlan = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return isSharePlan;
		
	}


}
