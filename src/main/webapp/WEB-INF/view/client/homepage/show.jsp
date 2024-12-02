<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chủ của học viên</title>
    <!-- Link to Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center justify-content-center vh-100">
<div class="container">
    <div class="row mb-5">
        <div class="col-md-6 offset-md-3 text-center">
            <h3 class="card-subtitle mb-2 text-muted">Trung tâm ngoại ngữ LCD</h3>
        </div>

    </div>
    <!-- Added a container -->
    <div class="row">  <!-- Added a row -->
        <div class="col-md-6 offset-md-3 text-center">  <!-- Centered column -->
            <div class="card text-center">
                <div class="card-header bg-info text-white">
                    <h5 class="card-title mb-0">Trang chủ của học viên</h5>
                </div>
                <div class="card-body">
                    <p class="card-text">Học viên: ${currentMember.name}</p>
                    <a href="/student/info" class="btn btn-outline-info w-100 mb-2">Quản lý thông tin cá nhân</a>
                    <a href="/student/registration" class="btn btn-outline-info w-100 mb-2">Đăng ký học trực tuyến</a>
                    <form method="post" action="/logout" id="logoutForm">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button class="btn btn-outline-secondary w-100" type="submit">Đăng xuất</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    
</script>
</body>
</html>

