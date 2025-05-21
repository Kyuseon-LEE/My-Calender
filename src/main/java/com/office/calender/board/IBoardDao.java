package com.office.calender.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IBoardDao {

	public int insertNewPost(BoardDto boardDto);

	public int getMaxNo();

	public int updateGroup(int maxNo);

	public List<BoardDto> getAllPost(String m_id);

	public BoardDto getPostByBNo(int b_no);

	public void setReplyShape(BoardDto boardDto);

	public int insertNewReplyPost(BoardDto boardDto);

	public int updatePost(BoardDto boardDto);

}
