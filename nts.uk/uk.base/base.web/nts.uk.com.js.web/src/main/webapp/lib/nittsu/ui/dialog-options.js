var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var option;
            (function (option_1) {
                var DialogOption = (function () {
                    function DialogOption() {
                        this.show = false;
                    }
                    return DialogOption;
                }());
                option_1.DialogOption = DialogOption;
                var ConfirmDialogOption = (function (_super) {
                    __extends(ConfirmDialogOption, _super);
                    function ConfirmDialogOption(option) {
                        _super.call(this);
                        this.modal = (option && option.modal !== undefined) ? option.modal : true;
                        this.buttons = [];
                        this.buttons.push({
                            text: "OK",
                            "class": "yes",
                            size: "large",
                            color: "proceed",
                            click: function (viewmodel, ui) {
                                viewmodel.okButtonClicked();
                                $(ui).dialog("close");
                            }
                        });
                    }
                    return ConfirmDialogOption;
                }(DialogOption));
                option_1.ConfirmDialogOption = ConfirmDialogOption;
                var DelDialogOption = (function (_super) {
                    __extends(DelDialogOption, _super);
                    function DelDialogOption(option) {
                        _super.call(this);
                        this.modal = (option && option.modal !== undefined) ? option.modal : true;
                        this.buttons = [];
                        this.buttons.push({
                            text: "はい",
                            "class": "yes ",
                            size: "large",
                            color: "danger",
                            click: function (viewmodel, ui) {
                                viewmodel.okButtonClicked();
                                ui.dialog("close");
                            }
                        });
                        this.buttons.push({
                            text: "いいえ",
                            "class": "no ",
                            size: "large",
                            color: "",
                            click: function (viewmodel, ui) {
                                viewmodel.cancelButtonClicked();
                                ui.dialog("close");
                            }
                        });
                    }
                    return DelDialogOption;
                }(DialogOption));
                option_1.DelDialogOption = DelDialogOption;
                var OKDialogOption = (function (_super) {
                    __extends(OKDialogOption, _super);
                    function OKDialogOption(option) {
                        _super.call(this);
                        this.modal = (option && option.modal !== undefined) ? option.modal : true;
                        this.buttons = [];
                        this.buttons.push({
                            text: "はい",
                            "class": "yes ",
                            size: "large",
                            color: "proceed",
                            click: function (viewmodel, ui) {
                                viewmodel.okButtonClicked();
                                ui.dialog("close");
                            }
                        });
                        this.buttons.push({
                            text: "いいえ",
                            "class": "no ",
                            size: "large",
                            color: "",
                            click: function (viewmodel, ui) {
                                viewmodel.cancelButtonClicked();
                                ui.dialog("close");
                            }
                        });
                    }
                    return OKDialogOption;
                }(DialogOption));
                option_1.OKDialogOption = OKDialogOption;
                var ErrorDialogOption = (function (_super) {
                    __extends(ErrorDialogOption, _super);
                    function ErrorDialogOption(option) {
                        _super.call(this);
                        this.headers = (option && option.headers) ? option.headers : [
                            new nts.uk.ui.errors.ErrorHeader("messageText", "エラー内容", "auto", true),
                            new nts.uk.ui.errors.ErrorHeader("errorCode", "エラーコード", 150, true)
                        ];
                        this.modal = (option && option.modal !== undefined) ? option.modal : false;
                        this.displayrows = (option && option.displayrows) ? option.displayrows : 10;
                        this.autoclose = (option && option.autoclose !== undefined) ? option.autoclose : true;
                        this.buttons = [];
                        this.buttons.push({
                            text: "閉じる",
                            "class": "yes ",
                            size: "large",
                            color: "",
                            click: function (viewmodel, ui) {
                                viewmodel.closeButtonClicked();
                                ui.dialog("close");
                            }
                        });
                    }
                    return ErrorDialogOption;
                }(DialogOption));
                option_1.ErrorDialogOption = ErrorDialogOption;
                var ErrorDialogWithTabOption = (function (_super) {
                    __extends(ErrorDialogWithTabOption, _super);
                    function ErrorDialogWithTabOption(option) {
                        _super.call(this);
                        this.headers = (option && option.headers) ? option.headers : [
                            new ui_1.errors.ErrorHeader("tab", "タブ", 90, true),
                            new ui_1.errors.ErrorHeader("location", "エラー箇所", 115, true),
                            new ui_1.errors.ErrorHeader("message", "エラー詳細", 250, true)
                        ];
                        this.modal = (option && option.modal !== undefined) ? option.modal : false;
                        this.displayrows = (option && option.displayrows) ? option.displayrows : 10;
                        this.autoclose = (option && option.autoclose !== undefined) ? option.autoclose : true;
                        this.buttons = [];
                        this.buttons.push({
                            text: "閉じる",
                            "class": "yes ",
                            size: "large",
                            color: "",
                            click: function (viewmodel, ui) {
                                viewmodel.closeButtonClicked();
                                ui.dialog("close");
                            }
                        });
                    }
                    return ErrorDialogWithTabOption;
                }(ErrorDialogOption));
                option_1.ErrorDialogWithTabOption = ErrorDialogWithTabOption;
                var DialogButton = (function () {
                    function DialogButton() {
                    }
                    DialogButton.prototype.click = function (viewmodel, ui) { };
                    ;
                    return DialogButton;
                }());
                option_1.DialogButton = DialogButton;
            })(option = ui_1.option || (ui_1.option = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=dialog-options.js.map