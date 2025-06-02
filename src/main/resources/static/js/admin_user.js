$(function() {
    $(".btn-delete").click(function() {
        const member_id = $(this).data("id");
        deleteMember(member_id);
    });
});



function deleteMember(member_id){
    if(!member_id){
        alert("삭제할 아이디가 없습니다.");
        return;
    }

    if(confirm("정말 삭제하시겠습니까?")){
        $.ajax({
            url:"/admin/delete",
            type:"POST",
            dataType:"json",
            data:{"id": member_id},
            success: function(data){
                alert("삭제 완료");
                location.href = location.href;
            },
            error:function(error) {
                alert("삭제 실패");
            }
        });
    }
}