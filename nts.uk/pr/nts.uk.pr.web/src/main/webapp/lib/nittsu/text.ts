module nts.uk.text {

    var regexp = {
        allHalfNumeric: /^\d+$/,
        allHalfAlphabet: /^[a-zA-Z]+$/,
        allHalfAlphanumeric: /^[a-zA-Z0-9]+$/,
        allHalfKatakanaReg: /^[ｱ-ﾝｧ-ｫｬ-ｮｯｦ ﾞﾟ｡.ｰ､･'-]+$/,
        allFullKatakanaReg: /^[ァ-ー　。．ー、・’－ヴヽヾ]+$/,
        allHiragana: /^[ぁ-ん　ー ]+$/,
    };

    /**
     * 文字列の半角文字数を数える（Unicode用）
     * @param text 解析対象の文字列
     */
    export function countHalf(text: string) {
        var count = 0;
        for (var i = 0; i < text.length; i++) {
            var c = text.charCodeAt(i);

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

    /**
     * 文字列が半角数字のみで構成された1文字以上の文字列かどうか判断する
     * @param text 解析対象の文字列
     */
    export function allHalfNumeric(text: string) {
        return text !== '' && regexp.allHalfNumeric.test(text);
    }

    /**
     * 文字列が半角英字のみで構成された1文字以上の文字列かどうか判断する
     * @param text 解析対象の文字列
     */
    export function allHalfAlphabet(text: string) {
        return text !== '' && regexp.allHalfAlphabet.test(text);
    }

    /**
     * 文字列が半角英数字のみで構成された1文字以上の文字列かどうか判断する
     * @param text 解析対象の文字列
     */
    export function allHalfAlphanumeric(text: string) {
        return text !== '' && regexp.allHalfAlphanumeric.test(text);
    }

    /**
     * 文字列が半角カナのみで構成された1文字以上の文字列かどうか判断する
     * @param text 解析対象の文字列
     */
    export function allHalfKatakana(text: string) {
        return text !== '' && regexp.allHalfKatakanaReg.test(text);
    }

    /**
     * 文字列が全角カナのみで構成された1文字以上の文字列かどうか判断する
     * @param text 解析対象の文字列
     */
    export function allFullKatakana(text: string) {
        return text !== '' && regexp.allFullKatakanaReg.test(text);
    }

    /**
     * 文字列が半角文字のみで構成された1文字以上の文字列かどうか判断する
     * @param text 解析対象の文字列
     */
    export function allHalf(text: string) {
        return text !== '' && text.length === countHalf(text);
    }

    /**
     * 文字列が平仮名のみで構成された1文字以上の文字列かどうか判断する
     * @param text 解析対象の文字列
     */
    export function allHiragana(text: string) {
        return text !== '' && regexp.allHiragana.test(text);
    }

    /**
     * 文字列がカタカナのみで構成された1文字以上の文字列かどうか判断する
     * @param text 解析対象の文字列
     */
    export function allKatakana(text: string) {
        return text !== '' && regexp.allFullKatakanaReg.test(text);
    }

    /**
     * 文字列中のHTML記号をサニタイズする
     * @param text 変換対象の文字列
     */
    export function htmlEncode(text: string) {
        var element = document.createElement('pre');
        if (typeof element.textContent !== 'undefined') {
            element.textContent = text;
        } else {
            element.innerText = text;
        }
        return element.innerHTML;
    }

    /**
     * 1文字目のみ小文字に変換する
     * @param text 変換対象の文字列
     */
    export function toLowerCaseFirst(text: string) {
        return text.charAt(0).toLowerCase() + text.slice(1);
    };

    /**
     * 1文字目のみ大文字に変換する
     * @param text 変換対象の文字列
     */
    export function toUpperCaseFirst(text: string) {
        return text.charAt(0).toUpperCase() + text.slice(1);
    }

    /**
    * 指定された文字列が、null、undefined、Emptyか判定する
    * @param text 判定対象の文字列
    */
    export function isNullOrEmpty(text: any) {
        var result = true;
        if (text !== null && text !== undefined) {
            var convertValue = String(text);
            result = convertValue.length === 0;
        }
        return result;
    }

    /**
    * 指定した文字列の各書式項目を、対応するオブジェクトの値と等価のテキストに置換する
    * @param text 書式文字列
    * @param args 置換の文字列（配列可）
    */
    export function format(format: string, ...args: any[]) {
        var replaceFunction: any = undefined;

        if (typeof args === 'object') {
            replaceFunction = function (m: any, k: any) { return args[k]; };
        }
        else {
            var workArgs = arguments;
            replaceFunction = function (m: any, k: any) { return workArgs[Number(k) + 1]; };
        }
        return format.replace(/\{(\w+)\}/g, replaceFunction);
    }

    /**
    * 日付をフォーマットする
    * @param  {Date}   date     日付
    * @param  {String} [format] フォーマット
    * @return {String}          フォーマット済み日付
    */
    export function formatDate(date: any, format: any) {
        if (!format)
            format = 'yyyy-MM-dd hh:mm:ss.SSS';
        format = format.replace(/yyyy/g, date.getFullYear());
        format = format.replace(/yy/g, ('0' + (date.getFullYear() % 100)).slice(-2));
        format = format.replace(/MM/g, ('0' + (date.getMonth() + 1)).slice(-2));
        format = format.replace(/dd/g, ('0' + date.getDate()).slice(-2));
        format = format.replace(/hh/g, ('0' + date.getHours()).slice(-2));
        format = format.replace(/mm/g, ('0' + date.getMinutes()).slice(-2));
        format = format.replace(/ss/g, ('0' + date.getSeconds()).slice(-2));
        if (format.match(/S/g)) {
            var milliSeconds = ('00' + date.getMilliseconds()).slice(-3);
            var length = format.match(/S/g).length;
            for (var i = 0; i < length; i++)
                format = format.replace(/S/, milliSeconds.substring(i, i + 1));
        }
        return format;
    }

    /**
    * 変換文字列の先頭に、文字数分の指定文字列を追加する
    * @param text 変換対象の文字列
    * @param paddingChar 指定文字列
    * @param length 文字数
    */
    export function padLeft(text: string, paddingChar: string, length: number) {
        return charPadding(text, paddingChar, true, length);
    }

    /**
    * 変換文字列の末尾に、文字数分の指定文字列を追加する
    * @param text 変換対象の文字列
    * @param paddingChar 指定文字列
    * @param length 文字数
    */
    export function padRight(text: string, paddingChar: string, length: number) {
        return charPadding(text, paddingChar, false, length);
    }

    /**
    * 指定した文字列に、指定した文字列数分、指定文字列を追加する
    * @param text 変換対象の文字列
    * @param paddingChar 埋める文字列
    * @param isPadLeft 左埋めフラグ（false：右埋め）
    * @param length 文字数
    */
    export function charPadding(text: string, paddingChar: string, isPadLeft: boolean, length: number) {
        var result: string;

        if (length === 0) {
            return '';
        }

        if (isPadLeft) {
            result = (new Array(length).join(paddingChar) + text).slice(-length);
        } else {
            result = (text + new Array(length).join(paddingChar)).slice(0, length);
        }
        return result;
    }
    
    export function formatYearMonth(yearMonth: number) {
        var result: string;
        var num = parseInt(String(yearMonth));
        var year = String(Math.floor(num / 100));
        var month = charPadding(String(num % 100), '0', true, 2);
        result = year + '/' + month;
        return result;
    }
    
    export function formatSeconds(seconds: number, formatOption: string) {
        
        seconds = parseInt(String(seconds));
        
        var ss = padLeft(String(seconds % 60), '0', 2);
        
        var minutes = Math.floor(seconds / 60);
        var mm = padLeft(String(minutes % 60), '0', 2);
        
        var hours = Math.floor(seconds / 60 / 60);
        var h = String(hours);
        
        // TODO: use formatOption
        return "h:mm:ss"
            .replace(/h/g, h)
            .replace(/mm/g, mm)
            .replace(/ss/g, ss);
    }
     
     export function formatNumber(value: number, formatOption: any){
        var groupseperator = formatOption.groupseperator() ? formatOption.groupseperator() : ',';
        var grouplength = formatOption.grouplength() ? formatOption.grouplength() : 0;
        var decimalseperator = formatOption.decimalseperator() ? formatOption.decimalseperator() : ".";
        var decimallength = formatOption.decimallength() ? formatOption.decimallength() : 0;
        var formattedValue = "";
        var stringValue = value.toString();
        var values = stringValue.split(decimalseperator);
        if(grouplength > 0){
            var x = values[0].split('').reverse().join('');
            for(var i = 0; i < x.length;){
                formattedValue += x.substr(i, grouplength) + (x.length > i + grouplength ? groupseperator : "");
                i += grouplength;
            }
            formattedValue = formattedValue.split('').reverse().join('');
        }else{
            formattedValue = values[0];
        }
        if(values[1] === undefined || decimallength > values[1].length){
            values[1] = text.padRight(values[1] ? values[1] : "", '0', values[1] ? decimallength : decimallength + 1);
        }else{
            values[1] = values[1].substr(0, decimallength);    
        }
         
        return formattedValue + decimalseperator + values[1];   
     }
}