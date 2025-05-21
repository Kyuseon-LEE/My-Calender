package com.office.calender.organizer;

import lombok.Data;

@Data
public class OrganizerDto {

	private int p_no;
	private int p_ori_no;
	private String p_ori_owner_id;
	private String m_id;
	private int p_year;
	private int p_month;
	private int p_date;
	private String p_title;
	private String p_body;
	private String p_img_name;
	private String p_reg_date;
	private String p_mod_date;
    
}
