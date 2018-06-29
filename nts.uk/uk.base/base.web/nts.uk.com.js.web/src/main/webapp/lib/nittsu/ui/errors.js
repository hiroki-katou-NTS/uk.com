var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var errors;
            (function (errors_1) {
                var ErrorsViewModel = (function () {
                    function ErrorsViewModel(dialogOptions) {
                        var _this = this;
                        this.title = "エラー一覧";
                        this.errors = ko.observableArray([]);
                        this.errors.extend({ rateLimit: 1 });
                        this.gridErrors = ko.observableArray([]);
                        this.displayErrors = !uk.util.isNullOrUndefined(dialogOptions) && dialogOptions.forGrid ? this.gridErrors : this.errors;
                        this.option = ko.observable(ko.mapping.fromJS(new ui.option.ErrorDialogOption(dialogOptions)));
                        this.occurs = ko.computed(function () { return _this.errors().length !== 0 || _this.gridErrors().length !== 0; });
                        this.allResolved = $.Callbacks();
                        this.allCellsResolved = $.Callbacks();
                        this.option().show.extend({ notify: "always" });
                        this.errors.subscribe(function () {
                            if (_this.errors().length === 0) {
                                _this.allResolved.fire();
                            }
                        });
                        this.allResolved.add(function () {
                            _this.hide();
                        });
                        this.gridErrors.subscribe(function () {
                            if (_this.gridErrors().length === 0) {
                                _this.allCellsResolved.fire();
                            }
                        });
                        this.allCellsResolved.add(function () {
                            _this.hide();
                        });
                    }
                    ErrorsViewModel.prototype.closeButtonClicked = function () {
                        this.option().show(false);
                    };
                    ErrorsViewModel.prototype.open = function () {
                        this.option().show(true);
                    };
                    ErrorsViewModel.prototype.hide = function () {
                        this.option().show(false);
                    };
                    ErrorsViewModel.prototype.addError = function (error) {
                        var duplicate = _.filter(this.errors(), function (e) { return e.$control.is(error.$control)
                            && (typeof error.message === "string" ? e.messageText === error.message : e.messageText === error.messageText); });
                        if (duplicate.length == 0) {
                            if (typeof error.message === "string") {
                                error.messageText = error.message;
                            }
                            else {
                                if (error.message.message) {
                                    error.messageText = error.message.message;
                                    error.errorCode = error.message.messageId != null && error.message.messageId.length > 0 ? error.message.messageId : "";
                                }
                                else {
                                    if (error.$control.length > 0) {
                                        var controlNameId = error.$control.eq(0).attr("data-name");
                                        if (controlNameId) {
                                            var params = _.concat(nts.uk.resource.getText(controlNameId), error.message.messageParams);
                                            error.messageText = nts.uk.resource.getMessage(error.message.messageId, params);
                                        }
                                        else {
                                            error.messageText = nts.uk.resource.getMessage(error.message.messageId, error.message.messageParams);
                                        }
                                    }
                                    else {
                                        error.messageText = nts.uk.resource.getMessage(error.message.messageId);
                                    }
                                    error.errorCode = error.message.messageId;
                                }
                            }
                            this.errors.push(error);
                        }
                    };
                    ErrorsViewModel.prototype.hasError = function () {
                        return this.errors().length > 0;
                    };
                    ErrorsViewModel.prototype.clearError = function () {
                        $(".error").children().each(function (index, element) {
                            if ($(element).data("hasError"))
                                $(element).data("hasError", false);
                        });
                        $(".error").removeClass('error');
                        this.errors.removeAll();
                    };
                    ErrorsViewModel.prototype.removeErrorByElement = function ($element) {
                        this.errors.remove(function (error) {
                            return error.$control.is($element);
                        });
                    };
                    ErrorsViewModel.prototype.removeErrorByCode = function ($element, errorCode) {
                        this.errors.remove(function (error) {
                            return error.$control.is($element) && error.errorCode === errorCode;
                        });
                    };
                    ErrorsViewModel.prototype.removeKibanError = function ($element) {
                        this.errors.remove(function (error) {
                            return error.$control.is($element) && error.businessError == false;
                        });
                    };
                    ErrorsViewModel.prototype.getErrorByElement = function ($element) {
                        return _.filter(this.errors(), function (e) {
                            return e.$control.is($element);
                        });
                    };
                    ErrorsViewModel.prototype.addCellError = function (error) {
                        var self = this;
                        var exists = _.filter(this.gridErrors(), function (err) {
                            return self.sameCells(error, err);
                        });
                        if (exists.length > 0)
                            return;
                        this.gridErrors.push(error);
                    };
                    ErrorsViewModel.prototype.removeCellError = function ($grid, rowId, columnKey) {
                        this.gridErrors.remove(function (err) {
                            return err.grid.is($grid) && err.rowId === rowId && err.columnKey === columnKey;
                        });
                    };
                    ErrorsViewModel.prototype.gridHasError = function () {
                        return this.gridErrors().length > 0;
                    };
                    ErrorsViewModel.prototype.clearGridErrors = function () {
                        this.gridErrors.removeAll();
                    };
                    ErrorsViewModel.prototype.sameCells = function (one, other) {
                        if (!one.grid.is(other.grid))
                            return false;
                        if (one.rowId !== other.rowId)
                            return false;
                        if (one.columnKey !== other.columnKey)
                            return false;
                        return true;
                    };
                    ErrorsViewModel.prototype.stashMemento = function () {
                        var memento = new ErrorViewModelMemento();
                        memento.setErrors(ko.unwrap(this.errors));
                        memento.setGridErrors(ko.unwrap(this.gridErrors));
                        memento.option = ko.unwrap(this.option);
                        memento.allResolved = this.allResolved;
                        memento.allCellsResolved = this.allCellsResolved;
                        memento.setErrorElements();
                        this.clearError();
                        return memento;
                    };
                    ErrorsViewModel.prototype.restoreFrom = function (memento) {
                        this.errors(memento.errors);
                        this.gridErrors(memento.gridErrors);
                        this.option(memento.option);
                        this.allResolved = memento.allResolved;
                        this.allCellsResolved = memento.allCellsResolved;
                        memento.restoreErrorElements();
                    };
                    return ErrorsViewModel;
                }());
                errors_1.ErrorsViewModel = ErrorsViewModel;
                var ErrorViewModelMemento = (function () {
                    function ErrorViewModelMemento() {
                    }
                    ErrorViewModelMemento.prototype.setErrors = function (errors) {
                        var _this = this;
                        if (!_.isArray(errors)) {
                            return;
                        }
                        this.errors = [];
                        errors.forEach(function (e) {
                            _this.errors.push(e);
                        });
                    };
                    ErrorViewModelMemento.prototype.setGridErrors = function (gridErrors) {
                        var _this = this;
                        if (!_.isArray(gridErrors)) {
                            return;
                        }
                        this.gridErrors = [];
                        gridErrors.forEach(function (e) {
                            _this.gridErrors.push(e);
                        });
                    };
                    ErrorViewModelMemento.prototype.setErrorElements = function () {
                        this.errorElements = $(".error").removeClass("error");
                    };
                    ErrorViewModelMemento.prototype.restoreErrorElements = function () {
                        this.errorElements.addClass("error");
                    };
                    return ErrorViewModelMemento;
                }());
                errors_1.ErrorViewModelMemento = ErrorViewModelMemento;
                var ErrorHeader = (function () {
                    function ErrorHeader(name, text, width, visible) {
                        this.name = name;
                        this.text = text;
                        this.width = width;
                        this.visible = visible;
                    }
                    return ErrorHeader;
                }());
                errors_1.ErrorHeader = ErrorHeader;
                function errorsViewModel() {
                    return nts.uk.ui._viewModel.kiban.errorDialogViewModel;
                }
                errors_1.errorsViewModel = errorsViewModel;
                function show() {
                    errorsViewModel().open();
                }
                errors_1.show = show;
                function hide() {
                    errorsViewModel().hide();
                }
                errors_1.hide = hide;
                function add(error) {
                    errorsViewModel().addError(error);
                    error.$control.data(nts.uk.ui.DATA_HAS_ERROR, true);
                    (error.$control.data(nts.uk.ui.DATA_SET_ERROR_STYLE) || function () { error.$control.parent().addClass('error'); })();
                }
                errors_1.add = add;
                function hasError() {
                    return errorsViewModel().hasError();
                }
                errors_1.hasError = hasError;
                function clearAll() {
                    if (nts.uk.ui._viewModel !== undefined)
                        errorsViewModel().clearError();
                }
                errors_1.clearAll = clearAll;
                function removeByElement($control) {
                    errorsViewModel().removeErrorByElement($control);
                    $control.data(nts.uk.ui.DATA_HAS_ERROR, false);
                    ($control.data(nts.uk.ui.DATA_CLEAR_ERROR_STYLE) || function () { $control.parent().removeClass('error'); })();
                }
                errors_1.removeByElement = removeByElement;
                function removeByCode($control, errorCode) {
                    errorsViewModel().removeErrorByCode($control, errorCode);
                    var remainErrors = getErrorByElement($control);
                    if (nts.uk.util.isNullOrEmpty(remainErrors)) {
                        $control.data(nts.uk.ui.DATA_HAS_ERROR, false);
                        ($control.data(nts.uk.ui.DATA_CLEAR_ERROR_STYLE) || function () { $control.parent().removeClass('error'); })();
                    }
                }
                errors_1.removeByCode = removeByCode;
                function removeCommonError($control) {
                    errorsViewModel().removeKibanError($control);
                    var remainErrors = getErrorByElement($control);
                    if (nts.uk.util.isNullOrEmpty(remainErrors)) {
                        $control.data(nts.uk.ui.DATA_HAS_ERROR, false);
                        ($control.data(nts.uk.ui.DATA_CLEAR_ERROR_STYLE) || function () { $control.parent().removeClass('error'); })();
                    }
                }
                errors_1.removeCommonError = removeCommonError;
                function getErrorByElement($element) {
                    return errorsViewModel().getErrorByElement($element);
                }
                errors_1.getErrorByElement = getErrorByElement;
                function addCell(error) {
                    errorsViewModel().addCellError(error);
                }
                errors_1.addCell = addCell;
                function removeCell($grid, rowId, columnKey) {
                    errorsViewModel().removeCellError($grid, rowId, columnKey);
                }
                errors_1.removeCell = removeCell;
                function gridHasError() {
                    return errorsViewModel().gridHasError();
                }
                errors_1.gridHasError = gridHasError;
                function clearAllGridErrors() {
                    if (nts.uk.ui._viewModel !== undefined)
                        errorsViewModel().clearGridErrors();
                }
                errors_1.clearAllGridErrors = clearAllGridErrors;
                function getErrorList() {
                    return errorsViewModel().displayErrors();
                }
                errors_1.getErrorList = getErrorList;
            })(errors = ui.errors || (ui.errors = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=errors.js.map