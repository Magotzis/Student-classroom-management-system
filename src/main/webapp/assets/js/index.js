$(document).ready(function() {
    $("#login-btn").click(function(){
        var url;
        var grade = $("#grade").val();
        if (grade == 'S'){
            url = "pages/student/index.jsp";
        }else if (grade == 'T'){
            url = "pages/teacher/index.jsp";
        }else if (grade == 'A'){
            url = "pages/admin/index.jsp";
        }
        $.ajax({
            type: 'POST',
            url: "common/login.do",
            data: {
                username : $("#username").val(),
                password : $("#password").val(),
                grade : grade
            },
            success: function(data){
                if (data.success){
                    window.location.href = url;
                }else {
                    bootbox.alert(data.info);
                }
            } ,
            dataType: 'json'
        });
    });


});