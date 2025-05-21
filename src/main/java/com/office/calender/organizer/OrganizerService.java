package com.office.calender.organizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.office.calender.member.MemberDto;
import com.office.calender.organizer.config.OrganizerConfig;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class OrganizerService {
	
	final private IOrganizerDao iOrganizerDao;
	final private OrganizerDao organizerDao;
	
	public OrganizerService(IOrganizerDao iOrganizerDao, OrganizerDao organizerDao) {
		this.iOrganizerDao = iOrganizerDao;
		this.organizerDao = organizerDao;
		
	}

	public Object writePlan(OrganizerDto organizerDto) {
		log.info("writePlan()");
		
		Map<String, Object> resultMap = new HashMap<>();
		
//		int result = organizerDao.insertNewPlan(organizerDto);
		int result = iOrganizerDao.insertNewPlan(organizerDto);
		if (result <= 0) {
			log.info("writePlan() FAIL!!");
			
		} else {
			log.info("writePlan() SUCCESS!!");
			
//			int lastInsertedId = organizerDao.getLastInsertedId();
			int lastInsertedId = iOrganizerDao.getLastInsertedId();
			log.info("lastInsertedId: {}", lastInsertedId);
			
//			int maxPNo = organizerDao.getMaxPNo();
//			int maxPNo = iOrganizerDao.getMaxPNo();
//			log.info("maxPNo: {}", maxPNo);
			
//			result = organizerDao.updateOriNo(lastInsertedId);
			result = iOrganizerDao.updateOriNo(lastInsertedId);
			
		}
		
		resultMap.put("result", result);
		
		return resultMap;
		
	}

	public Map<String, Object> getPlans(Map<String, String> msgMap) {
		log.info("getPlans()");
		
		Map<String, Object> resultMap = new HashMap<>();
		
//		List<OrganizerDto> organizerDtos = 
//				organizerDao.selectPlansByMNo(msgMap);
		List<OrganizerDto> organizerDtos = 
				iOrganizerDao.selectPlansByMNo(msgMap);
		resultMap.put("organizerDtos", organizerDtos);
		
		return resultMap;
		
	}

	public Map<String, Object> getPlan(Map<String, String> msgMap) {
		log.info("getPlan()");
		
		Map<String, Object> resultMap = new HashMap<>();
		
//		OrganizerDto organizerDto = organizerDao.selectPlanByPNo(msgMap);
		OrganizerDto organizerDto = iOrganizerDao.selectPlanByPNo(msgMap);
		resultMap.put("organizerDto", organizerDto);
		
		return resultMap;
		
	}

	public Map<String, Object> removePlan(Map<String, String> msgMap) {
		log.info("removePlan()");
		
		Map<String, Object> resultMap = new HashMap<>();
		
//		int result = organizerDao.deletePlanByPNo(msgMap);
		int result = iOrganizerDao.deletePlanByPNo(msgMap);
		if (result > 0)
			log.info("DELETE PLAN SUCCESS!!");
		else
			log.info("DELETE PLAN FAIL!!");
		
		resultMap.put("result", result);
		
		return resultMap;
		
	}

	public Map<String, Object> modifyPlan(OrganizerDto organizerDto) {
		log.info("modifyPlan()");
		
		Map<String, Object> resultMap = new HashMap<>();
		
//		int result = organizerDao.updatePlanByPNo(organizerDto);
		int result = iOrganizerDao.updatePlanByPNo(organizerDto);
		if (result > 0)
			log.info("MODIFY SUCCESS!!");
		else
			log.info("MODIFY FAIL!!");
		
		resultMap.put("result", result);
		
		return resultMap;
		
	}

	public Map<String, Object> searchFriends(Map<String, String> msgMap) {
		log.info("searchFriends()");
		
		Map<String, Object> resultMap = new HashMap<>();
		
//		List<MemberDto> memberDtos =
//				organizerDao.selectSearchFriends(msgMap.get("friendID"));
		List<MemberDto> memberDtos =
				iOrganizerDao.selectSearchFriends(msgMap.get("friendID"));
		
		resultMap.put("memberDtos", memberDtos);
		
		return resultMap;
		
	}

	public Map<String, Object> sharePlan(Map<String, String> msgMap) {
		log.info("sharePlan()");
		
		Map<String, Object> resultMap = new HashMap<>();
		
//		OrganizerDto organizerDto = organizerDao.selectPlanByPNo(msgMap);
		OrganizerDto organizerDto = iOrganizerDao.selectPlanByPNo(msgMap);
		
//		boolean isSharePlan = organizerDao.isSharePlan(organizerDto, msgMap.get("m_id"));
		boolean isSharePlan = iOrganizerDao.isSharePlan(organizerDto, msgMap.get("m_id"));
		int result = OrganizerConfig.SHARE_PLAN_FAIL;
		if (!isSharePlan) {
			
//			result = organizerDao.sharePlan(organizerDto, msgMap.get("m_id"));
			result = iOrganizerDao.sharePlan(organizerDto, msgMap.get("m_id"));
			switch (result) {
			case OrganizerConfig.SHARE_PLAN_FAIL:	
				log.info("SHARE PLAN FAIL!!");
				result = OrganizerConfig.SHARE_PLAN_FAIL;
				
				break;
				
			case OrganizerConfig.SHARE_PLAN_SUCCESS:		
				log.info("SHARE PLAN SUCCESS!!");
				result = OrganizerConfig.SHARE_PLAN_SUCCESS;
				
				break;

			}
			
		} else {
			log.info("ALREADY SHARE PLAN!!");
			result = OrganizerConfig.ALREADY_SHARED_PLAN;
			
		}
		
		resultMap.put("result", result);
		
		return resultMap;
		
	}
	
}
