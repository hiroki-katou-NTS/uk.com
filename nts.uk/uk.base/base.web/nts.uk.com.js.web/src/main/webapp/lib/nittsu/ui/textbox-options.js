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
            var option;
            (function (option_1) {
                var currenryPosition = {
                    "JPY": "left",
                    "USD": "right"
                };
                var EditorOptionBase = (function () {
                    function EditorOptionBase() {
                    }
                    return EditorOptionBase;
                }());
                option_1.EditorOptionBase = EditorOptionBase;
                var TextEditorOption = (function (_super) {
                    __extends(TextEditorOption, _super);
                    function TextEditorOption(option) {
                        _super.call(this);
                        this.textmode = (option !== undefined && option.textmode !== undefined) ? option.textmode : "text";
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "";
                        this.autofill = (option !== undefined && option.autofill !== undefined) ? option.autofill : false;
                        this.filldirection = (option !== undefined && option.filldirection !== undefined) ? option.filldirection : "left";
                        this.fillcharacter = (option !== undefined && option.fillcharacter !== undefined) ? option.fillcharacter : "0";
                    }
                    return TextEditorOption;
                }(EditorOptionBase));
                option_1.TextEditorOption = TextEditorOption;
                var TimeEditorOption = (function (_super) {
                    __extends(TimeEditorOption, _super);
                    function TimeEditorOption(option) {
                        _super.call(this);
                        this.inputFormat = (option !== undefined && option.inputFormat !== undefined) ? option.inputFormat : "date";
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.defaultValue = (option !== undefined && option.defaultValue !== undefined) ? option.defaultValue : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "right";
                    }
                    return TimeEditorOption;
                }(EditorOptionBase));
                option_1.TimeEditorOption = TimeEditorOption;
                var NumberEditorOption = (function (_super) {
                    __extends(NumberEditorOption, _super);
                    function NumberEditorOption(option) {
                        _super.call(this);
                        this.groupseperator = (option !== undefined && option.groupseperator !== undefined) ? option.groupseperator : ",";
                        this.grouplength = (option !== undefined && option.grouplength !== undefined) ? option.grouplength : 0;
                        this.decimalseperator = (option !== undefined && option.decimalseperator !== undefined) ? option.decimalseperator : ".";
                        this.decimallength = (option !== undefined && option.decimallength !== undefined) ? option.decimallength : 0;
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "right";
                        this.symbolChar = (option !== undefined && option.symbolChar !== undefined) ? option.symbolChar : "";
                        this.symbolPosition = (option !== undefined && option.symbolPosition !== undefined) ? option.symbolPosition : "right";
                        this.unitID = (option !== undefined && option.unitID !== undefined) ? option.unitID : "";
                        this.defaultValue = (option !== undefined && !nts.uk.util.isNullOrEmpty(option.defaultValue)) ? option.defaultValue : "";
                    }
                    return NumberEditorOption;
                }(EditorOptionBase));
                option_1.NumberEditorOption = NumberEditorOption;
                var CurrencyEditorOption = (function (_super) {
                    __extends(CurrencyEditorOption, _super);
                    function CurrencyEditorOption(option) {
                        _super.call(this);
                        this.groupseperator = (option !== undefined && option.groupseperator !== undefined) ? option.groupseperator : ",";
                        this.grouplength = (option !== undefined && option.grouplength !== undefined) ? option.grouplength : 0;
                        this.decimalseperator = (option !== undefined && option.decimalseperator !== undefined) ? option.decimalseperator : ".";
                        this.decimallength = (option !== undefined && option.decimallength !== undefined) ? option.decimallength : 0;
                        this.currencyformat = (option !== undefined && option.currencyformat !== undefined) ? option.currencyformat : "JPY";
                        this.currencyposition = (option !== undefined && option.currencyposition !== undefined)
                            ? option.currencyposition : getCurrencyPosition(this.currencyformat);
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "right";
                        this.defaultValue = (option !== undefined && !nts.uk.util.isNullOrEmpty(option.defaultValue)) ? option.defaultValue : "";
                        this.unitID = (option !== undefined && option.unitID !== undefined) ? option.unitID : "";
                    }
                    return CurrencyEditorOption;
                }(NumberEditorOption));
                option_1.CurrencyEditorOption = CurrencyEditorOption;
                function getCurrencyPosition(currencyformat) {
                    return currenryPosition[currencyformat] === null ? "right" : currenryPosition[currencyformat];
                }
                var MultilineEditorOption = (function (_super) {
                    __extends(MultilineEditorOption, _super);
                    function MultilineEditorOption(option) {
                        _super.call(this);
                        this.resizeable = (option !== undefined && option.resizeable !== undefined) ? option.resizeable : false;
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "";
                    }
                    return MultilineEditorOption;
                }(EditorOptionBase));
                option_1.MultilineEditorOption = MultilineEditorOption;
                var TimeWithDayAttrEditorOption = (function (_super) {
                    __extends(TimeWithDayAttrEditorOption, _super);
                    function TimeWithDayAttrEditorOption(option) {
                        _super.call(this);
                        this.timeWithDay = (option !== undefined && option.timeWithDay !== undefined) ? option.timeWithDay : true;
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "right";
                    }
                    return TimeWithDayAttrEditorOption;
                }(EditorOptionBase));
                option_1.TimeWithDayAttrEditorOption = TimeWithDayAttrEditorOption;
            })(option = ui.option || (ui.option = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=textbox-options.js.map