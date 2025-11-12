# Trò chơi Arkanoid - Bài tập lớn môn Lập trình hướng đối tượng

## Tác giả

Nhóm 2 - Lớp INT2204_11

1. Nguyễn Duy Hòa - 24021482
2. Lê Trung Hiếu - 24021474
3. Nghiêm Thành Long - 24021554

**Giảng viên hướng dẫn**: Kiều Văn Tuyên
**Học kì**: Học kì 1 - Năm học 2025-2026

---

## Mô tả

Đây là bài tập lớn môn Lập trình hướng đối tượng về game Arkanoid được phát triển bằng ngôn ngữ Java. Bài tập lớn này thể hiện việc thực hiện các nguyên tắc OOP và các mẫu thiết kế

**Tính năng chính:**

1. Game được phát triển sử dụng Java 24+ với GUI JavaFX
2. Implements core OOP principles: Encapsulation, Inheritance, Polymorphism, and Abstraction.
3. Applies multiple design patterns: Singleton, Factory Method, Strategy, Observer, and State.
4. Features multithreading for smooth gameplay and responsive UI.
5. Bao gồm hiệu ứng âm thanh, nhạc nền, hiệu ứng hình ảnh và hệ thống vật phẩm tăng sức mạnh
6. Có hệ thống bảng xếp hạng

**Cơ chế game**

- Điều khiển bảng đỡ bóng để phá hủy các viên gạch
- Thu thập các vật phẩm đặc biệt để tăng khả năng chiến thắng màn chơi
- Có các map khác nhau với độ khó ngẫu nhiên
- Ghi càng nhiều điểm, vượt qua càng nhiều màn để đứng đầu trên bảng xếp hạng

---

## UML Diagram

### Class Diagram

![[docs/uml/arkanoid_UML.png]]

Xem chi tiết tại ![[docs/uml/arkanoid_fullUMLL.png]]

---

## Mẫu thiết kế

### 1. Singleton Pattern

**Sử dụng:** `DatabaseManager`, `MusicManager`

**Mục đích:** Đảm bảo có 1 phiên bản Manager và Music tồn tại trong toàn bộ game

### 2. Prototype

**Sử dụng:** `Ball`

**Mục đích:** Tạo bản sao của Ball để thuận tiện tạo chức năng


---

## Đa luồng


---

## Cách cài đặt

1. Clone repository trên Github
2. Mở project trên IDE
3. Cài đặt JDK phiên bản 24, cài đặt JavaFX phiên bản 25
4. Build và chạy chương trình

## Hướng dẫn

### Điều khiển

1. **Mode Solo**

| Phím    | Hành động                   |
| ------- | --------------------------- |
| `←`     | Di chuyển bảng đỡ sang trái |
| `→`     | Di chuyển bảng đỡ sang phải |
| `SPACE` | Phát bóng                   |
| `P`     | Tạm dừng game               |
| `R`     | Khởi động lại game          |

1. **Mode Duel**

| Phím    | Hành động                                |
| ------- | ---------------------------------------- |
| `←`     | Di chuyển bảng đỡ người chơi 1 sang trái |
| `→`     | Di chuyển bảng đỡ người chơi 1 sang phải |
| `A`     | Di chuyển bảng đỡ người chơi 2 sang trái |
| `D`     | Di chuyển bảng đỡ người chơi 2 sang phải |
| `SPACE` | Phát bóng                                |
| `P`     | Tạm dừng game                            |
| `R`     | Khởi động lại game                       |


### Cách chơi

1. **Bắt đầu chơi:** bấm chọn Start ở Menu
2. **Điều khiển bảng đỡ:** 
	- Với chế độ Solo: sử dụng nút `→` hoặc `←`
	- Với chế độ Duel:
		- Người chơi số 1: sử dụng nút `→` hoặc `←`
		- Người chơi số 2: sử dụng nút `A` hoặc `D`
3. **Phát bóng:** sử dụng `SPACE` để phát bóng
4. **Phá hủy gạch:** sử dụng bóng va chạm với gạch để phá hủy
5. **Thu thập vật phẩm:** dùng bảng đỡ nhặt các vật phẩm rơi xuống
6. **Không để mất bóng:** không để bóng rơi dưới bảng đỡ
7. **Hoàn thành màn:** phá hủy tất cả các viên gạch để chuyển màn

### Vật phẩm

| Biểu tượng | Tên           | Hiệu ứng                         |
| ---------- | ------------- | -------------------------------- |
| 🟩         | Expand Paddle | Tăng kích thước bảng đỡ trong 5s |
| 🟥         | Shrink Paddle | Giảm kích thước bảng đỡ trong 5s |
| 🟦         | Double Ball   | Nhân đôi bóng trong 5s           |
| 🩷         | Health        | Tăng mạng                        |


### Cách tính điểm

- Green Brick, Yellow Brick, Maroon Brick: 10 điểm
- Two-hit Brick: 20 điểm
- Double-ball Brick: 30 điểm
- Healing Brick: 10 điểm
- Qua mỗi 1 màn chơi: 100 điểm

---

## Demo

### Ảnh chụp

**Main Menu**  
![[docs/demo/arkanoid_mainMenu.png]]

**Gameplay**
![[docs/demo/arkanoid_gamePlay.png]]
**Power-ups**
1. Double Ball
![[docs/demo/ark_doubleBall.png]]
2. Expand Paddle
![[docs/demo/ark_expandPaddle.png]]
3. Shrink Paddle
![[docs/demo/ark_shrink.png]]
**Leaderboard** 
![[docs/demo/arkanoid_leaderboard.png]]
### Video Demo

Video đầy đủ tại link: 
https://drive.google.com/file/d/15wQs4erYB14BYwDr8mUzzqdB3D9ATOgI/view?usp=sharing

---

## Kế hoạch phát triển


### Tính năng dự kiến

1. **Thêm lựa chọn mode**
    - Mode Story cho phép người chơi chọn màn
    - Mode boss cho phép người chơi đánh quái ở màn cuối
2. **Cải thiện về Gameplay**
    - Nhiều vật phẩm giúp trải nghiệm chơi thú vị hơn: bóng xuyên gạch, bảng đỡ có đạn bắn
	- Nhiều loại gạch hơn, ví dụ gạch không thể phá vỡ, gạch nổ
	- Nhiều map chơi hơn, tăng độ khó và thử thách
3. **Cải thiện kĩ thuật**
	- Tính năng login có mật khẩu
	- Cải thiện thêm về mặt đồ họa

---

## Công nghệ được sử dụng

| Công nghệ | Phiên bản | Vai trò                        |
| --------- | --------- | ------------------------------ |
| Java      | 24+       | Ngôn ngữ phát triển            |
| JavaFX    | 19.0.2    | Framework giao diện người dùng |
| Maven     | 3.9+      | Quản lý dự án và các phụ thuộc |


---

## Giấy phép

Dự án sử dụng cho mục đích giáo dục

**Liêm chính học thuật:** Dự án được cung cấp như một tài liệu tham khảo. Vui lòng tuân theo các tiêu chí liêm chính học thuật của cơ sở giáo dục

---

## Ghi chú

- Trò chơi được phát triển là một phần của môn học Lập trình hướng đối tượng với Java
- Các đoạn mã được viết bởi các thành viên dưới sự hướng dẫn
- Các nội dung hình ảnh, âm thanh được sử dụng với mục đích học thuật
- Bài tập lớn minh họa thực tế cách ứng dụng các nguyên tắc hướng đối tượng và mẫu thiết kế

---

Cập nhật cuối: 12/11/2025
