var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var EditorOptionBase = (function () {
    function EditorOptionBase() {
        this.enable = true;
        this.readonly = false;
    }
    return EditorOptionBase;
}());
var TextEditorOption = (function (_super) {
    __extends(TextEditorOption, _super);
    function TextEditorOption(option) {
        _super.call(this);
        // Default value
        this.textmode = (option && option.textmode) ? option.textmode : "text";
        this.placeholder = (option && option.placeholder) ? option.placeholder : "";
        this.width = (option && option.width) ? option.width : "";
        this.textalign = (option && option.textalign) ? option.textalign : "left";
    }
    return TextEditorOption;
}(EditorOptionBase));
var TimeEditorOption = (function (_super) {
    __extends(TimeEditorOption, _super);
    function TimeEditorOption(option) {
        _super.call(this);
        // Default value
        this.textmode = (option && option.textmode) ? option.textmode : "text";
        this.placeholder = (option && option.placeholder) ? option.placeholder : "";
        this.width = (option && option.width) ? option.width : "";
        this.textalign = (option && option.textalign) ? option.textalign : "left";
    }
    return TimeEditorOption;
}(EditorOptionBase));
var NumberEditorOption = (function (_super) {
    __extends(NumberEditorOption, _super);
    function NumberEditorOption(option) {
        _super.call(this);
        // Default value
        this.groupseperator = (option && option.groupseperator) ? option.groupseperator : ",";
        this.grouplength = (option && option.grouplength) ? option.grouplength : 0;
        this.decimalseperator = (option && option.decimalseperator) ? option.decimalseperator : ".";
        this.decimallength = (option && option.decimallength) ? option.decimallength : 0;
        this.placeholder = (option && option.placeholder) ? option.placeholder : "";
        this.width = (option && option.width) ? option.width : "";
        this.textalign = (option && option.textalign) ? option.textalign : "left";
    }
    return NumberEditorOption;
}(EditorOptionBase));
var CurrencyEditorOption = (function (_super) {
    __extends(CurrencyEditorOption, _super);
    function CurrencyEditorOption(option) {
        _super.call(this);
        // Default value
        this.groupseperator = (option && option.groupseperator) ? option.groupseperator : ",";
        this.grouplength = (option && option.grouplength) ? option.grouplength : 0;
        this.decimalseperator = (option && option.decimalseperator) ? option.decimalseperator : ".";
        this.decimallength = (option && option.decimallength) ? option.decimallength : 0;
        this.currencyformat = (option && option.currencyformat) ? option.currencyformat : "JPY";
        this.currencyposition = (option && option.currencyposition) ? option.currencyposition : "";
        // TODO: Write ()=> to return instead of check
        switch (this.currencyformat) {
            case "JPY":
                this.currencyposition = "left";
                break;
            case "USD":
                this.currencyposition = "right";
                break;
        }
        this.placeholder = (option && option.placeholder) ? option.placeholder : "";
        this.width = (option && option.width) ? option.width : "";
        this.textalign = (option && option.textalign) ? option.textalign : "left";
    }
    return CurrencyEditorOption;
}(NumberEditorOption));
