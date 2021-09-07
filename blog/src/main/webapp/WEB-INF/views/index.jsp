<%--
  Created by IntelliJ IDEA.
  User: MUHN2-031
  Date: 2021/09/07
  Time: 8:06 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-md bg-dark navbar-dark">
    <a class="navbar-brand" href="/blog">Mudura</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="/user/login">로그인</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user/join">회원가입</a>
            </li>
        </ul>
    </div>
</nav>
<br>

<div class="container">
    <div class="card m-2" >
        <div class="card-body">
            <h4 class="card-title">제목</h4>
            <p class="card-text"> 내용 </p>
            <a href="#" class="btn btn-primary">상세보기</a>
        </div>
    </div>
  <div class="card m-2" >
        <div class="card-body">
            <h4 class="card-title">제목</h4>
            <p class="card-text"> 내용 </p>
            <a href="#" class="btn btn-primary">상세보기</a>
        </div>
    </div>
  <div class="card m-2" >
        <div class="card-body">
            <h4 class="card-title">제목</h4>
            <p class="card-text"> 내용 </p>
            <a href="#" class="btn btn-primary">상세보기</a>
        </div>
    </div>

</div>
<%--푸터자리--%>
<div class="jumbotron text-center" style="margin-bottom:0">
    <p>🧑‍💻 created by Yangdongjue5510</p>
    <p>💭 Backend Dev.</p>
    <p>🌱 Powered by Spring Boot</p>
</div>
</body>
</html>


