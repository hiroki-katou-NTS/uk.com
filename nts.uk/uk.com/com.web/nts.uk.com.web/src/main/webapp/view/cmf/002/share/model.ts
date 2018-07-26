module nts.uk.com.view.cmf002.share.model {
    import getText = nts.uk.resource.getText;

    export enum SCREEN_MODE {
        NEW = 0,
        UPDATE = 1
    }

    export enum STANDARD_ATR {
        USER = 0,
        STANDARD = 1
    }

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

    export enum RESULT_STATUS {
        SUCCESS = 0,
        INTERRUPTION = 1,
        FAILURE
    }

    export enum DATA_FORMAT_SETTING_SCREEN_MODE {
        INDIVIDUAL = 0,
        INIT = 1
    }

    export enum ITEM_TYPE {
        NUMERIC = 0, // 数値型
        CHARACTER = 1, // 文字型
        DATE = 2, // 日付型
        TIME = 3, // 時間型
        INS_TIME = 4, // 時刻型
        AT_WORK_CLS = 5// 在職区分
    }
    
    export enum SYMBOL {
        AND = 0, // &
        PLUS = 1, // +
        MINUS = 2 // -
    }
    
    export enum SYMBOL_OPRERATION {
        PLUS = 0, // +
        MINUS = 1 // -
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

    export interface INumberDataFormatSetting {
        formatSelection: number,
        decimalDigit: number,
        decimalPointClassification: number,
        decimalFraction: number,
        outputMinusAsZero: number,
        fixedValueOperation: number,
        fixedValueOperationSymbol: number,
        fixedCalculationValue: number,
        fixedLengthOutput: number,
        fixedLengthIntegerDigit: number,
        fixedLengthEditingMethod: number,
        nullValueReplace: number,
        valueOfNullValueReplace: string,
        fixedValue: number,
        valueOfFixedValue: string
    }

    export class NumberDataFormatSetting {
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
        valueOfNullValueReplace: KnockoutObservable<string>;
        fixedValue: KnockoutObservable<number>;
        valueOfFixedValue: KnockoutObservable<string>;
        constructor(params: INumberDataFormatSetting) {
            this.formatSelection = ko.observable(params.formatSelection);
            this.decimalDigit = ko.observable(params.decimalDigit);
            this.decimalPointClassification = ko.observable(params.decimalPointClassification);
            this.decimalFraction = ko.observable(params.decimalFraction);
            this.outputMinusAsZero = ko.observable(params.outputMinusAsZero);
            this.fixedValueOperation = ko.observable(params.fixedValueOperation);
            this.fixedValueOperationSymbol = ko.observable(params.fixedValueOperationSymbol);
            this.fixedCalculationValue = ko.observable(params.fixedCalculationValue);
            this.fixedLengthOutput = ko.observable(params.fixedLengthOutput);
            this.fixedLengthIntegerDigit = ko.observable(params.fixedLengthIntegerDigit);
            this.fixedLengthEditingMethod = ko.observable(params.fixedLengthEditingMethod);
            this.nullValueReplace = ko.observable(params.nullValueReplace);
            this.valueOfNullValueReplace = ko.observable(params.valueOfNullValueReplace);
            this.fixedValue = ko.observable(params.fixedValue);
            this.valueOfFixedValue = ko.observable(params.valueOfFixedValue);
        }
    }
    
     export interface ICharacterDataFormatSetting {
        effectDigitLength: number;
        startDigit: number;
        endDigit: number;
        codeEditing: number;
        codeEditDigit: number;
        codeEditingMethod: number;
        spaceEditing: number;
        codeConvertCode: string;
        nullValueReplace: number;
        valueOfNullValueReplace: string;
        fixedValue: number;
        valueOfFixedValue: string;
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
        constructor(params: ICharacterDataFormatSetting) {
            this.effectDigitLength = ko.observable(params.effectDigitLength);
            this.startDigit = ko.observable(params.startDigit);
            this.endDigit = ko.observable(params.endDigit);
            this.codeEditing = ko.observable(params.codeEditing);
            this.codeEditDigit = ko.observable(params.codeEditDigit);
            this.codeEditingMethod = ko.observable(params.codeEditingMethod);
            this.spaceEditing = ko.observable(params.spaceEditing);
            this.codeConvertCode = ko.observable(params.codeConvertCode);
            this.nullValueReplace = ko.observable(params.nullValueReplace);
            this.valueOfNullValueReplace = ko.observable(params.valueOfNullValueReplace);
            this.fixedValue = ko.observable(params.fixedValue);
            this.valueOfFixedValue = ko.observable(params.valueOfFixedValue);
        }
    }
    
    export class TimeDataFormatSetting {
        nullValueSubs: KnockoutObservable<number>;
        outputMinusAsZero: KnockoutObservable<number>;
        fixedValue: KnockoutObservable<number>;
        valueOfFixedValue: KnockoutObservable<string>;
        fixedLengthOutput: KnockoutObservable<number>;
        fixedLongIntegerDigit: KnockoutObservable<number>;
        fixedLengthEditingMothod: KnockoutObservable<number>;
        delimiterSetting: KnockoutObservable<number>;
        selectHourMinute: KnockoutObservable<number>;
        minuteFractionDigit: KnockoutObservable<number>;
        decimalSelection: KnockoutObservable<number>;
        fixedValueOperationSymbol: KnockoutObservable<number>;
        fixedValueOperation: KnockoutObservable<number>;
        fixedCalculationValue: KnockoutObservable<number>;
        valueOfNullValueSubs: KnockoutObservable<string>;
        minuteFractionDigitProcessCls: KnockoutObservable<number>;
        constructor(params: ITimeDataFormatSetting) {
            this.nullValueSubs = ko.observable(params.nullValueSubs);
            this.outputMinusAsZero = ko.observable(params.outputMinusAsZero == 1);
            this.fixedValue = ko.observable(params.fixedValue); 
            this.valueOfFixedValue = ko.observable(params.valueOfFixedValue);
            this.fixedLengthOutput = ko.observable(params.fixedLengthOutput);
            this.fixedLongIntegerDigit = ko.observable(params.fixedLongIntegerDigit);
            this.fixedLengthEditingMothod = ko.observable(params.fixedLengthEditingMothod);
            this.delimiterSetting = ko.observable(params.delimiterSetting);
            this.selectHourMinute = ko.observable(params.selectHourMinute);
            this.minuteFractionDigit = ko.observable(params.minuteFractionDigit);
            this.decimalSelection = ko.observable(params.decimalSelection);
            this.fixedValueOperationSymbol = ko.observable(params.fixedValueOperationSymbol);
            this.fixedValueOperation = ko.observable(params.fixedValueOperation);
            this.fixedCalculationValue = ko.observable(params.fixedCalculationValue);
            this.valueOfNullValueSubs = ko.observable(params.valueOfNullValueSubs);
            this.minuteFractionDigitProcessCls = ko.observable(params.minuteFractionDigitProcessCls);
        }
    }

    export class InTimeDataFormatSetting {
        nullValueSubs: KnockoutObservable<number>;
        outputMinusAsZero: KnockoutObservable<number>;
        fixedValue: KnockoutObservable<number>;
        valueOfFixedValue: KnockoutObservable<string>;
        timeSeletion: KnockoutObservable<number>;
        fixedLengthOutput: KnockoutObservable<number>;
        fixedLongIntegerDigit: KnockoutObservable<number>;
        fixedLengthEditingMothod: KnockoutObservable<number>;
        delimiterSetting: KnockoutObservable<number>;
        previousDayOutputMethod: KnockoutObservable<number>;
        nextDayOutputMethod: KnockoutObservable<number>;
        minuteFractionDigit: KnockoutObservable<number>;
        decimalSelection: KnockoutObservable<number>;
        minuteFractionDigitProcessCls: KnockoutObservable<number>;
        valueOfNullValueSubs: KnockoutObservable<number>;
        constructor(params: IInTimeDataFormatSetting) {
            this.nullValueSubs = ko.observable(params.nullValueSubs);
            this.outputMinusAsZero = ko.observable(params.outputMinusAsZero == 1);
            this.fixedValue = ko.observable(params.fixedValue);
            this.valueOfFixedValue = ko.observable(params.valueOfFixedValue);
            this.timeSeletion = ko.observable(params.timeSeletion);
            this.fixedLengthOutput = ko.observable(params.fixedLengthOutput);
            this.fixedLongIntegerDigit = ko.observable(params.fixedLongIntegerDigit);
            this.fixedLengthEditingMothod = ko.observable(params.fixedLengthEditingMothod);
            this.delimiterSetting = ko.observable(params.delimiterSetting);
            this.previousDayOutputMethod = ko.observable(params.previousDayOutputMethod);
            this.nextDayOutputMethod = ko.observable(params.nextDayOutputMethod);
            this.minuteFractionDigit = ko.observable(params.minuteFractionDigit);
            this.decimalSelection = ko.observable(params.decimalSelection);
            this.minuteFractionDigitProcessCls = ko.observable(params.minuteFractionDigitProcessCls);
            this.valueOfNullValueSubs = ko.observable(params.valueOfNullValueSubs);
        }
    }
    
    export interface ITimeDataFormatSetting {
        nullValueSubs: number;
        outputMinusAsZero: number;
        fixedValue: number;
        valueOfFixedValue: string;
        fixedLengthOutput: number;
        fixedLongIntegerDigit: number;
        fixedLengthEditingMothod: number;
        delimiterSetting: number;
        selectHourMinute: number;
        minuteFractionDigit: number;
        decimalSelection: number;
        fixedValueOperationSymbol: number;
        fixedValueOperation: number;
        fixedCalculationValue: number;
        valueOfNullValueSubs: string;
        minuteFractionDigitProcessCls: number;
    }
    
    export interface IInTimeDataFormatSetting {
        nullValueSubs: number;
        outputMinusAsZero: number;
        fixedValue: number;
        valueOfFixedValue: string;
        timeSeletion: number;
        fixedLengthOutput: number;
        fixedLongIntegerDigit: number;
        fixedLengthEditingMothod: number;
        delimiterSetting: number;
        previousDayOutputMethod: string;
        nextDayOutputMethod: number;
        minuteFractionDigit: number;
        decimalSelection: number;
        minuteFractionDigitProcessCls: number;
        valueOfNullValueSubs: number;
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
        outItemCd: KnockoutObservable<string>;
        dispOutputItemCode: string;
        outItemName: KnockoutObservable<string>;
        dispOutputItemName: string;
        condSetCd: KnockoutObservable<string>;
        formulaResult: KnockoutObservable<string>;
        itemType: KnockoutObservable<number>;
        categoryItems: KnockoutObservableArray<CategoryItem>;
        atWorkDataOutputItem: KnockoutObservable<model.AtWorkDataOutputItem>;
        characterDataFormatSetting: KnockoutObservable<model.CharacterDataFormatSetting>;
        dateDataFormatSetting: KnockoutObservable<model.DateDataFormatSetting>;
        inTimeDataFormatSetting: KnockoutObservable<model.InTimeDataFormatSetting>;
        numberDataFormatSetting: KnockoutObservable<model.NumberDataFormatSetting>;
        timeDataFormatSetting: KnockoutObservable<model.TimeDataFormatSetting>;

        constructor(outItemCd: string, outItemName: string, condSetCd: string,
            formulaResult: string, itemType: number, categoryItems: Array<CategoryItem>) {
            this.outItemCd = ko.observable(outItemCd);
            this.dispOutputItemCode = outItemCd;
            this.outItemName = ko.observable(outItemName);
            this.dispOutputItemName = outItemName;
            this.condSetCd = ko.observable(condSetCd);
            this.formulaResult = ko.observable(formulaResult);
            this.itemType = ko.observable(itemType);
            this.categoryItems = ko.observableArray(categoryItems);
            let self = this;
            self.categoryItems.subscribe(function(values: Array<CategoryItem>) {
                let newFormulaResult = "";
                _.forEach(values, item => {
                    newFormulaResult = newFormulaResult + item.dispOperationSymbol + item.categoryItemName();
                });
                self.formulaResult(newFormulaResult);
            });
        }
    }

    export class CategoryItem {
        categoryId: KnockoutObservable<number>;
        categoryItemNo: KnockoutObservable<string>;
        dispCategoryItemNo: string;
        categoryItemName: KnockoutObservable<string>;
        dispCategoryItemName: string;
        operationSymbol: KnockoutObservable<number>;
        dispOperationSymbol: string;
        displayOrder: number;

        constructor(categoryId: number, categoryItemNo: string, categoryItemName: string,
            operationSymbol: number, displayOrder: number) {
            this.categoryId = ko.observable(categoryId);
            this.categoryItemNo = ko.observable(categoryItemNo);
            this.dispCategoryItemNo = categoryItemNo
            this.categoryItemName = ko.observable(categoryItemName);
            this.dispCategoryItemName = categoryItemName;
            this.operationSymbol = ko.observable(operationSymbol);
            this.dispOperationSymbol = this.getOperationSymbolText(operationSymbol);
            this.displayOrder = displayOrder;
        }
        
        getOperationSymbolText(operationSymbol: number): string {
            switch (operationSymbol) {
                case model.SYMBOL.AND:
                    return getText('CMF002_91');
                case model.SYMBOL.PLUS:
                    return getText('CMF002_92');
                case model.SYMBOL.MINUS:
                    return getText('CMF002_93');
                default:
                    return "";    
            }
        }
    }

    export interface IAtWorkDataOutputItem {
        closedOutput: string;
        absenceOutput: string;
        fixedValue: number;
        valueOfFixedValue: string;
        atWorkOutput: string;
        retirementOutput: string;
    }
    
    export class AtWorkDataOutputItem {
        closedOutput: KnockoutObservable<string>;
        absenceOutput: KnockoutObservable<string>;
        fixedValue: KnockoutObservable<number>;
        valueOfFixedValue: KnockoutObservable<string>;
        atWorkOutput: KnockoutObservable<string>;
        retirementOutput: KnockoutObservable<string>;

        constructor(params: IAtWorkDataOutputItem) {
            this.closedOutput = ko.observable(params.closedOutput);
            this.absenceOutput = ko.observable(params.absenceOutput);
            this.fixedValue = ko.observable(params.fixedValue);
            this.valueOfFixedValue = ko.observable(params.valueOfFixedValue);
            this.atWorkOutput = ko.observable(params.atWorkOutput);
            this.retirementOutput = ko.observable(params.retirementOutput);
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

    export function getFixedLengthEditingMethod(): Array<ItemModel> {
        return [
            new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.ZERO_BEFORE, getText('Enum_FixedLengthEditingMethod_ZERO_BEFORE')),
            new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.ZERO_AFTER, getText('Enum_FixedLengthEditingMethod_ZERO_AFTER')),
            new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.SPACE_BEFORE, getText('Enum_FixedLengthEditingMethod_SPACE_BEFORE')),
            new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.SPACE_AFTER, getText('Enum_FixedLengthEditingMethod_SPACE_AFTER'))
        ];
    }
    
    export function getOperationSymbol(): Array<ItemModel> {
        return [
            new model.ItemModel(model.SYMBOL_OPRERATION.PLUS, getText('CMF002_389')),
            new model.ItemModel(model.SYMBOL_OPRERATION.MINUS, getText('CMF002_390'))
        ];
    }

    export function getNotUseAtr(): Array<ItemModel> {
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
            new ItemModel(ITEM_TYPE.NUMERIC, getText('CMF002_366')),
            new ItemModel(ITEM_TYPE.CHARACTER, getText('CMF002_367')),
            new ItemModel(ITEM_TYPE.DATE, getText('CMF002_368')),
            new ItemModel(ITEM_TYPE.TIME, getText('CMF002_369')),
            new ItemModel(ITEM_TYPE.TIME_OF_DAY, getText('CMF002_370')),
            new ItemModel(ITEM_TYPE.IN_SERVICE_CATEGORY, getText('CMF002_371'))
        ];
    }

    export class OutputCodeConvert {
        convertCode: KnockoutObservable<string>;
        convertName: KnockoutObservable<string>;
        acceptWithoutSetting: KnockoutObservable<number>;
        dispConvertCode: string;
        dispConvertName: string;

        constructor(code: string, name: string, acceptWithoutSetting: number) {
            this.convertCode = ko.observable(code);
            this.convertName = ko.observable(name);
            this.acceptWithoutSetting = ko.observable(acceptWithoutSetting);
            this.dispConvertCode = code;
            this.dispConvertName = name;
        }
    }

    export function getNextDay(): Array<ItemModel> {
        return [
            new model.ItemModel(0, getText('CMF002_401')),
            new model.ItemModel(1, getText('CMF002_402'))
        ];
    }

    export function getPreDay(): Array<ItemModel> {
        return [
            new model.ItemModel(0, getText('CMF002_403')),
            new model.ItemModel(1, getText('CMF002_404')),
            new model.ItemModel(1, getText('CMF002_405'))
        ];
    }


    export enum EXIOOPERATIONSTATE {

        PERPAKING = 0,

        EXPORTING = 1,

        IMPORTING = 2,

        TEST_FINISH = 3,

        INTER_FINISH = 4,

        FAULT_FINISH = 5,

        CHECKING = 6,

        EXPORT_FINISH = 7,

        IMPORT_FINISH = 8
    }

    export function getStatusEnumS(): Array<ItemModel> {
        return [
            new model.ItemModel(EXIOOPERATIONSTATE.PERPAKING, getText('CMF002_515')),
            new model.ItemModel(EXIOOPERATIONSTATE.EXPORTING, getText('CMF002_516')),
            new model.ItemModel(EXIOOPERATIONSTATE.IMPORTING, getText('CMF002_517')),
            new model.ItemModel(EXIOOPERATIONSTATE.TEST_FINISH, getText('CMF002_518')),
            new model.ItemModel(EXIOOPERATIONSTATE.INTER_FINISH, getText('CMF002_519')),
            new model.ItemModel(EXIOOPERATIONSTATE.FAULT_FINISH, getText('CMF002_520')),
            new model.ItemModel(EXIOOPERATIONSTATE.CHECKING, getText('CMF002_521')),
            new model.ItemModel(EXIOOPERATIONSTATE.EXPORT_FINISH, getText('CMF002_522')),
            new model.ItemModel(EXIOOPERATIONSTATE.IMPORT_FINISH, getText('CMF002_523')),
        ];
    }

    export class DateDataFormatSetting {
        formatSelection: KnockoutObservable<number>;
        nullValueSubstitution: KnockoutObservable<number>;
        fixedValue: KnockoutObservable<number>;
        valueOfNullValueSubs: KnockoutObservable<string>;
        valueOfFixedValue: KnockoutObservable<string>;

        constructor(params: IDateDataFormatSetting) {
            this.formatSelection = ko.observable(params.formatSelection);
            this.nullValueSubstitution = ko.observable(params.nullValueSubstitution);
            this.valueOfNullValueSubs = ko.observable(params.valueOfNullValueSubs);
            this.fixedValue = ko.observable(params.fixedValue);
            this.valueOfFixedValue = ko.observable(params.valueOfFixedValue);
        }
    }

    export interface IDateDataFormatSetting {
        formatSelection: number;
        nullValueSubstitution: number;
        fixedValue: number;
        valueOfNullValueSubs: string;
        valueOfFixedValue: string;
    }
}