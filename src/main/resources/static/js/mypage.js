function loadContent(action) {
    var url = '/mypage/' + action;
    fetch(url)
        .then(response => response.text())
        .then(data => {
            document.getElementById('content-container').innerHTML = data;
        })
        .catch(error => console.error('에러:', error));
}




  function modifycheck() {

             if(document.modifyForm.username.value=="") {
                         		alert("아이디는 필수 입력입니다.");
                         		document.modifyForm.username.focus();
                 }
            else if(document.modifyForm.password.value=="") {
                     		alert("비밀번호은 필수 입력입니다.");
                     		document.modifyForm.password.focus();
                 }

             else if(document.modifyForm.name.value=="") {
                         		alert("이름은 필수 입력입니다.");
                         		document.modifyFormname.focus();
                     }

            else {
            		modifyForm.submit();
            	}

    }







 function deleteMember(){
       const username = $("input[name='username']").val().trim();


     if(!username){
         alert("삭제할 아이디가 없습니다.");
         return;
     }

     if(confirm("정말 삭제하시겠습니까?")){
         $.ajax({
             url:"/mypage/delete",
             type:"POST",
             dataType:"json",
             data:{"username": username},
             success: function(data){
                 alert("삭제 완료");
                 location.href = '/user/logout';
             },
             error:function(error) {
                 alert("삭제 실패");
             }
         });
     }
 }