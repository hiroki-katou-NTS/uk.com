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
        
        constructor() {
            
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
        convertDetails: KnockoutObservableArray<CodeConvertDetail>;
        acceptCodeWithoutSettings: KnockoutObservable<number>;
        
        constructor(code: string, name: string, details: Array<CodeConvertDetail>, acceptWithoutSettings: number) {
            this.convertCode = ko.observable(code);
            this.convertName = ko.observable(name);
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
        conditionValue1: KnockoutObservable<string>;
        conditionValue2: KnockoutObservable<string>;
        
        constructor(name: string, compareCondition: number, value1: string, value2: string) {
            this.receiptItemName = ko.observable(name);
            this.selectComparisonCondition = ko.observable(compareCondition);
            this.conditionValue1 = ko.observable(value1);
            this.conditionValue2 = ko.observable(value2);
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
    
    //screen R, screen S
    export class ImExExecuteResultLog {
        
    }
    
    export class ImExErrorLog {
        
    }

}