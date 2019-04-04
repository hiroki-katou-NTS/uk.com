## 2. Khai báo trong index.html

```html
<nts-time-editor
    v-model="time"
    :name="'Time-With-Day'"
    time-input-type="time-with-day"
    v-bind:constraint="validations.time"/>

<nts-time-editor
    v-model="time"
    :name="'Time-Point'"
    time-input-type="time-point"
    v-bind:constraint="validations.time"/>

<nts-time-editor
    v-model="time"
    :name="'Time-Duration'"
    time-input-type="time-duration"
    v-bind:constraint="validations.time"/>      
```

## 3. Khai báo trong index.ts

Khai báo một biến time trong class chính để bind vào giá trị 'time' trong v-model="time"

```ts
export class ViewModel extends Vue {
    *
    *
    time: number = 750;
    *
    *
}
```

Thêm một biến vào thuộc tính validations trong @component nếu muốn hạn chế giá trị đầu vào.

```ts
@component({
    *
    *
    validations: {
        time: {
            minValue: -720,
            maxValue: 4319
        }
    }
    *
    *
})

```

## 4. Thông tin bổ sung

"nts-time-editor" là một dạng input trong UK-Mobile, vì thế nó có các thuộc tính chung của Input như là: 

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

Khi khai báo nts-time-editor trong index.html, bạn có thể truyền thêm các tham số này nếu muốn.  
Ngoài ra, bạn cần truyền tham số 'timeInputType' là một trong 3 loại 'time-with-day', 'time-point', 'time-duration' để xác định dữ liệu sẽ được hiển thị và sửa đối như thế nào.

| TimeInputType| Mô tả |
| -- | --|
| time-with-day | Loại hiển thị có ngày, giờ và phút. (Ví dụ 当日 12: 30)|
| time-point | Loại hiển thị thời điểm trong ngày với giờ và phút. (Ví dụ 12:00) |
| time-duration | Loại hiển thị khoảng thời gian với giờ và phút. (Ví dụ 54:23) |

Tham số 'timeInputType' có giá trị mặc định là 'time-with-day'
