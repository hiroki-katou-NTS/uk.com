## 2. Khai báo trong index.html

```html
<nts-text-editor
    v-model="text"
    :name="'Text Item'"
    :disabled="false"
    :show-title="true"
    :errors-always="$errors.title"
    :columns="{title: 'col-md-2', 
                input: 'col-md-4'}"
/>
```

## 3. Khai báo trong index.ts

Khai báo một biến 'text' trong class chính để bind vào giá trị 'text' trong v-model="text"

```ts
export class ViewModel extends Vue {
    *
    *
    text: string = 'Init Value';
    *
    *
}
```
## 4. Thông tin bổ sung

"nts-text-editor" là một dạng input trong UK-Mobile, vì thế nó có các thuộc tính chung của Input như là: 

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

Khi khai báo nts-text-editor trong index.html, bạn có thể truyền thêm các tham số này nếu muốn.  

