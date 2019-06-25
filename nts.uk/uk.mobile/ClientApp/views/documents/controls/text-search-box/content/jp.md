##### 2. ビューでの定義

```html
<text-search-box v-on:search="searchList"/>
```

HTMLでは`v-on:search="searchList"`がある`text-search-box`のタグを作ってください。  
`searchList`はビューモデルにある関数である。  

##### 3. ビューモデルでの定義

ViewModelにはビューで使った関数を定義してください。  
ユーザーが「検索」ボタンをクリックするとこの関数が呼ばれる。

```ts
export class ViewModel extends Vue {
    *
    *
    public searchList(value: string) {
        // 「search」エベントを処理
    }
    *
    *
}
```

##### 4. 補足情報

| 属性名　| 種類 | 初期値 | 説明 |
| --------------|------| -------- | ------|
| placeholder | string | '' | インプットのヒント |
| class-input | string | '' | インプットに適用するクラス |
| class-container | string | '' | コンポネントに適用するクラス |

著者：　Pham Van Dan