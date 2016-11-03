module nts.uk.request {

    export class QueryString {

        items: { [key: string]: any };

        constructor() {
            this.items = {};
        }

        static parseUrl(url: string): QueryString {
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

        static build(entriesObj: { [key: string]: any }) {
            var instance = new QueryString();

            for (var key in entriesObj) {
                instance.set(key, entriesObj[key]);
            }

            return instance;
        }

        get(key: string): any {
            return this.items[key];
        }

        set(key: string, value: any) {
            if (key === null || key === undefined || key === '') {
                return;
            }
            this.items[key] = value;
        }

        remove(key: string) {
            delete this.items[key];
        }

        mergeFrom(otherObj: QueryString) {
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

    export class Locator {

        rawUrl: string;
        queryString: QueryString;

        constructor(url: string) {
            this.rawUrl = url;
            this.queryString = QueryString.parseUrl(url);
        }

        serialize() {
            if (this.queryString.hasItems()) {
                return this.rawUrl + '?' + this.queryString.serialize();
            } else {
                return this.rawUrl;
            }
        }
    }

}