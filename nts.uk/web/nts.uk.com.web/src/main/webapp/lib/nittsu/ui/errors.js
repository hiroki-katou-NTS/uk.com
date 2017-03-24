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
                            var duplicate = _.filter(_this.errors(), function (e) { return e.$control.is(error.$control) && e.message == error.message; });
                            if (duplicate.length == 0)
                                _this.errors.push(error);
                        });
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
                function removeByElement($control) {
                    errorsViewModel().removeErrorByElement($control);
                }
                errors.removeByElement = removeByElement;
            })(errors = ui.errors || (ui.errors = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=errors.js.map