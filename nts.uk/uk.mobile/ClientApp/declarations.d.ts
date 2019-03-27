import Vue, { ComponentOptions } from "vue";
import { WebAppId } from '@app/utils/request';

declare interface Window {
    Reflect: any;
}

interface IFetchOption {
    url: string;
    type?: 'url' | 'form' | 'json';
    method: 'get' | 'post' | 'push' | 'patch' | 'delete';
    data?: any;
    headers?: any;
}

declare interface IRule {
    url?: boolean;
    email?: boolean;
    alpha?: boolean;
    alphaNum?: boolean;
    numeric?: boolean;
    integer?: boolean;
    decimal?: boolean;
    required?: boolean;
    minLength?: number;
    maxLength?: number;
    minValue?: Date | number;
    maxValue?: Date | number;
    between?: Array<Date | number>;
    not?: Array<Date | number | string>;
    [rule: string]: Array<Date | number | string> | Date | number | boolean | IRule | string | {
        test: RegExp | Function;
        message: string;
    };
}

declare interface IModalOptions {
    type?: 'modal' | 'popup';
    size?: 'lg' | 'md' | 'sm' | 'xs';
    title?: string;
    animate?: {
        show?: string;
        hide?: string;
    };
}

declare module "vue/types/options" {
    interface ComponentOptions<V extends Vue> {
        route?: string | {
            url: string;
            parent?: string;
            components?: Array<{ [name: string]: any }>;
        };
        style?: string;
        resource?: {
            [lang: string]: {
                [resources: string]: string;
            }
        };
        validations?: {
            [name: string]: IRule;
        },
        markdown?: string | {
            [lang: string]: string;
        };
        constraints?: Array<string>;
    }
}

declare module "vue/types/vue" {
    interface Vue {
        pgName: string;
        $http: {
            get: (pgOrUrl: WebAppId | string, url?: string) => Promise<{}>;
            post: (pgOrUrl: WebAppId | string, urlOrData?: string | any, data?: any) => Promise<{}>;
            headers: {
                [header: string]: string;
            },
            file: {
                live: (fileId: string) => string;
                upload: (form: FormData) => Promise<{}>;
                download: (fileId: string) => Promise<{}>;
            },
            async: {
                info: (taskId: string) => Promise<{}>;
                cancel: (taskId: string) => Promise<{}>;
            }
        };
        $i18n: (resr: string, param?: { [key: string]: string }) => string;
        $close: (data?: any) => void;
        $mask: (act: 'hide' | 'show' | 'showonce') => {
            on: (click: () => void, hide?: () => void) => void;
        };
        $goto: (location: { name: string; params?: { [key: string]: any; }; }) => void;
        $modal: (name: (string | ComponentOptions<Vue>), params?: any, options?: IModalOptions) => Promise<{}>;
        $valid: boolean;
        $errors: {
            [name: string]: {
                [rule: string]: string;
            }
        };
        $validate: (name?: string) => boolean;
        $updateValidator: (rule: IRule) => void;
        validations: {
            [name: string]: IRule;
        };
        $toastAlert: (msgOrResourceId: string, options?: any) => Promise<{}>;
        $toastError: (msgOrResourceId: string, options?: any) => Promise<{}>;
        $toastConfirm: (msgOrResourceId: string, options?: any) => Promise<{}>;
        $dialogError: (msgOrResourceId: string | object, options?: any) => void;
        $dialogInfo: (msgOrResourceId: string | object, options?: any) => void;
        $dialogWarn: (msgOrResourceId: string | object, options?: any) => void;
        $dialogConfirm: (msgOrResourceId: string | object, options?: any) => void;
    }

    export interface VueConstructor<V extends Vue = Vue> {
        util: {
            defineReactive: (obj: any, key: string, val: any) => void;
            extend: (to: any, from: any) => any;
        };
    }
}

declare module "vue-router/types/router" {
    export interface VueRouter {
        goto: (location: { name: string; params: { [key: string]: any } }, onComplete?: Function, onAbort?: ErrorHandler) => void;
    }
}

export declare type VueClass<V> = {
    new(...args: any[]): V & Vue;
} & typeof Vue;