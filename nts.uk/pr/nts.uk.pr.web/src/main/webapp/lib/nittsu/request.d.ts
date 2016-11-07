declare module nts.uk.request {
    class QueryString {
        items: {
            [key: string]: any;
        };
        constructor();
        static parseUrl(url: string): QueryString;
        static build(entriesObj: {
            [key: string]: any;
        }): QueryString;
        get(key: string): any;
        set(key: string, value: any): void;
        remove(key: string): void;
        mergeFrom(otherObj: QueryString): void;
        count(): number;
        hasItems(): boolean;
        serialize(): string;
    }
    class Locator {
        rawUrl: string;
        queryString: QueryString;
        constructor(url: string);
        serialize(): string;
    }
}
