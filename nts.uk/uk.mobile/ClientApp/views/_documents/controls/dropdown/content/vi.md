## 2. Khai báo trong index.html

```html
<nts-dropdown
    v-model="selectedValue"
    name='DropDown Item'>

    <option v-for="item in dropdownList" :value="item.code">
        {{item.code}} &nbsp;&nbsp;&nbsp;  {{item.text}}
    </option>

</nts-dropdown>
/>
```

Ở html, bạn khai báo 'nts-dropdown' với 2 thuộc tính quan trọng là v-model="selectedValue" và name="DropDown Item".
"selectedValue" là một thuộc tính được khai báo trong file class chính, "Drop Item" là tên của item bạn muốn đặt.  
      
Ở giữa 2 thẻ 'nts-dropdown', bạn định nghĩ cái muốn hiển thị trong dropdown.
Dùng thẻ 'option' với v-for để tạo ra danh sách option.

## 3. Khai báo trong index.ts

Khai báo một biến 'selectedValue' trong class chính để bind vào giá trị 'selectedValue' trong v-model="selectedValue"   

Khai báo một mảng dropdownList trong class chính.

```ts
export class ViewModel extends Vue {
    *
    *
    selectedValue: number = 3;
    
    dropdownList: Array<Object> = [{
        code: 1,
        text: "The First"
    }, {
        code: 2,
        text: "The Second"
    }, {
        code: 3,
        text: "The Third"
    }, {
        code: 4,
        text: "The Fourth"
    },{
        code: 5,
        text: "The Fifth"
    }];
    *
    *
}
```
## 4. Thông tin bổ sung

"nts-dropdown" là một dạng input trong UK-Mobile, vì thế nó có các thuộc tính chung của Input như là: 

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
| columns | {title, input} | {title: 'col-md-12', input: 'col-md-12'} | ... |

Khi khai báo "nts-dropdown" trong index.html, bạn có thể truyền thêm các tham số này nếu muốn.  

