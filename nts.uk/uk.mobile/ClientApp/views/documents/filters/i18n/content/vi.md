##### 1. Giới thiệu

> `i18n` là một `filter` được sử dụng để hiển thị một chuỗi ký tự từ `resourceid`, tương tự như hệ thống UK.
> <br /> `i18n` là một hàm, nên cách sử dụng có thể tương tự như gọi một hàm nếu cần truyền tham số vào.

##### 2. Code
```html
<!-- dùng như một filter -->
<div>{{resourceid | i18n}}</div>
<!-- dùng như một hàm global -->
<div>{{ i18n(resourceid, [param1, param2]) }}</div>
<!-- hoặc dùng như một hàm mixin -->
<div>{{ $i18n(resourceid, [param1, param2]) }}</div>
```