<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Cập nhật thông tin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-group {
            margin-bottom: 15px;
        }
        .card {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="card">
        <div class="card-header">
            <h3>Cập nhật thông tin</h3>
        </div>
        <div class="card-body">
            <form:form modelAttribute="currentMember" method="POST" action="/student/info/edit">
                <!-- Remove the hidden input for _csrf -->
                <div class="mb-3 row">
                    <label for="name" class="col-sm-3 col-form-label">Họ tên:</label>
                    <div class="col-sm-9">
                        <form:input path="name" type="text" class="form-control" />
                    </div>
                </div>

                <div>
                    <form:hidden path="id"/>
                </div>

                <div class="mb-3 row">
                    <label for="dob" class="col-sm-3 col-form-label">Ngày sinh:</label>
                    <div class="col-sm-9">
                        <form:input path="dob" type="date" class="form-control" />
                    </div>
                </div>

                <div class="mb-3 row">
                    <label for="phone" class="col-sm-3 col-form-label">Số điện thoại:</label>
                    <div class="col-sm-9">
                        <form:input path="phone" type="tel" class="form-control" />
                    </div>
                </div>

                <div class="mb-3 row">
                    <label for="address" class="col-sm-3 col-form-label">Địa chỉ:</label>
                    <div class="col-sm-9">
                        <form:textarea path="address" class="form-control" rows="3"/>
                    </div>
                </div>

                <div class="mb-3 row">
                    <label for="description" class="col-sm-3 col-form-label">Mô tả:</label>
                    <div class="col-sm-9">
                        <form:textarea path="description" class="form-control" rows="3"/>
                    </div>
                </div>

                <div class="mb-3 row">
                    <div class="col-sm-9 offset-sm-3">
                        <input type="submit" value="Cập nhật" class="btn btn-primary"/>
                        <a href="/" class="btn btn-secondary">Back</a>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>