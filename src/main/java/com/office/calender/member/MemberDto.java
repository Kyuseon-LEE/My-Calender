package com.office.calender.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

	private int m_no;
	private String m_id;
	private String m_pw;
	private String m_mail;
	private String m_phone;
	private String m_reg_date;
	private String m_mod_date;
	private String m_role;
    
}
