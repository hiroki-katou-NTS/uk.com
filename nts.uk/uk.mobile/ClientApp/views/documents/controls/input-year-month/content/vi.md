## 2. Khai báo trong index.html

```html
<nts-year-month
      v-model="yearMonth"
      name="対象月"
    />
```

## 3. Khai báo trong index.ts

Khai báo một biến 'yearMonth' trong class chính để bind vào giá trị 'yearMonth' trong v-model="yearMonth"  
Giá trị của yearMonth là kiểu string, có dạng 'yyyymm'.

```ts
export class ViewModel extends Vue {
    *
    *
    yearMonth: string = '201905';
    *
    *
}
```
## 4. Thông tin bổ sung

"nts-year-month" là một dạng input trong UK-Mobile, vì thế nó có các thuộc tính chung của Input như là: 

| Tên Thuộc tính| Type | Mặc định | Mô tả |
| --------------|------| -------- | ------|
| name | string | '' | Tên hiển thị của item |
| showTitle | boolean | true | Có hiển thị title cùng input hay không? |
| inlineTitle | boolean | false | Constraint có hiển thị cùng một dòng mới title hay không? |
| value | any | '' | Giá trị đầu vào của item |
| disabled | boolean | false | Item có bị disable hay không? |
| errors | any | null | ... |
| errorsAlways | any | null | ... |inlineTitle 
| constraint | Irule | {} | Định dạng validate cho item |
| icons | {before, after} | { before: '', after: ''} | Icon của item |
| columns | {title, input} | {title: 'col-md-12', input: 'col-md-12'} | Định dạng độ rộng của title và input. col-md-12 là mặc định, có nghĩa là title và input sẽ có độ rộng full chiều ngang của màn hình |

Khi khai báo nts-year-month trong index.html, bạn có thể truyền thêm các tham số này nếu muốn.  

