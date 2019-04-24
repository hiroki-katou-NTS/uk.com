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

    public rawUrl: string;
    public queryString: QueryString;

    constructor(url: string) {
        this.rawUrl = url.split('?')[0];
        this.queryString = QueryString.parseUrl(url);
    }

    public serialize() {
        if (this.queryString.hasItems()) {
            return this.rawUrl + '?' + this.queryString.serialize();
        } else {
            return this.rawUrl;
        }
    }

    public mergeRelativePath(relativePath) {
        let stack = this.rawUrl.split('/');
        let parts = relativePath.split('?')[0].split('/');
        let queryStringToAdd = QueryString.parseUrl(relativePath);

        // 最後のファイル名は除外
        // (最後がフォルダ名でしかも / で終わっていない場合は考慮しない)
        stack.pop();

        // relativePathの先頭が '/' の場合、それを取り除く
        if (parts[0] === '') {
            parts.shift();
        }

        for (let i = 0; i < parts.length; i++) {
            if (parts[i] === '.') {
                continue;
            }
            if (parts[i] === '..') {
                stack.pop();
            } else {
                stack.push(parts[i]);
            }
        }

        let queryStringParts = queryStringToAdd.hasItems()
            ? '?' + queryStringToAdd.serialize()
            : '';

        return new Locator(stack.join('/') + queryStringParts);
    }
}

export class QueryString {

    public items: { [key: string]: any };

    constructor() {
        this.items = {};
    }

    public static parseUrl(url: string): QueryString {
        let instance = new QueryString();
        let queryString = url.split('?')[1];
        if (queryString) {
            let queryEntries = queryString.split('&');
            for (let i = 0; i < queryEntries.length; i++) {
                let entryParts = queryEntries[i].split('=');
                instance.set(entryParts[0], entryParts[1]);
            }
        }
        return instance;
    }

    public static build(entriesObj: { [key: string]: any }) {
        let instance = new QueryString();

        for (let key in entriesObj) {
            instance.set(key, entriesObj[key]);
        }

        return instance;
    }

    public get(key: string): any { 
        return this.items[key];
    }

    public set(key: string, value: any) {
        if (key === null || key === undefined || key === '') {
            return;
        }
        this.items[key] = value;
    }

    public remove(key: string) {
        delete this.items[key];
    }

    public mergeFrom(otherObj: QueryString) {
        for (let otherKey in otherObj.items) {
            this.set(otherKey, otherObj.items[otherKey]);
        }
    }

    public count() {
        let count = 0;
        for (let key in this.items) {
            count++;
        }
        return count;
    }

    public hasItems() {
        return this.count() !== 0;
    }

    public serialize() {
        let entryStrings = [];
        for (let key in this.items) {
            entryStrings.push(key + '=' + this.items[key]);
        }

        return entryStrings.join('&');
    }
}

export namespace location {
    export let current = new Locator(window.location.href);
    export let appRoot = current.mergeRelativePath(viewcontext.rootPath);
    export let siteRoot = appRoot.mergeRelativePath('../');
    export let ajaxRootDir = 'webapi/';

    let currentAppName = _.takeRight(appRoot.serialize().split('/'), 2)[0];
    export let currentAppId: WebAppId;
    for (let id in WEB_APP_NAME) {
        if (currentAppName === WEB_APP_NAME[id]) {
            currentAppId =  id as WebAppId;
            break;
        }
    }
}

export namespace login {
    let STORAGE_KEY_USED_LOGIN_PAGE = 'nts.uk.request.login.STORAGE_KEY_USED_LOGIN_PAGE';
    let STORAGE_KEY_SERIALIZED_SESSION = 'nts.uk.request.login.STORAGE_KEY_SERIALIZED_SESSION';
        
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

export namespace csrf {
    let STORAGE_KEY_CSRF_TOKEN = 'nts.uk.request.csrf.STORAGE_KEY_CSRF_TOKEN';
    
    let csrfToken = cookie.get('nts.arc.CSRF_TOKEN');
    if (!_.isEmpty(csrfToken)) {
        sessionStorage.setItem(STORAGE_KEY_CSRF_TOKEN, csrfToken);
    }
    
    export function getToken() {
        /** TODO: now, write token in client logic is not correct, and have not time for fix. */
        let csrfToken = sessionStorage.getItem(STORAGE_KEY_CSRF_TOKEN);
        if (_.isNil(csrfToken)) {
            return 'FIXED_TOKEN';
        }
        return csrfToken;
    }
}