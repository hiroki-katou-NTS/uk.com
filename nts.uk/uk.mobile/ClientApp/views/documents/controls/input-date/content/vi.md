## 2. Khai báo trong index.html

```html
<nts-date-input
        v-model="date"
        name='DateItem'/>
```
## 3. Khai báo trong index.ts
Khai báo một biến 'date' trong ViewModel để bind vào giá trị 'date' sử dụng ở v-model="date"

```ts
export class ViewModel extends Vue {
    *
    *
    date: Date = new Date();
    *
    *
}
```

## 4. Thông tin bổ sung

"nts-date-input" là một dạng input trong UK-Mobile, vì thế nó có các thuộc tính chung của Input như là: 

| Tên Thuộc tính| Type | Mặc định | Mô tả |
| --------------|------| -------- | ------|
| name | string | '' | Tên hiển thị của item |
| disabled | boolean | false | Item có bị disable hay không? |
| showTitle | boolean | true | Có hiển thị title cùng input hay không? |
| inlineTitle | boolean | false | Constraint có hiển thị cùng một dòng mới title hay không? |
| columns | {title, input} | {title: 'col-md-12', input: 'col-md-12'} | Điều chỉnh để title và input hiển thị cùng một dòng. (Ví dụ: { title: 'col-md-6', input: 'col-md-6'})|