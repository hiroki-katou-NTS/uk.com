##### 2. ビューでの定義

```html
    <text-search-box 
        placeholder="勤務種類コード/勤務種類名称"
        v-on:search="searchList"/>
```

HTMLでは`v-on:search="searchList"`がある`text-search-box`のタグを作ってください。  
`searchList`はビューモデルにある関数。  

良かったら`placeholder`の属性を追加してください。`placeholder`の初期値は空白。

##### 3. ビューモデルでの定義

ViewModelにはビューで使った関数を定義してください。  
ユーザーが「検索」ボタンをクリックするとこの関数が呼ばれる。

```ts
export class ViewModel extends Vue {
    *
    *
    public searchList(value: string) {
        // process the event 'search'
    }
    *
    *
}
```