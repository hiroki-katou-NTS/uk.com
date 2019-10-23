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
    required?: boolean;
    min?: number | Date;
    max?: number | Date;
    mantissaMaxLength?: number;
    minLength?: number;
    maxLength?: number;
    constraint?: string;
    charType?: 'Any' | 'Kana' | 'AnyHalfWidth' | 'AlphaNumeric' | 'Numeric';
    valueType?: 'String' | 'Decimal' | 'Integer' | 'HalfInt' | 'Date' | 'Time' | 'Clock' | 'Duration' | 'TimePoint';
    [rule: string]: Array<Date | number | string> | Date | number | boolean | IRule | string | {
        test: RegExp | Function;
        message: string;
    } | {
        test: RegExp | Function;
        messageId: string;
    };
}

declare interface IModalOptions {
    type?: 'modal' | 'popup' | 'info';
    size?: 'lg' | 'md' | 'sm' | 'xs';
    title?: string;
    style?: string;
    animate?: 'up' | 'right' | 'down' | 'left';
    opacity?: number;
}

declare interface IValidations {
    [name: string]: IValidations | IRule
}

declare module "vue/types/options" {
    interface ComponentOptions<V extends Vue> {
        route?: string | {
            url: string;
            parent?: string;
        };
        style?: string;
        resource?: {
            [lang: string]: {
                [resources: string]: string;
            }
        };
        validations?: {
            [name: string]: IRule | IValidations;
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
        $i18n(resr: string, param: string): string;
        $i18n(resr: string, param: string[]): string;
        $i18n(resr: string, param: { [key: string]: string }): string;
        $close(): void;
        $close(data: any): void;
        $mask(act: 'hide'): void;
        $mask(act: 'show', opacity?: number): {
            on: (click: () => void, hide?: () => void) => void;
        };
        $goto(name: string, params?: { [key: string]: any; }, onComplete?: Function, onAbort?: ErrorHandler): void;
        $goto(location: { name: string, params?: { [key: string]: any; } }, onComplete?: Function, onAbort?: ErrorHandler): void;
        $modal: {
            (name: string, params?: any, options?: IModalOptions): Promise<{}>;
            (component: ComponentOptions<Vue>, params?: any, options?: IModalOptions): Promise<{}>;
            warn(msg: string): Promise<{}>;
            warn(resource: { messageId: string, messageParams?: string[] | { [key: string]: string } }): Promise<{}>;
            info(msg: string): Promise<{}>;
            info(resource: { messageId: string, messageParams?: string[] | { [key: string]: string } }): Promise<{}>;
            error(msg: string): Promise<{}>;
            error(resource: { messageId: string, messageParams?: string[] | { [key: string]: string } }): Promise<{}>;
            confirm(msg: string, style?: 'normal' | 'process' | 'danger'): Promise<{}>;
            confirm(resource: { messageId: string, messageParams?: string[] | { [key: string]: string } }, style?: 'normal' | 'process' | 'danger'): Promise<{}>;
        };
        $picker(value: {
            [key: string]: any
        }, dataSources: {
            [key: string]: any[]
        }, options?: {
            text?: string;
            value?: string;
            title?: string;
            required?: boolean;
            onSelect?: (selects: { [key: string]: any }, pkr: { dataSources: { [key: string]: any[] } }) => void;
        }): Promise<{}>;
        $valid: boolean;
        $errors: {
            [name: string]: {
                [rule: string]: string;
            }
        };
        $validate(): void;
        $validate(act: 'clear'): void;
        $validate(name: string): boolean;
        $updateValidator(rule: IRule): void;
        $updateValidator(name: string, rule: IRule): void;
        validations: {
            [name: string]: IRule;
        };
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