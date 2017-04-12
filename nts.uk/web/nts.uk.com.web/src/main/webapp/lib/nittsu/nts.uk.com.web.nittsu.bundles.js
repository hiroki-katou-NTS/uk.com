var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var KeyCodes;
        (function (KeyCodes) {
            KeyCodes.Tab = 9;
        })(KeyCodes = uk.KeyCodes || (uk.KeyCodes = {}));
        var util;
        (function (util) {
            function alwaysTrue() {
                return true;
            }
            util.alwaysTrue = alwaysTrue;
            function findIndex(arr, value, key) {
                for (var i = 0; i < arr.length; i++) {
                    var item = arr[i];
                    if (item[key] === value)
                        return i;
                }
                return -1;
            }
            util.findIndex = findIndex;
            function addToArray(node, arr) {
                arr.push(node);
            }
            function visitDfs(node, func, childField, arr) {
                if (func) {
                    if (arr)
                        func(node, arr);
                    else
                        func(node);
                }
                var childs = node[childField];
                $.each(childs, function (child) {
                    visitDfs(childs[child], func, childField, arr);
                });
            }
            util.visitDfs = visitDfs;
            function flatArray(arr, childField) {
                var flatArr = [];
                if (!childField)
                    return arr;
                for (var i = 0; i < arr.length; i++) {
                    var item = arr[i];
                    visitDfs(item, addToArray, childField, flatArr);
                }
                return flatArr;
            }
            util.flatArray = flatArray;
            function searchArray(arr, searchTerm, fields, childField) {
                if (!arr) {
                    return [];
                }
                if (!(searchTerm instanceof String)) {
                    searchTerm = "" + searchTerm;
                }
                var flatArr = flatArray(arr, childField);
                var filter = searchTerm.toLowerCase();
                if (!filter) {
                    return flatArr;
                }
                var filtered = flatArr.filter(function (item) {
                    var i = fields.length;
                    while (i--) {
                        var prop = fields[i];
                        var strProp = ("" + item[prop]).toLocaleLowerCase();
                        if (strProp.indexOf(filter) !== -1) {
                            return true;
                        }
                        ;
                    }
                    return false;
                });
                return filtered;
            }
            util.searchArray = searchArray;
            function nextSelectionSearch(selected, arr, selectedKey, isArray) {
                var current = null;
                if (isArray) {
                    if (selected.length > 0)
                        current = selected[0];
                }
                else if (selected !== undefined && selected !== '' && selected !== null) {
                    current = selected;
                }
                if (arr.length > 0) {
                    if (current) {
                        for (var i = 0; i < arr.length - 1; i++) {
                            var item = arr[i];
                            if (item[selectedKey] === current)
                                return arr[i + 1][selectedKey];
                        }
                    }
                    if (selectedKey)
                        return arr[0][selectedKey];
                    return arr[0];
                }
                return undefined;
            }
            util.nextSelectionSearch = nextSelectionSearch;
            function isNullOrUndefined(target) {
                return target === null || target === undefined;
            }
            util.isNullOrUndefined = isNullOrUndefined;
            function randomId() {
                return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                    var r = Math.random() * 16 | 0;
                    return ((c == 'x') ? r : (r & 0x3 | 0x8)).toString(16);
                });
            }
            util.randomId = randomId;
            function isInFrame() {
                return window.parent != window;
            }
            util.isInFrame = isInFrame;
            function orDefault(valueMaybeEmpty, defaultValue) {
                return isNullOrUndefined(valueMaybeEmpty) ? defaultValue : valueMaybeEmpty;
            }
            util.orDefault = orDefault;
            function isIn(actual, expects) {
                for (var i = 0; i < expects.length; i++) {
                    if (actual === expects[i])
                        return true;
                }
                return false;
            }
            util.isIn = isIn;
            ;
            function createTreeFromString(original, openChar, closeChar, seperatorChar, operatorChar) {
                var result = convertToTree(original, openChar, closeChar, seperatorChar, 1, operatorChar).result;
                return result;
            }
            util.createTreeFromString = createTreeFromString;
            function moveToParentIfEmpty(tree) {
                var result = [];
                _.forEach(tree, function (e) {
                    if (e.children.length > 0) {
                        e.children = moveToParentIfEmpty(e.children);
                        if (uk.text.isNullOrEmpty(e.value)) {
                            result = result.concat(e.children);
                        }
                        else {
                            result.push(e);
                        }
                    }
                    else {
                        result.push(e);
                    }
                });
                return result;
            }
            function convertToTree(original, openChar, closeChar, separatorChar, index, operatorChar) {
                var result = [];
                while (original.trim().length > 0) {
                    var firstOpenIndex = original.indexOf(openChar);
                    if (firstOpenIndex < 0) {
                        var values = original.split(separatorChar);
                        _.forEach(values, function (value) {
                            var data = splitByArray(value, operatorChar.slice());
                            _.each(data, function (v) {
                                var object = new TreeObject();
                                object.value = v;
                                object.children = [];
                                object.isOperator = operatorChar.indexOf(v) >= 0;
                                result.push(object);
                            });
                        });
                        return {
                            "result": result,
                            "index": index
                        };
                    }
                    else {
                        var object = new TreeObject();
                        object.value = original.substring(0, firstOpenIndex).trim();
                        object.index = index;
                        var closeIndex = findIndexOfCloseChar(original, openChar, closeChar, firstOpenIndex);
                        if (closeIndex >= 0) {
                            index++;
                            var res = convertToTree(original.substring(firstOpenIndex + 1, closeIndex).trim(), openChar, closeChar, separatorChar, index, operatorChar);
                            object.children = res.result;
                            index = res.index++;
                            result.push(object);
                            var firstSeperatorIndex = original.indexOf(separatorChar, closeIndex);
                            if (firstSeperatorIndex >= 0) {
                                original = original.substring(firstSeperatorIndex + 1, original.length).trim();
                            }
                            else {
                                return {
                                    "result": result,
                                    "index": index
                                };
                            }
                        }
                        else {
                            return {
                                "result": result,
                                "index": index
                            };
                        }
                    }
                }
                return {
                    "result": result,
                    "index": index
                };
            }
            function splitByArray(original, operatorChar) {
                var temp = [];
                var result = [];
                if (original.trim().length <= 0) {
                    return temp;
                }
                if (operatorChar.length <= 0) {
                    return [original];
                }
                var operator = operatorChar.shift();
                while (original.trim().length > 0) {
                    var index = original.indexOf(operator);
                    if (index >= 0) {
                        temp.push(original.substring(0, index).trim());
                        temp.push(original.substring(index, index + 1).trim());
                        original = original.substring(index + 1, original.length).trim();
                    }
                    else {
                        temp.push(original);
                        break;
                    }
                }
                _.each(temp, function (value) {
                    result = result.concat(splitByArray(value, operatorChar));
                });
                return result;
            }
            function findIndexOfCloseChar(original, openChar, closeChar, firstOpenIndex) {
                var openCount = 0;
                var closeCount = 0;
                for (var i = firstOpenIndex; i < original.length; i++) {
                    if (original.charAt(i) === openChar) {
                        openCount++;
                    }
                    else if (original.charAt(i) === closeChar) {
                        closeCount++;
                    }
                    if (openCount > 0 && openCount === closeCount) {
                        return i;
                    }
                }
                return -1;
            }
            var TreeObject = (function () {
                function TreeObject(value, children, index, isOperator) {
                    var self = this;
                    self.value = value;
                    self.children = children;
                    self.index = index;
                    self.isOperator = isOperator;
                }
                return TreeObject;
            }());
            util.TreeObject = TreeObject;
            var optional;
            (function (optional) {
                function of(value) {
                    return new Optional(value);
                }
                optional.of = of;
                function empty() {
                    return new Optional(null);
                }
                optional.empty = empty;
                var Optional = (function () {
                    function Optional(value) {
                        this.value = orDefault(value, null);
                    }
                    Optional.prototype.ifPresent = function (consumer) {
                        if (this.isPresent) {
                            consumer(this.value);
                        }
                        return this;
                    };
                    Optional.prototype.ifEmpty = function (action) {
                        if (!this.isPresent) {
                            action();
                        }
                        return this;
                    };
                    Optional.prototype.map = function (mapper) {
                        return this.isPresent ? of(mapper(this.value)) : empty();
                    };
                    Optional.prototype.isPresent = function () {
                        return this.value !== null;
                    };
                    Optional.prototype.get = function () {
                        if (!this.isPresent) {
                            throw new Error('not present');
                        }
                        return this.value;
                    };
                    Optional.prototype.orElse = function (stead) {
                        return this.isPresent ? this.value : stead;
                    };
                    Optional.prototype.orElseThrow = function (errorBuilder) {
                        if (!this.isPresent) {
                            throw errorBuilder();
                        }
                    };
                    return Optional;
                }());
                optional.Optional = Optional;
            })(optional = util.optional || (util.optional = {}));
            var Range = (function () {
                function Range(start, end) {
                    if (start > end) {
                        throw new Error('start is larger than end');
                    }
                    this.start = start;
                    this.end = end;
                }
                Range.prototype.contains = function (value) {
                    return this.start <= value && value <= this.end;
                };
                Range.prototype.greaterThan = function (value) {
                    return value < this.start;
                };
                Range.prototype.greaterThanOrEqualTo = function (value) {
                    return value <= this.start;
                };
                Range.prototype.lessThan = function (value) {
                    return this.end < value;
                };
                Range.prototype.lessThanOrEqualTo = function (value) {
                    return this.end <= value;
                };
                Range.prototype.distanceFrom = function (value) {
                    if (this.greaterThan(value)) {
                        return value - this.start;
                    }
                    else if (this.lessThan(value)) {
                        return value - this.end;
                    }
                    else {
                        return 0;
                    }
                };
                return Range;
            }());
            util.Range = Range;
        })(util = uk.util || (uk.util = {}));
        var WebStorageWrapper = (function () {
            function WebStorageWrapper(nativeStorage) {
                this.nativeStorage = nativeStorage;
            }
            WebStorageWrapper.prototype.setItem = function (key, value) {
                if (value === undefined) {
                    return;
                }
                this.nativeStorage.setItem(key, value);
            };
            WebStorageWrapper.prototype.setItemAsJson = function (key, value) {
                this.setItem(key, JSON.stringify(value));
            };
            WebStorageWrapper.prototype.containsKey = function (key) {
                return this.getItem(key) !== null;
            };
            ;
            WebStorageWrapper.prototype.getItem = function (key) {
                var value = this.nativeStorage.getItem(key);
                if (value === null || value === undefined || value === 'undefined') {
                    return util.optional.empty();
                }
                return util.optional.of(value);
            };
            WebStorageWrapper.prototype.getItemAndRemove = function (key) {
                var item = this.getItem(key);
                this.removeItem(key);
                return item;
            };
            WebStorageWrapper.prototype.removeItem = function (key) {
                this.nativeStorage.removeItem(key);
            };
            WebStorageWrapper.prototype.clear = function () {
                this.nativeStorage.clear();
            };
            return WebStorageWrapper;
        }());
        uk.WebStorageWrapper = WebStorageWrapper;
        var deferred;
        (function (deferred) {
            function repeat(configurator) {
                var conf = repeater.createConfiguration();
                configurator(conf);
                return repeater.begin(conf);
            }
            deferred.repeat = repeat;
            var repeater;
            (function (repeater) {
                function begin(conf) {
                    return conf.run();
                }
                repeater.begin = begin;
                function createConfiguration() {
                    return new Configuration();
                }
                repeater.createConfiguration = createConfiguration;
                var Configuration = (function () {
                    function Configuration() {
                        this.pauseMilliseconds = 0;
                    }
                    Configuration.prototype.task = function (taskFunction) {
                        this.taskFunction = taskFunction;
                        return this;
                    };
                    Configuration.prototype.while = function (whileCondition) {
                        this.whileCondition = whileCondition;
                        return this;
                    };
                    Configuration.prototype.pause = function (pauseMilliseconds) {
                        this.pauseMilliseconds = pauseMilliseconds;
                        return this;
                    };
                    Configuration.prototype.run = function () {
                        var dfd = $.Deferred();
                        this.repeat(dfd);
                        return dfd.promise();
                    };
                    Configuration.prototype.repeat = function (dfd) {
                        var _this = this;
                        this.taskFunction().done(function (res) {
                            if (_this.whileCondition(res)) {
                                setTimeout(function () { return _this.repeat(dfd); }, _this.pauseMilliseconds);
                            }
                            else {
                                dfd.resolve(res);
                            }
                        }).fail(function (res) {
                            dfd.reject(res);
                        });
                    };
                    return Configuration;
                }());
            })(repeater || (repeater = {}));
        })(deferred = uk.deferred || (uk.deferred = {}));
        uk.sessionStorage = new WebStorageWrapper(window.sessionStorage);
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
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
            function allHalfNumeric(text) {
                return regexp.allHalfNumeric.test(text);
            }
            text_1.allHalfNumeric = allHalfNumeric;
            function allHalfAlphabet(text) {
                return regexp.allHalfAlphabet.test(text);
            }
            text_1.allHalfAlphabet = allHalfAlphabet;
            function allHalfAlphanumeric(text) {
                return regexp.allHalfAlphanumeric.test(text);
            }
            text_1.allHalfAlphanumeric = allHalfAlphanumeric;
            function allHalfKatakana(text) {
                return regexp.allHalfKatakanaReg.test(text);
            }
            text_1.allHalfKatakana = allHalfKatakana;
            function allFullKatakana(text) {
                return regexp.allFullKatakanaReg.test(text);
            }
            text_1.allFullKatakana = allFullKatakana;
            function allHalf(text) {
                return text.length === countHalf(text);
            }
            text_1.allHalf = allHalf;
            function allHiragana(text) {
                return regexp.allHiragana.test(text);
            }
            text_1.allHiragana = allHiragana;
            function allKatakana(text) {
                return regexp.allFullKatakanaReg.test(text);
            }
            text_1.allKatakana = allKatakana;
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
                var i = findLastContinousIndex(originalString, charSet, 0);
                return originalString.substr(i, originalString.length - i);
            }
            text_1.removeFromStart = removeFromStart;
            function findLastContinousIndex(originalString, charSet, startIndex) {
                if (startIndex >= originalString.length - 1) {
                    return startIndex;
                }
                if (originalString.substr(startIndex, charSet.length) !== charSet) {
                    return startIndex;
                }
                else {
                    return findLastContinousIndex(originalString, charSet, startIndex + charSet.length);
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
                    if (this.option.inputFormat === "yearmonth") {
                        result = uk.time.parseYearMonth(source);
                    }
                    else if (this.option.inputFormat === "time") {
                        result = uk.time.parseTime(source, true);
                    }
                    else {
                        result = moment(source, "YYYYMMDD");
                        if (result.isValid()) {
                            var format = getISOFormat(this.option.inputFormat);
                            return result.format(format);
                        }
                        return source;
                    }
                    if (result.success)
                        return result.format();
                    return source;
                };
                return TimeFormatter;
            }());
            text_1.TimeFormatter = TimeFormatter;
        })(text = uk.text || (uk.text = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ntsNumber;
        (function (ntsNumber) {
            function isInteger(value, option) {
                if (option !== undefined && option.groupseperator() !== undefined) {
                    value = isInteger(value) ? value : uk.text.replaceAll(value.toString(), option.groupseperator(), '');
                }
                return !isNaN(value) && parseInt(value) == value && !isNaN(parseInt(value, 10));
            }
            function isDecimal(value, option) {
                if (option !== undefined) {
                    var seperator = typeof option.groupseperator === 'function' ? option.groupseperator() : option.groupseperator;
                    value = isDecimal(value) || seperator === undefined ? value : uk.text.replaceAll(value.toString(), seperator, '');
                }
                return !isNaN(value) && parseFloat(value) == value && !isNaN(parseFloat(value));
            }
            function isNumber(value, isDecimalValue, option) {
                if (isDecimalValue) {
                    return isDecimal(value, option);
                }
                else {
                    return isInteger(value, option);
                }
            }
            ntsNumber.isNumber = isNumber;
            ntsNumber.trunc = (typeof Math.trunc === 'function') ? Math.trunc : function (value) { return value > 0 ? Math.floor(value) : Math.ceil(value); };
            function getDecimal(value, scale) {
                var scaleX = Math.pow(10, scale);
                return ntsNumber.trunc(value * scaleX) / scaleX;
            }
            ntsNumber.getDecimal = getDecimal;
            function formatNumber(value, formatOption) {
                if (value === undefined || value === null || value.toString().trim().lenth <= 0) {
                    return value;
                }
                var groupSeperator = formatOption.groupseperator ? formatOption.groupseperator : ',';
                var groupLength = formatOption.grouplength ? formatOption.grouplength : 0;
                var decimalSeperator = formatOption.decimalseperator ? formatOption.decimalseperator : ".";
                var decimalLength = formatOption.decimallength ? formatOption.decimallength : 0;
                var formattedValue = "";
                var stringValue = uk.text.replaceAll(value.toString(), groupSeperator, '');
                var isMinus = stringValue.charAt(0) === '-';
                var values = isMinus ? stringValue.split('-')[1].split(decimalSeperator) : stringValue.split(decimalSeperator);
                if (groupLength > 0) {
                    var x = values[0].split('').reverse().join('');
                    for (var i = 0; i < x.length;) {
                        formattedValue += x.substr(i, groupLength) + (x.length > i + groupLength ? groupSeperator : "");
                        i += groupLength;
                    }
                    formattedValue = formattedValue.split('').reverse().join('');
                }
                else {
                    formattedValue = values[0];
                }
                if (values[1] === undefined || decimalLength > values[1].length) {
                    values[1] = uk.text.padRight(values[1] ? values[1] : "", '0', values[1] ? decimalLength : decimalLength + 1);
                }
                else {
                    values[1] = values[1].substr(0, decimalLength);
                }
                values[1] = uk.text.splitOrPadRight(values[1], decimalLength, '0');
                return (isMinus ? '-' : '') + formattedValue + (decimalLength <= 0 ? '' : decimalSeperator + values[1]);
            }
            ntsNumber.formatNumber = formatNumber;
        })(ntsNumber = uk.ntsNumber || (uk.ntsNumber = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var time;
        (function (time_1) {
            var dotW = ["日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日"];
            function getYearMonthJapan(year, month) {
                if (month)
                    return year + "年 " + month + " 月";
                return year;
            }
            var JapanYearMonth = (function () {
                function JapanYearMonth(empire, year, month) {
                    this.empire = empire;
                    this.year = year;
                    this.month = month;
                }
                JapanYearMonth.prototype.getEmpire = function () {
                    return this.empire;
                };
                JapanYearMonth.prototype.getYear = function () {
                    return this.year;
                };
                JapanYearMonth.prototype.getMonth = function () {
                    return this.month;
                };
                JapanYearMonth.prototype.toString = function () {
                    return (this.empire === undefined ? "" : this.empire + " ")
                        + (this.year === undefined ? "" : this.year + " 年 ")
                        + (this.month === undefined ? "" : this.month + " 月");
                };
                return JapanYearMonth;
            }());
            time_1.JapanYearMonth = JapanYearMonth;
            function yearInJapanEmpire(date) {
                var year = moment.utc(date).year();
                if (year == 1868) {
                    return new JapanYearMonth("明治元年");
                }
                if (year <= 1912) {
                    var diff = year - 1867;
                    return new JapanYearMonth("明治 ", diff);
                }
                if (year <= 1926) {
                    var diff = year - 1911;
                    return new JapanYearMonth("大正 ", diff);
                }
                if (year < 1989) {
                    var diff = year - 1925;
                    return new JapanYearMonth("昭和 ", diff);
                }
                if (year == 1989) {
                    return new JapanYearMonth("平成元年 ", diff);
                }
                var diff = year - 1988;
                return new JapanYearMonth("平成 ", diff);
            }
            time_1.yearInJapanEmpire = yearInJapanEmpire;
            function yearmonthInJapanEmpire(yearmonth) {
                if (!(yearmonth instanceof String)) {
                    yearmonth = "" + yearmonth;
                }
                var nguyennien = "元年";
                yearmonth = yearmonth.replace("/", "");
                var year = parseInt(yearmonth.substring(0, 4));
                var month = parseInt(yearmonth.substring(4));
                if (year == 1868) {
                    return new JapanYearMonth("明治元年 ", undefined, month);
                }
                if (year < 1912) {
                    var diff = year - 1867;
                    return new JapanYearMonth("明治 ", diff, month);
                }
                if (year == 1912) {
                    if (month < 8)
                        return new JapanYearMonth("明治 ", 45, month);
                    return new JapanYearMonth("大正元年 ", undefined, month);
                }
                if (year < 1926) {
                    var diff = year - 1911;
                    return new JapanYearMonth("大正 ", diff, month);
                }
                if (year == 1926) {
                    if (month < 12)
                        return new JapanYearMonth("大正", 15, month);
                    return new JapanYearMonth("昭和元年 ", undefined, month);
                }
                if (year < 1989) {
                    var diff = year - 1925;
                    return new JapanYearMonth("昭和 ", diff, month);
                }
                if (year == 1989) {
                    return new JapanYearMonth("平成元年 ", undefined, month);
                }
                var diff = year - 1988;
                return new JapanYearMonth("平成 ", diff, month);
            }
            time_1.yearmonthInJapanEmpire = yearmonthInJapanEmpire;
            function formatSeconds(seconds, formatOption) {
                seconds = parseInt(String(seconds));
                var ss = uk.text.padLeft(String(seconds % 60), '0', 2);
                var minutes = Math.floor(seconds / 60);
                var mm = uk.text.padLeft(String(minutes % 60), '0', 2);
                var hours = uk.ntsNumber.trunc(seconds / 60 / 60);
                var h = String(hours);
                return "h:mm:ss"
                    .replace(/h/g, h)
                    .replace(/mm/g, mm)
                    .replace(/ss/g, ss);
            }
            time_1.formatSeconds = formatSeconds;
            function formatDate(date, format) {
                if (!format)
                    format = 'yyyy-MM-dd hh:mm:ss.SSS';
                format = format.replace(/yyyy/g, date.getFullYear());
                format = format.replace(/yy/g, ('0' + (date.getFullYear() % 100)).slice(-2));
                format = format.replace(/MM/g, ('0' + (date.getMonth() + 1)).slice(-2));
                format = format.replace(/dd/g, ('0' + date.getDate()).slice(-2));
                if (format.indexOf("DDD") != -1) {
                    var daystr = "(" + dotW[date.getDay()] + ")";
                    format = format.replace("DDD", daystr);
                }
                else if (format.indexOf("D") != -1) {
                    var daystr = "(" + dotW[date.getDay()].substring(0, 1) + ")";
                    format = format.replace("D", daystr);
                }
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
            time_1.formatDate = formatDate;
            function formatYearMonth(yearMonth) {
                var result;
                var num = parseInt(String(yearMonth));
                var year = String(Math.floor(num / 100));
                var month = uk.text.charPadding(String(num % 100), '0', true, 2);
                result = year + '/' + month;
                return result;
            }
            time_1.formatYearMonth = formatYearMonth;
            function formatPattern(date, inputFormat, outputFormat) {
                outputFormat = uk.text.getISOFormat(outputFormat);
                var inputFormats = (inputFormat) ? inputFormat : ["YYYY/MM/DD", "YYYY-MM-DD", "YYYYMMDD", "YYYY/MM", "YYYY-MM", "YYYYMM", "HH:mm", "HHmm"];
                return moment.utc(date, inputFormats).format(outputFormat);
            }
            time_1.formatPattern = formatPattern;
            var ParseResult = (function () {
                function ParseResult(success) {
                    this.success = success;
                }
                return ParseResult;
            }());
            var ResultParseTime = (function (_super) {
                __extends(ResultParseTime, _super);
                function ResultParseTime(success, minus, hours, minutes, msg) {
                    _super.call(this, success);
                    this.minus = minus;
                    this.hours = hours;
                    this.minutes = minutes;
                    this.msg = msg || "invalid time format";
                }
                ResultParseTime.succeeded = function (minus, hours, minutes) {
                    return new ResultParseTime(true, minus, hours, minutes);
                };
                ResultParseTime.failed = function () {
                    return new ResultParseTime(false);
                };
                ResultParseTime.prototype.format = function () {
                    if (!this.success)
                        return "";
                    return (this.minus ? '-' : '') + this.hours + ':' + uk.text.padLeft(String(this.minutes), '0', 2);
                };
                ResultParseTime.prototype.toValue = function () {
                    if (!this.success)
                        return 0;
                    return (this.minus ? -1 : 1) * (this.hours * 60 + this.minutes);
                };
                ResultParseTime.prototype.getMsg = function () { return this.msg; };
                return ResultParseTime;
            }(ParseResult));
            time_1.ResultParseTime = ResultParseTime;
            function parseTime(time, isMinutes) {
                if (time === undefined || time === null) {
                    return ResultParseTime.failed();
                }
                if (isMinutes) {
                    var hoursX = uk.ntsNumber.trunc(time / 60);
                    time = hoursX + uk.text.padLeft((Math.abs(time - hoursX * 60)).toString(), '0', 2);
                }
                if (!(time instanceof String)) {
                    time = time.toString();
                }
                if (time.length < 1 || time.split(':').length > 2 || time.split('-').length > 2
                    || time.lastIndexOf('-') > 0 || (time.length == 1 && !uk.ntsNumber.isNumber(time.charAt(0)))) {
                    return ResultParseTime.failed();
                }
                var minusNumber = time.charAt(0) === '-';
                if (minusNumber) {
                    time = time.split('-')[1];
                }
                var minutes;
                var hours;
                if (time.indexOf(':') > -1) {
                    var times = time.split(':');
                    minutes = times[1];
                    hours = times[0];
                }
                else {
                    time = uk.ntsNumber.trunc(time);
                    time = uk.text.padLeft(time, "0", time.length > 4 ? time.length : 4);
                    minutes = time.substr(-2, 2);
                    hours = time.substr(0, time.length - 2);
                }
                if (!uk.ntsNumber.isNumber(minutes, false) || parseInt(minutes) > 59 || !uk.ntsNumber.isNumber(hours, false)) {
                    return ResultParseTime.failed();
                }
                return ResultParseTime.succeeded(minusNumber, parseInt(hours), parseInt(minutes));
            }
            time_1.parseTime = parseTime;
            var ResultParseYearMonth = (function (_super) {
                __extends(ResultParseYearMonth, _super);
                function ResultParseYearMonth(success, msg, year, month) {
                    _super.call(this, success);
                    this.year = year;
                    this.month = month;
                    this.msg = msg || "must yyyymm or yyyy/mm format: year in [1900-9999] and month in [1-12] ";
                }
                ResultParseYearMonth.succeeded = function (year, month) {
                    return new ResultParseYearMonth(true, "", year, month);
                };
                ResultParseYearMonth.failed = function (msg) {
                    return new ResultParseYearMonth(false, msg);
                };
                ResultParseYearMonth.prototype.format = function () {
                    if (!this.success) {
                        return "";
                    }
                    return this.year + '/' + uk.text.padLeft(String(this.month), '0', 2);
                };
                ResultParseYearMonth.prototype.toValue = function () {
                    if (!this.success) {
                        return 0;
                    }
                    return (this.year * 100 + this.month);
                };
                ResultParseYearMonth.prototype.getMsg = function () { return this.msg; };
                return ResultParseYearMonth;
            }(ParseResult));
            time_1.ResultParseYearMonth = ResultParseYearMonth;
            function parseYearMonth(yearMonth) {
                if (yearMonth === undefined || yearMonth === null) {
                    return ResultParseYearMonth.failed("yearmonth can not empty!");
                }
                if (!(yearMonth instanceof String)) {
                    yearMonth = yearMonth.toString();
                }
                var stringLengh = yearMonth.length;
                yearMonth = yearMonth.replace("/", "");
                yearMonth = yearMonth.replace("/", "");
                var checkNum = yearMonth.replace(/[0-9]/g, "");
                if (checkNum.length > 0)
                    return ResultParseYearMonth.failed("yearmonth must contain digits and slashes only!");
                if (yearMonth.length != 6 && yearMonth.length != 5)
                    return ResultParseYearMonth.failed("wrong yearmonth format: must be yyyy/mm or yyyymm");
                var year = parseInt(yearMonth.substring(0, 4));
                var month = parseInt(yearMonth.substring(4));
                if (year < 1900 || year > 9999)
                    return ResultParseYearMonth.failed("wrong year: year must in range 1900-9999");
                if (month < 1 || month > 12)
                    return ResultParseYearMonth.failed("wrong month: month must in range 1-12");
                return ResultParseYearMonth.succeeded(year, month);
            }
            time_1.parseYearMonth = parseYearMonth;
            var ResultParseTimeOfTheDay = (function (_super) {
                __extends(ResultParseTimeOfTheDay, _super);
                function ResultParseTimeOfTheDay(success, msg, hour, minute) {
                    _super.call(this, success);
                    this.hour = hour;
                    this.minute = minute;
                    this.msg = msg || "time of the days must in format hh:mm with hour in range 0-23; minute in range 00-59";
                }
                ResultParseTimeOfTheDay.succeeded = function (hour, minute) {
                    return new ResultParseTimeOfTheDay(true, "", hour, minute);
                };
                ResultParseTimeOfTheDay.failed = function (msg) {
                    return new ResultParseTimeOfTheDay(false, msg);
                };
                ResultParseTimeOfTheDay.prototype.format = function () {
                    if (!this.success) {
                        return "";
                    }
                    return this.hour + ':' + uk.text.padLeft(String(this.minute), '0', 2);
                };
                ResultParseTimeOfTheDay.prototype.toValue = function () {
                    if (!this.success) {
                        return 0;
                    }
                    return (this.hour * 100 + this.minute);
                };
                ResultParseTimeOfTheDay.prototype.getMsg = function () { return this.msg; };
                return ResultParseTimeOfTheDay;
            }(ParseResult));
            time_1.ResultParseTimeOfTheDay = ResultParseTimeOfTheDay;
            function parseTimeOfTheDay(timeOfDay) {
                if (timeOfDay === undefined || timeOfDay === null) {
                    return ResultParseTimeOfTheDay.failed("time of the day cannot be empty!");
                }
                if (!(timeOfDay instanceof String)) {
                    timeOfDay = timeOfDay.toString();
                }
                timeOfDay = timeOfDay.replace(":", "");
                var checkNum = timeOfDay.replace(/[0-9]/g, "");
                var stringLength = timeOfDay.length;
                if (checkNum.length > 0)
                    return ResultParseTimeOfTheDay.failed("time of the day accept digits and ':' only");
                if (stringLength < 3 || stringLength > 4)
                    return ResultParseTimeOfTheDay.failed("invalid time of the day format");
                var hour = parseInt(timeOfDay.substring(0, stringLength - 2));
                var minute = parseInt(timeOfDay.substring(stringLength - 2));
                if (hour < 0 || hour > 23)
                    return ResultParseTimeOfTheDay.failed("invalid: hour must in range 0-23");
                if (minute < 0 || minute > 59)
                    return ResultParseTimeOfTheDay.failed("invalid: minute must in range 0-59");
                return ResultParseTimeOfTheDay.succeeded(hour, minute);
            }
            time_1.parseTimeOfTheDay = parseTimeOfTheDay;
            var ResultParseYearMonthDate = (function (_super) {
                __extends(ResultParseYearMonthDate, _super);
                function ResultParseYearMonthDate(success, msg, year, month, date) {
                    _super.call(this, success);
                    this.year = year;
                    this.month = month;
                    this.date = date;
                    this.msg = msg || "must yyyymm or yyyy/mm format: year in [1900-9999] and month in [1-12] ";
                }
                ResultParseYearMonthDate.succeeded = function (year, month, date) {
                    return new ResultParseYearMonthDate(true, "", year, month, date);
                };
                ResultParseYearMonthDate.failed = function (msg) {
                    return new ResultParseYearMonthDate(false, msg);
                };
                ResultParseYearMonthDate.prototype.format = function () {
                    if (!this.success) {
                        return "";
                    }
                    return this.year + '/' + uk.text.padLeft(String(this.month), '0', 2) + uk.text.padLeft(String(this.date), '0', 2);
                };
                ResultParseYearMonthDate.prototype.toValue = function () {
                    if (!this.success) {
                        return 0;
                    }
                    return (this.year * 10000 + this.month * 100 + this.date);
                };
                ResultParseYearMonthDate.prototype.getMsg = function () { return this.msg; };
                return ResultParseYearMonthDate;
            }(ParseResult));
            time_1.ResultParseYearMonthDate = ResultParseYearMonthDate;
            function parseYearMonthDate(yearMonthDate) {
                if (yearMonthDate === undefined || yearMonthDate === null) {
                    return ResultParseYearMonthDate.failed("full date can not empty!");
                }
                if (!(yearMonthDate instanceof String)) {
                    yearMonthDate = yearMonthDate.toString();
                }
                yearMonthDate = yearMonthDate.replace("/", "");
                yearMonthDate = yearMonthDate.replace("/", "");
                var checkNum = yearMonthDate.replace(/[0-9]/g, "");
                if (checkNum.length !== 0)
                    return ResultParseYearMonthDate.failed("full date must contain digits and slashes only");
                if (yearMonthDate.length != 8)
                    return ResultParseYearMonthDate.failed("full date format must be yyyy/mm/dd or yyyymmdd");
                var year = parseInt(yearMonthDate.substring(0, 4));
                if (year < 1900 || year > 9999) {
                    return ResultParseYearMonthDate.failed("invalid: year must in range 1900-9999");
                }
                var month = parseInt(yearMonthDate.substring(4, 6));
                if (month < 1 || month > 12)
                    return ResultParseYearMonthDate.failed("invalid: month must in range 1-12");
                var date = parseInt(yearMonthDate.substring(6));
                var maxDate = 30;
                switch (month) {
                    case 2:
                        if (year % 400 == 0) {
                            maxDate = 29;
                        }
                        else if (year % 4 == 0 && year % 25 != 0) {
                            maxDate = 29;
                        }
                        else {
                            maxDate = 28;
                        }
                        break;
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        maxDate = 31;
                        break;
                    default:
                        maxDate = 30;
                        break;
                }
                if (date < 1 || date > maxDate)
                    return ResultParseYearMonthDate.failed("invalid: month = " + month + ", so your date must in range 1-" + maxDate);
                return ResultParseYearMonthDate.succeeded(year, month, date);
            }
            time_1.parseYearMonthDate = parseYearMonthDate;
            var MomentResult = (function (_super) {
                __extends(MomentResult, _super);
                function MomentResult(momentObject, outputFormat) {
                    _super.call(this, true);
                    this.momentObject = momentObject;
                    this.outputFormat = uk.text.getISOFormat(outputFormat);
                }
                MomentResult.prototype.succeeded = function () {
                    this.success = true;
                };
                MomentResult.prototype.failed = function (msg) {
                    this.msg = (msg) ? msg : "Invalid format";
                    this.success = false;
                };
                MomentResult.prototype.format = function () {
                    if (!this.success)
                        return "";
                    return this.momentObject.format(this.outputFormat);
                };
                MomentResult.prototype.toValue = function () {
                    if (!this.success)
                        return null;
                    return this.momentObject;
                };
                MomentResult.prototype.toNumber = function (outputFormat) {
                    var dateFormats = ["YYYY/MM/DD", "YYYY-MM-DD", "YYYYMMDD", "date"];
                    var yearMonthFormats = ["YYYY/MM", "YYYY-MM", "YYYYMM", "yearmonth"];
                    if (!this.success)
                        return null;
                    if (dateFormats.indexOf(outputFormat) != -1) {
                        return this.momentObject.year() * 10000 + (this.momentObject.month() + 1) * 100 + this.momentObject.date();
                    }
                    else if (yearMonthFormats.indexOf(outputFormat) != -1) {
                        return this.momentObject.year() * 100 + (this.momentObject.month() + 1);
                    }
                    else {
                        return parseInt(this.momentObject.format(outputFormat).replace(/[^\d]/g, ""));
                    }
                };
                MomentResult.prototype.getMsg = function () { return this.msg; };
                return MomentResult;
            }(ParseResult));
            time_1.MomentResult = MomentResult;
            function parseMoment(datetime, outputFormat, inputFormat) {
                var inputFormats = (inputFormat) ? inputFormat : ["YYYY/MM/DD", "YYYY-MM-DD", "YYYYMMDD", "YYYY/MM", "YYYY-MM", "YYYYMM", "HH:mm", "HHmm"];
                var momentObject = moment.utc(datetime, inputFormats);
                var result = new MomentResult(momentObject, outputFormat);
                if (momentObject.isValid())
                    result.succeeded();
                else
                    result.failed();
                return result;
            }
            time_1.parseMoment = parseMoment;
            function UTCDate(year, month, date, hours, minutes, seconds, milliseconds) {
                if (uk.util.isNullOrUndefined(year)) {
                    var currentDate = new Date();
                    year = currentDate.getUTCFullYear();
                    month = (uk.util.isNullOrUndefined(month)) ? currentDate.getUTCMonth() : month;
                    date = (uk.util.isNullOrUndefined(date)) ? currentDate.getUTCDate() : date;
                    hours = (uk.util.isNullOrUndefined(hours)) ? currentDate.getUTCHours() : hours;
                    minutes = (uk.util.isNullOrUndefined(minutes)) ? currentDate.getUTCMinutes() : minutes;
                    seconds = (uk.util.isNullOrUndefined(seconds)) ? currentDate.getUTCSeconds() : seconds;
                    milliseconds = (uk.util.isNullOrUndefined(milliseconds)) ? currentDate.getUTCMilliseconds() : milliseconds;
                    return new Date(Date.UTC(year, month, date, hours, minutes, seconds, milliseconds));
                }
                else {
                    month = (uk.util.isNullOrUndefined(month)) ? 0 : month;
                    date = (uk.util.isNullOrUndefined(date)) ? 1 : date;
                    hours = (uk.util.isNullOrUndefined(hours)) ? 0 : hours;
                    minutes = (uk.util.isNullOrUndefined(minutes)) ? 0 : minutes;
                    seconds = (uk.util.isNullOrUndefined(seconds)) ? 1 : seconds;
                    milliseconds = (uk.util.isNullOrUndefined(milliseconds)) ? 0 : milliseconds;
                    return new Date(Date.UTC(year, month, date, hours, minutes, seconds, milliseconds));
                }
            }
            time_1.UTCDate = UTCDate;
        })(time = uk.time || (uk.time = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var request;
        (function (request) {
            request.STORAGE_KEY_TRANSFER_DATA = "nts.uk.request.STORAGE_KEY_TRANSFER_DATA";
            var WEB_APP_NAME = {
                com: 'nts.uk.com.web',
                pr: 'nts.uk.pr.web'
            };
            var QueryString = (function () {
                function QueryString() {
                    this.items = {};
                }
                QueryString.parseUrl = function (url) {
                    var instance = new QueryString();
                    var queryString = url.split('?')[1];
                    if (queryString) {
                        var queryEntries = queryString.split('&');
                        for (var i = 0; i < queryEntries.length; i++) {
                            var entryParts = queryEntries[i].split('=');
                            instance.set(entryParts[0], entryParts[1]);
                        }
                    }
                    return instance;
                };
                QueryString.build = function (entriesObj) {
                    var instance = new QueryString();
                    for (var key in entriesObj) {
                        instance.set(key, entriesObj[key]);
                    }
                    return instance;
                };
                QueryString.prototype.get = function (key) {
                    return this.items[key];
                };
                QueryString.prototype.set = function (key, value) {
                    if (key === null || key === undefined || key === '') {
                        return;
                    }
                    this.items[key] = value;
                };
                QueryString.prototype.remove = function (key) {
                    delete this.items[key];
                };
                QueryString.prototype.mergeFrom = function (otherObj) {
                    for (var otherKey in otherObj.items) {
                        this.set(otherKey, otherObj.items[otherKey]);
                    }
                };
                QueryString.prototype.count = function () {
                    var count = 0;
                    for (var key in this.items) {
                        count++;
                    }
                    return count;
                };
                QueryString.prototype.hasItems = function () {
                    return this.count() !== 0;
                };
                QueryString.prototype.serialize = function () {
                    var entryStrings = [];
                    for (var key in this.items) {
                        entryStrings.push(key + '=' + this.items[key]);
                    }
                    return entryStrings.join('&');
                };
                return QueryString;
            }());
            request.QueryString = QueryString;
            var Locator = (function () {
                function Locator(url) {
                    this.rawUrl = url;
                    this.queryString = QueryString.parseUrl(url);
                }
                Locator.prototype.serialize = function () {
                    if (this.queryString.hasItems()) {
                        return this.rawUrl + '?' + this.queryString.serialize();
                    }
                    else {
                        return this.rawUrl;
                    }
                };
                Locator.prototype.mergeRelativePath = function (relativePath) {
                    var stack = this.rawUrl.split('/');
                    var parts = relativePath.split('/');
                    var queryStringToAdd = QueryString.parseUrl(relativePath);
                    stack.pop();
                    if (parts[0] === '') {
                        parts.shift();
                    }
                    for (var i = 0; i < parts.length; i++) {
                        if (parts[i] === '.')
                            continue;
                        if (parts[i] === '..')
                            stack.pop();
                        else
                            stack.push(parts[i]);
                    }
                    queryStringToAdd.mergeFrom(this.queryString);
                    var queryStringParts = queryStringToAdd.hasItems()
                        ? '?' + queryStringToAdd.serialize()
                        : '';
                    return new Locator(stack.join('/') + queryStringParts);
                };
                return Locator;
            }());
            request.Locator = Locator;
            function ajax(webAppId, path, data, options) {
                if (typeof arguments[1] !== 'string') {
                    return ajax.apply(null, _.concat(location.currentAppId, arguments));
                }
                var dfd = $.Deferred();
                options = options || {};
                if (typeof data === 'object') {
                    data = JSON.stringify(data);
                }
                var webserviceLocator = location.siteRoot
                    .mergeRelativePath(WEB_APP_NAME[webAppId] + '/')
                    .mergeRelativePath(location.ajaxRootDir)
                    .mergeRelativePath(path);
                $.ajax({
                    type: options.method || 'POST',
                    contentType: options.contentType || 'application/json',
                    url: webserviceLocator.serialize(),
                    dataType: options.dataType || 'json',
                    data: data
                }).done(function (res) {
                    if (res !== undefined && res.businessException) {
                        dfd.reject(res);
                    }
                    else {
                        dfd.resolve(res);
                    }
                });
                return dfd.promise();
            }
            request.ajax = ajax;
            function exportFile(path, data, options) {
                var dfd = $.Deferred();
                ajax(path, data, options)
                    .then(function (res) {
                    return uk.deferred.repeat(function (conf) { return conf
                        .task(function () { return specials.getAsyncTaskInfo(res.taskId); })
                        .while(function (info) { return info.pending || info.running; })
                        .pause(1000); });
                })
                    .done(function (res) {
                    specials.donwloadFile(res.id);
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            request.exportFile = exportFile;
            var specials;
            (function (specials) {
                function getAsyncTaskInfo(taskId) {
                    return ajax('/ntscommons/arc/task/async/' + taskId);
                }
                specials.getAsyncTaskInfo = getAsyncTaskInfo;
                function donwloadFile(fileId) {
                    $('<iframe/>')
                        .attr('id', 'download-frame')
                        .appendTo('body')
                        .attr('src', resolvePath('/webapi/ntscommons/arc/filegate/get/' + fileId));
                }
                specials.donwloadFile = donwloadFile;
            })(specials = request.specials || (request.specials = {}));
            function jump(path, data) {
                uk.sessionStorage.setItemAsJson(request.STORAGE_KEY_TRANSFER_DATA, data);
                window.location.href = resolvePath(path);
            }
            request.jump = jump;
            function resolvePath(path) {
                var destination;
                if (path.charAt(0) === '/') {
                    destination = location.appRoot.mergeRelativePath(path);
                }
                else {
                    destination = location.current.mergeRelativePath(path);
                }
                return destination.rawUrl;
            }
            request.resolvePath = resolvePath;
            var location;
            (function (location) {
                location.current = new Locator(window.location.href);
                location.appRoot = location.current.mergeRelativePath(__viewContext.rootPath);
                location.siteRoot = location.appRoot.mergeRelativePath('../');
                location.ajaxRootDir = 'webapi/';
                var currentAppName = _.takeRight(location.appRoot.serialize().split('/'), 2)[0];
                for (var id in WEB_APP_NAME) {
                    if (currentAppName === WEB_APP_NAME[id]) {
                        location.currentAppId = id;
                        break;
                    }
                }
            })(location = request.location || (request.location = {}));
            ;
        })(request = uk.request || (uk.request = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            ui.documentReady = $.Callbacks();
            ui.viewModelBuilt = $.Callbacks();
            var KibanViewModel = (function () {
                function KibanViewModel() {
                    this.title = ko.observable('');
                    this.errorDialogViewModel = new nts.uk.ui.errors.ErrorsViewModel();
                }
                return KibanViewModel;
            }());
            ui.KibanViewModel = KibanViewModel;
            var init;
            (function (init) {
                var _start;
                __viewContext.ready = function (callback) {
                    _start = callback;
                };
                __viewContext.bind = function (contentViewModel) {
                    var kiban = new KibanViewModel();
                    ui._viewModel = {
                        content: contentViewModel,
                        kiban: kiban,
                        errors: {
                            isEmpty: ko.computed(function () { return !kiban.errorDialogViewModel.occurs(); })
                        }
                    };
                    kiban.title(__viewContext.title || 'THIS IS TITLE');
                    ui.viewModelBuilt.fire(ui._viewModel);
                    ko.applyBindings(ui._viewModel);
                };
                $(function () {
                    ui.documentReady.fire();
                    __viewContext.transferred = uk.sessionStorage.getItemAndRemove(uk.request.STORAGE_KEY_TRANSFER_DATA)
                        .map(function (v) { return JSON.parse(v); });
                    _.defer(function () { return _start.call(__viewContext); });
                });
            })(init || (init = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var notify;
            (function (notify) {
                var error;
                (function (error) {
                    ui.documentReady.add(function () {
                        var $functionsArea = $('#functions-area');
                        if ($functionsArea.length === 0) {
                            return;
                        }
                        $('#func-notifier-errors').position({ my: 'left+5 top-5', at: 'left bottom', of: $('#functions-area') });
                    });
                })(error || (error = {}));
            })(notify = ui.notify || (ui.notify = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var validation;
            (function (validation) {
                var NoValidator = (function () {
                    function NoValidator() {
                    }
                    NoValidator.prototype.validate = function (inputText) {
                        var result = new ValidationResult();
                        result.isValid = true;
                        result.parsedValue = inputText;
                        return result;
                    };
                    return NoValidator;
                }());
                validation.NoValidator = NoValidator;
                var ValidationResult = (function () {
                    function ValidationResult() {
                        this.errorMessage = 'error message';
                    }
                    ValidationResult.prototype.fail = function (errorMessage) {
                        this.errorMessage = errorMessage;
                        this.isValid = false;
                    };
                    ValidationResult.prototype.success = function (parsedValue) {
                        this.parsedValue = parsedValue;
                        this.isValid = true;
                    };
                    return ValidationResult;
                }());
                validation.ValidationResult = ValidationResult;
                var StringValidator = (function () {
                    function StringValidator(primitiveValueName, option) {
                        this.constraint = getConstraint(primitiveValueName);
                        this.charType = uk.text.getCharType(primitiveValueName);
                        this.required = option.required;
                    }
                    StringValidator.prototype.validate = function (inputText) {
                        var result = new ValidationResult();
                        if (this.required !== undefined && this.required !== false) {
                            if (!checkRequired(inputText)) {
                                result.fail('This field is required');
                                return result;
                            }
                        }
                        if (this.charType !== null && this.charType !== undefined) {
                            if (!this.charType.validate(inputText)) {
                                result.fail('Invalid text');
                                return result;
                            }
                        }
                        if (this.constraint !== undefined && this.constraint !== null) {
                            if (this.constraint.maxLength !== undefined && uk.text.countHalf(inputText) > this.constraint.maxLength) {
                                result.fail('Max length for this input is ' + this.constraint.maxLength);
                                return result;
                            }
                            if (!uk.text.isNullOrEmpty(this.constraint.stringExpression) && !this.constraint.stringExpression.test(inputText)) {
                                result.fail('This field is not valid with pattern!');
                                return result;
                            }
                        }
                        result.success(inputText);
                        return result;
                    };
                    return StringValidator;
                }());
                validation.StringValidator = StringValidator;
                var NumberValidator = (function () {
                    function NumberValidator(primitiveValueName, option) {
                        this.constraint = getConstraint(primitiveValueName);
                        this.option = option;
                    }
                    NumberValidator.prototype.validate = function (inputText) {
                        var result = new ValidationResult();
                        var isDecimalNumber = false;
                        if (this.option !== undefined) {
                            isDecimalNumber = (this.option.decimallength > 0);
                            inputText = uk.text.replaceAll(inputText.toString(), this.option.groupseperator, '');
                        }
                        if (!uk.ntsNumber.isNumber(inputText, isDecimalNumber)) {
                            result.fail('invalid number');
                            return result;
                        }
                        var value = isDecimalNumber ?
                            uk.ntsNumber.getDecimal(inputText, this.option.decimallength) : parseInt(inputText);
                        if (this.constraint !== null) {
                            if (this.constraint.max !== undefined && value > this.constraint.max) {
                                result.fail('invalid number');
                                return result;
                            }
                            if (this.constraint.min !== undefined && value < this.constraint.min) {
                                result.fail('invalid number');
                                return result;
                            }
                        }
                        result.success(inputText === "0" ? inputText : uk.text.removeFromStart(inputText, "0"));
                        return result;
                    };
                    return NumberValidator;
                }());
                validation.NumberValidator = NumberValidator;
                var TimeValidator = (function () {
                    function TimeValidator(primitiveValueName, option) {
                        this.constraint = getConstraint(primitiveValueName);
                        this.outputFormat = (option && option.inputFormat) ? option.inputFormat : "";
                        this.required = (option && option.required) ? option.required : false;
                        this.valueType = (option && option.valueType) ? option.valueType : "string";
                    }
                    TimeValidator.prototype.validate = function (inputText) {
                        var result = new ValidationResult();
                        if (this.required !== undefined && this.required !== false) {
                            if (!checkRequired(inputText)) {
                                result.fail('This field is required');
                                return result;
                            }
                        }
                        var parseResult;
                        parseResult = uk.time.parseMoment(inputText, this.outputFormat);
                        if (parseResult.success) {
                            if (this.valueType === "string")
                                result.success(parseResult.format());
                            else if (this.valueType === "number") {
                                result.success(parseResult.toNumber(this.outputFormat));
                            }
                            else if (this.valueType === "date") {
                                result.success(parseResult.toValue().toDate());
                            }
                            else if (this.valueType === "moment") {
                                result.success(parseResult.toValue());
                            }
                            else {
                                result.success(parseResult.format());
                            }
                        }
                        else {
                            result.fail(parseResult.getMsg());
                        }
                        return result;
                    };
                    return TimeValidator;
                }());
                validation.TimeValidator = TimeValidator;
                function checkRequired(value) {
                    if (value === undefined || value === null || value.length == 0) {
                        return false;
                    }
                    return true;
                }
                function getConstraint(primitiveValueName) {
                    var constraint = __viewContext.primitiveValueConstraints[primitiveValueName];
                    if (constraint === undefined)
                        return null;
                    else
                        return __viewContext.primitiveValueConstraints[primitiveValueName];
                }
                validation.getConstraint = getConstraint;
            })(validation = ui.validation || (ui.validation = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var errors;
            (function (errors) {
                var ErrorsViewModel = (function () {
                    function ErrorsViewModel() {
                        var _this = this;
                        this.title = "エラー一覧";
                        this.errors = ko.observableArray([]);
                        this.errors.extend({ rateLimit: 1 });
                        this.option = ko.mapping.fromJS(new ui.option.ErrorDialogOption());
                        this.occurs = ko.computed(function () { return _this.errors().length !== 0; });
                        this.allResolved = $.Callbacks();
                        this.errors.subscribe(function () {
                            if (_this.errors().length === 0) {
                                _this.allResolved.fire();
                            }
                        });
                        this.allResolved.add(function () {
                            _this.hide();
                        });
                    }
                    ErrorsViewModel.prototype.closeButtonClicked = function () {
                    };
                    ErrorsViewModel.prototype.open = function () {
                        this.option.show(true);
                    };
                    ErrorsViewModel.prototype.hide = function () {
                        this.option.show(false);
                    };
                    ErrorsViewModel.prototype.addError = function (error) {
                        var duplicate = _.filter(this.errors(), function (e) { return e.$control.is(error.$control) && e.message == error.message; });
                        if (duplicate.length == 0)
                            this.errors.push(error);
                    };
                    ErrorsViewModel.prototype.removeErrorByElement = function ($element) {
                        var removeds = _.filter(this.errors(), function (e) { return e.$control.is($element); });
                        this.errors.removeAll(removeds);
                    };
                    return ErrorsViewModel;
                }());
                errors.ErrorsViewModel = ErrorsViewModel;
                var ErrorHeader = (function () {
                    function ErrorHeader(name, text, width, visible) {
                        this.name = name;
                        this.text = text;
                        this.width = width;
                        this.visible = visible;
                    }
                    return ErrorHeader;
                }());
                errors.ErrorHeader = ErrorHeader;
                function errorsViewModel() {
                    return nts.uk.ui._viewModel.kiban.errorDialogViewModel;
                }
                errors.errorsViewModel = errorsViewModel;
                function show() {
                    errorsViewModel().open();
                }
                errors.show = show;
                function hide() {
                    errorsViewModel().hide();
                }
                errors.hide = hide;
                function add(error) {
                    errorsViewModel().addError(error);
                }
                errors.add = add;
                function removeByElement($control) {
                    errorsViewModel().removeErrorByElement($control);
                }
                errors.removeByElement = removeByElement;
            })(errors = ui.errors || (ui.errors = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var windows;
            (function (windows) {
                var MAIN_WINDOW_ID = 'MAIN_WINDOW';
                var DEFAULT_DIALOG_OPTIONS = {
                    autoOpen: false,
                    draggable: true,
                    resizable: false,
                    create: function (event) {
                        $(event.target).dialog('widget').css({ position: 'fixed' });
                    }
                };
                var ScreenWindow = (function () {
                    function ScreenWindow(id, isRoot, parent) {
                        this.globalContext = null;
                        this.$dialog = null;
                        this.$iframe = null;
                        this.onClosedHandler = $.noop;
                        this.id = id;
                        this.isRoot = isRoot;
                        this.parent = parent;
                    }
                    ScreenWindow.createMainWindow = function () {
                        return new ScreenWindow(MAIN_WINDOW_ID, true, null);
                    };
                    ScreenWindow.createSubWindow = function (parent) {
                        return new ScreenWindow(uk.util.randomId(), false, parent);
                    };
                    ScreenWindow.prototype.setGlobal = function (globalContext) {
                        this.globalContext = globalContext;
                    };
                    ScreenWindow.prototype.setTitle = function (newTitle) {
                        if (this.isRoot) {
                            this.globalContext.title = newTitle;
                        }
                        else {
                            this.$dialog.dialog('option', { title: newTitle });
                        }
                    };
                    ScreenWindow.prototype.setHeight = function (height) {
                        if (!isNaN(height)) {
                            this.$dialog.dialog('option', {
                                height: height
                            });
                            this.$dialog.resize();
                        }
                    };
                    ScreenWindow.prototype.setWidth = function (width) {
                        if (!isNaN(width)) {
                            this.$dialog.dialog('option', {
                                width: width
                            });
                            this.$dialog.resize();
                        }
                    };
                    ScreenWindow.prototype.setSize = function (height, width) {
                        if (!isNaN(width) && !isNaN(height)) {
                            this.$dialog.dialog('option', {
                                width: width,
                                height: height
                            });
                            this.$dialog.resize();
                        }
                    };
                    ScreenWindow.prototype.setupAsDialog = function (path, options) {
                        var _this = this;
                        options.close = function () {
                            _this.dispose();
                        };
                        this.build$dialog(options);
                        this.$iframe.bind('load', function () {
                            _this.globalContext.nts.uk.ui.windows.selfId = _this.id;
                            _this.$dialog.dialog('option', {
                                width: options.width || _this.globalContext.dialogSize.width,
                                height: options.height || _this.globalContext.dialogSize.height,
                                title: options.title || "dialog",
                                resizable: true,
                                beforeClose: function () {
                                }
                            }).dialog('open');
                        });
                        this.globalContext.location.href = uk.request.resolvePath(path);
                    };
                    ScreenWindow.prototype.build$dialog = function (options) {
                        this.$dialog = $('<div/>')
                            .css({
                            padding: '0px',
                            overflow: 'hidden'
                        })
                            .appendTo($('body'))
                            .dialog(options);
                        this.$iframe = $('<iframe/>').css({
                            width: '100%',
                            height: '100%'
                        })
                            .appendTo(this.$dialog);
                        this.setGlobal(this.$iframe[0].contentWindow);
                    };
                    ScreenWindow.prototype.onClosed = function (callback) {
                        this.onClosedHandler = function () {
                            callback();
                            windows.container.localShared = {};
                        };
                    };
                    ScreenWindow.prototype.close = function () {
                        if (this.isRoot) {
                            window.close();
                        }
                        else {
                            this.$dialog.dialog('close');
                        }
                    };
                    ScreenWindow.prototype.dispose = function () {
                        var _this = this;
                        _.defer(function () { return _this.onClosedHandler(); });
                        setTimeout(function () {
                            _this.$iframe.remove();
                            _this.$dialog.remove();
                            _this.$dialog = null;
                            _this.$iframe = null;
                            _this.globalContext = null;
                            _this.parent = null;
                            _this.onClosedHandler = null;
                        }, 2000);
                    };
                    return ScreenWindow;
                }());
                windows.ScreenWindow = ScreenWindow;
                var ScreenWindowContainer = (function () {
                    function ScreenWindowContainer() {
                        this.windows = {};
                        this.windows[windows.selfId] = ScreenWindow.createMainWindow();
                        this.windows[windows.selfId].setGlobal(window);
                        this.shared = {};
                        this.localShared = {};
                    }
                    ScreenWindowContainer.prototype.createDialog = function (path, options, parentId) {
                        var parentwindow = this.windows[parentId];
                        var subWindow = ScreenWindow.createSubWindow(parentwindow);
                        this.windows[subWindow.id] = subWindow;
                        options = $.extend({}, DEFAULT_DIALOG_OPTIONS, options);
                        subWindow.setupAsDialog(path, options);
                        return subWindow;
                    };
                    ScreenWindowContainer.prototype.getShared = function (key) {
                        return this.localShared[key] !== undefined ? this.localShared[key] : this.shared[key];
                    };
                    ScreenWindowContainer.prototype.setShared = function (key, data, isRoot, persist) {
                        if (persist || isRoot) {
                            this.shared[key] = data;
                        }
                        else {
                            this.localShared[key] = data;
                        }
                    };
                    ScreenWindowContainer.prototype.close = function (id) {
                        var target = this.windows[id];
                        delete this.windows[id];
                        target.close();
                    };
                    return ScreenWindowContainer;
                }());
                windows.ScreenWindowContainer = ScreenWindowContainer;
                if (uk.util.isInFrame()) {
                    var parent = window.parent;
                    windows.container = (parent.nts.uk.ui.windows.container);
                }
                else {
                    windows.selfId = MAIN_WINDOW_ID;
                    windows.container = new ScreenWindowContainer();
                }
                function getShared(key) {
                    return windows.container.getShared(key);
                }
                windows.getShared = getShared;
                function setShared(key, data, persist) {
                    windows.container.setShared(key, data, windows.getSelf().isRoot, persist);
                }
                windows.setShared = setShared;
                function getSelf() {
                    return windows.container.windows[windows.selfId];
                }
                windows.getSelf = getSelf;
                function close(windowId) {
                    windowId = uk.util.orDefault(windowId, windows.selfId);
                    windows.container.close(windowId);
                }
                windows.close = close;
                var sub;
                (function (sub) {
                    function modal(path, options) {
                        options = options || {};
                        options.modal = true;
                        return open(path, options);
                    }
                    sub.modal = modal;
                    function modeless(path, options) {
                        options = options || {};
                        options.modal = false;
                        return open(path, options);
                    }
                    sub.modeless = modeless;
                    function open(path, options) {
                        return windows.container.createDialog(path, options, windows.selfId);
                    }
                    sub.open = open;
                })(sub = windows.sub || (windows.sub = {}));
            })(windows = ui_1.windows || (ui_1.windows = {}));
            function localize(textId) {
                return textId;
            }
            ui_1.localize = localize;
            var dialog;
            (function (dialog) {
                function createNoticeDialog(text, buttons) {
                    var $control = $('<div/>').addClass('control');
                    text = text.replace(/\n/g, '<br />');
                    var $this = $('<div/>').addClass('notice-dialog')
                        .append($('<div/>').addClass('text').append(text))
                        .append($control)
                        .appendTo('body')
                        .dialog({
                        dialogClass: "no-close",
                        width: 'auto',
                        modal: true,
                        closeOnEscape: false,
                        buttons: buttons,
                        open: function () {
                            $(this).closest('.ui-dialog').css('z-index', 120001);
                            $('.ui-widget-overlay').last().css('z-index', 120000);
                            $(this).parent().find('.ui-dialog-buttonset > button:first-child').focus();
                            $(this).parent().find('.ui-dialog-buttonset > button').removeClass('ui-button ui-corner-all ui-widget');
                        },
                        close: function (event) {
                            $(this).dialog('destroy');
                            $(event.target).remove();
                        }
                    });
                    return $this;
                }
                function info(text) {
                    var then = $.noop;
                    var $dialog = $('<div/>').hide();
                    $(function () {
                        $dialog.appendTo('body').dialog({
                            autoOpen: false
                        });
                    });
                    setTimeout(function () {
                        var $this = createNoticeDialog(text, [{
                                text: "はい",
                                "class": "large",
                                click: function () {
                                    $this.dialog('close');
                                    then();
                                }
                            }]);
                    }, 0);
                    return {
                        then: function (callback) {
                            then = callback;
                        }
                    };
                }
                dialog.info = info;
                ;
                function alert(text) {
                    return info(text);
                }
                dialog.alert = alert;
                ;
                function confirm(text) {
                    var handleYes = $.noop;
                    var handleNo = $.noop;
                    var handleCancel = $.noop;
                    var handleThen = $.noop;
                    var hasNoButton = true;
                    var hasCancelButton = false;
                    var handlers = {
                        ifYes: function (handler) {
                            handleYes = handler;
                            return handlers;
                        },
                        ifCancel: function (handler) {
                            hasNoButton = false;
                            hasCancelButton = true;
                            handleCancel = handler;
                            return handlers;
                        },
                        ifNo: function (handler) {
                            hasNoButton = true;
                            handleNo = handler;
                            return handlers;
                        },
                        then: function (handler) {
                            handleThen = handler;
                            return handlers;
                        }
                    };
                    setTimeout(function () {
                        var buttons = [];
                        buttons.push({
                            text: "はい",
                            "class": "yes large danger",
                            click: function () {
                                $this.dialog('close');
                                handleYes();
                                handleThen();
                            }
                        });
                        if (hasNoButton) {
                            buttons.push({
                                text: "いいえ",
                                "class": "no large",
                                click: function () {
                                    $this.dialog('close');
                                    handleNo();
                                    handleThen();
                                }
                            });
                        }
                        if (hasCancelButton) {
                            buttons.push({
                                text: "キャンセル",
                                "class": "cancel large",
                                click: function () {
                                    $this.dialog('close');
                                    handleCancel();
                                    handleThen();
                                }
                            });
                        }
                        var $this = createNoticeDialog(text, buttons);
                    });
                    return handlers;
                }
                dialog.confirm = confirm;
                ;
            })(dialog = ui_1.dialog || (ui_1.dialog = {}));
            var contextmenu;
            (function (contextmenu) {
                var ContextMenu = (function () {
                    function ContextMenu(selector, items, enable) {
                        this.selector = selector;
                        this.items = items;
                        this.enable = (enable !== undefined) ? enable : true;
                        this.init();
                    }
                    ContextMenu.prototype.init = function () {
                        var self = this;
                        $('body .ntsContextMenu').each(function () {
                            if ($(this).data("selector") === self.selector) {
                                $("body").off("contextmenu", self.selector);
                                $(this).remove();
                            }
                        });
                        self.guid = nts.uk.util.randomId();
                        var $contextMenu = $("<ul id='" + self.guid + "' class='ntsContextMenu'></ul>").data("selector", self.selector).hide();
                        self.createMenuItems($contextMenu);
                        $('body').append($contextMenu);
                        $("html").on("contextmenu", self.selector, function (event) {
                            if (self.enable === true) {
                                event.preventDefault();
                                self.target = event.target;
                                $contextMenu.show().position({
                                    my: "left+2 top+2",
                                    of: event,
                                    collision: "fit"
                                });
                            }
                        });
                        $("html").on("mousedown", function (event) {
                            if (!$contextMenu.is(event.target) && $contextMenu.has(event.target).length === 0) {
                                $contextMenu.hide();
                            }
                        });
                    };
                    ContextMenu.prototype.destroy = function () {
                        $("html").off("contextmenu", this.selector);
                        $("#" + this.guid).remove();
                    };
                    ContextMenu.prototype.refresh = function () {
                        this.destroy();
                        this.init();
                    };
                    ContextMenu.prototype.getItem = function (target) {
                        if (typeof target === "number") {
                            return this.items[target];
                        }
                        else if (typeof target === "string") {
                            return _.find(this.items, ["key", target]);
                        }
                        else {
                            return undefined;
                        }
                    };
                    ContextMenu.prototype.addItem = function (item) {
                        this.items.push(item);
                        this.refresh();
                    };
                    ContextMenu.prototype.removeItem = function (target) {
                        var item = this.getItem(target);
                        if (item !== undefined) {
                            _.remove(this.items, item);
                            this.refresh();
                        }
                    };
                    ContextMenu.prototype.setEnable = function (enable) {
                        this.enable = enable;
                    };
                    ContextMenu.prototype.setEnableItem = function (enable, target) {
                        var item = this.getItem(target);
                        item.enable = enable;
                        this.refresh();
                    };
                    ContextMenu.prototype.setVisibleItem = function (visible, target) {
                        var item = this.getItem(target);
                        item.visible = visible;
                        this.refresh();
                    };
                    ContextMenu.prototype.createMenuItems = function (container) {
                        var self = this;
                        _.forEach(self.items, function (item) {
                            if (item.key !== "divider") {
                                var menuClasses = "menu-item ";
                                menuClasses += (item.enable === true) ? "" : "disabled ";
                                menuClasses += (item.visible === true) ? "" : "hidden ";
                                var menuItem = $("<li class='" + menuClasses + "'><span class='menu-icon " + item.icon + "'></span>" + item.text + "</li>")
                                    .data("key", item.key)
                                    .on("click", function () {
                                    if (!$(this).hasClass("disabled")) {
                                        item.handler(self.target);
                                        container.hide();
                                    }
                                }).appendTo(container);
                            }
                            else {
                                var menuItem = $("<li class='menu-item divider'></li>").appendTo(container);
                            }
                        });
                    };
                    return ContextMenu;
                }());
                contextmenu.ContextMenu = ContextMenu;
                var ContextMenuItem = (function () {
                    function ContextMenuItem(key, text, handler, icon, visible, enable) {
                        this.key = key;
                        this.text = text;
                        this.handler = (handler !== undefined) ? handler : $.noop;
                        this.icon = (icon) ? icon : "";
                        this.visible = (visible !== undefined) ? visible : true;
                        this.enable = (enable !== undefined) ? enable : true;
                    }
                    return ContextMenuItem;
                }());
                contextmenu.ContextMenuItem = ContextMenuItem;
            })(contextmenu = ui_1.contextmenu || (ui_1.contextmenu = {}));
            ui_1.confirmSave = function (dirtyChecker) {
                var frame = windows.getSelf();
                if (frame.$dialog === undefined || frame.$dialog === null) {
                    confirmSaveWindow(dirtyChecker);
                }
                else {
                    confirmSaveDialog(dirtyChecker, frame.$dialog);
                }
            };
            function confirmSaveWindow(dirtyChecker) {
                var beforeunloadHandler = function (e) {
                    if (dirtyChecker.isDirty()) {
                        return "ban co muon save hok?";
                    }
                };
                confirmSaveEnable(beforeunloadHandler);
            }
            function confirmSaveDialog(dirtyChecker, dialog) {
                var beforeunloadHandler = function (e) {
                    if (dirtyChecker.isDirty()) {
                        e.preventDefault();
                        nts.uk.ui.dialog.confirm("Are you sure you want to leave the page?")
                            .ifYes(function () {
                            dirtyChecker.reset();
                            dialog.dialog("close");
                        }).ifNo(function () {
                        });
                    }
                };
                confirmSaveEnableDialog(beforeunloadHandler, dialog);
            }
            function confirmSaveEnableDialog(beforeunloadHandler, dialog) {
                dialog.on("dialogbeforeclose", beforeunloadHandler);
            }
            ui_1.confirmSaveEnableDialog = confirmSaveEnableDialog;
            ;
            function confirmSaveDisableDialog(dialog) {
                dialog.on("dialogbeforeclose", function () { });
            }
            ui_1.confirmSaveDisableDialog = confirmSaveDisableDialog;
            ;
            function confirmSaveEnable(beforeunloadHandler) {
                $(window).bind('beforeunload', beforeunloadHandler);
            }
            ui_1.confirmSaveEnable = confirmSaveEnable;
            ;
            function confirmSaveDisable() {
                $(window).unbind('beforeunload');
            }
            ui_1.confirmSaveDisable = confirmSaveDisable;
            ;
            var DirtyChecker = (function () {
                function DirtyChecker(targetViewModelObservable) {
                    this.targetViewModel = targetViewModelObservable;
                    this.initialState = this.getCurrentState();
                }
                DirtyChecker.prototype.getCurrentState = function () {
                    return ko.toJSON(this.targetViewModel());
                };
                DirtyChecker.prototype.reset = function () {
                    this.initialState = this.getCurrentState();
                };
                DirtyChecker.prototype.isDirty = function () {
                    return this.initialState !== this.getCurrentState();
                };
                return DirtyChecker;
            }());
            ui_1.DirtyChecker = DirtyChecker;
            var ig;
            (function (ig) {
                var grid;
                (function (grid) {
                    function getRowIdFrom($anyElementInRow) {
                        return $anyElementInRow.closest('tr').attr('data-id');
                    }
                    grid.getRowIdFrom = getRowIdFrom;
                    function getRowIndexFrom($anyElementInRow) {
                        return parseInt($anyElementInRow.closest('tr').attr('data-row-idx'), 10);
                    }
                    grid.getRowIndexFrom = getRowIndexFrom;
                    var virtual;
                    (function (virtual) {
                        function getDisplayContainer(gridId) {
                            return $('#' + gridId + '_displayContainer');
                        }
                        virtual.getDisplayContainer = getDisplayContainer;
                        function getVisibleRows(gridId) {
                            return $('#' + gridId + ' > tbody > tr:visible');
                        }
                        virtual.getVisibleRows = getVisibleRows;
                        function getFirstVisibleRow(gridId) {
                            var top = getDisplayContainer(gridId).scrollTop();
                            var visibleRows = getVisibleRows(gridId);
                            for (var i = 0; i < visibleRows.length; i++) {
                                var $row = $(visibleRows[i]);
                                if (visibleRows[i].offsetTop + $row.height() > top) {
                                    return $row;
                                }
                            }
                        }
                        virtual.getFirstVisibleRow = getFirstVisibleRow;
                        function getLastVisibleRow(gridId) {
                            var $displayContainer = getDisplayContainer(gridId);
                            var bottom = $displayContainer.scrollTop() + $displayContainer.height();
                            return getVisibleRows(gridId).filter(function () {
                                return this.offsetTop < bottom;
                            }).last();
                        }
                        virtual.getLastVisibleRow = getLastVisibleRow;
                    })(virtual = grid.virtual || (grid.virtual = {}));
                    var header;
                    (function (header) {
                        function getCell(gridId, columnKey) {
                            var $headers = $('#' + gridId).igGrid("headersTable");
                            return $headers.find('#' + gridId + '_' + columnKey);
                        }
                        header.getCell = getCell;
                        function getLabel(gridId, columnKey) {
                            return getCell(gridId, columnKey).find('span');
                        }
                        header.getLabel = getLabel;
                    })(header = grid.header || (grid.header = {}));
                })(grid = ig.grid || (ig.grid = {}));
            })(ig = ui_1.ig || (ui_1.ig = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_2) {
            var option;
            (function (option_1) {
                var DialogOption = (function () {
                    function DialogOption() {
                        this.show = false;
                    }
                    return DialogOption;
                }());
                var ConfirmDialogOption = (function (_super) {
                    __extends(ConfirmDialogOption, _super);
                    function ConfirmDialogOption(option) {
                        _super.call(this);
                        this.modal = (option && option.modal !== undefined) ? option.modal : true;
                        this.buttons = [];
                        this.buttons.push({ text: "OK",
                            "class": "yes",
                            size: "large",
                            color: "proceed",
                            click: function (viewmodel, ui) {
                                viewmodel.okButtonClicked();
                                $(ui).dialog("close");
                            }
                        });
                    }
                    return ConfirmDialogOption;
                }(DialogOption));
                option_1.ConfirmDialogOption = ConfirmDialogOption;
                var DelDialogOption = (function (_super) {
                    __extends(DelDialogOption, _super);
                    function DelDialogOption(option) {
                        _super.call(this);
                        this.modal = (option && option.modal !== undefined) ? option.modal : true;
                        this.buttons = [];
                        this.buttons.push({ text: "はい",
                            "class": "yes ",
                            size: "large",
                            color: "danger",
                            click: function (viewmodel, ui) {
                                viewmodel.okButtonClicked();
                                ui.dialog("close");
                            }
                        });
                        this.buttons.push({ text: "いいえ",
                            "class": "no ",
                            size: "large",
                            color: "",
                            click: function (viewmodel, ui) {
                                viewmodel.cancelButtonClicked();
                                ui.dialog("close");
                            }
                        });
                    }
                    return DelDialogOption;
                }(DialogOption));
                option_1.DelDialogOption = DelDialogOption;
                var OKDialogOption = (function (_super) {
                    __extends(OKDialogOption, _super);
                    function OKDialogOption(option) {
                        _super.call(this);
                        this.modal = (option && option.modal !== undefined) ? option.modal : true;
                        this.buttons = [];
                        this.buttons.push({ text: "はい",
                            "class": "yes ",
                            size: "large",
                            color: "proceed",
                            click: function (viewmodel, ui) {
                                viewmodel.okButtonClicked();
                                ui.dialog("close");
                            }
                        });
                        this.buttons.push({ text: "いいえ",
                            "class": "no ",
                            size: "large",
                            color: "",
                            click: function (viewmodel, ui) {
                                viewmodel.cancelButtonClicked();
                                ui.dialog("close");
                            }
                        });
                    }
                    return OKDialogOption;
                }(DialogOption));
                option_1.OKDialogOption = OKDialogOption;
                var ErrorDialogOption = (function (_super) {
                    __extends(ErrorDialogOption, _super);
                    function ErrorDialogOption(option) {
                        _super.call(this);
                        this.headers = (option && option.headers) ? option.headers : [
                            new nts.uk.ui.errors.ErrorHeader("location", "エラー箇所", 115, true),
                            new nts.uk.ui.errors.ErrorHeader("message", "エラー詳細", 250, true)
                        ];
                        this.modal = (option && option.modal !== undefined) ? option.modal : false;
                        this.displayrows = (option && option.displayrows) ? option.displayrows : 10;
                        this.maxrows = (option && option.maxrows) ? option.maxrows : 1000;
                        this.autoclose = (option && option.autoclose !== undefined) ? option.autoclose : true;
                        this.buttons = [];
                        this.buttons.push({ text: "閉じる",
                            "class": "yes ",
                            size: "large",
                            color: "",
                            click: function (viewmodel, ui) {
                                viewmodel.closeButtonClicked();
                                ui.dialog("close");
                            }
                        });
                    }
                    return ErrorDialogOption;
                }(DialogOption));
                option_1.ErrorDialogOption = ErrorDialogOption;
                var ErrorDialogWithTabOption = (function (_super) {
                    __extends(ErrorDialogWithTabOption, _super);
                    function ErrorDialogWithTabOption(option) {
                        _super.call(this);
                        this.headers = (option && option.headers) ? option.headers : [
                            new ui_2.errors.ErrorHeader("tab", "タブ", 90, true),
                            new ui_2.errors.ErrorHeader("location", "エラー箇所", 115, true),
                            new ui_2.errors.ErrorHeader("message", "エラー詳細", 250, true)
                        ];
                        this.modal = (option && option.modal !== undefined) ? option.modal : false;
                        this.displayrows = (option && option.displayrows) ? option.displayrows : 10;
                        this.maxrows = (option && option.maxrows) ? option.maxrows : 1000;
                        this.autoclose = (option && option.autoclose !== undefined) ? option.autoclose : true;
                        this.buttons = [];
                        this.buttons.push({ text: "閉じる",
                            "class": "yes ",
                            size: "large",
                            color: "",
                            click: function (viewmodel, ui) {
                                viewmodel.closeButtonClicked();
                                ui.dialog("close");
                            }
                        });
                    }
                    return ErrorDialogWithTabOption;
                }(ErrorDialogOption));
                option_1.ErrorDialogWithTabOption = ErrorDialogWithTabOption;
                var DialogButton = (function () {
                    function DialogButton() {
                    }
                    DialogButton.prototype.click = function (viewmodel, ui) { };
                    ;
                    return DialogButton;
                }());
            })(option = ui_2.option || (ui_2.option = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var option;
            (function (option_2) {
                var EditorOptionBase = (function () {
                    function EditorOptionBase() {
                    }
                    return EditorOptionBase;
                }());
                var TextEditorOption = (function (_super) {
                    __extends(TextEditorOption, _super);
                    function TextEditorOption(option) {
                        _super.call(this);
                        this.textmode = (option !== undefined && option.textmode !== undefined) ? option.textmode : "text";
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "left";
                        this.filldirection = (option !== undefined && option.filldirection !== undefined) ? option.filldirection : "right";
                        this.fillcharacter = (option !== undefined && option.fillcharacter !== undefined) ? option.fillcharacter : "0";
                    }
                    return TextEditorOption;
                }(EditorOptionBase));
                option_2.TextEditorOption = TextEditorOption;
                var TimeEditorOption = (function (_super) {
                    __extends(TimeEditorOption, _super);
                    function TimeEditorOption(option) {
                        _super.call(this);
                        this.inputFormat = (option !== undefined && option.inputFormat !== undefined) ? option.inputFormat : "date";
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "left";
                    }
                    return TimeEditorOption;
                }(EditorOptionBase));
                option_2.TimeEditorOption = TimeEditorOption;
                var NumberEditorOption = (function (_super) {
                    __extends(NumberEditorOption, _super);
                    function NumberEditorOption(option) {
                        _super.call(this);
                        this.groupseperator = (option !== undefined && option.groupseperator !== undefined) ? option.groupseperator : ",";
                        this.grouplength = (option !== undefined && option.grouplength !== undefined) ? option.grouplength : 0;
                        this.decimalseperator = (option !== undefined && option.decimalseperator !== undefined) ? option.decimalseperator : ".";
                        this.decimallength = (option !== undefined && option.decimallength !== undefined) ? option.decimallength : 0;
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "left";
                        this.symbolChar = (option !== undefined && option.symbolChar !== undefined) ? option.symbolChar : "";
                        this.symbolPosition = (option !== undefined && option.symbolPosition !== undefined) ? option.symbolPosition : "right";
                    }
                    return NumberEditorOption;
                }(EditorOptionBase));
                option_2.NumberEditorOption = NumberEditorOption;
                var CurrencyEditorOption = (function (_super) {
                    __extends(CurrencyEditorOption, _super);
                    function CurrencyEditorOption(option) {
                        _super.call(this);
                        this.groupseperator = (option !== undefined && option.groupseperator !== undefined) ? option.groupseperator : ",";
                        this.grouplength = (option !== undefined && option.grouplength !== undefined) ? option.grouplength : 0;
                        this.decimalseperator = (option !== undefined && option.decimalseperator !== undefined) ? option.decimalseperator : ".";
                        this.decimallength = (option !== undefined && option.decimallength !== undefined) ? option.decimallength : 0;
                        this.currencyformat = (option !== undefined && option.currencyformat !== undefined) ? option.currencyformat : "JPY";
                        this.currencyposition = (option !== undefined && option.currencyposition !== undefined)
                            ? option.currencyposition : getCurrencyPosition(this.currencyformat);
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "left";
                    }
                    return CurrencyEditorOption;
                }(NumberEditorOption));
                option_2.CurrencyEditorOption = CurrencyEditorOption;
                function getCurrencyPosition(currencyformat) {
                    return currenryPosition[currencyformat] === null ? "right" : currenryPosition[currencyformat];
                }
                var MultilineEditorOption = (function (_super) {
                    __extends(MultilineEditorOption, _super);
                    function MultilineEditorOption(option) {
                        _super.call(this);
                        this.resizeable = (option !== undefined && option.resizeable !== undefined) ? option.resizeable : false;
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "left";
                    }
                    return MultilineEditorOption;
                }(EditorOptionBase));
                option_2.MultilineEditorOption = MultilineEditorOption;
                var currenryPosition = {
                    "JPY": "left",
                    "USD": "right"
                };
            })(option = ui.option || (ui.option = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
                var ntsError;
                (function (ntsError) {
                    var DATA_HAS_ERROR = 'hasError';
                    $.fn.ntsError = function (action, message) {
                        var $control = $(this);
                        if (action === DATA_HAS_ERROR) {
                            return _.some($control, function (c) { return hasError($(c)); });
                        }
                        else {
                            $control.each(function (index) {
                                var $item = $(this);
                                $item = processErrorOnItem($item, message, action);
                            });
                            return $control;
                        }
                    };
                    function processErrorOnItem($control, message, action) {
                        switch (action) {
                            case 'set':
                                return setError($control, message);
                            case 'clear':
                                return clearErrors($control);
                        }
                    }
                    function setError($control, message) {
                        $control.data(DATA_HAS_ERROR, true);
                        ui.errors.add({
                            location: $control.data('name') || "",
                            message: message,
                            $control: $control
                        });
                        $control.parent().addClass('error');
                        return $control;
                    }
                    function clearErrors($control) {
                        $control.data(DATA_HAS_ERROR, false);
                        ui.errors.removeByElement($control);
                        $control.parent().removeClass('error');
                        return $control;
                    }
                    function hasError($control) {
                        return $control.data(DATA_HAS_ERROR) === true;
                    }
                })(ntsError || (ntsError = {}));
                var ntsPopup;
                (function (ntsPopup) {
                    var DATA_INSTANCE_NAME = 'nts-popup-panel';
                    $.fn.ntsPopup = function () {
                        if (arguments.length === 1) {
                            var p = arguments[0];
                            if (_.isPlainObject(p)) {
                                return init.apply(this, arguments);
                            }
                        }
                        if (typeof arguments[0] === 'string') {
                            return handleMethod.apply(this, arguments);
                        }
                    };
                    function init(param) {
                        var popup = new NtsPopupPanel($(this), param.position);
                        var dismissible = param.dismissible === false;
                        _.defer(function () {
                            if (!dismissible) {
                                $(window).mousedown(function (e) {
                                    if ($(e.target).closest(popup.$panel).length === 0) {
                                        popup.hide();
                                    }
                                });
                            }
                        });
                        return popup.$panel;
                    }
                    function handleMethod() {
                        var methodName = arguments[0];
                        var popup = $(this).data(DATA_INSTANCE_NAME);
                        switch (methodName) {
                            case 'show':
                                popup.show();
                                break;
                            case 'hide':
                                popup.hide();
                                break;
                            case 'destroy':
                                popup.hide();
                                popup.destroy();
                                break;
                            case 'toggle':
                                popup.toggle();
                                break;
                        }
                    }
                    var NtsPopupPanel = (function () {
                        function NtsPopupPanel($panel, position) {
                            this.position = position;
                            var parent = $panel.parent();
                            this.$panel = $panel
                                .data(DATA_INSTANCE_NAME, this)
                                .addClass('popup-panel')
                                .appendTo(parent);
                            this.$panel.css("z-index", 100);
                        }
                        NtsPopupPanel.prototype.show = function () {
                            this.$panel
                                .css({
                                visibility: 'hidden',
                                display: 'block'
                            })
                                .position(this.position)
                                .css({
                                visibility: 'visible'
                            });
                        };
                        NtsPopupPanel.prototype.hide = function () {
                            this.$panel.css({
                                display: 'none'
                            });
                        };
                        NtsPopupPanel.prototype.destroy = function () {
                            this.$panel = null;
                        };
                        NtsPopupPanel.prototype.toggle = function () {
                            var isDisplaying = this.$panel.css("display");
                            if (isDisplaying === 'none') {
                                this.show();
                            }
                            else {
                                this.hide();
                            }
                        };
                        return NtsPopupPanel;
                    }());
                })(ntsPopup || (ntsPopup = {}));
                var ntsGridList;
                (function (ntsGridList) {
                    var OUTSIDE_AUTO_SCROLL_SPEED = {
                        RATIO: 0.2,
                        MAX: 30
                    };
                    $.fn.ntsGridList = function (action, param) {
                        var $grid = $(this);
                        switch (action) {
                            case 'setupSelecting':
                                return setupSelecting($grid);
                            case 'getSelected':
                                return getSelected($grid);
                            case 'setSelected':
                                return setSelected($grid, param);
                            case 'deselectAll':
                                return deselectAll($grid);
                            case 'setupDeleteButton':
                                return setupDeleteButton($grid, param);
                        }
                    };
                    function getSelected($grid) {
                        if ($grid.igGridSelection('option', 'multipleSelection')) {
                            var selectedRows = $grid.igGridSelection('selectedRows');
                            if (selectedRows)
                                return _.map(selectedRows, convertSelected);
                            return [];
                        }
                        else {
                            var selectedRow = $grid.igGridSelection('selectedRow');
                            if (selectedRow)
                                return convertSelected(selectedRow);
                            return undefined;
                        }
                    }
                    function convertSelected(igGridSelectedRow) {
                        return {
                            id: igGridSelectedRow.id,
                            index: igGridSelectedRow.index
                        };
                    }
                    function setSelected($grid, selectedId) {
                        deselectAll($grid);
                        if ($grid.igGridSelection('option', 'multipleSelection')) {
                            selectedId.forEach(function (id) { return $grid.igGridSelection('selectRowById', id); });
                        }
                        else {
                            $grid.igGridSelection('selectRowById', selectedId);
                        }
                    }
                    function deselectAll($grid) {
                        $grid.igGridSelection('clearSelection');
                    }
                    function setupDeleteButton($grid, param) {
                        var itemDeletedEvent = new CustomEvent("itemDeleted", {
                            detail: {},
                        });
                        var currentColumns = $grid.igGrid("option", "columns");
                        currentColumns.push({
                            dataType: "bool", columnCssClass: "delete-column", headerText: "test", key: param.deleteField,
                            width: 60, formatter: function createButton(deleteField, row) {
                                var primaryKey = $grid.igGrid("option", "primaryKey");
                                var result = $('<button class="small delete-button">Delete</button>');
                                result.attr("data-value", row[primaryKey]);
                                if (deleteField === true && primaryKey !== null && !uk.util.isNullOrUndefined(row[primaryKey])) {
                                    return result[0].outerHTML;
                                }
                                else {
                                    return result.attr("disabled", "disabled")[0].outerHTML;
                                }
                            }
                        });
                        $grid.igGrid("option", "columns", currentColumns);
                        $grid.on("click", ".delete-button", function () {
                            var key = $(this).attr("data-value");
                            var primaryKey = $grid.igGrid("option", "primaryKey");
                            var source = _.cloneDeep($grid.igGrid("option", "dataSource"));
                            _.remove(source, function (current) {
                                return _.isEqual(current[primaryKey].toString(), key.toString());
                            });
                            if (!uk.util.isNullOrUndefined(param.sourceTarget) && typeof param.sourceTarget === "function") {
                                param.sourceTarget(source);
                            }
                            else {
                                $grid.igGrid("option", "dataSource", source);
                                $grid.igGrid("dataBind");
                            }
                            itemDeletedEvent.detail["target"] = key;
                            document.getElementById($grid.attr('id')).dispatchEvent(itemDeletedEvent);
                        });
                    }
                    function setupSelecting($grid) {
                        setupDragging($grid);
                        setupSelectingEvents($grid);
                        return $grid;
                    }
                    function setupDragging($grid) {
                        var dragSelectRange = [];
                        var mousePos = null;
                        $grid.bind('mousedown', function (e) {
                            var $container = $grid.closest('.ui-iggrid-scrolldiv');
                            if ($(e.target).closest('.ui-iggrid-table').length === 0) {
                                return;
                            }
                            var gridVerticalRange = new uk.util.Range($container.offset().top, $container.offset().top + $container.height());
                            mousePos = {
                                x: e.pageX,
                                y: e.pageY,
                                rowIndex: ui.ig.grid.getRowIndexFrom($(e.target))
                            };
                            dragSelectRange.push(mousePos.rowIndex);
                            var $scroller = $('#' + $grid.attr('id') + '_scrollContainer');
                            var timerAutoScroll = setInterval(function () {
                                var distance = gridVerticalRange.distanceFrom(mousePos.y);
                                if (distance === 0) {
                                    return;
                                }
                                var delta = Math.min(distance * OUTSIDE_AUTO_SCROLL_SPEED.RATIO, OUTSIDE_AUTO_SCROLL_SPEED.MAX);
                                var currentScrolls = $scroller.scrollTop();
                                $grid.igGrid('virtualScrollTo', (currentScrolls + delta) + 'px');
                            }, 20);
                            $(window).bind('mousemove.NtsGridListDragging', function (e) {
                                var newPointedRowIndex = ui.ig.grid.getRowIndexFrom($(e.target));
                                if (mousePos.rowIndex === newPointedRowIndex) {
                                    return;
                                }
                                mousePos = {
                                    x: e.pageX,
                                    y: e.pageY,
                                    rowIndex: newPointedRowIndex
                                };
                                if (dragSelectRange.length === 1 && !e.ctrlKey) {
                                    $grid.igGridSelection('clearSelection');
                                }
                                updateSelections();
                            });
                            $(window).one('mouseup', function (e) {
                                mousePos = null;
                                dragSelectRange = [];
                                $(window).unbind('mousemove.NtsGridListDragging');
                                clearInterval(timerAutoScroll);
                            });
                        });
                        function updateSelections() {
                            if (isNaN(mousePos.rowIndex)) {
                                return;
                            }
                            for (var i = 0, i_len = dragSelectRange.length; i < i_len; i++) {
                                $grid.igGridSelection('deselectRow', dragSelectRange[i]);
                            }
                            var newDragSelectRange = [];
                            if (dragSelectRange[0] <= mousePos.rowIndex) {
                                for (var j = dragSelectRange[0]; j <= mousePos.rowIndex; j++) {
                                    $grid.igGridSelection('selectRow', j);
                                    newDragSelectRange.push(j);
                                }
                            }
                            else if (dragSelectRange[0] > mousePos.rowIndex) {
                                for (var j = dragSelectRange[0]; j >= mousePos.rowIndex; j--) {
                                    $grid.igGridSelection('selectRow', j);
                                    newDragSelectRange.push(j);
                                }
                            }
                            dragSelectRange = newDragSelectRange;
                        }
                    }
                    function setupSelectingEvents($grid) {
                        $grid.bind('iggridselectioncellselectionchanging', function () {
                        });
                        $grid.bind('iggridselectionrowselectionchanged', function () {
                            $grid.triggerHandler('selectionchanged');
                        });
                        $grid.on('mouseup', function () {
                            $grid.triggerHandler('selectionchanged');
                        });
                    }
                })(ntsGridList || (ntsGridList = {}));
                var ntsListBox;
                (function (ntsListBox) {
                    $.fn.ntsListBox = function (action) {
                        var $grid = $(this);
                        switch (action) {
                            case 'deselectAll':
                                deselectAll($grid);
                                break;
                            case 'selectAll':
                                selectAll($grid);
                            default:
                                break;
                        }
                    };
                    function selectAll($list) {
                        $list.find('.nts-list-box > li').addClass("ui-selected");
                        $list.find("li").attr("clicked", "");
                        $list.find('.nts-list-box').data("ui-selectable")._mouseStop(null);
                    }
                    function deselectAll($list) {
                        var selectListBoxContainer = $list.find('.nts-list-box');
                        selectListBoxContainer.data('value', '');
                        $list.find('.nts-list-box > li').removeClass("ui-selected");
                        $list.find('.nts-list-box > li > div').removeClass("ui-selected");
                        $list.trigger("selectionChange");
                    }
                })(ntsListBox || (ntsListBox = {}));
                var ntsEditor;
                (function (ntsEditor) {
                    $.fn.ntsEditor = function (action) {
                        var $editor = $(this);
                        switch (action) {
                            case 'validate':
                                validate($editor);
                            default:
                                break;
                        }
                    };
                    function validate($editor) {
                        var validateEvent = new CustomEvent("validate", {});
                        $editor.each(function (index) {
                            var $input = $(this);
                            document.getElementById($input.attr('id')).dispatchEvent(validateEvent);
                        });
                    }
                })(ntsEditor || (ntsEditor = {}));
                var ntsWizard;
                (function (ntsWizard) {
                    $.fn.ntsWizard = function (action, index) {
                        var $wizard = $(this);
                        if (action === "begin") {
                            return begin($wizard);
                        }
                        else if (action === "end") {
                            return end($wizard);
                        }
                        else if (action === "goto") {
                            return goto($wizard, index);
                        }
                        else if (action === "prev") {
                            return prev($wizard);
                        }
                        else if (action === "next") {
                            return next($wizard);
                        }
                        else if (action === "getCurrentStep") {
                            return getCurrentStep($wizard);
                        }
                        else {
                            return $wizard;
                        }
                        ;
                    };
                    function begin(wizard) {
                        wizard.setStep(0);
                        return wizard;
                    }
                    function end(wizard) {
                        wizard.setStep(wizard.data("length") - 1);
                        return wizard;
                    }
                    function goto(wizard, index) {
                        wizard.setStep(index);
                        return wizard;
                    }
                    function prev(wizard) {
                        wizard.steps("previous");
                        return wizard;
                    }
                    function next(wizard) {
                        wizard.steps("next");
                        return wizard;
                    }
                    function getCurrentStep(wizard) {
                        return wizard.steps("getCurrentIndex");
                    }
                })(ntsWizard || (ntsWizard = {}));
                var ntsUserGuide;
                (function (ntsUserGuide) {
                    $.fn.ntsUserGuide = function (action) {
                        var $controls = $(this);
                        if (nts.uk.util.isNullOrUndefined(action) || action === "init") {
                            return init($controls);
                        }
                        else if (action === "destroy") {
                            return destroy($controls);
                        }
                        else if (action === "show") {
                            return show($controls);
                        }
                        else if (action === "hide") {
                            return hide($controls);
                        }
                        else if (action === "toggle") {
                            return toggle($controls);
                        }
                        else if (action === "isShow") {
                            return isShow($controls);
                        }
                        else {
                            return $controls;
                        }
                        ;
                    };
                    function init(controls) {
                        controls.each(function () {
                            var $control = $(this);
                            $control.remove();
                            if (!$control.hasClass("ntsUserGuide"))
                                $control.addClass("ntsUserGuide");
                            $($control).appendTo($("body")).show();
                            var target = $control.data('target');
                            var direction = $control.data('direction');
                            $control.children().each(function () {
                                var $box = $(this);
                                var boxDirection = $box.data("direction");
                                $box.addClass("userguide-box caret-" + getReveseDirection(boxDirection) + " caret-overlay");
                            });
                            var $overlay = $("<div class='userguide-overlay'></div>")
                                .addClass("overlay-" + direction)
                                .appendTo($control);
                            $control.hide();
                        });
                        $("html").on("mouseup keypress", { controls: controls }, hideBinding);
                        return controls;
                    }
                    function destroy(controls) {
                        controls.each(function () {
                            $(this).remove();
                        });
                        $("html").off("mouseup keypress", hideBinding);
                        return controls;
                    }
                    function hideBinding(e) {
                        e.data.controls.each(function () {
                            $(this).hide();
                        });
                        return e.data.controls;
                    }
                    function show(controls) {
                        controls.each(function () {
                            var $control = $(this);
                            $control.show();
                            var target = $control.data('target');
                            var direction = $control.data('direction');
                            $control.find(".userguide-overlay").each(function (index, elem) {
                                calcOverlayPosition($(elem), target, direction);
                            });
                            $control.children().each(function () {
                                var $box = $(this);
                                var boxTarget = $box.data("target");
                                var boxDirection = $box.data("direction");
                                var boxMargin = ($box.data("margin")) ? $box.data("margin") : "20";
                                calcBoxPosition($box, boxTarget, boxDirection, boxMargin);
                            });
                        });
                        return controls;
                    }
                    function hide(controls) {
                        controls.each(function () {
                            $(this).hide();
                        });
                        return controls;
                    }
                    function toggle(controls) {
                        if (isShow(controls))
                            hide(controls);
                        else
                            show(controls);
                        return controls;
                    }
                    function isShow(controls) {
                        var result = true;
                        controls.each(function () {
                            if (!$(this).is(":visible"))
                                result = false;
                        });
                        return result;
                    }
                    function calcOverlayPosition(overlay, target, direction) {
                        if (direction === "left")
                            return overlay.css("right", "auto")
                                .css("width", $(target).offset().left);
                        else if (direction === "right")
                            return overlay.css("left", $(target).offset().left + $(target).outerWidth());
                        else if (direction === "top")
                            return overlay.css("position", "absolute")
                                .css("bottom", "auto")
                                .css("height", $(target).offset().top);
                        else if (direction === "bottom")
                            return overlay.css("position", "absolute")
                                .css("top", $(target).offset().top + $(target).outerHeight())
                                .css("height", $("body").height() - $(target).offset().top);
                    }
                    function calcBoxPosition(box, target, direction, margin) {
                        var operation = "+";
                        if (direction === "left" || direction === "top")
                            operation = "-";
                        return box.position({
                            my: getReveseDirection(direction) + operation + margin,
                            at: direction,
                            of: target,
                            collision: "none"
                        });
                    }
                    function getReveseDirection(direction) {
                        if (direction === "left")
                            return "right";
                        else if (direction === "right")
                            return "left";
                        else if (direction === "top")
                            return "bottom";
                        else if (direction === "bottom")
                            return "top";
                    }
                })(ntsUserGuide || (ntsUserGuide = {}));
                var ntsSearchBox;
                (function (ntsSearchBox) {
                    $.fn.setupSearchScroll = function (controlType, virtualization) {
                        var $control = this;
                        if (controlType.toLowerCase() == 'iggrid')
                            return setupIgGridScroll($control, virtualization);
                        if (controlType.toLowerCase() == 'igtreegrid')
                            return setupTreeGridScroll($control, virtualization);
                        if (controlType.toLowerCase() == 'igtree')
                            return setupIgTreeScroll($control);
                        return this;
                    };
                    function setupIgGridScroll($control, virtualization) {
                        var $grid = $control;
                        if (virtualization) {
                            $grid.on("selectChange", function () {
                                var row = null;
                                var selectedRows = $grid.igGrid("selectedRows");
                                if (selectedRows) {
                                    row = selectedRows[0];
                                }
                                else {
                                    row = $grid.igGrid("selectedRow");
                                }
                                if (row)
                                    $grid.igGrid("virtualScrollTo", row.index);
                            });
                        }
                        else {
                            $grid.on("selectChange", function () {
                                var row = null;
                                var selectedRows = $grid.igGrid("selectedRows");
                                if (selectedRows) {
                                    row = selectedRows[0];
                                }
                                else {
                                    row = $grid.igGrid("selectedRow");
                                }
                                if (row) {
                                    var index = row.index;
                                    var height = row.element[0].scrollHeight;
                                    var gridId = $grid.attr('id');
                                    $("#" + gridId + "_scrollContainer").scrollTop(index * height);
                                }
                            });
                        }
                        return $grid;
                    }
                    function setupTreeGridScroll($control, virtualization) {
                        var $treegrid = $control;
                        var id = $treegrid.attr('id');
                        $treegrid.on("selectChange", function () {
                            var row = null;
                            var selectedRows = $treegrid.igTreeGridSelection("selectedRows");
                            if (selectedRows) {
                                row = selectedRows[0];
                            }
                            else {
                                row = $treegrid.igTreeGridSelection("selectedRow");
                            }
                            if (row) {
                                var index = row.index;
                                var height = row.element[0].scrollHeight;
                                $("#" + id + "_scroll").scrollTop(index * height);
                            }
                        });
                        return $treegrid;
                    }
                    function setupIgTreeScroll($control) {
                        return $control;
                    }
                })(ntsSearchBox || (ntsSearchBox = {}));
                var ntsSideBar;
                (function (ntsSideBar) {
                    $.fn.ntsSideBar = function (action, index) {
                        var $control = $(this);
                        if (nts.uk.util.isNullOrUndefined(action) || action === "init") {
                            return init($control);
                        }
                        else if (action === "active") {
                            return active($control, index);
                        }
                        else if (action === "enable") {
                            return enable($control, index);
                        }
                        else if (action === "disable") {
                            return disable($control, index);
                        }
                        else if (action === "show") {
                            return show($control, index);
                        }
                        else if (action === "hide") {
                            return hide($control, index);
                        }
                        else if (action === "getCurrent") {
                            return getCurrent($control);
                        }
                        else {
                            return $control;
                        }
                        ;
                    };
                    function init(control) {
                        $("html").addClass("sidebar-html");
                        control.find("div[role=tabpanel]").hide();
                        control.on("click", "#sidebar-area .navigator a", function (e) {
                            e.preventDefault();
                            if ($(this).attr("disabled") !== "true" &&
                                $(this).attr("disabled") !== "disabled" &&
                                $(this).attr("href") !== undefined) {
                                active(control, $(this).closest("li").index());
                            }
                        });
                        control.find("#sidebar-area .navigator a.active").trigger('click');
                        return control;
                    }
                    function active(control, index) {
                        control.find("#sidebar-area .navigator a").removeClass("active");
                        control.find("#sidebar-area .navigator a").eq(index).addClass("active");
                        control.find("div[role=tabpanel]").hide();
                        $(control.find("#sidebar-area .navigator a").eq(index).attr("href")).show();
                        return control;
                    }
                    function enable(control, index) {
                        control.find("#sidebar-area .navigator a").eq(index).removeAttr("disabled");
                        return control;
                    }
                    function disable(control, index) {
                        control.find("#sidebar-area .navigator a").eq(index).attr("disabled", "disabled");
                        return control;
                    }
                    function show(control, index) {
                        control.find("#sidebar-area .navigator a").eq(index).show();
                        return control;
                    }
                    function hide(control, index) {
                        var current = getCurrent(control);
                        if (current === index) {
                            active(control, 0);
                        }
                        control.find("#sidebar-area .navigator a").eq(index).hide();
                        return control;
                    }
                    function getCurrent(control) {
                        var index = 0;
                        index = control.find("#sidebar-area .navigator a.active").closest("li").index();
                        return index;
                    }
                })(ntsSideBar || (ntsSideBar = {}));
            })(jqueryExtentions = ui.jqueryExtentions || (ui.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsCheckboxBindingHandler = (function () {
                    function NtsCheckboxBindingHandler() {
                    }
                    NtsCheckboxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var setChecked = data.checked;
                        var textId = data.text;
                        var checkBoxText;
                        var container = $(element);
                        container.addClass("ntsControl");
                        if (textId) {
                            checkBoxText = textId;
                        }
                        else {
                            checkBoxText = container.text();
                            container.text('');
                        }
                        var checkBoxLabel = $("<label class='ntsCheckBox'></label>");
                        var checkBox = $('<input type="checkbox">').on("change", function () {
                            if (typeof setChecked === "function")
                                setChecked($(this).is(":checked"));
                        }).appendTo(checkBoxLabel);
                        var box = $("<span class='box'></span>").appendTo(checkBoxLabel);
                        if (checkBoxText && checkBoxText.length > 0)
                            var label = $("<span class='label'></span>").text(checkBoxText).appendTo(checkBoxLabel);
                        checkBoxLabel.appendTo(container);
                    };
                    NtsCheckboxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var checked = ko.unwrap(data.checked);
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        var checkBox = $(element).find("input[type='checkbox']");
                        checkBox.prop("checked", checked);
                        (enable === true) ? checkBox.removeAttr("disabled") : checkBox.attr("disabled", "disabled");
                    };
                    return NtsCheckboxBindingHandler;
                }());
                var NtsMultiCheckBoxBindingHandler = (function () {
                    function NtsMultiCheckBoxBindingHandler() {
                    }
                    NtsMultiCheckBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        $(element).addClass("ntsControl");
                    };
                    NtsMultiCheckBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var options = ko.unwrap(data.options);
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = data.value;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        var getOptionValue = function (item) {
                            return (optionValue === undefined) ? item : item[optionValue];
                        };
                        if (!_.isEqual(container.data("options"), options)) {
                            container.empty();
                            _.forEach(options, function (option) {
                                var checkBoxLabel = $("<label class='ntsCheckBox'></label>");
                                var checkBox = $('<input type="checkbox">').data("value", getOptionValue(option)).on("change", function () {
                                    var _this = this;
                                    var self = this;
                                    if ($(self).is(":checked"))
                                        selectedValue.push($(self).data("value"));
                                    else
                                        selectedValue.remove(_.find(selectedValue(), function (value) {
                                            return _.isEqual(value, $(_this).data("value"));
                                        }));
                                }).appendTo(checkBoxLabel);
                                var box = $("<span class='box'></span>").appendTo(checkBoxLabel);
                                if (option[optionText] && option[optionText].length > 0)
                                    var label = $("<span class='label'></span>").text(option[optionText]).appendTo(checkBoxLabel);
                                checkBoxLabel.appendTo(container);
                            });
                            container.data("options", options.slice());
                        }
                        container.find("input[type='checkbox']").prop("checked", function () {
                            var _this = this;
                            return (_.find(selectedValue(), function (value) {
                                return _.isEqual(value, $(_this).data("value"));
                            }) !== undefined);
                        });
                        (enable === true) ? container.find("input[type='checkbox']").removeAttr("disabled") : container.find("input[type='checkbox']").attr("disabled", "disabled");
                    };
                    return NtsMultiCheckBoxBindingHandler;
                }());
                ko.bindingHandlers['ntsCheckBox'] = new NtsCheckboxBindingHandler();
                ko.bindingHandlers['ntsMultiCheckBox'] = new NtsMultiCheckBoxBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_3) {
            var koExtentions;
            (function (koExtentions) {
                var ComboBoxBindingHandler = (function () {
                    function ComboBoxBindingHandler() {
                    }
                    ComboBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    ComboBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var self = this;
                        var options = ko.unwrap(data.options);
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = ko.unwrap(data.value);
                        var editable = ko.unwrap(data.editable);
                        var enable = ko.unwrap(data.enable);
                        var columns = ko.unwrap(data.columns);
                        var container = $(element);
                        var comboMode = editable ? 'editable' : 'dropdown';
                        var distanceColumns = '     ';
                        var fillCharacter = ' ';
                        var maxWidthCharacter = 15;
                        if (_.find(options, function (item) { return item[optionValue] === selectedValue; }) === undefined && !editable) {
                            selectedValue = options.length > 0 ? options[0][optionValue] : '';
                            data.value(selectedValue);
                        }
                        var haveColumn = columns && columns.length > 0;
                        var isChangeOptions = !_.isEqual(container.data("options"), options);
                        if (isChangeOptions) {
                            container.data("options", options.slice());
                            options = options.map(function (option) {
                                var newOptionText = '';
                                if (haveColumn) {
                                    _.forEach(columns, function (item, i) {
                                        var prop = option[item.prop];
                                        var length = item.length;
                                        if (i === columns.length - 1) {
                                            newOptionText += prop;
                                        }
                                        else {
                                            newOptionText += uk.text.padRight(prop, fillCharacter, length) + distanceColumns;
                                        }
                                    });
                                }
                                else {
                                    newOptionText = option[optionText];
                                }
                                option['nts-combo-label'] = newOptionText;
                                return option;
                            });
                        }
                        var currentColumnSetting = container.data("columns");
                        var currentComboMode = container.data("comboMode");
                        var isInitCombo = !_.isEqual(currentColumnSetting, columns) || !_.isEqual(currentComboMode, comboMode);
                        if (isInitCombo) {
                            if (container.data("igCombo") != null) {
                                container.igCombo('destroy');
                                container.removeClass('ui-state-disabled');
                            }
                            var itemTemplate = undefined;
                            if (haveColumn) {
                                itemTemplate = '<div class="nts-combo-item">';
                                _.forEach(columns, function (item, i) {
                                    itemTemplate += '<div class="nts-column nts-combo-column-' + i + '">${' + item.prop + '}</div>';
                                });
                                itemTemplate += '</div>';
                            }
                            container.igCombo({
                                dataSource: options,
                                valueKey: data.optionsValue,
                                textKey: 'nts-combo-label',
                                mode: comboMode,
                                disabled: !enable,
                                placeHolder: '',
                                enableClearButton: false,
                                initialSelectedItems: [
                                    { value: selectedValue }
                                ],
                                itemTemplate: itemTemplate,
                                selectionChanged: function (evt, ui) {
                                    if (ui.items.length > 0) {
                                        data.value(ui.items[0].data[optionValue]);
                                    }
                                }
                            });
                        }
                        else {
                            container.igCombo("option", "disabled", !enable);
                        }
                        if (isChangeOptions && !isInitCombo) {
                            container.igCombo("option", "dataSource", options);
                            container.igCombo("dataBind");
                        }
                        if (selectedValue !== undefined && selectedValue !== null) {
                            container.igCombo("value", selectedValue);
                        }
                        if (haveColumn && (isChangeOptions || isInitCombo)) {
                            var totalWidth = 0;
                            var $dropDownOptions = $(container.igCombo("dropDown"));
                            _.forEach(columns, function (item, i) {
                                var charLength = item.length;
                                var width = charLength * maxWidthCharacter + 10;
                                $dropDownOptions.find('.nts-combo-column-' + i).width(width);
                                if (i != columns.length - 1) {
                                    $dropDownOptions.find('.nts-combo-column-' + i).css({ 'float': 'left' });
                                }
                                totalWidth += width + 10;
                            });
                            $dropDownOptions.find('.nts-combo-item').css({ 'min-width': totalWidth });
                            container.css({ 'min-width': totalWidth });
                        }
                        container.data("columns", columns);
                        container.data("comboMode", comboMode);
                    };
                    return ComboBoxBindingHandler;
                }());
                ko.bindingHandlers['ntsComboBox'] = new ComboBoxBindingHandler();
            })(koExtentions = ui_3.koExtentions || (ui_3.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var DatePickerBindingHandler = (function () {
                    function DatePickerBindingHandler() {
                    }
                    DatePickerBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var value = data.value;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var dateFormat = (data.dateFormat !== undefined) ? ko.unwrap(data.dateFormat) : "YYYY/MM/DD";
                        var ISOFormat = uk.text.getISOFormat(dateFormat);
                        var hasDayofWeek = (ISOFormat.indexOf("ddd") !== -1);
                        var dayofWeekFormat = ISOFormat.replace(/[^d]/g, "");
                        ISOFormat = ISOFormat.replace(/d/g, "").trim();
                        var valueFormat = (data.valueFormat !== undefined) ? ko.unwrap(data.valueFormat) : "";
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var button = (data.button !== undefined) ? ko.unwrap(data.button) : false;
                        var startDate = (data.startDate !== undefined) ? ko.unwrap(data.startDate) : null;
                        var endDate = (data.endDate !== undefined) ? ko.unwrap(data.endDate) : null;
                        var autoHide = (data.autoHide !== undefined) ? ko.unwrap(data.autoHide) : true;
                        var valueType = typeof value();
                        if (valueType === "string") {
                            valueFormat = (valueFormat) ? valueFormat : uk.text.getISOFormat("ISO");
                        }
                        else if (valueType === "number") {
                            valueFormat = (valueFormat) ? valueFormat : ISOFormat;
                        }
                        else if (valueType === "object") {
                            if (moment.isDate(value())) {
                                valueType = "date";
                            }
                            else if (moment.isMoment(value())) {
                                valueType = "moment";
                            }
                        }
                        var container = $(element);
                        if (!container.attr("id")) {
                            var idString = nts.uk.util.randomId();
                            container.attr("id", idString);
                        }
                        container.addClass("ntsControl nts-datepicker-wrapper").data("init", true);
                        var inputClass = (ISOFormat.length < 10) ? "yearmonth-picker" : "";
                        var $input = $("<input id='" + container.attr("id") + "-input' class='ntsDatepicker nts-input' />").addClass(inputClass);
                        container.append($input);
                        if (hasDayofWeek) {
                            var lengthClass = (dayofWeekFormat.length > 3) ? "long-day" : "short-day";
                            var $label = $("<label id='" + container.attr("id") + "-label' for='" + container.attr("id") + "-input' class='dayofweek-label' />");
                            $input.addClass(lengthClass);
                            container.append($label);
                        }
                        $input.datepicker({
                            language: 'ja-JP',
                            format: ISOFormat,
                            startDate: startDate,
                            endDate: endDate,
                            autoHide: autoHide,
                        });
                        var validator = new ui.validation.TimeValidator(constraintName, { required: required, inputFormat: valueFormat, valueType: valueType });
                        $input.on("change", function (e) {
                            var newText = $input.val();
                            var result = validator.validate(newText);
                            $input.ntsError('clear');
                            if (result.isValid) {
                                if (hasDayofWeek)
                                    $label.text("(" + uk.time.formatPattern(newText, "", dayofWeekFormat) + ")");
                                value(result.parsedValue);
                            }
                            else {
                                $input.ntsError('set', result.errorMessage);
                                value(newText);
                            }
                        });
                        $input.on('validate', (function (e) {
                            var newText = $input.val();
                            var result = validator.validate(newText);
                            $input.ntsError('clear');
                            if (result.isValid) {
                                $input.ntsError('set', "Invalid format");
                            }
                        }));
                    };
                    DatePickerBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var value = data.value;
                        var dateFormat = (data.dateFormat !== undefined) ? ko.unwrap(data.dateFormat) : "YYYY/MM/DD";
                        var ISOFormat = uk.text.getISOFormat(dateFormat);
                        var hasDayofWeek = (ISOFormat.indexOf("ddd") !== -1);
                        var dayofWeekFormat = ISOFormat.replace(/[^d]/g, "");
                        ISOFormat = ISOFormat.replace(/d/g, "").trim();
                        var valueFormat = (data.valueFormat !== undefined) ? ko.unwrap(data.valueFormat) : ISOFormat;
                        var disabled = (data.disabled !== undefined) ? ko.unwrap(data.disabled) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : undefined;
                        var startDate = (data.startDate !== undefined) ? ko.unwrap(data.startDate) : null;
                        var endDate = (data.endDate !== undefined) ? ko.unwrap(data.endDate) : null;
                        var container = $(element);
                        var init = container.data("init");
                        var $input = container.find(".nts-input");
                        var $label = container.find(".dayofweek-label");
                        var dateFormatValue = (value() !== "") ? uk.time.formatPattern(value(), valueFormat, ISOFormat) : "";
                        if (init === true || uk.time.formatPattern($input.datepicker("getDate", true), "", ISOFormat) !== dateFormatValue) {
                            if (dateFormatValue !== "" && dateFormatValue !== "Invalid date") {
                                $input.datepicker('setDate', dateFormatValue);
                                if (hasDayofWeek)
                                    $label.text("(" + moment.utc(value(), valueFormat).format(dayofWeekFormat) + ")");
                            }
                        }
                        container.data("init", false);
                        $input.datepicker('setStartDate', startDate);
                        $input.datepicker('setEndDate', endDate);
                        if (enable !== undefined)
                            $input.prop("disabled", !enable);
                        else
                            $input.prop("disabled", disabled);
                        if (data.button)
                            container.find('.datepicker-btn').prop("disabled", disabled);
                    };
                    return DatePickerBindingHandler;
                }());
                ko.bindingHandlers['ntsDatePicker'] = new DatePickerBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsDialogBindingHandler = (function () {
                    function NtsDialogBindingHandler() {
                    }
                    NtsDialogBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    NtsDialogBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var option = ko.unwrap(data.option);
                        var title = ko.unwrap(data.title);
                        var message = ko.unwrap(data.message);
                        var modal = ko.unwrap(option.modal);
                        var show = ko.unwrap(option.show);
                        var buttons = ko.unwrap(option.buttons);
                        var $dialog = $("<div id='ntsDialog'></div>");
                        if (show == true) {
                            $('body').append($dialog);
                            var dialogbuttons = [];
                            var _loop_1 = function(button) {
                                dialogbuttons.push({
                                    text: ko.unwrap(button.text),
                                    "class": ko.unwrap(button.class) + ko.unwrap(button.size) + " " + ko.unwrap(button.color),
                                    click: function () { button.click(bindingContext.$data, $dialog); }
                                });
                            };
                            for (var _i = 0, buttons_1 = buttons; _i < buttons_1.length; _i++) {
                                var button = buttons_1[_i];
                                _loop_1(button);
                            }
                            $dialog.dialog({
                                title: title,
                                modal: modal,
                                closeOnEscape: false,
                                buttons: dialogbuttons,
                                dialogClass: "no-close",
                                open: function () {
                                    $(this).parent().find('.ui-dialog-buttonset > button.yes').focus();
                                    $(this).parent().find('.ui-dialog-buttonset > button').removeClass('ui-button ui-corner-all ui-widget');
                                    $('.ui-widget-overlay').last().css('z-index', 120000);
                                },
                                close: function (event) {
                                    bindingContext.$data.option.show(false);
                                }
                            }).text(message);
                        }
                        else {
                            if ($('#ntsDialog').dialog("instance") != null)
                                $('#ntsDialog').dialog("destroy");
                            $('#ntsDialog').remove();
                        }
                    };
                    return NtsDialogBindingHandler;
                }());
                var NtsErrorDialogBindingHandler = (function () {
                    function NtsErrorDialogBindingHandler() {
                    }
                    NtsErrorDialogBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var option = ko.unwrap(data.option);
                        var title = ko.unwrap(data.title);
                        var headers = ko.unwrap(option.headers);
                        var modal = ko.unwrap(option.modal);
                        var show = ko.unwrap(option.show);
                        var buttons = ko.unwrap(option.buttons);
                        var $dialog = $("<div id='ntsErrorDialog'></div>");
                        $('body').append($dialog);
                        var dialogbuttons = [];
                        var _loop_2 = function(button) {
                            dialogbuttons.push({
                                text: ko.unwrap(button.text),
                                "class": ko.unwrap(button.class) + ko.unwrap(button.size) + " " + ko.unwrap(button.color),
                                click: function () { button.click(bindingContext.$data, $dialog); }
                            });
                        };
                        for (var _i = 0, buttons_2 = buttons; _i < buttons_2.length; _i++) {
                            var button = buttons_2[_i];
                            _loop_2(button);
                        }
                        var dialogWidth = 40 + 35 + 17;
                        headers.forEach(function (header, index) {
                            if (ko.unwrap(header.visible)) {
                                dialogWidth += ko.unwrap(header.width);
                            }
                        });
                        $dialog.dialog({
                            title: title,
                            modal: modal,
                            closeOnEscape: false,
                            width: dialogWidth,
                            buttons: dialogbuttons,
                            dialogClass: "no-close",
                            open: function () {
                                $(this).parent().find('.ui-dialog-buttonset > button.yes').focus();
                                $(this).parent().find('.ui-dialog-buttonset > button').removeClass('ui-button ui-corner-all ui-widget');
                                $('.ui-widget-overlay').last().css('z-index', 120000);
                            },
                            close: function (event) {
                                bindingContext.$data.option.show(false);
                            }
                        });
                    };
                    NtsErrorDialogBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var option = ko.unwrap(data.option);
                        var title = ko.unwrap(data.title);
                        var errors = ko.unwrap(data.errors);
                        var headers = ko.unwrap(option.headers);
                        var displayrows = ko.unwrap(option.displayrows);
                        var maxrows = ko.unwrap(option.maxrows);
                        var autoclose = ko.unwrap(option.autoclose);
                        var show = ko.unwrap(option.show);
                        var $dialog = $("#ntsErrorDialog");
                        if (show == true) {
                            $dialog.dialog("open");
                            var $errorboard = $("<div id='error-board'></div>");
                            var $errortable = $("<table></table>");
                            var $header = $("<thead><tr></tr></thead>");
                            $header.find("tr").append("<th style='width: 35px'></th>");
                            headers.forEach(function (header, index) {
                                if (ko.unwrap(header.visible)) {
                                    var $headerElement = $("<th>" + ko.unwrap(header.text) + "</th>").width(ko.unwrap(header.width));
                                    $header.find("tr").append($headerElement);
                                }
                            });
                            $errortable.append($header);
                            var $body = $("<tbody></tbody>");
                            errors.forEach(function (error, index) {
                                if (index < maxrows) {
                                    var $row_1 = $("<tr></tr>");
                                    $row_1.append("<td style='width:35px'>" + (index + 1) + "</td>");
                                    headers.forEach(function (header) {
                                        if (ko.unwrap(header.visible))
                                            if (error.hasOwnProperty(ko.unwrap(header.name))) {
                                                var $column = $("<td>" + error[ko.unwrap(header.name)] + "</td>").width(ko.unwrap(header.width));
                                                $row_1.append($column);
                                            }
                                    });
                                    $body.append($row_1);
                                }
                            });
                            $errortable.append($body);
                            $errorboard.append($errortable);
                            var $message = $("<div></div>");
                            if (errors.length > maxrows)
                                $message.text("Showing " + maxrows + " in total " + errors.length + " errors");
                            $dialog.html("");
                            $dialog.append($errorboard).append($message);
                            $body.height(Math.min(displayrows, errors.length) * $(">:first-child", $body).outerHeight() + 1);
                        }
                        else {
                            $dialog.dialog("close");
                        }
                    };
                    return NtsErrorDialogBindingHandler;
                }());
                ko.bindingHandlers['ntsDialog'] = new NtsDialogBindingHandler();
                ko.bindingHandlers['ntsErrorDialog'] = new NtsErrorDialogBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var validation = nts.uk.ui.validation;
                var EditorProcessor = (function () {
                    function EditorProcessor() {
                    }
                    EditorProcessor.prototype.init = function ($input, data) {
                        var _this = this;
                        var value = data.value;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        var immediate = ko.unwrap(data.immediate !== undefined ? data.immediate : 'false');
                        var valueUpdate = (immediate === true) ? 'input' : 'change';
                        var characterWidth = 9;
                        if (constraint && constraint.maxLength && !$input.is("textarea")) {
                            var autoWidth = constraint.maxLength * characterWidth;
                            $input.width(autoWidth);
                        }
                        $input.addClass('nts-editor nts-input');
                        $input.wrap("<span class= 'nts-editor-wrapped ntsControl'/>");
                        var validator = this.getValidator(data);
                        $input.on(valueUpdate, function (e) {
                            var newText = $input.val();
                            var result = validator.validate(newText);
                            $input.ntsError('clear');
                            if (result.isValid) {
                                value(result.parsedValue);
                            }
                            else {
                                $input.ntsError('set', result.errorMessage);
                                value(newText);
                            }
                        });
                        $input.blur(function () {
                            var formatter = _this.getFormatter(data);
                            var newText = $input.val();
                            var result = validator.validate(newText);
                            if (result.isValid) {
                                $input.val(formatter.format(result.parsedValue));
                            }
                        });
                        $input.on('validate', (function (e) {
                            var newText = $input.val();
                            var result = validator.validate(newText);
                            $input.ntsError('clear');
                            if (!result.isValid) {
                                $input.ntsError('set', result.errorMessage);
                            }
                        }));
                    };
                    EditorProcessor.prototype.update = function ($input, data) {
                        var value = data.value;
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var option = (data.option !== undefined) ? ko.unwrap(data.option) : ko.mapping.fromJS(this.getDefaultOption());
                        var placeholder = ko.unwrap(option.placeholder || '');
                        var width = ko.unwrap(option.width || '');
                        var textalign = ko.unwrap(option.textalign || '');
                        (enable !== false) ? $input.removeAttr('disabled') : $input.attr('disabled', 'disabled');
                        (readonly === false) ? $input.removeAttr('readonly') : $input.attr('readonly', 'readonly');
                        $input.attr('placeholder', placeholder);
                        if (width.trim() != "")
                            $input.width(width);
                        if (textalign.trim() != "")
                            $input.css('text-align', textalign);
                        var formatted = $input.ntsError('hasError')
                            ? value()
                            : this.getFormatter(data).format(value());
                        $input.val(formatted);
                    };
                    EditorProcessor.prototype.getDefaultOption = function () {
                        return {};
                    };
                    EditorProcessor.prototype.getFormatter = function (data) {
                        return new uk.format.NoFormatter();
                    };
                    EditorProcessor.prototype.getValidator = function (data) {
                        return new validation.NoValidator();
                    };
                    return EditorProcessor;
                }());
                var TextEditorProcessor = (function (_super) {
                    __extends(TextEditorProcessor, _super);
                    function TextEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    TextEditorProcessor.prototype.update = function ($input, data) {
                        var editorOption = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var textmode = editorOption.textmode;
                        $input.attr('type', textmode);
                        _super.prototype.update.call(this, $input, data);
                    };
                    TextEditorProcessor.prototype.getDefaultOption = function () {
                        return new nts.uk.ui.option.TextEditorOption();
                    };
                    TextEditorProcessor.prototype.getFormatter = function (data) {
                        var editorOption = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        return new uk.text.StringFormatter({ constraintName: constraintName, constraint: constraint, editorOption: editorOption });
                    };
                    TextEditorProcessor.prototype.getValidator = function (data) {
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        return new validation.StringValidator(constraintName, { required: required });
                    };
                    return TextEditorProcessor;
                }(EditorProcessor));
                var MultilineEditorProcessor = (function (_super) {
                    __extends(MultilineEditorProcessor, _super);
                    function MultilineEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    MultilineEditorProcessor.prototype.update = function ($input, data) {
                        var editorOption = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var resizeable = ko.unwrap(editorOption.resizeable);
                        $input.css('resize', (resizeable) ? "both" : "none");
                        _super.prototype.update.call(this, $input, data);
                    };
                    MultilineEditorProcessor.prototype.getDefaultOption = function () {
                        return new ui.option.MultilineEditorOption();
                    };
                    MultilineEditorProcessor.prototype.getFormatter = function (data) {
                        var editorOption = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        return new uk.text.StringFormatter({ constraintName: constraintName, constraint: constraint, editorOption: editorOption });
                    };
                    MultilineEditorProcessor.prototype.getValidator = function (data) {
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        return new validation.StringValidator(constraintName, { required: required });
                    };
                    return MultilineEditorProcessor;
                }(EditorProcessor));
                var NumberEditorProcessor = (function (_super) {
                    __extends(NumberEditorProcessor, _super);
                    function NumberEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    NumberEditorProcessor.prototype.init = function ($input, data) {
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        $input.focus(function () {
                            var selectionType = document.getSelection().type;
                            $input.val(data.value());
                            if (selectionType === 'Range') {
                                $input.select();
                            }
                        });
                        _super.prototype.init.call(this, $input, data);
                    };
                    NumberEditorProcessor.prototype.update = function ($input, data) {
                        _super.prototype.update.call(this, $input, data);
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var align = option.textalign !== "left" ? "right" : "left";
                        $input.css({ 'text-align': align, "box-sizing": "border-box" });
                        var $parent = $input.parent();
                        var width = option.width;
                        var parentTag = $parent.parent().prop("tagName").toLowerCase();
                        if (parentTag === "td" || parentTag === "th" || parentTag === "a" || width === "100%") {
                            $parent.css({ 'width': '100%' });
                        }
                        if (option.currencyformat !== undefined && option.currencyformat !== null) {
                            $parent.addClass("symbol").addClass(option.currencyposition === 'left' ? 'symbol-left' : 'symbol-right');
                            $input.width(width);
                            var format = option.currencyformat === "JPY" ? "\u00A5" : '$';
                            $parent.attr("data-content", format);
                        }
                        else if (option.symbolChar !== undefined && option.symbolChar !== "" && option.symbolPosition !== undefined) {
                            $parent.addClass("symbol").addClass(option.symbolPosition === 'right' ? 'symbol-right' : 'symbol-left');
                            $input.width(width);
                            $parent.attr("data-content", option.symbolChar);
                        }
                    };
                    NumberEditorProcessor.prototype.getDefaultOption = function () {
                        return new nts.uk.ui.option.NumberEditorOption();
                    };
                    NumberEditorProcessor.prototype.getFormatter = function (data) {
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        return new uk.text.NumberFormatter({ option: option });
                    };
                    NumberEditorProcessor.prototype.getValidator = function (data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        return new validation.NumberValidator(constraintName, option);
                    };
                    return NumberEditorProcessor;
                }(EditorProcessor));
                var TimeEditorProcessor = (function (_super) {
                    __extends(TimeEditorProcessor, _super);
                    function TimeEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    TimeEditorProcessor.prototype.update = function ($input, data) {
                        _super.prototype.update.call(this, $input, data);
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        $input.css({ 'text-align': 'right', "box-sizing": "border-box" });
                        var parent = $input.parent();
                        parent.css({ "display": "inline-block" });
                        var parentTag = parent.parent().prop("tagName").toLowerCase();
                        var width = option.width;
                        if (parentTag === "td" || parentTag === "th" || parentTag === "a" || width === "100%") {
                            parent.css({ 'width': '100%' });
                        }
                        $input.css({ 'paddingLeft': '12px', 'width': width });
                    };
                    TimeEditorProcessor.prototype.getDefaultOption = function () {
                        return new nts.uk.ui.option.TimeEditorOption();
                    };
                    TimeEditorProcessor.prototype.getFormatter = function (data) {
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        return new uk.text.TimeFormatter({ inputFormat: option.inputFormat });
                    };
                    TimeEditorProcessor.prototype.getValidator = function (data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        return new validation.TimeValidator(constraintName, option);
                    };
                    return TimeEditorProcessor;
                }(EditorProcessor));
                var NtsEditorBindingHandler = (function () {
                    function NtsEditorBindingHandler() {
                    }
                    NtsEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new EditorProcessor().init($(element), valueAccessor());
                    };
                    NtsEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new EditorProcessor().update($(element), valueAccessor());
                    };
                    return NtsEditorBindingHandler;
                }());
                var NtsTextEditorBindingHandler = (function (_super) {
                    __extends(NtsTextEditorBindingHandler, _super);
                    function NtsTextEditorBindingHandler() {
                        _super.apply(this, arguments);
                    }
                    NtsTextEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TextEditorProcessor().init($(element), valueAccessor());
                    };
                    NtsTextEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TextEditorProcessor().update($(element), valueAccessor());
                    };
                    return NtsTextEditorBindingHandler;
                }(NtsEditorBindingHandler));
                var NtsNumberEditorBindingHandler = (function () {
                    function NtsNumberEditorBindingHandler() {
                    }
                    NtsNumberEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new NumberEditorProcessor().init($(element), valueAccessor());
                    };
                    NtsNumberEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new NumberEditorProcessor().update($(element), valueAccessor());
                    };
                    return NtsNumberEditorBindingHandler;
                }());
                var NtsTimeEditorBindingHandler = (function () {
                    function NtsTimeEditorBindingHandler() {
                    }
                    NtsTimeEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TimeEditorProcessor().init($(element), valueAccessor());
                    };
                    NtsTimeEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TimeEditorProcessor().update($(element), valueAccessor());
                    };
                    return NtsTimeEditorBindingHandler;
                }());
                var NtsMultilineEditorBindingHandler = (function (_super) {
                    __extends(NtsMultilineEditorBindingHandler, _super);
                    function NtsMultilineEditorBindingHandler() {
                        _super.apply(this, arguments);
                    }
                    NtsMultilineEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new MultilineEditorProcessor().init($(element), valueAccessor());
                    };
                    NtsMultilineEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new MultilineEditorProcessor().update($(element), valueAccessor());
                    };
                    return NtsMultilineEditorBindingHandler;
                }(NtsEditorBindingHandler));
                ko.bindingHandlers['ntsTextEditor'] = new NtsTextEditorBindingHandler();
                ko.bindingHandlers['ntsNumberEditor'] = new NtsNumberEditorBindingHandler();
                ko.bindingHandlers['ntsTimeEditor'] = new NtsTimeEditorBindingHandler();
                ko.bindingHandlers['ntsMultilineEditor'] = new NtsMultilineEditorBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsFormLabelBindingHandler = (function () {
                    function NtsFormLabelBindingHandler() {
                    }
                    NtsFormLabelBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var primitiveValue = ko.unwrap(data.constraint);
                        var isRequired = ko.unwrap(data.required) === true;
                        var isInline = ko.unwrap(data.inline) === true;
                        var isEnable = ko.unwrap(data.enable) !== false;
                        var $formLabel = $(element).addClass('form-label');
                        $('<label/>').html($formLabel.html()).appendTo($formLabel.empty());
                        if (!isEnable) {
                            $formLabel.addClass('disabled');
                        }
                        else {
                            $formLabel.removeClass('disabled');
                        }
                        if (isRequired) {
                            $formLabel.addClass('required');
                        }
                        if (primitiveValue !== undefined) {
                            $formLabel.addClass(isInline ? 'inline' : 'broken');
                            var constraintText = NtsFormLabelBindingHandler.buildConstraintText(primitiveValue);
                            $('<i/>').text(constraintText).appendTo($formLabel);
                        }
                    };
                    NtsFormLabelBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    NtsFormLabelBindingHandler.buildConstraintText = function (primitiveValues) {
                        if (!Array.isArray(primitiveValues))
                            primitiveValues = [primitiveValues];
                        var constraintText = "";
                        _.forEach(primitiveValues, function (primitiveValue) {
                            var constraint = __viewContext.primitiveValueConstraints[primitiveValue];
                            switch (constraint.valueType) {
                                case 'String':
                                    constraintText += (constraintText.length > 0) ? "/" : "";
                                    constraintText += uk.text.getCharType(primitiveValue).buildConstraintText(constraint.maxLength);
                                    break;
                                case 'Decimal':
                                    constraintText += (constraintText.length > 0) ? "/" : "";
                                    constraintText += constraint.min + "～" + constraint.max;
                                    break;
                                case 'Integer':
                                    constraintText += (constraintText.length > 0) ? "/" : "";
                                    constraintText += constraint.min + "～" + constraint.max;
                                    break;
                                default:
                                    constraintText += 'ERROR';
                                    break;
                            }
                        });
                        return constraintText;
                    };
                    return NtsFormLabelBindingHandler;
                }());
                ko.bindingHandlers['ntsFormLabel'] = new NtsFormLabelBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsGridListBindingHandler = (function () {
                    function NtsGridListBindingHandler() {
                    }
                    NtsGridListBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var HEADER_HEIGHT = 27;
                        var $grid = $(element);
                        if (nts.uk.util.isNullOrUndefined($grid.attr('id'))) {
                            throw new Error('the element NtsGridList must have id attribute.');
                        }
                        var data = valueAccessor();
                        var optionsValue = data.primaryKey !== undefined ? data.primaryKey : data.optionsValue;
                        var options = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
                        var deleteOptions = ko.unwrap(data.deleteOptions);
                        var observableColumns = ko.unwrap(data.columns);
                        var iggridColumns = _.map(observableColumns, function (c) {
                            c["key"] = c["key"] === undefined ? c["prop"] : c["key"];
                            c["dataType"] = 'string';
                            return c;
                        });
                        var features = [];
                        features.push({ name: 'Selection', multipleSelection: data.multiple });
                        features.push({ name: 'Sorting', type: 'local' });
                        if (data.multiple) {
                            features.push({ name: 'RowSelectors', enableCheckBoxes: data.multiple, enableRowNumbering: false });
                        }
                        $grid.igGrid({
                            width: data.width,
                            height: (data.height) + "px",
                            primaryKey: optionsValue,
                            columns: iggridColumns,
                            virtualization: true,
                            virtualizationMode: 'continuous',
                            features: features
                        });
                        if (!uk.util.isNullOrUndefined(deleteOptions) && !uk.util.isNullOrUndefined(deleteOptions.deleteField)
                            && deleteOptions.visible === true) {
                            var sources = (data.dataSource !== undefined ? data.dataSource : data.options);
                            $grid.ntsGridList("setupDeleteButton", {
                                deleteField: deleteOptions.deleteField,
                                sourceTarget: sources
                            });
                        }
                        $grid.ntsGridList('setupSelecting');
                        $grid.bind('selectionchanged', function () {
                            if (data.multiple) {
                                var selected = $grid.ntsGridList('getSelected');
                                if (selected) {
                                    data.value(_.map(selected, function (s) { return s.id; }));
                                }
                                else {
                                    data.value([]);
                                }
                            }
                            else {
                                var selected = $grid.ntsGridList('getSelected');
                                if (selected) {
                                    data.value(selected.id);
                                }
                                else {
                                    data.value('');
                                }
                            }
                        });
                        var gridId = $grid.attr('id');
                        $grid.setupSearchScroll("igGrid", true);
                    };
                    NtsGridListBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var $grid = $(element);
                        var data = valueAccessor();
                        var optionsValue = data.primaryKey !== undefined ? data.primaryKey : data.optionsValue;
                        var currentSource = $grid.igGrid('option', 'dataSource');
                        var sources = (data.dataSource !== undefined ? data.dataSource() : data.options());
                        if (!_.isEqual(currentSource, sources)) {
                            var currentSources = sources.slice();
                            var observableColumns = _.filter(ko.unwrap(data.columns), function (c) {
                                c["key"] = c["key"] === undefined ? c["prop"] : c["key"];
                                return c["isDateColumn"] !== undefined && c["isDateColumn"] !== null && c["isDateColumn"] === true;
                            });
                            _.forEach(currentSources, function (s) {
                                _.forEach(observableColumns, function (c) {
                                    var key = c["key"] === undefined ? c["prop"] : c["key"];
                                    s[key] = moment(s[key]).format(c["format"]);
                                });
                            });
                            $grid.igGrid('option', 'dataSource', currentSources);
                            $grid.igGrid("dataBind");
                        }
                        var currentSelectedItems = $grid.ntsGridList('getSelected');
                        var isEqual = _.isEqualWith(currentSelectedItems, data.value(), function (current, newVal) {
                            if ((current === undefined && newVal === undefined) || (current !== undefined && current.id === newVal)) {
                                return true;
                            }
                        });
                        if (!isEqual) {
                            $grid.ntsGridList('setSelected', data.value());
                        }
                        $grid.closest('.ui-iggrid').addClass('nts-gridlist').height(data.height);
                    };
                    return NtsGridListBindingHandler;
                }());
                ko.bindingHandlers['ntsGridList'] = new NtsGridListBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_4) {
            var koExtentions;
            (function (koExtentions) {
                function selectOnListBox(event) {
                    var container = $(event.delegateTarget);
                    container.find(".ui-selected").removeClass('ui-selected');
                    $(this).addClass('ui-selected');
                    container.data('value', $(this).data('value'));
                    document.getElementById(container.parent().attr('id')).dispatchEvent(event.data.event);
                }
                function unbindMultible($target) {
                    $target.selectable("destroy");
                }
                function bindMultible($target, changeEvent) {
                    $target.selectable({
                        filter: 'li',
                        selected: function (event, ui) {
                        },
                        stop: function (event, ui) {
                            var data = [];
                            $("li.ui-selected", $target).each(function (index, opt) {
                                data[index] = $(opt).data('value');
                            });
                            $target.data('value', data);
                            document.getElementById($target.parent().attr('id')).dispatchEvent(changeEvent);
                        },
                        selecting: function (event, ui) {
                            if (event.shiftKey) {
                                if ($(ui.selecting).attr("clicked") !== "true") {
                                    var source = $target.find("li");
                                    var clicked = _.find(source, function (row) {
                                        return $(row).attr("clicked") === "true";
                                    });
                                    if (clicked === undefined) {
                                        $(ui.selecting).attr("clicked", "true");
                                    }
                                    else {
                                        $target.find("li").attr("clicked", "");
                                        $(ui.selecting).attr("clicked", "true");
                                        var start = parseInt($(clicked).attr("data-idx"));
                                        var end = parseInt($(ui.selecting).attr("data-idx"));
                                        var max = start > end ? start : end;
                                        var min = start < end ? start : end;
                                        var range = _.filter(source, function (row) {
                                            var index = parseInt($(row).attr("data-idx"));
                                            return index >= min && index <= max;
                                        });
                                        $(range).addClass("ui-selected");
                                    }
                                }
                            }
                            else if (!event.ctrlKey) {
                                $target.find("li").attr("clicked", "");
                                $(ui.selecting).attr("clicked", "true");
                            }
                        }
                    });
                }
                function bindSingleSelectListBox($target, changeEvent) {
                    $target.on("click", "li", { event: changeEvent }, selectOnListBox);
                }
                function unbindSingleSelectListBox($target) {
                    $target.off("click", "li");
                }
                function selectOneRow(container, selectedValue) {
                    container.find("li").removeClass("ui-selected");
                    var target = _.find($('li', container), function (opt) {
                        var optValue = $(opt).data('value');
                        return optValue == selectedValue;
                    });
                    $(target).addClass('ui-selected');
                }
                function selectMultiRow(container, selectedValues) {
                    container.find("li").removeClass("ui-selected");
                    _.forEach(selectedValues, function (selectedValue) {
                        var target = _.find($('li', container), function (opt) {
                            var optValue = $(opt).data('value');
                            return optValue == selectedValue;
                        });
                        $(target).addClass('ui-selected');
                    });
                }
                var ListBoxBindingHandler = (function () {
                    function ListBoxBindingHandler() {
                    }
                    ListBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var options = ko.unwrap(data.options);
                        var optionValue = ko.unwrap(data.primaryKey === undefined ? data.optionsValue : data.primaryKey);
                        var optionText = ko.unwrap(data.primaryText === undefined ? data.optionsText : data.primaryText);
                        var selectedValue = ko.unwrap(data.value);
                        var isMultiSelect = ko.unwrap(data.multiple);
                        var enable = ko.unwrap(data.enable);
                        var required = ko.unwrap(data.required) || false;
                        var container = $(element);
                        container.addClass('ntsListBox ntsControl').data('required', required);
                        container.data("options", options.slice());
                        container.data("init", true);
                        container.data("enable", enable);
                        container.append('<ol class="nts-list-box"></ol>');
                        var selectListBoxContainer = container.find('.nts-list-box');
                        var changeEvent = new CustomEvent("selectionChange", {
                            detail: {},
                        });
                        container.data("selectionChange", changeEvent);
                        if (isMultiSelect) {
                            bindMultible(selectListBoxContainer, changeEvent);
                        }
                        else {
                            bindSingleSelectListBox(selectListBoxContainer, changeEvent);
                        }
                        container.on('selectionChange', (function (e) {
                            var changingEvent = new CustomEvent("selectionChanging", {
                                detail: itemsSelected,
                                bubbles: true,
                                cancelable: true,
                            });
                            var isMulti = container.data("multiple");
                            var itemsSelected = selectListBoxContainer.data('value');
                            document.getElementById(container.attr('id')).dispatchEvent(changingEvent);
                            if (changingEvent.returnValue !== undefined && !changingEvent.returnValue) {
                                console.log(selectedValue);
                                selectListBoxContainer.data('value', data.value());
                                if (isMulti) {
                                    selectMultiRow(selectListBoxContainer, data.value());
                                }
                                else {
                                    selectOneRow(selectListBoxContainer, data.value());
                                }
                            }
                            else {
                                data.value(itemsSelected);
                                container.data("selected", !isMulti ? itemsSelected : itemsSelected.slice());
                            }
                        }));
                        container.data("multiple", isMultiSelect);
                    };
                    ListBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var options = ko.unwrap(data.options);
                        var optionValue = ko.unwrap(data.primaryKey === undefined ? data.optionsValue : data.primaryKey);
                        var optionText = ko.unwrap(data.primaryText === undefined ? data.optionsText : data.primaryText);
                        var selectedValue = ko.unwrap(data.value);
                        var isMultiSelect = ko.unwrap(data.multiple);
                        var enable = ko.unwrap(data.enable);
                        var columns = data.columns;
                        var rows = data.rows;
                        var container = $(element);
                        var selectListBoxContainer = container.find('.nts-list-box');
                        var maxWidthCharacter = 15;
                        var required = ko.unwrap(data.required) || false;
                        container.data('required', required);
                        var getOptionValue = function (item) {
                            if (optionValue === undefined) {
                                return item;
                            }
                            else {
                                return item[optionValue];
                            }
                        };
                        var originalOptions = container.data("options");
                        var init = container.data("init");
                        var originalSelected = container.data("selected");
                        var oldMultiOption = container.data("multiple");
                        if (oldMultiOption !== isMultiSelect && !init) {
                            var changeEvent = new CustomEvent("selectionChange", {
                                detail: {},
                            });
                            if (oldMultiOption) {
                                unbindMultible(selectListBoxContainer);
                                bindSingleSelectListBox(selectListBoxContainer, changeEvent);
                                container.find("li").removeClass("ui-selected");
                                if (selectedValue.length > 0) {
                                    selectOneRow(selectListBoxContainer, selectedValue[0]);
                                    data.value(selectedValue[0]);
                                }
                            }
                            else {
                                unbindSingleSelectListBox(selectListBoxContainer);
                                bindMultible(selectListBoxContainer, changeEvent);
                                if (!uk.text.isNullOrEmpty(selectedValue)) {
                                    var array = [];
                                    array.push(selectedValue);
                                    data.value(array);
                                }
                            }
                            container.data("multiple", isMultiSelect);
                        }
                        if (!_.isEqual(originalOptions, options) || init) {
                            if (!init) {
                                selectListBoxContainer.empty();
                            }
                            options.forEach(function (item, idx) {
                                var isSelected = false;
                                if (isMultiSelect) {
                                    isSelected = selectedValue.indexOf(getOptionValue(item)) !== -1;
                                }
                                else {
                                    isSelected = selectedValue === getOptionValue(item);
                                }
                                var target = _.find($('li', container), function (opt) {
                                    var optValue = $(opt).data('value');
                                    return optValue == getOptionValue(item);
                                });
                                if (init || target === undefined) {
                                    var selectedClass = isSelected ? 'ui-selected' : '';
                                    var itemTemplate = '';
                                    if (columns && columns.length > 0) {
                                        columns.forEach(function (col, cIdx) {
                                            itemTemplate += '<div class="nts-column nts-list-box-column-' + cIdx + '">' + item[col.key !== undefined ? col.key : col.prop] + '</div>';
                                        });
                                    }
                                    else {
                                        itemTemplate = '<div class="nts-column nts-list-box-column-0">' + item[optionText] + '</div>';
                                    }
                                    $('<li/>').addClass(selectedClass).attr("data-idx", idx)
                                        .html(itemTemplate).data('value', getOptionValue(item))
                                        .appendTo(selectListBoxContainer);
                                }
                                else {
                                    var targetOption = $(target);
                                    if (isSelected) {
                                        targetOption.addClass('ui-selected');
                                    }
                                    else {
                                        targetOption.removeClass('ui-selected');
                                    }
                                }
                            });
                            var padding = 10;
                            var rowHeight = 28;
                            if (columns && columns.length > 0) {
                                var totalWidth = 0;
                                columns.forEach(function (item, cIdx) {
                                    container.find('.nts-list-box-column-' + cIdx).width(item.length * maxWidthCharacter + 20);
                                    totalWidth += item.length * maxWidthCharacter + 20;
                                });
                                totalWidth += padding * (columns.length + 1);
                                container.find('.nts-list-box > li').css({ 'width': totalWidth });
                                container.find('.nts-list-box').css({ 'width': totalWidth });
                                container.css({ 'width': totalWidth });
                            }
                            if (rows && rows > 0) {
                                container.css('height', rows * rowHeight);
                                container.find('.nts-list-box').css('height', rows * rowHeight);
                            }
                        }
                        container.data("options", options.slice());
                        container.data("init", false);
                        var haveData = isMultiSelect ? !uk.text.isNullOrEmpty(selectedValue) && selectedValue.length > 0
                            : !uk.text.isNullOrEmpty(selectedValue);
                        if (haveData && (!_.isEqual(originalSelected, selectedValue) || init)) {
                            selectListBoxContainer.data('value', selectedValue);
                            if (isMultiSelect) {
                                selectMultiRow(selectListBoxContainer, selectedValue);
                            }
                            else {
                                selectOneRow(selectListBoxContainer, selectedValue);
                            }
                            container.trigger('selectionChange');
                        }
                        else if (!haveData) {
                            container.ntsListBox("deselectAll");
                        }
                        if (isMultiSelect) {
                            if (!enable) {
                                selectListBoxContainer.selectable("disable");
                                ;
                                container.addClass('disabled');
                            }
                            else {
                                selectListBoxContainer.selectable("enable");
                                container.removeClass('disabled');
                            }
                        }
                        else {
                            if (!enable) {
                                container.off("click", "li");
                                container.addClass('disabled');
                            }
                            else {
                                if (container.hasClass("disabled")) {
                                    container.on("click", "li", { event: container.data("selectionChange") }, selectOnListBox);
                                    container.removeClass('disabled');
                                }
                            }
                        }
                        container.data("enable", enable);
                    };
                    return ListBoxBindingHandler;
                }());
                ko.bindingHandlers['ntsListBox'] = new ListBoxBindingHandler();
            })(koExtentions = ui_4.koExtentions || (ui_4.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsRadioBoxGroupBindingHandler = (function () {
                    function NtsRadioBoxGroupBindingHandler() {
                    }
                    NtsRadioBoxGroupBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        $(element).addClass("ntsControl");
                    };
                    NtsRadioBoxGroupBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var options = ko.unwrap(data.options);
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = data.value;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        var getOptionValue = function (item) {
                            return (optionValue === undefined) ? item : item[optionValue];
                        };
                        if (!_.isEqual(container.data("options"), options)) {
                            var radioName = uk.util.randomId();
                            container.empty();
                            _.forEach(options, function (option) {
                                var radioBoxLabel = $("<label class='ntsRadioBox'></label>");
                                var radioBox = $('<input type="radio">').attr("name", radioName).data("value", getOptionValue(option)).on("change", function () {
                                    var self = this;
                                    if ($(self).is(":checked"))
                                        selectedValue($(self).data("value"));
                                }).appendTo(radioBoxLabel);
                                var box = $("<span class='box'></span>").appendTo(radioBoxLabel);
                                if (option[optionText] && option[optionText].length > 0)
                                    var label = $("<span class='label'></span>").text(option[optionText]).appendTo(radioBoxLabel);
                                radioBoxLabel.appendTo(container);
                            });
                            container.data("options", options.slice());
                        }
                        var checkedRadio = _.find(container.find("input[type='radio']"), function (item) {
                            return _.isEqual($(item).data("value"), selectedValue());
                        });
                        if (checkedRadio !== undefined)
                            $(checkedRadio).prop("checked", true);
                        (enable === true) ? container.find("input[type='radio']").removeAttr("disabled") : container.find("input[type='radio']").attr("disabled", "disabled");
                    };
                    return NtsRadioBoxGroupBindingHandler;
                }());
                ko.bindingHandlers['ntsRadioBoxGroup'] = new NtsRadioBoxGroupBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var filteredArray = function (array, searchTerm, fields, childField) {
                    if (!array) {
                        return [];
                    }
                    if (!(searchTerm instanceof String)) {
                        searchTerm = "" + searchTerm;
                    }
                    var flatArr = nts.uk.util.flatArray(array, childField);
                    var filter = searchTerm.toLowerCase();
                    if (!filter) {
                        return flatArr;
                    }
                    var filtered = ko.utils.arrayFilter(flatArr, function (item) {
                        var i = fields.length;
                        while (i--) {
                            var prop = fields[i];
                            var strProp = ("" + item[prop]).toLocaleLowerCase();
                            if (strProp.indexOf(filter) !== -1) {
                                return true;
                            }
                            ;
                        }
                        return false;
                    });
                    return filtered;
                };
                var getNextItem = function (selected, arr, searchResult, selectedKey, isArray) {
                    var current = null;
                    if (isArray) {
                        if (selected.length > 0)
                            current = selected[0];
                    }
                    else if (selected !== undefined && selected !== '' && selected !== null) {
                        current = selected;
                    }
                    if (searchResult.length > 0) {
                        if (current) {
                            var currentIndex = nts.uk.util.findIndex(arr, current, selectedKey);
                            var nextIndex = 0;
                            var found = false;
                            for (var i = 0; i < searchResult.length; i++) {
                                var item = searchResult[i];
                                var itemIndex = nts.uk.util.findIndex(arr, item[selectedKey], selectedKey);
                                if (!found && itemIndex >= currentIndex + 1) {
                                    found = true;
                                    nextIndex = i;
                                }
                                if ((i < searchResult.length - 1) && item[selectedKey] == current)
                                    return searchResult[i + 1][selectedKey];
                            }
                            return searchResult[nextIndex][selectedKey];
                        }
                        return searchResult[0][selectedKey];
                    }
                    return undefined;
                };
                var NtsSearchBoxBindingHandler = (function () {
                    function NtsSearchBoxBindingHandler() {
                    }
                    NtsSearchBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var searchBox = $(element);
                        var data = valueAccessor();
                        var fields = ko.unwrap(data.fields);
                        var searchText = (data.searchText !== undefined) ? ko.unwrap(data.searchText) : "検索";
                        var placeHolder = (data.placeHolder !== undefined) ? ko.unwrap(data.placeHolder) : "コード・名称で検索・・・";
                        var selected = data.selected;
                        var selectedKey = null;
                        if (data.selectedKey) {
                            selectedKey = ko.unwrap(data.selectedKey);
                        }
                        var arr = ko.unwrap(data.items);
                        var component = $("#" + ko.unwrap(data.comId));
                        var childField = null;
                        if (data.childField) {
                            childField = ko.unwrap(data.childField);
                        }
                        searchBox.data("searchResult", nts.uk.util.flatArray(arr, childField));
                        var $container = $(element);
                        $container.append("<input class='ntsSearchBox' type='text' />");
                        $container.append("<button class='search-btn caret-bottom'>" + searchText + "</button>");
                        var $input = $container.find("input.ntsSearchBox");
                        $input.attr("placeholder", placeHolder);
                        var $button = $container.find("button.search-btn");
                        $input.outerWidth($container.outerWidth(true) - $button.outerWidth(true));
                        var nextSearch = function () {
                            var filtArr = searchBox.data("searchResult");
                            var compareKey = fields[0];
                            var isArray = $.isArray(selected());
                            var selectedItem = getNextItem(selected(), nts.uk.util.flatArray(arr, childField), filtArr, selectedKey, isArray);
                            if (data.mode) {
                                if (data.mode == 'igGrid') {
                                    var selectArr = [];
                                    selectArr.push("" + selectedItem);
                                    component.ntsGridList("setSelected", selectArr);
                                    data.selected(selectArr);
                                    component.trigger("selectChange");
                                }
                                else if (data.mode == 'igTree') {
                                    var liItem = $("li[data-value='" + selectedItem + "']");
                                    component.igTree("expandToNode", liItem);
                                    component.igTree("select", liItem);
                                }
                            }
                            else {
                                if (!isArray)
                                    selected(selectedItem);
                                else {
                                    selected([]);
                                    selected.push(selectedItem);
                                }
                                component.trigger("selectChange");
                            }
                        };
                        $input.keyup(function () {
                            $input.change();
                        }).keydown(function (event) {
                            if (event.which == 13) {
                                event.preventDefault();
                                nextSearch();
                            }
                        });
                        $input.change(function (event) {
                            var searchTerm = $input.val();
                            searchBox.data("searchResult", filteredArray(ko.unwrap(data.items), searchTerm, fields, childField));
                        });
                        $button.click(nextSearch);
                    };
                    NtsSearchBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var searchBox = $(element);
                        var $input = searchBox.find("input.ntsSearchBox");
                        var searchTerm = $input.val();
                        var data = valueAccessor();
                        var arr = ko.unwrap(data.items);
                        var fields = ko.unwrap(data.fields);
                        var childField = null;
                        if (data.childField) {
                            childField = ko.unwrap(data.childField);
                        }
                        searchBox.data("searchResult", filteredArray(arr, searchTerm, fields, childField));
                    };
                    return NtsSearchBoxBindingHandler;
                }());
                ko.bindingHandlers['ntsSearchBox'] = new NtsSearchBoxBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_5) {
            var koExtentions;
            (function (koExtentions) {
                var NtsSwapListBindingHandler = (function () {
                    function NtsSwapListBindingHandler() {
                    }
                    NtsSwapListBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var HEADER_HEIGHT = 27;
                        var CHECKBOX_WIDTH = 70;
                        var SEARCH_AREA_HEIGHT = 45;
                        var BUTTON_SEARCH_WIDTH = 60;
                        var INPUT_SEARCH_PADDING = 65;
                        var $swap = $(element);
                        var elementId = $swap.attr('id');
                        if (nts.uk.util.isNullOrUndefined(elementId)) {
                            throw new Error('the element NtsSwapList must have id attribute.');
                        }
                        var data = valueAccessor();
                        var originalSource = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
                        var totalWidth = ko.unwrap(data.width);
                        var height = ko.unwrap(data.height);
                        var showSearchBox = ko.unwrap(data.showSearchBox);
                        var primaryKey = data.primaryKey !== undefined ? data.primaryKey : data.optionsValue;
                        var columns = data.columns;
                        $swap.wrap("<div class= 'ntsComponent ntsSwapList' id='" + elementId + "_container'/>");
                        if (totalWidth !== undefined) {
                            $swap.parent().width(totalWidth);
                        }
                        $swap.parent().height(height);
                        $swap.addClass("ntsSwapList-container");
                        var gridWidth = _.sumBy(columns(), function (c) {
                            return c.width;
                        });
                        var iggridColumns = _.map(columns(), function (c) {
                            c["key"] = c.key === undefined ? c.prop : c.key;
                            c["dataType"] = 'string';
                            return c;
                        });
                        var gridHeight = (height - 20);
                        var grid1Id = "#" + elementId + "-grid1";
                        var grid2Id = "#" + elementId + "-grid2";
                        if (!uk.util.isNullOrUndefined(showSearchBox) && (showSearchBox.showLeft || showSearchBox.showEright)) {
                            var search = function ($swap, gridId, primaryKey) {
                                var value = $swap.find(".ntsSearchInput").val();
                                var source = $(gridId).igGrid("option", "dataSource").slice();
                                var selected = $(gridId).ntsGridList("getSelected");
                                if (selected.length > 0) {
                                    var gotoEnd = source.splice(0, selected[0].index + 1);
                                    source = source.concat(gotoEnd);
                                }
                                var searchedValues = _.find(source, function (val) {
                                    return _.find(iggridColumns, function (x) {
                                        return x !== undefined && x !== null && val[x["key"]].toString().indexOf(value) >= 0;
                                    }) !== undefined;
                                });
                                $(gridId).ntsGridList('setSelected', searchedValues !== undefined ? [searchedValues[primaryKey]] : []);
                                if (searchedValues !== undefined && (selected.length === 0 ||
                                    selected[0].id !== searchedValues[primaryKey])) {
                                    var current = $(gridId).igGrid("selectedRows");
                                    if (current.length > 0 && $(gridId).igGrid("hasVerticalScrollbar")) {
                                        $(gridId).igGrid("virtualScrollTo", current[0].index === source.length - 1
                                            ? current[0].index : current[0].index + 1);
                                    }
                                }
                            };
                            var initSearchArea = function ($SearchArea, targetId) {
                                $SearchArea.append("<div class='ntsSearchTextContainer'/>")
                                    .append("<div class='ntsSearchButtonContainer'/>");
                                $SearchArea.find(".ntsSearchTextContainer")
                                    .append("<input id = " + searchAreaId + "-input" + " class = 'ntsSearchInput ntsSearchBox'/>");
                                $SearchArea.find(".ntsSearchButtonContainer")
                                    .append("<button id = " + searchAreaId + "-btn" + " class='ntsSearchButton search-btn caret-bottom'/>");
                                $SearchArea.find(".ntsSearchInput").attr("placeholder", "コード・名称で検索・・・").keyup(function (event, ui) {
                                    if (event.which === 13) {
                                        search($SearchArea, targetId, primaryKey);
                                    }
                                });
                                $SearchArea.find(".ntsSearchButton").text("検索").click(function (event, ui) {
                                    search($SearchArea, targetId, primaryKey);
                                });
                            };
                            var searchAreaId = elementId + "-search-area";
                            $swap.append("<div class = 'ntsSearchArea' id = " + searchAreaId + "/>");
                            var $searchArea = $swap.find(".ntsSearchArea");
                            $searchArea.append("<div class='ntsSwapSearchLeft'/>")
                                .append("<div class='ntsSwapSearchRight'/>");
                            $searchArea.css({ position: "relative" });
                            var searchAreaWidth = gridWidth + CHECKBOX_WIDTH;
                            if (showSearchBox.showLeft) {
                                var $searchLeftContainer = $swap.find(".ntsSwapSearchLeft");
                                $searchLeftContainer.width(searchAreaWidth).css({ position: "absolute", left: 0 });
                                initSearchArea($searchLeftContainer, grid1Id);
                            }
                            if (showSearchBox.showRight) {
                                var $searchRightContainer = $swap.find(".ntsSwapSearchRight");
                                $searchRightContainer.width(gridWidth + CHECKBOX_WIDTH).css({ position: "absolute", right: 0 });
                                initSearchArea($searchRightContainer, grid2Id);
                            }
                            $searchArea.find(".ntsSearchBox").width(searchAreaWidth - BUTTON_SEARCH_WIDTH - INPUT_SEARCH_PADDING);
                            $searchArea.height(SEARCH_AREA_HEIGHT);
                            gridHeight -= SEARCH_AREA_HEIGHT;
                        }
                        $swap.append("<div class= 'ntsSwapArea ntsGridArea'/>");
                        $swap.find(".ntsGridArea").append("<div class = 'ntsSwapGridArea ntsSwapComponent' id = " + elementId + "-gridArea1" + "/>")
                            .append("<div class = 'ntsMoveDataArea ntsSwapComponent' id = " + elementId + "-move-data" + "/>")
                            .append("<div class = 'ntsSwapGridArea ntsSwapComponent' id = " + elementId + "-gridArea2" + "/>");
                        $swap.find("#" + elementId + "-gridArea1").append("<table class = 'ntsSwapGrid' id = " + elementId + "-grid1" + "/>");
                        $swap.find("#" + elementId + "-gridArea2").append("<table class = 'ntsSwapGrid' id = " + elementId + "-grid2" + "/>");
                        var $grid1 = $swap.find(grid1Id);
                        var $grid2 = $swap.find(grid2Id);
                        var features = [{ name: 'Selection', multipleSelection: true },
                            { name: 'Sorting', type: 'local' },
                            { name: 'RowSelectors', enableCheckBoxes: true, enableRowNumbering: true }];
                        $swap.find(".nstSwapGridArea").width(gridWidth + CHECKBOX_WIDTH);
                        $grid1.igGrid({
                            width: gridWidth + CHECKBOX_WIDTH,
                            height: (gridHeight) + "px",
                            primaryKey: primaryKey,
                            columns: iggridColumns,
                            virtualization: true,
                            virtualizationMode: 'continuous',
                            features: features
                        });
                        $grid1.closest('.ui-iggrid')
                            .addClass('nts-gridlist')
                            .height(gridHeight);
                        $grid1.ntsGridList('setupSelecting');
                        $grid2.igGrid({
                            width: gridWidth + CHECKBOX_WIDTH,
                            height: (gridHeight) + "px",
                            primaryKey: primaryKey,
                            columns: iggridColumns,
                            virtualization: true,
                            virtualizationMode: 'continuous',
                            features: features
                        });
                        $grid2.closest('.ui-iggrid')
                            .addClass('nts-gridlist')
                            .height(gridHeight);
                        $grid2.ntsGridList('setupSelecting');
                        var $moveArea = $swap.find("#" + elementId + "-move-data")
                            .append("<button class = 'move-button move-forward'><i class='icon icon-button-arrow-right'></i></button>")
                            .append("<button class = 'move-button move-back'><i class='icon icon-button-arrow-left'></i></button>");
                        var $moveForward = $moveArea.find(".move-forward");
                        var $moveBack = $moveArea.find(".move-back");
                        var move = function (id1, id2, key, currentSource, value, isForward) {
                            var selectedEmployees = _.sortBy($(isForward ? id1 : id2).igGrid("selectedRows"), 'id');
                            if (selectedEmployees.length > 0) {
                                $(isForward ? id1 : id2).igGridSelection("clearSelection");
                                var source = $(isForward ? id1 : id2).igGrid("option", "dataSource");
                                var employeeList = [];
                                for (var i = 0; i < selectedEmployees.length; i++) {
                                    var current = source[selectedEmployees[i].index];
                                    if (current[key].toString() === selectedEmployees[i].id.toString()) {
                                        employeeList.push(current);
                                    }
                                    else {
                                        var sameCodes = _.find(source, function (subject) {
                                            return subject[key].toString() === selectedEmployees[i].id.toString();
                                        });
                                        if (sameCodes !== undefined) {
                                            employeeList.push(sameCodes);
                                        }
                                    }
                                }
                                var length = value().length;
                                var notExisted = _.filter(employeeList, function (list) {
                                    return _.find(currentSource, function (data) {
                                        return data[key].toString() === list[key].toString();
                                    }) === undefined;
                                });
                                if (notExisted.length > 0) {
                                    $(id1).igGrid("virtualScrollTo", 0);
                                    $(id2).igGrid("virtualScrollTo", 0);
                                    var newSource = _.filter(source, function (list) {
                                        return _.find(notExisted, function (data) {
                                            return data[key].toString() === list[key].toString();
                                        }) === undefined;
                                    });
                                    var sources = currentSource.concat(notExisted);
                                    value(isForward ? sources : newSource);
                                    $(id1).igGrid("option", "dataSource", isForward ? newSource : sources);
                                    $(id1).igGrid("option", "dataBind");
                                    $(id1).igGrid("virtualScrollTo", isForward ? selectedEmployees[0].index - 1 : sources.length - selectedEmployees.length);
                                    $(id2).igGrid("virtualScrollTo", isForward ? value().length : selectedEmployees[0].index);
                                }
                            }
                        };
                        $moveForward.click(function () {
                            move(grid1Id, grid2Id, primaryKey, data.value(), data.value, true);
                        });
                        $moveBack.click(function () {
                            move(grid1Id, grid2Id, primaryKey, $(grid1Id).igGrid("option", "dataSource"), data.value, false);
                        });
                    };
                    NtsSwapListBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var $swap = $(element);
                        var data = valueAccessor();
                        var elementId = $swap.attr('id');
                        var primaryKey = data.primaryKey !== undefined ? data.primaryKey : data.optionsValue;
                        if (nts.uk.util.isNullOrUndefined(elementId)) {
                            throw new Error('the element NtsSwapList must have id attribute.');
                        }
                        var $grid1 = $swap.find("#" + elementId + "-grid1");
                        var $grid2 = $swap.find("#" + elementId + "-grid2");
                        var currentSource = $grid1.igGrid('option', 'dataSource');
                        var currentSelected = $grid2.igGrid('option', 'dataSource');
                        var sources = (data.dataSource !== undefined ? data.dataSource() : data.options());
                        var selectedSources = data.value();
                        _.remove(sources, function (item) {
                            return _.find(selectedSources, function (selected) {
                                return selected[primaryKey] === item[primaryKey];
                            }) !== undefined;
                        });
                        if (!_.isEqual(currentSource, sources)) {
                            $grid1.igGrid('option', 'dataSource', sources.slice());
                            $grid1.igGrid("dataBind");
                        }
                        if (!_.isEqual(currentSelected, selectedSources)) {
                            $grid2.igGrid('option', 'dataSource', selectedSources.slice());
                            $grid2.igGrid("dataBind");
                        }
                    };
                    return NtsSwapListBindingHandler;
                }());
                ko.bindingHandlers['ntsSwapList'] = new NtsSwapListBindingHandler();
            })(koExtentions = ui_5.koExtentions || (ui_5.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsSwitchButtonBindingHandler = (function () {
                    function NtsSwitchButtonBindingHandler() {
                    }
                    NtsSwitchButtonBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    NtsSwitchButtonBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var selectedCssClass = 'selected';
                        var options = ko.unwrap(data.options);
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = ko.unwrap(data.value);
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        $('button', container).each(function (index, btn) {
                            var $btn = $(btn);
                            var btnValue = $(btn).data('swbtn');
                            var foundFlag = _.findIndex(options, function (opt) {
                                return opt[optionValue] == btnValue;
                            }) != -1;
                            if (!foundFlag) {
                                $btn.remove();
                                return;
                            }
                        });
                        _.forEach(options, function (opt) {
                            var value = opt[optionValue];
                            var text = opt[optionText];
                            var targetBtn;
                            $('button', container).each(function (index, btn) {
                                var btnValue = $(btn).data('swbtn');
                                if (btnValue == value) {
                                    targetBtn = $(btn);
                                }
                                if (btnValue == selectedValue) {
                                    $(btn).addClass(selectedCssClass);
                                }
                                else {
                                    $(btn).removeClass(selectedCssClass);
                                }
                            });
                            if (targetBtn) {
                            }
                            else {
                                var btn = $('<button>').text(text)
                                    .addClass('nts-switch-button')
                                    .data('swbtn', value)
                                    .on('click', function () {
                                    var selectedValue = $(this).data('swbtn');
                                    data.value(selectedValue);
                                    $('button', container).removeClass(selectedCssClass);
                                    $(this).addClass(selectedCssClass);
                                });
                                if (selectedValue == value) {
                                    btn.addClass(selectedCssClass);
                                }
                                container.append(btn);
                            }
                        });
                        (enable === true) ? $('button', container).prop("disabled", false) : $('button', container).prop("disabled", true);
                    };
                    return NtsSwitchButtonBindingHandler;
                }());
                ko.bindingHandlers['ntsSwitchButton'] = new NtsSwitchButtonBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_6) {
            var koExtentions;
            (function (koExtentions) {
                var NtsTreeGridViewBindingHandler = (function () {
                    function NtsTreeGridViewBindingHandler() {
                    }
                    NtsTreeGridViewBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var options = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
                        var optionsValue = ko.unwrap(data.primaryKey !== undefined ? data.primaryKey : data.optionsValue);
                        var optionsText = ko.unwrap(data.primaryText !== undefined ? data.primaryText : data.optionsText);
                        var optionsChild = ko.unwrap(data.childDataKey !== undefined ? data.childDataKey : data.optionsChild);
                        var extColumns = ko.unwrap(data.columns !== undefined ? data.columns : data.extColumns);
                        var showCheckBox = data.showCheckBox !== undefined ? ko.unwrap(data.showCheckBox) : true;
                        var enable = data.enable !== undefined ? ko.unwrap(data.enable) : true;
                        var height = ko.unwrap(data.height !== undefined ? data.height : '100%');
                        var width = ko.unwrap(data.width !== undefined ? data.width : '100%');
                        if (extColumns !== undefined && extColumns !== null) {
                            var displayColumns = extColumns;
                        }
                        else {
                            var displayColumns = [
                                { headerText: "コード", key: optionsValue, dataType: "string", hidden: true },
                                { headerText: "コード／名称", key: optionsText, dataType: "string" }
                            ];
                        }
                        var $treegrid = $(element);
                        $(element).igTreeGrid({
                            width: width,
                            height: height,
                            dataSource: _.cloneDeep(options),
                            primaryKey: optionsValue,
                            columns: displayColumns,
                            childDataKey: optionsChild,
                            initialExpandDepth: 10,
                            features: [
                                {
                                    name: "Selection",
                                    multipleSelection: true,
                                    activation: true,
                                    rowSelectionChanged: function (evt, ui) {
                                        var selectedRows = ui.selectedRows;
                                        if (ko.unwrap(data.multiple)) {
                                            if (ko.isObservable(data.selectedValues)) {
                                                data.selectedValues(_.map(selectedRows, function (row) {
                                                    return row.id;
                                                }));
                                            }
                                        }
                                        else {
                                            if (ko.isObservable(data.value)) {
                                                data.value(selectedRows.length <= 0 ? undefined : selectedRows[0].id);
                                            }
                                        }
                                    }
                                },
                                {
                                    name: "RowSelectors",
                                    enableCheckBoxes: showCheckBox,
                                    checkBoxMode: "biState"
                                }]
                        });
                        var treeGridId = $treegrid.attr('id');
                        $(element).closest('.ui-igtreegrid').addClass('nts-treegridview');
                        $treegrid.setupSearchScroll("igTreeGrid");
                    };
                    NtsTreeGridViewBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var options = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
                        var selectedValues = ko.unwrap(data.selectedValues);
                        var singleValue = ko.unwrap(data.value);
                        var originalSource = $(element).igTreeGrid('option', 'dataSource');
                        if (!_.isEqual(originalSource, options)) {
                            $(element).igTreeGrid("option", "dataSource", _.cloneDeep(options));
                            $(element).igTreeGrid("dataBind");
                        }
                        var multiple = data.multiple != undefined ? ko.unwrap(data.multiple) : true;
                        if ($(element).igTreeGridSelection("option", "multipleSelection") !== multiple) {
                            $(element).igTreeGridSelection("option", "multipleSelection", multiple);
                        }
                        var showCheckBox = ko.unwrap(data.showCheckBox != undefined ? data.showCheckBox : true);
                        if ($(element).igTreeGridRowSelectors("option", "enableCheckBoxes") !== showCheckBox) {
                            $(element).igTreeGridRowSelectors("option", "enableCheckBoxes", showCheckBox);
                        }
                        if ((selectedValues === null || selectedValues === undefined) && (singleValue === null || singleValue === undefined)) {
                            $(element).igTreeGridSelection("clearSelection");
                        }
                        else {
                            var olds = _.map($(element).igTreeGridSelection("selectedRow"), function (row) {
                                return row.id;
                            });
                            if (multiple) {
                                if (_.isEqual(selectedValues.sort(), olds.sort())) {
                                    return;
                                }
                                $(element).igTreeGridSelection("clearSelection");
                                selectedValues.forEach(function (val) {
                                    $(element).igTreeGridSelection("selectRowById", val);
                                });
                            }
                            else {
                                if (olds.length > 1 && olds[0] === singleValue) {
                                    return;
                                }
                                $(element).igTreeGridSelection("clearSelection");
                                $(element).igTreeGridSelection("selectRowById", singleValue);
                            }
                        }
                    };
                    return NtsTreeGridViewBindingHandler;
                }());
                ko.bindingHandlers['ntsTreeGridView'] = new NtsTreeGridViewBindingHandler();
            })(koExtentions = ui_6.koExtentions || (ui_6.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_7) {
            var koExtentions;
            (function (koExtentions) {
                var NtsUpDownBindingHandler = (function () {
                    function NtsUpDownBindingHandler() {
                    }
                    NtsUpDownBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var $upDown = $(element);
                        if ($upDown.prop("tagName").toLowerCase() !== "div") {
                            throw new Error('The element must be a div');
                        }
                        var data = valueAccessor();
                        var elementId = $upDown.attr('id');
                        var comId = ko.unwrap(data.comId);
                        var childField = ko.unwrap(data.childDataKey);
                        var primaryKey = ko.unwrap(data.primaryKey);
                        var height = ko.unwrap(data.height);
                        var targetType = ko.unwrap(data.type);
                        var swapTarget = ko.unwrap(data.swapTarget);
                        if (nts.uk.util.isNullOrUndefined(elementId)) {
                            throw new Error('The element NtsSwapList must have id attribute.');
                        }
                        if (nts.uk.util.isNullOrUndefined(comId)) {
                            throw new Error('The target element of NtsUpDown is required.');
                        }
                        $upDown.addClass("ntsComponent ntsUpDown").append("<div class='upDown-container'/>");
                        $upDown.find(".upDown-container")
                            .append("<button class = 'ntsUpButton ntsButton ntsUpDownButton auto-height' id= '" + elementId + "-up'/>")
                            .append("<button class = 'ntsDownButton ntsButton ntsUpDownButton auto-height' id= '" + elementId + "-down'/>");
                        var $target = $(comId);
                        if (height !== undefined) {
                            $upDown.height(height);
                            $upDown.find(".upDown-container").height(height);
                        }
                        else {
                            var targetHeight = $(comId + "_container").height();
                            if (targetHeight === undefined) {
                                var h = _.find($(comId).attr("data-bind").split(","), function (attr) {
                                    return attr.indexOf("height") >= 0;
                                });
                                if (h !== undefined) {
                                    targetHeight = parseFloat(h.split(":")[1]);
                                }
                            }
                            $upDown.height(targetHeight);
                            $upDown.find(".upDown-container").height(targetHeight);
                        }
                        var $up = $upDown.find(".ntsUpButton");
                        var $down = $upDown.find(".ntsDownButton");
                        $up.append("<i class='icon icon-button-arrow-top'/>");
                        $down.append("<i class='icon icon-button-arrow-bottom'/>");
                        var move = function (upDown, $targetElement) {
                            var multySelectedRaw = $targetElement.igGrid("selectedRows");
                            var singleSelectedRaw = $targetElement.igGrid("selectedRow");
                            var selected = [];
                            if (multySelectedRaw !== null) {
                                selected = _.filter(multySelectedRaw, function (item) {
                                    return item["index"] >= 0;
                                });
                            }
                            else if (singleSelectedRaw !== null) {
                                selected.push(singleSelectedRaw);
                            }
                            else {
                                return;
                            }
                            var source = _.cloneDeep($targetElement.igGrid("option", "dataSource"));
                            var group = 1;
                            var grouped = { "group1": [] };
                            if (selected.length > 0) {
                                var size = selected.length;
                                selected = _.sortBy(selected, "index");
                                _.forEach(selected, function (item, idx) {
                                    grouped["group" + group].push(item);
                                    if (idx !== size - 1 && item["index"] + 1 !== selected[idx + 1]["index"]) {
                                        group++;
                                        grouped["group" + group] = [];
                                    }
                                });
                                var moved = false;
                                _.forEach(_.valuesIn(grouped), function (items) {
                                    var firstIndex = items[0].index;
                                    var lastIndex = items[items.length - 1].index;
                                    if (upDown < 0) {
                                        var canMove = firstIndex > 0;
                                    }
                                    else {
                                        var canMove = lastIndex < source.length - 1;
                                    }
                                    if (canMove) {
                                        var removed = source.splice(firstIndex, items.length);
                                        _.forEach(removed, function (item, idx) {
                                            source.splice(firstIndex + upDown + idx, 0, item);
                                        });
                                        moved = true;
                                    }
                                });
                                if (moved) {
                                    $targetElement.igGrid("virtualScrollTo", 0);
                                    data.targetSource(source);
                                    var index = upDown + grouped["group1"][0].index;
                                    $targetElement.igGrid("virtualScrollTo", index);
                                }
                            }
                        };
                        var moveTree = function (upDown, $targetElement) {
                            var multiSelectedRaw = $targetElement.igTreeGrid("selectedRows");
                            var singleSelectedRaw = $targetElement.igTreeGrid("selectedRow");
                            var selected;
                            if (multiSelectedRaw !== null) {
                                if (multiSelectedRaw.length !== 1) {
                                    return;
                                }
                                selected = multiSelectedRaw[0];
                            }
                            else if (singleSelectedRaw !== null) {
                                selected.push(singleSelectedRaw);
                            }
                            else {
                                return;
                            }
                            if (selected["index"] < 0) {
                                return;
                            }
                            var source = _.cloneDeep($targetElement.igTreeGrid("option", "dataSource"));
                            var result = findChild(upDown, selected["id"], source, false, false);
                            var moved = result.moved;
                            var changed = result.changed;
                            source = result.source;
                            if (moved && changed) {
                                data.targetSource(source);
                                var index = $targetElement.igTreeGrid("selectedRows")[0].index;
                                if (index !== selected["index"]) {
                                    var scrollTo = _.sumBy(_.filter($target.igTreeGrid("allRows"), function (row) {
                                        return $(row).attr("data-row-idx") < index;
                                    }), function (row) {
                                        return $(row).height();
                                    });
                                    $targetElement.igTreeGrid("scrollContainer").scrollTop(scrollTo);
                                }
                            }
                        };
                        var findChild = function (upDown, key, children, moved, changed) {
                            var index = -1;
                            if (children !== undefined && children !== null && children.length > 0 && !moved && !changed) {
                                _.forEach(children, function (child, idx) {
                                    if (!moved) {
                                        if (child[primaryKey] === key) {
                                            index = idx;
                                            return false;
                                        }
                                        else {
                                            var result = findChild(upDown, key, child[childField], moved, changed);
                                            child[childField] = result.source;
                                            moved = result.moved;
                                            changed = result.changed;
                                        }
                                    }
                                    else {
                                        return false;
                                    }
                                });
                                if (index >= 0) {
                                    if (upDown < 0) {
                                        var canMove = index > 0;
                                    }
                                    else {
                                        var canMove = index < children.length - 1;
                                    }
                                    if (canMove) {
                                        var removed = children.splice(index, 1);
                                        children.splice(index + upDown, 0, removed[0]);
                                        changed = true;
                                    }
                                    moved = true;
                                }
                                return {
                                    source: children,
                                    moved: moved,
                                    changed: changed
                                };
                            }
                            return {
                                source: children,
                                moved: moved,
                                changed: changed
                            };
                        };
                        $up.click(function (event, ui) {
                            if (targetType === "tree") {
                                moveTree(-1, $target);
                            }
                            else if (targetType === "grid") {
                                move(-1, $target);
                            }
                            else if (targetType === "swap") {
                                var swapTargetGrid = swapTarget.toLocaleLowerCase() === "left" ? "-grid1" : "-grid2";
                                move(-1, $(comId + swapTargetGrid));
                            }
                        });
                        $down.click(function (event, ui) {
                            if (targetType === "tree") {
                                moveTree(1, $target);
                            }
                            else if (targetType === "grid") {
                                move(1, $target);
                            }
                            else if (targetType === "swap") {
                                var swapTargetGrid = swapTarget.toLocaleLowerCase() === "left" ? "-grid1" : "-grid2";
                                move(1, $(comId + swapTargetGrid));
                            }
                        });
                    };
                    NtsUpDownBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var $upDown = $(element);
                        var elementId = $upDown.attr('id');
                        if (nts.uk.util.isNullOrUndefined(elementId)) {
                            throw new Error('the element NtsSwapList must have id attribute.');
                        }
                        var data = valueAccessor();
                    };
                    return NtsUpDownBindingHandler;
                }());
                ko.bindingHandlers['ntsUpDown'] = new NtsUpDownBindingHandler();
            })(koExtentions = ui_7.koExtentions || (ui_7.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var WizardBindingHandler = (function () {
                    function WizardBindingHandler() {
                    }
                    WizardBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var options = ko.unwrap(data.steps);
                        var theme = ko.unwrap(data.theme);
                        var cssClass = "nts-wizard " + "theme-" + theme;
                        var container = $(element);
                        for (var i = 0; i < options.length; i++) {
                            var contentClass = ko.unwrap(options[i].content);
                            var htmlStep = container.children('.steps').children(contentClass).html();
                            var htmlContent = container.children('.contents').children(contentClass).html();
                            container.append('<h1 class="' + contentClass + '">' + htmlStep + '</h1>');
                            container.append('<div>' + htmlContent + '</div>');
                        }
                        var icon = container.find('.header .image').data('icon');
                        var header = container.children('.header');
                        container.children('.header').remove();
                        container.children('.steps').remove();
                        container.children('.contents').remove();
                        container.steps({
                            headerTag: "h1",
                            bodyTag: "div",
                            transitionEffect: "slideLeft",
                            stepsOrientation: "vertical",
                            titleTemplate: '<div>#title#</div>',
                            enablePagination: false,
                            enableFinishButton: false,
                            autoFocus: false,
                            onStepChanged: function () {
                                container.children('.steps').children('ul').children('li').removeClass('step-current');
                                container.children('.steps').children('ul').children('li').removeClass('step-prev');
                                container.children('.steps').children('ul').children('li').removeClass('step-next');
                                container.children('.steps').children('ul').children('.done').addClass('disabled');
                                container.children('.steps').children('ul').children('.current').addClass('step-current');
                                container.children('.steps').children('ul').children('.done').addClass('step-prev');
                                container.children('.steps').children('ul').children('.step-current').nextAll('li').not('.done').addClass('step-next');
                                return true;
                            }
                        }).data("length", options.length);
                        container.addClass(cssClass);
                        container.children('.steps').children('ul').children('li').children('a').before('<div class="nts-steps"></div>');
                        container.children('.steps').children('ul').children('li').children('a').addClass('nts-step-contents');
                        container.children('.steps').children('ul').children('.last').addClass('end');
                        container.children('.steps').children('ul').children('li').not('.begin').not('.end').children('.nts-steps').addClass('nts-steps-middle');
                        container.find('.nts-steps-middle').append('<div class="nts-vertical-line"></div><div class="nts-bridge"><div class="nts-point"></div><div class="nts-horizontal-line"></div></div>');
                        container.children('.steps').children('ul').children('li').removeClass('step-current');
                        container.children('.steps').children('ul').children('li').removeClass('step-prev');
                        container.children('.steps').children('ul').children('li').removeClass('step-next');
                        container.children('.steps').children('ul').children('.current').addClass('step-current');
                        container.children('.steps').children('ul').children('.done').addClass('step-prev');
                        container.children('.steps').children('ul').children('.step-current').nextAll('li').not('.done').addClass('step-next');
                        container.find('.actions').hide();
                        container.children('.steps').prepend(header);
                        container.find('.header .image').attr('style', 'background-image: url("' + icon + '")');
                    };
                    WizardBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    return WizardBindingHandler;
                }());
                ko.bindingHandlers['ntsWizard'] = new WizardBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_8) {
            var koExtentions;
            (function (koExtentions) {
                var TabPanelBindingHandler = (function () {
                    function TabPanelBindingHandler() {
                    }
                    TabPanelBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var tabs = ko.unwrap(data.dataSource);
                        var direction = ko.unwrap(data.direction || "horizontal");
                        var container = $(element);
                        container.prepend('<ul></ul>');
                        var titleContainer = container.children('ul');
                        for (var i = 0; i < tabs.length; i++) {
                            var id = tabs[i].id;
                            var title = tabs[i].title;
                            titleContainer.append('<li><a href="#' + id + '">' + title + '</a></li>');
                            var content = tabs[i].content;
                            container.children(content).wrap('<div id="' + id + '"></div>');
                        }
                        container.tabs({
                            activate: function (evt, ui) {
                                data.active(ui.newPanel[0].id);
                                container.children('ul').children('.ui-tabs-active').addClass('active');
                                container.children('ul').children('li').not('.ui-tabs-active').removeClass('active');
                                container.children('ul').children('.ui-state-disabled').addClass('disabled');
                                container.children('ul').children('li').not('.ui-state-disabled').removeClass('disabled');
                            }
                        }).addClass(direction);
                    };
                    TabPanelBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var tabs = ko.unwrap(data.dataSource);
                        var container = $(element);
                        var activeTab = tabs.filter(function (tab) { return tab.id == data.active(); })[0];
                        var indexActive = tabs.indexOf(activeTab);
                        container.tabs("option", "active", indexActive);
                        tabs.forEach(function (tab) {
                            if (tab.enable()) {
                                container.tabs("enable", '#' + tab.id);
                                container.children('#' + tab.id).children('div').show();
                                container.children('ul').children('li[aria-controls="' + tab.id + '"]').removeClass('disabled');
                            }
                            else {
                                container.tabs("disable", '#' + tab.id);
                                container.children('#' + tab.id).children('div').hide();
                                container.children('ul').children('li[aria-controls="' + tab.id + '"]').addClass('disabled');
                            }
                            if (!tab.visible()) {
                                container.children('ul').children('li[aria-controls="' + tab.id + '"]').hide();
                            }
                            else {
                                container.children('ul').children('li[aria-controls="' + tab.id + '"]').show();
                            }
                        });
                    };
                    return TabPanelBindingHandler;
                }());
                ko.bindingHandlers['ntsTabPanel'] = new TabPanelBindingHandler();
            })(koExtentions = ui_8.koExtentions || (ui_8.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var file;
            (function (file_1) {
                var FileDownload = (function () {
                    function FileDownload(servicePath, data) {
                        var self = this;
                        self.servicePath = servicePath;
                        self.data = data;
                        self.isFinishTask = ko.observable(false);
                        self.isFinishTask.subscribe(function (value) {
                            if (value) {
                                clearInterval(self.interval);
                                self.isFinishTask(false);
                                self.download();
                            }
                        });
                    }
                    FileDownload.prototype.isTaskFinished = function (file) {
                        var options = {
                            dataType: 'text',
                            contentType: 'text/plain'
                        };
                        uk.request.ajax("file/file/isfinished", file.taskId).done(function (res) {
                            file.isFinishTask(res);
                        }).fail(function (error) {
                            file.reject(error);
                        });
                    };
                    FileDownload.prototype.print = function () {
                        var self = this;
                        self.deferred = $.Deferred();
                        var options = {
                            dataType: 'text',
                            contentType: 'application/json'
                        };
                        uk.request.ajax(self.servicePath, self.data, options).done(function (res) {
                            self.taskId = res;
                            self.interval = setInterval(self.isTaskFinished, 1000, self);
                        }).fail(function (error) {
                            self.deferred.reject(error);
                        });
                        return self.deferred.promise();
                    };
                    FileDownload.prototype.download = function () {
                        var self = this;
                        window.location.href = ("http://localhost:8080/nts.uk.pr.web/webapi/file/file/dl/" + self.taskId);
                        if (self.deferred) {
                            self.deferred.resolve();
                        }
                    };
                    return FileDownload;
                }());
                file_1.FileDownload = FileDownload;
            })(file = ui.file || (ui.file = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsHelpButtonBindingHandler = (function () {
                    function NtsHelpButtonBindingHandler() {
                    }
                    NtsHelpButtonBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var image = ko.unwrap(data.image);
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var position = ko.unwrap(data.position);
                        var myPositions = position.replace(/[^a-zA-Z ]/gmi, "").split(" ");
                        var atPositions = position.split(" ");
                        var operator = 1;
                        var marginDirection = "";
                        var caretDirection = "";
                        var caretPosition = "";
                        if (myPositions[0].search(/(top|left)/i) !== -1) {
                            operator = -1;
                        }
                        if (myPositions[0].search(/(left|right)/i) === -1) {
                            atPositions[0] = atPositions.splice(1, 1, atPositions[0])[0];
                            myPositions[0] = myPositions.splice(1, 1, myPositions[0])[0];
                            caretDirection = myPositions[1] = uk.text.reverseDirection(myPositions[1]);
                            caretPosition = "left";
                            marginDirection = "margin-top";
                        }
                        else {
                            caretDirection = myPositions[0] = uk.text.reverseDirection(myPositions[0]);
                            caretPosition = "top";
                            marginDirection = "margin-left";
                        }
                        $(element).on("click", function () {
                            if ($popup.is(":visible")) {
                                $popup.hide();
                            }
                            else {
                                var CARET_WIDTH = parseFloat($caret.css("font-size")) * 2;
                                $popup.show()
                                    .css(marginDirection, 0)
                                    .position({
                                    my: myPositions[0] + " " + myPositions[1],
                                    at: atPositions[0] + " " + atPositions[1],
                                    of: $(element),
                                    collision: "none"
                                })
                                    .css(marginDirection, CARET_WIDTH * operator);
                                $caret.css(caretPosition, parseFloat($popup.css(caretPosition)) * -1);
                            }
                        }).wrap($("<div class='ntsControl ntsHelpButton'></div>"));
                        var $container = $(element).closest(".ntsHelpButton");
                        var $caret = $("<span class='caret-helpbutton caret-" + caretDirection + "'></span>");
                        var $popup = $("<div class='nts-help-button-image'></div>")
                            .append($caret)
                            .append($("<img src='" + uk.request.resolvePath(image) + "' />"))
                            .appendTo($container).hide();
                        $("html").on("click", function (event) {
                            if (!$container.is(event.target) && $container.has(event.target).length === 0) {
                                $container.find(".nts-help-button-image").hide();
                            }
                        });
                    };
                    NtsHelpButtonBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        (enable === true) ? $(element).removeAttr("disabled") : $(element).attr("disabled", "disabled");
                    };
                    return NtsHelpButtonBindingHandler;
                }());
                ko.bindingHandlers['ntsHelpButton'] = new NtsHelpButtonBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var sharedvm;
            (function (sharedvm) {
                var KibanTimer = (function () {
                    function KibanTimer(target) {
                        var self = this;
                        self.elapsedSeconds = 0;
                        self.formatted = ko.observable(uk.time.formatSeconds(this.elapsedSeconds, 'hh:mm:ss'));
                        self.targetComponent = target;
                        self.isTimerStart = ko.observable(false);
                        self.oldDated = ko.observable(undefined);
                        document.getElementById(self.targetComponent).innerHTML = self.formatted();
                    }
                    KibanTimer.prototype.run = function (timer) {
                        var x = new Date().getTime() - timer.oldDated().getTime();
                        x = Math.floor(x / 1000);
                        timer.elapsedSeconds = x;
                        document.getElementById(timer.targetComponent).innerHTML
                            = uk.time.formatSeconds(x, 'hh:mm:ss');
                    };
                    KibanTimer.prototype.start = function () {
                        var self = this;
                        if (!self.isTimerStart()) {
                            self.oldDated(new Date());
                            self.isTimerStart(true);
                            self.interval = setInterval(self.run, 1000, self);
                        }
                    };
                    KibanTimer.prototype.end = function () {
                        var self = this;
                        if (self.isTimerStart()) {
                            self.oldDated(undefined);
                            self.isTimerStart(false);
                            clearInterval(self.interval);
                        }
                    };
                    return KibanTimer;
                }());
                sharedvm.KibanTimer = KibanTimer;
            })(sharedvm = ui.sharedvm || (ui.sharedvm = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsLinkButtonBindingHandler = (function () {
                    function NtsLinkButtonBindingHandler() {
                    }
                    NtsLinkButtonBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var jump = ko.unwrap(data.jump);
                        var action = data.action;
                        var linkText = $(element).text();
                        var $linkButton = $(element).wrap('<div class="ntsControl"/>')
                            .addClass('link-button')
                            .click(function () {
                            event.preventDefault();
                            if (!nts.uk.util.isNullOrUndefined(action))
                                action.call(viewModel);
                            else if (!nts.uk.util.isNullOrUndefined(jump))
                                nts.uk.request.jump(jump);
                        });
                    };
                    NtsLinkButtonBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    return NtsLinkButtonBindingHandler;
                }());
                ko.bindingHandlers['ntsLinkButton'] = new NtsLinkButtonBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=nts.uk.com.web.nittsu.bundles.js.map