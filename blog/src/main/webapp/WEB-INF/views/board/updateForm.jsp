<%--
  Created by IntelliJ IDEA.
  User: MUHN2-031
  Date: 2021/09/07
  Time: 8:06 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../layout/header.jsp"%>

<div class="container">
    <form>
        <input type="hidden" id="id" value="${board.id}"/>
        <div class="form-group">
            <input value="${board.title}" type="text" class="form-control" placeholder="Enter title" id="title">
        </div>
        <div class="form-group">
            <textarea  class="form-control summernote" rows="5" id="content">${board.content}</textarea>
        </div>
    </form>
    <button id="btn-update" class="btn btn-primary">글수정 완료</button>
</div>
<%--summernote--%>
<script>
    $('.summernote').summernote({
        tabsize: 2,
        height: 300
    });
</script>
<script src = "/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>


