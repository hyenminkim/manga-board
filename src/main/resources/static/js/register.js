var pwdcheck = false;

function setFieldError(field, message) {
    var $field = $(field);
    $field.addClass('is-invalid');
    // Remove existing feedback
    $field.next('.invalid-feedback').remove();
    $field.after('<div class="invalid-feedback">' + message + '</div>');
}

function clearFieldError(field) {
    var $field = $(field);
    $field.removeClass('is-invalid');
    $field.next('.invalid-feedback').remove();
}

function setFieldSuccess(field, message) {
    clearFieldError(field);
    var $field = $(field);
    $field.addClass('is-valid');
    $field.next('.valid-feedback').remove();
    $field.after('<div class="valid-feedback">' + message + '</div>');
}

$(document).ready(function () {
    $("#username").on("focusout", function () {
        var username = $("#username").val();

        if (username == '' || username.length == 0) {
            setFieldError('#username', '공백은 ID로 사용할 수 없습니다.');
            $("#label1").css("color", "red").text("공백은 ID로 사용할 수 없습니다.");
            return false;
        }

        $.ajax({
            url: './ConfirmId',
            type: 'POST',
            data: { username: username },
            dataType: 'json',
            success: function (result) {
                if (result === false) {
                    clearFieldError('#username');
                    setFieldSuccess('#username', '사용 가능한 ID 입니다.');
                    $("#label1").css("color", "black").text("사용 가능한 ID 입니다.");
                } else if (result === true) {
                    setFieldError('#username', '존재하는 ID 입니다.');
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

        if (re_password == password) {
            clearFieldError('#re_password');
            setFieldSuccess('#re_password', '비밀번호가 확인되었습니다.');
            $("#password_check").css("color", "black").text("비밀번호가 확인되었습니다.");
            pwdcheck = true;
        } else {
            setFieldError('#re_password', '비밀번호가 틀립니다.');
            $("#password_check").css("color", "red").text("비밀번호가 틀립니다.");
            pwdcheck = false;
        }
    });

    // Clear errors on input
    $('input').on('input', function () {
        $(this).removeClass('is-invalid is-valid');
        $(this).next('.invalid-feedback, .valid-feedback').remove();
    });
});

function registercheck() {
    var form = document.registerForm;

    if (form.username.value == "") {
        setFieldError('#username', '아이디는 필수 입력입니다.');
        form.username.focus();
    } else if (form.password.value == "") {
        setFieldError('#password', '비밀번호는 필수 입력입니다.');
        form.password.focus();
    } else if (pwdcheck == false) {
        setFieldError('#re_password', '비밀번호 확인을 다시 입력해주세요.');
        form.re_password.focus();
    } else if (form.name.value == "") {
        setFieldError('#name', '이름은 필수 입력입니다.');
        form.name.focus();
    } else {
        if (typeof showToast === 'function') {
            showToast("가입 성공!", "success");
        }
        form.submit();
    }
}
