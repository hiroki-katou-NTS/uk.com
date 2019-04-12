##### 2. Giải thích

> Trong quá trình runtime của một `view/component` bất kỳ. Sẽ có những thao tác mà ở đó phải đợi kết quả trong một khoảng thời gian, thao tác đó lại không cho phép người dùng cuối được phép thao tác với màn hình khi kết quả chưa được trả về.
> <br />Để giải quyết vấn đề này, một `masklayer` (lớp mặt nạ) hay `blockui` (khoá màn hình) sẽ được bật lên để ngăn cản các thao tác của người dùng tác động lên màn hình.
> <br />Như ví dụ ở trên, `masklayer` được bật lên, sau một khoảng thời gian (kết quả được trả về) thì `masklayer` được gỡ bỏ đi.

> **Mẹo**: Có thể gọi `masklayer` ở ngay trong `view` thông qua một `event` bất kỳ bằng `v-on`. Ví dụ: `v-on:click="$mash('show')"`.

##### 3. Code

```typescript
class SampleMaskViewModel extends Vue {
    // method show mask
    showMask() {
        this.$mask('show')
            .on(() => {
                // click event
                self.messages.push('mask_click');
            }, () => {
                // close event
                self.messages.push('mask_hide');
            });
    }

    // method hide mask
    hideMask() {
        this.$mask('hide');
    }
}
```