package com.office.calender.organizer.comment;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICommentDao {

	public int insertComment(Map<String, String> msgMap);

	public List<CommentDto> getComments(Map<String, String> msgMap);
	
}
