package com.office.calender.organizer.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CommentService {

	final private ICommentDao iCommentDao;
	final private CommentDao commentDao;
	
	public CommentService(ICommentDao iCommentDao, CommentDao commentDao) {
		this.iCommentDao = iCommentDao;
		this.commentDao = commentDao;
		
	}

	public Object registComment(Map<String, String> msgMap) {
		log.info("registComment()");
		
		Map<String, Object> resultMap = new HashMap<>();
		
		List<CommentDto> commentDtos = null;
		
//		int result = commentDao.insertComment(msgMap);
		int result = iCommentDao.insertComment(msgMap);
		if (result > 0) {
			log.info("INSERT COMMENT SUCCESS!!");
			
//			commentDtos = commentDao.getComments(msgMap);
			commentDtos = iCommentDao.getComments(msgMap);
			
		} else {
			log.info("INSERT COMMENT FAIL!!");
			
		}
		
		resultMap.put("result", result);
		resultMap.put("commentDtos", commentDtos);
		
		return resultMap;
		
	}

	public Object getComments(Map<String, String> msgMap) {
		log.info("getComments()");
		
		Map<String, Object> resultMap = new HashMap<>();
		
//		List<CommentDto> commentDtos =
//				commentDao.getComments(msgMap);
		List<CommentDto> commentDtos =
				iCommentDao.getComments(msgMap);
		
		resultMap.put("commentDtos", commentDtos);
		
		return resultMap;
		
	}
	
}
