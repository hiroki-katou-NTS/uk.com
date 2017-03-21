var TextEditorBindingHandler = (function () {
    function TextEditorBindingHandler() {
    }
    TextEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
        ko.bindingHandlers['ntsTextEditor'].init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext);
        if (valueAccessor().valueUpdate) {
            switch (valueAccessor().valueUpdate) {
                case 'keyup':
                    element.onkeyup = function () { valueAccessor().value(this.value); };
                    break;
                case 'keypress':
                    element.onkeypress = function () { valueAccessor().value(this.value); };
                    break;
                case 'afterkeydown':
                    element.onkeydown = function () { valueAccessor().value(this.value); };
                    break;
            }
        }
    };
    TextEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
        ko.bindingHandlers['ntsTextEditor'].update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext);
    };
    return TextEditorBindingHandler;
}());
var CheckBoxWithHelpBindingHandler = (function () {
    function CheckBoxWithHelpBindingHandler() {
    }
    CheckBoxWithHelpBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
        ko.bindingHandlers['ntsCheckBox'].init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext);
        if (valueAccessor().helper) {
            var span = document.createElement('span');
            span.innerHTML = '(' + valueAccessor().helper + ')';
            span.setAttribute('class', 'label helper');
            element.getElementsByTagName('label')[0].appendChild(span);
        }
    };
    CheckBoxWithHelpBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
        ko.bindingHandlers['ntsCheckBox'].update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext);
    };
    return CheckBoxWithHelpBindingHandler;
}());
var CheckboxHasNotLabelBindingHandler = (function () {
    function CheckboxHasNotLabelBindingHandler() {
    }
    CheckboxHasNotLabelBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
        valueAccessor().text = valueAccessor().text || '';
        ko.bindingHandlers['ntsCheckBox'].init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext);
        element.getElementsByTagName('span')[1].remove();
    };
    CheckboxHasNotLabelBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
        ko.bindingHandlers['ntsCheckBox'].update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext);
    };
    return CheckboxHasNotLabelBindingHandler;
}());
ko.bindingHandlers['ntsTextEditor2'] = new TextEditorBindingHandler();
ko.bindingHandlers['ntsCheckBox2'] = new CheckBoxWithHelpBindingHandler();
ko.bindingHandlers['ntsCheckBox3'] = new CheckboxHasNotLabelBindingHandler();
var qmm005;
(function (qmm005) {
    var common;
    (function (common) {
        function webapi() {
            return {
                'qmm005a': {
                    'getdata': 'pr/core/qmm005a/getdata',
                    'select': {
                        'payday': 'pr/core/qmm005a/payday',
                        'paydayprocessing': 'pr/core/qmm005a/paydayprocessing'
                    }
                },
                'qmm005c': {
                    'insert': 'pr/core/qmm005c/insert'
                }
            };
        }
        common.webapi = webapi;
        function formatNumber(num, lenght) {
            var len = num.toString().length;
            if (len >= lenght) {
                return num.toString();
            }
            var str = '';
            for (var i = 1; i <= lenght - len; i++) {
                str += '0';
            }
            return str + num;
        }
        common.formatNumber = formatNumber;
        var SelectItem = (function () {
            function SelectItem(param) {
                var self = this;
                self.index = param.index;
                self.label = param.label;
            }
            return SelectItem;
        }());
        common.SelectItem = SelectItem;
        var CheckBoxItem = (function () {
            function CheckBoxItem(param) {
                var self = this;
                self.text = param.text;
                self.helper = param.helper;
                self.enable = ko.observable(param.enable || true);
                self.checked = ko.observable(param.checked || false);
            }
            return CheckBoxItem;
        }());
        common.CheckBoxItem = CheckBoxItem;
    })(common = qmm005.common || (qmm005.common = {}));
})(qmm005 || (qmm005 = {}));
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
