declare module nts.uk.util {
    /**
     * 常にtrueを返す関数が必要になったらこれ
     */
    function alwaysTrue(): boolean;
    /**
     * Returns true if the target is null or undefined.
     */
    function isNullOrUndefined(target: any): boolean;
    /**
     * Generate random identifier string (UUIDv4)
     */
    function randomId(): string;
    /**
     * Returns true if current window is in frame.
     */
    function isInFrame(): boolean;
    /**
     * valueMaybeEmptyがnullまたはundefinedの場合、defaultValueを返す。
     * そうでなければ、valueMaybeEmptyを返す。
     */
    function orDefault(valueMaybeEmpty: any, defaultValue: any): any;
    /**
     * Returns true if expects contains actual.
     */
    function isIn(actual: any, expects: Array<any>): boolean;
    /**
     * Like Java Optional
     */
    module optional {
        function of<V>(value: V): Optional<V>;
        function empty(): Optional<any>;
        class Optional<V> {
            value: V;
            constructor(value: V);
            ifPresent(consumer: (value: V) => {}): this;
            ifEmpty(action: () => {}): this;
            map<M>(mapper: (value: V) => M): Optional<M>;
            isPresent(): boolean;
            get(): V;
            orElse(stead: V): V;
            orElseThrow(errorBuilder: () => Error): void;
        }
    }
}
