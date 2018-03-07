module nts.uk.com.view.cmf002.share.model {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;

    export class NumericDataFormatSetting {
        formatSelection: KnockoutObservable<number>;
        decimalDigit: KnockoutObservable<number>;
        decimalPointClassification: KnockoutObservable<number>;
        decimalFraction: KnockoutObservable<number>;
        outputMinusAsZero: KnockoutObservable<number>;
        fixedValueOperation: KnockoutObservable<number>;
        fixedValueOperationSymbol: KnockoutObservable<number>;
        fixedCalculationValue: KnockoutObservable<number>;
        fixedLengthOutput: KnockoutObservable<number>;
        fixedLengthIntegerDigit: KnockoutObservable<number>;
        fixedLengthEditingMethod: KnockoutObservable<number>;
        nullValueReplace: KnockoutObservable<number>;
        valueOfNullValueReplace: KnockoutObservable<number>;
        fixedValue: KnockoutObservable<number>;
        valueOfFixedValue: KnockoutObservable<string>;
        constructor(formatSelection: number, decimalDigit: number, decimalPointClassification: number, decimalFraction: number, outputMinusAsZero: number,
            fixedValueOperation: number, fixedValueOperationSymbol: number, fixedCalculationValue: number, fixedLengthOutput: number, fixedLengthIntegerDigit: number,
            fixedLengthEditingMethod: number, nullValueReplace: number, valueOfNullValueReplace: number, fixedValue: number, valueOfFixedValue: string) {
            
            this.formatSelection = ko.observable(formatSelection);
            this.decimalDigit = ko.observable(decimalDigit);
            this.decimalPointClassification = ko.observable(decimalPointClassification);
            this.decimalFraction = ko.observable(decimalFraction);
            this.outputMinusAsZero = ko.observable(outputMinusAsZero);
            this.fixedValueOperation = ko.observable(fixedValueOperation);
            this.fixedValueOperationSymbol = ko.observable(fixedValueOperationSymbol);
            this.fixedCalculationValue = ko.observable(fixedCalculationValue);
            this.fixedLengthOutput = ko.observable(fixedLengthOutput);
            this.fixedLengthIntegerDigit = ko.observable(fixedLengthIntegerDigit);
            this.fixedLengthEditingMethod = ko.observable(fixedLengthEditingMethod);
            this.nullValueReplace = ko.observable(nullValueReplace);
            this.valueOfNullValueReplace = ko.observable(valueOfNullValueReplace);
            this.fixedValue = ko.observable(fixedValue);  
            this.valueOfFixedValue = ko.observable(valueOfFixedValue);  
        }
    }

}