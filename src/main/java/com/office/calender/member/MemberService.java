package com.office.calender.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MemberService {

	final static public int ID_ALREADY_EXISTS 				= -2;
	final static public int DATABASE_COMMUNICATION_TROUBLE 	= -1;
	final static public int INSERT_FAIL_AT_DATABASE 		= 0;
	final static public int INSERT_SUCCESS_AT_DATABASE 		= 1;
	
	
	final private MemberDao memberDao;
	final private IMemberDao iMemberDao;
	final private PasswordEncoder passwordEncoder;
	
	public MemberService(MemberDao memberDao, IMemberDao iMemberDao, PasswordEncoder passwordEncoder) {
		this.memberDao = memberDao;
		this.iMemberDao = iMemberDao;
		this.passwordEncoder = passwordEncoder;
		
	}

	public int createAccountConfirm(MemberDto memberDto) {
		log.info("createAccountConfirm()");
		
//		boolean isMember = memberDao.isMember(memberDto.getM_id());
		boolean isMember = iMemberDao.isMember(memberDto.getM_id());
		if (!isMember) {
//			int result = memberDao.insertMember(memberDto);
			memberDto.setM_pw(passwordEncoder.encode(memberDto.getM_pw()));
			int result = iMemberDao.insertMember(memberDto);
			
			switch (result) {
			case DATABASE_COMMUNICATION_TROUBLE:
				log.info("DATABASE COMMUNICATION TROUBLE");
				break;

			case INSERT_FAIL_AT_DATABASE:
				log.info("INSERT FAIL AT DATABASE");
				break;
				
			case INSERT_SUCCESS_AT_DATABASE:
				log.info("INSERT SUCCESS AT DATABASE");
				break;
			}
			
			return result;
			
		} else {
			return ID_ALREADY_EXISTS;
			
		}
		
	}

	public MemberDto memberLoginConfirm(MemberDto memberDto) {
		log.info("memberLoginConfirm()");
		
//		MemberDto loginedMemberDto = memberDao.selectMemberForLogin(memberDto);
		MemberDto loginedMemberDto = iMemberDao.selectMemberForLogin(memberDto);
		if (loginedMemberDto != null) {
			if (!passwordEncoder.matches(memberDto.getM_pw(), loginedMemberDto.getM_pw())) {
				loginedMemberDto = null;
			}
			
			return loginedMemberDto;
		}
		
		return null;
		
	}

	public MemberDto memberModifyForm(String loginedMemberID) {
		log.info("memberModifyForm");
		
//		return memberDao.getLoginedMemberByMId(loginedMemberID);
		return iMemberDao.getLoginedMemberByMId(loginedMemberID);
		
	}

	public int memberModifyConfirm(MemberDto memberDto) {
		log.info("memberModifyConfirm()");
		
//		return memberDao.updateMemberForModify(memberDto);
		return iMemberDao.updateMemberForModify(memberDto);
		
	}

	public int memberDeleteConfirm(String loginedMemberID) {
		log.info("memberDeleteConfirm()");
		
//		return memberDao.deleteMemberByMId(loginedMemberID);
		return iMemberDao.deleteMemberByMId(loginedMemberID);
		
	}
	
}
