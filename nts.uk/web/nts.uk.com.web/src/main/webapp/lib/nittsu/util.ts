module nts.uk {
    
    export module KeyCodes {
        export const Tab = 9;
    }

    export module util {
    
        /**
         * 常にtrueを返す関数が必要になったらこれ
         */
        export function alwaysTrue() {
            return true;
        }
        /**
         * function find an item index in array
         * if key presented will perform find index of item in array which contain key equal to the 'item' parameter
         */
        export function findIndex(arr, value, key) {
            for(var i = 0; i < arr.length; i++) {
                var item = arr[i];
                if(item[key] === value) return i; 
            }
            return -1;
        }
        /**
         * function add item to array, this function is used in combine with visitDfs function
         * visitDfs(node, addToArray, childField, arr) will return flatArray by DFS order, start by node and following by each child belong to it.
         */
        function addToArray(node, arr) {
            arr.push(node)
        }
        /**
         * DFS algorithm function to iterate over an object with structre like tree
         */
        export function visitDfs(node, func, childField, arr?) {
            if (func) {
                if(arr)
                    func(node, arr);
                else func(node);
            }
            var childs = node[childField];
            $.each(childs, function (child) {
                visitDfs(childs[child], func, childField, arr);
            });
        }
        /**
         * return flatern array of array of tree-like objects
         */
        export function flatArray(arr, childField) {
            var flatArr = [];
            if(!childField) return arr;
            for(var i = 0; i < arr.length; i++) {
                var item = arr[i];
                visitDfs(item, addToArray, childField, flatArr);
            }
            return flatArr;
        }
        /**
         * return filtered array
         * @param {Array} array of items
         * @param {String} user input
         * @param {Array} array of fields used to search on
         * @param {String} if not null, search will perform in flatarray of arr
         */
        export function searchArray(arr, searchTerm, fields, childField) {
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
            var filtered = flatArr.filter(function(item) {
                var i = fields.length;
                while (i--) {
                    var prop = fields[i];
                    var strProp = ("" + item[prop]).toLocaleLowerCase();
                    if (strProp.indexOf(filter) !== -1) {
                        return true;
                    };
                }
                return false;
            });
            return filtered; 
        }
        /**
         * SearchBox helper function to jump next search
         */
        export function nextSelectionSearch(selected, arr, selectedKey, isArray) {
            var current = null;
            if (isArray) {
                if (selected.length > 0) current = selected[0];
            } else if (selected !== undefined && selected !== '' && selected !== null) {
                current = selected;
            }
            if (arr.length > 0) {
                if (current) {
                    for (var i = 0; i < arr.length - 1; i++) {
                        var item = arr[i];
                        if (item[selectedKey] === current) return arr[i + 1][selectedKey];                      
                    }
                }
                if (selectedKey) return arr[0][selectedKey];
                return arr[0];
            }
            return undefined;
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
        
        export class Range {
            start: number;
            end: number;
            
            constructor(start: number, end: number) {
                if (start > end) {
                    throw new Error('start is larger than end');
                }
                
                this.start = start;
                this.end = end;
            }
            
            contains(value: number) {
                return this.start <= value && value <= this.end;
            }
            
            greaterThan(value: number) {
                return value < this.start;
            }
            
            greaterThanOrEqualTo(value: number) {
                return value <= this.start;
            }
            
            lessThan(value: number) {
                return this.end < value;
            }
            
            lessThanOrEqualTo(value: number) {
                return this.end <= value;
            }
            
            distanceFrom(value: number) {
                if (this.greaterThan(value)) {
                    return value - this.start;
                } else if (this.lessThan(value)) {
                    return value - this.end;
                } else {
                    return 0;
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
    
    
    /**
     * Utilities about jquery deferred
     */
    export module deferred {
        
        /**
         * Repeats a task with jQuery Deferred
         */
        export function repeat(configurator: (conf: repeater.IConfiguration) => void) {
            var conf = repeater.createConfiguration();
            configurator(conf);
            return repeater.begin(conf);
        }
        
        module repeater {
            export function begin(conf: IConfiguration) {
                return (<Configuration>conf).run();
            }
            
            export interface IConfiguration {
                /**
                 * Set task returns JQueryPromise.
                 */
                task(taskFunction: () => JQueryPromise<any>): IConfiguration;
                
                /**
                 * Set condition to repeat task.
                 */
                while(whileCondition: (taskResult: any) => boolean): IConfiguration;
                
                /**
                 * Set pause time as milliseconds.
                 */
                pause(pauseMilliseconds: number): IConfiguration;
            }
            
            export function createConfiguration(): IConfiguration {
                return new Configuration();
            }
            
            class Configuration implements IConfiguration {
                taskFunction: () => JQueryPromise<any>;
                whileCondition: (taskResult: any) => boolean;
                pauseMilliseconds = 0;
                
                task(taskFunction: () => JQueryDeferred<any>) {
                    this.taskFunction = taskFunction;
                    return this;
                }
                
                while(whileCondition: (taskResult: any) => boolean) {
                    this.whileCondition = whileCondition;
                    return this;
                }
                
                pause(pauseMilliseconds: number) {
                    this.pauseMilliseconds = pauseMilliseconds;
                    return this;
                }
                
                run() {
                    let dfd = $.Deferred();
                    this.repeat(dfd);
                    return dfd.promise();
                }
                
                repeat(dfd: JQueryDeferred<any>) {
                    this.taskFunction().done(res => {
                        if (this.whileCondition(res)) {
                            setTimeout(() => this.repeat(dfd), this.pauseMilliseconds);
                        } else {
                            dfd.resolve(res);
                        }
                    }).fail(res => {
                        dfd.reject(res);
                    });
                }
            }
        }
    }
    
    export var sessionStorage = new WebStorageWrapper(window.sessionStorage);
    
}