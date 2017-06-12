$(document).ready(function() {
	$("#login-btn").click(function() {
		var username = $("#username").val();
		if (username == null || username == '') {
			bootbox.alert("请填写用户名！");
			return;
		}
		var password = $("#password").val();
		if (password == null || password == '') {
			bootbox.alert("请填写密码！");
			return;
		}
		var url;
		var grade = $("#grade").val();
		if (grade == 'S') {
			url = "pages/student/index.jsp";
		} else if (grade == 'T') {
			url = "pages/teacher/index.jsp";
		} else if (grade == 'A') {
			url = "pages/admin/index.jsp";
		}
		$.ajax({
			type : 'POST',
			url : "common/login.do",
			data : {
				username : username,
				password : password,
				grade : grade
			},
			success : function(data) {
				if (data.success) {
					window.location.href = url;
				} else {
					bootbox.alert(data.info);
				}
			},
			dataType : 'json'
		});
	});
	$("#register-btn").click(function() {
		window.location.href = 'register.jsp';
	});
});