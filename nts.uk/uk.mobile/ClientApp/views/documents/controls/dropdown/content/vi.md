##### 2. Diễn giải
> `nts-dropdown` là một component control hỗ trợ việc bind dữ liệu từ viewmodel lên view thông qua thẻ html `select` dễ dàng và thống nhất trong `UK Mobile`.

###### 2.1 Khai báo view

> Để khai báo một `nts-dropdown` component, ta cần tối thiểu 2 binding: `name` và `v-model`.
- `name` là binding nhận vào một `string`, chỉ định rõ tên của component là gì (*phục vụ cho trường hợp hiển thị nhãn và lỗi*).
- `v-model` là binding liên kết với `model` khai báo trong `view-model`, thông qua binding này, mọi thay đổi giữa `view` và `model` sẽ được đồng bộ.

> **Chú ý:** `nts-dropdown` được render thành `select` tag nên component này sử dụng các tags `option` và `optgroup` tương tự như `select` tag. Chi tiết cách sử dụng xem ví dụ html bên dưới.

```html
<nts-dropdown name="DropDown Item" v-model="selectedValue">
    <option v-for="item in dropdownList" :value="item.code">
        {{item.code}} &nbsp;&nbsp;&nbsp;  {{item.text}}
    </option>
</nts-dropdown>
```

###### 2.1 Khai báo viewmodel

> Diễn giải của viewmodel xem trong comment của ví dụ

```typescript
export class ViewModel extends Vue {
    // model được bind vào nts-dropdown
    selectedValue: number = 3;
    
    // datasource để bind vào các option
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
}
```

##### 3. API

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