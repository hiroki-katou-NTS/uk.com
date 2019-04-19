##### 1. Float buttons
> `v-float-action` là một `directive` nhóm một nhóm các `button` thành một `floating` nhóm các `button` nổi lên trên tất cả các đối tượng khác.
> <br />Thực tế mỗi màn hình sẽ sử dụng `v-float-action` này cho mỗi tác vụ khác nhau, hãy ứng dụng sao cho thật hiệu quả và phù hợp với thiết kế chung của màn hình.

##### 2. Preview
<div class="fixed-action-btn active" style="z-index: 99; position: relative; width: 330px; margin-top: 50px;">
    <ul class="list-unstyled" style="height: 228px">
        <li class="btn-info btn btn-floating">
            <span>vote_app</span>
            <i class="fas fa-star"></i>
        </li>
        <li class="btn-primary btn btn-floating">
            <span>manager_users</span>
            <i class="fas fa-user"></i>
        </li>
        <li class="btn-success btn btn-floating">
            <span>go_to_mail</span>
            <i class="fas fa-envelope"></i>
        </li>
        <li class="btn-warning btn btn-floating">
            <span>go_to_cart</span>
            <i class="fas fa-shopping-cart"></i>
        </li>
    </ul>
    <a class="btn btn-danger btn-lg btn-floating" style="margin-left: 135px;">
        <i class="fas fa-plus"></i>
    </a>
</div>


##### 3. Code
> Cấu trúc cơ bản của một `v-float-action`:

```html
<div v-float-action>
    <ul>
        <li class="btn-info">
            <span>{{'vote_app' | i18n}}</span>
            <i class="fas fa-star"></i>
        </li>
        <li class="btn-primary">
            <span>{{'manager_users' | i18n}}</span>
            <i class="fas fa-user"></i>
        </li>
        <li class="btn-success">
            <span>{{'go_to_mail' | i18n}}</span>
            <i class="fas fa-envelope"></i>
        </li>
        <li class="btn-warning">
            <span>{{'go_to_cart' | i18n}}</span>
            <i class="fas fa-shopping-cart"></i>
        </li>
    </ul>
</div>
```