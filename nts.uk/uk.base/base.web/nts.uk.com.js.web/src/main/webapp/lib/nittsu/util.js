var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var KeyCodes;
        (function (KeyCodes) {
            KeyCodes.Tab = 9;
            KeyCodes.Enter = 13;
            KeyCodes.Ctrl = 17;
        })(KeyCodes = uk.KeyCodes || (uk.KeyCodes = {}));
        var util;
        (function (util) {
            function compare(obj1, obj2) {
                for (var p in obj1) {
                    if (obj1.hasOwnProperty(p) !== obj2.hasOwnProperty(p)) {
                        return false;
                    }
                    switch (typeof (obj1[p])) {
                        case "object":
                            if (!compare(obj1[p], obj2[p]))
                                return false;
                            break;
                        case "function":
                            break;
                        default:
                            if (obj1[p] !== obj2[p]) {
                                return false;
                            }
                    }
                }
                for (var p in obj2) {
                    if (typeof (obj1[p]) == 'undefined')
                        return false;
                }
                return true;
            }
            util.compare = compare;
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
            function isNullOrEmpty(target) {
                return (target === undefined || target === null || target.length == 0);
            }
            util.isNullOrEmpty = isNullOrEmpty;
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
            function getConstraintMes(primitiveValues) {
                if (isNullOrEmpty(primitiveValues)) {
                    return "";
                }
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
                        case 'Integer':
                        case 'Date':
                        case 'Time':
                        case 'Clock':
                        case 'Duration':
                        case 'TimePoint':
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
            util.getConstraintMes = getConstraintMes;
            function getConstraintLabel(primitiveValues) {
                var constraintText = getConstraintMes(primitiveValues);
                return "<span class='constraint-label'>(" + constraintText + ")</span>";
            }
            util.getConstraintLabel = getConstraintLabel;
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
                        if (this.isPresent()) {
                            consumer(this.value);
                        }
                        return this;
                    };
                    Optional.prototype.ifEmpty = function (action) {
                        if (!this.isPresent()) {
                            action();
                        }
                        return this;
                    };
                    Optional.prototype.map = function (mapper) {
                        return this.isPresent() ? of(mapper(this.value)) : empty();
                    };
                    Optional.prototype.isPresent = function () {
                        return this.value !== null;
                    };
                    Optional.prototype.get = function () {
                        if (!this.isPresent()) {
                            throw new Error('not present');
                        }
                        return this.value;
                    };
                    Optional.prototype.orElse = function (stead) {
                        return this.isPresent() ? this.value : stead;
                    };
                    Optional.prototype.orElseThrow = function (errorBuilder) {
                        if (!this.isPresent()) {
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
            var value;
            (function (value) {
                function reset($controls, defaultVal, immediateApply) {
                    var resetEvent = new CustomEvent(DefaultValue.RESET_EVT, {
                        detail: {
                            value: defaultVal,
                            immediateApply: immediateApply === undefined ? true : immediateApply
                        }
                    });
                    _.forEach($controls, function (control) {
                        control.dispatchEvent(resetEvent);
                    });
                }
                value.reset = reset;
                var DefaultValue = (function () {
                    function DefaultValue() {
                    }
                    DefaultValue.prototype.onReset = function ($control, koValue) {
                        var self = this;
                        $control.addClass("reset-element");
                        $control.on(DefaultValue.RESET_EVT, function (e) {
                            var param = e.detail;
                            self.asDefault($(this), koValue, param.value, param.immediateApply);
                        });
                        return this;
                    };
                    DefaultValue.prototype.applyReset = function ($control, koValue) {
                        var defaultVal = _.cloneDeep($control.data("default"));
                        var isDirty = defaultVal !== koValue();
                        if ($control.ntsError("hasError"))
                            $control.ntsError("clear");
                        if (defaultVal !== undefined && isDirty)
                            setTimeout(function () { return koValue(defaultVal); }, 0);
                        return { isDirty: isDirty };
                    };
                    DefaultValue.prototype.asDefault = function ($control, koValue, defaultValue, immediateApply) {
                        var defaultVal = defaultValue !== undefined ? defaultValue : koValue();
                        $control.data("default", defaultVal);
                        if (immediateApply)
                            this.applyReset($control, koValue);
                    };
                    DefaultValue.RESET_EVT = "reset";
                    return DefaultValue;
                }());
                value.DefaultValue = DefaultValue;
            })(value = util.value || (util.value = {}));
            var accessor;
            (function (accessor) {
                function defineInto(obj) {
                    return new AccessorDefine(obj);
                }
                accessor.defineInto = defineInto;
                var AccessorDefine = (function () {
                    function AccessorDefine(obj) {
                        this.obj = obj;
                    }
                    AccessorDefine.prototype.get = function (name, func) {
                        Object.defineProperty(this.obj, name, { get: func, configurable: true });
                        return this;
                    };
                    return AccessorDefine;
                }());
                accessor.AccessorDefine = AccessorDefine;
            })(accessor = util.accessor || (util.accessor = {}));
            var exception;
            (function (exception_1) {
                function isBundledBusinessErrors(exception) {
                    return !isNullOrUndefined(exception) && ($.isArray(exception["errors"])
                        && exception["businessException"]);
                }
                exception_1.isBundledBusinessErrors = isBundledBusinessErrors;
                function isErrorToReject(res) {
                    return !isNullOrUndefined(res) && (res.businessException || res.optimisticLock);
                }
                exception_1.isErrorToReject = isErrorToReject;
                function isBusinessError(res) {
                    return !isNullOrUndefined(res) && (res.businessException);
                }
                exception_1.isBusinessError = isBusinessError;
            })(exception = util.exception || (util.exception = {}));
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
                        this.runAfter = 0;
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
                    Configuration.prototype.after = function (runAfterMilliseconds) {
                        this.runAfter = runAfterMilliseconds;
                        return this;
                    };
                    Configuration.prototype.run = function () {
                        var _this = this;
                        var dfd = $.Deferred();
                        if (this.runAfter > 0) {
                            setTimeout(function () { return _this.repeat(dfd); }, this.runAfter);
                        }
                        else {
                            this.repeat(dfd);
                        }
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
            })(repeater = deferred.repeater || (deferred.repeater = {}));
        })(deferred = uk.deferred || (uk.deferred = {}));
        var resource;
        (function (resource) {
            var names = window['names'] || {};
            var messages = window['messages'] || {};
            function getText(code, params) {
                var text = names[code];
                if (text) {
                    text = formatParams(text, params);
                    text = formatCompCustomizeResource(text);
                    return text.replace(/\\r\\n/g, '\r\n');
                }
                return code;
            }
            resource.getText = getText;
            function getMessage(messageId, params) {
                var message = messages[messageId];
                if (!message) {
                    var responseText_1 = "";
                    nts.uk.request.syncAjax("com", "i18n/resources/rawcontent/" + messageId).done(function (res) {
                        responseText_1 = res;
                    }).fail(function () {
                    });
                    if (responseText_1.length == 0 || responseText_1 === messageId) {
                        return messageId;
                    }
                    message = responseText_1;
                    messages[messageId] = message;
                }
                message = formatParams(message, params);
                message = formatCompCustomizeResource(message);
                return message.replace(/\\r\\n/g, '\r\n');
            }
            resource.getMessage = getMessage;
            function formatCompCustomizeResource(message) {
                var compDependceParamRegex = /{#(\w*)}/;
                var matches;
                while (matches = compDependceParamRegex.exec(message)) {
                    var code = matches[1];
                    var text_1 = getText(code);
                    message = message.replace(compDependceParamRegex, text_1);
                }
                return message;
            }
            function formatParams(message, args) {
                if (args == null || args.length == 0)
                    return message;
                var paramRegex = /{([0-9])+(:\w+)?}/;
                var matches;
                var formatter = uk.time.getFormatter();
                while (matches = paramRegex.exec(message)) {
                    var code = matches[1];
                    var text_2 = args[parseInt(code)];
                    var param = matches[2];
                    if (param !== undefined && formatter !== undefined) {
                        text_2 = uk.time.applyFormat(param.substring(1), text_2, formatter);
                    }
                    message = message.replace(paramRegex, text_2);
                }
                return message;
            }
            function getControlName(name) {
                var hashIdx = name.indexOf("#");
                if (hashIdx !== 0)
                    return name;
                var names = name.substring(hashIdx + 2, name.length - 1).split(",");
                if (names.length > 1) {
                    var params_1 = new Array();
                    _.forEach(names, function (n, idx) {
                        if (idx === 0)
                            return true;
                        params_1.push(getText(n.trim()));
                    });
                    return getText(names[0], params_1);
                }
                return getText(names[0]);
            }
            resource.getControlName = getControlName;
        })(resource = uk.resource || (uk.resource = {}));
        uk.sessionStorage = new WebStorageWrapper(window.sessionStorage);
        uk.localStorage = new WebStorageWrapper(window.localStorage);
        var characteristics;
        (function (characteristics) {
            var delayToEmulateAjax = 100;
            function convertObjectToArray(key) {
                var result = [];
                for (var p in key) {
                    result.push([p, key[p]]);
                }
                result.sort(function (a, b) {
                    return (a > b) ? 1 : (a < b) ? -1 : 0;
                });
                return result.toString();
            }
            function saveByObjectKey(key, value) {
                return save(convertObjectToArray(key), value);
            }
            characteristics.saveByObjectKey = saveByObjectKey;
            function restoreByObjectKey(key) {
                return restore(convertObjectToArray(key));
            }
            characteristics.restoreByObjectKey = restoreByObjectKey;
            function save(key, value) {
                var dfd = $.Deferred();
                setTimeout(function () {
                    uk.localStorage.setItemAsJson(createKey(key), value);
                    dfd.resolve();
                }, delayToEmulateAjax);
                return dfd.promise();
            }
            characteristics.save = save;
            function restore(key) {
                var dfd = $.Deferred();
                setTimeout(function () {
                    var value = uk.localStorage.getItem(createKey(key))
                        .map(function (v) { return JSON.parse(v); }).orElse(undefined);
                    dfd.resolve(value);
                }, delayToEmulateAjax);
                return dfd.promise();
            }
            characteristics.restore = restore;
            function remove(key) {
                var dfd = $.Deferred();
                setTimeout(function () {
                    uk.localStorage.removeItem(createKey(key));
                    dfd.resolve();
                }, delayToEmulateAjax);
                return dfd.promise();
            }
            characteristics.remove = remove;
            function createKey(key) {
                return 'nts.uk.characteristics.' + key;
            }
        })(characteristics = uk.characteristics || (uk.characteristics = {}));
        var types;
        (function (types_1) {
            function matchArguments(values, types) {
                if (values.length !== types.length) {
                    return false;
                }
                for (var i = 0; i < values.length; i++) {
                    if (typeof values[i] !== types[i])
                        return false;
                }
                return true;
            }
            types_1.matchArguments = matchArguments;
        })(types = uk.types || (uk.types = {}));
        var cookie;
        (function (cookie) {
            function get(name) {
                var value = asMap()[name];
                return util.optional.of(value);
            }
            cookie.get = get;
            function remove(name, attr) {
                document.cookie = name + "=; path=" + attr.path + "; max-age=0";
            }
            cookie.remove = remove;
            function asMap() {
                var map = {};
                document.cookie.split(";")
                    .forEach(function (item) {
                    var positionOfDelimiter = item.indexOf("=");
                    var name = item.slice(0, positionOfDelimiter).trim();
                    map[name] = item.slice(positionOfDelimiter + 1);
                });
                return map;
            }
            cookie.asMap = asMap;
        })(cookie = uk.cookie || (uk.cookie = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=util.js.map