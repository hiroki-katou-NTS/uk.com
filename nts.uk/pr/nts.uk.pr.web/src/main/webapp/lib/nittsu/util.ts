module nts.uk.util {

    /**
     * 常にtrueを返す関数が必要になったらこれ
     */
    export function alwaysTrue() {
        return true;
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