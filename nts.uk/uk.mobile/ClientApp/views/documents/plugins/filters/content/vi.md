##### 2. Diễn giải
> Giống như `i18n`, một `filter` được sử dụng để hiển thị văn bản dựa vào `resource_id` được cung cấp bởi đội thiết kế. UK mobile cũng sử dụng một số filter như `date` (*Ngày tháng*), `timept` (*Thời điểm*), `timedr` (*Khoảng thời gian*), `timewd` (*Khoảng thời gian theo ngày*) để hiển thị các giá trị này trên view. Developer cần chỉ định `filter` tương ứng với giá trị cần hiển thị thì giá trị hiển thị sẽ được hiển thị đúng với format của hệ thống.

> *Các kiểu giá trị* tương ứng với các `filter`:
- `date`: `Date`
- `timept`: `number`
- `timedr`: `number`
- `timewd`: `number`

**HTML Code:**
```html
<div class="sample">
    <span>{{ new Date() | date }}</span>
    <!-- kết quả dạng: 2019/01/01 -->
    <span>{{ new Date() | date('dd-mm-yyyy') }}</span>
    <!-- kết quả dạng: 01-01-2019 -->
    <span>{{ 720 | timept }}</span>
    <!-- kết quả dạng: 12:00 -->
    <span>{{ 720 | timedr }}</span>
    <!-- kết quả dạng: 12:00 -->
    <span>{{ 720 | timewd }}</span>
    <!-- kết quả dạng: 当日 12:00 -->
</div>
```
<div class="mt-2"></div>

> Viết bởi: **Nguyễn Văn Vương**.
<div class="mb-2"></div>