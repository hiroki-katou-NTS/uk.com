/// <reference path="../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var option;
            (function (option_1) {
                class DialogOption {
                    constructor() {
                        this.show = false;
                    }
                }
                class ConfirmDialogOption extends DialogOption {
                    constructor(option) {
                        super();
                        // Default value
                        this.modal = (option && option.modal !== undefined) ? option.modal : true;
                        this.buttons = [];
                        // Add OK Button
                        this.buttons.push({ text: "OK",
                            "class": "yes",
                            size: "large",
                            color: "proceed",
                            click: function (viewmodel, ui) {
                                viewmodel.okButtonClicked();
                                $(ui).dialog("close");
                            }
                        });
                    }
                }
                option_1.ConfirmDialogOption = ConfirmDialogOption;
                class DelDialogOption extends DialogOption {
                    constructor(option) {
                        super();
                        // Default value
                        this.modal = (option && option.modal !== undefined) ? option.modal : true;
                        this.buttons = [];
                        // Add OK Button
                        this.buttons.push({ text: "はい",
                            "class": "yes ",
                            size: "large",
                            color: "danger",
                            click: function (viewmodel, ui) {
                                viewmodel.okButtonClicked();
                                ui.dialog("close");
                            }
                        });
                        // Add Cancel Button
                        this.buttons.push({ text: "いいえ",
                            "class": "no ",
                            size: "large",
                            color: "",
                            click: function (viewmodel, ui) {
                                viewmodel.cancelButtonClicked();
                                ui.dialog("close");
                            }
                        });
                    }
                }
                option_1.DelDialogOption = DelDialogOption;
                class OKDialogOption extends DialogOption {
                    constructor(option) {
                        super();
                        // Default value
                        this.modal = (option && option.modal !== undefined) ? option.modal : true;
                        this.buttons = [];
                        // Add OK Button
                        this.buttons.push({ text: "はい",
                            "class": "yes ",
                            size: "large",
                            color: "proceed",
                            click: function (viewmodel, ui) {
                                viewmodel.okButtonClicked();
                                ui.dialog("close");
                            }
                        });
                        // Add Cancel Button
                        this.buttons.push({ text: "いいえ",
                            "class": "no ",
                            size: "large",
                            color: "",
                            click: function (viewmodel, ui) {
                                viewmodel.cancelButtonClicked();
                                ui.dialog("close");
                            }
                        });
                    }
                }
                option_1.OKDialogOption = OKDialogOption;
                class ErrorDialogOption extends DialogOption {
                    constructor(option) {
                        super();
                        // Default value
                        this.headers = (option && option.headers) ? option.headers : [
                            new nts.uk.ui.errors.ErrorHeader("location", "エラー箇所", 115, true),
                            new nts.uk.ui.errors.ErrorHeader("message", "エラー詳細", 250, true)
                        ];
                        this.modal = (option && option.modal !== undefined) ? option.modal : false;
                        this.displayrows = (option && option.displayrows) ? option.displayrows : 10;
                        this.maxrows = (option && option.maxrows) ? option.maxrows : 1000;
                        this.autoclose = (option && option.autoclose !== undefined) ? option.autoclose : true;
                        this.buttons = [];
                        // Add Close Button
                        this.buttons.push({ text: "閉じる",
                            "class": "yes ",
                            size: "large",
                            color: "",
                            click: function (viewmodel, ui) {
                                viewmodel.closeButtonClicked();
                                ui.dialog("close");
                            }
                        });
                    }
                }
                option_1.ErrorDialogOption = ErrorDialogOption;
                class ErrorDialogWithTabOption extends ErrorDialogOption {
                    constructor(option) {
                        super();
                        // Default value
                        this.headers = (option && option.headers) ? option.headers : [
                            new ui_1.errors.ErrorHeader("tab", "タブ", 90, true),
                            new ui_1.errors.ErrorHeader("location", "エラー箇所", 115, true),
                            new ui_1.errors.ErrorHeader("message", "エラー詳細", 250, true)
                        ];
                        this.modal = (option && option.modal !== undefined) ? option.modal : false;
                        this.displayrows = (option && option.displayrows) ? option.displayrows : 10;
                        this.maxrows = (option && option.maxrows) ? option.maxrows : 1000;
                        this.autoclose = (option && option.autoclose !== undefined) ? option.autoclose : true;
                        this.buttons = [];
                        // Add Close Button
                        this.buttons.push({ text: "閉じる",
                            "class": "yes ",
                            size: "large",
                            color: "",
                            click: function (viewmodel, ui) {
                                viewmodel.closeButtonClicked();
                                ui.dialog("close");
                            }
                        });
                    }
                }
                option_1.ErrorDialogWithTabOption = ErrorDialogWithTabOption;
                class DialogButton {
                    click(viewmodel, ui) { }
                    ;
                }
            })(option = ui_1.option || (ui_1.option = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=dialog-options.js.map
