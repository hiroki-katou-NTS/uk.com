module nts.uk.request {
    
    export var STORAGE_KEY_TRANSFER_DATA = "nts.uk.request.STORAGE_KEY_TRANSFER_DATA";

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

    /**
     * URL and QueryString
     */
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
    
    export function ajax(path: string, data?: any, options?: any) {
        var dfd = $.Deferred();
        options = options || {};

        if (typeof data === 'object') {
            data = JSON.stringify(data);
        }

        var webserviceLocator = location.ajaxRoot.mergeRelativePath(path);

        $.ajax({
            type: options.method || 'POST',
            contentType: options.contentType || 'application/json',
            url: webserviceLocator.serialize(),
            dataType: options.dataType || 'json',
            data: data
        }).done(function (res) {
            dfd.resolve(res);
        });

        return dfd.promise();
    }
    
    export function jump(path: string, data: any) {
        
        uk.sessionStorage.setItemAsJson(STORAGE_KEY_TRANSFER_DATA, data);
       
        window.location.href = resolvePath(path);
    }
     
    export function resolvePath(path: string) {
        var destination: Locator;
        if (path.charAt(0) === '/') {
            destination = location.appRoot.mergeRelativePath(path);
        } else {
            destination = location.current.mergeRelativePath(path);
        }
        
        return destination.rawUrl;
    }
    
    export module location {
        export var current = new Locator(window.location.href);
        export var appRoot = current.mergeRelativePath(__viewContext.rootPath);
        export var ajaxRoot = appRoot.mergeRelativePath('webapi/')
    };

}