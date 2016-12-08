var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var format;
        (function (format) {
            var NoFormatter = (function () {
                function NoFormatter() {
                }
                NoFormatter.prototype.format = function (source) {
                    return source;
                };
                return NoFormatter;
            }());
            format.NoFormatter = NoFormatter;
        })(format = uk.format || (uk.format = {}));
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
            function replaceAll(str, find, replace) {
                return str.split(find).join(replace);
            }
            text_1.replaceAll = replaceAll;
            function removeFromStart(str, charSet) {
                var i = findLastContinousIndex(str, charSet, 0);
                return str.substr(i + 1, str.length - i - 1);
            }
            text_1.removeFromStart = removeFromStart;
            function findLastContinousIndex(str, charSet, startIndex) {
                if (startIndex === str.length - 1) {
                    return startIndex;
                }
                if (str.substr(startIndex, charSet.length) !== charSet) {
                    return startIndex - charSet.length;
                }
                else {
                    return findLastContinousIndex(str, charSet, startIndex + charSet.length);
                }
            }
            /**
             * Type of characters
             */
            var CharType = (function () {
                function CharType(viewName, width, validator) {
                    this.viewName = viewName;
                    this.width = width;
                    this.validator = validator;
                }
                CharType.prototype.validate = function (text) {
                    var result = new uk.ui.validation.ValidationResult();
                    if (this.validator(text)) {
                        return true;
                    }
                    else {
                        return false;
                    }
                };
                CharType.prototype.buildConstraintText = function (maxLength) {
                    return this.viewName + this.getViewLength(maxLength) + '文字';
                };
                CharType.prototype.getViewLength = function (length) {
                    return Math.floor(length / (this.width * 2));
                };
                return CharType;
            }());
            text_1.CharType = CharType;
            var charTypes = {
                AnyHalfWidth: new CharType('半角', 0.5, nts.uk.text.allHalf),
                AlphaNumeric: new CharType('半角英数字', 0.5, nts.uk.text.allHalfAlphanumeric),
                Alphabet: new CharType('半角英字', 0.5, nts.uk.text.allHalfAlphabet),
                Numeric: new CharType('半角数字', 0.5, nts.uk.text.allHalfNumeric),
                Any: new CharType('全角', 1, nts.uk.util.alwaysTrue),
            };
            function getCharType(primitiveValueName) {
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
            text_1.getCharType = getCharType;
            function formatEmployeeCode(code, filldirection, fillcharacter, length) {
                if (filldirection === "left")
                    return padLeft(code, fillcharacter, length);
                else
                    return padRight(code, fillcharacter, length);
            }
            text_1.formatEmployeeCode = formatEmployeeCode;
            function splitString(str, decimallength, char) {
                if (str === undefined || decimallength > str.length) {
                    str = text.padRight(str ? str : "", char, str ? decimallength : decimallength + 1);
                }
                else {
                    str = str.substr(0, decimallength);
                }
                return str;
            }
            text_1.splitString = splitString;
            var StringFormatter = (function () {
                function StringFormatter(args) {
                    this.args = args;
                }
                StringFormatter.prototype.format = function (source) {
                    var constraintName = this.args.constraintName;
                    if (constraintName === "EmployeeCode") {
                        var constraint = this.args.constraint;
                        var filldirection = this.args.editorOption.filldirection;
                        var fillcharacter = this.args.editorOption.fillcharacter;
                        var length = (constraint && constraint.maxLength) ? constraint.maxLength : 10;
                        return formatEmployeeCode(source, filldirection, fillcharacter, length);
                    }
                    return source;
                };
                return StringFormatter;
            }());
            text_1.StringFormatter = StringFormatter;
            var NumberFormatter = (function () {
                function NumberFormatter(option) {
                    this.option = option;
                }
                NumberFormatter.prototype.format = function (source) {
                    return uk.ntsNumber.formatNumber(source, this.option.option);
                };
                return NumberFormatter;
            }());
            text_1.NumberFormatter = NumberFormatter;
            var TimeFormatter = (function () {
                function TimeFormatter(option) {
                    this.option = option;
                }
                TimeFormatter.prototype.format = function (source) {
                    var result;
                    if (this.option.option.inputFormat === "yearmonth") {
                        result = uk.time.parseYearMonth(source);
                    }
                    else if (this.option.option.inputFormat === "time") {
                        result = uk.time.parseTime(source, true);
                    }
                    else {
                        result = uk.time.ResultParseTime.failed();
                    }
                    if (result.success) {
                        return result.format();
                    }
                    return source;
                };
                return TimeFormatter;
            }());
            text_1.TimeFormatter = TimeFormatter;
        })(text = uk.text || (uk.text = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
