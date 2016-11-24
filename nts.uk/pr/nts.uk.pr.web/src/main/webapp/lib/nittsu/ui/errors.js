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
                        this.title = "エラー一覧";
                        this.errors = ko.observableArray([]);
                        this.option = ko.mapping.fromJS(new ui.option.ErrorDialogOption());
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
                        this.errors.push(error);
                    };
                    ErrorsViewModel.prototype.removeErrorByElementId = function (elementId) {
                        var removeds = _.filter(this.errors(), function (e) { return e.elementId === elementId; });
                        this.errors.removeAll(removeds);
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
                function removeById(elementId) {
                    errorsViewModel().removeErrorByElementId(elementId);
                }
                errors.removeById = removeById;
            })(errors = ui.errors || (ui.errors = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
