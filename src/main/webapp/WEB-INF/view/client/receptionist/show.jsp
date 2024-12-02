<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Giao Diện Lễ Tân</title>
  <!-- Bootstrap 5 CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
  <div class="container text-center mt-5">
    <!-- Tiêu đề -->
    <h2 class="mb-4">Giao diện chính của lễ tân</h2>

    <!-- Tên lễ tân -->
    <div class="mb-3">
      <h4>${currentMember.name}</h4>
    </div>

    <!-- Nút Thanh toán học phí -->
    <div class="d-flex justify-content-center">
      <a href="/receptionist/search" class="btn btn-primary me-3">Thanh toán học phí</a>
      <form method="post" action="/logout" id="logoutForm">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button class="btn btn-danger" type="submit">Đăng xuất</button>
      </form>
    </div>
  </div>

  <!-- Bootstrap 5 JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
