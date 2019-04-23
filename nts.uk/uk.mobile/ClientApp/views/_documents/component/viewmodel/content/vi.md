# Giới thiệu
> UK Mobile là dự án sử dụng html, ts, css để xây dựng giao diện người dùng trên mô hình [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel), kết hợp [SPA](https://en.wikipedia.org/wiki/Single-page_application) dựa trên framework VueJS + các thư viện kèm theo như vue-router, vue-ex,....

> UK Mobile không sử dụng jQuery và tuân thủ chặt chẽ mô hình MVVM nên vui lòng không bao giờ viết xử lý của view trên view model, nếu cần những xử lý này, hãy đọc về [filter](https://vi.vuejs.org/v2/guide/filters.html) và [directive](https://vi.vuejs.org/v2/guide/custom-directive.html) của [VueJS](https://vi.vuejs.org/). Trường hợp không thể tự xử lý được, hãy liên hệ với Kiban.


# Tổng quan về một view
> Khái niệm view, component hay page ở trong bài viết này được hiểu theo nghĩa là một trang web tuân thủ theo đúng mô hình MVVM ở trên.

### Cấu trúc một component như sau:
> Một component cơ bản sẽ gồm có ít nhất là 3 thành phần gồm: ViewModel-Model (file: index.ts), View (file: index.html), CSS (file style.scss hoặc style.css).

#### 1. ViewModel
> File ViewModel đặt theo nguyên tắc nằm trong thư mục views (nếu là view do dev viết) hoặc thư mục components (nếu là control do kiban viết). Nó sẽ có tên là index.ts hoặc tên của component (nếu không có view).

```typescript
import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: 'uk/sample',
    style: require('style.scss'),
    template: require('./index.html'),
    // và một số option khác
})
export class SampleComponent extends Vue {

}
```

#### 2. View

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