/// <reference path="../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var errors;
            (function (errors) {
                class ErrorsViewModel {
                    constructor() {
                        this.title = "エラー一覧";
                        this.errors = ko.observableArray([]);
                        this.errors.extend({ rateLimit: 1 });
                        this.option = ko.mapping.fromJS(new ui.option.ErrorDialogOption());
                        this.occurs = ko.computed(() => this.errors().length !== 0);
                        this.allResolved = $.Callbacks();
                        this.errors.subscribe(() => {
                            if (this.errors().length === 0) {
                                this.allResolved.fire();
                            }
                        });
                        this.allResolved.add(() => {
                            this.hide();
                        });
                    }
                    closeButtonClicked() {
                    }
                    open() {
                        this.option.show(true);
                    }
                    hide() {
                        this.option.show(false);
                    }
                    addError(error) {
                        // defer無しでerrorsを呼び出すと、なぜか全てのKnockoutBindingHandlerのupdateが呼ばれてしまうので、
                        // 原因がわかるまでひとまずdeferを使っておく
                        //_.defer(() => {
                        var duplicate = _.filter(this.errors(), e => e.$control.is(error.$control) && e.message == error.message);
                        if (duplicate.length == 0)
                            this.errors.push(error);
                        //});
                    }
                    hasError() {
                        return this.occurs();
                    }
                    clearError() {
                        $(".error").removeClass('error');
                        this.errors.removeAll();
                    }
                    removeErrorByElement($element) {
                        // addErrorと同じ対応
                        //_.defer(() => {
                        var removeds = _.filter(this.errors(), e => e.$control.is($element));
                        this.errors.removeAll(removeds);
                        //});
                    }
                }
                errors.ErrorsViewModel = ErrorsViewModel;
                class ErrorHeader {
                    constructor(name, text, width, visible) {
                        this.name = name;
                        this.text = text;
                        this.width = width;
                        this.visible = visible;
                    }
                }
                errors.ErrorHeader = ErrorHeader;
                /**
                 *  Public API
                **/
                function errorsViewModel() {
                    return nts.uk.ui._viewModel.kiban.errorDialogViewModel;
                }
                errors.errorsViewModel = errorsViewModel;
                function show() {
                    errorsViewModel().open();
                }
                errors.show = show;
                function hide() {
                    errorsViewModel().hide();
                }
                errors.hide = hide;
                function add(error) {
                    errorsViewModel().addError(error);
                }
                errors.add = add;
                function hasError() {
                    return errorsViewModel().hasError();
                }
                errors.hasError = hasError;
                function clearAll() {
                    errorsViewModel().clearError();
                }
                errors.clearAll = clearAll;
                function removeByElement($control) {
                    errorsViewModel().removeErrorByElement($control);
                }
                errors.removeByElement = removeByElement;
            })(errors = ui.errors || (ui.errors = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
