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
                allHalfNumeric: /^\d*$/,
                allHalfAlphabet: /^[a-zA-Z]*$/,
                allHalfAlphanumeric: /^[a-zA-Z0-9]*$/,
                allHalfKatakanaReg: /^[ｱ-ﾝｧ-ｫｬ-ｮｯｦ ﾞﾟ｡.ｰ､･'-]*$/,
                allFullKatakanaReg: /^[ァ-ー　。．ー、・’－ヴヽヾ]*$/,
                allHiragana: /^[ぁ-ん　ー ]*$/,
                workplaceCode: /^[a-zA-Z0-9_-]{1,10}$/
            };
            function countHalf(text) {
                var count = 0;
                for (var i = 0; i < text.length; i++) {
                    var c = text.charCodeAt(i);
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
            function limitText(str, maxlength, index) {
                var idx = nts.uk.util.isNullOrUndefined(index) ? 0 : index;
                return str.substring(idx, findIdxFullHafl(str, maxlength, idx));
            }
            text_1.limitText = limitText;
            function findIdxFullHafl(text, max, index) {
                var count = 0;
                for (var i = index; i < text.length; i++) {
                    var c = text.charCodeAt(i);
                    var charLength = 2;
                    if ((0x20 <= c && c <= 0x7e) || (0xff61 <= c && c <= 0xff9f)) {
                        charLength = 1;
                    }
                    if (charLength + count <= max) {
                        count += charLength;
                    }
                    else {
                        return i;
                    }
                }
                return text.length - index;
            }
            function toOneByteAlphaNumberic(text) {
                return text.replace(/[！-～　]/g, function (s) {
                    if (s === "　") {
                        return String.fromCharCode(s.charCodeAt(0) - 12256);
                    }
                    return String.fromCharCode(s.charCodeAt(0) - 0xFEE0);
                });
            }
            text_1.toOneByteAlphaNumberic = toOneByteAlphaNumberic;
            function toTwoByteAlphaNumberic(text) {
                return text.replace(/[\!-\~ ]/g, function (s) {
                    if (s === " ") {
                        return String.fromCharCode(s.charCodeAt(0) + 12256);
                    }
                    return String.fromCharCode(s.charCodeAt(0) + 0xFEE0);
                });
            }
            text_1.toTwoByteAlphaNumberic = toTwoByteAlphaNumberic;
            function katakanaToHiragana(text) {
                text = text.replace(/[ァ-ヴ]/g, function (s) {
                    return String.fromCharCode(s.charCodeAt(0) - 0x60);
                }).replace(/ﾞ/g, '゛').replace(/ﾟ/g, '゜')
                    .replace(/(う゛)/g, 'ゔ').replace(/ヷ/g, 'わ゛')
                    .replace(/ヸ/g, 'ゐ゛').replace(/ヹ/g, 'ゑ゛')
                    .replace(/ヺ/g, 'を゛').replace(/(ヽ゛)/g, 'ゞ')
                    .replace(/ヽ/g, 'ゝ').replace(/ヾ/g, 'ゞ');
                return text;
            }
            text_1.katakanaToHiragana = katakanaToHiragana;
            function hiraganaToKatakana(text, opt) {
                text = text.replace(/[ぁ-ゔ]/g, function (s) {
                    return String.fromCharCode(s.charCodeAt(0) + 0x60);
                }).replace(/ﾞ/g, '゛').replace(/ﾟ/g, '゜')
                    .replace(/(ウ゛)/g, 'ヴ').replace(/(ワ゛)/g, 'ヷ')
                    .replace(/(ヰ゛)/g, 'ヸ').replace(/(ヱ゛)/g, 'ヹ')
                    .replace(/(ヲ゛)/g, 'ヺ').replace(/(ゝ゛)/g, 'ヾ')
                    .replace(/ゝ/g, 'ヽ').replace(/ゞ/g, 'ヾ');
                if (opt !== false) {
                    text = text.replace(/ゕ/g, 'ヵ').replace(/ゖ/g, 'ヶ');
                }
                return text;
            }
            text_1.hiraganaToKatakana = hiraganaToKatakana;
            function oneByteKatakanaToTwoByte(text) {
                var katakanaMap = {
                    'ｶﾞ': 'ガ', 'ｷﾞ': 'ギ', 'ｸﾞ': 'グ', 'ｹﾞ': 'ゲ', 'ｺﾞ': 'ゴ',
                    'ｻﾞ': 'ザ', 'ｼﾞ': 'ジ', 'ｽﾞ': 'ズ', 'ｾﾞ': 'ゼ', 'ｿﾞ': 'ゾ',
                    'ﾀﾞ': 'ダ', 'ﾁﾞ': 'ヂ', 'ﾂﾞ': 'ヅ', 'ﾃﾞ': 'デ', 'ﾄﾞ': 'ド',
                    'ﾊﾞ': 'バ', 'ﾋﾞ': 'ビ', 'ﾌﾞ': 'ブ', 'ﾍﾞ': 'ベ', 'ﾎﾞ': 'ボ',
                    'ﾊﾟ': 'パ', 'ﾋﾟ': 'ピ', 'ﾌﾟ': 'プ', 'ﾍﾟ': 'ペ', 'ﾎﾟ': 'ポ',
                    'ｳﾞ': 'ヴ', 'ﾜﾞ': 'ヷ', 'ｦﾞ': 'ヺ',
                    'ｱ': 'ア', 'ｲ': 'イ', 'ｳ': 'ウ', 'ｴ': 'エ', 'ｵ': 'オ',
                    'ｶ': 'カ', 'ｷ': 'キ', 'ｸ': 'ク', 'ｹ': 'ケ', 'ｺ': 'コ',
                    'ｻ': 'サ', 'ｼ': 'シ', 'ｽ': 'ス', 'ｾ': 'セ', 'ｿ': 'ソ',
                    'ﾀ': 'タ', 'ﾁ': 'チ', 'ﾂ': 'ツ', 'ﾃ': 'テ', 'ﾄ': 'ト',
                    'ﾅ': 'ナ', 'ﾆ': 'ニ', 'ﾇ': 'ヌ', 'ﾈ': 'ネ', 'ﾉ': 'ノ',
                    'ﾊ': 'ハ', 'ﾋ': 'ヒ', 'ﾌ': 'フ', 'ﾍ': 'ヘ', 'ﾎ': 'ホ',
                    'ﾏ': 'マ', 'ﾐ': 'ミ', 'ﾑ': 'ム', 'ﾒ': 'メ', 'ﾓ': 'モ',
                    'ﾔ': 'ヤ', 'ﾕ': 'ユ', 'ﾖ': 'ヨ',
                    'ﾗ': 'ラ', 'ﾘ': 'リ', 'ﾙ': 'ル', 'ﾚ': 'レ', 'ﾛ': 'ロ',
                    'ﾜ': 'ワ', 'ｦ': 'ヲ', 'ﾝ': 'ン',
                    'ｧ': 'ァ', 'ｨ': 'ィ', 'ｩ': 'ゥ', 'ｪ': 'ェ', 'ｫ': 'ォ',
                    'ｯ': 'ッ', 'ｬ': 'ャ', 'ｭ': 'ュ', 'ｮ': 'ョ',
                    '｡': '。', '､': '、', 'ｰ': 'ー', '｢': '「', '｣': '」', '･': '・'
                };
                var expression = new RegExp('(' + Object.keys(katakanaMap).join('|') + ')', 'g');
                return text.replace(expression, function (match) {
                    return katakanaMap[match];
                }).replace(/ﾞ/g, '゛').replace(/ﾟ/g, '゜');
            }
            text_1.oneByteKatakanaToTwoByte = oneByteKatakanaToTwoByte;
            function anyChar(text) {
                return {
                    probe: true,
                    messageId: 'FND_E_ANY'
                };
            }
            text_1.anyChar = anyChar;
            function allHalfNumeric(text) {
                return {
                    probe: regexp.allHalfNumeric.test(text),
                    messageId: 'FND_E_NUMERIC'
                };
            }
            text_1.allHalfNumeric = allHalfNumeric;
            function allHalfAlphabet(text) {
                return {
                    probe: regexp.allHalfAlphabet.test(text),
                    messageId: 'NO_MESSAGE'
                };
            }
            text_1.allHalfAlphabet = allHalfAlphabet;
            function allHalfAlphanumeric(text) {
                return {
                    probe: regexp.allHalfAlphanumeric.test(text),
                    messageId: 'FND_E_ALPHANUMERIC'
                };
            }
            text_1.allHalfAlphanumeric = allHalfAlphanumeric;
            function allHalfKatakana(text) {
                return {
                    probe: regexp.allHalfKatakanaReg.test(text),
                    messageId: 'NO_MESSAGE'
                };
            }
            text_1.allHalfKatakana = allHalfKatakana;
            function allFullKatakana(text) {
                return {
                    probe: regexp.allFullKatakanaReg.test(text),
                    messageId: 'FND_E_KANA'
                };
            }
            text_1.allFullKatakana = allFullKatakana;
            function allHalf(text) {
                return {
                    probe: text.length === countHalf(text),
                    messageId: 'FND_E_ANYHALFWIDTH'
                };
            }
            text_1.allHalf = allHalf;
            function allHiragana(text) {
                return {
                    probe: regexp.allHiragana.test(text),
                    messageId: 'NO_MESSAGE'
                };
            }
            text_1.allHiragana = allHiragana;
            function allKatakana(text) {
                return {
                    probe: regexp.allFullKatakanaReg.test(text),
                    messageId: 'NO_MESSAGE'
                };
            }
            text_1.allKatakana = allKatakana;
            function halfInt(text) {
                var val = parseFloat(text);
                var probe = false;
                if (val !== NaN && (val * 2) % 1 === 0)
                    probe = true;
                return {
                    probe: probe,
                    messageId: 'FND_E_HALFINT'
                };
            }
            text_1.halfInt = halfInt;
            function workplaceCode(text) {
                return {
                    probe: regexp.workplaceCode.test(text),
                    messageId: 'FND_E_ALPHANUMERIC'
                };
            }
            text_1.workplaceCode = workplaceCode;
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
            function toLowerCaseFirst(text) {
                return text.charAt(0).toLowerCase() + text.slice(1);
            }
            text_1.toLowerCaseFirst = toLowerCaseFirst;
            ;
            function toUpperCaseFirst(text) {
                return text.charAt(0).toUpperCase() + text.slice(1);
            }
            text_1.toUpperCaseFirst = toUpperCaseFirst;
            function toUpperCase(text) {
                return text.replace(/[a-z]/g, function (c) {
                    return String.fromCharCode(c.charCodeAt(0) - 0x20);
                });
            }
            text_1.toUpperCase = toUpperCase;
            function isNullOrEmpty(text) {
                var result = true;
                if (text !== null && text !== undefined) {
                    var convertValue = String(text);
                    result = convertValue.length === 0;
                }
                return result;
            }
            text_1.isNullOrEmpty = isNullOrEmpty;
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
            function padLeft(text, paddingChar, length) {
                return charPadding(text, paddingChar, true, length);
            }
            text_1.padLeft = padLeft;
            function padRight(text, paddingChar, length) {
                return charPadding(text, paddingChar, false, length);
            }
            text_1.padRight = padRight;
            function charPadding(text, paddingChar, isPadLeft, length) {
                var result;
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
                }
                else {
                    return text + pad;
                }
            }
            text_1.charPadding = charPadding;
            function replaceAll(originalString, find, replace) {
                return originalString.split(find).join(replace);
            }
            text_1.replaceAll = replaceAll;
            function removeFromStart(originalString, charSet) {
                if (originalString.length === charSet.length) {
                    return (originalString === charSet) ? "" : originalString;
                }
                var i = findLastContinousIndex(originalString, charSet, 0, true);
                return originalString.substr(i, originalString.length - i);
            }
            text_1.removeFromStart = removeFromStart;
            function removeFromEnd(originalString, charSet) {
                if (originalString.length === charSet.length) {
                    return (originalString === charSet) ? "" : originalString;
                }
                var i = findLastContinousIndex(originalString, charSet, originalString.length, false);
                return originalString.substr(0, i);
            }
            text_1.removeFromEnd = removeFromEnd;
            function findLastContinousIndex(originalString, charSet, startIndex, fromStart) {
                if (originalString.substring(startIndex, fromStart ? (startIndex + charSet.length) : (startIndex - charSet.length)) !== charSet) {
                    return startIndex;
                }
                else {
                    return findLastContinousIndex(originalString, charSet, fromStart ? (startIndex + charSet.length) : (startIndex - charSet.length), fromStart);
                }
            }
            var CharType = (function () {
                function CharType(viewName, width, validator) {
                    this.viewName = viewName;
                    this.width = width;
                    this.validator = validator;
                }
                CharType.prototype.validate = function (text) {
                    var result = new uk.ui.validation.ValidationResult();
                    var validateResult = this.validator(text);
                    if (validateResult === true || validateResult.probe) {
                        result.isValid = true;
                        result.errorMessage = validateResult.messageId;
                        result.errorCode = validateResult.messageId;
                    }
                    else {
                        result.fail(validateResult.messageId, validateResult.messageId);
                    }
                    return result;
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
                Any: new CharType('全角', 1, nts.uk.text.anyChar),
                Kana: new CharType('カナ', 1, nts.uk.text.allFullKatakana),
                HalfInt: new CharType('半整数', 0.5, nts.uk.text.halfInt),
                WorkplaceCode: new CharType('半角英数字', 0.5, nts.uk.text.workplaceCode)
            };
            function getCharType(primitiveValueName) {
                var constraint = __viewContext.primitiveValueConstraints[primitiveValueName];
                if (constraint === undefined)
                    return null;
                if (primitiveValueName === "WorkplaceCode" && !constraint.charType) {
                    constraint.charType = "WorkplaceCode";
                }
                else if (constraint.charType === undefined)
                    constraint.charType = "Any";
                var charType = charTypes[constraint.charType];
                if (charType === undefined) {
                    throw new Error('invalid charTypeName: ' + constraint.charType);
                }
                return charType;
            }
            text_1.getCharType = getCharType;
            function getCharTypeByType(charTypeName) {
                var charType = charTypes[charTypeName];
                if (charType === undefined) {
                    throw new Error('invalid charTypeName: ' + charTypeName);
                }
                return charType;
            }
            text_1.getCharTypeByType = getCharTypeByType;
            function formatCode(code, filldirection, fillcharacter, length) {
                if (filldirection === "left")
                    return padLeft(code, fillcharacter, length);
                else
                    return padRight(code, fillcharacter, length);
            }
            text_1.formatCode = formatCode;
            function splitOrPadRight(originalString, length, char) {
                if (originalString === undefined || length > originalString.length) {
                    originalString = text.padRight(originalString ? originalString : "", char ? char : " ", length);
                }
                else {
                    originalString = originalString.substr(0, length);
                }
                return originalString;
            }
            text_1.splitOrPadRight = splitOrPadRight;
            function addSeperation(amount) {
                var leng = amount.indexOf(".") > -1 ? amount.indexOf(".") : amount.length;
                if (leng < 4)
                    return amount;
                var result = amount.substring(leng);
                var num = parseInt(amount.substring(0, leng));
                var times = Math.floor(leng / 3);
                for (var i = 0; i < times; i++) {
                    var block = num % 1000;
                    if (i > 0)
                        result = padLeft("" + block, "0", 3) + "," + result;
                    else
                        result = padLeft("" + block, "0", 3) + result;
                    num = Math.floor(num / 1000);
                }
                result = num % 1000 + "," + result;
                return result;
            }
            function formatCurrency(amount, locale) {
                var result = addSeperation("" + amount);
                if (locale == 'en' || locale == 'EN')
                    return "￥" + result;
                return result + "円";
            }
            text_1.formatCurrency = formatCurrency;
            function reverseDirection(direction) {
                if (direction === "left")
                    return "right";
                else if (direction === "right")
                    return "left";
                else if (direction === "top")
                    return "bottom";
                else if (direction === "bottom")
                    return "top";
            }
            text_1.reverseDirection = reverseDirection;
            function getISOFormat(format) {
                format = uk.util.orDefault(format, "ISO");
                if (format.toLowerCase() === "iso")
                    return "YYYY-MM-DD[T]HH:mm:ss.SSS[Z]";
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
            text_1.getISOFormat = getISOFormat;
            var StringFormatter = (function () {
                function StringFormatter(args) {
                    this.args = args;
                }
                StringFormatter.prototype.format = function (source) {
                    var constraintName = this.args.constraintName;
                    var autofill = this.args.editorOption.autofill;
                    if (!uk.util.isNullOrEmpty(source)) {
                        if (autofill === true || constraintName === "EmployeeCode") {
                            var constraint = this.args.constraint;
                            var filldirection = this.args.editorOption.filldirection;
                            var fillcharacter = this.args.editorOption.fillcharacter;
                            var length = (constraint && constraint.maxLength) ? constraint.maxLength : 10;
                            return formatCode(source, filldirection, fillcharacter, length);
                        }
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
                    return nts.uk.util.isNullOrEmpty(source) ? (!nts.uk.util.isNullOrEmpty(this.option.option.defaultValue)
                        ? this.option.option.defaultValue : source)
                        : uk.ntsNumber.formatNumber(source, this.option.option);
                };
                return NumberFormatter;
            }());
            text_1.NumberFormatter = NumberFormatter;
            var TimeFormatter = (function () {
                function TimeFormatter(option) {
                    this.option = option;
                }
                TimeFormatter.prototype.format = function (source) {
                    if (nts.uk.util.isNullOrEmpty(source)) {
                        return "";
                    }
                    var result;
                    if (this.option.mode === "time") {
                        if (this.option.inputFormat.indexOf("s") >= 0) {
                            result = uk.time.parseTimeWithSecond(source, true);
                        }
                        else {
                            result = uk.time.parseTime(source, true);
                        }
                    }
                    else {
                        if (this.option.inputFormat === "yearmonth") {
                            result = uk.time.parseYearMonth(source);
                        }
                        else {
                            result = moment(source, "YYYYMMDD");
                            if (result.isValid()) {
                                var format = getISOFormat(this.option.inputFormat);
                                return result.format(format);
                            }
                            return source;
                        }
                    }
                    if (result.success)
                        return result.format();
                    return source;
                };
                return TimeFormatter;
            }());
            text_1.TimeFormatter = TimeFormatter;
            var TimeWithDayFormatter = (function () {
                function TimeWithDayFormatter(option) {
                    this.option = option;
                }
                TimeWithDayFormatter.prototype.format = function (source) {
                    if (nts.uk.util.isNullOrEmpty(source) || !isFinite(source)) {
                        return source;
                    }
                    var timeWithDayAttr = uk.time.minutesBased.clock.dayattr.create(source);
                    return this.option.timeWithDay ? timeWithDayAttr.fullText : timeWithDayAttr.shortText;
                };
                return TimeWithDayFormatter;
            }());
            text_1.TimeWithDayFormatter = TimeWithDayFormatter;
            var NumberUnit = (function () {
                function NumberUnit(unitID, unitText, position, language) {
                    this.unitID = unitID;
                    this.unitText = unitText;
                    this.position = position;
                    this.language = language;
                }
                return NumberUnit;
            }());
            text_1.NumberUnit = NumberUnit;
            var units = {
                "JPY": {
                    "ja": new NumberUnit("JPY", "円", "right", "ja"),
                    "en": new NumberUnit("JPY", "\u00A5", "left", "en")
                },
                "PERCENT": {
                    "ja": new NumberUnit("PERCENT", "%", "right", "ja"),
                    "en": new NumberUnit("PERCENT", "%", "right", "en")
                },
                "DAYS": {
                    "ja": new NumberUnit("DAYS", "日", "right", "ja")
                },
                "MONTHS": {
                    "ja": new NumberUnit("MONTHS", "ヶ月", "right", "ja")
                },
                "YEARS": {
                    "ja": new NumberUnit("YEARS", "年", "right", "ja")
                },
                "FIS_MONTH": {
                    "ja": new NumberUnit("FIS_MONTH", "月度", "right", "ja")
                },
                "FIS_YEAR": {
                    "ja": new NumberUnit("FIS_YEAR", "年度", "right", "ja")
                },
                "TIMES": {
                    "ja": new NumberUnit("TIMES", "回", "right", "ja")
                },
                "AGE": {
                    "ja": new NumberUnit("AGE", "歳", "right", "ja")
                }
            };
            function getNumberUnit(unitId) {
                return units[unitId][systemLanguage];
            }
            text_1.getNumberUnit = getNumberUnit;
        })(text = uk.text || (uk.text = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=text.js.map