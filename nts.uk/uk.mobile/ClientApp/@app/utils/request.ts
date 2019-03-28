import { viewcontext } from '@app/utils/viewcontext';
import { sessionStorage, cookie } from '@app/utils';
import { _ } from '@app/provider';

export type WebAppId = 'comjs' | 'com' | 'pr' | 'at' | 'mobi';
export const WEB_APP_NAME = {
    comjs: 'nts.uk.com.js.web',
    com: 'nts.uk.com.web',
    pr: 'nts.uk.pr.web',
    at: 'nts.uk.at.web',
    mobi: 'nts.uk.mobile.web'
};

export class Locator {

    rawUrl: string;
    queryString: QueryString;

    constructor(url: string) {
        this.rawUrl = url.split('?')[0];
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
        var parts = relativePath.split('?')[0].split('/');
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

        var queryStringParts = queryStringToAdd.hasItems()
            ? '?' + queryStringToAdd.serialize()
            : '';

        return new Locator(stack.join('/') + queryStringParts);
    }
}

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

export module location {
    export var current = new Locator(window.location.href);
    export var appRoot = current.mergeRelativePath(viewcontext.rootPath);
    export var siteRoot = appRoot.mergeRelativePath('../');
    export var ajaxRootDir = 'webapi/';

    var currentAppName = _.takeRight(appRoot.serialize().split('/'), 2)[0];
    export var currentAppId: WebAppId;
    for (var id in WEB_APP_NAME) {
        if (currentAppName === WEB_APP_NAME[id]) {
            currentAppId = <WebAppId>id;
            break;
        }
    }
}

export module login {
    let STORAGE_KEY_USED_LOGIN_PAGE = "nts.uk.request.login.STORAGE_KEY_USED_LOGIN_PAGE";
    let STORAGE_KEY_SERIALIZED_SESSION = "nts.uk.request.login.STORAGE_KEY_SERIALIZED_SESSION";
        
        export function keepUsedLoginPage(webAppId: WebAppId, path: string);
        export function keepUsedLoginPage(url?: string) {
            if (arguments.length === 2) {
                let loginLocator = location.siteRoot
                                        .mergeRelativePath(WEB_APP_NAME[arguments[0]] + '/')
                                        .mergeRelativePath(arguments[1]);
                keepUsedLoginPage.apply(null, [ loginLocator.serialize() ]);
                return;
            }
            
            if (url === undefined) {
                keepUsedLoginPage.apply(null, [ location.current.serialize() ]);
                return;
            }
            
            sessionStorage.setItem(STORAGE_KEY_USED_LOGIN_PAGE, url);
        }
        
        export function jumpToUsedLoginPage() {
            // sessionStorage.getItem(STORAGE_KEY_USED_LOGIN_PAGE).ifPresent(path => {
            //     window.location.href = path;
            // }).ifEmpty(() => {
                this.$router.push({ name: 'login' });
            // });
        }
        
        export function keepSerializedSession(): Promise<any> {
//            request.ajax("/shr/web/session/serialize").done(res => {
//                uk.sessionStorage.setItem(STORAGE_KEY_SERIALIZED_SESSION, res);
//                dfd.resolve();
//            });
//            
            return new Promise((resolve, reject) => { resolve(); });
        }
        
        export function restoreSessionTo(webAppId: WebAppId): Promise<any> {
            return new Promise((resolve, reject) => { resolve(); });
            
//            let serializedTicket = uk.sessionStorage.getItem(STORAGE_KEY_SERIALIZED_SESSION).get();
//            return (<any>request).ajax(webAppId, "/shr/web/session/restore", serializedTicket, null, false);
        }
}

export module csrf {
    let STORAGE_KEY_CSRF_TOKEN = "nts.uk.request.csrf.STORAGE_KEY_CSRF_TOKEN";
    
    cookie.get("nts.arc.CSRF_TOKEN")
        .ifPresent(csrfToken => {
            sessionStorage.setItem(STORAGE_KEY_CSRF_TOKEN, csrfToken);
        });
    
    export function getToken() {
        return sessionStorage.getItem(STORAGE_KEY_CSRF_TOKEN).orElse("");
    }
}