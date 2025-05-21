package com.office.calender.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberDao {

	public boolean isMember(String m_id);

	public int insertMember(MemberDto memberDto);

	public MemberDto selectMemberForLogin(MemberDto memberDto);

	public MemberDto getLoginedMemberByMId(String loginedMemberID);

	public MemberDto selectMemberByMId(String m_id);
	
	public int updateMemberForModify(MemberDto memberDto);

	public int deleteMemberByMId(String loginedMemberID);
	
}