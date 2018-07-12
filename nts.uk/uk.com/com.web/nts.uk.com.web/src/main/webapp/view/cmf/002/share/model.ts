module nts.uk.com.view.cmf002.share.model {
    import getText = nts.uk.resource.getText;

    export enum NOT_USE_ATR {
        NOT_USE = 0,
        USE = 1
    }
    export enum ROUNDING_METHOD {
        TRUNCATION = 0,
        ROUND_UP = 1,
        DOWN_4_UP_5 = 2
    }

    export enum FORMAT_SELECTION {
        NO_DECIMAL = 0,
        DECIMAL = 1
    }

    export enum DECIMAL_POINT_CLASSIFICATION {
        NO_OUTPUT_DECIMAL_POINT = 0,
        OUTPUT_DECIMAL_POINT = 1
    }

    export enum FIXED_LENGTH_EDITING_METHOD {
        ZERO_BEFORE = 0,
        ZERO_AFTER = 1,
        SPACE_BEFORE = 2,
        SPACE_AFTER = 3
    }
    export class AcceptanceCodeConvert {
        convertCode: KnockoutObservable<string>;
        convertName: KnockoutObservable<string>;
        dispConvertCode: string;
        dispConvertName: string;
        acceptCodeWithoutSettings: KnockoutObservable<number>;

        constructor(code: string, name: string, acceptWithoutSettings: number) {
            this.convertCode = ko.observable(code);
            this.convertName = ko.observable(name);
            this.dispConvertCode = code;
            this.dispConvertName = name;
            this.acceptCodeWithoutSettings = ko.observable(acceptWithoutSettings);
        }
    }

    export class CodeConvertDetail {
        lineNumber: KnockoutObservable<number>;
        outputItem: KnockoutObservable<string>;
        systemCode: KnockoutObservable<string>;

        constructor(lineNumber: number, output: string, sysCode: string) {
            this.lineNumber = ko.observable(lineNumber);
            this.outputItem = ko.observable(output);
            this.systemCode = ko.observable(sysCode);
        }
    }
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
    export class CharacterDataFormatSetting {
        effectDigitLength: KnockoutObservable<number>;
        startDigit: KnockoutObservable<number>;
        endDigit: KnockoutObservable<number>;
        codeEditing: KnockoutObservable<number>;
        codeEditDigit: KnockoutObservable<number>;
        codeEditingMethod: KnockoutObservable<number>;
        spaceEditing: KnockoutObservable<number>;
        codeConvertCode: KnockoutObservable<string>;
        nullValueReplace: KnockoutObservable<number>;
        valueOfNullValueReplace: KnockoutObservable<string>;
        fixedValue: KnockoutObservable<number>;
        valueOfFixedValue: KnockoutObservable<string>;
        constructor(effectDigitLength: number, startDigit: number, endDigit: number, codeEditing: number,
            codeEditDigit: number, codeEditingMethod: number, spaceEditing: number, codeConvertCode: string,
            nullValueReplace: number, valueOfNullValueReplace: string, fixedValue: number, valueOfFixedValue: string) {
            this.effectDigitLength = ko.observable(effectDigitLength);
            this.startDigit = ko.observable(startDigit);
            this.endDigit = ko.observable(endDigit);
            this.codeEditing = ko.observable(codeEditing);
            this.codeEditDigit = ko.observable(codeEditDigit);
            this.codeEditingMethod = ko.observable(codeEditingMethod);
            this.spaceEditing = ko.observable(spaceEditing);
            this.codeConvertCode = ko.observable(codeConvertCode);
            this.nullValueReplace = ko.observable(nullValueReplace);
            this.valueOfNullValueReplace = ko.observable(valueOfNullValueReplace);
            this.fixedValue = ko.observable(fixedValue);
            this.valueOfFixedValue = ko.observable(valueOfFixedValue);
        }
    }

    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export class StandardOutputItem {
        outputItemCode: KnockoutObservable<string>;
        dispOutputItemCode: string;
        outputItemName: KnockoutObservable<string>;
        dispOutputItemName: string;
        conditionSettingCode: KnockoutObservable<string>;
        formulaResult: KnockoutObservable<string>;
        itemType: KnockoutObservable<number>;

        constructor(outputItemCode: string, outputItemName: string,
            conditionSettingCode: string, formulaResult: string, itemType: number) {
            this.outputItemCode = ko.observable(outputItemCode);
            this.dispOutputItemCode = outputItemCode;
            this.outputItemName = ko.observable(outputItemName);
            this.dispOutputItemName = outputItemName;
            this.conditionSettingCode = ko.observable(conditionSettingCode);
            this.formulaResult = ko.observable(formulaResult);
            this.itemType = ko.observable(itemType);
        }
    }

    export class CategoryItem {
        categoryItemNo: KnockoutObservable<string>;
        dispCategoryItemNo: string;
        categoryId: KnockoutObservable<number>;
        operationSymbol: KnockoutObservable<number>;
        dispOperationSymbol: string;

        constructor(categoryItemNo: string, categoryId: number, operationSymbol: number) {
            this.categoryItemNo = ko.observable(categoryItemNo);
            this.dispCategoryItemNo = categoryItemNo
            this.categoryId = ko.observable(categoryId);
            this.operationSymbol = ko.observable(operationSymbol);
            //this.dispOperationSymbol = operationSymbol;
        }
    }
    
    export class AtWorkDataOutputItem {
        closedOutput: KnockoutObservable<string>;
        absenceOutput: KnockoutObservable<string>;
        fixedValue: KnockoutObservable<number>;
        valueOfFixedValue: KnockoutObservable<string>;
        atWorkOutput: KnockoutObservable<string>;
        retirementOutput: KnockoutObservable<string>;

        constructor(closedOutput: string, absenceOutput: string, fixedValue: number, valueOfFixedValue: string, atWorkOutput: string, retirementOutput: string) {
            this.closedOutput = ko.observable(closedOutput);
            this.absenceOutput = ko.observable(absenceOutput);
            this.fixedValue = ko.observable(fixedValue);
            this.valueOfFixedValue = ko.observable(valueOfFixedValue);
            this.atWorkOutput = ko.observable(atWorkOutput);
            this.retirementOutput = ko.observable(retirementOutput);
        }
    }

    export class ExternalOutputCategoryItemData {
        itemNo: KnockoutObservable<string>;
        dispItemNo: string;
        itemName: KnockoutObservable<string>;
        dispitemName: string;
        isCheck: KnockoutObservable<boolean>;

        constructor(itemNo: string, itemName: string) {
            this.itemNo = ko.observable(itemNo);
            this.dispItemNo = itemNo;
            this.itemName = ko.observable(itemName);
            this.dispitemName = itemName;
            this.isCheck = ko.observable(false);
        }
    }

    export function getFixedLengthEditingMethod():Array<ItemModel>{
        return [
            new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.ZERO_BEFORE, getText('Enum_FixedLengthEditingMethod_ZERO_BEFORE')),
            new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.ZERO_AFTER, getText('Enum_FixedLengthEditingMethod_ZERO_AFTER')),
            new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.SPACE_BEFORE, getText('Enum_FixedLengthEditingMethod_SPACE_BEFORE')),
            new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.SPACE_AFTER, getText('Enum_FixedLengthEditingMethod_SPACE_AFTER'))
        ];
    }

    export function getNotUseAtr():Array<ItemModel> {
        return [
            new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF002_149')),
            new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF002_150'))
        ];
    }

    export function getRounding(): Array<ItemModel> {
        return [
            new model.ItemModel(0, getText('CMF002_384')),
            new model.ItemModel(1, getText('CMF002_385')),
            new model.ItemModel(2, getText('CMF002_386'))
        ];
    }

    export function getTimeSelected(): Array<ItemModel> {
        return [
            new model.ItemModel(0, getText('CMF002_194')),
            new model.ItemModel(1, getText('CMF002_195'))
        ];
    }

    export function getDecimalSelect(): Array<ItemModel> {
        return [
            new model.ItemModel(0, getText('CMF002_201')),
            new model.ItemModel(1, getText('CMF002_202'))
        ];
    }

    export function getSeparator(): Array<ItemModel> {
        return [
            new model.ItemModel(0, getText('CMF002_398')),
            new model.ItemModel(1, getText('CMF002_399')),
            new model.ItemModel(2, getText('CMF002_400'))
        ];
    }

    export function getItemTypes(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('CMF002_366')),
            new ItemModel(1, getText('CMF002_367')),
            new ItemModel(2, getText('CMF002_368')),
            new ItemModel(3, getText('CMF002_369')),
            new ItemModel(4, getText('CMF002_370')),
            new ItemModel(5, getText('CMF002_371'))
        ];
    }

}