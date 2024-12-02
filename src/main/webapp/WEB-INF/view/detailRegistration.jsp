<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Chi tiết đơn đăng ký</title>
  <!-- Bootstrap 5 CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
  <div class="container mt-5">
    <!-- Tiêu đề -->
    <h2 class="text-center mb-3">Chi tiết đơn đăng ký</h2>
    <p class="text-center">Ngày đăng ký: <fmt:formatDate value="<%= new java.util.Date() %>" pattern="dd/MM/yyyy"/></p>

    <!-- Thông tin học viên -->
    <div class="mb-4">
      <h5>Thông tin học viên:</h5>
      <div class="row">
        <div class="col-md-8">
          <table class="table table-bordered">
            <tr>
              <th>Tên:</th>
              <td>${member.name}</td>
            </tr>
            <tr>
              <th>Số điện thoại:</th>
              <td>${member.phone}</td>
            </tr>
            <tr>
              <th>Địa chỉ:</th>
              <td>${member.address}</td>
            </tr>
          </table>
        </div>
        <div class="col-md-4 text-end">
          <a id="editMember" class="btn btn-link">Sửa</a>
        </div>
      </div>
    </div>

    <!-- Modal cập nhật thông tin -->
    <div class="modal fade" id="updateInfoModal" tabindex="-1" aria-labelledby="updateInfoModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header table-primary text-center">
                    <h5 class="modal-title w-100" id="updateInfoModalLabel">Cập nhật thông tin</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form>
                      <input type="hidden" id="memberId" value="${member.id}">
                        <div class="mb-3">
                            <label for="memberName" class="form-label">Tên học viên</label>
                            <input type="text" class="form-control" id="memberName" value="${member.name}">
                        </div>
                        <div class="mb-3">
                            <label for="memberBirthday" class="form-label">Ngày sinh</label>
                            <input type="date" class="form-control" id="memberBirthday" value="${member.dob}">
                        </div>
                        <div class="mb-3">
                            <label for="memberPhone" class="form-label">Số điện thoại</label>
                            <input type="tel" class="form-control" id="memberPhone" value="${member.phone}">
                        </div>
                        <div class="mb-3">
                            <label for="memberAddress" class="form-label">Địa chỉ</label>
                            <input type="text" class="form-control" id="memberAddress" value="${member.address}">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Trở về</button>
                    <button type="button" class="btn btn-primary" id="saveChanges">Lưu</button>
                </div>
            </div>
        </div>
    </div>

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

    <!-- Thông tin lớp học -->
    <div class="mb-4">
      <h5>Thông tin lớp học:</h5>
      <table class="table table-bordered text-center" id="registrationTable">
        <thead class="table-primary">
          <tr>
            <th>STT</th>
            <th>Tên lớp</th>
            <th>Lịch học</th>
            <th>Khai giảng</th>
            <th>Hình thức</th>
            <th>Học phí</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="registration" items="${selectedRegistrations}" varStatus="status">
            <tr>
              <td>${status.count}</td>
              <td>${registration.lophoc.name}</td>
              <td><a href="#" class="schedule-registration" data-lophoc-name="${registration.lophoc.name}">Lịch học</a></td>
              <td>${registration.lophoc.startDate}</td>
              <td>Offline</td>
              <td>${registration.lophoc.fee}</td>
              <td><a href="#" class="btn btn-link">Sửa</a></td>
            </tr>
            <input type="hidden" class="registration-id" data-regis-id="${registration.id}" value="${registration.id}">
          </c:forEach>
          
        </tbody>
      </table>
    </div>

    <!-- Lịch sử thanh toán -->
    <div class="mb-4">
      <h5>Lịch sử thanh toán:</h5>
      <table class="table table-bordered text-center">
        <thead class="table-primary">
          <tr>
            <th>STT</th>
            <th>Mã hóa đơn</th>
            <th>Ngày thanh toán</th>
            <th>Hình thức thanh toán</th>
            <th>Số tiền</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="bill" items="${bills}" varStatus="status">
            <tr></tr>
              <td>${status.count}</td>
              <td>${bill.id}</td>
              <td>${bill.paymentDate}</td>
              <td>${bill.paymentType}</td>
              <td>${bill.amount}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>

    <!-- Trạng thái -->
    <div class="mb-4">
      <h5>Trạng thái:</h5>
      <p>Chưa thanh toán đủ</p>
    </div>
    <!-- Phương thức thanh toán -->
    <div class="mb-4">
      <h5>Phương thức thanh toán:</h5>
      <div class="form-check">
        <input class="form-check-input" type="radio" name="paymentMethod" id="cashPayment" value="Tiền mặt" checked>
        <label class="form-check-label" for="cashPayment">
          Tiền mặt
        </label>
      </div>
      <div class="form-check">
        <input class="form-check-input" type="radio" name="paymentMethod" id="transferPayment" value="Chuyển khoản">
        <label class="form-check-label" for="transferPayment">
          Chuyển khoản
        </label>
      </div>
    </div>
    <!-- Nút Thanh toán và Trở về -->
    <div class="text-center">
      <button class="btn btn-primary me-3" id="confirm-payment">Thanh toán</button>
      <button class="btn btn-secondary">Trở về</button>
    </div>
  </div>

  <!-- Bootstrap 5 JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <script>
    // Add event listener for edit buttons
    document.getElementById('editMember').addEventListener('click', function(e) {
      e.preventDefault();
      const myModal = new bootstrap.Modal(document.getElementById('updateInfoModal'));
      myModal.show();
    });
    // Add event listener for save button in modal
    document.getElementById('saveChanges').addEventListener('click', function(e) {
      e.preventDefault();
      
      // Get form data
      const memberId = document.getElementById('memberId').value;
      const formData = {
        name: document.getElementById('memberName').value,
        phone: document.getElementById('memberPhone').value,
        dob: document.getElementById('memberBirthday').value,
        address: document.getElementById('memberAddress').value
      };

      // Call API to update member info
      fetch(`/api/student-info/edit/` + memberId, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
      })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        // Close modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('updateInfoModal'));
        modal.hide();
        
        // Redirect back to detail page
        window.location.href = '/receptionist/detail-registration';
      })
      .catch(error => {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi cập nhật thông tin. Vui lòng thử lại.');
      });
    });

    document.querySelectorAll('.schedule-registration').forEach(element => {
      element.addEventListener('click', function(event) {
        event.preventDefault();
        const lophocName = this.getAttribute('data-lophoc-name');
        fetchLessonSchedule(lophocName);
      });
    });

    function fetchLessonSchedule(className) {
      $.ajax({
          url: `/api/lesson/cal/` + className,
          type: 'GET',
          success: function(data) {
              var lessonContent = document.getElementById("lessonContent");
              lessonContent.innerHTML = ""; // Clear existing content
              data.forEach((lesson, index) => {
                  var row = document.createElement("tr");

                  var cell1 = document.createElement("td");
                  cell1.innerText = index + 1;
                  row.appendChild(cell1);

                  var cell2 = document.createElement("td");
                  cell2.innerText = lesson.day;
                  row.appendChild(cell2);

                  var cell3 = document.createElement("td");
                  cell3.innerText = lesson.shift.startTime;
                  row.appendChild(cell3);

                  var cell4 = document.createElement("td");
                  cell4.innerText = lesson.shift.endTime;
                  row.appendChild(cell4);

                  var cell5 = document.createElement("td");
                  cell5.innerText = lesson.skill.name;
                  row.appendChild(cell5);

                  lessonContent.appendChild(row);
              });
              console.log(data);
              $('#lessonModal').modal('show');
          },
          error: function(error) {
              console.error('Error fetching lesson schedule:', error);
          }
      });
  }

    document.getElementById('confirm-payment').addEventListener('click', function() {
        // Get all registration IDs from hidden inputs
        const registrationIds = Array.from(document.querySelectorAll('.registration-id')).map(input => {
            return parseInt(input.value);
        });

        // Get selected payment method from radio buttons
        const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;

        // Prepare data for API call
        const data = {
            registrationIds: registrationIds,
            paymentMethod: paymentMethod
        };

        // Call API to confirm payment
        fetch('/api/confirm-payment', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (!response.ok) throw new Error('Network response was not ok');
            return response.json();
        })
        .then(data => {
            // Redirect to search page on success
            window.location.href = '/receptionist/search';
        })
        .catch(error => {
            console.error('Error confirming payment:', error);
            alert('Có lỗi xảy ra khi xác nhận thanh toán. Vui lòng thử lại.');
        });
    });
  </script>
</body>
</html>
