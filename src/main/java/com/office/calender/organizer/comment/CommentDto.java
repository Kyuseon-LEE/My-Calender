package com.office.calender.organizer.comment;

import com.office.calender.member.MemberDto;
import com.office.calender.organizer.OrganizerDto;

import lombok.Data;

@Data
public class CommentDto {

	private int c_no;
	private OrganizerDto organizerDto;
	private MemberDto memberDto;
	private String c_txt;
	private String c_reg_date;
	private String c_mod_date;
    
}
