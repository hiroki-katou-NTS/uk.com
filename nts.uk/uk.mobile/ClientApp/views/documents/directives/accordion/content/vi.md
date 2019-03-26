# 2. Code
----
> `accordion` là một directive html. Khi được gắn vào một container bất kỳ, sự kiện `click` sẽ được gắn cho các `element` có class là `btn-link`. Bất kỳ sự kiện `click` nào xảy ra, các class `card` sẽ được đóng mở dựa vào quan hệ cha con với `btn-link` được click.

> `accordion` có thể được sử dụng như một `step-wizard` directive khi có thêm tham số cho các nút điều hướng hoặc thêm class là `accordion-step` vào container gốc chứa class `accordion`.

> **Mẹo**: Để một accordion bất kỳ được mở ngay khi tải trang, hãy thêm class `show` vào `element` có class `card` tương ứng.

##### Diễn giải theo cấu trúc tree node:
#####  (directive bind: `v-nts-accordion`)

```
div.accordion{.accordion-step} [v-nts-accordion]
    div.card <repeat>
        div.card-header
            button.btn.btn-link.collapase
                {{title of collapse}}
        div.collapse
            div.card-body
                {{content of collapse}}
```

## 2.1 Html (like accordion)

```html
<div class="accordion" v-nts-accordion>
    <div class="card">
        <div class="card-header">
            <button class="btn btn-link collapsed" type="button">
                {{title_1}}
            </button>
        </div>
        <div class="collapse">
            <div class="card-body">
                {{content_1}}
            </div>
        </div>
    </div>
    <div class="card">
        <div class="card-header">
            <button class="btn btn-link collapsed" type="button">
                {{title_2}}
            </button>
        </div>
        <div class="collapse">
            <div class="card-body">
                {{content_2}}
            </div>
        </div>
    </div>
    <div class="card">
        <div class="card-header">
            <button class="btn btn-link collapsed" type="button">
                {{title_3}}
            </button>
        </div>
        <div class="collapse">
            <div class="card-body">
                {{content_3}}
            </div>
        </div>
    </div>
</div>
```
## 2.2 Html (like step wizard)

```html
<div class="accordion" v-nts-accordion="{ disable: { next: false, preview: false } }">
    <div class="card">
        <div class="card-header">
            <button class="btn btn-link collapsed" type="button">
                {{title_1}}
            </button>
        </div>
        <div class="collapse">
            <div class="card-body">
                {{content_1}}
            </div>
        </div>
    </div>
    <div class="card">
        <div class="card-header">
            <button class="btn btn-link collapsed" type="button">
                {{title_2}}
            </button>
        </div>
        <div class="collapse">
            <div class="card-body">
                {{content_2}}
            </div>
        </div>
    </div>
    <div class="card">
        <div class="card-header">
            <button class="btn btn-link collapsed" type="button">
                {{title_3}}
            </button>
        </div>
        <div class="collapse">
            <div class="card-body">
                {{content_3}}
            </div>
        </div>
    </div>
</div>
```