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
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
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
                koExtentions.TextEditorOption = TextEditorOption;
                var TimeEditorOption = (function (_super) {
                    __extends(TimeEditorOption, _super);
                    function TimeEditorOption(option) {
                        _super.call(this);
                        // Default value
                        this.inputFormat = (option && option.inputFormat) ? option.inputFormat : "date";
                        this.placeholder = (option && option.placeholder) ? option.placeholder : "";
                        this.width = (option && option.width) ? option.width : "";
                        this.textalign = (option && option.textalign) ? option.textalign : "left";
                    }
                    return TimeEditorOption;
                }(EditorOptionBase));
                koExtentions.TimeEditorOption = TimeEditorOption;
                var MaskEditorOption = (function (_super) {
                    __extends(MaskEditorOption, _super);
                    function MaskEditorOption(option) {
                        _super.call(this);
                        // Default value
                        this.textmode = (option && option.textmode) ? option.textmode : "text";
                        this.placeholder = (option && option.placeholder) ? option.placeholder : "";
                        this.width = (option && option.width) ? option.width : "";
                        this.textalign = (option && option.textalign) ? option.textalign : "left";
                    }
                    return MaskEditorOption;
                }(EditorOptionBase));
                koExtentions.MaskEditorOption = MaskEditorOption;
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
                        // Regex: /^-?\d+(\,\d{3})*([.]\d+)?$/
                        this.regex = "/^-?\\d+";
                        if (this.grouplength > 0)
                            this.regex += "(\\" + this.groupseperator + "\\d{" + this.grouplength + "})*";
                        if (this.decimallength > 0)
                            this.regex += "([" + this.decimalseperator + "]\\d+)?";
                        this.regex += "$/";
                        console.log(this.regex);
                    }
                    return NumberEditorOption;
                }(EditorOptionBase));
                koExtentions.NumberEditorOption = NumberEditorOption;
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
                        // TODO: Write ()=> to return string instead of check
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
                koExtentions.CurrencyEditorOption = CurrencyEditorOption;
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
