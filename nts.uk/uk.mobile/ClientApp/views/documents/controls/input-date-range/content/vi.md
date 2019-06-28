##### 2. Giải thích
> `nts-date-range-input` là một `control` cho phép nhập dữ liệu kiểu `Date` dưới dạng khoảng thời gian `start`, `end`. Giá trị bind vào `control` và được `control` trả ra là một `reactive object` có 2 thuộc tính `start`, `end` như sau: `{ start: Date | null; end: Date | null; }`.

**HTML Code:**
```html
<nts-date-range-input 
    name="Date range" 
    v-model="dateRange" />
```

**Typescript code:**
```typescript
import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
})
export class SampleViewModel extends Vue {
    // model chứa giá trị start & end của date range
    public dateRange: { start: Date | null; end: Date | null } = {
        start: null,
        end: null
    };

    public validate() {
        this.$validate();
    }
}
```
##### 3. Thông tin bổ sung

"nts-time-range-input" là một dạng input trong UK-Mobile, vì thế nó có các thuộc tính chung của Input như là: 

| Tên Thuộc tính| Type | Mặc định | Mô tả |
| --------------|------| -------- | ------|
| name | string | '' | Tên hiển thị của item |
| disabled | boolean | false | Item có bị disable hay không? |
| showTitle | boolean | **false** | Có hiển thị title cùng input hay không? |
| inlineTitle | boolean | false | Constraint có hiển thị cùng một dòng mới title hay không? |
| columns | {title, input} | {title: 'col-md-12', input: 'col-md-12'} | Điều chỉnh để title và input hiển thị cùng một dòng. (Ví dụ: { title: 'col-md-6', input: 'col-md-6'})|
  