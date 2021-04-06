### Rule is absolute!

Whatever is writen in **Implement Rules** mean you MUST implement it in your page.


Disable save button when error


Not allow user click "save", "registry"... button when there is any error


#### Implement _(deprecate: use [mutation] binding with below document)_
```xml
<button class="proceed" data-bind="enable: $root.errors.isEmpty">登録</button>
```

### Mutation binding

#### view (html)
```xml
<!-- without developer validate -->
<button class="proceed" data-bind="i18n: '登録', mutation, click: $vm.saveData"></button>
<!-- or -->
<button class="proceed" data-bind="i18n: '登録', mutation: true, click: $vm.saveData"></button>

<!-- width developder validate -->
<button class="proceed" data-bind="i18n: '登録', mutation: $vm.enableSaveButton, click: $vm.saveData"></button>
```

#### viewmodel (typescript)
```js
@bean()
export class ViewModel extends ko.ViewModel {
    enableSaveButton!: KnockoutComputed<boolean>;

    constructor() {
        super();

        // computed enable or disable save button by developer
        this.enableSaveButton = ko.computed({
            read: () => {
                const condition_1: boolean = ko.unwrap(condition_model_1);
                const condition_2: boolean = ko.unwrap(condition_model_2);
                const condition_n: boolean = ko.unwrap(condition_model_n);

                return condition_1 && condition_2 && condition_n;
            }
        });
    }

    saveData() {
        const vm = this;

        // do something for push data via api at here
    }
}
```

#### Explain
With **mutation** binding, kiban error viewmodel always check before *enableSaveButton* computed of developer. If one of two part is failed, button will be disabled.