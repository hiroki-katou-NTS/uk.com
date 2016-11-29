module nts.uk.ui.option {
    
    abstract class EditorOptionBase {
        placeholder: string;
        width: string;
        textalign: string;
    }

    // Text Editor Option
    interface ITextEditorOption{
        textmode?: TextMode;
        placeholder?: string;
        width?: string;
        textalign?: string;
        filldirection?: FillDirection;
        fillcharacter?: string;
    }

    export class TextEditorOption extends EditorOptionBase {
        textmode: TextMode;
        filldirection: FillDirection;
        fillcharacter: string;
        
        constructor(option?: ITextEditorOption) {
            super();
            // Default value
            this.textmode = (option && option.textmode) ? option.textmode : "text";
            this.placeholder = (option && option.placeholder) ? option.placeholder : "";
            this.width = (option && option.width) ? option.width : "";
            this.textalign = (option && option.textalign) ? option.textalign : "left";
            this.filldirection = (option && option.filldirection) ? option.filldirection : "right";
            this.fillcharacter = (option && option.fillcharacter) ? option.fillcharacter : "0";
        }
    }

    // Time Editor Option
    interface ITimeEditorOption{
        inputFormat?: string;
        placeholder?: string;
        width?: string;
        textalign?: string;
    }
    
    export class TimeEditorOption extends EditorOptionBase {
        inputFormat: string;
        
        constructor(option?: ITimeEditorOption) {
            super();
            // Default value
            this.inputFormat = (option && option.inputFormat) ? option.inputFormat : "date";
            this.placeholder = (option && option.placeholder) ? option.placeholder : "";
            this.width = (option && option.width) ? option.width : "";
            this.textalign = (option && option.textalign) ? option.textalign : "left";
        }
    }

    // Number Editor Option
    interface INumberEditorOption{
        groupseperator?: string,
        grouplength?: number,
        decimalseperator?: string,
        decimallength?: number,
        currencyformat?: Currency,
        currencyposition?: string,
        placeholder?: string;
        width?: string;
        textalign?: string;
    }

    export class NumberEditorOption extends EditorOptionBase {
        groupseperator: string;
        grouplength: number;
        decimalseperator: string;
        decimallength: number;
        protected regex: string;
        
        constructor(option?: INumberEditorOption) {
            super();
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
        }
    }

    export class CurrencyEditorOption extends NumberEditorOption {
        currencyformat: Currency;
        currencyposition: string;
        
        constructor(option?: INumberEditorOption) {
            super();
            // Default value
            this.groupseperator = (option && option.groupseperator) ? option.groupseperator : ",";
            this.grouplength = (option && option.grouplength) ? option.grouplength : 0;
            this.decimalseperator = (option && option.decimalseperator) ? option.decimalseperator : ".";
            this.decimallength = (option && option.decimallength) ? option.decimallength : 0;
            this.currencyformat = (option && option.currencyformat) ? option.currencyformat : "JPY";
            this.currencyposition = (option && option.currencyposition) ? option.currencyposition : "";
            // TODO: Write ()=> to return string instead of check
            switch(this.currencyformat) {
                case "JPY":
                    this.currencyposition = "left"; break;
                case "USD":
                    this.currencyposition = "right"; break;
            }
            this.placeholder = (option && option.placeholder) ? option.placeholder : "";
            this.width = (option && option.width) ? option.width : "";
            this.textalign = (option && option.textalign) ? option.textalign : "left";
        }
    }
        
    type TextMode = "text" | "password";
    type FillDirection = "left" |"right";
    type Currency = "JPY" | "USD";
}