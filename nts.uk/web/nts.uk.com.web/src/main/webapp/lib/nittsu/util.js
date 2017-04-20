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
//# sourceMappingURL=util.js.map
