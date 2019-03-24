function refreshCaptcha() {
	$.ajax({
		type : 'GET',
		url : '/captcha-servlet',
		success : function(data) {
			$('img').attr('src', '/captcha-servlet');
		}
	});
}