package com.office.calender.organizer;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.office.calender.member.MemberDto;

@Mapper
public interface IOrganizerDao {
	
	public int insertNewPlan(OrganizerDto organizerDto);

	public int getMaxPNo();

	public int updateOriNo(int maxPNo);

	public List<OrganizerDto> selectPlansByMNo(Map<String, String> msgMap);

	public int getLastInsertedId();

	public OrganizerDto selectPlanByPNo(Map<String, String> msgMap);

	public int deletePlanByPNo(Map<String, String> msgMap);

	public int updatePlanByPNo(OrganizerDto organizerDto);

	public List<MemberDto> selectSearchFriends(String friendID);

	public int sharePlan(@Param("organizerDto") OrganizerDto organizerDto, @Param("m_id") String m_id);

	public boolean isSharePlan(@Param("organizerDto") OrganizerDto organizerDto, @Param("m_id") String m_id);
	
}
