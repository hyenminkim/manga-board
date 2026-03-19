$(document).ready(function () {
    $(document).on("click", ".btn-delete[name='btn-delete']", function () {
        var postId = $(this).data("id");
        if (!postId) return;

        if (typeof showConfirm === 'function') {
            showConfirm("해당 게시글을 삭제하시겠습니까?").then(function (confirmed) {
                if (confirmed) deletePost(postId);
            });
        } else {
            if (confirm("해당 게시글을 삭제하시겠습니까?")) {
                deletePost(postId);
            }
        }
    });

    function deletePost(postId) {
        $.ajax({
            url: "/admin/post/delete",
            type: "POST",
            data: { id: postId },
            dataType: "json",
            success: function () {
                if (typeof showToast === 'function') {
                    showToast("게시글이 삭제되었습니다.", "success");
                } else {
                    alert("게시글이 삭제되었습니다.");
                }
                setTimeout(function () { location.reload(); }, 500);
            },
            error: function () {
                if (typeof showToast === 'function') {
                    showToast("게시글 삭제에 실패했습니다.", "error");
                } else {
                    alert("게시글 삭제에 실패했습니다.");
                }
            }
        });
    }
});
