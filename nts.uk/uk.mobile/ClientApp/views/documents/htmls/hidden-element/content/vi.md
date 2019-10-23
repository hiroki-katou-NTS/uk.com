##### 1. Giải thích
> Đôi khi, trong quá trình hoạt động của trang. Ta cần ẩn một hoặc một vài đối tượng của trang web đi. Ví dụ, ẩn button xoá khi người dùng đang đăng nhập không có quyền xoá. Ta sử dụng class `.d-none` để ẩn button này đi.

- Dưới đây là bảng các class ẩn đối tượng tuỳ theo từng thiết bị cụ thể:

Screen Size |	Class
----|----
Hidden on all |	`.d-none`
Hidden only on xs |	`.d-none .d-sm-block`
Hidden only on sm |	`.d-sm-none .d-md-block`
Hidden only on md |	`.d-md-none .d-lg-block`
Hidden only on lg |	`.d-lg-none .d-xl-block`
Hidden only on xl |	`.d-xl-none`
Visible on all | `.d-block`
Visible only on xs | `.d-block .d-sm-none`
Visible only on sm | `.d-none .d-sm-block .d-md-none`
Visible only on md | `.d-none .d-md-block .d-lg-none`
Visible only on lg | `.d-none .d-lg-block .d-xl-none`
Visible only on xl | `.d-none .d-xl-block`
