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
    export interface IImExErrorLog{
        /**
        * ログ連番
        */
       logSeqNumber: number;
        
        /**
        * 会社ID
        */
        cid: string;
        
        /**
        * 外部受入処理ID
        */
        externalProcessId: string;
        
        /**
        * CSVエラー項目名
        */
        csvErrorItemName: string;
        
        /**
        * CSV受入値
        */
        csvAcceptedValue: string;
        
        /**
        * エラー内容
        */
        errorContents: string;
        
        /**
        * レコード番号
        */
        recordNumber: number;
        
        /**
        * ログ登録日時
        */
        logRegDateTime: string;
        
        /**
        * 項目名
        */
        itemName: string;
        
        /**
        * エラー発生区分
        */
        errorAtr: number;
    }
    
    export class ImExErrorLog {
        /**
        * ログ連番
        */
       logSeqNumber: number;
        
        /**
        * 会社ID
        */
        cid: string;
        
        /**
        * 外部受入処理ID
        */
        externalProcessId: string;
        
        /**
        * CSVエラー項目名
        */
        csvErrorItemName: string;
        
        /**
        * CSV受入値
        */
        csvAcceptedValue: string;
        
        /**
        * エラー内容
        */
        errorContents: string;
        
        /**
        * レコード番号
        */
        recordNumber: number;
        
        /**
        * ログ登録日時
        */
        logRegDateTime: string;
        
        /**
        * 項目名
        */
        itemName: string;
        
        /**
        * エラー発生区分
        */
        errorAtr: number;
        
        constructor(param: IImExErrorLog) {
            let self = this;
            if (param)
            {
                self.logSeqNumber(param.logSeqNumber);
                self.cid(param.cid);
                self.externalProcessId(param.externalProcessId);
                self.csvErrorItemName(param.csvErrorItemName);
                self.csvAcceptedValue(param.csvAcceptedValue);
                self.errorContents(param.errorContents);
                self.recordNumber(param.recordNumber);
                self.logRegDateTime(param.logRegDateTime);
                self.errorAtr(param.errorAtr);
            }
        }
    }

    export interface IImExExecuteResultLogR{
        /**
        * 会社ID
        */
        cid: string;
        
        /**
        * 条件設定コード
        */
        conditionSetCd: string;
        
        /**
        * 外部受入処理ID
        */
        externalProcessId: string;
        
        /**
        * 実行者ID
        */
        executorId: string;
        
        /**
        * ユーザID
        */
        userId: string;
        
        /**
        * 処理開始日時
        */
        processStartDatetime: string;
        
        /**
        * 定型区分
        */
        standardAtr: number;
        
        /**
        * 実行形態
        */
        executeForm: number;
        
        /**
        * 対象件数
        */
        targetCount: number;
        
        /**
        * エラー件数
        */
        errorCount: number;
        
        /**
        * ファイル名
        */
        fileName: string;
        
        /**
        * システム種類
        */
        systemType: number;
        
        /**
        * 結果状態
        */
        resultStatus: number;
        
        /**
        * 処理終了日時
        */
        processEndDatetime: string;
        
        /**
        * 処理区分
        */
        processAtr: number;
    }
    
    export class ImExExecuteResultLogR {
        /**
        * 会社ID
        */
        cid: string;
        
        /**
        * 条件設定コード
        */
        conditionSetCd: string;
        
        /**
        * 外部受入処理ID
        */
        externalProcessId: string;
        
        /**
        * 実行者ID
        */
        executorId: string;
        
        /**
        * ユーザID
        */
        userId: string;
        
        /**
        * 処理開始日時
        */
        processStartDatetime: string;
        
        /**
        * 定型区分
        */
        standardAtr: number;
        
        /**
        * 実行形態
        */
        executeForm: number;
        
        /**
        * 対象件数
        */
        targetCount: number;
        
        /**
        * エラー件数
        */
        errorCount: number;
        
        /**
        * ファイル名
        */
        fileName: string;
        
        /**
        * システム種類
        */
        systemType: number;
        
        /**
        * 結果状態
        */
        resultStatus: number;
        
        /**
        * 処理終了日時
        */
        processEndDatetime: string;
        
        /**
        * 処理区分
        */
        processAtr: number;
        
        constructor(param: IImExExecuteResultLogR) {
            let self = this;
            if (param){
                self.cid(param.cid);
                self.conditionSetCd(param.conditionSetCd);
                self.externalProcessId(param.externalProcessId);
                self.executorId(param.executorId);
                self.userId(param.userId);
                self.processStartDatetime(param.processStartDatetime);
                self.standardAtr(param.standardAtr);
                self.executeForm(param.executeForm);
                self.targetCount(param.targetCount);
                self.errorCount(param.errorCount);
                self.fileName(param.fileName);
                self.systemType(param.systemType);
                self.resultStatus(param.resultStatus);
                self.processEndDatetime(param.processEndDatetime);
                self.processAtr(param.processAtr);                                
            }
        }

    }
}