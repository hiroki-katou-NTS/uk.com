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
                    _start.call(__viewContext);
                });
                // Kiban ViewModel
                var KibanViewModel = (function () {
                    function KibanViewModel() {
                        var self = this;
                        self.errorDialogViewModel = {
                            title: ko.observable("Error Dialog title"),
                            headers: ko.observableArray([
                                new ErrorHeader("tab", "タブ", 90, true),
                                new ErrorHeader("location", "エラー箇所", 115, true),
                                new ErrorHeader("message", "エラー詳細", 250, true)
                            ]),
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
                            option: ko.mapping.fromJS(new option.ErrorDialogOption()),
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
                var ErrorHeader = (function () {
                    function ErrorHeader(name, text, width, visible) {
                        this.name = name;
                        this.text = text;
                        this.width = width;
                        this.visible = visible;
                    }
                    return ErrorHeader;
                }());
            })(init || (init = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
