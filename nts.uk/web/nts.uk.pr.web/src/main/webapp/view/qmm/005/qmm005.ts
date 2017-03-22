class TextEditorBindingHandler implements KnockoutBindingHandler {
    constructor() {
    }

    init(element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        ko.bindingHandlers['ntsTextEditor'].init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext);

        if (valueAccessor().valueUpdate) {
            switch (valueAccessor().valueUpdate) {
                case 'keyup':
                    element.onkeyup = function() { valueAccessor().value(this.value); };
                    break;
                case 'keypress':
                    element.onkeypress = function() { valueAccessor().value(this.value); };
                    break;
                case 'afterkeydown':
                    element.onkeydown = function() { valueAccessor().value(this.value); };
                    break;
            }
        }
    }

    update(element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        ko.bindingHandlers['ntsTextEditor'].update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext);
    }
}

class CheckBoxWithHelpBindingHandler implements KnockoutBindingHandler {
    constructor() {
    }

    init(element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        ko.bindingHandlers['ntsCheckBox'].init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext);
        if (valueAccessor().helper) {
            var span = document.createElement('span');
            span.innerHTML = '(' + valueAccessor().helper + ')';
            span.setAttribute('class', 'label helper');
            element.getElementsByTagName('label')[0].appendChild(span);
        }
    }

    update(element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        ko.bindingHandlers['ntsCheckBox'].update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext);
    }
}

ko.bindingHandlers['ntsTextEditor2'] = new TextEditorBindingHandler();
ko.bindingHandlers['ntsCheckBox2'] = new CheckBoxWithHelpBindingHandler();

Date.prototype["getDayJP"] = function() {
    return ['日', '月', '火', '水', '木', '金', '土'][this.getDay()];
}

module qmm005.common {
    export function webapi(): any {
        return {
            'qmm005a': {
                'update': 'pr/core/qmm005a/update',
                'getdata': 'pr/core/qmm005a/getdata'
            },
            'qmm005c': {
                'insert': 'pr/core/qmm005c/insert'
            },
            'qmm005d': {
                'update': 'pr/core/qmm005d/update',
                'getdata': 'pr/core/qmm005d/getdata'
            }
        }
    }

    export function formatNumber(num: number, lenght: number): string {
        let len = num.toString().length;
        if (len >= lenght) {
            return num.toString();
        }

        let str: string = '';
        for (let i = 1; i <= lenght - len; i++) {
            str += '0';
        }

        return str + num;
    }

    interface ISelectItem {
        index: any;
        label: string;
    }

    export class SelectItem {
        index: any;
        label: string;
        constructor(param: ISelectItem) {
            let self = this;
            self.index = param.index;
            self.label = param.label;
        }
    }

    interface ICheckBoxItem {
        text: string;
        helper?: string;
        enable?: boolean;
        checked?: boolean;
    }

    export class CheckBoxItem {
        text: string;
        helper: string;
        enable: KnockoutObservable<boolean>;
        checked: KnockoutObservable<boolean>;
        constructor(param: ICheckBoxItem) {
            let self = this;
            self.text = param.text;
            self.helper = param.helper;
            self.enable = ko.observable(param.enable || true);
            self.checked = ko.observable(param.checked || false);
        }
    }
}

// for develop
var _ref = (window.location.href.indexOf('localhost') == -1) && new Date().getTime() || 'v1.0.0';

var route = window.location.href
    .slice(0, window.location.href.lastIndexOf('/'))
    .slice(window.location.href.indexOf('/view/') + 6)
    .replace('/', '')
    .replace(/\//g, '.');

document.writeln("<link rel='stylesheet' type='text/css' href='" + route + ".style.css?ref=" + _ref + "' />");
document.writeln("<script type='text/javascript' src='" + route + ".service.js?ref=" + _ref + "'></script>");
document.writeln("<script type='text/javascript' src='" + route + ".viewmodel.js?ref=" + _ref + "'></script>");
document.writeln("<script type='text/javascript' src='" + route + ".start.js?ref=" + _ref + "'></script>");