var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var text;
        (function (text_1) {
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
            function countHalf(text) {
                var count = 0;
                for (var i = 0; i < text.length; i++) {
                    var c = text.charCodeAt(i);
                    // 0x20 ～ 0x80: 半角記号と半角英数字
                    // 0xff61 ～ 0xff9f: 半角カタカナ
                    if ((0x20 <= c && c <= 0x7e) || (0xff61 <= c && c <= 0xff9f)) {
                        count += 1;
                    }
                    else {
                        count += 2;
                    }
                }
                return count;
            }
            text_1.countHalf = countHalf;
            /**
             * 文字列が半角数字のみで構成された1文字以上の文字列かどうか判断する
             * @param text 解析対象の文字列
             */
            function allHalfNumeric(text) {
                return text !== '' && regexp.allHalfNumeric.test(text);
            }
            text_1.allHalfNumeric = allHalfNumeric;
            /**
             * 文字列が半角英字のみで構成された1文字以上の文字列かどうか判断する
             * @param text 解析対象の文字列
             */
            function allHalfAlphabet(text) {
                return text !== '' && regexp.allHalfAlphabet.test(text);
            }
            text_1.allHalfAlphabet = allHalfAlphabet;
            /**
             * 文字列が半角英数字のみで構成された1文字以上の文字列かどうか判断する
             * @param text 解析対象の文字列
             */
            function allHalfAlphanumeric(text) {
                return text !== '' && regexp.allHalfAlphanumeric.test(text);
            }
            text_1.allHalfAlphanumeric = allHalfAlphanumeric;
            /**
             * 文字列が半角カナのみで構成された1文字以上の文字列かどうか判断する
             * @param text 解析対象の文字列
             */
            function allHalfKatakana(text) {
                return text !== '' && regexp.allHalfKatakanaReg.test(text);
            }
            text_1.allHalfKatakana = allHalfKatakana;
            /**
             * 文字列が全角カナのみで構成された1文字以上の文字列かどうか判断する
             * @param text 解析対象の文字列
             */
            function allFullKatakana(text) {
                return text !== '' && regexp.allFullKatakanaReg.test(text);
            }
            text_1.allFullKatakana = allFullKatakana;
            /**
             * 文字列が半角文字のみで構成された1文字以上の文字列かどうか判断する
             * @param text 解析対象の文字列
             */
            function allHalf(text) {
                return text !== '' && text.length === countHalf(text);
            }
            text_1.allHalf = allHalf;
            /**
             * 文字列が平仮名のみで構成された1文字以上の文字列かどうか判断する
             * @param text 解析対象の文字列
             */
            function allHiragana(text) {
                return text !== '' && regexp.allHiragana.test(text);
            }
            text_1.allHiragana = allHiragana;
            /**
             * 文字列がカタカナのみで構成された1文字以上の文字列かどうか判断する
             * @param text 解析対象の文字列
             */
            function allKatakana(text) {
                return text !== '' && regexp.allFullKatakanaReg.test(text);
            }
            text_1.allKatakana = allKatakana;
            /**
             * 文字列中のHTML記号をサニタイズする
             * @param text 変換対象の文字列
             */
            function htmlEncode(text) {
                var element = document.createElement('pre');
                if (typeof element.textContent !== 'undefined') {
                    element.textContent = text;
                }
                else {
                    element.innerText = text;
                }
                return element.innerHTML;
            }
            text_1.htmlEncode = htmlEncode;
            /**
             * 1文字目のみ小文字に変換する
             * @param text 変換対象の文字列
             */
            function toLowerCaseFirst(text) {
                return text.charAt(0).toLowerCase() + text.slice(1);
            }
            text_1.toLowerCaseFirst = toLowerCaseFirst;
            ;
            /**
             * 1文字目のみ大文字に変換する
             * @param text 変換対象の文字列
             */
            function toUpperCaseFirst(text) {
                return text.charAt(0).toUpperCase() + text.slice(1);
            }
            text_1.toUpperCaseFirst = toUpperCaseFirst;
            /**
            * 指定された文字列が、null、undefined、Emptyか判定する
            * @param text 判定対象の文字列
            */
            function isNullOrEmpty(text) {
                var result = true;
                if (text !== null && text !== undefined) {
                    var convertValue = String(text);
                    result = convertValue.length === 0;
                }
                return result;
            }
            text_1.isNullOrEmpty = isNullOrEmpty;
            /**
            * 指定した文字列の各書式項目を、対応するオブジェクトの値と等価のテキストに置換する
            * @param text 書式文字列
            * @param args 置換の文字列（配列可）
            */
            function format(format) {
                var args = [];
                for (var _i = 1; _i < arguments.length; _i++) {
                    args[_i - 1] = arguments[_i];
                }
                var replaceFunction = undefined;
                if (typeof args === 'object') {
                    replaceFunction = function (m, k) { return args[k]; };
                }
                else {
                    var workArgs = arguments;
                    replaceFunction = function (m, k) { return workArgs[Number(k) + 1]; };
                }
                return format.replace(/\{(\w+)\}/g, replaceFunction);
            }
            text_1.format = format;
            /**
            * 日付をフォーマットする
            * @param  {Date}   date     日付
            * @param  {String} [format] フォーマット
            * @return {String}          フォーマット済み日付
            */
            function formatDate(date, format) {
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
            text_1.formatDate = formatDate;
            /**
            * 変換文字列の先頭に、文字数分の指定文字列を追加する
            * @param text 変換対象の文字列
            * @param paddingChar 指定文字列
            * @param length 文字数
            */
            function padLeft(text, paddingChar, length) {
                return charPadding(text, paddingChar, true, length);
            }
            text_1.padLeft = padLeft;
            /**
            * 変換文字列の末尾に、文字数分の指定文字列を追加する
            * @param text 変換対象の文字列
            * @param paddingChar 指定文字列
            * @param length 文字数
            */
            function padRight(text, paddingChar, length) {
                return charPadding(text, paddingChar, false, length);
            }
            text_1.padRight = padRight;
            /**
            * 指定した文字列に、指定した文字列数分、指定文字列を追加する
            * @param text 変換対象の文字列
            * @param paddingChar 埋める文字列
            * @param isPadLeft 左埋めフラグ（false：右埋め）
            * @param length 文字数
            */
            function charPadding(text, paddingChar, isPadLeft, length) {
                var result;
                if (length === 0) {
                    return '';
                }
                if (isPadLeft) {
                    result = (new Array(length).join(paddingChar) + text).slice(-length);
                }
                else {
                    result = (text + new Array(length).join(paddingChar)).slice(0, length);
                }
                return result;
            }
            text_1.charPadding = charPadding;
            function formatYearMonth(yearMonth) {
                var result;
                var num = parseInt(String(yearMonth));
                var year = String(Math.floor(num / 100));
                var month = charPadding(String(num % 100), '0', true, 2);
                result = year + '/' + month;
                return result;
            }
            text_1.formatYearMonth = formatYearMonth;
            function formatSeconds(seconds, formatOption) {
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
            text_1.formatSeconds = formatSeconds;
        })(text = uk.text || (uk.text = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
