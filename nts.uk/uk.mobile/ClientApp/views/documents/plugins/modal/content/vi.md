##### 2. Hộp hội thoại
###### 2.1 Giải thích
`Modal` là một `plugin` hỗ trợ để hiển thị một component dưới dạng một dialog.  
Để khởi tạo một modal, hãy gọi method `$modal(component, params?, option?)`.
- `component` là một string hoặc một Component để chỉ định nội dung hiển thị trên dialog.
- `params` sử dụng để đẩy dữ liệu từ viewmodel (màn hình cha) sang component. Thuộc tính này sẽ chỉ đẩy dữ liệu lên *một lần duy nhất*. Để đồng bộ dữ liệu nhiều lần, hãy sử dụng `state` hoặc `eventBus`.
- `option` là thuộc tính sử dụng để cài đặt các kiểu hiển thị và tiêu đề cho modal.  


Tham số thứ 3 `option` là 1 Object có dạng:
```typescript 
declare interface IModalOptions {
    type?: 'modal' | 'dropback';
    title?: string;
    animate?: 'up' | 'right' | 'down' | 'left';
    opacity?: number;
}
```

- `type`: là dạng hiển thị của modal. `modal` sẽ hiển thị full màn hình, trong khi `dropback` hiển thị che bớt một phần màn hình chính.
- `title`: trong trường hợp nội dung bên trong Modal-Component không có thẻ có class là `modal-header`, `title` truyền vào sẽ được hiển thị ở header của modal.
- `animate`: định nghĩa cách modal xuất hiện. Mặc định sẽ là từ bên phải sang(`right`).
- `opacity`: ( chỉ cần thiết trong trường hợp type=`"dropback"`.) Thuộc tính này định nghĩa độ sáng của màn hình chính chưa bị che. sáng 0<=opacity<=1 tối.

> Chú ý: Phương thức $modal trả về một đối tượng Promise nên ta có thể sử dụng hàm then để thực thi đồng bộ các thao tác dữ liệu được trả về từ $modal.

###### 2.2. Code TypeScript
```typescript
@component({
    ...
    components: {
        // khai báo modal
        'sample': ModalComponent
    }
})
export class ParentComponent extends Vue {
    name: string = 'Nittsu System Viet Nam';

    showModal() {
        let name = this.name;
        // gọi modal theo tên đã khai báo ở decorator
        this.$modal(
            'sample', // tên modal
            { name } // params (prop)
        ).then(v => {
            // kết quả trả về
            alert(`You are choose: ${v}`);
        });
    }
}
```
###### 2.3 Khai báo Modal-Component

`Modal-component` là component được hiển thị đè lên màn hình chính.  
Template của component này có 3 phần chính là header, body và footer.  
- header: có class là `"modal-header"`. nếu không khai báo header, nó sẽ hiển thị mặc định theo khai báo của Kiban.
- footer: có class là `"modal-footer"`. có thể khai báo hoặc không.
- body: nằm giữa header và footer, là nội dung chính của một Modal-Component

```javascript
@component({
    template: `<div class="modal-component">
        <div class="modal-header">
            Header Content
        </div>
        
        <div>Body Content<div>

        <div class="modal-footer">
            Footer Content
        </div>
    </div>`
})
export class ModalComponent extends Vue {
    ...
}
```


##### 3. Hộp thông báo.

Có thể khởi tạo modal ở các dạng hiển thị đặc biệt như 
<span style="color: #1ba4d6">**information**</span>, 
<span style="color: #ffaa00">**warning**</span>, 
<span style="color: #ff1c30">**error**</span>, 
**confirm** bằng các cách sau.
``` typescript
$modal.info('Nội dung message');
$modal.warn('Nội dung message');
$modal.error('Nội dung message');
$modal.confirm('Nội dung message', 'normal' | 'dange' | 'primary');
```

Bạn cũng có thể dùng `messageId` và `messageParams` để hiện thị nội dung của dialog như sau:

``` typescript
$modal.info({
    messageId: string,
    messageParams: string[] | { [key: string]: string } 
});
```