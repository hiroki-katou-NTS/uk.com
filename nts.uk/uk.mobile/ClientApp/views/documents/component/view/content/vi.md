##### 1. Định nghĩa
> `View` là một `file html` hoặc một đoạn string `template html` được khai báo ở [`decorator`](/documents/component/decorator) của mỗi `component` thông qua thuộc tính `template` như ví dụ dưới đây.

```typescript
import { component } from '@app/core/component';

@component({
    // có thể là string template
    template: `<div>{{pgName}}</div>`,
    // hoặc là file template
    template: require('./index.html')
})
```

> Chi tiết về [`decorator`](/documents/component/decorator) [`xem tại đây`](/documents/component/decorator)

##### 2. Ví dụ
> **Chú ý**: Dù là `string template` hay `file template` thì một `view` cũng sẽ được đóng/mở bởi **duy nhất một thẻ html**, VueJS không chấp nhận một `template` có nhiều hơn một thẻ **root**.

```html
<div id="home">
    <language-bar></language-bar>
    <hr />
    <button class="btn btn-primary" v-bind:disabled="!$valid" v-on:click="alertNow()">{{'home' | i18n}}</button>
    <hr />

    <v-input-string
        v-model="title"
        v-bind:name="'title'"
        v-bind:disabled="disabled"
        v-bind:show-title="true"
        v-bind:constraint="validations.title"
        v-bind:errors-always="$errors.title"
        v-bind:columns="{title: 'col-md-2', input: 'col-md-4'}" />

    <v-input-number
        v-model="resource"
        v-bind:name="'resource'"
        v-bind:disabled="disabled"
        v-bind:show-title="true"
        v-bind:constraint="validations.resource"
        v-bind:errors-always="$errors.resource"
        v-bind:columns="{title: 'col-md-2', input: 'col-md-4'}" />

    <v-input-string
        v-model="model.name"
        v-bind:name="'model.name'"
        v-bind:disabled="disabled"
        v-bind:show-title="true"
        v-bind:constraint="validations.model.name"
        v-bind:errors-always="$errors.model.name"
        v-bind:icons="{before: '@', after: '$'}"
        v-bind:columns="{title: 'col-md-2', input: 'col-md-4'}" />

    <div class="form-group">
        <v-label v-bind:constraint="validations.model.name">{{$i18n('model.name')}}</v-label>
        <input type="text" v-validate="$errors.model.name" class="form-control" v-model="model.name" />
        <v-errors v-for="error in $errors.model.name" v-bind:data="error" v-bind:name="'model.name'" />
    </div>

    <div class="form-group">
        <v-label v-bind:constraint="validations.model.addrs">{{$i18n('model.addrs')}}</v-label>
        <input type="text" v-validate="$errors.model.addrs" class="form-control" v-model="model.addrs" />
        <v-errors v-for="error in $errors.model.addrs" v-bind:data="error" v-bind:name="'model.addrs'" />
    </div>

    <button class="btn btn-primary" v-on:click="$validate()">{{$i18n('validate')}}</button>
</div>
```