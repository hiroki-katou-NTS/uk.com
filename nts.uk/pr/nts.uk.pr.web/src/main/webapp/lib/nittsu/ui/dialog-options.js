var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var DialogOption = (function () {
    function DialogOption() {
    }
    return DialogOption;
}());
var ConfirmDialogOption = (function (_super) {
    __extends(ConfirmDialogOption, _super);
    function ConfirmDialogOption(option) {
        _super.call(this);
        this.dialogType = "alert";
        this.title = (option && option.title) ? option.title : "";
        this.message = (option && option.message) ? option.message : "";
        this.modal = (option && option.modal !== undefined) ? option.modal : true;
        this.show = (option && option.show !== undefined) ? option.show : false;
        this.buttonSize = (option && option.buttonSize) ? option.buttonSize : "large";
        this.okButtonColor = (option && option.okButtonColor) ? option.okButtonColor : "proceed";
        this.okButtonText = (option && option.okButtonText) ? option.okButtonText : "OK";
        this.buttons = [];
        // Add OK Button
        this.buttons.push({ text: this.okButtonText,
            "class": "yes",
            size: this.buttonSize,
            color: this.okButtonColor,
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
        this.dialogType = "confirm";
        this.title = (option && option.title) ? option.title : "";
        this.message = (option && option.message) ? option.message : "";
        this.modal = (option && option.modal !== undefined) ? option.modal : true;
        this.show = (option && option.show !== undefined) ? option.show : false;
        this.buttonSize = (option && option.buttonSize) ? option.buttonSize : "large";
        this.okButtonColor = (option && option.okButtonColor) ? option.okButtonColor : "danger";
        this.okButtonText = (option && option.okButtonText) ? option.okButtonText : "はい";
        this.cancelButtonText = (option && option.cancelButtonText) ? option.cancelButtonText : "いいえ";
        this.buttons = [];
        // Add OK Button
        this.buttons.push({ text: this.okButtonText,
            "class": "yes ",
            size: this.buttonSize,
            color: this.okButtonColor,
            click: function (viewmodel, ui) {
                viewmodel.okButtonClicked();
                ui.dialog("close");
            }
        });
        // Add Cancel Button
        this.buttons.push({ text: this.cancelButtonText,
            "class": "no ",
            size: this.buttonSize,
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
        this.dialogType = "confirm";
        this.title = (option && option.title) ? option.title : "";
        this.message = (option && option.message) ? option.message : "";
        this.modal = (option && option.modal !== undefined) ? option.modal : true;
        this.show = (option && option.show !== undefined) ? option.show : false;
        this.buttonSize = (option && option.buttonSize) ? option.buttonSize : "large";
        this.okButtonColor = (option && option.okButtonColor) ? option.okButtonColor : "proceed";
        this.okButtonText = (option && option.okButtonText) ? option.okButtonText : "はい";
        this.cancelButtonText = (option && option.cancelButtonText) ? option.cancelButtonText : "いいえ";
        this.buttons = [];
        // Add OK Button
        this.buttons.push({ text: this.okButtonText,
            "class": "yes ",
            size: this.buttonSize,
            color: this.okButtonColor,
            click: function (viewmodel, ui) {
                viewmodel.okButtonClicked();
                ui.dialog("close");
            }
        });
        // Add Cancel Button
        this.buttons.push({ text: this.cancelButtonText,
            "class": "no ",
            size: this.buttonSize,
            color: "",
            click: function (viewmodel, ui) {
                viewmodel.cancelButtonClicked();
                ui.dialog("close");
            }
        });
    }
    return OKDialogOption;
}(DialogOption));
var DialogButton = (function () {
    function DialogButton() {
    }
    DialogButton.prototype.click = function (viewmodel, ui) { };
    ;
    return DialogButton;
}());
