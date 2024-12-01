<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý thông tin cá nhân</title>
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
        <div class="card-header bg-info text-white">
            <h5 class="card-title mb-0">Thông tin cá nhân</h5>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <form:form modelAttribute="currentMember" method="POST" action="/updateProfile">
                    <div class="form-group">
                        <form:label path="name">Họ tên:</form:label>
                        <form:input path="name" type="text" class="form-control" readonly="true"/>
                    </div>

                    <div class="form-group">
                        <form:label path="phone">Số điện thoại:</form:label>
                        <form:input path="phone" type="tel" class="form-control" readonly="true"/>
                    </div>

                    <div class="form-group">
                        <form:label path="dob">Ngày sinh:</form:label>
                        <form:input path="dob" type="date" class="form-control" readonly="true"/>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <form:label path="address">Địa chỉ:</form:label>
                        <form:textarea path="address" class="form-control" rows="3" readonly="true"/>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Mô tả:</form:label>
                        <form:textarea path="description" class="form-control" rows="3" readonly="true"/>
                    </div>
                    <div class="mt-3">
                        <a href="/student/info/edit" class="btn btn-primary">Update</a>
                        <a href="/" class="btn btn-secondary">Back</a>
                    </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>