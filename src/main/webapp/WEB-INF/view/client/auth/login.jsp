<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .login-container {
            max-width: 400px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .header {
            background-color: #e0e9f1;
            text-align: center;
            padding: 10px;
            font-weight: bold;
            margin-bottom: 20px;
            border-radius: 5px;
        }
    </style>
</head>
<body>

<div class="login-container">
    <div class="header">HỆ THỐNG QUẢN LÝ TRUNG TÂM TIẾNG ANH</div>
    <c:if test="${error != null}">
        <div class="my-2" style="color: red;">${error}</div>
    </c:if>
    <form:form method="post" action="/login" modelAttribute="member">
        <div class="mb-3">
            <label for="username" class="form-label">Username:</label>
            <form:input type="text" class="form-control" path="username" placeholder="Enter your username"/>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password:</label>
            <form:input type="password" class="form-control" path="password" placeholder="Enter your password"/>
        </div>
        <button type="submit" class="btn btn-primary w-100">LOGIN</button>
    </form:form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>