<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Tìm kiếm đơn đăng ký</title>
  <!-- Bootstrap 5 CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
  <div class="container mt-5">
    <!-- Tiêu đề -->
    <h2 class="text-center mb-4">Tìm kiếm đơn đăng ký</h2>

    <!-- Ô tìm kiếm -->
    <div class="row mb-3">
      <label for="search" class="col-sm-2 col-form-label">Nhập từ khóa:</label>
      <div class="col-sm-6">
        <input type="text" id="search" class="form-control" placeholder="Nhập tên học viên hoặc số điện thoại">
      </div>
      <div class="col-sm-2">
        <button type="button" class="btn btn-primary">Tìm kiếm</button>
      </div>
    </div>

    <!-- Bảng thông tin -->
    <div class="table-responsive">
      <table class="table table-bordered text-center">
        <thead class="table-primary">
          <tr>
            <th>STT</th>
            <th>Mã đơn</th>
            <th>Tên HV</th>
            <th>Ngày đăng ký</th>
            <th>Học phí còn lại</th>
            <th>Số điện thoại</th>
            <th>Chọn</th>
          </tr>
        </thead>
        <tbody>
            <c:forEach var="remainingFee" items="${remainingFees}" varStatus="status">
                <tr>
                    <td>${status.count}</td>
                    <td>${remainingFee.key.id}</td>
                    <td>${remainingFee.key.member.name}</td>
                    <td><fmt:formatDate pattern="dd/MM/yyyy" value="${remainingFee.key.date}"/></td>
                    <td>${remainingFee.value}</td>
                    <td>${remainingFee.key.member.phone}</td>
                    <td><input type="checkbox" name="registrationIds" value="${remainingFee.key.id}"></td>
                  </tr>
            </c:forEach>
          
        
        </tbody>
      </table>
    </div>

    <!-- Nút Back và Thanh toán -->
    <div class="text-center mt-3 mb-3">
      <button type="button" class="btn btn-secondary me-3" onclick="window.location.href='/receptionist'">Back</button>
      <button type="button" class="btn btn-success" id="paymentBtn">Thanh toán</button>
    </div>
  </div>

  <!-- Bootstrap 5 JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
  <script>
    // Event listener cho nút tìm kiếm
    document.querySelector('.btn-primary').addEventListener('click', function() {
      const searchValue = document.getElementById('search').value;
      
      fetch('/api/receptionist/search?param=' + encodeURIComponent(searchValue))
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok: ' + response.status);
          }
          return response.json();
        })
        .then(data => {
          console.log('API Response:', data);
          
          const tbody = document.querySelector('tbody');
          tbody.innerHTML = '';
          
          data.forEach((item, index) => {
            const registration = item.registration;
            const remainingFee = item.remainingFee;
            const date = new Date(registration.date);
            const formattedDate = date.toLocaleDateString('vi-VN', {
              day: '2-digit',
              month: '2-digit',
              year: 'numeric'
            });

            // Create table cells individually
            const row = document.createElement('tr');
            
            const tdIndex = document.createElement('td');
            tdIndex.textContent = index + 1;
            row.appendChild(tdIndex);
            
            const tdId = document.createElement('td'); 
            tdId.textContent = registration.id;
            row.appendChild(tdId);
            
            const tdName = document.createElement('td');
            tdName.textContent = registration.member.name;
            row.appendChild(tdName);
            
            const tdDate = document.createElement('td');
            tdDate.textContent = formattedDate;
            row.appendChild(tdDate);
            
            const tdFee = document.createElement('td');
            tdFee.textContent = remainingFee;
            row.appendChild(tdFee);
            
            const tdPhone = document.createElement('td');
            tdPhone.textContent = registration.member.phone;
            row.appendChild(tdPhone);
            
            const tdCheckbox = document.createElement('td');
            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.name = 'registrationIds';
            checkbox.value = registration.id;
            tdCheckbox.appendChild(checkbox);
            row.appendChild(tdCheckbox);

            tbody.appendChild(row);
          });
        })
        .catch(error => {
          console.error('Detailed error:', error);
          alert('Có lỗi xảy ra khi tìm kiếm. Vui lòng thử lại.');
        });
    
    // Event listener riêng cho nút thanh toán
    
    });
    document.getElementById('paymentBtn').addEventListener('click', function() {
        // Get all checked checkboxes
        const checkedBoxes = document.querySelectorAll('input[name="registrationIds"]:checked');
        
        if (checkedBoxes.length === 0) {
            alert('Vui lòng chọn ít nhất một đăng ký để thanh toán');
            return;
        }

        // Get array of selected registration IDs
        const selectedIds = Array.from(checkedBoxes).map(checkbox => checkbox.value);
        console.log('Selected IDs:', selectedIds);

        // Call API to save selected registrations to session
        fetch('/api/detail-registrations', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(selectedIds)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // Redirect to detail page after successful API call
            window.location.href = '/receptionist/detail-registration';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Có lỗi xảy ra. Vui lòng thử lại.');
        });
    });
  </script>
</body>
</html>
