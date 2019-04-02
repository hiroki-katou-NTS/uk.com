import Vue, { ComponentOptions } from "vue";
import { WebAppId } from '@app/utils/request';
import { ErrorHandler } from "vue-router/types/router";

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
    allHalf?: boolean;
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
            get(url: string): Promise<{}>;
            get(pg: WebAppId, url: string): Promise<{}>;
            post(url: string, data?: any): Promise<{}>;
            post(pg: WebAppId, url: string, data?: any): Promise<{}>;
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
        $i18n(resr: string): string;
        $i18n(resr: string, param: { [key: string]: string }): string;
        $close(): void;
        $close(data: any): void;
        $mask(act: 'hide' | 'show' | 'showonce'): {
            on: (click: () => void, hide?: () => void) => void;
        };
        $goto(name: string, params?: { [key: string]: any; }, onComplete?: Function, onAbort?: ErrorHandler): void;
        $goto(location: { name: string, params?: { [key: string]: any; } }, onComplete?: Function, onAbort?: ErrorHandler): void;
        $modal(name: string, params?: any, options?: IModalOptions): Promise<{}>;
        $modal(name: ComponentOptions<Vue>, params?: any, options?: IModalOptions): Promise<{}>;
        $valid: boolean;
        $errors: {
            [name: string]: {
                [rule: string]: string;
            }
        };
        $validate(): boolean;
        $validate(name: string): boolean;
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