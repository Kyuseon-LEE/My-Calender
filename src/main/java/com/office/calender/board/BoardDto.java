package com.office.calender.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

	private int b_no;
	private String m_id;
	private String b_title;
	private String b_body;
	private int b_hit;
	private int b_group;
	private int b_step;
	private int b_indent;
    private String b_reg_date;
    private String b_mod_date;
	
}
