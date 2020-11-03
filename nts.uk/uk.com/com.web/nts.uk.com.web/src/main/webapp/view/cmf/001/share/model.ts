module nts.uk.com.view.cmf001.share.model {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;

    export enum SCREEN_MODE {
        NEW = 0,
        UPDATE = 1
    }

    export enum NOT_USE_ATR {
        NOT_USE = 0,
        USE = 1
    }

    export enum SYSTEM_TYPE {
        PERSON_SYS = 0,
        ATTENDANCE_SYS = 1,
        PAYROLL_SYS = 2,
        OFFICE_HELPER = 3
    }

    export enum ACCEPT_MODE {
        INSERT_ONLY = 0,
        UPDATE_ONLY = 1,
        INSERT_AND_UPDATE = 2
    }

    export enum DELETE_EXIST_DATA_METHOD {
        DELETE_ALL = 1,
        DELETE_TARGET = 2
    }

    export enum ITEM_TYPE {
        NUMERIC = 0,
        CHARACTER = 1,
        DATE = 2,
        INS_TIME = 3,
        TIME = 4
    }

    export enum ROUNDING_METHOD {
        TRUNCATION = 0,
        ROUND_UP = 1,
        DOWN_4_UP_5 = 2
    }

    export enum DECIMAL_DEVISION {
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

    export enum M_ACTIVATION {
        Duplicate_Standard = 0,
        Duplicate_User_Settings = 1,
        Duplicate_From_Standard_To_User_Setting = 2,
        Duplicate_From_User_Setting_To_Standard = 3
    }

    export enum HOURLY_SEGMENT {
        //時分
        HOUR_MINUTE = 0,
        //分
        MINUTE = 1
    }

    export enum DECIMAL_SELECTION {
        //60進数
        HEXA_DECIMAL = 0,
        //10進数
        DECIMAL = 1
    }

    export function getSystemTypes(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('CMF001_600')),
            new ItemModel(1, getText('CMF001_601')),
            new ItemModel(2, getText('CMF001_602')),
            new ItemModel(3, getText('CMF001_603'))
        ];
    }

    export function getDeleteExistDataMethod(): Array<ItemModel> {
        return [
            new ItemModel(1, getText('CMF001_604')),
            new ItemModel(2, getText('CMF001_605'))
        ];
    }

    export function getItemTypes(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('CMF001_650')),
            new ItemModel(1, getText('CMF001_651')),
            new ItemModel(2, getText('CMF001_652')),
            new ItemModel(3, getText('CMF001_653')),
            new ItemModel(4, getText('CMF001_654'))
        ];
    }

    export function getCompareTypes(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('CMF001_626')),
            new ItemModel(1, getText('CMF001_627')),
            new ItemModel(2, getText('CMF001_628')),
            new ItemModel(3, getText('CMF001_629')),
            new ItemModel(4, getText('CMF001_630')),
            new ItemModel(5, getText('CMF001_631')),
            new ItemModel(6, getText('CMF001_632')),
            new ItemModel(7, getText('CMF001_633')),
            new ItemModel(8, getText('CMF001_634')),
            new ItemModel(9, getText('CMF001_635')),
            new ItemModel(10, getText('CMF001_636'))
        ];
    }

    export function getDelimiterSetting(): Array<ItemModel> {
        return [
            new model.ItemModel(0, getText('CMF001_620')),
            new model.ItemModel(1, getText('CMF001_621')),
            new model.ItemModel(2, getText('CMF001_622'))
        ];
    }

    export function getTimeRounding(): Array<ItemModel> {
        return [
            // １分未満切り捨て
            new model.ItemModel(1, getText('CMF001_623')),
            // １分未満切り上げ
            new model.ItemModel(2, getText('CMF001_624')),
            // １分未満四捨五入（小数点第１位迄）
            new model.ItemModel(3, getText('CMF001_625'))
        ];
    }

    export function getEncodingList(): Array<EncodingModel> {
        return [
            new model.EncodingModel(3, 'Shift JIS')
        ];
    }

    export class StandardAcceptanceConditionSetting {
        conditionSetCode: KnockoutObservable<string>;
        dispConditionSettingCode: string;
        conditionSetName: KnockoutObservable<string>;
        dispConditionSettingName: string;
        deleteExistData: KnockoutObservable<number>;
        deleteExistDataMethod: KnockoutObservable<number> = ko.observable(null);
        acceptMode: KnockoutObservable<number> = ko.observable(null);
        csvDataItemLineNumber: KnockoutObservable<number> = ko.observable(null);
        csvDataStartLine: KnockoutObservable<number> = ko.observable(null);
        characterCode: KnockoutObservable<number> = ko.observable(null);
        systemType: KnockoutObservable<number>;
        alreadySetting: KnockoutObservable<boolean> = ko.observable(false);
        action: KnockoutObservable<number> = ko.observable(0);
        categoryId: KnockoutObservable<string> = ko.observable(null);

        constructor(systemType: number, code: string, name: string, deleteExistData: number, acceptMode?: number, csvDataItemLineNumber?: number, csvDataStartLine?: number, characterCode?: number, deleteExistDataMethod?: number, categoryId?: string) {
            this.systemType = ko.observable(systemType);
            this.conditionSetCode = ko.observable(code);
            this.dispConditionSettingCode = code;
            this.conditionSetName = ko.observable(name);
            this.dispConditionSettingName = name;
            this.deleteExistData = ko.observable(deleteExistData);
            if (deleteExistDataMethod)
                this.deleteExistDataMethod(deleteExistDataMethod);
            if (!nts.uk.util.isNullOrUndefined(acceptMode))
                this.acceptMode(acceptMode);
            if (csvDataItemLineNumber)
                this.csvDataItemLineNumber(csvDataItemLineNumber);
            if (csvDataStartLine)
                this.csvDataStartLine(csvDataStartLine);
            if (characterCode)
                this.characterCode(characterCode);
            if (categoryId) this.categoryId(categoryId);
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

    export class EncodingModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export class StandardAcceptItem {
        csvItemName: KnockoutObservable<string>;
        csvItemNumber: KnockoutObservable<number>;
        itemType: KnockoutObservable<number>;
        acceptItemNumber: KnockoutObservable<number>;
        acceptItemName: KnockoutObservable<string>;
        conditionSettingCode: KnockoutObservable<string>;
        numberFormatSetting: KnockoutObservable<NumericDataFormatSetting> = ko.observable(null);
        charFormatSetting: KnockoutObservable<CharacterDataFormatSetting> = ko.observable(null);
        dateFormatSetting: KnockoutObservable<DateDataFormatSetting> = ko.observable(null);
        instTimeFormatSetting: KnockoutObservable<InstantTimeDataFormatSetting> = ko.observable(null);
        timeFormatSetting: KnockoutObservable<TimeDataFormatSetting> = ko.observable(null);
        screenConditionSetting: KnockoutObservable<AcceptScreenConditionSetting> = ko.observable(null);
        categoryItemNo: KnockoutObservable<number>;
        systemType: KnockoutObservable<number>;
        sampleData: KnockoutObservable<string> = ko.observable(null);
        bakScreenConditionSetting: AcceptScreenConditionSetting;
        bakItemType: number;

        constructor(csvItemName: string, csvItemNumber: number, itemType: number, acceptItemNumber: number, acceptItemName: string, systemType: number, conditionCode: string, categoryItemNo: number, formatSet?: any, screenSet?: AcceptScreenConditionSetting, sampleData?: string) {
            this.csvItemName = ko.observable(csvItemName);
            this.csvItemNumber = ko.observable(csvItemNumber);
            this.itemType = ko.observable(itemType);
            this.bakItemType = itemType;
            this.acceptItemNumber = ko.observable(acceptItemNumber);
            this.acceptItemName = ko.observable(acceptItemName);
            this.conditionSettingCode = ko.observable(conditionCode);
            this.systemType = ko.observable(systemType);
            this.categoryItemNo = ko.observable(categoryItemNo);
            if (formatSet) {
                switch (itemType) {
                    case model.ITEM_TYPE.NUMERIC:
                        this.numberFormatSetting(formatSet);
                        break;
                    case model.ITEM_TYPE.CHARACTER:
                        this.charFormatSetting(formatSet);
                        break;
                    case model.ITEM_TYPE.DATE:
                        this.dateFormatSetting(formatSet);
                        break;
                    case model.ITEM_TYPE.INS_TIME:
                        this.instTimeFormatSetting(formatSet);
                        break;
                    case model.ITEM_TYPE.TIME:
                        this.timeFormatSetting(formatSet);
                        break;
                }
            }
            if (screenSet) {
                this.screenConditionSetting(screenSet);
                this.bakScreenConditionSetting = screenSet;
            }
            if (sampleData)
                this.sampleData(sampleData);
            this.itemType.subscribe((value) => {
                if (value == this.bakItemType && this.screenConditionSetting() == null)
                    this.screenConditionSetting(this.bakScreenConditionSetting);
                else 
                    this.screenConditionSetting(null);
            });
        }
    }

    export class ExternalAcceptanceCategory {
        categoryId: string;
        categoryName: string;

        constructor(id: string, name: string) {
            this.categoryId = id;
            this.categoryName = name;
        }
    }

    export class ExternalAcceptanceCategoryItemData {
        itemNo: number;
        itemName: string;
        required: boolean;

        constructor(code: number, name: string, required: boolean) {
            this.itemNo = code;
            this.itemName = name;
            if (required) this.required = required;
        }
    }

    //screen F, screen K
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

    //screen E
    export class MappingListData {
        csvItemName: string;
        csvItemNumber: number;
        sampleData: string;

        constructor(itemNumber: number, itemName: string, sampleData?: string) {
            this.csvItemName = itemName;
            this.csvItemNumber = itemNumber;
            if (sampleData) this.sampleData = sampleData;
        }
    }

    //screen G
    export class NumericDataFormatSetting {
        fixedValue: KnockoutObservable<number>;
        decimalDivision: KnockoutObservable<number>;
        effectiveDigitLength: KnockoutObservable<number>;
        codeConvertCode: KnockoutObservable<string>;
        valueOfFixedValue: KnockoutObservable<string>;
        decimalDigitNumber: KnockoutObservable<number>;
        startDigit: KnockoutObservable<number>;
        endDigit: KnockoutObservable<number>;
        decimalPointClassification: KnockoutObservable<number>;
        decimalFraction: KnockoutObservable<number>;

        constructor(effectDigitLength: number, startDigit: number, endDigit: number, decimalDivision: number, decimalDigitNumber: number,
            decimalPointClassification: number, decimalFraction: number, codeConvertCode: string, fixedValue: number, valueOfFixedValue: string) {
            this.fixedValue = ko.observable(fixedValue);
            this.decimalDivision = ko.observable(decimalDivision);
            this.effectiveDigitLength = ko.observable(effectDigitLength);
            this.codeConvertCode = ko.observable(codeConvertCode);
            this.valueOfFixedValue = ko.observable(valueOfFixedValue);
            this.decimalDigitNumber = ko.observable(decimalDigitNumber);
            this.startDigit = ko.observable(startDigit);
            this.endDigit = ko.observable(endDigit);
            this.decimalPointClassification = ko.observable(decimalPointClassification);
            this.decimalFraction = ko.observable(decimalFraction);
        }
    }

    //screen H
    export class CharacterDataFormatSetting {
        codeEditing: KnockoutObservable<number>;
        fixedValue: KnockoutObservable<number>;
        effectiveDigitLength: KnockoutObservable<number>;
        codeConvertCode: KnockoutObservable<string>;
        codeEditingMethod: KnockoutObservable<number>;
        codeEditDigit: KnockoutObservable<number>;
        fixedVal: KnockoutObservable<string>;
        startDigit: KnockoutObservable<number>;
        endDigit: KnockoutObservable<number>;

        constructor(effectDigitLength: number, startDigit: number, endDigit: number, codeEditing: number, codeEditDigit: number,
            codeEditingMethod: number, codeConvertCode: string, fixedValue: number, fixedVal: string) {
            this.fixedValue = ko.observable(fixedValue);
            this.codeEditing = ko.observable(codeEditing);
            this.effectiveDigitLength = ko.observable(effectDigitLength);
            this.codeConvertCode = ko.observable(codeConvertCode);
            this.fixedVal = ko.observable(fixedVal);
            this.codeEditDigit = ko.observable(codeEditDigit);
            this.startDigit = ko.observable(startDigit);
            this.endDigit = ko.observable(endDigit);
            this.codeEditingMethod = ko.observable(codeEditingMethod);
        }
    }

    //screen I
    export class DateDataFormatSetting {
        fixedValue: KnockoutObservable<number>;
        formatSelection: KnockoutObservable<number>;
        //importedJapCalendarName: KnockoutObservable<number>;
        valueOfFixedValue: KnockoutObservable<string>;

        constructor(formatSelection: number, fixedValue: number, valueOfFixedValue: string) {
            this.formatSelection = ko.observable(formatSelection);
            this.fixedValue = ko.observable(fixedValue);
            this.valueOfFixedValue = ko.observable(valueOfFixedValue);
        }
    }

    //screen J
    export class InstantTimeDataFormatSetting {
        effectiveDigitLength: KnockoutObservable<number>;
        startDigit: KnockoutObservable<number>;
        endDigit: KnockoutObservable<number>;
        decimalSelect: KnockoutObservable<number>;
        hourMinSelect: KnockoutObservable<number>;
        delimiterSet: KnockoutObservable<number>;
        roundProc: KnockoutObservable<number>;
        roundProcCls: KnockoutObservable<number>;
        fixedValue: KnockoutObservable<number>;
        valueOfFixedValue: KnockoutObservable<string>;
        constructor(effectiveDigitLength: number, startDigit: number, endDigit: number, decimalSelect: number,
            hourMinSelect: number, delimiterSet: number, roundProc: number,
            roundProcCls: number, fixedValue: number, valueOfFixedValue: string) {
            this.effectiveDigitLength = ko.observable(effectiveDigitLength);
            this.startDigit = ko.observable(startDigit);
            this.endDigit = ko.observable(endDigit);
            this.decimalSelect = ko.observable(decimalSelect);
            this.hourMinSelect = ko.observable(hourMinSelect || 0);
            this.delimiterSet = ko.observable(delimiterSet);
            this.roundProc = ko.observable(roundProc);
            this.roundProcCls = ko.observable(roundProcCls);
            this.fixedValue = ko.observable(fixedValue);
            this.valueOfFixedValue = ko.observable(valueOfFixedValue);
        }
    }

    //screen J
    export class TimeDataFormatSetting {
        effectiveDigitLength: KnockoutObservable<number>;
        startDigit: KnockoutObservable<number>;
        endDigit: KnockoutObservable<number>;
        decimalSelect: KnockoutObservable<number>;
        hourMinSelect: KnockoutObservable<number>;
        delimiterSet: KnockoutObservable<number>;
        roundProc: KnockoutObservable<number>;
        roundProcCls: KnockoutObservable<number>;
        fixedValue: KnockoutObservable<number>;
        valueOfFixedValue: KnockoutObservable<string>;
        constructor(effectiveDigitLength: number, startDigit: number, endDigit: number, decimalSelect: number,
            hourMinSelect: number, delimiterSet: number, roundProc: number,
            roundProcCls: number, fixedValue: number, valueOfFixedValue: string) {
            this.effectiveDigitLength = ko.observable(effectiveDigitLength);
            this.startDigit = ko.observable(startDigit);
            this.endDigit = ko.observable(endDigit);
            this.decimalSelect = ko.observable(decimalSelect);
            this.hourMinSelect = ko.observable(hourMinSelect || 0);
            this.delimiterSet = ko.observable(delimiterSet);
            this.roundProc = ko.observable(roundProc);
            this.roundProcCls = ko.observable(roundProcCls);
            this.fixedValue = ko.observable(fixedValue);
            this.valueOfFixedValue = ko.observable(valueOfFixedValue);
        }
    }

    //screen L
    export class AcceptScreenConditionSetting {
        receiptItemName: KnockoutObservable<string>;
        selectComparisonCondition: KnockoutObservable<number>;
        timeConditionValue2: KnockoutObservable<number>;
        timeConditionValue1: KnockoutObservable<number>;
        timeMomentConditionValue2: KnockoutObservable<number>;
        timeMomentConditionValue1: KnockoutObservable<number>;
        dateConditionValue2: KnockoutObservable<string>;
        dateConditionValue1: KnockoutObservable<string>;
        characterConditionValue2: KnockoutObservable<string>;
        characterConditionValue1: KnockoutObservable<string>;
        numberConditionValue2: KnockoutObservable<number>;
        numberConditionValue1: KnockoutObservable<number>;
        acceptItemNum: KnockoutObservable<number>;
        conditionSetCd: KnockoutObservable<string>;

        constructor(receiptItemName: string, selectComparisonCondition: number, timeConditionValue2: number, timeConditionValue1: number, timeMomentConditionValue2: number, timeMomentConditionValue1: number,
            dateConditionValue2: string, dateConditionValue1: string, characterConditionValue2: string, characterConditionValue1: string, numberConditionValue2: number, numberConditionValue1: number, conditionSetCd?: string, acceptItemNum?: number) {
            this.receiptItemName = ko.observable(receiptItemName);
            this.selectComparisonCondition = ko.observable(selectComparisonCondition);
            this.timeConditionValue2 = ko.observable(timeConditionValue2);
            this.timeConditionValue1 = ko.observable(timeConditionValue1);
            this.timeMomentConditionValue2 = ko.observable(timeMomentConditionValue2);
            this.timeMomentConditionValue1 = ko.observable(timeMomentConditionValue1);
            this.dateConditionValue2 = ko.observable(dateConditionValue2);
            this.dateConditionValue1 = ko.observable(dateConditionValue1);
            this.characterConditionValue2 = ko.observable(characterConditionValue2);
            this.characterConditionValue1 = ko.observable(characterConditionValue1);
            this.numberConditionValue2 = ko.observable(numberConditionValue2);
            this.numberConditionValue1 = ko.observable(numberConditionValue1);

        }
    }

    //screen Q
    export class ImExManagementOperation {
        operationState: KnockoutObservable<number>;
        processCount: KnockoutObservable<number>;
        processTotalCount: KnockoutObservable<number>;
        errorCount: KnockoutObservable<number>;

        constructor(state: number, pCount: number, pCountTotal: number, errorCount: number) {
            this.operationState = ko.observable(state);
            this.processCount = ko.observable(pCount);
            this.processTotalCount = ko.observable(pCountTotal);
            this.errorCount(errorCount);
        }
    }

    //screen S
    export class ImExConditonSetting {
        conditionCode: KnockoutObservable<string>;
        conditionName: KnockoutObservable<string>;
        dispConditionCode: string;
        dispConditionName: string;

        constructor(condCode: string, condName: string) {
            this.conditionCode = ko.observable(condCode);
            this.conditionName = ko.observable(condName);
            this.dispConditionCode = condCode;
            this.dispConditionName = condName;
        }

    }

    export class ImExExecuteResultLog {
        executorId: KnockoutObservable<string>;
        executorName: KnockoutObservable<string>;
        processStartDateTime: KnockoutObservable<string>;
        executeForm: KnockoutObservable<string>;
        targetCount: KnockoutObservable<string>;
        errorCount: KnockoutObservable<string>;
        fileName: KnockoutObservable<string>;
        processId: KnockoutObservable<string>;

        constructor(executorId: string, executorName: string, processStartDateTime: string, executeForm: string, targetCount: string, errorCount: string, fileName: string, processId: string) {
            this.executorId = ko.observable(executorId);
            this.executorName = ko.observable(executorName);
            this.processStartDateTime = ko.observable(processStartDateTime);
            this.executeForm = ko.observable(executeForm);
            this.targetCount = ko.observable(targetCount);
            this.errorCount = ko.observable(errorCount);
            this.fileName = ko.observable(fileName);
            this.processId = ko.observable(processId);
        }

    }


}
