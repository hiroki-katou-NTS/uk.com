##### 2. Diễn giải
> `tree-list` là danh sách các đối tượng có quan hệ phân cấp (`hierarchy`) cha con. Để hiển thị danh sách dạng này, chúng ta cần sử dụng một hàm (`hierachy`) được cung cấp trong thư viện `utils/object` theo ví dụ dưới đây.

```typescript
import { obj } from '@app/utils';

export class SampleViewModel {
    // danh sách item cần hiển thị dạng tree list
    public items: Array<any> = [];

    // sử dụng computed của vue để tính toán các item cần hiển thị
    get flatten() {
        /**
         *  Hàm này nhận vào danh sách các item cần hiển thị dạng tree-list
         * params
         *      items: danh sách item cần hiển thị dạng tree-list
         *      childProp: tên thuộc tính danh sách các item con cần hiển thị
         */ 

        return obj.hierarchy(this.items, 'childs');
    }
}
```
> Sau khi tính toán được danh sách các item cần hiển thị từ `viewmodel` ta dựng `view` theo ví dụ dưới đây.

```html
<ul class="list-group list-group-tree">
    <li class="list-group-item" v-for="(item, k) in flatten" v-bind:key="k" v-tree="item">
        <span>{{item.id}} {{item.text}}</span>
        <input class="selection" type="radio" name="abc" v-model="selected" v-bind:value="item">
    </li>
</ul>
```
> Các điểm cần chú ý:
- Ở thẻ root của tree-view, class `list-group-tree` là bắt buộc.
- Danh sách item được lặp là danh sách các đối tượng được tính toán từ hàm get (`computed`) của `viewmodel`.
- Ở các thẻ con, `directive: v-tree` là bắt buộc, nó nhận vào item được lặp trong danh sách.
- Để sử dụng `checkbox` hoặc `radio`, sử dụng `input tag` với `type` tương ứng như ví dụ, class `selection` là bắt buộc với thẻ này. 

> **Đặc biệt chú ý**: Hàm `hierarchy` trong `utils` tính toán danh sách các đối tượng cần hiển thị nhưng *có làm thay đổi các đối tượng ban đầu*.

<div class="mt-3"></div>

Viết bởi: **Nguyễn Văn Vương**.
<div class="mb-3"></div>