var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var DialogType;
(function (DialogType) {
    DialogType[DialogType["Alert"] = 0] = "Alert";
    DialogType[DialogType["Confirm"] = 1] = "Confirm";
})(DialogType || (DialogType = {}));
var DialogOption = (function () {
    // Default Constructor
    function DialogOption() {
    }
    return DialogOption;
}());
var AlertDialogOption = (function (_super) {
    __extends(AlertDialogOption, _super);
    //Default Contructor
    function AlertDialogOption() {
        _super.call(this);
        this.dialogType = DialogType.Alert;
        this.buttons = [];
        this.buttons.push({ text: "はい", size: "large", color: "success", fontSize: "" });
    }
    return AlertDialogOption;
}(DialogOption));
var ConfirmDialogOption = (function (_super) {
    __extends(ConfirmDialogOption, _super);
    //Default Contructor
    function ConfirmDialogOption() {
        _super.call(this);
        this.dialogType = DialogType.Confirm;
        this.buttons = [];
        this.buttons.push({ text: "はい", size: "large", color: "danger", fontSize: "" });
        this.buttons.push({ text: "いいえ", size: "large", color: "", fontSize: "" });
    }
    return ConfirmDialogOption;
}(DialogOption));
var DialogButton = (function () {
    //Default Contructor
    function DialogButton() {
    }
    return DialogButton;
}());
