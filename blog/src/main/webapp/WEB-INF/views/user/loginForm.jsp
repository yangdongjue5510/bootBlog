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
    <a href="https://kauth.kakao.com/oauth/authorize?client_id=588c0de26db068ebeb3e6d00bb442aff&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><img height="38px" src="/image/kakaoButton.png"/> </a>
  </form>
</div>
<script src = "/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>