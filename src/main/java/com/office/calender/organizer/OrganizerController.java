package com.office.calender.organizer;

import java.security.Principal;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.office.calender.organizer.util.UploadFileService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;


@Log4j2
@Controller
@RequestMapping("/organizer")
public class OrganizerController {

	final private OrganizerService organizerService;
	final private UploadFileService uploadFileService;
	
	public OrganizerController(OrganizerService organizerService, 
			UploadFileService uploadFileService) {
		this.organizerService = organizerService;
		this.uploadFileService = uploadFileService;
		
	}
	
	@GetMapping({"", "/"})
	public String home() {
		log.info("home()");
		
		String nextPage = "organizer/home";
		
		return nextPage;
		
	}
	
	@PostMapping("/writePlan")
	@ResponseBody
	public Object writePlan(OrganizerDto organizerDto, 
			@RequestParam("file") MultipartFile file, 
			HttpSession session, Principal principal) {
		log.info("writePlan");
		
		String loginedMemberID = principal.getName();
		
		// SAVE FILE
		String savedFileName = 
				uploadFileService.upload(loginedMemberID, file);
		if (savedFileName != null) {
			organizerDto.setP_img_name(savedFileName);
			organizerDto.setM_id(loginedMemberID);
			
			return organizerService.writePlan(organizerDto);
			
		} else {
			
			return null;
		
		}
		
	}
	
	@PostMapping("/getPlans")
	@ResponseBody
	public Object getPlans(
			@RequestBody Map<String, String> msgMap, 
			HttpSession session, Principal principal) {
		log.info("getPlans()");
		
		String loginedMemberID = principal.getName();
		
		msgMap.put("m_id", loginedMemberID);
		
		return organizerService.getPlans(msgMap);
		
	}
	
	@PostMapping("/getPlan")
	@ResponseBody
	public Object getPlan(@RequestBody Map<String, String> msgMap) {
		log.info("getPlan()");
		
		Map<String, Object> resultMap = organizerService.getPlan(msgMap);
		
		return resultMap;
		
	}
	
	@PostMapping("/removePlan")
	@ResponseBody
	public Object removePlan(@RequestBody Map<String, String> msgMap) {
		log.info("removePlan()");
		
		Map<String, Object> resultMap =
				organizerService.removePlan(msgMap);
		
		return resultMap;
		
	}
	
	@PostMapping("/modifyPlan")
	@ResponseBody
	public Object modifyPlan(OrganizerDto organizerDto, 
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpSession session, Principal principal) {
		log.info("modifyPlan()");
		
		if (file != null) {
			
			String loginedMemberID = principal.getName();
			
			String savedFileName =
					uploadFileService.upload(loginedMemberID, file);
			
			if (savedFileName != null) {
				
				organizerDto.setM_id(loginedMemberID);
				organizerDto.setP_img_name(savedFileName);
				
				return organizerService.modifyPlan(organizerDto);
				
			} else {
				return null;
				
			}
			
		} else {
			return organizerService.modifyPlan(organizerDto);
			
		}
		
	}
	
	@PostMapping("/searchFriends")
	@ResponseBody
	public Object searchFriends(@RequestBody Map<String, String> msgMap) {
		log.info("searchFriends()");
		
		Map<String, Object> resultMap =
				organizerService.searchFriends(msgMap);
		
		return resultMap;
		
	}
	
	@PostMapping("/sharePlan")
	@ResponseBody
	public Object sharePlan(@RequestBody Map<String, String> msgMap) {
		log.info("sharePlan()");
		
		Map<String, Object> resultMap =
				organizerService.sharePlan(msgMap);
		
		return resultMap;
		
	}
	
	
}
