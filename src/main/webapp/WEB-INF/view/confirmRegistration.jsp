<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác nhận đăng ký</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<div class="container mt-5">
    <div class="card">
        <div class="card-header bg-primary text-white text-center">
            <h4>Xác nhận đăng ký</h4>
        </div>
        <div class="card-body">
            <p class="text-end">Ngày: 19/05/2024</p>
            <div class="mb-3">
                <h5>Thông tin học viên:</h5>
                <div class="form-group">
                    <label for="name">Tên:</label>
                    <input type="text" class="form-control" id="name" value="Nguyễn Văn A" readonly>
                </div>
                <div class="form-group">
                    <label for="phone">Số điện thoại:</label>
                    <input type="text" class="form-control" id="phone" value="039876654" readonly>
                </div>
                <div class="form-group">
                    <label for="address">Địa chỉ:</label>
                    <input type="text" class="form-control" id="address" value="Hà Nội" readonly>
                </div>
            </div>
            <div class="mb-3">
                <h5>Thông tin lớp học:</h5>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>STT</th>
                        <th>Tên lớp</th>
                        <th>Khai giảng</th>
                        <th>Hình thức</th>
                        <th>Học phí</th>
                        <td>Thao tác</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="registration" items="${registrations}" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${registration.lophoc.name}</td>
                            <td><fmt:formatDate pattern="dd/MM/yyyy" value="${registration.lophoc.startDate}"/></td>
                            <td>Offline</td>
                            <td>${registration.lophoc.fee}</td>
                            <td>
                                <button class="btn btn-secondary btn-delete" id="delete-btn" data-index="${status.count}">
                                    Xóa
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr></tr>
                        <td colspan="4">Tổng học phí:</td>
                        <td>${total}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="d-flex justify-content-between">
                <button class="btn btn-primary" onclick="window.location.href='/student/registration'">Trở về</button>
                <button class="btn btn-success" id="confirm-btn">Xác nhận</button>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
    $(document).ready(function () {
        // Code here
        const deleteBtn = document.getElementById('delete-btn');
        deleteBtn.addEventListener('click', function () {
            const index = this.getAttribute('data-index');
            if (confirm('Bạn có chắc chắn muốn xóa đăng ký này?')) {
                deleteRegistration(index);
            }
        });

        const confirmBtn = document.getElementById('confirm-btn');
        confirmBtn.addEventListener('click', function () {
            const registrations = [];
            document.querySelectorAll('.btn-delete').forEach(button => {
                const index = button.getAttribute('data-index');
                registrations.push(index);
            });
            addRegistrations(registrations);
        });


        function deleteRegistration(index) {
            fetch(`/api/registration/delete/` + index, {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        alert('Đăng ký đã được xóa thành công!');
                        // Xóa hàng tương ứng trong bảng
                        // document.querySelector(`.btn-delete[data-id="`+ id +`"]`).closest('tr').remove();
                        window.location.href = "/student/confirm-registration";
                    } else {
                        alert('Có lỗi xảy ra khi xóa đăng ký.');
                    }
                })
                .catch(error => {
                    console.error('Lỗi:', error);
                    alert('Có lỗi xảy ra khi xóa đăng ký.');
                });
        }

        function addRegistrations(registrations) {
            fetch('/api/registration', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(registrations)
            })
                .then(response => {
                    if (response.ok) {
                        alert('Đăng ký đã được thêm thành công!');
                        // Xử lý sau khi thêm thành công, ví dụ: tải lại trang
                        window.location.href = "/student/registration"
                    } else {
                        alert('Có lỗi xảy ra khi thêm đăng ký.');
                    }
                })
                .catch(error => {
                    console.error('Lỗi:', error);
                    alert('Có lỗi xảy ra khi thêm đăng ký.');
                });
        }
    });
</script>
</body>
</html>
