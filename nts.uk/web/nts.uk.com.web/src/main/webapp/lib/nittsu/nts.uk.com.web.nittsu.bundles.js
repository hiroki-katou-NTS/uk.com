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
            /**
             * 常にtrueを返す関数が必要になったらこれ
             */
            function alwaysTrue() {
                return true;
            }
            util.alwaysTrue = alwaysTrue;
            /**
             * function find an item index in array
             * if key presented will perform find index of item in array which contain key equal to the 'item' parameter
             */
            function findIndex(arr, value, key) {
                for (var i = 0; i < arr.length; i++) {
                    var item = arr[i];
                    if (item[key] === value)
                        return i;
                }
                return -1;
            }
            util.findIndex = findIndex;
            /**
             * function add item to array, this function is used in combine with visitDfs function
             * visitDfs(node, addToArray, childField, arr) will return flatArray by DFS order, start by node and following by each child belong to it.
             */
            function addToArray(node, arr) {
                arr.push(node);
            }
            /**
             * DFS algorithm function to iterate over an object with structre like tree
             */
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
            /**
             * return flatern array of array of tree-like objects
             */
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
            /**
             * return filtered array
             * @param {Array} array of items
             * @param {String} user input
             * @param {Array} array of fields used to search on
             * @param {String} if not null, search will perform in flatarray of arr
             */
            function searchArray(arr, searchTerm, fields, childField) {
                //if items is empty return empty array
                if (!arr) {
                    return [];
                }
                if (!(searchTerm instanceof String)) {
                    searchTerm = "" + searchTerm;
                }
                var flatArr = flatArray(arr, childField);
                var filter = searchTerm.toLowerCase();
                //if filter is empty return all the items
                if (!filter) {
                    return flatArr;
                }
                //filter data
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
            /**
             * SearchBox helper function to jump next search
             */
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
            /**
             * Returns true if the target is null or undefined.
             */
            function isNullOrUndefined(target) {
                return target === null || target === undefined;
            }
            util.isNullOrUndefined = isNullOrUndefined;
            /**
             * Returns true if the target is null or undefined or blank.
             * @param  {any} [target] Target need to check
             * @return {boolean}      True for blank
             */
            function isNullOrEmpty(target) {
                return (target === undefined || target === null || target.length == 0);
            }
            util.isNullOrEmpty = isNullOrEmpty;
            /**
             * Generate random identifier string (UUIDv4)
             */
            function randomId() {
                return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                    var r = Math.random() * 16 | 0;
                    return ((c == 'x') ? r : (r & 0x3 | 0x8)).toString(16);
                });
            }
            util.randomId = randomId;
            /**
             * Returns true if current window is in frame.
             */
            function isInFrame() {
                return window.parent != window;
            }
            util.isInFrame = isInFrame;
            /**
             * valueMaybeEmptyがnullまたはundefinedの場合、defaultValueを返す。
             * そうでなければ、valueMaybeEmptyを返す。
             */
            function orDefault(valueMaybeEmpty, defaultValue) {
                return isNullOrUndefined(valueMaybeEmpty) ? defaultValue : valueMaybeEmpty;
            }
            util.orDefault = orDefault;
            /**
             * Returns true if expects contains actual.
             */
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
                let result = convertToTree(original, openChar, closeChar, seperatorChar, 1, operatorChar).result;
                //            result = moveToParentIfEmpty(result);
                return result;
            }
            util.createTreeFromString = createTreeFromString;
            function moveToParentIfEmpty(tree) {
                let result = [];
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
                let result = [];
                while (original.trim().length > 0) {
                    let firstOpenIndex = original.indexOf(openChar);
                    if (firstOpenIndex < 0) {
                        let values = original.split(separatorChar);
                        _.forEach(values, function (value) {
                            let data = splitByArray(value, operatorChar.slice());
                            _.each(data, function (v) {
                                let object = new TreeObject();
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
                        let object = new TreeObject();
                        object.value = original.substring(0, firstOpenIndex).trim();
                        object.index = index;
                        let closeIndex = findIndexOfCloseChar(original, openChar, closeChar, firstOpenIndex);
                        if (closeIndex >= 0) {
                            index++;
                            let res = convertToTree(original.substring(firstOpenIndex + 1, closeIndex).trim(), openChar, closeChar, separatorChar, index, operatorChar);
                            object.children = res.result;
                            index = res.index++;
                            result.push(object);
                            let firstSeperatorIndex = original.indexOf(separatorChar, closeIndex);
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
                let temp = [];
                let result = [];
                if (original.trim().length <= 0) {
                    return temp;
                }
                if (operatorChar.length <= 0) {
                    return [original];
                }
                let operator = operatorChar.shift();
                while (original.trim().length > 0) {
                    let index = original.indexOf(operator);
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
                let openCount = 0;
                let closeCount = 0;
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
            class TreeObject {
                constructor(value, children, index, isOperator) {
                    var self = this;
                    self.value = value;
                    self.children = children;
                    self.index = index;
                    self.isOperator = isOperator;
                }
            }
            util.TreeObject = TreeObject;
            /**
             * Like Java Optional
             */
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
                class Optional {
                    constructor(value) {
                        this.value = orDefault(value, null);
                    }
                    ifPresent(consumer) {
                        if (this.isPresent) {
                            consumer(this.value);
                        }
                        return this;
                    }
                    ifEmpty(action) {
                        if (!this.isPresent) {
                            action();
                        }
                        return this;
                    }
                    map(mapper) {
                        return this.isPresent ? of(mapper(this.value)) : empty();
                    }
                    isPresent() {
                        return this.value !== null;
                    }
                    get() {
                        if (!this.isPresent) {
                            throw new Error('not present');
                        }
                        return this.value;
                    }
                    orElse(stead) {
                        return this.isPresent ? this.value : stead;
                    }
                    orElseThrow(errorBuilder) {
                        if (!this.isPresent) {
                            throw errorBuilder();
                        }
                    }
                }
                optional.Optional = Optional;
            })(optional = util.optional || (util.optional = {}));
            class Range {
                constructor(start, end) {
                    if (start > end) {
                        throw new Error('start is larger than end');
                    }
                    this.start = start;
                    this.end = end;
                }
                contains(value) {
                    return this.start <= value && value <= this.end;
                }
                greaterThan(value) {
                    return value < this.start;
                }
                greaterThanOrEqualTo(value) {
                    return value <= this.start;
                }
                lessThan(value) {
                    return this.end < value;
                }
                lessThanOrEqualTo(value) {
                    return this.end <= value;
                }
                distanceFrom(value) {
                    if (this.greaterThan(value)) {
                        return value - this.start;
                    }
                    else if (this.lessThan(value)) {
                        return value - this.end;
                    }
                    else {
                        return 0;
                    }
                }
            }
            util.Range = Range;
        })(util = uk.util || (uk.util = {}));
        class WebStorageWrapper {
            constructor(nativeStorage) {
                this.nativeStorage = nativeStorage;
            }
            setItem(key, value) {
                if (value === undefined) {
                    return;
                }
                this.nativeStorage.setItem(key, value);
            }
            setItemAsJson(key, value) {
                this.setItem(key, JSON.stringify(value));
            }
            containsKey(key) {
                return this.getItem(key) !== null;
            }
            ;
            getItem(key) {
                var value = this.nativeStorage.getItem(key);
                if (value === null || value === undefined || value === 'undefined') {
                    return util.optional.empty();
                }
                return util.optional.of(value);
            }
            getItemAndRemove(key) {
                var item = this.getItem(key);
                this.removeItem(key);
                return item;
            }
            removeItem(key) {
                this.nativeStorage.removeItem(key);
            }
            clear() {
                this.nativeStorage.clear();
            }
        }
        uk.WebStorageWrapper = WebStorageWrapper;
        /**
         * Utilities about jquery deferred
         */
        var deferred;
        (function (deferred) {
            /**
             * Repeats a task with jQuery Deferred
             */
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
                class Configuration {
                    constructor() {
                        this.pauseMilliseconds = 0;
                    }
                    task(taskFunction) {
                        this.taskFunction = taskFunction;
                        return this;
                    }
                    while(whileCondition) {
                        this.whileCondition = whileCondition;
                        return this;
                    }
                    pause(pauseMilliseconds) {
                        this.pauseMilliseconds = pauseMilliseconds;
                        return this;
                    }
                    run() {
                        let dfd = $.Deferred();
                        this.repeat(dfd);
                        return dfd.promise();
                    }
                    repeat(dfd) {
                        this.taskFunction().done(res => {
                            if (this.whileCondition(res)) {
                                setTimeout(() => this.repeat(dfd), this.pauseMilliseconds);
                            }
                            else {
                                dfd.resolve(res);
                            }
                        }).fail(res => {
                            dfd.reject(res);
                        });
                    }
                }
            })(repeater || (repeater = {}));
        })(deferred = uk.deferred || (uk.deferred = {}));
        var resource;
        (function (resource) {
            function getText(code) {
                return __viewContext.codeNames[code];
            }
            resource.getText = getText;
            function getMessage(messageId, ...params) {
                let message = __viewContext.messages[messageId];
                message = formatParams(message, params);
                message = formatCompDependParam(message);
                return message;
            }
            resource.getMessage = getMessage;
            function formatCompDependParam(message) {
                let compDependceParamRegex = /{#(\w*)}/;
                let matches;
                while (matches = compDependceParamRegex.exec(message)) {
                    let code = matches[1];
                    let text = __viewContext.codeNames[code];
                    message = message.replace(compDependceParamRegex, text);
                }
                return message;
            }
            function formatParams(message, args) {
                if (args == undefined)
                    return message;
                let paramRegex = /{([0-9])+(:\\w+)?}/;
                let matches;
                while (matches = paramRegex.exec(message)) {
                    let code = matches[1];
                    let text = args[parseInt(code)];
                    message = message.replace(paramRegex, text);
                }
                return message;
            }
        })(resource = uk.resource || (uk.resource = {}));
        uk.sessionStorage = new WebStorageWrapper(window.sessionStorage);
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var format;
        (function (format) {
            class NoFormatter {
                format(source) {
                    return source;
                }
            }
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
            /**
             * 半角カタカナを全角カタカナに変換
             *
             * @param {String} str 変換したい文字列
             */
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
            /**
             * 文字列が半角数字のみで構成された1文字以上の文字列かどうか判断する
             * @param text 解析対象の文字列
             */
            function allHalfNumeric(text) {
                return regexp.allHalfNumeric.test(text);
            }
            text_1.allHalfNumeric = allHalfNumeric;
            /**
             * 文字列が半角英字のみで構成された1文字以上の文字列かどうか判断する
             * @param text 解析対象の文字列
             */
            function allHalfAlphabet(text) {
                return regexp.allHalfAlphabet.test(text);
            }
            text_1.allHalfAlphabet = allHalfAlphabet;
            /**
             * 文字列が半角英数字のみで構成された1文字以上の文字列かどうか判断する
             * @param text 解析対象の文字列
             */
            function allHalfAlphanumeric(text) {
                return regexp.allHalfAlphanumeric.test(text);
            }
            text_1.allHalfAlphanumeric = allHalfAlphanumeric;
            /**
             * 文字列が半角カナのみで構成された1文字以上の文字列かどうか判断する
             * @param text 解析対象の文字列
             */
            function allHalfKatakana(text) {
                return regexp.allHalfKatakanaReg.test(text);
            }
            text_1.allHalfKatakana = allHalfKatakana;
            /**
             * 文字列が全角カナのみで構成された1文字以上の文字列かどうか判断する
             * @param text 解析対象の文字列
             */
            function allFullKatakana(text) {
                return regexp.allFullKatakanaReg.test(text);
            }
            text_1.allFullKatakana = allFullKatakana;
            /**
             * 文字列が半角文字のみで構成された1文字以上の文字列かどうか判断する
             * @param text 解析対象の文字列
             */
            function allHalf(text) {
                return text.length === countHalf(text);
            }
            text_1.allHalf = allHalf;
            /**
             * 文字列が平仮名のみで構成された1文字以上の文字列かどうか判断する
             * @param text 解析対象の文字列
             */
            function allHiragana(text) {
                return regexp.allHiragana.test(text);
            }
            text_1.allHiragana = allHiragana;
            /**
             * 文字列がカタカナのみで構成された1文字以上の文字列かどうか判断する
             * @param text 解析対象の文字列
             */
            function allKatakana(text) {
                return regexp.allFullKatakanaReg.test(text);
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
            function format(format, ...args) {
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
            /**
             * Type of characters
             */
            class CharType {
                constructor(viewName, width, validator) {
                    this.viewName = viewName;
                    this.width = width;
                    this.validator = validator;
                }
                validate(text) {
                    var result = new uk.ui.validation.ValidationResult();
                    if (this.validator(text)) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                buildConstraintText(maxLength) {
                    return this.viewName + this.getViewLength(maxLength) + '文字';
                }
                getViewLength(length) {
                    return Math.floor(length / (this.width * 2));
                }
            }
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
            /**
             * Format for EmployeeCode
             * @return {String}  EmployeeCode
             */
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
            class StringFormatter {
                constructor(args) {
                    this.args = args;
                }
                format(source) {
                    var constraintName = this.args.constraintName;
                    if (constraintName === "EmployeeCode") {
                        var constraint = this.args.constraint;
                        var filldirection = this.args.editorOption.filldirection;
                        var fillcharacter = this.args.editorOption.fillcharacter;
                        var length = (constraint && constraint.maxLength) ? constraint.maxLength : 10;
                        return formatEmployeeCode(source, filldirection, fillcharacter, length);
                    }
                    return source;
                }
            }
            text_1.StringFormatter = StringFormatter;
            class NumberFormatter {
                constructor(option) {
                    this.option = option;
                }
                format(source) {
                    return uk.ntsNumber.formatNumber(source, this.option.option);
                }
            }
            text_1.NumberFormatter = NumberFormatter;
            class TimeFormatter {
                constructor(option) {
                    this.option = option;
                }
                format(source) {
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
                }
            }
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
            ntsNumber.trunc = (typeof Math.trunc === 'function') ? Math.trunc : value => value > 0 ? Math.floor(value) : Math.ceil(value);
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
/// <reference path="reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var time;
        (function (time_1) {
            var defaultInputFormat = ["YYYY/MM/DD", "YYYY-MM-DD", "YYYYMMDD", "YYYY/MM", "YYYY-MM", "YYYYMM", "HH:mm"];
            var listEmpire = {
                "明治": "1868/01/01",
                "大正": "1912/07/30",
                "昭和": "1926/12/25",
                "平成": "1989/01/08"
            };
            var dotW = ["日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日"];
            function getYearMonthJapan(year, month) {
                if (month)
                    return year + "年 " + month + " 月";
                return year;
            }
            class JapanYearMonth {
                constructor(empire, year, month) {
                    this.empire = empire;
                    this.year = year;
                    this.month = month;
                }
                getEmpire() {
                    return this.empire;
                }
                getYear() {
                    return this.year;
                }
                getMonth() {
                    return this.month;
                }
                toString() {
                    return (this.empire === undefined ? "" : this.empire + " ")
                        + (this.year === undefined ? "" : this.year + " 年 ")
                        + (this.month === undefined ? "" : this.month + " 月");
                }
            }
            time_1.JapanYearMonth = JapanYearMonth;
            function yearInJapanEmpire(date) {
                let year = moment.utc(date).year();
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
            class JapanDateMoment {
                constructor(date, outputFormat) {
                    var MomentResult = parseMoment(date, outputFormat);
                    var year = MomentResult.momentObject.year();
                    var month = MomentResult.momentObject.month() + 1;
                }
                toString() {
                    return (this.empire === undefined ? "" : this.empire + " ")
                        + (this.year === undefined ? "" : this.year + " 年 ")
                        + (this.month === undefined ? "" : this.month + " 月")
                        + (this.day === undefined ? "" : this.day + " ");
                }
            }
            time_1.JapanDateMoment = JapanDateMoment;
            function dateInJapanEmpire(date) {
                return new JapanDateMoment(date);
            }
            time_1.dateInJapanEmpire = dateInJapanEmpire;
            /**
            * Format by pattern
            * @param  {number} [seconds]	  Input seconds
            * @param  {string} [formatOption] Format option
            * @return {string}				Formatted duration
            */
            function formatSeconds(seconds, formatOption) {
                seconds = parseInt(String(seconds));
                var ss = uk.text.padLeft(String(seconds % 60), '0', 2);
                var minutes = Math.floor(seconds / 60);
                var mm = uk.text.padLeft(String(minutes % 60), '0', 2);
                var hours = uk.ntsNumber.trunc(seconds / 60 / 60);
                var h = String(hours);
                // TODO: use formatOption
                return "h:mm:ss"
                    .replace(/h/g, h)
                    .replace(/mm/g, mm)
                    .replace(/ss/g, ss);
            }
            time_1.formatSeconds = formatSeconds;
            /**
            * 日付をフォーマットする
            * @param  {Date}   date	 日付
            * @param  {String} [format] フォーマット
            * @return {String}		  フォーマット済み日付
            */
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
            /**
            * Format YearMonth
            * @param  {Number} [yearMonth]	Input Yearmonth
            * @return {String}				Formatted YearMonth
            */
            function formatYearMonth(yearMonth) {
                var result;
                var num = parseInt(String(yearMonth));
                var year = String(Math.floor(num / 100));
                var month = uk.text.charPadding(String(num % 100), '0', true, 2);
                result = year + '/' + month;
                return result;
            }
            time_1.formatYearMonth = formatYearMonth;
            /**
            * Format by pattern
            * @param  {Date}   [date]		 Input date
            * @param  {String} [inputFormat]  Input format
            * @param  {String} [outputFormat] Output format
            * @return {String}				Formatted date
            */
            function formatPattern(date, inputFormat, outputFormat) {
                outputFormat = uk.text.getISOFormat(outputFormat);
                var inputFormats = (inputFormat) ? inputFormat : defaultInputFormat;
                return moment.utc(date, inputFormats).format(outputFormat);
            }
            time_1.formatPattern = formatPattern;
            class ParseResult {
                constructor(success) {
                    this.success = success;
                }
            }
            class ResultParseTime extends ParseResult {
                constructor(success, minus, hours, minutes, msg) {
                    super(success);
                    this.minus = minus;
                    this.hours = hours;
                    this.minutes = minutes;
                    this.msg = msg || "invalid time format";
                }
                static succeeded(minus, hours, minutes) {
                    return new ResultParseTime(true, minus, hours, minutes);
                }
                static failed() {
                    return new ResultParseTime(false);
                }
                format() {
                    if (!this.success)
                        return "";
                    return (this.minus ? '-' : '') + this.hours + ':' + uk.text.padLeft(String(this.minutes), '0', 2);
                }
                toValue() {
                    if (!this.success)
                        return 0;
                    return (this.minus ? -1 : 1) * (this.hours * 60 + this.minutes);
                }
                getMsg() { return this.msg; }
            }
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
            class ResultParseYearMonth extends ParseResult {
                constructor(success, msg, year, month) {
                    super(success);
                    this.year = year;
                    this.month = month;
                    this.msg = msg || "must yyyymm or yyyy/mm format: year in [1900-9999] and month in [1-12] ";
                }
                static succeeded(year, month) {
                    return new ResultParseYearMonth(true, "", year, month);
                }
                static failed(msg) {
                    return new ResultParseYearMonth(false, msg);
                }
                format() {
                    if (!this.success) {
                        return "";
                    }
                    return this.year + '/' + uk.text.padLeft(String(this.month), '0', 2);
                }
                toValue() {
                    if (!this.success) {
                        return 0;
                    }
                    return (this.year * 100 + this.month);
                }
                getMsg() { return this.msg; }
            }
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
            class ResultParseTimeOfTheDay extends ParseResult {
                constructor(success, msg, hour, minute) {
                    super(success);
                    this.hour = hour;
                    this.minute = minute;
                    this.msg = msg || "time of the days must in format hh:mm with hour in range 0-23; minute in range 00-59";
                }
                static succeeded(hour, minute) {
                    return new ResultParseTimeOfTheDay(true, "", hour, minute);
                }
                static failed(msg) {
                    return new ResultParseTimeOfTheDay(false, msg);
                }
                format() {
                    if (!this.success) {
                        return "";
                    }
                    return this.hour + ':' + uk.text.padLeft(String(this.minute), '0', 2);
                }
                toValue() {
                    if (!this.success) {
                        return 0;
                    }
                    return (this.hour * 100 + this.minute);
                }
                getMsg() { return this.msg; }
            }
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
                //console.log(checkNum.substring(0,stringLength-2));
                if (hour < 0 || hour > 23)
                    return ResultParseTimeOfTheDay.failed("invalid: hour must in range 0-23");
                if (minute < 0 || minute > 59)
                    return ResultParseTimeOfTheDay.failed("invalid: minute must in range 0-59");
                return ResultParseTimeOfTheDay.succeeded(hour, minute);
            }
            time_1.parseTimeOfTheDay = parseTimeOfTheDay;
            class ResultParseYearMonthDate extends ParseResult {
                constructor(success, msg, year, month, date) {
                    super(success);
                    this.year = year;
                    this.month = month;
                    this.date = date;
                    this.msg = msg || "must yyyymm or yyyy/mm format: year in [1900-9999] and month in [1-12] ";
                }
                static succeeded(year, month, date) {
                    return new ResultParseYearMonthDate(true, "", year, month, date);
                }
                static failed(msg) {
                    return new ResultParseYearMonthDate(false, msg);
                }
                format() {
                    if (!this.success) {
                        return "";
                    }
                    return this.year + '/' + uk.text.padLeft(String(this.month), '0', 2) + uk.text.padLeft(String(this.date), '0', 2);
                }
                toValue() {
                    if (!this.success) {
                        return 0;
                    }
                    return (this.year * 10000 + this.month * 100 + this.date);
                }
                getMsg() { return this.msg; }
            }
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
            class MomentResult extends ParseResult {
                constructor(momentObject, outputFormat) {
                    super(true);
                    this.momentObject = momentObject;
                    this.outputFormat = uk.text.getISOFormat(outputFormat);
                }
                succeeded() {
                    this.success = true;
                }
                failed(msg) {
                    this.msg = (msg) ? msg : "Invalid format";
                    this.success = false;
                }
                format() {
                    if (!this.success)
                        return "";
                    return this.momentObject.format(this.outputFormat);
                }
                toValue() {
                    if (!this.success)
                        return null;
                    return this.momentObject;
                }
                toNumber(outputFormat) {
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
                }
                getMsg() { return this.msg; }
            }
            time_1.MomentResult = MomentResult;
            function parseMoment(datetime, outputFormat, inputFormat) {
                var inputFormats = (inputFormat) ? inputFormat : defaultInputFormat;
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
                // Return local time in UTC
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
            const WEB_APP_NAME = {
                com: 'nts.uk.com.web',
                pr: 'nts.uk.pr.web'
            };
            class QueryString {
                constructor() {
                    this.items = {};
                }
                static parseUrl(url) {
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
                }
                static build(entriesObj) {
                    var instance = new QueryString();
                    for (var key in entriesObj) {
                        instance.set(key, entriesObj[key]);
                    }
                    return instance;
                }
                get(key) {
                    return this.items[key];
                }
                set(key, value) {
                    if (key === null || key === undefined || key === '') {
                        return;
                    }
                    this.items[key] = value;
                }
                remove(key) {
                    delete this.items[key];
                }
                mergeFrom(otherObj) {
                    for (var otherKey in otherObj.items) {
                        this.set(otherKey, otherObj.items[otherKey]);
                    }
                }
                count() {
                    var count = 0;
                    for (var key in this.items) {
                        count++;
                    }
                    return count;
                }
                hasItems() {
                    return this.count() !== 0;
                }
                serialize() {
                    var entryStrings = [];
                    for (var key in this.items) {
                        entryStrings.push(key + '=' + this.items[key]);
                    }
                    return entryStrings.join('&');
                }
            }
            request.QueryString = QueryString;
            /**
             * URL and QueryString
             */
            class Locator {
                constructor(url) {
                    this.rawUrl = url;
                    this.queryString = QueryString.parseUrl(url);
                }
                serialize() {
                    if (this.queryString.hasItems()) {
                        return this.rawUrl + '?' + this.queryString.serialize();
                    }
                    else {
                        return this.rawUrl;
                    }
                }
                mergeRelativePath(relativePath) {
                    var stack = this.rawUrl.split('/');
                    var parts = relativePath.split('/');
                    var queryStringToAdd = QueryString.parseUrl(relativePath);
                    // 最後のファイル名は除外
                    // (最後がフォルダ名でしかも / で終わっていない場合は考慮しない)
                    stack.pop();
                    // relativePathの先頭が '/' の場合、それを取り除く
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
                }
            }
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
                let dfd = $.Deferred();
                ajax(path, data, options)
                    .then((res) => {
                    return uk.deferred.repeat(conf => conf
                        .task(() => specials.getAsyncTaskInfo(res.taskId))
                        .while(info => info.pending || info.running)
                        .pause(1000));
                })
                    .done((res) => {
                    specials.donwloadFile(res.id);
                    dfd.resolve(res);
                })
                    .fail(res => {
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
/// <reference path="../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            /** Event to notify document ready to initialize UI. */
            ui.documentReady = $.Callbacks();
            /** Event to notify ViewModel built to bind. */
            ui.viewModelBuilt = $.Callbacks();
            // Kiban ViewModel
            class KibanViewModel {
                constructor() {
                    this.title = ko.observable('');
                    this.errorDialogViewModel = new nts.uk.ui.errors.ErrorsViewModel();
                }
            }
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
                            isEmpty: ko.computed(() => !kiban.errorDialogViewModel.occurs())
                        }
                    };
                    kiban.title(__viewContext.title || 'THIS IS TITLE');
                    ui.viewModelBuilt.fire(ui._viewModel);
                    ko.applyBindings(ui._viewModel);
                };
                $(function () {
                    ui.documentReady.fire();
                    __viewContext.transferred = uk.sessionStorage.getItemAndRemove(uk.request.STORAGE_KEY_TRANSFER_DATA)
                        .map(v => JSON.parse(v));
                    _.defer(() => _start.call(__viewContext));
                });
            })(init || (init = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../reference.ts"/>
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
                    ui.documentReady.add(() => {
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
/// <reference path="../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var validation;
            (function (validation) {
                class NoValidator {
                    validate(inputText, option) {
                        var result = new ValidationResult();
                        result.isValid = true;
                        result.parsedValue = inputText;
                        return result;
                    }
                }
                validation.NoValidator = NoValidator;
                class ValidationResult {
                    constructor() {
                        this.errorMessage = 'error message';
                    }
                    fail(errorMessage) {
                        this.errorMessage = errorMessage;
                        this.isValid = false;
                    }
                    success(parsedValue) {
                        this.parsedValue = parsedValue;
                        this.isValid = true;
                    }
                }
                validation.ValidationResult = ValidationResult;
                class StringValidator {
                    constructor(primitiveValueName, option) {
                        this.constraint = getConstraint(primitiveValueName);
                        this.charType = uk.text.getCharType(primitiveValueName);
                        this.required = option.required;
                    }
                    validate(inputText, option) {
                        var result = new ValidationResult();
                        // Check Required
                        if (this.required !== undefined && this.required !== false) {
                            if (uk.util.isNullOrEmpty(inputText)) {
                                result.fail('This field is required');
                                return result;
                            }
                        }
                        // Check CharType
                        if (this.charType !== null && this.charType !== undefined) {
                            if (this.charType.viewName === '半角数字' || this.charType.viewName === '半角英数字') {
                                inputText = uk.text.toOneByteAlphaNumberic(inputText);
                            }
                            else if (this.charType.viewName === 'カタカナ') {
                                inputText = uk.text.oneByteKatakanaToTwoByte(inputText);
                            }
                            if (!this.charType.validate(inputText)) {
                                result.fail('Invalid text');
                                return result;
                            }
                        }
                        // Check Constraint
                        if (this.constraint !== undefined && this.constraint !== null) {
                            if (this.constraint.maxLength !== undefined && uk.text.countHalf(inputText) > this.constraint.maxLength) {
                                result.fail('Max length for this input is ' + this.constraint.maxLength);
                                return result;
                            }
                            if (!uk.util.isNullOrUndefined(option) && option.isCheckExpression === true) {
                                if (!uk.text.isNullOrEmpty(this.constraint.stringExpression) && !this.constraint.stringExpression.test(inputText)) {
                                    result.fail('This field is not valid with pattern!');
                                    return result;
                                }
                            }
                        }
                        result.success(inputText);
                        return result;
                    }
                }
                validation.StringValidator = StringValidator;
                class NumberValidator {
                    constructor(primitiveValueName, option) {
                        this.constraint = getConstraint(primitiveValueName);
                        this.option = option;
                    }
                    validate(inputText) {
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
                    }
                }
                validation.NumberValidator = NumberValidator;
                class TimeValidator {
                    constructor(primitiveValueName, option) {
                        this.constraint = getConstraint(primitiveValueName);
                        this.outputFormat = (option && option.outputFormat) ? option.outputFormat : "";
                        this.required = (option && option.required) ? option.required : false;
                        this.valueType = (option && option.valueType) ? option.valueType : "string";
                    }
                    validate(inputText) {
                        var result = new ValidationResult();
                        // Check required
                        if (uk.util.isNullOrEmpty(inputText)) {
                            if (this.required === true) {
                                result.fail('This field is required');
                                return result;
                            }
                            else {
                                result.success("");
                                return result;
                            }
                        }
                        // Create Parser
                        var parseResult = uk.time.parseMoment(inputText, this.outputFormat);
                        // Parse
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
                    }
                }
                validation.TimeValidator = TimeValidator;
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
/// <reference path="../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var errors;
            (function (errors) {
                class ErrorsViewModel {
                    constructor() {
                        this.title = "エラー一覧";
                        this.errors = ko.observableArray([]);
                        this.errors.extend({ rateLimit: 1 });
                        this.option = ko.mapping.fromJS(new ui.option.ErrorDialogOption());
                        this.occurs = ko.computed(() => this.errors().length !== 0);
                        this.allResolved = $.Callbacks();
                        this.errors.subscribe(() => {
                            if (this.errors().length === 0) {
                                this.allResolved.fire();
                            }
                        });
                        this.allResolved.add(() => {
                            this.hide();
                        });
                    }
                    closeButtonClicked() {
                    }
                    open() {
                        this.option.show(true);
                    }
                    hide() {
                        this.option.show(false);
                    }
                    addError(error) {
                        // defer無しでerrorsを呼び出すと、なぜか全てのKnockoutBindingHandlerのupdateが呼ばれてしまうので、
                        // 原因がわかるまでひとまずdeferを使っておく
                        //_.defer(() => {
                        var duplicate = _.filter(this.errors(), e => e.$control.is(error.$control) && e.message == error.message);
                        if (duplicate.length == 0)
                            this.errors.push(error);
                        //});
                    }
                    hasError() {
                        return this.occurs();
                    }
                    clearError() {
                        $(".error").removeClass('error');
                        this.errors.removeAll();
                    }
                    removeErrorByElement($element) {
                        // addErrorと同じ対応
                        //_.defer(() => {
                        var removeds = _.filter(this.errors(), e => e.$control.is($element));
                        this.errors.removeAll(removeds);
                        //});
                    }
                }
                errors.ErrorsViewModel = ErrorsViewModel;
                class ErrorHeader {
                    constructor(name, text, width, visible) {
                        this.name = name;
                        this.text = text;
                        this.width = width;
                        this.visible = visible;
                    }
                }
                errors.ErrorHeader = ErrorHeader;
                /**
                 *  Public API
                **/
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
                function hasError() {
                    return errorsViewModel().hasError();
                }
                errors.hasError = hasError;
                function clearAll() {
                    errorsViewModel().clearError();
                }
                errors.clearAll = clearAll;
                function removeByElement($control) {
                    errorsViewModel().removeErrorByElement($control);
                }
                errors.removeByElement = removeByElement;
            })(errors = ui.errors || (ui.errors = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../reference.ts"/>
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
                /**
                 * Main or Sub Window(dialog)
                 */
                class ScreenWindow {
                    constructor(id, isRoot, parent) {
                        this.globalContext = null;
                        this.$dialog = null;
                        this.$iframe = null;
                        this.onClosedHandler = $.noop;
                        this.id = id;
                        this.isRoot = isRoot;
                        this.parent = parent;
                    }
                    static createMainWindow() {
                        return new ScreenWindow(MAIN_WINDOW_ID, true, null);
                    }
                    static createSubWindow(parent) {
                        return new ScreenWindow(uk.util.randomId(), false, parent);
                    }
                    setGlobal(globalContext) {
                        this.globalContext = globalContext;
                    }
                    setTitle(newTitle) {
                        if (this.isRoot) {
                            this.globalContext.title = newTitle;
                        }
                        else {
                            this.$dialog.dialog('option', { title: newTitle });
                        }
                    }
                    setHeight(height) {
                        if (!isNaN(height)) {
                            this.$dialog.dialog('option', {
                                height: height
                            });
                            this.$dialog.resize();
                        }
                    }
                    setWidth(width) {
                        if (!isNaN(width)) {
                            this.$dialog.dialog('option', {
                                width: width
                            });
                            this.$dialog.resize();
                        }
                    }
                    setSize(height, width) {
                        if (!isNaN(width) && !isNaN(height)) {
                            this.$dialog.dialog('option', {
                                width: width,
                                height: height
                            });
                            this.$dialog.resize();
                        }
                    }
                    setupAsDialog(path, options) {
                        options.close = () => {
                            this.dispose();
                        };
                        this.build$dialog(options);
                        this.$iframe.bind('load', () => {
                            this.globalContext.nts.uk.ui.windows.selfId = this.id;
                            this.$dialog.dialog('option', {
                                width: options.width || this.globalContext.dialogSize.width,
                                height: options.height || this.globalContext.dialogSize.height,
                                title: options.title || "dialog",
                                resizable: true,
                                beforeClose: function () {
                                    //return dialogWindow.__viewContext.dialog.beforeClose();
                                }
                            }).dialog('open');
                        });
                        this.globalContext.location.href = uk.request.resolvePath(path);
                    }
                    build$dialog(options) {
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
                    }
                    onClosed(callback) {
                        this.onClosedHandler = function () {
                            callback();
                            windows.container.localShared = {};
                        };
                    }
                    close() {
                        if (this.isRoot) {
                            window.close();
                        }
                        else {
                            this.$dialog.dialog('close');
                        }
                    }
                    dispose() {
                        _.defer(() => this.onClosedHandler());
                        // delay 2 seconds to avoid IE error when any JS is running in destroyed iframe
                        setTimeout(() => {
                            this.$iframe.remove();
                            this.$dialog.remove();
                            this.$dialog = null;
                            this.$iframe = null;
                            this.globalContext = null;
                            this.parent = null;
                            this.onClosedHandler = null;
                        }, 2000);
                    }
                }
                windows.ScreenWindow = ScreenWindow;
                /**
                 * All ScreenWindows are managed by this container.
                 * this instance is singleton in one browser-tab.
                 */
                class ScreenWindowContainer {
                    constructor() {
                        this.windows = {};
                        this.windows[windows.selfId] = ScreenWindow.createMainWindow();
                        this.windows[windows.selfId].setGlobal(window);
                        this.shared = {};
                        this.localShared = {};
                    }
                    /**
                     * All dialog object is in MainWindow.
                     */
                    createDialog(path, options, parentId) {
                        var parentwindow = this.windows[parentId];
                        var subWindow = ScreenWindow.createSubWindow(parentwindow);
                        this.windows[subWindow.id] = subWindow;
                        options = $.extend({}, DEFAULT_DIALOG_OPTIONS, options);
                        subWindow.setupAsDialog(path, options);
                        return subWindow;
                    }
                    getShared(key) {
                        return this.localShared[key] !== undefined ? this.localShared[key] : this.shared[key];
                    }
                    setShared(key, data, isRoot, persist) {
                        if (persist || isRoot) {
                            this.shared[key] = data;
                        }
                        else {
                            this.localShared[key] = data;
                        }
                    }
                    close(id) {
                        var target = this.windows[id];
                        delete this.windows[id];
                        target.close();
                    }
                }
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
                /**
                 * Show information dialog.
                 *
                 * @param {String}
                 *			text information text
                 * @returns handler
                 */
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
                /**
                 * Show alert dialog.
                 *
                 * @param {String}
                 *			text information text
                 * @returns handler
                 */
                function alert(text) {
                    return info(text);
                }
                dialog.alert = alert;
                ;
                /**
                 * Show confirm dialog.
                 *
                 * @param {String}
                 *			text information text
                 * @returns handler
                 */
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
                        // yes button
                        buttons.push({
                            text: "はい",
                            "class": "yes large danger",
                            click: function () {
                                $this.dialog('close');
                                handleYes();
                                handleThen();
                            }
                        });
                        // no button
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
                        // cancel button
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
                class ContextMenu {
                    /**
                     * Create an instance of ContextMenu. Auto call init() method
                     *
                     * @constructor
                     * @param {selector} Jquery selector for elements need to show ContextMenu
                     * @param {items} List ContextMenuItem for ContextMenu
                     * @param {enable} (Optinal) Set enable/disable for ContextMenu
                     */
                    constructor(selector, items, enable) {
                        this.selector = selector;
                        this.items = items;
                        this.enable = (enable !== undefined) ? enable : true;
                        this.init();
                    }
                    /**
                     * Create ContextMenu and bind event in DOM
                     */
                    init() {
                        var self = this;
                        // Remove ContextMenu with same 'selector' (In case Ajax call will re-create DOM elements)
                        $('body .ntsContextMenu').each(function () {
                            if ($(this).data("selector") === self.selector) {
                                $("body").off("contextmenu", self.selector);
                                $(this).remove();
                            }
                        });
                        // Initial
                        self.guid = nts.uk.util.randomId();
                        var $contextMenu = $("<ul id='" + self.guid + "' class='ntsContextMenu'></ul>").data("selector", self.selector).hide();
                        self.createMenuItems($contextMenu);
                        $('body').append($contextMenu);
                        // Binding contextmenu event
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
                        // Hiding when click outside
                        $("html").on("mousedown", function (event) {
                            if (!$contextMenu.is(event.target) && $contextMenu.has(event.target).length === 0) {
                                $contextMenu.hide();
                            }
                        });
                    }
                    /**
                     * Remove and unbind ContextMenu event
                     */
                    destroy() {
                        // Unbind contextmenu event
                        $("html").off("contextmenu", this.selector);
                        $("#" + this.guid).remove();
                    }
                    /**
                     * Re-create ContextMenu. Useful when you change various things in ContextMenu.items
                     */
                    refresh() {
                        this.destroy();
                        this.init();
                    }
                    /**
                     * Get a ContextMenuItem instance
                     *
                     * @param {target} Can be string or number. String type will select item by "key", Number type will select item by index
                     * @return {any} Return ContextMenuItem if found or undefiend
                     */
                    getItem(target) {
                        if (typeof target === "number") {
                            return this.items[target];
                        }
                        else if (typeof target === "string") {
                            return _.find(this.items, ["key", target]);
                        }
                        else {
                            return undefined;
                        }
                    }
                    /**
                     * Add an ContextMenuItem instance to ContextMenu
                     *
                     * @param {item} An ContextMenuItem instance
                     */
                    addItem(item) {
                        this.items.push(item);
                        this.refresh();
                    }
                    /**
                     * Remove item with given "key" or index
                     *
                     * @param {target} Can be string or number. String type will select item by "key", Number type will select item by index
                     */
                    removeItem(target) {
                        var item = this.getItem(target);
                        if (item !== undefined) {
                            _.remove(this.items, item);
                            this.refresh();
                        }
                    }
                    /**
                     * Enable/Disable ContextMenu. If disable right-click will have default behavior
                     *
                     * @param {enable} A boolean value set enable/disable
                     */
                    setEnable(enable) {
                        this.enable = enable;
                    }
                    /**
                     * Enable/Disable item with given "key" or index
                     *
                     * @param {enable} A boolean value set enable/disable
                     * @param {target} Can be string or number. String type will select item by "key", Number type will select item by index
                     */
                    setEnableItem(enable, target) {
                        var item = this.getItem(target);
                        item.enable = enable;
                        this.refresh();
                    }
                    /**
                     * Show/Hide item with given "key" or index
                     *
                     * @param {enable} A boolean value set visible/hidden
                     * @param {target} Can be string or number. String type will select item by "key", Number type will select item by index
                     */
                    setVisibleItem(visible, target) {
                        var item = this.getItem(target);
                        item.visible = visible;
                        this.refresh();
                    }
                    createMenuItems(container) {
                        var self = this;
                        _.forEach(self.items, function (item) {
                            if (item.key !== "divider") {
                                let menuClasses = "menu-item ";
                                menuClasses += (item.enable === true) ? "" : "disabled ";
                                menuClasses += (item.visible === true) ? "" : "hidden ";
                                let menuItem = $("<li class='" + menuClasses + "'><span class='menu-icon " + item.icon + "'></span>" + item.text + "</li>")
                                    .data("key", item.key)
                                    .on("click", function () {
                                    if (!$(this).hasClass("disabled")) {
                                        item.handler(self.target);
                                        container.hide();
                                    }
                                }).appendTo(container);
                            }
                            else {
                                let menuItem = $("<li class='menu-item divider'></li>").appendTo(container);
                            }
                        });
                    }
                }
                contextmenu.ContextMenu = ContextMenu;
                class ContextMenuItem {
                    constructor(key, text, handler, icon, visible, enable) {
                        this.key = key;
                        this.text = text;
                        this.handler = (handler !== undefined) ? handler : $.noop;
                        this.icon = (icon) ? icon : "";
                        this.visible = (visible !== undefined) ? visible : true;
                        this.enable = (enable !== undefined) ? enable : true;
                    }
                }
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
                //dialog* any;
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
            class DirtyChecker {
                constructor(targetViewModelObservable) {
                    this.targetViewModel = targetViewModelObservable;
                    this.initialState = this.getCurrentState();
                }
                getCurrentState() {
                    return ko.toJSON(this.targetViewModel());
                }
                reset() {
                    this.initialState = this.getCurrentState();
                }
                isDirty() {
                    return this.initialState !== this.getCurrentState();
                }
            }
            ui_1.DirtyChecker = DirtyChecker;
            /**
             * Utilities for IgniteUI
             */
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
                            let top = getDisplayContainer(gridId).scrollTop();
                            let visibleRows = getVisibleRows(gridId);
                            for (var i = 0; i < visibleRows.length; i++) {
                                let $row = $(visibleRows[i]);
                                if (visibleRows[i].offsetTop + $row.height() > top) {
                                    return $row;
                                }
                            }
                        }
                        virtual.getFirstVisibleRow = getFirstVisibleRow;
                        function getLastVisibleRow(gridId) {
                            let $displayContainer = getDisplayContainer(gridId);
                            let bottom = $displayContainer.scrollTop() + $displayContainer.height();
                            return getVisibleRows(gridId).filter(function () {
                                return this.offsetTop < bottom;
                            }).last();
                        }
                        virtual.getLastVisibleRow = getLastVisibleRow;
                    })(virtual = grid.virtual || (grid.virtual = {}));
                    var header;
                    (function (header) {
                        function getCell(gridId, columnKey) {
                            let $headers = $('#' + gridId).igGrid("headersTable");
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
/// <reference path="../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_2) {
            var option;
            (function (option_1) {
                class DialogOption {
                    constructor() {
                        this.show = false;
                    }
                }
                class ConfirmDialogOption extends DialogOption {
                    constructor(option) {
                        super();
                        // Default value
                        this.modal = (option && option.modal !== undefined) ? option.modal : true;
                        this.buttons = [];
                        // Add OK Button
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
                }
                option_1.ConfirmDialogOption = ConfirmDialogOption;
                class DelDialogOption extends DialogOption {
                    constructor(option) {
                        super();
                        // Default value
                        this.modal = (option && option.modal !== undefined) ? option.modal : true;
                        this.buttons = [];
                        // Add OK Button
                        this.buttons.push({ text: "はい",
                            "class": "yes ",
                            size: "large",
                            color: "danger",
                            click: function (viewmodel, ui) {
                                viewmodel.okButtonClicked();
                                ui.dialog("close");
                            }
                        });
                        // Add Cancel Button
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
                }
                option_1.DelDialogOption = DelDialogOption;
                class OKDialogOption extends DialogOption {
                    constructor(option) {
                        super();
                        // Default value
                        this.modal = (option && option.modal !== undefined) ? option.modal : true;
                        this.buttons = [];
                        // Add OK Button
                        this.buttons.push({ text: "はい",
                            "class": "yes ",
                            size: "large",
                            color: "proceed",
                            click: function (viewmodel, ui) {
                                viewmodel.okButtonClicked();
                                ui.dialog("close");
                            }
                        });
                        // Add Cancel Button
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
                }
                option_1.OKDialogOption = OKDialogOption;
                class ErrorDialogOption extends DialogOption {
                    constructor(option) {
                        super();
                        // Default value
                        this.headers = (option && option.headers) ? option.headers : [
                            new nts.uk.ui.errors.ErrorHeader("location", "エラー箇所", 115, true),
                            new nts.uk.ui.errors.ErrorHeader("message", "エラー詳細", 250, true)
                        ];
                        this.modal = (option && option.modal !== undefined) ? option.modal : false;
                        this.displayrows = (option && option.displayrows) ? option.displayrows : 10;
                        this.maxrows = (option && option.maxrows) ? option.maxrows : 1000;
                        this.autoclose = (option && option.autoclose !== undefined) ? option.autoclose : true;
                        this.buttons = [];
                        // Add Close Button
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
                }
                option_1.ErrorDialogOption = ErrorDialogOption;
                class ErrorDialogWithTabOption extends ErrorDialogOption {
                    constructor(option) {
                        super();
                        // Default value
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
                        // Add Close Button
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
                }
                option_1.ErrorDialogWithTabOption = ErrorDialogWithTabOption;
                class DialogButton {
                    click(viewmodel, ui) { }
                    ;
                }
            })(option = ui_2.option || (ui_2.option = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var option;
            (function (option_2) {
                class EditorOptionBase {
                }
                class TextEditorOption extends EditorOptionBase {
                    constructor(option) {
                        super();
                        // Default value
                        this.textmode = (option !== undefined && option.textmode !== undefined) ? option.textmode : "text";
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "";
                        this.filldirection = (option !== undefined && option.filldirection !== undefined) ? option.filldirection : "right";
                        this.fillcharacter = (option !== undefined && option.fillcharacter !== undefined) ? option.fillcharacter : "0";
                    }
                }
                option_2.TextEditorOption = TextEditorOption;
                class TimeEditorOption extends EditorOptionBase {
                    constructor(option) {
                        super();
                        // Default value
                        this.inputFormat = (option !== undefined && option.inputFormat !== undefined) ? option.inputFormat : "date";
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "right";
                    }
                }
                option_2.TimeEditorOption = TimeEditorOption;
                class NumberEditorOption extends EditorOptionBase {
                    constructor(option) {
                        super();
                        // Default value
                        this.groupseperator = (option !== undefined && option.groupseperator !== undefined) ? option.groupseperator : ",";
                        this.grouplength = (option !== undefined && option.grouplength !== undefined) ? option.grouplength : 0;
                        this.decimalseperator = (option !== undefined && option.decimalseperator !== undefined) ? option.decimalseperator : ".";
                        this.decimallength = (option !== undefined && option.decimallength !== undefined) ? option.decimallength : 0;
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "right";
                        this.symbolChar = (option !== undefined && option.symbolChar !== undefined) ? option.symbolChar : "";
                        this.symbolPosition = (option !== undefined && option.symbolPosition !== undefined) ? option.symbolPosition : "right";
                    }
                }
                option_2.NumberEditorOption = NumberEditorOption;
                class CurrencyEditorOption extends NumberEditorOption {
                    constructor(option) {
                        super();
                        // Default value
                        this.groupseperator = (option !== undefined && option.groupseperator !== undefined) ? option.groupseperator : ",";
                        this.grouplength = (option !== undefined && option.grouplength !== undefined) ? option.grouplength : 0;
                        this.decimalseperator = (option !== undefined && option.decimalseperator !== undefined) ? option.decimalseperator : ".";
                        this.decimallength = (option !== undefined && option.decimallength !== undefined) ? option.decimallength : 0;
                        this.currencyformat = (option !== undefined && option.currencyformat !== undefined) ? option.currencyformat : "JPY";
                        this.currencyposition = (option !== undefined && option.currencyposition !== undefined)
                            ? option.currencyposition : getCurrencyPosition(this.currencyformat);
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "right";
                    }
                }
                option_2.CurrencyEditorOption = CurrencyEditorOption;
                function getCurrencyPosition(currencyformat) {
                    return currenryPosition[currencyformat] === null ? "right" : currenryPosition[currencyformat];
                }
                class MultilineEditorOption extends EditorOptionBase {
                    constructor(option) {
                        super();
                        // Default value
                        this.resizeable = (option !== undefined && option.resizeable !== undefined) ? option.resizeable : false;
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "";
                    }
                }
                option_2.MultilineEditorOption = MultilineEditorOption;
                var currenryPosition = {
                    "JPY": "left",
                    "USD": "right"
                };
            })(option = ui.option || (ui.option = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../reference.ts"/>
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
                            return _.some($control, c => hasError($(c)));
                        }
                        else {
                            $control.each(function (index) {
                                var $item = $(this);
                                $item = processErrorOnItem($item, message, action);
                            });
                            return $control;
                        }
                    };
                    //function for set and clear error
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
                    let DATA_INSTANCE_NAME = 'nts-popup-panel';
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
                    class NtsPopupPanel {
                        constructor($panel, position) {
                            this.position = position;
                            var parent = $panel.parent();
                            this.$panel = $panel
                                .data(DATA_INSTANCE_NAME, this)
                                .addClass('popup-panel')
                                .appendTo(parent);
                            this.$panel.css("z-index", 100);
                        }
                        show() {
                            this.$panel
                                .css({
                                visibility: 'hidden',
                                display: 'block'
                            })
                                .position(this.position)
                                .css({
                                visibility: 'visible'
                            });
                        }
                        hide() {
                            this.$panel.css({
                                display: 'none'
                            });
                        }
                        destroy() {
                            this.$panel = null;
                        }
                        toggle() {
                            var isDisplaying = this.$panel.css("display");
                            if (isDisplaying === 'none') {
                                this.show();
                            }
                            else {
                                this.hide();
                            }
                        }
                    }
                })(ntsPopup || (ntsPopup = {}));
                var ntsGridList;
                (function (ntsGridList) {
                    let OUTSIDE_AUTO_SCROLL_SPEED = {
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
                    $.fn.ntsGridListFeature = function (feature, action, ...params) {
                        var $grid = $(this);
                        switch (feature) {
                            case 'switch':
                                switch (action) {
                                    case 'setValue':
                                        return setSwitchValue($grid, params);
                                }
                        }
                    };
                    function setSwitchValue($grid, ...params) {
                        let rowId = params[0][0];
                        let columnKey = params[0][1];
                        let selectedValue = params[0][2];
                        let $row = $($grid.igGrid("rowById", rowId));
                        let $parent = $row.find(".ntsControl");
                        //            let currentElement = _.find($parent.find(".nts-switch-button"), function (e){
                        //                return $(e).hasClass('selected');    
                        //            });
                        //            let currentSelect = currentElement === undefined ? undefined : $(currentElement).attr('data-value');
                        let currentSelect = $parent.attr('data-value');
                        if (selectedValue !== currentSelect) {
                            //                let $tr = $parent.closest("tr");   
                            let rowKey = $row.attr("data-id");
                            $parent.find(".nts-switch-button").removeClass("selected");
                            let element = _.find($parent.find(".nts-switch-button"), function (e) {
                                return selectedValue === $(e).attr('data-value');
                            });
                            if (element !== undefined) {
                                $(element).addClass('selected');
                                $parent.attr('data-value', selectedValue);
                                //                    let $scroll = $("#" + $grid.attr("id") + "_scrollContainer")
                                //                    let currentPosition = $scroll[0].scrollTop;
                                $grid.igGridUpdating("setCellValue", rowKey, columnKey, selectedValue);
                                $grid.igGrid("commit");
                                if ($grid.igGrid("hasVerticalScrollbar")) {
                                    let current = $grid.ntsGridList("getSelected");
                                    $grid.igGrid("virtualScrollTo", (typeof current === 'object' ? current.index : current[0].index) + 1);
                                }
                            }
                        }
                    }
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
                            selectedId.forEach(id => $grid.igGridSelection('selectRowById', id));
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
                        // used to auto scrolling when dragged above/below grid)
                        var mousePos = null;
                        $grid.bind('mousedown', function (e) {
                            // グリッド内がマウスダウンされていない場合は処理なしで終了
                            var $container = $grid.closest('.ui-iggrid-scrolldiv');
                            if ($(e.target).closest('.ui-iggrid-table').length === 0) {
                                return;
                            }
                            // current grid size
                            var gridVerticalRange = new uk.util.Range($container.offset().top, $container.offset().top + $container.height());
                            mousePos = {
                                x: e.pageX,
                                y: e.pageY,
                                rowIndex: ui.ig.grid.getRowIndexFrom($(e.target))
                            };
                            // set position to start dragging
                            dragSelectRange.push(mousePos.rowIndex);
                            var $scroller = $('#' + $grid.attr('id') + '_scrollContainer');
                            // auto scroll while mouse is outside grid
                            var timerAutoScroll = setInterval(() => {
                                var distance = gridVerticalRange.distanceFrom(mousePos.y);
                                if (distance === 0) {
                                    return;
                                }
                                var delta = Math.min(distance * OUTSIDE_AUTO_SCROLL_SPEED.RATIO, OUTSIDE_AUTO_SCROLL_SPEED.MAX);
                                var currentScrolls = $scroller.scrollTop();
                                $grid.igGrid('virtualScrollTo', (currentScrolls + delta) + 'px');
                            }, 20);
                            // handle mousemove on window while dragging (unhandle when mouseup)
                            $(window).bind('mousemove.NtsGridListDragging', function (e) {
                                var newPointedRowIndex = ui.ig.grid.getRowIndexFrom($(e.target));
                                // selected range is not changed
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
                            // stop dragging
                            $(window).one('mouseup', function (e) {
                                mousePos = null;
                                dragSelectRange = [];
                                $(window).unbind('mousemove.NtsGridListDragging');
                                clearInterval(timerAutoScroll);
                            });
                        });
                        function updateSelections() {
                            // rowIndex is NaN when mouse is outside grid
                            if (isNaN(mousePos.rowIndex)) {
                                return;
                            }
                            // 以前のドラッグ範囲の選択を一旦解除する
                            // TODO: probably this code has problem of perfomance when select many rows
                            // should process only "differences" instead of "all"
                            for (var i = 0, i_len = dragSelectRange.length; i < i_len; i++) {
                                // http://jp.igniteui.com/help/api/2016.2/ui.iggridselection#methods:deselectRow
                                $grid.igGridSelection('deselectRow', dragSelectRange[i]);
                            }
                            var newDragSelectRange = [];
                            if (dragSelectRange[0] <= mousePos.rowIndex) {
                                for (var j = dragSelectRange[0]; j <= mousePos.rowIndex; j++) {
                                    // http://jp.igniteui.com/help/api/2016.2/ui.iggridselection#methods:selectRow
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
                        $grid.bind('iggridselectioncellselectionchanging', () => {
                        });
                        $grid.bind('iggridselectionrowselectionchanged', () => {
                            $grid.triggerHandler('selectionchanged');
                        });
                        $grid.on('mouseup', () => {
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
                        //            document.getElementById($editor.attr('id')).dispatchEvent(validateEvent);
                        //            $editor.trigger("validate");
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
                            // UserGuide container
                            let $control = $(this);
                            $control.remove();
                            if (!$control.hasClass("ntsUserGuide"))
                                $control.addClass("ntsUserGuide");
                            $($control).appendTo($("body")).show();
                            let target = $control.data('target');
                            let direction = $control.data('direction');
                            // Userguide Information Box
                            $control.children().each(function () {
                                let $box = $(this);
                                let boxDirection = $box.data("direction");
                                $box.addClass("userguide-box caret-" + getReveseDirection(boxDirection) + " caret-overlay");
                            });
                            // Userguide Overlay
                            let $overlay = $("<div class='userguide-overlay'></div>")
                                .addClass("overlay-" + direction)
                                .appendTo($control);
                            $control.hide();
                        });
                        // Hiding when click outside
                        $("html").on("mouseup keypress", { controls: controls }, hideBinding);
                        return controls;
                    }
                    function destroy(controls) {
                        controls.each(function () {
                            $(this).remove();
                        });
                        // Unbind Hiding when click outside
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
                            let $control = $(this);
                            $control.show();
                            let target = $control.data('target');
                            let direction = $control.data('direction');
                            $control.find(".userguide-overlay").each(function (index, elem) {
                                calcOverlayPosition($(elem), target, direction);
                            });
                            $control.children().each(function () {
                                let $box = $(this);
                                let boxTarget = $box.data("target");
                                let boxDirection = $box.data("direction");
                                let boxMargin = ($box.data("margin")) ? $box.data("margin") : "20";
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
                        let result = true;
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
                        let operation = "+";
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
                        //implement later if needed
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
                        let index = 0;
                        index = control.find("#sidebar-area .navigator a.active").closest("li").index();
                        return index;
                    }
                })(ntsSideBar || (ntsSideBar = {}));
            })(jqueryExtentions = ui.jqueryExtentions || (ui.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * CheckBox binding handler
                 */
                class NtsCheckboxBindingHandler {
                    /**
                     * Constructor.
                     */
                    constructor() {
                    }
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var setChecked = data.checked;
                        var textId = data.text;
                        var checkBoxText;
                        // Container
                        var container = $(element);
                        container.addClass("ntsControl").on("click", (e) => {
                            if (container.data("readonly") === true)
                                e.preventDefault();
                        });
                        if (textId) {
                            checkBoxText = textId;
                        }
                        else {
                            checkBoxText = container.text();
                            container.text('');
                        }
                        var $checkBoxLabel = $("<label class='ntsCheckBox'></label>");
                        var $checkBox = $('<input type="checkbox">').on("change", function () {
                            if (typeof setChecked === "function")
                                setChecked($(this).is(":checked"));
                        }).appendTo($checkBoxLabel);
                        var $box = $("<span class='box'></span>").appendTo($checkBoxLabel);
                        if (checkBoxText && checkBoxText.length > 0)
                            var label = $("<span class='label'></span>").text(checkBoxText).appendTo($checkBoxLabel);
                        $checkBoxLabel.appendTo(container);
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var checked = ko.unwrap(data.checked);
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Container
                        var container = $(element);
                        container.data("readonly", readonly);
                        var $checkBox = $(element).find("input[type='checkbox']");
                        // Checked
                        $checkBox.prop("checked", checked);
                        // Enable
                        (enable === true) ? $checkBox.removeAttr("disabled") : $checkBox.attr("disabled", "disabled");
                    }
                }
                /**
                 * MultiCheckbox binding handler
                 */
                class NtsMultiCheckBoxBindingHandler {
                    constructor() {
                    }
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var container = $(element);
                        container.addClass("ntsControl").on("click", (e) => {
                            if (container.data("readonly") === true)
                                e.preventDefault();
                        });
                        let enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        container.data("enable", _.clone(enable));
                    }
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var options = data.options === undefined ? [] : JSON.parse(ko.toJSON(data.options));
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = data.value;
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Container
                        var container = $(element);
                        container.data("readonly", readonly);
                        // Get option or option[optionValue]
                        var getOptionValue = (item) => {
                            return (optionValue === undefined) ? item : item[optionValue];
                        };
                        // Render
                        if (!_.isEqual(container.data("options"), options)) {
                            container.empty();
                            _.forEach(options, (option) => {
                                var checkBoxLabel = $("<label class='ntsCheckBox'></label>");
                                var checkBox = $('<input type="checkbox">').data("value", getOptionValue(option)).on("change", function () {
                                    var self = this;
                                    if ($(self).is(":checked"))
                                        selectedValue.push($(self).data("value"));
                                    else
                                        selectedValue.remove(_.find(selectedValue(), (value) => {
                                            return _.isEqual(value, $(this).data("value"));
                                        }));
                                });
                                let disableOption = option["enable"];
                                if (!nts.uk.util.isNullOrUndefined(disableOption) && (disableOption === false)) {
                                    checkBox.attr("disabled", "disabled");
                                }
                                checkBox.appendTo(checkBoxLabel);
                                var box = $("<span class='box'></span>").appendTo(checkBoxLabel);
                                if (option[optionText] && option[optionText].length > 0)
                                    var label = $("<span class='label'></span>").text(option[optionText]).appendTo(checkBoxLabel);
                                checkBoxLabel.appendTo(container);
                            });
                            // Save a clone
                            container.data("options", _.cloneDeep(options));
                        }
                        // Checked
                        container.find("input[type='checkbox']").prop("checked", function () {
                            return (_.find(selectedValue(), (value) => {
                                return _.isEqual(value, $(this).data("value"));
                            }) !== undefined);
                        });
                        // Enable
                        if (!_.isEqual(container.data("enable"), enable)) {
                            container.data("enable", _.clone(enable));
                            (enable === true) ? container.find("input[type='checkbox']").removeAttr("disabled") : container.find("input[type='checkbox']").attr("disabled", "disabled");
                            _.forEach(data.options(), (option) => {
                                if (typeof option["enable"] === "function") {
                                    option["enable"](enable);
                                }
                                else {
                                    option["enable"] = (enable);
                                }
                            });
                        }
                    }
                }
                ko.bindingHandlers['ntsCheckBox'] = new NtsCheckboxBindingHandler();
                ko.bindingHandlers['ntsMultiCheckBox'] = new NtsMultiCheckBoxBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_3) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * ComboBox binding handler
                 */
                class ComboBoxBindingHandler {
                    /**
                     * Constructor.
                     */
                    constructor() {
                    }
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var self = this;
                        // Get options.
                        var options = ko.unwrap(data.options);
                        // Get options value.
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = ko.unwrap(data.value);
                        var editable = ko.unwrap(data.editable);
                        var enable = ko.unwrap(data.enable);
                        var columns = ko.unwrap(data.columns);
                        // Container.
                        var container = $(element);
                        var comboMode = editable ? 'editable' : 'dropdown';
                        // Default values.
                        var distanceColumns = '     ';
                        var fillCharacter = ' '; // Character used fill to the columns.
                        var maxWidthCharacter = 15;
                        // Check selected code.
                        if (_.find(options, item => item[optionValue] === selectedValue) === undefined && !editable) {
                            selectedValue = options.length > 0 ? options[0][optionValue] : '';
                            data.value(selectedValue);
                        }
                        var haveColumn = columns && columns.length > 0;
                        var isChangeOptions = !_.isEqual(container.data("options"), options);
                        if (isChangeOptions) {
                            container.data("options", options.slice());
                            options = options.map((option) => {
                                var newOptionText = '';
                                // Check muti columns.
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
                                // Add label attr.
                                option['nts-combo-label'] = newOptionText;
                                return option;
                            });
                        }
                        var currentColumnSetting = container.data("columns");
                        var currentComboMode = container.data("comboMode");
                        var isInitCombo = !_.isEqual(currentColumnSetting, columns) || !_.isEqual(currentComboMode, comboMode);
                        if (isInitCombo) {
                            // Delete igCombo.
                            if (container.data("igCombo") != null) {
                                container.igCombo('destroy');
                                container.removeClass('ui-state-disabled');
                            }
                            // Set attribute for multi column.
                            var itemTemplate = undefined;
                            if (haveColumn) {
                                itemTemplate = '<div class="nts-combo-item">';
                                _.forEach(columns, function (item, i) {
                                    // Set item template.
                                    itemTemplate += '<div class="nts-column nts-combo-column-' + i + '">${' + item.prop + '}</div>';
                                });
                                itemTemplate += '</div>';
                            }
                            // Create igCombo.
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
                        // Set width for multi columns.
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
                    }
                }
                ko.bindingHandlers['ntsComboBox'] = new ComboBoxBindingHandler();
            })(koExtentions = ui_3.koExtentions || (ui_3.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * Dialog binding handler
                 */
                class NtsDialogBindingHandler {
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
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
                            // Create Buttons
                            var dialogbuttons = [];
                            for (let button of buttons) {
                                dialogbuttons.push({
                                    text: ko.unwrap(button.text),
                                    "class": ko.unwrap(button.class) + ko.unwrap(button.size) + " " + ko.unwrap(button.color),
                                    click: function () { button.click(bindingContext.$data, $dialog); }
                                });
                            }
                            // Create dialog
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
                            // Destroy dialog
                            if ($('#ntsDialog').dialog("instance") != null)
                                $('#ntsDialog').dialog("destroy");
                            $('#ntsDialog').remove();
                        }
                    }
                }
                /**
                 * Error Dialog binding handler
                 */
                class NtsErrorDialogBindingHandler {
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var option = ko.unwrap(data.option);
                        var title = ko.unwrap(data.title);
                        var headers = ko.unwrap(option.headers);
                        var modal = ko.unwrap(option.modal);
                        var show = ko.unwrap(option.show);
                        var buttons = ko.unwrap(option.buttons);
                        var $dialog = $("<div id='ntsErrorDialog'></div>");
                        $('body').append($dialog);
                        // Create Buttons
                        var dialogbuttons = [];
                        for (let button of buttons) {
                            dialogbuttons.push({
                                text: ko.unwrap(button.text),
                                "class": ko.unwrap(button.class) + ko.unwrap(button.size) + " " + ko.unwrap(button.color),
                                click: function () { button.click(bindingContext.$data, $dialog); }
                            });
                        }
                        // Calculate width
                        var dialogWidth = 40 + 35 + 17;
                        headers.forEach(function (header, index) {
                            if (ko.unwrap(header.visible)) {
                                dialogWidth += ko.unwrap(header.width);
                            }
                        });
                        // Create dialog
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
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
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
                            // Create Error Table
                            var $errorboard = $("<div id='error-board'></div>");
                            var $errortable = $("<table></table>");
                            // Header
                            var $header = $("<thead><tr></tr></thead>");
                            $header.find("tr").append("<th style='width: 35px'></th>");
                            headers.forEach(function (header, index) {
                                if (ko.unwrap(header.visible)) {
                                    let $headerElement = $("<th>" + ko.unwrap(header.text) + "</th>").width(ko.unwrap(header.width));
                                    $header.find("tr").append($headerElement);
                                }
                            });
                            $errortable.append($header);
                            // Body
                            var $body = $("<tbody></tbody>");
                            errors.forEach(function (error, index) {
                                if (index < maxrows) {
                                    // Row
                                    let $row = $("<tr></tr>");
                                    $row.append("<td style='width:35px'>" + (index + 1) + "</td>");
                                    headers.forEach(function (header) {
                                        if (ko.unwrap(header.visible))
                                            if (error.hasOwnProperty(ko.unwrap(header.name))) {
                                                // TD
                                                let $column = $("<td>" + error[ko.unwrap(header.name)] + "</td>").width(ko.unwrap(header.width));
                                                $row.append($column);
                                            }
                                    });
                                    $body.append($row);
                                }
                            });
                            $errortable.append($body);
                            $errorboard.append($errortable);
                            // Errors over maxrows message
                            var $message = $("<div></div>");
                            if (errors.length > maxrows)
                                $message.text("Showing " + maxrows + " in total " + errors.length + " errors");
                            $dialog.html("");
                            $dialog.append($errorboard).append($message);
                            // Calculate body height base on displayrow
                            $body.height(Math.min(displayrows, errors.length) * $(">:first-child", $body).outerHeight() + 1);
                        }
                        else {
                            $dialog.dialog("close");
                        }
                    }
                }
                ko.bindingHandlers['ntsDialog'] = new NtsDialogBindingHandler();
                ko.bindingHandlers['ntsErrorDialog'] = new NtsErrorDialogBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var validation = nts.uk.ui.validation;
                /**
                 * BaseEditor Processor
                 */
                class EditorProcessor {
                    init($input, data) {
                        var value = data.value;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        var immediate = ko.unwrap(data.immediate !== undefined ? data.immediate : 'false');
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var valueUpdate = (immediate === true) ? 'input' : 'change';
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : {};
                        this.editorOption = $.extend(this.getDefaultOption(), option);
                        var characterWidth = 9;
                        if (constraint && constraint.maxLength && !$input.is("textarea")) {
                            var autoWidth = constraint.maxLength * characterWidth;
                            $input.width(autoWidth);
                        }
                        $input.addClass('nts-editor nts-input');
                        $input.wrap("<span class= 'nts-editor-wrapped ntsControl'/>");
                        let validator = this.getValidator(data);
                        $input.on(valueUpdate, (e) => {
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
                        // Format on blur
                        $input.blur(() => {
                            if (!readonly) {
                                var formatter = this.getFormatter(data);
                                var newText = $input.val();
                                var result = validator.validate(newText);
                                if (result.isValid) {
                                    $input.val(formatter.format(result.parsedValue));
                                }
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
                    }
                    update($input, data) {
                        var value = data.value;
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : {};
                        this.editorOption = $.extend(this.getDefaultOption(), option);
                        var placeholder = this.editorOption.placeholder;
                        var textalign = this.editorOption.textalign;
                        var width = this.editorOption.width;
                        // Properties
                        (enable !== false) ? $input.removeAttr('disabled') : $input.attr('disabled', 'disabled');
                        (readonly === false) ? $input.removeAttr('readonly') : $input.attr('readonly', 'readonly');
                        $input.attr('placeholder', placeholder);
                        $input.css('text-align', textalign);
                        if (width.trim() != "")
                            $input.width(width);
                        // Format value
                        var formatted = $input.ntsError('hasError') ? value() : this.getFormatter(data).format(value());
                        $input.val(formatted);
                    }
                    getDefaultOption() {
                        return {};
                    }
                    getFormatter(data) {
                        return new uk.format.NoFormatter();
                    }
                    getValidator(data) {
                        return new validation.NoValidator();
                    }
                }
                /**
                 * TextEditor Processor
                 */
                class TextEditorProcessor extends EditorProcessor {
                    init($input, data) {
                        var value = data.value;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var characterWidth = 9;
                        if (constraint && constraint.maxLength && !$input.is("textarea")) {
                            var autoWidth = constraint.maxLength * characterWidth;
                            $input.width(autoWidth);
                        }
                        $input.addClass('nts-editor nts-input');
                        $input.wrap("<span class= 'nts-editor-wrapped ntsControl'/>");
                        let validator = this.getValidator(data);
                        $input.on("keyup", (e) => {
                            if (!readonly) {
                                var newText = $input.val();
                                var result = validator.validate(newText);
                                $input.ntsError('clear');
                                if (!result.isValid) {
                                    $input.ntsError('set', result.errorMessage);
                                }
                            }
                        });
                        $input.on("blur", (e) => {
                            if (!readonly) {
                                var newText = $input.val();
                                var result = validator.validate(newText, { isCheckExpression: true });
                                $input.ntsError('clear');
                                if (result.isValid) {
                                    if (value() === result.parsedValue) {
                                        $input.val(result.parsedValue);
                                    }
                                    else {
                                        value(result.parsedValue);
                                    }
                                }
                                else {
                                    $input.ntsError('set', result.errorMessage);
                                    value(newText);
                                }
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
                    }
                    update($input, data) {
                        super.update($input, data);
                        var textmode = this.editorOption.textmode;
                        $input.attr('type', textmode);
                    }
                    getDefaultOption() {
                        return new nts.uk.ui.option.TextEditorOption();
                    }
                    getFormatter(data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        return new uk.text.StringFormatter({ constraintName: constraintName, constraint: constraint, editorOption: this.editorOption });
                    }
                    getValidator(data) {
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        return new validation.StringValidator(constraintName, { required: required });
                    }
                }
                /**
                 * MultilineEditor Processor
                 */
                class MultilineEditorProcessor extends EditorProcessor {
                    update($input, data) {
                        super.update($input, data);
                        var resizeable = this.editorOption.resizeable;
                        $input.css('resize', (resizeable) ? "both" : "none");
                    }
                    getDefaultOption() {
                        return new ui.option.MultilineEditorOption();
                    }
                    getFormatter(data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        return new uk.text.StringFormatter({ constraintName: constraintName, constraint: constraint, editorOption: this.editorOption });
                    }
                    getValidator(data) {
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        return new validation.StringValidator(constraintName, { required: required });
                    }
                }
                /**
                 * NumberEditor Processor
                 */
                class NumberEditorProcessor extends EditorProcessor {
                    init($input, data) {
                        super.init($input, data);
                        $input.focus(() => {
                            var selectionType = document.getSelection().type;
                            // Remove separator (comma)
                            $input.val(data.value());
                            // If focusing is caused by Tab key, select text
                            // this code is needed because removing separator deselects.
                            if (selectionType === 'Range') {
                                $input.select();
                            }
                        });
                    }
                    update($input, data) {
                        super.update($input, data);
                        var $parent = $input.parent();
                        var width = this.editorOption.width;
                        var parentTag = $parent.parent().prop("tagName").toLowerCase();
                        if (parentTag === "td" || parentTag === "th" || parentTag === "a" || width === "100%") {
                            $parent.css({ 'width': '100%' });
                        }
                        $input.css("box-sizing", "border-box");
                        if (width.trim() != "")
                            $input.width(width);
                        if (this.editorOption.currencyformat !== undefined && this.editorOption.currencyformat !== null) {
                            $parent.addClass("symbol").addClass(this.editorOption.currencyposition === 'left' ? 'symbol-left' : 'symbol-right');
                            var format = this.editorOption.currencyformat === "JPY" ? "\u00A5" : '$';
                            $parent.attr("data-content", format);
                        }
                        else if (this.editorOption.symbolChar !== undefined && this.editorOption.symbolChar !== "" && this.editorOption.symbolPosition !== undefined) {
                            $parent.addClass("symbol").addClass(this.editorOption.symbolPosition === 'right' ? 'symbol-right' : 'symbol-left');
                            $parent.attr("data-content", this.editorOption.symbolChar);
                        }
                    }
                    getDefaultOption() {
                        return new nts.uk.ui.option.NumberEditorOption();
                    }
                    getFormatter(data) {
                        return new uk.text.NumberFormatter({ option: this.editorOption });
                    }
                    getValidator(data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        return new validation.NumberValidator(constraintName, this.editorOption);
                    }
                }
                /**
                 * TimeEditor Processor
                 */
                class TimeEditorProcessor extends EditorProcessor {
                    update($input, data) {
                        super.update($input, data);
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var width = option.width;
                        var parent = $input.parent();
                        var parentTag = parent.parent().prop("tagName").toLowerCase();
                        if (parentTag === "td" || parentTag === "th" || parentTag === "a" || width === "100%") {
                            parent.css({ 'width': '100%' });
                        }
                    }
                    getDefaultOption() {
                        return new nts.uk.ui.option.TimeEditorOption();
                    }
                    getFormatter(data) {
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var inputFormat = (data.inputFormat !== undefined) ? ko.unwrap(data.inputFormat) : option.inputFormat;
                        return new uk.text.TimeFormatter({ inputFormat: inputFormat });
                    }
                    getValidator(data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var inputFormat = (data.inputFormat !== undefined) ? ko.unwrap(data.inputFormat) : option.inputFormat;
                        return new validation.TimeValidator(constraintName, { required: required, outputFormat: inputFormat });
                    }
                }
                /**
                 * Base Editor
                 */
                class NtsEditorBindingHandler {
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new EditorProcessor().init($(element), valueAccessor());
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new EditorProcessor().update($(element), valueAccessor());
                    }
                }
                /**
                 * TextEditor
                 */
                class NtsTextEditorBindingHandler extends NtsEditorBindingHandler {
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TextEditorProcessor().init($(element), valueAccessor());
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TextEditorProcessor().update($(element), valueAccessor());
                    }
                }
                /**
                 * NumberEditor
                 */
                class NtsNumberEditorBindingHandler {
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new NumberEditorProcessor().init($(element), valueAccessor());
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new NumberEditorProcessor().update($(element), valueAccessor());
                    }
                }
                /**
                 * TimeEditor
                 */
                class NtsTimeEditorBindingHandler {
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TimeEditorProcessor().init($(element), valueAccessor());
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        new TimeEditorProcessor().update($(element), valueAccessor());
                    }
                }
                /**
                 * MultilineEditor
                 */
                class NtsMultilineEditorBindingHandler extends NtsEditorBindingHandler {
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new MultilineEditorProcessor().init($(element), valueAccessor());
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new MultilineEditorProcessor().update($(element), valueAccessor());
                    }
                }
                ko.bindingHandlers['ntsTextEditor'] = new NtsTextEditorBindingHandler();
                ko.bindingHandlers['ntsNumberEditor'] = new NtsNumberEditorBindingHandler();
                ko.bindingHandlers['ntsTimeEditor'] = new NtsTimeEditorBindingHandler();
                ko.bindingHandlers['ntsMultilineEditor'] = new NtsMultilineEditorBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * FormLabel
                 */
                class NtsFormLabelBindingHandler {
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
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
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    }
                    static buildConstraintText(primitiveValues) {
                        if (!Array.isArray(primitiveValues))
                            primitiveValues = [primitiveValues];
                        let constraintText = "";
                        _.forEach(primitiveValues, function (primitiveValue) {
                            let constraint = __viewContext.primitiveValueConstraints[primitiveValue];
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
                    }
                }
                ko.bindingHandlers['ntsFormLabel'] = new NtsFormLabelBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_4) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * GridList binding handler
                 */
                class NtsGridListBindingHandler {
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var HEADER_HEIGHT = 27;
                        var $grid = $(element);
                        let gridId = $grid.attr('id');
                        if (nts.uk.util.isNullOrUndefined(gridId)) {
                            throw new Error('the element NtsGridList must have id attribute.');
                        }
                        var data = valueAccessor();
                        var optionsValue = data.primaryKey !== undefined ? data.primaryKey : data.optionsValue;
                        var options = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
                        var deleteOptions = ko.unwrap(data.deleteOptions);
                        var observableColumns = ko.unwrap(data.columns);
                        var showNumbering = ko.unwrap(data.showNumbering) === true ? true : false;
                        var features = [];
                        features.push({ name: 'Selection', multipleSelection: data.multiple });
                        features.push({ name: 'Sorting', type: 'local' });
                        if (data.multiple) {
                            features.push({ name: 'RowSelectors', enableCheckBoxes: data.multiple, enableRowNumbering: showNumbering });
                        }
                        var gridFeatures = ko.unwrap(data.features);
                        var iggridColumns = _.map(observableColumns, c => {
                            c["key"] = c["key"] === undefined ? c["prop"] : c["key"];
                            c["dataType"] = 'string';
                            if (c["controlType"] === "switch") {
                                let switchF = _.find(gridFeatures, function (s) {
                                    return s["name"] === "Switch";
                                });
                                if (!uk.util.isNullOrUndefined(switchF)) {
                                    features.push({ name: 'Updating', enableAddRow: false, enableDeleteRow: false, editMode: 'none' });
                                    let switchOptions = ko.unwrap(switchF.options);
                                    let switchValue = switchF.optionsValue;
                                    let switchText = switchF.optionsText;
                                    c["formatter"] = function createButton(val, row) {
                                        let result = $('<div class="ntsControl"/>');
                                        _.forEach(switchOptions, function (opt) {
                                            let value = opt[switchValue];
                                            let text = opt[switchText];
                                            let btn = $('<button>').text(text)
                                                .addClass('nts-switch-button');
                                            btn.attr('data-value', value);
                                            if (val == value) {
                                                btn.addClass('selected');
                                            }
                                            btn.appendTo(result);
                                        }, result.attr("data-value", val));
                                        return result[0].outerHTML;
                                    };
                                    $grid.on("click", ".nts-switch-button", function (evt, ui) {
                                        let $element = $(this);
                                        let selectedValue = $element.attr('data-value');
                                        let $tr = $element.closest("tr");
                                        $grid.ntsGridListFeature('switch', 'setValue', $tr.attr("data-id"), c["key"], selectedValue);
                                    });
                                }
                            }
                            return c;
                        });
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
                        $grid.bind('selectionchanged', () => {
                            if (data.multiple) {
                                let selected = $grid.ntsGridList('getSelected');
                                if (selected) {
                                    data.value(_.map(selected, s => s.id));
                                }
                                else {
                                    data.value([]);
                                }
                            }
                            else {
                                let selected = $grid.ntsGridList('getSelected');
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
                    }
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var $grid = $(element);
                        var data = valueAccessor();
                        var optionsValue = data.primaryKey !== undefined ? data.primaryKey : data.optionsValue;
                        var currentSource = $grid.igGrid('option', 'dataSource');
                        var sources = (data.dataSource !== undefined ? data.dataSource() : data.options());
                        if (!_.isEqual(currentSource, sources)) {
                            let currentSources = sources.slice();
                            var observableColumns = _.filter(ko.unwrap(data.columns), function (c) {
                                c["key"] = c["key"] === undefined ? c["prop"] : c["key"];
                                return c["isDateColumn"] !== undefined && c["isDateColumn"] !== null && c["isDateColumn"] === true;
                            });
                            _.forEach(currentSources, function (s) {
                                _.forEach(observableColumns, function (c) {
                                    let key = c["key"] === undefined ? c["prop"] : c["key"];
                                    s[key] = moment(s[key]).format(c["format"]);
                                });
                                //                    currentSources.push(s);
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
                    }
                }
                ko.bindingHandlers['ntsGridList'] = new NtsGridListBindingHandler();
            })(koExtentions = ui_4.koExtentions || (ui_4.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_5) {
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
                            // Add selected value.
                            var data = [];
                            $("li.ui-selected", $target).each(function (index, opt) {
                                data[index] = $(opt).data('value');
                            });
                            $target.data('value', data);
                            // fire event change.
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
                /**
                 * ListBox binding handler
                 */
                class ListBoxBindingHandler {
                    /**
                     * Constructor.
                     */
                    constructor() {
                    }
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        // Get options
                        var options = ko.unwrap(data.options);
                        // Get options value
                        var optionValue = ko.unwrap(data.primaryKey === undefined ? data.optionsValue : data.primaryKey);
                        var optionText = ko.unwrap(data.primaryText === undefined ? data.optionsText : data.primaryText);
                        var selectedValue = ko.unwrap(data.value);
                        var isMultiSelect = ko.unwrap(data.multiple);
                        var enable = ko.unwrap(data.enable);
                        var required = ko.unwrap(data.required) || false;
                        // Container
                        var container = $(element);
                        container.addClass('ntsListBox ntsControl').data('required', required);
                        container.data("options", options.slice());
                        container.data("init", true);
                        container.data("enable", enable);
                        // Create select
                        container.append('<ol class="nts-list-box"></ol>');
                        var selectListBoxContainer = container.find('.nts-list-box');
                        // Create changing event.
                        var changeEvent = new CustomEvent("selectionChange", {
                            detail: {},
                        });
                        container.data("selectionChange", changeEvent);
                        if (isMultiSelect) {
                            // Bind selectable.
                            bindMultible(selectListBoxContainer, changeEvent);
                        }
                        else {
                            bindSingleSelectListBox(selectListBoxContainer, changeEvent);
                        }
                        // Fire event.
                        container.on('selectionChange', (function (e) {
                            // Check is multi-selection.
                            var changingEvent = new CustomEvent("selectionChanging", {
                                detail: itemsSelected,
                                bubbles: true,
                                cancelable: true,
                            });
                            var isMulti = container.data("multiple");
                            var itemsSelected = selectListBoxContainer.data('value');
                            // Dispatch/Trigger/Fire the event => use event.detai to get selected value.
                            document.getElementById(container.attr('id')).dispatchEvent(changingEvent);
                            if (changingEvent.returnValue !== undefined && !changingEvent.returnValue) {
                                // revert select.
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
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        // Get options.
                        var options = ko.unwrap(data.options);
                        // Get options value.
                        var optionValue = ko.unwrap(data.primaryKey === undefined ? data.optionsValue : data.primaryKey);
                        var optionText = ko.unwrap(data.primaryText === undefined ? data.optionsText : data.primaryText);
                        var selectedValue = ko.unwrap(data.value);
                        var isMultiSelect = ko.unwrap(data.multiple);
                        var enable = ko.unwrap(data.enable);
                        var columns = data.columns;
                        var rows = data.rows;
                        // Container.
                        var container = $(element);
                        var selectListBoxContainer = container.find('.nts-list-box');
                        var maxWidthCharacter = 15;
                        var required = ko.unwrap(data.required) || false;
                        container.data('required', required);
                        var getOptionValue = item => {
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
                                    let array = [];
                                    array.push(selectedValue);
                                    data.value(array);
                                }
                            }
                            container.data("multiple", isMultiSelect);
                        }
                        if (!_.isEqual(originalOptions, options) || init) {
                            if (!init) {
                                //remove children
                                selectListBoxContainer.empty();
                            }
                            // Append options.
                            options.forEach((item, idx) => {
                                // Check option is Selected
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
                                    // Add option
                                    var selectedClass = isSelected ? 'ui-selected' : '';
                                    var itemTemplate = '';
                                    if (columns && columns.length > 0) {
                                        columns.forEach((col, cIdx) => {
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
                            // Set width for multi columns
                            if (columns && columns.length > 0) {
                                var totalWidth = 0;
                                columns.forEach((item, cIdx) => {
                                    container.find('.nts-list-box-column-' + cIdx).width(item.length * maxWidthCharacter + 20);
                                    totalWidth += item.length * maxWidthCharacter + 20;
                                });
                                totalWidth += padding * (columns.length + 1); // + 50;
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
                        // Set value
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
                            // Check enable
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
                                //selectListBoxContainer.selectable("disable");;
                                selectListBoxContainer.off("click", "li");
                                container.addClass('disabled');
                            }
                            else {
                                //selectListBoxContainer.selectable("enable");
                                if (container.hasClass("disabled")) {
                                    selectListBoxContainer.on("click", "li", { event: container.data("selectionChange") }, selectOnListBox);
                                    container.removeClass('disabled');
                                }
                            }
                        }
                        container.data("enable", enable);
                    }
                }
                ko.bindingHandlers['ntsListBox'] = new ListBoxBindingHandler();
            })(koExtentions = ui_5.koExtentions || (ui_5.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * RadioBoxGroup binding handler
                 */
                class NtsRadioBoxGroupBindingHandler {
                    constructor() {
                    }
                    /**
                     * Init
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        $(element).addClass("ntsControl");
                        let enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        $(element).data("enable", _.clone(enable));
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var options = data.options === undefined ? [] : JSON.parse(ko.toJSON(data.options));
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = data.value;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Container
                        var container = $(element);
                        var getOptionValue = (item) => {
                            return (optionValue === undefined) ? item : item[optionValue];
                        };
                        // Render
                        if (!_.isEqual(container.data("options"), options)) {
                            var radioName = uk.util.randomId();
                            container.empty();
                            _.forEach(options, (option) => {
                                var radioBoxLabel = $("<label class='ntsRadioBox'></label>");
                                var radioBox = $('<input type="radio">').attr("name", radioName).data("value", getOptionValue(option)).on("change", function () {
                                    var self = this;
                                    if ($(self).is(":checked"))
                                        selectedValue($(self).data("value"));
                                });
                                let disableOption = option["enable"];
                                if (!nts.uk.util.isNullOrUndefined(disableOption) && (disableOption === false)) {
                                    radioBox.attr("disabled", "disabled");
                                }
                                radioBox.appendTo(radioBoxLabel);
                                var box = $("<span class='box'></span>").appendTo(radioBoxLabel);
                                if (option[optionText] && option[optionText].length > 0)
                                    var label = $("<span class='label'></span>").text(option[optionText]).appendTo(radioBoxLabel);
                                radioBoxLabel.appendTo(container);
                            });
                            // Save a clone
                            container.data("options", _.cloneDeep(options));
                        }
                        // Checked
                        var checkedRadio = _.find(container.find("input[type='radio']"), (item) => {
                            return _.isEqual($(item).data("value"), selectedValue());
                        });
                        if (checkedRadio !== undefined)
                            $(checkedRadio).prop("checked", true);
                        // Enable
                        if (!_.isEqual(container.data("enable"), enable)) {
                            container.data("enable", _.clone(enable));
                            (enable === true) ? container.find("input[type='radio']").removeAttr("disabled") : container.find("input[type='radio']").attr("disabled", "disabled");
                            _.forEach(data.options(), (option) => {
                                if (typeof option["enable"] === "function") {
                                    option["enable"](enable);
                                }
                                else {
                                    option["enable"] = (enable);
                                }
                            });
                        }
                    }
                }
                ko.bindingHandlers['ntsRadioBoxGroup'] = new NtsRadioBoxGroupBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                /**
                * SearchBox Binding Handler
                */
                var filteredArray = function (array, searchTerm, fields, childField) {
                    //if items is empty return empty array
                    if (!array) {
                        return [];
                    }
                    if (!(searchTerm instanceof String)) {
                        searchTerm = "" + searchTerm;
                    }
                    var flatArr = nts.uk.util.flatArray(array, childField);
                    var filter = searchTerm.toLowerCase();
                    //if filter is empty return all the items
                    if (!filter) {
                        return flatArr;
                    }
                    //filter data
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
                class NtsSearchBoxBindingHandler {
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
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
                    }
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
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
                    }
                }
                ko.bindingHandlers['ntsSearchBox'] = new NtsSearchBoxBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_6) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * SwapList binding handler
                 */
                class NtsSwapListBindingHandler {
                    /**
                     * Constructor.
                     */
                    constructor() {
                    }
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
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
                        //            var selectedValues = ko.unwrap(data.value);
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
                        var gridWidth = _.sumBy(columns(), c => {
                            return c.width;
                        });
                        var iggridColumns = _.map(columns(), c => {
                            c["key"] = c.key === undefined ? c.prop : c.key;
                            c["dataType"] = 'string';
                            return c;
                        });
                        var gridHeight = (height - 20);
                        var grid1Id = "#" + elementId + "-grid1";
                        var grid2Id = "#" + elementId + "-grid2";
                        if (!uk.util.isNullOrUndefined(showSearchBox) && (showSearchBox.showLeft || showSearchBox.showEright)) {
                            var initSearchArea = function ($SearchArea, targetId) {
                                $SearchArea.append("<div class='ntsSearchTextContainer'/>")
                                    .append("<div class='ntsSearchButtonContainer'/>");
                                $SearchArea.find(".ntsSearchTextContainer")
                                    .append("<input id = " + searchAreaId + "-input" + " class = 'ntsSearchInput ntsSearchBox'/>");
                                $SearchArea.find(".ntsSearchButtonContainer")
                                    .append("<button id = " + searchAreaId + "-btn" + " class='ntsSearchButton search-btn caret-bottom'/>");
                                $SearchArea.find(".ntsSearchInput").attr("placeholder", "コード・名称で検索・・・");
                                $SearchArea.find(".ntsSearchButton").text("検索");
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
                        var criterion = _.map(columns(), c => { return c.key === undefined ? c.prop : c.key; });
                        var swapParts = new Array();
                        swapParts.push(new GridSwapPart().listControl($grid1)
                            .searchControl($swap.find(".ntsSwapSearchLeft").find(".ntsSearchButton"))
                            .searchBox($swap.find(".ntsSwapSearchLeft").find(".ntsSearchBox"))
                            .setDataSource(originalSource)
                            .setSearchCriterion(data.searchCriterion || criterion)
                            .setSearchMode(data.searchMode || "highlight")
                            .setColumns(columns())
                            .setPrimaryKey(primaryKey)
                            .setInnerDrop((data.innerDrag && data.innerDrag.left !== undefined) ? data.innerDrag.left : true)
                            .setOuterDrop((data.outerDrag && data.outerDrag.left !== undefined) ? data.outerDrag.left : true)
                            .build());
                        swapParts.push(new GridSwapPart().listControl($grid2)
                            .searchControl($swap.find(".ntsSwapSearchRight").find(".ntsSearchButton"))
                            .searchBox($swap.find(".ntsSwapSearchRight").find(".ntsSearchBox"))
                            .setDataSource(data.value())
                            .setSearchCriterion(data.searchCriterion || criterion)
                            .setSearchMode(data.searchMode || "highlight")
                            .setColumns(columns())
                            .setPrimaryKey(primaryKey)
                            .setInnerDrop((data.innerDrag && data.innerDrag.right !== undefined) ? data.innerDrag.right : true)
                            .setOuterDrop((data.outerDrag && data.outerDrag.right !== undefined) ? data.outerDrag.right : true)
                            .build());
                        this.swapper = new SwapHandler().setModel(new GridSwapList($swap, swapParts));
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
                        if (data.draggable === true)
                            this.swapper.enableDragDrop();
                        $grid2.closest('.ui-iggrid')
                            .addClass('nts-gridlist')
                            .height(gridHeight);
                        $grid2.ntsGridList('setupSelecting');
                        var $moveArea = $swap.find("#" + elementId + "-move-data")
                            .append("<button class = 'move-button move-forward'><i class='icon icon-button-arrow-right'></i></button>")
                            .append("<button class = 'move-button move-back'><i class='icon icon-button-arrow-left'></i></button>");
                        var $moveForward = $moveArea.find(".move-forward");
                        var $moveBack = $moveArea.find(".move-back");
                        var swapper = this.swapper;
                        $moveForward.click(function () {
                            swapper.Model.move(true, data.value);
                        });
                        $moveBack.click(function () {
                            swapper.Model.move(false, data.value);
                        });
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var elementId = this.swapper.Model.$container.attr('id');
                        var primaryKey = this.swapper.Model.swapParts[0].primaryKey;
                        if (nts.uk.util.isNullOrUndefined(elementId)) {
                            throw new Error('the element NtsSwapList must have id attribute.');
                        }
                        var $grid1 = this.swapper.Model.swapParts[0].$listControl;
                        var $grid2 = this.swapper.Model.swapParts[1].$listControl;
                        var currentSource = $grid1.igGrid('option', 'dataSource');
                        var currentSelectedList = $grid2.igGrid('option', 'dataSource');
                        var newSources = (data.dataSource !== undefined ? data.dataSource() : data.options());
                        var newSelectedList = data.value();
                        _.remove(newSources, function (item) {
                            return _.find(newSelectedList, function (selected) {
                                return selected[primaryKey] === item[primaryKey];
                            }) !== undefined;
                        });
                        if (!_.isEqual(currentSource, newSources)) {
                            this.swapper.Model.swapParts[0].bindData(newSources.slice());
                        }
                        if (!_.isEqual(currentSelectedList, newSelectedList)) {
                            this.swapper.Model.swapParts[1].bindData(newSelectedList.slice());
                        }
                    }
                    /**
                     * Share swapper b/w init and update
                     */
                    makeBindings() {
                        var handler = this;
                        return {
                            init: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
                                var newHandler = Object.create(handler);
                                if (handler.init) {
                                    handler.init.call(newHandler, element, valueAccessor, allBindings, viewModel, bindingContext);
                                }
                                if (handler.update) {
                                    ko.computed({
                                        read: handler.update.bind(newHandler, element, valueAccessor, allBindings, viewModel, bindingContext),
                                        disposeWhenNodeIsRemoved: element
                                    });
                                }
                            }
                        };
                    }
                }
                ko.bindingHandlers['ntsSwapList'] = new NtsSwapListBindingHandler().makeBindings();
                class SwapHandler {
                    constructor() {
                    }
                    setModel(model) {
                        this.model = model;
                        return this;
                    }
                    get Model() {
                        return this.model;
                    }
                    handle(parts) {
                        var self = this;
                        var model = this.model;
                        for (var id in parts) {
                            var options = {
                                items: "tbody > tr",
                                containment: this.model.$container,
                                cursor: "move",
                                connectWith: ".ntsSwapGrid",
                                placeholder: "ui-state-highlight",
                                helper: this._createHelper,
                                appendTo: this.model.$container,
                                start: function (evt, ui) {
                                    model.transportBuilder.at(model.sender(ui));
                                },
                                beforeStop: function (evt, ui) {
                                    self._beforeStop.call(this, model, evt, ui);
                                },
                                update: function (evt, ui) {
                                    self._update.call(this, model, evt, ui);
                                }
                            };
                            this.model.swapParts[parts[id]].initDraggable(options);
                        }
                    }
                    _createHelper(evt, ui) {
                        var selectedRowElms = $(evt.currentTarget).igGrid("selectedRows");
                        // Set the orders same as on grid
                        selectedRowElms.sort(function (one, two) {
                            return one.index - two.index;
                        });
                        var $helper;
                        if (selectedRowElms.length > 1) {
                            $helper = $("<div><table><tbody></tbody></table></div>").addClass("select-drag");
                            var rowId = ui.data("row-idx");
                            var selectedItems = selectedRowElms.map(function (elm) { return elm.element; });
                            var height = 0;
                            $.each(selectedItems, function () {
                                $helper.find("tbody").append($(this).clone());
                                height += $(this).outerHeight();
                                if (rowId !== this.data("row-idx"))
                                    $(this).hide();
                            });
                            $helper.height(height);
                            $helper.find("tr").first().children().each(function (idx) {
                                $(this).width(ui.children().eq(idx).width());
                            });
                        }
                        else {
                            $helper = ui.clone();
                            $helper.children().each(function (idx) {
                                $(this).width(ui.children().eq(idx).width());
                            });
                        }
                        return $helper[0];
                    }
                    _beforeStop(model, evt, ui) {
                        var partId = model.transportBuilder.startAt === "first" ? 0 : 1;
                        var destPartId = model.receiver(ui) === "first" ? 0 : 1;
                        model.transportBuilder.toAdjacent(model.neighbor(ui)).target(model.target(ui));
                        // In case of muliple selections
                        if (ui.helper.hasClass("select-drag") === true) {
                            var rowsInHelper = ui.helper.find("tr");
                            var rows = rowsInHelper.toArray();
                            if (model.transportBuilder.startAt === model.receiver(ui)
                                || (model.swapParts[partId].outerDrop === false
                                    && model.transportBuilder.startAt !== model.receiver(ui))) {
                                $(this).sortable("cancel");
                                for (var idx in rows) {
                                    model.swapParts[partId].$listControl.find("tbody").children()
                                        .eq($(rows[idx]).data("row-idx")).show();
                                }
                                return;
                            }
                            else {
                                var standardIdx = ui.placeholder.index();
                                var targetIdx = ui.item.data("row-idx");
                                var rowsBefore = new Array();
                                var rowsAfter = new Array();
                                for (var id in rows) {
                                    if ($(rows[id]).data("row-idx") < targetIdx)
                                        rowsBefore.push(rows[id]);
                                    else if ($(rows[id]).data("row-idx") > targetIdx)
                                        rowsAfter.push(rows[id]);
                                }
                                model.swapParts[destPartId].$listControl.find("tbody").children()
                                    .eq(standardIdx - 1).before(rowsBefore).after(rowsAfter);
                                // Remove rows in source
                                var sourceRows = model.swapParts[partId].$listControl.find("tbody").children();
                                for (var index = rowsAfter.length - 1; index >= 0; index--) {
                                    if ($(rows[index]).data("row-idx") === targetIdx)
                                        continue;
                                    sourceRows.eq($(rowsAfter[index]).data("row-idx") - 1).remove();
                                }
                                for (var val in rowsBefore) {
                                    sourceRows.eq($(rowsBefore[val]).data("row-idx")).remove();
                                }
                            }
                        }
                        else if ((model.swapParts[partId].innerDrop === false
                            && model.transportBuilder.startAt === model.receiver(ui))
                            || (model.swapParts[partId].outerDrop === false
                                && model.transportBuilder.startAt !== model.receiver(ui))) {
                            $(this).sortable("cancel");
                        }
                    }
                    _update(model, evt, ui) {
                        if (ui.item.closest("table").length === 0)
                            return;
                        model.transportBuilder.directTo(model.receiver(ui)).update();
                        if (model.transportBuilder.startAt === model.transportBuilder.direction) {
                            model.transportBuilder.startAt === "first"
                                ? model.swapParts[0].bindData(model.transportBuilder.getFirst())
                                : model.swapParts[1].bindData(model.transportBuilder.getSecond());
                        }
                        else {
                            model.swapParts[0].bindData(model.transportBuilder.getFirst());
                            model.swapParts[1].bindData(model.transportBuilder.getSecond());
                        }
                        setTimeout(function () { model.dropDone(); }, 0);
                    }
                    enableDragDrop(parts) {
                        parts = parts || [0, 1];
                        this.model.enableDrag(this, parts, this.handle);
                    }
                }
                class SwapModel {
                    constructor($container, swapParts) {
                        this.$container = $container;
                        this.swapParts = swapParts;
                        this.transportBuilder = new ListItemTransporter(this.swapParts[0].dataSource, this.swapParts[1].dataSource)
                            .primary(this.swapParts[0].primaryKey);
                    }
                }
                class SearchResult {
                    constructor(results, indices) {
                        this.data = results;
                        this.indices = indices;
                    }
                }
                class SwapPart {
                    constructor() {
                        this.searchMode = "highlight"; // highlight & filter - Default: highlight
                        this.innerDrop = true;
                        this.outerDrop = true;
                    }
                    listControl($listControl) {
                        this.$listControl = $listControl;
                        return this;
                    }
                    searchControl($searchControl) {
                        this.$searchControl = $searchControl;
                        return this;
                    }
                    searchBox($searchBox) {
                        this.$searchBox = $searchBox;
                        return this;
                    }
                    setSearchMode(searchMode) {
                        this.searchMode = searchMode;
                        return this;
                    }
                    setSearchCriterion(searchCriterion) {
                        this.searchCriterion = searchCriterion;
                        return this;
                    }
                    setDataSource(dataSource) {
                        this.dataSource = dataSource;
                        return this;
                    }
                    setColumns(columns) {
                        this.columns = columns;
                        return this;
                    }
                    setPrimaryKey(primaryKey) {
                        this.primaryKey = primaryKey;
                        return this;
                    }
                    setInnerDrop(innerDrop) {
                        this.innerDrop = innerDrop;
                        return this;
                    }
                    setOuterDrop(outerDrop) {
                        this.outerDrop = outerDrop;
                        return this;
                    }
                    initDraggable(opts) {
                        this.$listControl.sortable(opts).disableSelection();
                    }
                    resetOriginalDataSource() {
                        this.originalDataSource = this.dataSource;
                    }
                    search() {
                        var searchContents = this.$searchBox.val();
                        var orders = new Array();
                        if (searchContents === "") {
                            if (this.originalDataSource === undefined)
                                return null;
                            return new SearchResult(this.originalDataSource, orders);
                        }
                        var searchCriterion = this.searchCriterion;
                        if (this.originalDataSource === undefined)
                            this.resetOriginalDataSource();
                        var results = _.filter(this.originalDataSource, function (value, index) {
                            var found = false;
                            _.forEach(searchCriterion, function (criteria) {
                                if (value[criteria].toString().indexOf(searchContents) !== -1) {
                                    found = true;
                                    return false;
                                }
                                else
                                    return true;
                            });
                            orders.push(index);
                            return found;
                        });
                        return new SearchResult(results, orders);
                    }
                    bindData(src) {
                        this.bindIn(src);
                        this.dataSource = src;
                    }
                    bindSearchEvent() {
                        var self = this;
                        var proceedSearch = this.proceedSearch;
                        this.$searchControl.click(function (evt, ui) {
                            proceedSearch.apply(self);
                        });
                        this.$searchBox.keyup(function (evt, ui) {
                            if (evt.which === 13) {
                                proceedSearch.apply(self);
                            }
                        });
                    }
                    proceedSearch() {
                        if (this.searchMode === "filter") {
                            var results = this.search();
                            if (results === null)
                                return;
                            this.bindData(results.data);
                        }
                        else {
                            this.highlightSearch();
                        }
                    }
                    build() {
                        this.bindSearchEvent();
                        return this;
                    }
                }
                class GridSwapPart extends SwapPart {
                    search() {
                        return super.search();
                    }
                    highlightSearch() {
                        var value = this.$searchBox.val();
                        var source = this.dataSource.slice();
                        var selected = this.$listControl.ntsGridList("getSelected");
                        if (selected.length > 0) {
                            var gotoEnd = source.splice(0, selected[0].index + 1);
                            source = source.concat(gotoEnd);
                        }
                        var iggridColumns = _.map(this.columns, c => {
                            c["key"] = c.key === undefined ? c.prop : c.key;
                            c["dataType"] = 'string';
                            return c;
                        });
                        var searchedValues = _.find(source, function (val) {
                            return _.find(iggridColumns, function (x) {
                                return x !== undefined && x !== null && val[x["key"]].toString().indexOf(value) >= 0;
                            }) !== undefined;
                        });
                        this.$listControl.ntsGridList('setSelected', searchedValues !== undefined ? [searchedValues[this.primaryKey]] : []);
                        if (searchedValues !== undefined && (selected.length === 0 ||
                            selected[0].id !== searchedValues[this.primaryKey])) {
                            var current = this.$listControl.igGrid("selectedRows");
                            if (current.length > 0 && this.$listControl.igGrid("hasVerticalScrollbar")) {
                                this.$listControl.igGrid("virtualScrollTo", current[0].index === source.length - 1
                                    ? current[0].index : current[0].index + 1);
                            }
                        }
                    }
                    bindIn(src) {
                        this.$listControl.igGrid("option", "dataSource", src);
                    }
                }
                class GridSwapList extends SwapModel {
                    sender(opts) {
                        return opts.item.closest("table")[0].id == this.swapParts[0].$listControl.attr("id")
                            ? "first" : "second";
                    }
                    receiver(opts) {
                        return opts.item.closest("table")[0].id == this.swapParts[1].$listControl.attr("id")
                            ? "second" : "first";
                    }
                    target(opts) {
                        if (opts.helper !== undefined && opts.helper.hasClass("select-drag")) {
                            return opts.helper.find("tr").map(function () {
                                return $(this).data("id");
                            });
                        }
                        return [opts.item.data("id")];
                    }
                    neighbor(opts) {
                        return opts.item.prev().length === 0 ? "ceil" : opts.item.prev().data("id");
                    }
                    dropDone() {
                        var self = this;
                        self.swapParts[0].$listControl.igGridSelection("clearSelection");
                        self.swapParts[1].$listControl.igGridSelection("clearSelection");
                        setTimeout(function () {
                            (self.transportBuilder.direction === "first"
                                ? self.swapParts[0].$listControl
                                : self.swapParts[1].$listControl).igGrid("virtualScrollTo", self.transportBuilder.incomeIndex);
                            (self.transportBuilder.startAt === "first"
                                ? self.swapParts[0].$listControl
                                : self.swapParts[1].$listControl).igGrid("virtualScrollTo", self.transportBuilder.outcomeIndex + 1);
                        }, 0);
                    }
                    enableDrag(ctx, parts, cb) {
                        var self = this;
                        for (var idx in parts) {
                            this.swapParts[parts[idx]].$listControl.on("iggridrowsrendered", function (evt, ui) {
                                cb.call(ctx, parts);
                            });
                        }
                    }
                    move(forward, value) {
                        var $source = forward === true ? this.swapParts[0].$listControl : this.swapParts[1].$listControl;
                        var sourceList = forward === true ? this.swapParts[0].dataSource : this.swapParts[1].dataSource;
                        var $dest = forward === true ? this.swapParts[1].$listControl : this.swapParts[0].$listControl;
                        var destList = forward === true ? this.swapParts[1].dataSource : this.swapParts[0].dataSource;
                        var selectedRows = $source.igGrid("selectedRows");
                        selectedRows.sort(function (one, two) {
                            return one.index - two.index;
                        });
                        var selectedIds = selectedRows.map(function (row) { return row.id; });
                        this.transportBuilder.at(forward ? "first" : "second").directTo(forward ? "second" : "first")
                            .target(selectedIds).toAdjacent(destList[destList.length - 1][this.swapParts[0].primaryKey]).update();
                        this.swapParts[0].bindData(this.transportBuilder.getFirst());
                        this.swapParts[1].bindData(this.transportBuilder.getSecond());
                        $source.igGridSelection("clearSelection");
                        $dest.igGridSelection("clearSelection");
                        setTimeout(function () {
                            $source.igGrid("virtualScrollTo", sourceList.length - 1 === selectedRows[0].index
                                ? selectedRows[0].index - 1 : selectedRows[0].index + 1);
                            $dest.igGrid("virtualScrollTo", destList.length - 1);
                        }, 10);
                    }
                }
                class ListItemTransporter {
                    constructor(firstList, secondList) {
                        this.firstList = firstList;
                        this.secondList = secondList;
                    }
                    first(firstList) {
                        this.firstList = firstList;
                        return this;
                    }
                    second(secondList) {
                        this.secondList = secondList;
                        return this;
                    }
                    at(startAt) {
                        this.startAt = startAt;
                        return this;
                    }
                    directTo(direction) {
                        this.direction = direction;
                        return this;
                    }
                    out(index) {
                        this.outcomeIndex = index;
                        return this;
                    }
                    into(index) {
                        this.incomeIndex = index;
                        return this;
                    }
                    primary(primaryKey) {
                        this.primaryKey = primaryKey;
                        return this;
                    }
                    target(targetIds) {
                        this.targetIds = targetIds;
                        return this;
                    }
                    toAdjacent(adjId) {
                        this.adjacentIncomeId = adjId;
                        return this;
                    }
                    indexOf(list, targetId) {
                        return _.findIndex(list, elm => elm[this.primaryKey].toString() === targetId.toString());
                    }
                    move(src, dest) {
                        for (var i = 0; i < this.targetIds.length; i++) {
                            this.outcomeIndex = this.indexOf(src, this.targetIds[i]);
                            if (this.outcomeIndex === -1)
                                return;
                            var target = src.splice(this.outcomeIndex, 1);
                            this.incomeIndex = this.indexOf(dest, this.adjacentIncomeId) + 1;
                            if (this.incomeIndex === 0) {
                                if (this.adjacentIncomeId === "ceil")
                                    this.incomeIndex = 0;
                                else if (target !== undefined) {
                                    src.splice(this.outcomeIndex, 0, target[0]);
                                    return;
                                }
                            }
                            dest.splice(this.incomeIndex + i, 0, target[0]);
                        }
                    }
                    determineDirection() {
                        if (this.startAt.toLowerCase() !== this.direction.toLowerCase()
                            && this.direction.toLowerCase() === "second") {
                            return "firstToSecond";
                        }
                        else if (this.startAt.toLowerCase() !== this.direction.toLowerCase()
                            && this.direction.toLowerCase() === "first") {
                            return "secondToFirst";
                        }
                        else if (this.startAt.toLowerCase() === this.direction.toLowerCase()
                            && this.direction.toLowerCase() === "first") {
                            return "insideFirst";
                        }
                        else
                            return "insideSecond";
                    }
                    update() {
                        switch (this.determineDirection()) {
                            case "firstToSecond":
                                this.move(this.firstList, this.secondList);
                                break;
                            case "secondToFirst":
                                this.move(this.secondList, this.firstList);
                                break;
                            case "insideFirst":
                                this.move(this.firstList, this.firstList);
                                break;
                            case "insideSecond":
                                this.move(this.secondList, this.secondList);
                                break;
                        }
                    }
                    getFirst() {
                        return this.firstList;
                    }
                    getSecond() {
                        return this.secondList;
                    }
                }
            })(koExtentions = ui_6.koExtentions || (ui_6.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * SwitchButton binding handler
                 */
                class NtsSwitchButtonBindingHandler {
                    /**
                     * Constructor.
                     */
                    constructor() {
                    }
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var selectedCssClass = 'selected';
                        // Get options.
                        var options = ko.unwrap(data.options);
                        // Get options value.
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = ko.unwrap(data.value);
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Container.
                        var container = $(element);
                        // Remove deleted button.
                        $('button', container).each(function (index, btn) {
                            var $btn = $(btn);
                            var btnValue = $(btn).data('swbtn');
                            // Check if btn is contained in options.
                            var foundFlag = _.findIndex(options, function (opt) {
                                return opt[optionValue] == btnValue;
                            }) != -1;
                            if (!foundFlag) {
                                $btn.remove();
                                return;
                            }
                        });
                        // Start binding new state.
                        _.forEach(options, function (opt) {
                            var value = opt[optionValue];
                            var text = opt[optionText];
                            // Find button.
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
                                // Recreate
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
                        // Enable
                        (enable === true) ? $('button', container).prop("disabled", false) : $('button', container).prop("disabled", true);
                    }
                }
                ko.bindingHandlers['ntsSwitchButton'] = new NtsSwitchButtonBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_7) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * TabPanel Binding Handler
                 */
                class TabPanelBindingHandler {
                    /**
                     * Constructor.
                     */
                    constructor() {
                    }
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var tabs = ko.unwrap(data.dataSource);
                        var direction = ko.unwrap(data.direction || "horizontal");
                        // Container.
                        var container = $(element);
                        // Create title.
                        container.prepend('<ul></ul>');
                        var titleContainer = container.children('ul');
                        for (var i = 0; i < tabs.length; i++) {
                            var id = tabs[i].id;
                            var title = tabs[i].title;
                            titleContainer.append('<li><a href="#' + id + '">' + title + '</a></li>');
                            // Wrap content.
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
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        // Get tab list.
                        var tabs = ko.unwrap(data.dataSource);
                        // Container.
                        var container = $(element);
                        // Select tab.
                        var activeTab = tabs.filter(tab => { return tab.id == data.active(); })[0];
                        var indexActive = tabs.indexOf(activeTab);
                        container.tabs("option", "active", indexActive);
                        // Disable & visible tab.
                        tabs.forEach(tab => {
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
                    }
                }
                ko.bindingHandlers['ntsTabPanel'] = new TabPanelBindingHandler();
            })(koExtentions = ui_7.koExtentions || (ui_7.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_8) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * TreeGrid binding handler
                 */
                class NtsTreeGridViewBindingHandler {
                    /**
                     * Constructor.
                     */
                    constructor() {
                    }
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var options = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
                        var optionsValue = ko.unwrap(data.primaryKey !== undefined ? data.primaryKey : data.optionsValue);
                        var optionsText = ko.unwrap(data.primaryText !== undefined ? data.primaryText : data.optionsText);
                        var optionsChild = ko.unwrap(data.childDataKey !== undefined ? data.childDataKey : data.optionsChild);
                        var extColumns = ko.unwrap(data.columns !== undefined ? data.columns : data.extColumns);
                        // Default.
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
                        // Init ig grid.
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
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var options = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
                        var selectedValues = ko.unwrap(data.selectedValues);
                        var singleValue = ko.unwrap(data.value);
                        // Update datasource.
                        var originalSource = $(element).igTreeGrid('option', 'dataSource');
                        if (!_.isEqual(originalSource, options)) {
                            $(element).igTreeGrid("option", "dataSource", _.cloneDeep(options));
                            $(element).igTreeGrid("dataBind");
                        }
                        // Set multiple data source.
                        var multiple = data.multiple != undefined ? ko.unwrap(data.multiple) : true;
                        if ($(element).igTreeGridSelection("option", "multipleSelection") !== multiple) {
                            $(element).igTreeGridSelection("option", "multipleSelection", multiple);
                        }
                        // Set show checkbox.
                        var showCheckBox = ko.unwrap(data.showCheckBox != undefined ? data.showCheckBox : true);
                        if ($(element).igTreeGridRowSelectors("option", "enableCheckBoxes") !== showCheckBox) {
                            $(element).igTreeGridRowSelectors("option", "enableCheckBoxes", showCheckBox);
                        }
                        // Clear selection.
                        if ((selectedValues === null || selectedValues === undefined) && (singleValue === null || singleValue === undefined)) {
                            $(element).igTreeGridSelection("clearSelection");
                        }
                        else {
                            // Compare value.
                            var olds = _.map($(element).igTreeGridSelection("selectedRow"), function (row) {
                                return row.id;
                            });
                            // Not change, do nothing.
                            if (multiple) {
                                if (_.isEqual(selectedValues.sort(), olds.sort())) {
                                    return;
                                }
                                // Update.
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
                    }
                }
                ko.bindingHandlers['ntsTreeGridView'] = new NtsTreeGridViewBindingHandler();
            })(koExtentions = ui_8.koExtentions || (ui_8.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_9) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * UpDownButton binding handler
                 */
                class NtsUpDownBindingHandler {
                    /**
                     * Constructor.
                     */
                    constructor() {
                    }
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
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
                                    //                        $targetElement.igGrid("option", "dataSource", source);
                                    //                        $targetElement.igGrid("dataBind");
                                    var index = upDown + grouped["group1"][0].index;
                                    //                        var index = $targetElement.igGrid("selectedRows")[0].index;
                                    $targetElement.igGrid("virtualScrollTo", index);
                                }
                            }
                        };
                        var moveTree = function (upDown, $targetElement) {
                            var multiSelectedRaw = $targetElement.igTreeGrid("selectedRows");
                            var singleSelectedRaw = $targetElement.igTreeGrid("selectedRow");
                            //                var targetSource = ko.unwrap(data.targetSource);
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
                            //                var targetSource = ko.unwrap(data.targetSource);
                            var source = _.cloneDeep($targetElement.igTreeGrid("option", "dataSource"));
                            var result = findChild(upDown, selected["id"], source, false, false);
                            var moved = result.moved;
                            var changed = result.changed;
                            source = result.source;
                            if (moved && changed) {
                                data.targetSource(source);
                                //                    $targetElement.igTreeGrid("option", "dataSource", source);
                                //                    $targetElement.igTreeGrid("dataBind");
                                //                    data.targetSource(source);
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
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var $upDown = $(element);
                        var elementId = $upDown.attr('id');
                        if (nts.uk.util.isNullOrUndefined(elementId)) {
                            throw new Error('the element NtsSwapList must have id attribute.');
                        }
                        var data = valueAccessor();
                    }
                }
                ko.bindingHandlers['ntsUpDown'] = new NtsUpDownBindingHandler();
            })(koExtentions = ui_9.koExtentions || (ui_9.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * Wizard binding handler
                 */
                class WizardBindingHandler {
                    /**
                     * Constructor.
                     */
                    constructor() {
                    }
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        // Get step list
                        var options = ko.unwrap(data.steps);
                        var theme = ko.unwrap(data.theme);
                        var cssClass = "nts-wizard " + "theme-" + theme;
                        // Container
                        var container = $(element);
                        // Create steps
                        for (var i = 0; i < options.length; i++) {
                            var contentClass = ko.unwrap(options[i].content);
                            var htmlStep = container.children('.steps').children(contentClass).html();
                            var htmlContent = container.children('.contents').children(contentClass).html();
                            container.append('<h1 class="' + contentClass + '">' + htmlStep + '</h1>');
                            container.append('<div>' + htmlContent + '</div>');
                        }
                        var icon = container.find('.header .image').data('icon');
                        // Remove html
                        var header = container.children('.header');
                        container.children('.header').remove();
                        container.children('.steps').remove();
                        container.children('.contents').remove();
                        // Create wizard
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
                                // Remove old class.
                                container.children('.steps').children('ul').children('li').removeClass('step-current');
                                container.children('.steps').children('ul').children('li').removeClass('step-prev');
                                container.children('.steps').children('ul').children('li').removeClass('step-next');
                                // Add new class.
                                container.children('.steps').children('ul').children('.done').addClass('disabled');
                                container.children('.steps').children('ul').children('.current').addClass('step-current');
                                container.children('.steps').children('ul').children('.done').addClass('step-prev');
                                container.children('.steps').children('ul').children('.step-current').nextAll('li').not('.done').addClass('step-next');
                                return true;
                            }
                        }).data("length", options.length);
                        // Add default class
                        container.addClass(cssClass);
                        container.children('.steps').children('ul').children('li').children('a').before('<div class="nts-steps"></div>');
                        container.children('.steps').children('ul').children('li').children('a').addClass('nts-step-contents');
                        //container.children('.steps').children('ul').children('.first').addClass('begin');
                        container.children('.steps').children('ul').children('.last').addClass('end');
                        container.children('.steps').children('ul').children('li').not('.begin').not('.end').children('.nts-steps').addClass('nts-steps-middle');
                        container.find('.nts-steps-middle').append('<div class="nts-vertical-line"></div><div class="nts-bridge"><div class="nts-point"></div><div class="nts-horizontal-line"></div></div>');
                        // Remove old class
                        container.children('.steps').children('ul').children('li').removeClass('step-current');
                        container.children('.steps').children('ul').children('li').removeClass('step-prev');
                        container.children('.steps').children('ul').children('li').removeClass('step-next');
                        // Add new class
                        container.children('.steps').children('ul').children('.current').addClass('step-current');
                        container.children('.steps').children('ul').children('.done').addClass('step-prev');
                        container.children('.steps').children('ul').children('.step-current').nextAll('li').not('.done').addClass('step-next');
                        // Remove content
                        container.find('.actions').hide();
                        // Add Header
                        container.children('.steps').prepend(header);
                        container.find('.header .image').attr('style', 'background-image: url("' + icon + '")');
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    }
                }
                ko.bindingHandlers['ntsWizard'] = new WizardBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="ui/viewcontext.d.ts"/>
/// <reference path="util.ts"/>
/// <reference path="text.ts"/>
/// <reference path="number.ts"/>
/// <reference path="time.ts"/>
/// <reference path="request.ts"/>
/// <reference path="ui/init.ts"/>
/// <reference path="ui/notify.ts"/>
/// <reference path="ui/validation.ts"/>
/// <reference path="ui/errors.ts"/>
/// <reference path="ui/ui.ts"/>
/// <reference path="ui/dialog-options.ts"/>
/// <reference path="ui/textbox-options.ts"/>
/// <reference path="ui/jquery-ext.ts"/>
/// <reference path="ui/ko-ext.ts"/>
/// <reference path="ui/ko-ext/checkbox-ko-ext.ts"/>
/// <reference path="ui/ko-ext/combobox-ko-ext.ts"/>
/// <reference path="ui/ko-ext/datepicker-ko-ext.ts"/>
/// <reference path="ui/ko-ext/dialog-ko-ext.ts"/>
/// <reference path="ui/ko-ext/editor-ko-ext.ts"/>
/// <reference path="ui/ko-ext/formlabel-ko-ext.ts"/>
/// <reference path="ui/ko-ext/gridlist-ko-ext.ts"/>
/// <reference path="ui/ko-ext/listbox-ko-ext.ts"/>
/// <reference path="ui/ko-ext/radiobox-ko-ext.ts"/>
/// <reference path="ui/ko-ext/searchbox-ko-ext.ts"/>
/// <reference path="ui/ko-ext/swaplist-ko-ext.ts"/>
/// <reference path="ui/ko-ext/switch-button-ko-ext.ts"/>
/// <reference path="ui/ko-ext/tabpanel-ko-ext.ts"/>
/// <reference path="ui/ko-ext/treegrid-ko-ext.ts"/>
/// <reference path="ui/ko-ext/updown-button-ko-ext.ts"/>
/// <reference path="ui/ko-ext/wizard-ko-ext.ts"/> 
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                class DatePickerBindingHandler {
                    /**
                     * Constructor.
                     */
                    constructor() {
                    }
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
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
                        // Init Datepicker
                        $input.datepicker({
                            language: 'ja-JP',
                            format: ISOFormat,
                            startDate: startDate,
                            endDate: endDate,
                            autoHide: autoHide,
                        });
                        var validator = new ui.validation.TimeValidator(constraintName, { required: required, outputFormat: valueFormat, valueType: valueType });
                        $input.on("change", (e) => {
                            var newText = $input.val();
                            var result = validator.validate(newText);
                            $input.ntsError('clear');
                            if (result.isValid) {
                                // Day of Week
                                if (hasDayofWeek) {
                                    if (uk.util.isNullOrEmpty(result.parsedValue))
                                        $label.text("");
                                    else
                                        $label.text("(" + uk.time.formatPattern(newText, "", dayofWeekFormat) + ")");
                                }
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
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
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
                        // Value Binding
                        var dateFormatValue = (value() !== "") ? uk.time.formatPattern(value(), valueFormat, ISOFormat) : "";
                        if (init === true || uk.time.formatPattern($input.datepicker("getDate", true), "", ISOFormat) !== dateFormatValue) {
                            if (dateFormatValue !== "" && dateFormatValue !== "Invalid date") {
                                $input.datepicker('setDate', dateFormatValue);
                                $label.text("(" + uk.time.formatPattern(value(), valueFormat, dayofWeekFormat) + ")");
                            }
                            else {
                                $input.val("");
                                $label.text("");
                            }
                        }
                        container.data("init", false);
                        // Properties Binding
                        $input.datepicker('setStartDate', startDate);
                        $input.datepicker('setEndDate', endDate);
                        if (enable !== undefined)
                            $input.prop("disabled", !enable);
                        else
                            $input.prop("disabled", disabled);
                        if (data.button)
                            container.find('.datepicker-btn').prop("disabled", disabled);
                    }
                }
                ko.bindingHandlers['ntsDatePicker'] = new DatePickerBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * HelpButton binding handler
                 */
                class NtsHelpButtonBindingHandler {
                    constructor() {
                    }
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var image = ko.unwrap(data.image);
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var position = ko.unwrap(data.position);
                        //Position
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
                        // Container
                        $(element).on("click", function () {
                            if ($popup.is(":visible")) {
                                $popup.hide();
                            }
                            else {
                                let CARET_WIDTH = parseFloat($caret.css("font-size")) * 2;
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
                        // Click outside event
                        $("html").on("click", function (event) {
                            if (!$container.is(event.target) && $container.has(event.target).length === 0) {
                                $container.find(".nts-help-button-image").hide();
                            }
                        });
                    }
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Enable
                        (enable === true) ? $(element).removeAttr("disabled") : $(element).attr("disabled", "disabled");
                    }
                }
                ko.bindingHandlers['ntsHelpButton'] = new NtsHelpButtonBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * LinkButton
                 */
                class NtsLinkButtonBindingHandler {
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
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
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    }
                }
                ko.bindingHandlers['ntsLinkButton'] = new NtsLinkButtonBindingHandler();
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
                class KibanTimer {
                    constructor(target) {
                        var self = this;
                        self.elapsedSeconds = 0;
                        self.formatted = ko.observable(uk.time.formatSeconds(this.elapsedSeconds, 'hh:mm:ss'));
                        self.targetComponent = target;
                        self.isTimerStart = ko.observable(false);
                        self.oldDated = ko.observable(undefined);
                        document.getElementById(self.targetComponent).innerHTML = self.formatted();
                    }
                    run(timer) {
                        var x = new Date().getTime() - timer.oldDated().getTime();
                        x = Math.floor(x / 1000);
                        timer.elapsedSeconds = x;
                        document.getElementById(timer.targetComponent).innerHTML
                            = uk.time.formatSeconds(x, 'hh:mm:ss');
                    }
                    start() {
                        var self = this;
                        if (!self.isTimerStart()) {
                            self.oldDated(new Date());
                            self.isTimerStart(true);
                            self.interval = setInterval(self.run, 1000, self);
                        }
                    }
                    end() {
                        var self = this;
                        if (self.isTimerStart()) {
                            self.oldDated(undefined);
                            self.isTimerStart(false);
                            clearInterval(self.interval);
                        }
                    }
                }
                sharedvm.KibanTimer = KibanTimer;
            })(sharedvm = ui.sharedvm || (ui.sharedvm = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
/// <reference path="../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var file;
            (function (file_1) {
                class FileDownload {
                    constructor(servicePath, data) {
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
                    isTaskFinished(file) {
                        var options = {
                            dataType: 'text',
                            contentType: 'text/plain'
                        };
                        uk.request.ajax("file/file/isfinished", file.taskId).done(function (res) {
                            file.isFinishTask(res);
                        }).fail(function (error) {
                            file.reject(error);
                        });
                    }
                    print() {
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
                    }
                    download() {
                        var self = this;
                        window.location.href = ("http://localhost:8080/nts.uk.pr.web/webapi/file/file/dl/" + self.taskId);
                        if (self.deferred) {
                            self.deferred.resolve();
                        }
                    }
                }
                file_1.FileDownload = FileDownload;
            })(file = ui.file || (ui.file = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
