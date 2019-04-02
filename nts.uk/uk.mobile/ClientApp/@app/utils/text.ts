export module text {

    export function countHalf(text: string) : number {
        let count = 0;
        for (let i = 0; i < text.length; i++) {
            let c = text.charCodeAt(i);
    
            // 0x20 ～ 0x80: 半角記号と半角英数字
            // 0xff61 ～ 0xff9f: 半角カタカナ
            if ((0x20 <= c && c <= 0x7e) || (0xff61 <= c && c <= 0xff9f)) {
                count += 1;
            } else {
                count += 2;
            }
        }
        return count;
    }
}