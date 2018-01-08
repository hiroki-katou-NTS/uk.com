module kal004.share.model {
    export interface AlarmPatternSettingDto {
        alarmPatternCD: string;
        alarmPatternName: string;
        alarmPerSet:  AlarmPermissionSettingDto;
        checkConList: Array<CheckConditionDto>;
    }    
    export interface AlarmPermissionSettingDto{
        authSetting: boolean;
        roleIds: Array<string>;        
    }
    export interface CheckConditionDto{
        alarmCategory: number;
        checkConditionCodes: Array<string>;  
    }
    
    export interface AlarmCheckConditonCodeDto{
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
           constructor(dto : AlarmCheckConditonCodeDto) {
               this.category = dto.category.value;
               this.categoryName = dto.category.fieldName;
               this.checkConditonCode = dto.checkConditonCode;
               this.checkConditionName = dto.checkConditionName;
               this.listRoleId = dto.listRoleId;
               this.GUID = dto.category.value + dto.checkConditonCode;
           }
    }
      export interface EnumConstantDto {
        value: number;
        fieldName: string;
        localizedName: string;
      }  
    
    export class ExtractionRangeDto {
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

        constructor(extractionId: string, extractionRange: number, strSpecify: number, strPreviousDay: number, strMakeToDay: number,
            strDay: number, strPreviousMonth: number, strCurrentMonth: number, strMonth: number, endSpecify: number, endPreviousDay: number,
            endMakeToDay: number, endDay: number, endPreviousMonth: number, endCurrentMonth: number, endMonth: number)  {
           
            var self = this;
            self.extractionId = ko.observable(extractionId);
            self.extractionRange = ko.observable(extractionRange);
            self.strSpecify = ko.observable(strSpecify);
            self.strPreviousDay = ko.observable(strPreviousDay);
            self.strMakeToDay = ko.observable(strMakeToDay);
            self.strDay = ko.observable(strDay);
            self.strPreviousMonth = ko.observable(strPreviousMonth);
            self.strCurrentMonth = ko.observable(strCurrentMonth);
            self.strMonth = ko.observable(strMonth);
            self.endSpecify = ko.observable(endSpecify);
            self.endPreviousDay = ko.observable(endPreviousDay);
            self.endMakeToDay = ko.observable(endMakeToDay);
            self.endDay = ko.observable(endDay);
            self.endPreviousMonth = ko.observable(endPreviousMonth);
            self.endCurrentMonth = ko.observable(endCurrentMonth);
            self.endMonth = ko.observable(endMonth);
        }
    }
    
    
    
    
}