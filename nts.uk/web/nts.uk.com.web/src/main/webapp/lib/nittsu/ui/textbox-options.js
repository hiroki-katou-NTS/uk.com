/// <reference path="../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var option;
            (function (option_1) {
                class EditorOptionBase {
                }
                class TextEditorOption extends EditorOptionBase {
                    constructor(option) {
                        super();
                        // Default value
                        this.textmode = (option !== undefined && option.textmode !== undefined) ? option.textmode : "text";
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "";
                        this.filldirection = (option !== undefined && option.filldirection !== undefined) ? option.filldirection : "right";
                        this.fillcharacter = (option !== undefined && option.fillcharacter !== undefined) ? option.fillcharacter : "0";
                    }
                }
                option_1.TextEditorOption = TextEditorOption;
                class TimeEditorOption extends EditorOptionBase {
                    constructor(option) {
                        super();
                        // Default value
                        this.inputFormat = (option !== undefined && option.inputFormat !== undefined) ? option.inputFormat : "date";
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "right";
                    }
                }
                option_1.TimeEditorOption = TimeEditorOption;
                class NumberEditorOption extends EditorOptionBase {
                    constructor(option) {
                        super();
                        // Default value
                        this.groupseperator = (option !== undefined && option.groupseperator !== undefined) ? option.groupseperator : ",";
                        this.grouplength = (option !== undefined && option.grouplength !== undefined) ? option.grouplength : 0;
                        this.decimalseperator = (option !== undefined && option.decimalseperator !== undefined) ? option.decimalseperator : ".";
                        this.decimallength = (option !== undefined && option.decimallength !== undefined) ? option.decimallength : 0;
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "right";
                        this.symbolChar = (option !== undefined && option.symbolChar !== undefined) ? option.symbolChar : "";
                        this.symbolPosition = (option !== undefined && option.symbolPosition !== undefined) ? option.symbolPosition : "right";
                    }
                }
                option_1.NumberEditorOption = NumberEditorOption;
                class CurrencyEditorOption extends NumberEditorOption {
                    constructor(option) {
                        super();
                        // Default value
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
                    }
                }
                option_1.CurrencyEditorOption = CurrencyEditorOption;
                function getCurrencyPosition(currencyformat) {
                    return currenryPosition[currencyformat] === null ? "right" : currenryPosition[currencyformat];
                }
                class MultilineEditorOption extends EditorOptionBase {
                    constructor(option) {
                        super();
                        // Default value
                        this.resizeable = (option !== undefined && option.resizeable !== undefined) ? option.resizeable : false;
                        this.placeholder = (option !== undefined && option.placeholder !== undefined) ? option.placeholder : "";
                        this.width = (option !== undefined && option.width !== undefined) ? option.width : "";
                        this.textalign = (option !== undefined && option.textalign !== undefined) ? option.textalign : "";
                    }
                }
                option_1.MultilineEditorOption = MultilineEditorOption;
                var currenryPosition = {
                    "JPY": "left",
                    "USD": "right"
                };
            })(option = ui.option || (ui.option = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
