var nts;
(function (nts) {
    function buildStorage(storage) {
        var wrapper = {
            setItem: function (key, value) {
                if (value === undefined) {
                    return;
                }
                storage.setItem(key, value);
            },
            setItemStringifyJson: function (key, value) {
                wrapper.setItem(key, JSON.stringify(value));
            },
            containsKey: function (key) {
                return this.getItem(key) !== null;
            },
            getItem: function (key) {
                var value = storage.getItem(key);
                if (value === null || value === undefined || value === 'undefined') {
                    return null;
                }
                return value;
            },
            getItemParsedJson: function (key) {
                var json = wrapper.getItem(key);
                if (json !== null) {
                    return JSON.parse(json);
                }
                else {
                    return null;
                }
            },
            getItemAndRemove: function (key) {
                var item = this.getItem(key);
                this.removeItem(key);
                return item;
            },
            ifPresent: function (key, consumer) {
                var value = wrapper.getItem(key);
                if (value !== null) {
                    consumer(value);
                }
            },
            ifPresentParsedJson: function (key, consumer) {
                wrapper.ifPresent(key, function (json) {
                    consumer(JSON.parse(json));
                });
            },
            removeItem: function (key) {
                storage.removeItem(key);
            },
            clear: function () {
                storage.clear();
            }
        };
        // 開発時のローカル実行用にフェイクを作っておく
        if (storage === undefined) {
            var fake = {};
            for (var key in wrapper) {
                if (wrapper.hasOwnProperty(key) && typeof wrapper[key] === 'function') {
                    fake[key] = function () {
                    };
                }
            }
            wrapper = fake;
        }
        return wrapper;
    }
    nts.buildStorage = buildStorage;
    function sessionStorage() {
        return nts.buildStorage(window.sessionStorage);
    }
    nts.sessionStorage = sessionStorage;
})(nts || (nts = {}));
var nts;
(function (nts) {
    var uk;
    (function (uk) {
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
             * Returns true if the target is null or undefined.
             */
            function isNullOrUndefined(target) {
                return target === null || target === undefined;
            }
            util.isNullOrUndefined = isNullOrUndefined;
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
        })(util = uk.util || (uk.util = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
