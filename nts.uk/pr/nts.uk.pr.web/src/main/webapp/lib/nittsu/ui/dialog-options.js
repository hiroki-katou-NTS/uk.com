var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var DialogOption = (function () {
    function DialogOption() {
        this.show = false;
    }
    return DialogOption;
}());
var ConfirmDialogOption = (function (_super) {
    __extends(ConfirmDialogOption, _super);
    function ConfirmDialogOption(option) {
        _super.call(this);
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
    return ConfirmDialogOption;
}(DialogOption));
var DelDialogOption = (function (_super) {
    __extends(DelDialogOption, _super);
    function DelDialogOption(option) {
        _super.call(this);
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
    return DelDialogOption;
}(DialogOption));
var OKDialogOption = (function (_super) {
    __extends(OKDialogOption, _super);
    function OKDialogOption(option) {
        _super.call(this);
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
    return OKDialogOption;
}(DialogOption));
var ErrorDialogOption = (function (_super) {
    __extends(ErrorDialogOption, _super);
    function ErrorDialogOption(option) {
        _super.call(this);
        // Default value
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
    return ErrorDialogOption;
}(DialogOption));
var DialogButton = (function () {
    function DialogButton() {
    }
    DialogButton.prototype.click = function (viewmodel, ui) { };
    ;
    return DialogButton;
}());
