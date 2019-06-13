##### 2. Giải thích
> `$modal` là một `plugin` được viết để hỗ trợ hiển thị một component bất kỳ (*trừ các component đã được khai báo là view*) trong một component dưới dạng hộp thoại (*dialog*) theo một cách thống nhất và đơn giản nhất.
> <br />Để khởi tạo `$modal`, chỉ cần gọi hàm `$modal('componentName', propPrams, option)` hoặc `$modal($componentOption, propParams, option)` từ view hoặc viewmodel cần gọi `$modal`.

> Để khởi tạo một `$modal` ta cần chỉ định rõ *component* sẽ hiển thị trong `$modal` thông qua tham số đầu tiên của hàm, tham số này có thể là `name` của *component* đã được khai báo trong `decorator` của *viewmodel* hoặc là chính `component instance` đã được `import` ở header của file `.ts`.
- `$modal('componentName', propPrams, option)`
- `$modal($componentOption, propParams, option)`

> Ngoài ra, `$modal` còn có 4 phương thức con để hiển thị các thông báo (*hoặc xác nhận thao tác*) với cách dùng như dưới đây:

- `$modal.info('Nội dung message')`
- `$modal.warn('Nội dung message')`
- `$modal.error('Nội dung message')`
- `$modal.confirm('Nội dung message', 'normal' | 'dange' | 'primary')`

> Hoặc hiển thị các thông báo (*xác nhận*) bằng cách truyền messageId, messageParams vào theo  mô tả dưới đây:

- `$modal.info({messageId: string; messageParams: string[] | { [key: string]: string; } })`
- `$modal.warn({messageId: string; messageParams: string[] | { [key: string]: string; } })`
- `$modal.error({messageId: string; messageParams: string[] | { [key: string]: string; } })`
- `$modal.confirm({messageId: string; messageParams: string[] | { [key: string]: string; } }, 'normal' | 'dange' | 'primary')`


**Parent view:**
```typescript
import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { ModalComponent } from './modal-component';

@component({
    name: 'documentspluginsmodal',
    route: {
        url: '/plugins/modal',
        parent: '/documents'
    },
    template: require('./index.vue'),
    resource: require('./resources.json'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    },
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

    showWarn() {
        // hiển thị một warning message
        this.$modal.warn('Warning, this is warning message!')
            .then(() => {
                alert('Clicked: close');
            });
    }

    showInfo() {
        // hiển thị một info message
        this.$modal.info('Info, this is info message!')
            .then(() => {
                alert('Clicked: close');
            });
    }

    showError() {
        // hiển thị một error message
        this.$modal.error('Info, this is info message!')
            .then(() => {
                alert('Clicked: close');
            });
    }

    showConfirm() {
        // hiển thị một confirm message
        this.$modal.confirm('Info, this is info message!', 'danger')
            .then(v => {
                alert('Clicked: ' + v);
            });
    }
}
```

**Modal component:**
```typescript
import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    template: `<div class="modal-component">
        <div>Hello {{params.name | i18n}} component!</div>
        <div class="modal-footer">
            <button class="btn btn-link" v-click="acceptEvent">{{'accept' | i18n}}</button>
            <button class="btn btn-link" v-click="cancelEvent">{{'cancel' | i18n}}</button>
        </div>
    </div>`
})
export class ModalComponent extends Vue {
    // đối tượng nhận giá trị từ params truyền vào trong hàm $modal ở viewmodel cha
    @Prop({ default: () => ({ name: '' }) })
    params!: { [key: string]: any };

    acceptEvent() {
        // trả về giá trị accept (bắt ở hàm then của viewmodel cha)
        this.$close('accept');
    }

    cancelEvent() {
        // trả về giá trị cancel (bắt ở hàm then của viewmodel cha)
        this.$close('cancel');
    }
}
```

##### 3. API
> **Chú ý**: Phương thức `$modal` trả về một đối tượng `Promise` nên ta có thể sử dụng hàm then để thực thi đồng bộ các thao tác dữ liệu được trả về từ `$modal` và nên sử dụng cách thức này.


| Prop | Kiểu dữ liệu | Diễn giải |
| -----|--------------|-----------|
| nameOrInstance | `string` \| `ComponentOption<Vue>` | Là thuộc tính đầu tiên của hàm `$modal` được sử dụng để chỉ định rõ `component` nào sẽ được sử dụng để hiển thị trong modal. |
| params? | `any` | Là thuộc tính thứ 2 được sử dụng để đẩy dữ liệu từ viewmodel (màn hình cha) sang component.<br />**Chú ý**: Thuộc tính này sẽ chỉ đẩy dữ liệu lên *một lần duy nhất*, để đồng bộ dữ liệu nhiều lần, hãy sử dụng `state` hoặc `eventBus`.
| options? | `any` | Là thuộc tính sử dụng để cài đặt các kiểu hiển thị và tiêu đề,... cho modal. |