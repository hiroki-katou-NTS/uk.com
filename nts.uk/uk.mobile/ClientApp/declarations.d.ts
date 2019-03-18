import Vue, { ComponentOptions } from "vue";

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
        $http: {
            get: (url: string) => Promise<{}>;
            post: (url: string, data?: any) => Promise<{}>;
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
        $modal: (name: string, params?: any, options?: IModalOptions) => {
            onClose: (callback: (data?: any) => void) => void;
        };
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
    }

    export interface VueConstructor<V extends Vue = Vue> {
        util: {
            defineReactive: (obj: any, key: string, val: any) => void;
            extend: (to: any, from: any) => any;
        };
    }
}

export declare type VueClass<V> = {
    new(...args: any[]): V & Vue;
} & typeof Vue;