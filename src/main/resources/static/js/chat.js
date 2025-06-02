$(document).ready(function(){
    $("#createChatBtn").click(function(){
        const title = $("#title").val().trim();
        const member_id = $("#member_id").val().trim();
        if(!title){
            alert("채팅방 제목을 입력하세요.");
            return;
        }

                $.ajax({
                url: "/chat/chatcreate",
                type: "GET",
                data: {
                  title: title,
                  member_id: member_id
                },
                success: function(response) {
                  alert("채팅방 생성 완료");
                },
                error: function(xhr) {
                  alert("오류 발생: " + xhr.status);
                }
                });
    });
});



    function deleteRoom(btn) {
        const roomId = btn.getAttribute("data-room-id");
        if (confirm("정말 삭제하시겠습니까?")) {
            $.ajax({
                url: "/chat/delete",
                method: 'POST',
                data: {"id": roomId},
                success: function (data) {
                    alert("삭제 완료");
                    location.reload();
                },
                error: function () {
                    alert("삭제 실패");
                }
            });
        }
    }

