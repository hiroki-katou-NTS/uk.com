module nts.uk.at.view.kal004.share.model {

    export interface EnumConstantDto {
        value: number;
        fieldName: string;
        localizedName: string;
    }

    export interface AlarmPatternSettingDto {
        alarmPatternCD: string;
        alarmPatternName: string;
        alarmPerSet: AlarmPermissionSettingDto;
        checkConList: Array<CheckConditionDto>;
    }
    export interface AlarmPermissionSettingDto {
        authSetting: boolean;
        roleIds: Array<string>;
    }

    export interface CheckConditionDto {
        alarmCategory: number;
        checkConditionCodes: Array<string>;
        extractionDailyDto?: ExtractionDailyDto;
    }

    export class CheckCondition {
        GUID: string;
        alarmCategory: number;
        categoryName: string;
        extractionDailyDto: ExtractionDaily;
        constructor(alarmCategory: number, categoryName: string, extractionDailyDto?: ExtractionDailyDto) {
            this.alarmCategory = alarmCategory;
            this.categoryName = categoryName
            if (nts.uk.util.isNullOrUndefined(extractionDailyDto)) {
                this.extractionDailyDto = new ExtractionDaily({
                    extractionId: "",
                    extractionRange: 0,
                    strSpecify: 1,
                    strPreviousDay: null,
                    strMakeToDay: null,
                    strDay: null,
                    strPreviousMonth: 1,
                    strCurrentMonth: 0,
                    strMonth: 2,
                    endSpecify: 1,
                    endPreviousDay: null,
                    endMakeToDay: null,
                    endDay: null,
                    endPreviousMonth: 1,
                    endCurrentMonth: 0,
                    endMonth: 1
                });
            } else {
                this.extractionDailyDto = new ExtractionDaily(extractionDailyDto);
            }
        }
    }

    export interface AlarmCheckConditonCodeDto {
        category: EnumConstantDto;
        checkConditonCode: string;
        checkConditionName: string;
        listRoleId: Array<string>;
    }
    
    
    
    export class ModelCheckConditonCode {
        GUID: string;
        category: number;
        categoryName: string;
        checkConditonCode: string;
        checkConditionName: string;
        listRoleId: Array<string>;
        constructor(dto: AlarmCheckConditonCodeDto) {
            this.category = dto.category.value;
            this.categoryName = dto.category.fieldName;
            this.checkConditonCode = dto.checkConditonCode;
            this.checkConditionName = dto.checkConditionName;
            this.listRoleId = dto.listRoleId;
            this.GUID = dto.category.value + dto.checkConditonCode;
        }
    }

    export interface ExtractionDailyDto {
        extractionId: string;
        extractionRange: number;
        strSpecify: number;
        strPreviousDay?: number;
        strMakeToDay?: number;
        strDay?: number;
        strPreviousMonth?: number;
        strCurrentMonth?: number;
        strMonth?: number;
        endSpecify: number;
        endPreviousDay?: number;
        endMakeToDay?: number;
        endDay?: number;
        endPreviousMonth?: number;
        endCurrentMonth?: number;
        endMonth?: number;
    }

    export class ExtractionDaily {
        extractionId: KnockoutObservable<string>;
        extractionRange: KnockoutObservable<number>;
        strSpecify: KnockoutObservable<number>;
        strPreviousDay: KnockoutObservable<number>;
        strMakeToDay: KnockoutObservable<number>;
        strDay: KnockoutObservable<number>;
        strPreviousMonth: KnockoutObservable<number>;
        strCurrentMonth: KnockoutObservable<number>;
        strMonth: KnockoutObservable<number>;
        endSpecify: KnockoutObservable<number>;
        endPreviousDay: KnockoutObservable<number>;
        endMakeToDay: KnockoutObservable<number>;
        endDay: KnockoutObservable<number>;
        endPreviousMonth: KnockoutObservable<number>;
        endCurrentMonth: KnockoutObservable<number>;
        endMonth: KnockoutObservable<number>;

        constructor(dto: ExtractionDailyDto) {
            var self = this;
            self.extractionId = ko.observable(dto.extractionId);
            self.extractionRange = ko.observable(dto.extractionRange);
            self.strSpecify = ko.observable(dto.strSpecify);
            self.strPreviousDay = ko.observable(dto.strPreviousDay);
            self.strMakeToDay = ko.observable(dto.strMakeToDay);
            self.strDay = ko.observable(dto.strDay);
            self.strPreviousMonth = ko.observable(dto.strPreviousMonth);
            self.strCurrentMonth = ko.observable(dto.strCurrentMonth);
            self.strMonth = ko.observable(dto.strMonth);
            self.endSpecify = ko.observable(dto.endSpecify);
            self.endPreviousDay = ko.observable(dto.endPreviousDay);
            self.endMakeToDay = ko.observable(dto.endMakeToDay);
            self.endDay = ko.observable(dto.endDay);
            self.endPreviousMonth = ko.observable(dto.endPreviousMonth);
            self.endCurrentMonth = ko.observable(dto.endCurrentMonth);
            self.endMonth = ko.observable(dto.endMonth);
        }
    }
    
    //interface WorkRecordExtraCon
        export interface IWorkRecordExtraCon {
            errorAlarmCheckID: string;
            checkItem: number;
            messageBold: boolean;
            messageColor: string;
            sortOrderBy: number;
            useAtr?: KnockoutObservable<boolean>;
            nameWKRecord: string;
        }
    
    //class WorkRecordExtraCon
    export class WorkRecordExtraCon {
        errorAlarmCheckID: string;
        checkItem: number;
        messageBold: boolean;
        messageColor: string;
        sortOrderBy: number;
        useAtr: boolean;
        nameWKRecord: string;
        constructor(data: IWorkRecordExtraCon) {
            this.errorAlarmCheckID = data.errorAlarmCheckID;
            this.checkItem = data.checkItem;
            this.messageBold = data.messageBold;
            this.messageColor = data.messageColor;
            this.sortOrderBy = data.sortOrderBy;
            this.useAtr = ko.observable(data.useAtr);
            this.nameWKRecord = data.nameWKRecord;
        }
    }//end class WorkRecordExtraCon
    
    //interface FixedConditionWorkRecord
    export interface IFixedConditionWorkRecord {
        errorAlarmCode: string;
        fixConWorkRecordNo: number;
        message: string;
        useAtr?: boolean;
    }
    //class FixedConditionWorkRecord
    export class FixedConditionWorkRecord {
        errorAlarmCode: string;
        fixConWorkRecordNo: number;
        message: string;
        useAtr: KnockoutObservable<boolean>;
        constructor(data: IFixedConditionWorkRecord) {
            this.errorAlarmCode = data.errorAlarmCode;
            this.fixConWorkRecordNo = data.fixConWorkRecordNo;
            this.message = data.message;
            this.useAtr = ko.observable(data.useAtr);
        }
    }
        
//Command
    export class AddAlarmPatternSettingCommand{        
        alarmPatternCD: string;
        alarmPatterName: string;
        alarmPerSet: AlarmPermissionSettingCommand;
        checkConditonList: Array<CheckConditionCommand>;
        
        constructor(alarmPatternCD: string, alarmPatterName: string, alarmPerSet: AlarmPermissionSettingCommand, checkConditonList: Array<CheckConditionCommand>){
            this.alarmPatternCD = alarmPatternCD;
            this.alarmPatterName = alarmPatterName;
            this.alarmPerSet = alarmPerSet;
            this.checkConditonList = checkConditonList;
        }
    }
    
    export class AlarmPermissionSettingCommand{
        authSetting: boolean;
        roleIds: Array<string>;
        constructor(authSetting: boolean, roleIds: Array<string>){
            this.authSetting = authSetting;
            this.roleIds = roleIds;
        }
        
    }
    export class CheckConditionCommand{
        private alarmCategory: number;
        private extractPeriod: ExtractionPeriodDailyCommand;
        checkConditionCodes: Array<string>;
        
        constructor(alarmCategory: number, checkConditionCodes: Array<string>){
            this.alarmCategory = alarmCategory;
            this.checkConditionCodes = checkConditionCodes;
        }
    
        setExtractPeriod(extractPeriod: ExtractionPeriodDailyCommand) {
            this.extractPeriod =  extractPeriod;
        }
    }
    
    export class ExtractionPeriodDailyCommand{
        
    }
}