> **Ghi chú**: Xoá giá trị một trong hai input ở trên để kiểm tra validator `required` có hoạt động không.

##### 2. Giải thích
> Validations là một `plugin` làm nhiệm vụ theo dõi `model` và gọi các `validators` tương ứng với các `validate` được khai báo trong `validations` mỗi khi `model` có sự thay đổi giá trị được `binding` và hiển thị các thông báo lỗi hoặc xoá thông báo lỗi ngay khi nhận được kết quả trả về từ các `validators`.

> Có 2 loại `validator` được sử dụng trong `validations`.
- `fixed validator`: là các `validators` chung được sử dụng thường xuyên trong hệ thống. Các `validators` này do kiban khai báo và quản lý.
- `custom validator`: là các `validators` riêng được sử dụng bởi từng model cụ thể do developer tự khai báo và quản lý.

> `custom validator` có cấu trúc là một đối tượng với 2 thuộc tính như sau:
```typescript
custom_validator: {
    test: Regex;
    message: string;
}
```
hoặc
```typescript
custom_validator: {
    test: (value) {
        // validate code
    };
    message: string;
}
```
> Nếu hàm test trả về giá trị `false` tức là không `validate` thành công (có lỗi), lúc này `message` sẽ được sử dụng để bind lỗi vào model.
> <br />**Chú ý**: Tại một thời điểm, chỉ có một lỗi được bind vào model. Các `validator` sẽ được gọi theo thứ tự khai báo, không phân biệt `fixed validator` hay `custom validator`.

**HTML Code:**
```html
<nts-text-editor
    name='username'
    v-model="model.username" />
<nts-input-password
    name='password'
    v-model="model.password" />
```

**Typescript code:**
```typescript
import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    // khai báo cấu trúc validators tương ứng với model
    validations: {
        model: {
            username: {
                // fixed validator
                required: true,
                // custom validator
                custom_validator: {
                    test: /\d{3,5}/,
                    message: '{0} is number in range [100, 99999].'
                }
            },
            password: {
                // fixed validator
                required: true
            }
        }
    }
})
export class DocumentsPluginsValidationComponent extends Vue {
    // khai báo cấu trúc model tương ứng với validators
    public model: {
        username: string;
        password: string;
    } = {
        username: 'username',
        password: 'password'
    };
}
```
> **Chú ý**: Các `custom validator` có tên được đặt tuỳ ý (*chỉ dùng để phân biệt*).

##### 3. API

**Fixed validators:**
Validator | type/params | Giải thích
----|----|---------------------
required | `true` \| `false` | `Validator` xử lý `validate` `require` của `model`. Nếu kết quả `validator` trả về là `false` thì sẽ gắn `message`: `MsgB_1` vào `model`.
min | `number` | `Validator` xử lý `validate` giá trị `min` của một số. Nếu giá trị của model nhỏ hơn giá trị min được khai báo thì sẽ gắn `message`: `Msg_` vào `model`.
max | `number` | Tương tự `validator` `min`.
... | ... | ...

**Custom validators:**
Validator | type/params | Giải thích
----|----|---------------------
test | `Regex` \| `Function` | `Custom validator` xử lý `validate` được chỉ định bởi developer của `model`. Nếu kết quả `validator` trả về là `false` thì sẽ gắn `message` vào `model`.
message | `string` | Message hoặc MessageId được gắn vào model khi có lỗi xảy ra (không thoả mãn yêu cầu `test`).

