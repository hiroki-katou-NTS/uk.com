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