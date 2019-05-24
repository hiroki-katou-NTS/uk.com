# 2. Code
> `nts-radio` và `nts-checkbox` là 2 component giúp đơn giản hoá việc bind một model từ viewmodel lên view.<br /> Component `nts-radio` và `nts-checkbox` là những component đơn, để kết hợp một loạt những component `nts-radio` và `nts-checkbox` lại thành một nhóm, ta có thể sử dụng nhiều component cùng loại hoặc sử dụng kết hợp với v-for có `bind:name` cho component.

> Vui lòng xem qua đoạn mã ví dụ dưới đây cho cả view và viewmodel, kết hợp đọc rõ mục API để sử dụng `nts-radio` và `nts-checkbox` một cách hiệu quả nhất.
-----
### 2.1 Radio
> With v-for
```html
<nts-radio v-for="(radio, k) in radios"
    v-model="checked"
    v-bind:name="'radio1'"
    v-bind:value="radio.id"
    v-bind:disabled="radio.disabled"
    v-bind:class="''">
    {{radio.id}} - {{radio.name}}
</nts-radio>
```
> Without v-for
```html
<nts-radio
    v-model="checked2"
    v-bind:name="'radio2'"
    v-bind:value="1"
    v-bind:disabled="false"
    v-bind:class="''">
    Radio 1
</nts-radio>
```
### 2.2 Checkbox
> With v-for
```html
<nts-checkbox v-for="(radio, k) in radios"
    v-model="checkeds"
    v-bind:value="radio.id"
    v-bind:disabled="radio.disabled"
    v-bind:class="''">
    {{radio.id}} - {{radio.name}}
</nts-checkbox>
```
> Without v-for
```html
<nts-checkbox
    v-model="checked2s"
    v-bind:value="1"
    v-bind:disabled="false"
    v-bind:class="''">
    Check 1
</nts-checkbox>
```
### 2.3 SwitchBox
> **Chú ý:** `nts-switchbox` là một nhóm các control nên cần được đóng trong một thẻ `container` (tức là không nằm đồng mức với bất kỳ control nào khác).
```html
<div class="btn-group btn-group-toggle">
    <nts-switchbox 
        v-for="(radio, k) in radios" 
        v-model="switchbox" 
        v-bind:name="'radio1'"
        v-bind:value="radio.id" 
        v-bind:disabled="radio.disabled" 
        v-bind:class="''">
        {{radio.id}} - {{radio.name}}
    </nts-switchbox>
<div>
```
### 2.4 ViewModel
```typescript
import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({})
export class FormCheckComponent extends Vue {
    radios = [
        { id: 1, name: 'Option 1' },
        { id: 2, name: 'Option 2' },
        { id: 3, name: 'Option 3' },
        { id: 4, name: 'Option 4' }
    ];

    checked: number = 3;
    checked2: number = 2;
    checkeds: Array<number> = [2, 4];
    checked2s: Array<number> = [2, 4];
}
```
# 3. API
> Dưới đây là các prop (binding) được phép sử dụng với `nts-radio` và `nts-checkbox`

| Prop | Sử dụng | Kiểu dữ liệu | Diễn giải |
| -----|---------|--------------|-----------|
| `name?` | `v-bind:name=""` hoặc `:name=""` | `string` | Thuộc tính nhóm các component cùng loại lại với nhau |
| `model`| `v-model=""` | `any` hoặc `any[]` | Thuộc tính binding model chứa dữ liệu khi `radio` hoặc `checkbox` xảy ra sự kiện `input` hoặc `change` <br /> Chú ý: model của `nts-radio` là kiểu `any`, của `nts-checkbox` là `any[]` (hoặc `any` nếu chỉ sử dụng 1 checkbox input) |
| `value`| `v-bind:value=""` hoặc `:value=""` | `any` | Thuộc tính binding giá trị để component gán giá trị cho `v-model` khi xảy ra sự kiện `input` hoặc `change' |
| `disabled?` | `v-bind:disabled=""` hoặc `:disabled=""` | `boolean` | Thuộc tính enable hay disable `radio` hoặc `checkbox` tuỳ thuộc vào giá trị được binding là `false` hay `true` |
| `class?` | `v-bind:class=""` hoặc `:class=""` | `string` | Thuộc tính bind class cho container của `nts-radio` hoặc `nts-checkbox`. <br /> VD: Muốn các `radio` hoặc `checkbox` hiển thị theo hàng ngang thay vì hàng dọc thì sử dụng: `v-bind:class="'form-check-inline'"` |

> Document write by: Nguyen Van Vuong