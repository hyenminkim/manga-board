$(function(){
    loadComment();

    $("#btn_notice").click(function(){
        const subject = $("#subject").val().trim();
        const contents = $("#contents").val().trim();

        // Validate inputs
        if(!subject || !contents) {
            alert("제목과 내용을 모두 입력해주세요.");
            return;
        }

        // 전달할 parameter 들 준비
        const data = {
            "subject": subject,
            "contents": contents
        };

        $.ajax({
            url: "/notice/write",
            type: "POST",
            data: data,
            cache: false,
            success: function(data){
                loadComment();
                $("#subject").val('');
                $("#contents").val('');
            },
            error: function(xhr, status, error) {
                alert("등록 중 오류가 발생했습니다: " + error);
            }
        });
    });
});

function loadComment(){
    $.ajax({
        url: "/notice/list",
        type: "GET",
        dataType: "json",
        success: function(data){
            buildNotice(data);
        },
        error: function(xhr, status, error) {
            console.error("목록을 불러오는 중 오류가 발생했습니다:", error);
        }
    });
}

function buildNotice(result){
    const out = [];
    result.forEach(notice => {
        const row = `
            <tr>
                <td>${escapeHtml(notice.write_name)}</td>
                <td>${escapeHtml(notice.subject)}</td>
                <td>${escapeHtml(notice.contents)}</td>
                <td>${notice.regDate != null ? notice.regDate.split("T")[0] : ''}</td>
                <td>
                    <button type="button" class="btn-delete" id="btn-delete" name="btn-delete" data-id="${notice.id}">삭제</button>
                </td>
            </tr>`;
        out.push(row);
    });

    $("#notice_list").html(out.join("\n"));

    // Attach event handlers to delete buttons
    $(".btn-delete").click(function() {
        const noticeId = $(this).data("id");
        deleteNotice(noticeId);
    });
}

function deleteNotice(noticeId) {
    if(!noticeId) {
        console.error("삭제할 ID가 없습니다.");
        return;
    }

    if(confirm("정말 삭제하시겠습니까?")) {
        $.ajax({
            url: "/notice/delete",
            type: "POST",
            dataType: "json",
            data: {"id": noticeId},
            success: function(data){
                alert("삭제 완료");
                loadComment(); // 삭제 성공 후 목록 다시 로드
            },
            error: function(error) {
                alert("삭제 중 오류가 발생했습니다: " + error);
            }
        });
    }
}

// Helper function to prevent XSS
function escapeHtml(unsafe) {
    return unsafe
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}