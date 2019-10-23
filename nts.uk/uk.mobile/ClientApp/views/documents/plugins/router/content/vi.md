##### 1. Giới thiệu
> UK Mobile sử dụng mô hình Single Page Application (SPA) để phát phát triển, điều này dẫn đến việc điều hướng trong hệ thống (chuyển từ màn hình này sang màn hình khác) cũng có những đặc thù riêng. UK Mobile sử dụng `vue-router` để quản lý các trạng thái và điều hướng. Để biết rõ ràng hơn, hãy đọc tài liệu về [`vue-router`](https://router.vuejs.org/) [tại đây](https://router.vuejs.org/).
> <br />UK mobile hỗ trợ điều hướng trực tiếp bằng cách gọi tên màn hình và các tham số cần truyền vào thông qua hàm `$goto` được `mixin` vào mỗi `viewmodel` hoặc cũng có thể gọi thông qua `path` được khai báo ở `component` (không khuyến khích dùng cách này).

##### 2. Các hàm gọi.
> Bằng hàm mixin kiban thêm vào (khuyến khích dùng).
> <br />**Chú ý**: Gọi qua các hàm `goto`, giá trị sẽ tự động bind vào Prop `params` được khai báo ở `component` được gọi. Chi tiết về `prop binding` [xem tại đây](/documents/component/viewmodel);

###### Gọi từ view:
```html
<!--Gọi trực tiếp từ hàm $goto mixin vào component -->
<a class="btn btn-link" v-on:click="$goto('SampleComponent')">{{'go_to_sample' | i18n}}</a>

<!--Gọi thông qua hàm goto được extends từ $router -->
<a class="btn btn-link" v-on:click="$router.goto('SampleComponent')">{{'go_to_sample' | i18n}}</a>
```

###### Gọi từ view-model:
```typescript
export class OtherSampleComponent extends Vue {

    gotoSampleComponent() {
        let self = this;

        // Cách thứ nhất
        // Truyền vào 2 tham số
        /**
         * Tham số thứ nhất là tên của component cần chuyển tới
         * Tham số thứ hai là params (sẽ nhận thông qua Prop params)
         */
        self.$goto('SampleComponent', { id: 100, name: 'Nguyen Van A' });

        // Cách thứ hai
        // Truyền vào 1 tham số
        /**
         * [name] là tên của component cần chuyển tới
         * [params] là params (sẽ nhận thông qua Prop params)
         */
        self.$goto({ name: 'SampleComponent', params: { id: 100, name: 'Nguyen Van A' } });
        // hoặc
        self.$router.goto({ name: 'SampleComponent', params: { id: 100, name: 'Nguyen Van A' } });
    }
}
```

> Ngoài ra từ view ta cũng có thể sử dụng virtual element (component) của vue-router để phục vụ cho mục đích chuyển trang (nếu không truyền tham số).
> <br />Để rõ hơn về router-link, hãy [đọc document của nhà phát triển tại đây](https://router.vuejs.org/api/#router-link).
> <br />**Chú ý**: Có sự khác nhau giữa `:to` (props) và `to` (attribute). `:to` là binding của vue nên tham số nhận vào là một biến, `to` là attribute của html nên tham số nhận vào là một chuỗi. Hãy chú ý điều này.

```html
<!-- tham số là url -->
<router-link to="/sample">{{'go_to_sample' | i18n}}</router-link>
<!-- tham số là url bind qua v-bind:to -->
<router-link :to="'/sample'">{{'go_to_sample' | i18n}}</router-link>
<!-- tham số là name, bind qua v-bind:to -->
<router-link :to="{ name: 'SampleComponent' }">{{'go_to_sample' | i18n}}</router-link>
```

```typescript
export class OtherSampleComponent extends Vue {

    gotoSampleComponent() {
        let self = this;
        // Chú ý, gọi qua cách này, tham số không được nhận qua prop params
        // và tham số là dạng Dictionary<string> | { [key: string]: string }
        self.$router.push('SampleComponent', { id: '100', name: 'Nguyen Van A' });
    }
}
```


##### 3. Kỹ thuật chống tràn bộ nhớ.
> Thông thường, khi chuyển qua view mới, `vue-router` sẽ tự động `destroy` component cũ đi để giải phóng bộ nhớ. Tuy nhiên, có những đối tượng mà dev khai báo riêng và được sử dụng không theo thiết kế tiêu chuẩn. Khi chuyển qua view mới, các đối tượng này không được `destroy` dẫn tới tình trạng bộ nhớ cấp phát cho app ngày một tăng lên sau mỗi lần chuyển view. Điều này gây ra tình trạng app chạy chậm, giật, lâu phản hồi, gây hao pin của thiết bị. Đặc biệt là với mô hình SPA đang áp dụng cho dự án này thì càng thể hiện rõ ràng điều đó.
> <br /> Chính vì vậy, hãy đảm bảo trước khi chuyển qua view mới, tất cả các đối tượng được khởi tạo đặc biệt phải được huỷ đi để giải phóng bộ nhớ. `vue-router` cung cấp cho chúng ta 2 hàm callback đặc biệt để giải quyết vấn đề này theo ý của chúng ta là: `onComplete` và `onAbort`.
> <br /> Để sử dụng chúng ta chỉ cần thêm vào 2 hàm $goto đã được đề cập ở trên 1 hoặc 2 hàm callback tương ứng. Chi tiết xem qua ví dụ dưới đây.

```typescript
export class OtherSampleComponent extends Vue {

    gotoSampleComponent() {
        let self = this;

        self.$goto(
            // name
            'SampleComponent', 
            // params
            { id: 100, name: 'Nguyen Van A' },
            // onComplete
            () => {
                // xử lý giải phóng bộ nhớ khi đã chuyển trang thành công ở đây
                // VD:
                self.$destroy();
            }
        );
        self.$goto(
            // location
            { name: 'SampleComponent', params: { id: 100, name: 'Nguyen Van A' } }, 
            // onComplete
            () => {
                // xử lý giải phóng bộ nhớ khi đã chuyển trang thành công ở đây
                // VD:
                self.$destroy();
            });
        // hoặc
        self.$router.goto(
            // location
            { name: 'SampleComponent', params: { id: 100, name: 'Nguyen Van A' } }, 
            // onComplete
            () => {
                // xử lý giải phóng bộ nhớ khi đã chuyển trang thành công ở đây
                // VD:
                self.$destroy();
            });
    }
}

```

> Tài liệu được viết bởi:&nbsp;&nbsp;&nbsp;`Nguyễn Văn Vương`