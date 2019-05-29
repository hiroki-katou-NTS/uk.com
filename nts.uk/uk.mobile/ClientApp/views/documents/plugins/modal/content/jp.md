##### 2. 説明

`Modal`はあるコンポネントをダイアログの形で表示をサポートするプラグインである。
モーダルを作るために`$modal(component, params?, option?)`メソッドを使ってください。
- `component`の種類は`string`か`Component`です。これはダイアログで表示される内容を指定する。
- `params`はモーダルに渡される親のビューモデルのデータである。
- `option`は表示方法を決める。


#####   3. Code TypeScript
```typescript
@component({
    ...
    components: {
        // 子のコンポーネントを定義
        'sample': ModalComponent
    }
})
export class ParentComponent extends Vue {
    name: string = 'Nittsu System Viet Nam';

    showModal() {
        let name = this.name;
        // モーダルを作る
        this.$modal(
            'sample', // 上に定義したコンポーネント名
            { name } // params
        ).then(v => {
            // もらう結果
            alert(`You are choose: ${v}`);
        });
    }
}
```

`$modal()`メソッドの三番目のパラメータ(`option`)は下みたい。
```typescript 
declare interface IModalOptions {
    type?: 'modal' | 'popup' | 'info' | 'dropback';
    size?: 'lg' | 'md' | 'sm' | 'xs';
    title?: string;
    style?: string;
    animate?: 'up' | 'right' | 'down' | 'left';
    opacity?: number;
}
```
##### 4. 特別なモーダル

<span style="color: #1ba4d6">**information**</span>, 
<span style="color: #ffaa00">**warning**</span>, 
<span style="color: #ff1c30">**error**</span>, 
**confirm**など特別なモーダルをこんなように作ってください。
``` typescript
$modal.info('メッセージ内容');
$modal.warn('メッセージ内容');
$modal.error('メッセージ内容');
$modal.confirm('メッセージ内容', 'normal' | 'dange' | 'primary');
```

`messageId`も使える。

``` typescript
$modal.info({
    messageId: string,
    messageParams: string[] | { [key: string]: string } 
});
$modal.warn({
    messageId: string, 
    messageParams: string[] | { [key: string]: string; } 
});
$modal.error({
    messageId: string,
    messageParams: string[] | { [key: string]: string; } 
});
$modal.confirm(
    {
        messageId: string, 
        messageParams: string[] | { [key: string]: string; } 
    }, 
    'normal' | 'dange' | 'primary'
)
```