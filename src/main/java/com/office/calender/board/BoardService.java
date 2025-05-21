package com.office.calender.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BoardService {

	final private IBoardDao iBoardDao;
	
	public BoardService(IBoardDao iBoardDao) {
		this.iBoardDao = iBoardDao;
	}

	public int writeConfirm(BoardDto boardDto) {
		log.info("writeConfirm()");
		
		int result = iBoardDao.insertNewPost(boardDto);
		
		if(result <= 0 ) {
			log.info("WRITE FAIL");
		} else {
			log.info("WRITE SUCCESS");
			
			int maxNo = iBoardDao.getMaxNo();
			result = iBoardDao.updateGroup(maxNo);
		}
			
		
		return result;
	}

	public Map<String, Object> list(String m_id) {
		log.info("list()");
		
		Map<String, Object> resultMap = new HashMap<>();
		
		List<BoardDto> boardDtos = iBoardDao.getAllPost(m_id);
		
		resultMap.put("boardDtos", boardDtos);
		
		return resultMap;
	}

	public BoardDto body(int b_no) {
		log.info("body()");
		
		return iBoardDao.getPostByBNo(b_no);
	}

	public BoardDto replyForm(int b_no) {
		log.info("replyForm()");
		
		return body(b_no);
	}

	public int replyConfirm(BoardDto boardDto) {
		log.info("replyConfirm()");
		
		iBoardDao.setReplyShape(boardDto);		
		
		return iBoardDao.insertNewReplyPost(boardDto);
	}

	public BoardDto modifyForm(int b_no) {
		log.info("modifyForm()");
		
		return body(b_no);
	}

	public int modifyConfirm(BoardDto boardDto) {
		log.info("modifyConfirm()");
		
		return iBoardDao.updatePost(boardDto);
		
	}
	
}
