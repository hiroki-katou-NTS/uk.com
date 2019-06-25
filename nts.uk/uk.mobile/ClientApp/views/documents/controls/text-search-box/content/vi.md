##### 2. Giải thích
`text-search-box` là component được sử dụng ở các màn hình tìm kiếm dữ liệu theo danh sách.  
Nó là công cụ để nhập vào text và đẩy ra thông qua 2 event là `input` và `search`.  
- input: được đẩy ra mỗi khi giá trị trong khung nhập thay đổi.
- search: được đẩy ra khi người dùng click vào icon searh <span class="fa fa-search"></span>.


##### 2. Code HTML
Ở HTML, tạo thẻ `text-search-box` với attribute v-on:search ứng với tên 1 function trong file ts
trong trường hợp này là hàm searchList.  

```html
<text-search-box 
    v-on:input="inputEvent"
    v-on:search="searchEvent"/>
```
##### 3. ViewModel
Tại file ts, tạo hàm có tên giống với tên đã khai báo trong file html.   
Hàm này có nhiệm vụ xử lý sự kiện khi người dùng click vào button search hoặc 'enter' của component.

```typescript
@component({
})
export class ViewModel extends Vue {

    // xử lý khi input thay đổi
    public inputEvent(value: string) {
        
    }

    // xử lý khi người dùng click vào icon search
    public searchEvent(value: string) {
        
    }
}
```
> **Chú ý**: Chỉ sử dụng một trong 2 event `search` hoặc `input` tùy vào thiết kế màn hình.

##### 4. Thông tin bổ xung
Ngoài ra, bạn có thể truyền thêm vào component một số các tham số sau.

| Tên Thuộc tính| Type | Mặc định | Mô tả |
| --------------|------| -------- | ------|
| placeholder | string | '' | Ghi chú cho thẻ input (có thể dùng resources id) |
| class-input | string | '' | Class css sẽ gắn vào thẻ input |
| class-container | string | '' | Class css sẽ gắn vào container/thẻ bao của thẻ input |

**Tạo bởi:** Phạm Văn Dân