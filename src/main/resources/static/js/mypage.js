function loadContent(action) {
    var url = '/mypage/' + action;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            $('#content-container').html(data);
        },
        error: function (error) {
            console.error('에러:', error);
            if (typeof showToast === 'function') {
                showToast('컨텐츠를 불러오는데 실패했습니다.', 'error');
            }
        }
    });
}

function setModifyError(field, message) {
    var $field = $(field);
    $field.addClass('is-invalid');
    $field.next('.invalid-feedback').remove();
    $field.after('<div class="invalid-feedback">' + message + '</div>');
}

function clearModifyError(field) {
    $(field).removeClass('is-invalid');
    $(field).next('.invalid-feedback').remove();
}

function modifycheck() {
    var form = document.modifyForm;

    // Clear previous errors
    $(form).find('.is-invalid').removeClass('is-invalid');
    $(form).find('.invalid-feedback').remove();

    if (form.username.value == "") {
        setModifyError(form.username, '아이디는 필수 입력입니다.');
        form.username.focus();
    } else if (form.password.value == "") {
        setModifyError(form.password, '비밀번호는 필수 입력입니다.');
        form.password.focus();
    } else if (form.name.value == "") {
        setModifyError(form.name, '이름은 필수 입력입니다.');
        form.name.focus();
    } else {
        form.submit();
    }
}

function deleteMember() {
    var username = $("input[name='username']").val().trim();

    if (!username) {
        if (typeof showToast === 'function') {
            showToast("삭제할 아이디가 없습니다.", "error");
        } else {
            alert("삭제할 아이디가 없습니다.");
        }
        return;
    }

    var doDelete = function () {
        $.ajax({
            url: "/mypage/delete",
            type: "POST",
            dataType: "json",
            data: { "username": username },
            success: function () {
                if (typeof showToast === 'function') {
                    showToast("삭제 완료", "success");
                    setTimeout(function () { location.href = '/user/logout'; }, 1000);
                } else {
                    alert("삭제 완료");
                    location.href = '/user/logout';
                }
            },
            error: function () {
                if (typeof showToast === 'function') {
                    showToast("삭제 실패", "error");
                } else {
                    alert("삭제 실패");
                }
            }
        });
    };

    if (typeof showConfirm === 'function') {
        showConfirm("정말 삭제하시겠습니까?").then(function (confirmed) {
            if (confirmed) doDelete();
        });
    } else {
        if (confirm("정말 삭제하시겠습니까?")) doDelete();
    }
}
