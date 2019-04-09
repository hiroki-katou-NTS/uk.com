##### 1. Định nghĩa
> `Decorator` (hàm trang trí, hàm chạy trước) là một mẫu thiết kế được đưa ra để thêm, bớt hoặc thay đổi thuộc tính của một đối tượng nào đó. Cách làm này trái với khái niệm kế thừa trong OOP vì đôi khi kế thừa làm cho đối tượng trở nên rối hơn và nhiều vấn đề cần giải quyết hơn mức cần thiết.
> <br />Ví dụ đơn giản: Nếu mọi người sử dụng cấu trúc `component` option mặc định của `vuejs` hẳn mọi người sẽ rất bối rối khi lấy `data` từ `methods` thông qua từ khoá `this`. Vấn đề này tôi sẽ bàn kỹ hơn vào một thời điểm thích hợp.
> <br />Cụ thể hơn trong trường hợp này, `decorator` được sử dụng để tách `view` ra độc lập hơn với `view-model`, tương tự như vậy, `style` sẽ được tách rời khỏi `view` hoặc `single file template` của vue để chúng ta code vue component theo đúng thiết kế của mô hình MVVM. Đồng thời `decorator` cũng giúp cho việc gộp các computed, methods, data vào một `class` duy nhất để chúng ta không nhầm lẫn trong quá trình code. Thêm nữa là chúng ta code bằng typescript sẽ rành mạch hơn.

```typescript
// import decorator (bắt buộc)
import { component } from '@app/core/component';

// khai báo decorator
@component({
    // đường link url được sử dụng để tải component
    path: `/sample`,
    // template của component
    template: require('./index.html')
    // style của component (chấp nhận css/scss)
    style: require('./style.scss'),
    // thuộc tính name của component (không bắt buộc)
    // sẽ được dùng thay cho className nếu như được khai báo
    name: 'name_of_component',
    // các validate áp được áp dụng cho model khai báo trong view-model
    // cấu trúc khai báo bắt buộc phải giống model trong view-model
    validations: {
        [model: string]: any
    }
})
```