##### 2. Hướng dẫn
> `step-wizard` là một `component` không thuộc dạng `common`, nó được sử dụng để biểu thị một thao tác nào đó trong một tập các bước thao tác. Để sử dụng `step-wizard`, chúng ta cần import `StepwizardComponent` từ `@app/components` vào `component` hoặc `viewmodel` mà mọi người cần sử dụng theo ví dụ dưới đây.

###### 2.1 Typescript
```typescript
import { Vue } from '@app/provider';
import { component } from '@app/core/component';
// import step component vào 
import { StepwizardComponent } from '@app/components';

@component({
    components: {
        // khai báo name cho step component
        'step-wizard': StepwizardComponent
    }
})
export class ViewModel extends Vue {
    // active step
    private step: string = 'step_1';

    public prevStep() {
        // xử lý để quay về bước trước đó nếu có
    }

    public nextStep() {
        // xử lý để chuyển sang bước tiếp theo
    }
}
```
###### 2.2 Html
```html
<!-- sử dụng name đã khai báo ở view model để tạo virtual dom-->
<step-wizard v-bind:items="['step_1', 'step_2', 'step_3', 'step_4']" v-bind:selected="step" />
<!-- phần thân của view tương ứng với từng step -->
<div class="mt-3 mb-3">
    <div v-if="step == 'step_1'">
        <!-- nếu step_1 được chọn -->
    </div>
    <div v-if="step == 'step_2'">
        <!-- nếu step_2 được chọn -->
    </div>
    <div v-if="step == 'step_3'">
        <!-- nếu step_3 được chọn -->
    </div>
    <div v-if="step == 'step_4'">
        <!-- nếu step_4 được chọn -->
    </div>
</div>
<!-- các nút điều hướng -->
<div class="text-center mt-4 mb-4">
    <button class="btn btn-secondary mr-2" v-bind:disabled="numb <= 0" v-on:click="prevStep">{{'step_back' | i18n}}</button>
    <button class="btn btn-primary btn-lg" v-bind:disabled="numb >= 3" v-on:click="nextStep">{{'step_forward' | i18n}}</button>
</div>
```
> **Chú ý**: `step-wizard` là một component có 2 thuộc tính được truyền vào. Thuộc tính `items` là mảng các chuỗi khóa (`string key`) đại diện cho các `step`, mỗi `string key` phải được đảm bảo tính duy nhất. Thuộc tính `selected` là chuỗi khóa đại diện cho `step` đang được chọn.

> **Mẹo**: Hãy sử dụng `resources id` được cung cấp bởi đội thiết kế để làm `key` cho các `step` vì các `resources id` này đã đảm bảo được tính duy nhất của các `string key` trong `step-wizard`.
<hr class="mt-5">

> **Người viết**: Nguyễn Văn Vương