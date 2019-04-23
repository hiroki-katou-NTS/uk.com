# Toolbar
> Khái niệm `toolbar` (thanh công cụ) ở đây được hiểu là một `directive` chỉ định một thẻ `nav` hoặc thẻ `div` bất kỳ trong `template` của `view/component` được phép chuyển đổi thành `toolbar` thay thế cho `toolbar` mặc định của hệ thống.<br />Để tạo `toolbar` (nơi đặt các nút Thêm, Lưu, Sửa, Xoá, Tìm kiếm,...) ta sử dụng directive `v-toolbar` ở thẻ `nav` hoặc `div` được chỉ định.<br />Khi directive `v-toolbar` được gọi, `toolbar` mặc định của hệ thống sẽ được ẩn đi mà không cần điều chỉnh gì thêm.

##### Ví dụ về một `toolbar`
<div class="navbar bg-primary" style="padding-left: 15px">
    <div class="row">
        <div class="col-md-9">
            <button type="button" class="btn btn-link"><i class="fa fa-save"></i></button>
            <button type="button" class="btn btn-link">Middle</button>
            <button type="button" class="btn btn-link">Right</button>
        </div>
        <div class="col-md-3">
            <div class="input-group input-group-transparent input-group-search">
                <div class="input-group-append">
                    <span class="input-group-text fa fa-search"></span>
                </div>
                <input type="text" class="form-control" />
            </div>
        </div>
    </div>
</div>
<br />

##### Code
```html
<div v-toolbar>
    <div class="row">
        <div class="col-md-9">
            <button type="button" class="btn btn-link">
                <i class="fa fa-save"></i>
            </button>
            <button type="button" class="btn btn-link">Middle</button>
            <button type="button" class="btn btn-link">Right</button>
        </div>
        <div class="col-md-3">
            <div class="input-group input-group-transparent input-group-search">
                <div class="input-group-append">
                    <span class="input-group-text fa fa-search"></span>
                </div>
                <input type="text" class="form-control" />
            </div>
        </div>
    </div>
</div>
```