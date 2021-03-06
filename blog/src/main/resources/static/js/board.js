let index = {
    init : function(){
        $("#btn-save").on("click",()=>{
            this.save();
        });//btn-save를 찾아 리스너를 킨다.
        // $("#btn-login").on("click",()=>{
        //     this.login();
        // });//btn-login을 찾아 리스너를 킨다.
        $("#btn-delete").on("click",()=>{
            this.deleteById();
        });
        $("#btn-update").on("click",()=>{
            this.update();
        });
        $("#btn-reply-save").on("click",()=>{
            this.replySave();
        });

    },
    save : function(){
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        };
        //console.log(data);
        //ajax 호출 시 비동기 호출
        $.ajax({
            //글쓰기를 요청
            type : "POST",
            url : "/api/board",
            data : JSON.stringify(data), //자바가 js 객체를 인식못해서 json으로.
            contentType: "application/json; charset=utf-8", //바디 데이터가 어떤 타입인지
            dataType : "json" //서버가 보낸 응답이 왔을때 생긴게 json이면 js오브젝트로 변환해서 parse!
        }).done(function (resp) {//resp에는 json객체가 있겠죠.
            //성공시 수행
            console.log(resp);
            alert("글쓰기 완료!");
            location.href = "/"; //응답이 정상일때 출력할 링크
        }).fail(function (error) {
            //실패시 수행
            alert(JSON.stringify(error)); //에러도 json객체로 바꿈

        });//ajax 통신을 이용해서 2개의 데이터를 json으로 변경해 insert요청!
    },
    deleteById : function(){
        //ajax 호출 시 비동기 호출
        let id = $("#id").text();
        $.ajax({
            type : "DELETE",
            url : "/api/board/"+id,
            dataType: "json"
        }).done(function (resp) {
            //성공시 수행
            alert("글삭제 완료!");
            location.href = "/"; //응답이 정상일때 출력할 링크
        }).fail(function (error) {
            //실패시 수행
            alert(JSON.stringify(error)); //에러도 json객체로 바꿈

        });//ajax 통신을 이용해서 2개의 데이터를 json으로 변경해 insert요청!
    },
    update : function(){
        let id = $("#id").val();
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        };
        $.ajax({
            type : "PUT",
            url : "/api/board/"+id,
            data : JSON.stringify(data), //자바가 js 객체를 인식못해서 json으로.
            contentType: "application/json; charset=utf-8", //바디 데이터가 어떤 타입인지
            dataType : "json"
        }).done(function (resp) {//resp에는 json객체가 있겠죠.
            //성공시 수행
            console.log(resp);
            alert("글수정 완료!");
            location.href = "/"; //응답이 정상일때 출력할 링크
        }).fail(function (error) {
            //실패시 수행
            alert(JSON.stringify(error)); //에러도 json객체로 바꿈

        });//ajax 통신을 이용해서 2개의 데이터를 json으로 변경해 insert요청!
    },
    replySave : function(){
        let data = {
            content: $("#reply-content").val(),
            boardId : $("#boardId").val(),
            userId : $("#userId").val()
        };
        $.ajax({
            type : "POST",
            url : `/api/board/${data.boardId}/reply`,
            data : JSON.stringify(data), //자바가 js 객체를 인식못해서 json으로.
            contentType: "application/json; charset=utf-8", //바디 데이터가 어떤 타입인지
            dataType : "json" //서버가 보낸 응답이 왔을때 생긴게 json이면 js오브젝트로 변환해서 parse!
        }).done(function (resp) {//resp에는 json객체가 있겠죠.
            //성공시 수행
            console.log(resp);
            alert("댓글 등록 완료!");
            location.href = `/board/${data.boardId}`;
        }).fail(function (error) {
            //실패시 수행
            alert(JSON.stringify(error));
        });
    },
    replyDelete : function(boardId, replyId){
        $.ajax({
            type : "DELETE",
            url : `/api/board/${boardId}/reply/${replyId}`,
            dataType : "json" //서버가 보낸 응답이 왔을때 생긴게 json이면 js오브젝트로 변환해서 parse!
        }).done(function (resp) {//resp에는 json객체가 있겠죠.
            alert("댓글 삭제 완료!");
            location.href = `/board/${boardId}`;
        }).fail(function (error) {
            //실패시 수행
            alert(JSON.stringify(error));
        });
    },
}
index.init();