module nts.uk {

    export module format {

        export interface IFormatter {
            format(source: any): string;
        }

        export interface IFormatterOption {
            constraintName: string
        }

        export class NoFormatter implements format.IFormatter {
            format(source: any): string {
                return source;
            }
        }
    }

    export module text {

        var regexp = {
            allHalfNumeric: /^\d*$/,
            allHalfAlphabet: /^[a-zA-Z]*$/,
            allHalfAlphanumeric: /^[a-zA-Z0-9]*$/,
            allHalfKatakanaReg: /^[ｱ-ﾝｧ-ｫｬ-ｮｯｦ ﾞﾟ｡.ｰ､･'-]*$/,
            allFullKatakanaReg: /^[ァ-ー　。．ー、・’－ヴヽヾ]*$/,
            allHiragana: /^[ぁ-ん　ー ]*$/,
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
            return regexp.allHalfNumeric.test(text);
        }

        /**
         * 文字列が半角英字のみで構成された1文字以上の文字列かどうか判断する
         * @param text 解析対象の文字列
         */
        export function allHalfAlphabet(text: string) {
            return regexp.allHalfAlphabet.test(text);
        }

        /**
         * 文字列が半角英数字のみで構成された1文字以上の文字列かどうか判断する
         * @param text 解析対象の文字列
         */
        export function allHalfAlphanumeric(text: string) {
            return regexp.allHalfAlphanumeric.test(text);
        }

        /**
         * 文字列が半角カナのみで構成された1文字以上の文字列かどうか判断する
         * @param text 解析対象の文字列
         */
        export function allHalfKatakana(text: string) {
            return regexp.allHalfKatakanaReg.test(text);
        }

        /**
         * 文字列が全角カナのみで構成された1文字以上の文字列かどうか判断する
         * @param text 解析対象の文字列
         */
        export function allFullKatakana(text: string) {
            return regexp.allFullKatakanaReg.test(text);
        }

        /**
         * 文字列が半角文字のみで構成された1文字以上の文字列かどうか判断する
         * @param text 解析対象の文字列
         */
        export function allHalf(text: string) {
            return text.length === countHalf(text);
        }

        /**
         * 文字列が平仮名のみで構成された1文字以上の文字列かどうか判断する
         * @param text 解析対象の文字列
         */
        export function allHiragana(text: string) {
            return regexp.allHiragana.test(text);
        }

        /**
         * 文字列がカタカナのみで構成された1文字以上の文字列かどうか判断する
         * @param text 解析対象の文字列
         */
        export function allKatakana(text: string) {
            return regexp.allFullKatakanaReg.test(text);
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
                replaceFunction = function(m: any, k: any) { return args[k]; };
            }
            else {
                var workArgs = arguments;
                replaceFunction = function(m: any, k: any) { return workArgs[Number(k) + 1]; };
            }
            return format.replace(/\{(\w+)\}/g, replaceFunction);
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

            if (countHalf(paddingChar) !== 1) {
                throw new Error('paddingChar "' + paddingChar + '" is not single character');
            }

            var lengthOfSource = countHalf(text);
            var shortage = length - lengthOfSource;
            if (shortage <= 0) {
                return text;
            }

            var pad = new Array(shortage + 1).join(paddingChar);

            if (isPadLeft) {
                return pad + text;
            } else {
                return text + pad;
            }
        }

        export function replaceAll(originalString: string, find: string, replace: string) {
            return originalString.split(find).join(replace);
        }

        export function removeFromStart(originalString: string, charSet: string) {
            if (originalString.length === charSet.length) {
                return (originalString === charSet) ? "" : originalString;
            }
            var i = findLastContinousIndex(originalString, charSet, 0);
            return originalString.substr(i, originalString.length - i);
        }

        function findLastContinousIndex(originalString: string, charSet: string, startIndex: number) {
            if (startIndex >= originalString.length - 1) {
                return startIndex;
            }
            if (originalString.substr(startIndex, charSet.length) !== charSet) {
                return startIndex;
            } else {
                return findLastContinousIndex(originalString, charSet, startIndex + charSet.length);
            }
        }

        /**
         * Type of characters
         */
        export class CharType {
            viewName: string;
            width: number;
            validator: (text: string) => boolean;

            constructor(
                viewName: string,
                width: number,
                validator: (text: string) => boolean) {

                this.viewName = viewName;
                this.width = width;
                this.validator = validator;
            }

            validate(text: string) {
                var result = new ui.validation.ValidationResult();
                if (this.validator(text)) {
                    return true;
                } else {
                    return false;
                }
            }

            buildConstraintText(maxLength: number) {
                return this.viewName + this.getViewLength(maxLength) + '文字';
            }

            getViewLength(length) {
                return Math.floor(length / (this.width * 2));
            }
        }

        var charTypes: { [key: string]: CharType } = {
            AnyHalfWidth: new CharType(
                '半角',
                0.5,
                nts.uk.text.allHalf),
            AlphaNumeric: new CharType(
                '半角英数字',
                0.5,
                nts.uk.text.allHalfAlphanumeric),
            Alphabet: new CharType(
                '半角英字',
                0.5,
                nts.uk.text.allHalfAlphabet),
            Numeric: new CharType(
                '半角数字',
                0.5,
                nts.uk.text.allHalfNumeric),
            Any: new CharType(
                '全角',
                1,
                nts.uk.util.alwaysTrue),

        };

        export function getCharType(primitiveValueName: string): CharType {
            var constraint = __viewContext.primitiveValueConstraints[primitiveValueName];
            if (constraint === undefined)
                return null;
            if (constraint.charType === undefined)
                constraint.charType = "Any";
            var charType = charTypes[constraint.charType];
            if (charType === undefined) {
                throw new Error('invalid charTypeName: ' + constraint.charType);
            }

            return charType;
        }

        export function formatEmployeeCode(code: string, filldirection: string, fillcharacter: string, length: number): string {
            if (filldirection === "left")
                return padLeft(code, fillcharacter, length);
            else
                return padRight(code, fillcharacter, length);
        }

        export function splitOrPadRight(originalString: string, length: number, char?: string) {
            if (originalString === undefined || length > originalString.length) {
                originalString = text.padRight(originalString ? originalString : "", char ? char : " ", length);
            } else {
                originalString = originalString.substr(0, length);
            }
            return originalString;
        }

        function addSeperation(amount: string) {
            var leng = amount.indexOf(".") > -1 ? amount.indexOf(".") : amount.length;
            if (leng < 4) return amount;
            var result = amount.substring(leng);
            var num = parseInt(amount.substring(0, leng));
            var times = Math.floor(leng / 3);
            for (var i = 0; i < times; i++) {
                var block = num % 1000;
                if (i > 0)
                    result = padLeft("" + block, "0", 3) + "," + result;
                else result = padLeft("" + block, "0", 3) + result;
                num = Math.floor(num / 1000);
            }
            result = num % 1000 + "," + result;
            return result;
        }

        export function formatCurrency(amount: number, locale: string) {
            var result = addSeperation("" + amount);
            if (locale == 'en' || locale == 'EN') return "￥" + result;
            return result + "円";
        }

        export function reverseDirection(direction: string): string {
            if (direction === "left")
                return "right";
            else if (direction === "right")
                return "left";
            else if (direction === "top")
                return "bottom";
            else if (direction === "bottom")
                return "top";
        }
        
        export function getISO8601Format(format: string): string {
            if (format.toLowerCase() === "date")
                return "YYYY/MM/DD";
            if (format.toLowerCase() === "yearmonth")
                return "YYYY/MM";
            if (format.toLowerCase() === "time")
                return "HH:mm";
            if (format.toLowerCase() === "datetime")
                return "YYYY/MM/DD HH:mm";
            format = format.replace(/y/g, "Y");
            return format;
        }

        export class StringFormatter implements format.IFormatter {
            args: any;

            constructor(args: any) {
                this.args = args;
            }

            format(source: any): string {
                var constraintName = this.args.constraintName;
                if (constraintName === "EmployeeCode") {
                    var constraint = this.args.constraint;
                    var filldirection: string = this.args.editorOption.filldirection;
                    var fillcharacter: string = this.args.editorOption.fillcharacter;
                    var length: number = (constraint && constraint.maxLength) ? constraint.maxLength : 10;
                    return formatEmployeeCode(source, filldirection, fillcharacter, length);
                }
                return source;
            }
        }

        export class NumberFormatter implements format.IFormatter {

            option: any;

            constructor(option: any) {
                this.option = option;
            }

            format(source: any): string {
                return ntsNumber.formatNumber(source, this.option.option);
            }
        }

        export class TimeFormatter implements format.IFormatter {

            option: any;

            constructor(option: any) {
                this.option = option;
            }

            format(source: any): string {
                var result;
                if (this.option.inputFormat === "yearmonth") {
                    result = time.parseYearMonth(source);
                }
                else if (this.option.inputFormat === "time") {
                    result = time.parseTime(source, true);
                }
                else {
                    result = moment(source);
                    if (result.isValid())
                        return result.format(this.option.inputFormat);
                    return source;
                }
                
                if (result.success) 
                    return result.format();
                
                return source;
            }
        }
    }
}