## 2. Khai báo trong index.html

```html
<nts-number-editor 
    v-model="number" 
    name="Number Item"
    />
```

## 3. Khai báo trong index.ts

Khai báo một biến 'number' trong class chính để bind vào giá trị 'number' trong v-model="number"

```ts
export class ViewModel extends Vue {
    *
    *
    number: Number = '10';
    *
    *
}
```
## 4. Thông tin bổ sung

"nts-number-editor" là một dạng input trong UK-Mobile, vì thế nó có các thuộc tính chung của Input như là: 

| Tên Thuộc tính| Type | Mặc định | Mô tả |
| --------------|------| -------- | ------|
| name | string | '' | Tên hiển thị của item |
| value | any | '' | Giá trị đầu vào của item |
| disabled | boolean | false | Item có bị disable hay không? |
| errors | any | null | ... |
| errorsAlways | any | null | ... |
| constraint | Irule | {} | Định dạng validate cho item |
| item | {before, after} | { before: '', after: ''} | Icon của item |
| columns | {title, input} | {title: 'col-md-12', input: 'col-md-12'} | ... |

Khi khai báo nts-number-editor trong index.html, bạn có thể truyền thêm các tham số này nếu muốn.  

