function memberLoginForm() {
	console.log('memberLoginForm()');
	
	let form = document.member_login_form;
	if (form.m_id.value === '') {
		alert('INPUT ID!!');
		form.m_id.focus();
		
	} else if (form.m_pw.value === '') {
		alert('INPUT PW!!');
		form.m_pw.focus();
			
	} else {
		form.submit();
		
	}
	
}