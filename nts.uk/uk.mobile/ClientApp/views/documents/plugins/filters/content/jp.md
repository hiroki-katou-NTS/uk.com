##### 2. 説明
UKモバイルはこれらのフィルタを提供している：
- `date`: 日付
- `timewd`: 日区分付き時刻
- `timept`: 時刻
- `timedr`: 時間


フィルタのインプットの種類はフィルタによって違う：
- `date`: `Date`
- `timewd`: `number`
- `timept`: `number`
- `timedr`: `number`

**HTML Code:**
```html
<div class="sample">
    <span>{{ new Date() | date }}</span>
    <!-- Output: 2019/01/01 -->
    <span>{{ new Date() | date('dd-mm-yyyy') }}</span>
    <!-- Output: 01-01-2019 -->

    <span>{{ -10 | timewd }}</span>
    <!-- Output: 前日 23:50 -->
    <span>{{ -10 | timept }}</span>
    <!-- Output: -23:50 -->
    <span>{{ -10 | timedr }}</span>
    <!-- Output: -00:10 -->
    
</div>
```

著者：Nguyen Van Vuong