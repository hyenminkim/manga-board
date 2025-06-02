
 var pwdcheck = false;

$(document).ready(function () {
    $("#username").on("focusout", function () {
        var username = $("#username").val();

        	if(username == '' || username.length == 0) {
            			$("#label1").css("color", "red").text("공백은 ID로 사용할 수 없습니다.");
            			return false;
            }



        $.ajax({
            url: './ConfirmId',
            type: 'POST',
            data: { username: username },
            dataType: 'json',
            success: function (result) {
                console.log("결과:", result);

                if (result === false) {
                    $("#label1").css("color", "black").text("사용 가능한 ID 입니다.");
                }

                else if(result === true) {
                    $("#label1").css("color", "red").text("존재하는 ID 입니다.");
                    $("#username").val('');
                }
            },
            error: function (xhr, status, err) {
                console.log("에러 발생:", err);
            }
        });
    });



     $("#re_password").on("focusout", function () {
            var re_password = $("#re_password").val();
            var password = $("#password").val();

            	if(re_password == password) {
                			$("#password_check").css("color", "black").text("비밀번호가 확인되었습니다.");
                            pwdcheck = true;
                }else {
                    $("#password_check").css("color", "red").text("비밀번호가 틀립니다.");

                }



        });


});


  function registercheck() {



             if(document.registerForm.username.value=="") {
                         		alert("아이디는 필수 입력입니다.");
                         		document.registerForm.username.focus();
                 }
            else if(document.registerForm.password.value=="") {
                     		alert("비밀번호은 필수 입력입니다.");
                     		document.registerForm.password.focus();
                 }

             else if(pwdcheck == false) {
                alert("비밀번호 확인을 다시 입력해주세요.");
                	document.registerForm.re_password.focus();
             }
             else if(document.registerForm.name.value=="") {
                         		alert("이름은 필수 입력입니다.");
                         		document.registerForm.name.focus();
                     }

            else {

            		alert("가입 성공!");
            		registerForm.submit();
            	}

    }

