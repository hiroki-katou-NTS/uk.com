var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var option = nts.uk.ui.option;
            var init;
            (function (init) {
                var _start;
                __viewContext.ready = function (callback) {
                    _start = callback;
                };
                __viewContext.bind = function (contentViewModel) {
                    ui._viewModel = {
                        content: contentViewModel,
                        kiban: new KibanViewModel() // Kiban's view model
                    };
                    ko.applyBindings(ui._viewModel);
                };
                $(function () {
                    __viewContext.transferred = uk.sessionStorage.getItemAndRemove(uk.request.STORAGE_KEY_TRANSFER_DATA)
                        .map(function (v) { return JSON.parse(v); });
                    _start.call(__viewContext);
                });
                // Kiban ViewModel
                var KibanViewModel = (function () {
                    function KibanViewModel() {
                        var self = this;
                        self.errorDialogViewModel = {
                            title: ko.observable("Error Dialog title"),
                            errors: ko.observableArray([
                                { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }, { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" },
                                { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }, { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" },
                                { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }, { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" },
                                { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }, { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" },
                                { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }, { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" },
                                { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }, { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" },
                                { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }, { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" },
                                { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }, { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" },
                                { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }, { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" },
                                { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }, { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" },
                                { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }, { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" },
                                { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }, { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" },
                                { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }, { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" },
                                { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }, { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" },
                                { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }, { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" },
                                { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }, { tab: "基本情報", location: "メールアドレス", message: "メールアドレスは必須項目です" }
                            ]),
                            option: ko.mapping.fromJS(new option.ErrorDialogWithTabOption()),
                            closeButtonClicked: function () { },
                            open: function () {
                                var self = this;
                                self.option.show(true);
                            },
                            hide: function () {
                                var self = this;
                                self.option.show(false);
                            }
                        };
                    }
                    return KibanViewModel;
                }());
            })(init || (init = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
