<%--
  Created by IntelliJ IDEA.
  User: MUHN2-031
  Date: 2021/09/08
  Time: 4:58 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../layout/header.jsp"%>

<div class="container">
  <form action="/auth/loginProc" method="post">
    <div class="form-group">
      <label for="username">Username</label>
      <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
    </div>
    <div class="form-group">
      <label for="password">Password:</label>
      <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
    </div>

    <button id="btn-login" class="btn btn-primary">로그인</button>
  </form>
</div>
<script src = "/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>