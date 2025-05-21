package com.office.calender.organizer.comment;

import java.security.Principal;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/comment")
public class CommentController {

	final private CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
		
	}
	
	@PostMapping("/registComment")
	public Object registComment(@RequestBody Map<String, String> msgMap, HttpSession session, Principal principal) {
		log.info("registComment");
		
		String loginedMemberID = principal.getName();
		
		msgMap.put("m_id", loginedMemberID);
		
		return commentService.registComment(msgMap);
		
	}
	
	@PostMapping("/getComments")
	public Object getComments(@RequestBody Map<String, String> msgMap) {
		log.info("getComments");
		
		return commentService.getComments(msgMap);
		
	}
	
	
	
}
