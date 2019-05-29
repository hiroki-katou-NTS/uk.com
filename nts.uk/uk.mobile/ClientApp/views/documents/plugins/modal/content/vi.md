##### 2. Giải thích

`Modal` là một `plugin` hỗ trợ để hiển thị một component dưới dạng một dialog.  
Để khởi tạo một modal, hãy gọi method `$modal(component, params?, option?)`.
- `component` là một string hoặc một Component để chỉ định nội dung hiển thị trên dialog.
- `params` sử dụng để đẩy dữ liệu từ viewmodel (màn hình cha) sang component. Thuộc tính này sẽ chỉ đẩy dữ liệu lên *một lần duy nhất*. Để đồng bộ dữ liệu nhiều lần, hãy sử dụng `state` hoặc `eventBus`.
- `option` là thuộc tính sử dụng để cài đặt các kiểu hiển thị và tiêu đề cho modal.  

> Chú ý: Phương thức $modal trả về một đối tượng Promise nên ta có thể sử dụng hàm then để thực thi đồng bộ các thao tác dữ liệu được trả về từ $modal.

#####   3. Code TypeScript
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

Tham số thứ 3 `option` là 1 Object có dạng:
```typescript 
declare interface IModalOptions {
    type?: 'modal' | 'popup' | 'info' | 'dropback';
    size?: 'lg' | 'md' | 'sm' | 'xs';
    title?: string;
    style?: string;
    animate?: 'up' | 'right' | 'down' | 'left';
    opacity?: number;
}
```
##### 4. Các modal đặc biệt

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

Bạn cũng có thể dùng `messageId` để hiện thị nội dung của dialog như sau:

``` typescript
$modal.info({
    messageId: string,
    messageParams: string[] | { [key: string]: string } 
});
$modal.warn({
    messageId: string, 
    messageParams: string[] | { [key: string]: string; } 
});
$modal.error({
    messageId: string,
    messageParams: string[] | { [key: string]: string; } 
});
$modal.confirm(
    {
        messageId: string, 
        messageParams: string[] | { [key: string]: string; } 
    }, 
    'normal' | 'dange' | 'primary'
)
```