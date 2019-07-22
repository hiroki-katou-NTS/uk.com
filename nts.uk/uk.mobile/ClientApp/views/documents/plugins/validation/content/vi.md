##### 2. Validation là gì?

`Validation` là một `plugin` do team kiban tạo ra nhằm đảm bảo việc validate dữ liệu nhập vào.  
Để sử dụng plugins này, tạo thuộc tính `validations` trong `@Component` 
và khai báo trong đó những thuộc tính có tên trùng với tên của các biến cần được validate.

```typescript
@component({
    validations: {
        textValue: {
            required: true,
        },
        numberValue: {
            required: true
        }
    }
})
export class ViewModel extends Vue {
    
    public textValue: string = 'nittsu';

    public numberValue: number = 1;

}
```
Ở ví dụ trên, 'textValue' và 'numberValue' được khai báo required là true. Nếu xóa dữ liệu của 2 biến này, plugin Validation sẽ báo lỗi.


Có 2 kiểu validate là:
- `fixed validate`: là kiểu validate phổ biến được định nghĩa sẵn. Ví dụ: required, min, max.
- `custom validate`: là kiểu validate đặc biệt, do developer tự định nghĩa.


##### 3. Fixed validators
Validator | type | Mặc định |Giải thích
----|----|---------| ------------
validate | Boolean | true | Có validate hay không? 
required | Boolean | false | Có require hay không? 
min | Number | undefined | Giá trị nhỏ nhất của biến
max | Number | undefined | Giá trị lớn nhất của biến
charType | String | undefined |Validate giá trị text nhập vào. CharType là một trong số: Kana, AnyHalfWidth, AlphaNumeric, Numeric, Any
dateRange | Boolean | false |Dữ liệu có phải kiểu date-range hay không?
timeRange | Boolean | false |Dữ liệu có phải kiểu time-range hay không?


##### 4. Custom validators
Ví dụ để validate biến `textValue` ở trên ta khai báo trong thuộc tính `validations` như sau:  
```typescript
textValue: {
    test: Regex;
    message: string;
}
```
hoặc 
```typescript
textValue: {
    test: (value) {
        // validate code
    };
    message: string;
}
```
Hàm test trả về giá trị `false` nghĩa là có lỗi, lúc này message báo lỗi sẽ hiện lên.  
Tại một thời điểm, chỉ có một lỗi được bind vào model. Việc validate sẽ được thực hiện lần lượt theo thứ tự được khai báo, không phân biệt `fixed validator` hay `custom validator`.
##### 4. Update validators
> Trong quá trình runtime, có những model cần cập nhật lại validator hoặc loại bỏ một/một vài validator. Lúc này, dev chỉ cần sử dụng hàm `$updateValidator` để cập nhật lại.
<br />Sau quá trình cập nhật, cần gọi lại hàm `$validate` để validate lại model (có thể validate chỉ riêng model vừa cập nhật bằng cách truyền path vào hàm $validate).

**Sample**:
```typescript
import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    validations: {
        checked: {
            required: true
        }
    }
})
export class ViewModelSample extends Vue {
    public checked: boolean | null = null;

    public updateValidator() {
        // cập nhật validator cho model: checked
        this.$updateValidator('checked', { validate: false });

        // gọi lại hàm validate để validate
        // model vừa được cập nhật validator
        this.$validate('checked');
    }
}
```
> Kết quả của đoạn ví dụ trên là validations sẽ bỏ qua model checked, không gọi các validator đã khai báo để validate model này nữa.


> Document writer: **Nguyễn Văn Vương**

<div class="mb-3 mt-3"></div>