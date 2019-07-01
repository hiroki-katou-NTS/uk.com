##### 2. Diễn giải
> `approved` control không phải là `common` control nên khi sử dụng, developer cần tự import vào màn hình cần sử dụng theo ví dụ mẫu dưới đây

**ViewModel (Typescript)**
```typescript
import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { ApprovedComponent } from '@app/components';

@component({
    components: {
        // khai báo virtual tag name
        'approved': ApprovedComponent
    }
})
export class DocumentsControlsApprovedStatusComponent extends Vue {
    // khai báo model selected (sẽ trả ra theo thứ tự select)
    public selected: number = 0;
}
```

**View (HTML):**
```html
<!-- bind model chứa giá trị là thứ tự item cần select vào component -->
<approved v-model="selected">
    <template v-slot:buttons>
        <!-- Khi một button bất kỳ được click, giá trị được emit ra là thứ tự của button -->
        <button class="uk-apply-reflected">済</button>
        <button class="uk-apply-approved">済</button>
        <button class="uk-apply-denial">済</button>
        <button class="uk-apply-return">済</button>
        <button class="uk-apply-cancel">済</button>
    </template>
    <template v-slot:popovers>
        <!-- duyệt theo thứ tự button ở đây -->
        <template v-if="selected == 0"></template>
        <template v-else-if="selected == 1"></template>
        <template v-else-if="selected == 2"></template>
        <template v-else-if="selected == 3"></template>
        <template v-else-if="selected == 4"></template>
    </template>
</approved>
```