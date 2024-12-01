<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Interface</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-5">
    <h3 class="text-center mb-4">Giao diện đăng ký học</h3>

    <!-- User's Course -->
    <div class="card mb-4">
        <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
            <span>Khóa học của bạn</span>
            <button class="btn btn-secondary btn-sm" id="cart">Giỏ hàng</button>
        </div>
        <div class="card-body">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>STT</th>
                    <th>Chương trình</th>
                    <th>Mức độ</th>
                    <th>Lớp</th>
                    <th>Ngày đăng ký</th>
                    <th>Khai giảng</th>
                    <th>Lịch học</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="registration" items="${registrations}" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>${registration.lophoc.level.course.name}</td>
                        <td>${registration.lophoc.level.name}</td>
                        <td>${registration.lophoc.name}</td>
                        <td>${registration.date}</td>
                        <td>${registration.lophoc.startDate}</td>
                        <td><a href="#" class="schedule-registration" data-lophoc-name="${registration.lophoc.name}">Lịch học</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Select Program and Level -->
    <div class="row mb-4">
        <div class="col-md-6">
            <label for="programSelect" class="form-label">Chương trình học:</label>
            <select class="form-select" id="programSelect">
                <option>chọn chương trình học</option>
                <c:forEach var="course" items="${courses}">
                    <option>${course.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-6">
            <label for="levelSelect" class="form-label">Chọn mức độ:</label>
            <select class="form-select" id="levelSelect">

            </select>
        </div>
    </div>

    <!-- Suitable Classes -->
    <div class="card mb-4">
        <div class="card-header bg-primary text-white">Lớp học phù hợp với bạn</div>
        <div class="card-body">
            <table class="table table-bordered" id="table-content">
                <thead>
                <tr>
                    <th>STT</th>
                    <th>Tên lớp</th>
                    <th>Lịch học</th>
                    <th>Khai giảng</th>
                    <th>Hình thức</th>
                    <th>Chọn</th>
                </tr>
                </thead>
                <tbody>


                </tbody>
            </table>
        </div>
    </div>

    <!-- Buttons -->
    <div class="d-flex justify-content-center gap-3">
        <button class="btn btn-secondary">Trở về</button>
        <button class="btn btn-info" id="add-to-cart">Thêm vào giỏ</button>
        <button class="btn btn-primary" id="register-now">Đăng ký ngay</button>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="lessonModal" tabindex="-1" aria-labelledby="lessonModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="lessonModalLabel">Lịch học</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" style="max-height: 400px; overflow-y: auto;">
                    <!-- Centered lesson schedule table with blue background -->
                    <div class="table-responsive">
                        <table class="table table-bordered table-centered bg-primary text-white">
                            <thead>
                            <tr>
                                <th>STT</th>
                                <th>Ngày</th>
                                <th>Thời gian bắt đầu</th>
                                <th>Thời gian kết thúc</th>
                                <th>Kĩ năng</th>
                            </tr>
                            </thead>
                            <tbody id="lessonContent">
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Cart Modal -->
    <div class="modal" id="cartModal" tabindex="-1" aria-labelledby="cartModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="cartModalLabel">Giỏ hàng</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" style="max-height: 400px; overflow-y: auto;">
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>STT</th>
                                <th>Chương trình</th>
                                <th>Mức độ</th>
                                <th>Lớp</th>
                                <th>Ngày đăng ký</th>
                                <th>Khai giảng</th>
                                <th>Lịch học</th>
                            </tr>
                            </thead>
                            <tbody id="cartContent">
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
    $(document).ready(function () {
        // Get levels by course
        $(document).on('click', '#cart', function(event) {
            $.ajax({
                url: '/api/class-add-to-cart',
                type: 'GET',
                success: function (data) {
                    var cartContent = document.getElementById("cartContent");
                    cartContent.innerHTML = ""; // Clear existing content
                    data.forEach((lophoc, index) => {
                        var row = document.createElement("tr");

                        var cell1 = document.createElement("td");
                        cell1.innerText = index + 1;
                        row.appendChild(cell1);

                        var cell2 = document.createElement("td");
                        cell2.innerText = lophoc.level.name;
                        row.appendChild(cell2);

                        var cell3 = document.createElement("td");
                        cell3.innerText = lophoc.level.name;
                        row.appendChild(cell3);

                        var cell4 = document.createElement("td");
                        cell4.innerText = lophoc.name;
                        row.appendChild(cell4);

                        var cell5 = document.createElement("td");
                        cell5.innerText = new Date(lophoc.startDate).toLocaleDateString();
                        row.appendChild(cell5);

                        var cell6 = document.createElement("td");
                        cell6.innerText = lophoc.startDate;
                        row.appendChild(cell6);

                        var cell7 = document.createElement("td");
                        var link = document.createElement("a");
                        link.href = "#";
                        link.className = "schedule-registration";
                        link.dataset.lophocName = lophoc.name;
                        link.innerText = "Lịch học";
                        row.appendChild(cell7);

                        cartContent.appendChild(row);
                    });
                    $('#cartModal').modal('show');
                },
                error: function (error) {
                    console.error('Error fetching cart data:', error);
                }
            });
        });
    });
</script>
<script src="/client/register.js"></script>
</body>
</html>

