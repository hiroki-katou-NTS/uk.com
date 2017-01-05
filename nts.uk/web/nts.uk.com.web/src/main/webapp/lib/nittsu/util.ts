module nts.uk {

    export module util {
    
        /**
         * 常にtrueを返す関数が必要になったらこれ
         */
        export function alwaysTrue() {
            return true;
        }
        
        function addToArray(node, arr) {
            arr.push(node)
        }
        export function visitDfs(node, func, arr, childField) {
            if (func) {
                func(node, arr);
            }
            var childs = node[childField];
            $.each(childs, function (child) {
                visitDfs(childs[child], func, arr, childField);
            });
        }      
        export function flatArray(arr, childField) {
            var flatArr = [];
            if(!childField) return arr;
            for(var i = 0; i < arr.length; i++) {
                var item = arr[i];
                visitDfs(item, addToArray, flatArr, childField);
            }
            return flatArr;
        }
        /**
         * Returns true if the target is null or undefined.
         */
        export function isNullOrUndefined(target: any) {
            return target === null || target === undefined;
        }
    
        /**
         * Generate random identifier string (UUIDv4)
         */
        export function randomId() {
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                var r = Math.random() * 16 | 0;
                return ((c == 'x') ? r : (r & 0x3 | 0x8)).toString(16);
            });
        }
    
        /**
         * Returns true if current window is in frame.
         */
        export function isInFrame() {
            return window.parent != window;
        }
    
        /**
         * valueMaybeEmptyがnullまたはundefinedの場合、defaultValueを返す。
         * そうでなければ、valueMaybeEmptyを返す。
         */
        export function orDefault(valueMaybeEmpty: any, defaultValue: any) {
            return isNullOrUndefined(valueMaybeEmpty) ? defaultValue : valueMaybeEmpty;
        }
    
        /**
         * Returns true if expects contains actual.
         */
        export function isIn(actual: any, expects: Array<any>) {
            for (var i = 0; i < expects.length; i++) {
                if (actual === expects[i]) return true;
            }
            return false;
        };
    
        /**
         * Like Java Optional
         */
        export module optional {
            export function of<V>(value: V) {
                return new Optional(value);
            }
    
            export function empty() {
                return new Optional(null);
            }
    
            export class Optional<V> {
                value: V;
    
                constructor(value: V) {
                    this.value = orDefault(value, null);
                }
    
                ifPresent(consumer: (value: V) => {}) {
                    if (this.isPresent) {
                        consumer(this.value);
                    }
                    return this;
                }
    
                ifEmpty(action: () => {}) {
                    if (!this.isPresent) {
                        action();
                    }
                    return this;
                }
    
                map<M>(mapper: (value: V) => M): Optional<M> {
                    return this.isPresent ? of(mapper(this.value)) : empty();
                }
    
                isPresent(): boolean {
                    return this.value !== null;
                }
    
                get(): V {
                    if (!this.isPresent) {
                        throw new Error('not present');
                    }
                    return this.value;
                }
    
                orElse(stead: V): V {
                    return this.isPresent ? this.value : stead;
                }
    
                orElseThrow(errorBuilder: () => Error) {
                    if (!this.isPresent) {
                        throw errorBuilder();
                    }
                }
            }
        }
    }
    
    export class WebStorageWrapper {
        
        nativeStorage: Storage;
        
        constructor(nativeStorage: Storage) {
            this.nativeStorage = nativeStorage;
        }
        
        setItem(key: string, value: string) {
            if (value === undefined) {
                return;
            }
            this.nativeStorage.setItem(key, value);
        }
        
        setItemAsJson(key: string, value: any) {
            this.setItem(key, JSON.stringify(value));
        }
        
        containsKey(key: string) {
            return this.getItem(key) !== null;
        };
        
        getItem(key: string): util.optional.Optional<string> {
            var value: string = this.nativeStorage.getItem(key);
            if (value === null || value === undefined || value === 'undefined') {
                return util.optional.empty();
            }
            return util.optional.of(value);
        }
        
        getItemAndRemove(key: string) {
            var item = this.getItem(key);
            this.removeItem(key);
            return item;
        }
        
        removeItem(key: string) {
            this.nativeStorage.removeItem(key);
        }
        
        clear() {
            this.nativeStorage.clear();
        }
    }
    
    export var sessionStorage = new WebStorageWrapper(window.sessionStorage);
}