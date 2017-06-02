/// <reference path="../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var errors;
            (function (errors) {
                var ErrorsViewModel = (function () {
                    function ErrorsViewModel() {
                        var _this = this;
                        this.title = "エラー一覧";
                        this.errors = ko.observableArray([]);
                        this.errors.extend({ rateLimit: 1 });
                        this.option = ko.mapping.fromJS(new ui.option.ErrorDialogOption());
                        this.occurs = ko.computed(function () { return _this.errors().length !== 0; });
                        this.allResolved = $.Callbacks();
                        this.errors.subscribe(function () {
                            if (_this.errors().length === 0) {
                                _this.allResolved.fire();
                            }
                        });
                        this.allResolved.add(function () {
                            _this.hide();
                        });
                    }
                    ErrorsViewModel.prototype.closeButtonClicked = function () {
                    };
                    ErrorsViewModel.prototype.open = function () {
                        this.option.show(true);
                    };
                    ErrorsViewModel.prototype.hide = function () {
                        this.option.show(false);
                    };
                    ErrorsViewModel.prototype.addError = function (error) {
                        var _this = this;
                        // defer無しでerrorsを呼び出すと、なぜか全てのKnockoutBindingHandlerのupdateが呼ばれてしまうので、
                        // 原因がわかるまでひとまずdeferを使っておく
                        _.defer(function () {
                            var duplicate = _.filter(_this.errors(), function (e) { return e.$control.is(error.$control) && e.messageText == error.messageText; });
                            if (duplicate.length == 0) {
                                if (typeof error.message === "string") {
                                    error.messageText = error.message;
                                    error.message = "";
                                }
                                else {
                                    //business exception
                                    if (error.message.message) {
                                        error.messageText = error.message.message;
                                        error.message = error.message.messageId != null && error.message.messageId.length > 0 ? error.message.messageId : "";
                                    }
                                    else {
                                        if (error.$control.length > 0) {
                                            var controlNameId = error.$control.eq(0).attr("data-name");
                                            if (controlNameId) {
                                                error.messageText = nts.uk.resource.getMessage(error.message.messageId, nts.uk.resource.getText(controlNameId), error.message.messageParams);
                                            }
                                            else {
                                                error.messageText = nts.uk.resource.getMessage(error.message.messageId, error.message.messageParams);
                                            }
                                        }
                                        else {
                                            error.messageText = nts.uk.resource.getMessage(error.message.messageId);
                                        }
                                        error.message = error.message.messageId;
                                    }
                                }
                                _this.errors.push(error);
                            }
                        });
                    };
                    ErrorsViewModel.prototype.hasError = function () {
                        return this.occurs();
                    };
                    ErrorsViewModel.prototype.clearError = function () {
                        $(".error").removeClass('error');
                        this.errors.removeAll();
                    };
                    ErrorsViewModel.prototype.removeErrorByElement = function ($element) {
                        var _this = this;
                        // addErrorと同じ対応
                        _.defer(function () {
                            var removeds = _.filter(_this.errors(), function (e) { return e.$control.is($element); });
                            _this.errors.removeAll(removeds);
                        });
                    };
                    return ErrorsViewModel;
                }());
                errors.ErrorsViewModel = ErrorsViewModel;
                var ErrorHeader = (function () {
                    function ErrorHeader(name, text, width, visible) {
                        this.name = name;
                        this.text = text;
                        this.width = width;
                        this.visible = visible;
                    }
                    return ErrorHeader;
                }());
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
//# sourceMappingURL=errors.js.map