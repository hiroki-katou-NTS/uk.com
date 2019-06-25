> `nts-search-box` hay `text-search-box` là `control` phục vụ cho các sự kiện liên quan tới tìm kiếm dữ liệu trong danh sách. Control này chỉ xử lý từ khóa (`keyword`) và đẩy từ khóa này ra thông qua 2 `event` là `input` và `search`. Event `input` được thực thi liên tục mỗi khi có ký tự mới được nhập vào `search-box`, event `search` được thực thi mỗi khi người dùng chạm/nhấp chuột vào icon <span class="fa fa-search"></span>.

**Các prop binding** được hỗ trợ trong `nts-search-box`.

| Tên Thuộc tính| Type | Mặc định | Mô tả |
| --------------|------| -------- | ------|
| placeholder | string | '' | Ghi chú cho thẻ input (có thể dùng resources id) |
| class-input | string | '' | Class css sẽ gắn vào thẻ input |
| class-container | string | '' | Class css sẽ gắn vào container/thẻ bao của thẻ input |

##### 2. Code
**View**:
```html
<nts-search-box
    placeholder="勤務種類コード/勤務種類名称"
    v-bind:class-input=""
    v-bind:class-container=""
    v-on:input="inputEvent"
    v-on:search="searchEvent"/>
```
hoặc
```html
<text-search-box 
    placeholder="勤務種類コード/勤務種類名称"
    v-bind:class-input=""
    v-bind:class-container=""
    v-on:input="inputEvent"
    v-on:search="searchEvent"/>
```

**View model**:
```typescript
import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
})
export class SampleViewModel extends Vue {
    // xử lý event search khi nhập xong một ký tự bất kỳ tại đây
    public inputEvent(value: string) {
        // process the event 'search'
    }

    // xử lý event search khi click vào icon search tại đây
    public searchEvent(value: string) {
        // process the event 'search'
    }
}
```
> **Chú ý**: Chỉ sử dụng một trong 2 event `search` hoặc `input` tùy vào yêu cầu của thuật toán (`đã giải thích ý nghĩa event ở trên`).