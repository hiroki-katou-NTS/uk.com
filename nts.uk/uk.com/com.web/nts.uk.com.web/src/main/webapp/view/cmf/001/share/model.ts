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
        TIME = 3
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
    
    export function getSystemTypes(): Array<ItemModel> {
        return [
            new model.ItemModel(0, 'HR System'),
            new model.ItemModel(1, 'Attendance System'),
            new model.ItemModel(2, 'Payroll System'),
            new model.ItemModel(3, 'Office Helper')
        ];
    }

    export class StandardAcceptanceConditionSetting {
        conditionSettingCode: KnockoutObservable<string>;
        dispConditionSettingCode: string;
        conditionSettingName: KnockoutObservable<string>;
        dispConditionSettingName: string;
        deleteExistData: KnockoutObservable<number> = ko.observable(0);
        deleteExistDataMethod: KnockoutObservable<number> = ko.observable(null);
        acceptMode: KnockoutObservable<number>;
        csvDataItemLineNumber: KnockoutObservable<number>;
        csvDataStartLine: KnockoutObservable<number>;
        systemType: KnockoutObservable<number> = ko.observable(0);

        constructor(code: string, name: string, deleteExistData: number, acceptMode: number, csvDataItemLineNumber: number, csvDataStartLine: number, deleteExistDataMethod?: number) {
            this.conditionSettingCode = ko.observable(code);
            this.dispConditionSettingCode = code;
            this.conditionSettingName = ko.observable(name);
            this.dispConditionSettingName = name;
            this.deleteExistData(deleteExistData);
            if (deleteExistDataMethod) {
                this.deleteExistDataMethod(deleteExistDataMethod);
            }
            this.acceptMode = ko.observable(acceptMode);
            this.csvDataItemLineNumber = ko.observable(csvDataItemLineNumber);
            this.csvDataStartLine = ko.observable(csvDataStartLine);
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

    export class StandardAcceptItem {
        csvItemName: KnockoutObservable<string>;
        csvItemNumber: KnockoutObservable<number>;
        itemType: KnockoutObservable<number>;
        acceptItemNumber: KnockoutObservable<number>;
        acceptItemName: KnockoutObservable<string>;
        conditionSettingCode: KnockoutObservable<string>;

        constructor(csvItemName: string, csvItemNumber: number, itemType: number, acceptItemNumber: number, acceptItemName: string, conditionCode: string) {
            this.csvItemName = ko.observable(csvItemName);
            this.csvItemNumber = ko.observable(csvItemNumber);
            this.itemType = ko.observable(itemType);
            this.acceptItemNumber = ko.observable(acceptItemNumber);
            this.acceptItemName = ko.observable(acceptItemName);
            this.conditionSettingCode = ko.observable(conditionCode);
        }
    }

    export class ExternalAcceptanceCategory {
        categoryCode: KnockoutObservable<string>;
        categoryName: KnockoutObservable<string>;
        dispCategoryCode: string;
        dispCategoryName: string;

        constructor(code: string, name: string) {
            this.categoryCode = ko.observable(code);
            this.categoryName = ko.observable(name);
            this.dispCategoryCode = code;
            this.dispCategoryName = name;
        }
    }

    export class ExternalAcceptanceCategoryItemData {
        itemCode: KnockoutObservable<string>;
        itemName: KnockoutObservable<string>;
        dispItemCode: string;
        dispItemName: string;

        constructor(code: string, name: string) {
            this.itemCode = ko.observable(code);
            this.itemName = ko.observable(name);
            this.dispItemCode = code;
            this.dispItemName = name;
        }
    }

    //screen F, screen K
    export class AcceptanceCodeConvert {
        convertCode: KnockoutObservable<string>;
        convertName: KnockoutObservable<string>;
        dispConvertCode: string;
        dispConvertName: string;
        convertDetails: KnockoutObservableArray<CodeConvertDetail>;
        acceptCodeWithoutSettings: KnockoutObservable<number>;

        constructor(code: string, name: string, details: Array<CodeConvertDetail>, acceptWithoutSettings: number) {
            this.convertCode = ko.observable(code);
            this.convertName = ko.observable(name);
            this.dispConvertCode = code;
            this.dispConvertName = name;
            this.convertDetails = ko.observableArray(details);
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

    //screen G
    export class NumericDataFormatSetting {
        fixedValue: KnockoutObservable<number>;
        decimalDivision: KnockoutObservable<number>;
        effectiveDigitLength: KnockoutObservable<number>;
        codeConvertCode: KnockoutObservable<string>;
        valueOfFixed: KnockoutObservable<string>;
        decimalDigitNumber: KnockoutObservable<number>;
        startDigit: KnockoutObservable<number>;
        endDigit: KnockoutObservable<number>;
        decimalPointClassification: KnockoutObservable<number>;
        decimalFraction: KnockoutObservable<number>;

        constructor() {

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
        valueOfFixed: KnockoutObservable<string>;
        startDigit: KnockoutObservable<number>;
        endDigit: KnockoutObservable<number>;

        constructor() {

        }
    }

    //screen I
    export class DateDataFormatSetting {
        fixedValue: KnockoutObservable<number>;
        formatSelection: KnockoutObservable<number>;
        importedJapCalendarName: KnockoutObservable<number>;
        valueOfFixed: KnockoutObservable<string>;

        constructor() {

        }
    }

    //screen J
    export class InstantTimeDataFormatSetting {
        fixedValue: KnockoutObservable<number>;
        decimalDivision: KnockoutObservable<number>;
        effectiveDigitLength: KnockoutObservable<number>;
        codeConvertCode: KnockoutObservable<string>;
        valueOfFixed: KnockoutObservable<string>;
        decimalDigitNumber: KnockoutObservable<number>;
        startDigit: KnockoutObservable<number>;
        endDigit: KnockoutObservable<number>;
        decimalPointClassification: KnockoutObservable<number>;
        decimalFraction: KnockoutObservable<number>;

        constructor() {

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
        receiptItemNumber: KnockoutObservable<number>;
        
        constructor(receiptItemName: string, selectComparisonCondition: number,timeConditionValue2: number, timeConditionValue1: number, timeMomentConditionValue2:number, timeMomentConditionValue1: number,
        dateConditionValue2: string, dateConditionValue1: string, characterConditionValue2: string, characterConditionValue1: string, numberConditionValue2: number, numberConditionValue1: number) {
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

    //screen R
    export class ImExErrorLog {
        recordNumber: number;
        csvFieldName: string;
        fieldName: string;
        fieldValue: string;
        errorDesciption: string;
        constructor(recordNumber: number, csvFieldName: string, fieldName: string, fieldValue: string, errorDesciption: string) {
            this.recordNumber = recordNumber;
            this.csvFieldName = csvFieldName;
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;
            this.errorDesciption = errorDesciption;
        }
    }

    export class ImExExecuteResultLogR {
        condCode: KnockoutObservable<string>;
        condName: KnockoutObservable<string>;
        startTime: KnockoutObservable<string>;
        totalCount: KnockoutObservable<string>;
        normalCount: KnockoutObservable<string>;
        errorCount: KnockoutObservable<string>;

        constructor(condCode: string, condName: string, startTime: string, totalCount: string, normalCount: string, errorCount: string) {
            this.condCode = ko.observable(condCode);
            this.condName = ko.observable(condName);
            this.startTime = ko.observable(startTime);
            this.totalCount = ko.observable(totalCount);
            this.normalCount = ko.observable(normalCount);
            this.errorCount = ko.observable(errorCount);
        }

    }

}