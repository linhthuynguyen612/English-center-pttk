$(document).ready(() => {
    const addToCartBtn = document.getElementById("add-to-cart"); // Nút thêm vào giỏ hàng
    const registerNowBtn = document.getElementById("register-now"); // Nút đăng ký ngay

    fetchLevels()
    $('#programSelect').change(() => {
        fetchLevels();
    });

    $('#levelSelect').change(() => {
        fetchCourses();
    });

    function fetchLevels() {
        var select = document.getElementById("programSelect");
        var selectedOption = select.options[select.selectedIndex];
        var courseName = selectedOption.value;

        $.ajax({
            url: `/api/course/` + courseName +`/level`,
            type: 'GET',
            success: function(data) {
                var levelSelect = document.getElementById("levelSelect");
                levelSelect.innerHTML = ""; // Clear existing options
                data.forEach(level => {
                    var option = document.createElement("option");
                    option.text = level.name;
                    levelSelect.add(option);
                });
                console.log(data);
            },
            error: function(error) {
                console.error('Error fetching levels:', error);
            }
        });
    }
    function fetchCourses() {
        // Get selected level and program
        var levelSelect = document.getElementById("levelSelect");
        var levelName = levelSelect.options[levelSelect.selectedIndex].value;

        var programSelect = document.getElementById("programSelect");
        var courseName = programSelect.options[programSelect.selectedIndex].value;

        $.ajax({
            url: `/api/course/` + courseName + `/` + levelName + `/class`,
            type: 'GET',
            success: function (data) {
                // Check if table-content and tbody exist
                var tableBody = document.getElementById("table-content").getElementsByTagName('tbody')[0];
                if (!tableBody) {
                    console.error("Table body not found");
                    return;
                }

                // Clear existing rows
                tableBody.innerHTML = "";

                // Populate rows with new data
                data.forEach((lophoc, index) => {
                    var row = document.createElement('tr');

                    var cell1 = document.createElement('td');
                    cell1.innerText = index + 1;
                    row.appendChild(cell1);

                    var cell2 = document.createElement('td');
                    cell2.innerText = lophoc.name;
                    row.appendChild(cell2);

                    var cell3 = document.createElement('td');
                    cell3.innerText = 'Lịch học';
                    cell3.style.cursor = 'pointer';
                    cell3.style.textDecoration = 'underline';
                    cell3.style.color = 'blue';
                    cell3.onclick = function() {
                        fetchLessonSchedule(lophoc.name);
                    };
                    row.appendChild(cell3);

                    var cell4 = document.createElement('td');
                    cell4.innerText = new Date(lophoc.startDate).toLocaleDateString();
                    row.appendChild(cell4);

                    var cell5 = document.createElement('td');
                    cell5.innerText = lophoc.format || 'Offline'; // Use format if available, default to 'Offline'
                    row.appendChild(cell5);

                    var cell6 = document.createElement('td');
                    var checkbox = document.createElement('input');
                    checkbox.type = 'checkbox';
                    checkbox.value = lophoc.id;
                    cell6.appendChild(checkbox);
                    row.appendChild(cell6);

                    tableBody.appendChild(row);
                });
                console.log(data);
            },
            error: function (error) {
                console.error('Error fetching classes:', error);
            }
        });
    }

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

    addToCartBtn.addEventListener("click", function () {
        const selectedClasses = getSelectedClasses(); // Lấy danh sách các lớp được chọn
        if (selectedClasses.length > 0) {
            console.log("Thêm vào giỏ hàng:", selectedClasses);
            callApi('/add-to-cart', selectedClasses); // Gọi API tương ứng
        } else {
            alert("Vui lòng chọn ít nhất một lớp để thêm vào giỏ hàng!");
        }
    });

    // Xử lý nút "Đăng ký ngay"
    registerNowBtn.addEventListener("click", function () {
        const selectedClasses = getSelectedClasses(); // Lấy danh sách các lớp được chọn
        if (selectedClasses.length > 0) {
            console.log("Đăng ký ngay:", selectedClasses);
            callApi('/register-now', selectedClasses, "/student/confirm-registration");
        } else {
            alert("Vui lòng chọn ít nhất một lớp để đăng ký!");
        }
    });


    // Hàm lấy danh sách các lớp học được chọn
    function getSelectedClasses() {
        const checkboxes = document.querySelectorAll("input[type='checkbox']:checked"); // Lấy tất cả checkbox được tích
        const selectedClasses = [];
        checkboxes.forEach((checkbox) => {
            const row = checkbox.closest("tr"); // Lấy hàng chứa checkbox
            const className = row.querySelector("td:nth-child(2)").innerText; // Lấy tên lớp (cột thứ 2)
            selectedClasses.push(className); // Thêm tên lớp vào danh sách
        });
        return selectedClasses;
    }

    async function callApi(endpoint, data, redirectUrl = null) {
        try {
            const response = await $.ajax({
                url: endpoint,
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ classes: data }), // Gửi danh sách các lớp học
            });
            console.log(`API "${endpoint}" gọi thành công:`, response);
            console.log("re:", redirectUrl);
            if (redirectUrl) {
                console.log(`Chuyển hướng đến: ${redirectUrl}`);
                setTimeout(function() {
                    window.location.href = redirectUrl; // Chuyển hướng sau khi alert được đóng
                }, 100); // Đợi 100ms sau khi alert được hiển thị
            }
            // alert("Thao tác thành công!");

        } catch (error) {
            console.error(`API "${endpoint}" lỗi:`, error);
            alert("Có lỗi xảy ra, vui lòng thử lại.");
        }
    }




    $(document).on('click', '.schedule-registration', function(event) {
        event.preventDefault();
        const lophocName = $(this).data('lophoc-name');
        fetchLessonSchedule(lophocName);
    });

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
                    cell2.innerText = lophoc.level.course.name;
                    row.appendChild(cell2);

                    var cell3 = document.createElement("td");
                    cell3.innerText = lophoc.level.name;
                    row.appendChild(cell3);

                    var cell4 = document.createElement("td");
                    cell4.innerText = lophoc.name;
                    row.appendChild(cell4);

                    var cell5 = document.createElement("td");
                    cell5.innerText = new Date(lophoc.startDate).toLocaleDateString('vi-VN', {day: '2-digit', month: '2-digit', year: 'numeric'});
                    row.appendChild(cell5);

                    var cell6 = document.createElement("td");
                    var link = document.createElement("a");
                    link.href = "#";
                    link.className = "schedule-registration";
                    link.setAttribute('data-lophoc-name', lophoc.name);
                    link.innerText = "Lịch học";
                    cell6.appendChild(link); // Thiếu dòng này - cần thêm link vào cell6
                    row.appendChild(cell6);

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