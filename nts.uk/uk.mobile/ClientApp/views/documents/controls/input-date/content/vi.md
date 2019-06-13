## 2. Khai báo trong index.html

```html
<nts-date-input
        v-model="date"
        :name="'DateItem'"/>
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
| showTitle | boolean | true | Có hiển thị title cùng input hay không? |
| showConstraint | boolean | true | Có hiển thị constraint cùng title hay không? |
| inlineTitle | boolean | false | Constraint có hiển thị cùng một dòng mới title hay không? |
| value | any | '' | Giá trị đầu vào của item |
| disabled | boolean | false | Item có bị disable hay không? |
| errors | any | null | ... |
| errorsAlways | any | null | ... |inlineTitle 
| constraint | Irule | {} | Định dạng validate cho item |
| icons | {before, after} | { before: '', after: ''} | Icon của item |
| columns | {title, input} | {title: 'col-md-12', input: 'col-md-12'} | ... |