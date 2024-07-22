<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link rel="stylesheet" href="../css/common.css">
    <link rel="stylesheet" href="../css/header.css">
    <link rel="stylesheet" href="../css/signup.css">
</head>
<body>
<div class="container">
    <header class="header">
        <h2 class="header-title">HELLO, WEB!</h2>
        <nav>
            <form action="../login" method="get" style="display: inline;">
                <button type="submit" class="login-button">로그인</button>
            </form>
            <form action="../signup" method="get" style="display: inline;">
                <button type="submit" class="signup-button">회원가입</button>
            </form>
        </nav>
    </header>
    <main>
        <h1 class="registration-title">회원가입</h1>
        <form class="registration-form" action="signup" method="post">
            <div class="input-field">
                <label for="email">이메일</label>
                <input
                        type="email"
                        id="email"
                        name="email"
                        placeholder="이메일을 입력해주세요"
                        required
                />
            </div>
            <div class="input-field">
                <label for="nickname">닉네임</label>
                <input
                        type="text"
                        id="nickname"
                        name="nickname"
                        placeholder="닉네임을 입력해주세요"
                        required
                />
            </div>
            <div class="input-field">
                <label for="password">비밀번호</label>
                <input
                        type="password"
                        id="password"
                        name="password"
                        placeholder="비밀번호를 입력해주세요"
                        required
                />
            </div>
            <button type="submit" class="signup-button">회원가입</button>
        </form>
        <p class="login-link">
            이미 회원이신가요? <a href="../login">로그인하기</a>
        </p>
    </main>
</div>
</body>
</html>
