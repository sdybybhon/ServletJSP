<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Вход</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="card shadow" style="max-width: 400px; margin: 0 auto;">
        <div class="card-header bg-primary text-white">
            <h4 class="mb-0">Вход в систему</h4>
        </div>
        <div class="card-body">
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <form action="<c:url value='/login'/>" method="post">
                <div class="mb-3">
                    <label class="form-label">Логин</label>
                    <input type="text" name="username" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Пароль</label>
                    <input type="password" name="password" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Войти</button>
            </form>

            <div class="mt-3 text-center">
                <a href="<c:url value='/register'/>">Создать аккаунт</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>