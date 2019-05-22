##### 2. Khai báo trong index.html

```html
    <text-search-box 
        placeholder="勤務種類コード/勤務種類名称"
        v-on:search="searchList"/>
```

Ở HTML, tạo thẻ <text-search-box> với attribute v-on:search ứng với tên 1 function trong file ts
trong trường hợp này là hàm searchList.  
  
Thêm vào placeholder nếu muốn, placeholder mặc định là kí tự rỗng ''

##### 3. Khai báo trong index.ts

Tại file ts, tạo hàm có tên giống với tên đã khai báo trong file html.   
Hàm này có nhiệm vụ xử lý sự kiện khi người dùng click vào button search hoặc 'enter' của component.
Nếu người dùng chưa nhập, giá trị trả ra sẽ là 'null'.

```ts
export class ViewModel extends Vue {
    *
    *
    public searchList(value: string) {
        // process the event 'search'
    }
    *
    *
}
```