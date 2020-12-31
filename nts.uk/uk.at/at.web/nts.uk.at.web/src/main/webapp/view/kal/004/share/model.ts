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
        extractionDaily?: ExtractionDailyDto;
        extractionUnit?: PeriodUnitDto;
        listExtractionMonthly ?: Array<ExtractionPeriodMonthlyDto>;
        extractionYear ?: ExtractionRangeYearDto;
        extractionMutilMonth ?: ExtractionAverMonthDto;
    }



    export interface AlarmCheckConditonCodeDto {
        category: EnumConstantDto;
        checkConditonCode: string;
        checkConditionName: string;
    }


    export class ModelCheckConditonCode {
        GUID: string;
        category: number;
        categoryName: string;
        checkConditonCode: string;
        checkConditionName: string;
        cssClass: string;
        constructor(dto: AlarmCheckConditonCodeDto) {
            this.category = dto.category.value;
            this.categoryName = dto.category.localizedName;
            this.checkConditonCode = dto.checkConditonCode;
            this.checkConditionName = dto.checkConditionName;
            this.GUID = dto.category.value + dto.checkConditonCode;
            this.cssClass = "";
        }

        public static createNotFoundCheckConditonCode(category: EnumConstantDto, checkConditonCode: string): ModelCheckConditonCode {
            var result = new ModelCheckConditonCode({
                category: category,
                checkConditonCode: checkConditonCode,
                checkConditionName: nts.uk.resource.getText('KAL004_117'),
            });
            result.cssClass = "red-color";
            return result;
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

    export interface PeriodUnitDto {
        extractionId: string;
        extractionRange: number;
        segmentationOfCycle: number;
    }

    export interface ExtractionPeriodMonthlyDto{
         extractionId: string;
         extractionRange: number;
         unit: number;        
         strSpecify : number;        
         yearType: number;
         specifyMonth: number;
         strMonth: number;
         strCurrentMonth: number;
         strPreviousAtr: number;
         endSpecify: number;
         extractPeriod: number;
         endMonth: number;
         endCurrentMonth: number;
         endPreviousAtr: number;                    
    }
    
    export interface ExtractionRangeYearDto{
         extractionId: string;
         extractionRange: number;
         year : number;
         thisYear : number;           
    }
    
    export interface ExtractionAverMonthDto{
         extractionId: string;
         extractionRange: number;
         strMonth: number;
        
    }

    //Command
    export class AddAlarmPatternSettingCommand {
        alarmPatternCD: string;
        alarmPatterName: string;
        alarmPerSet: AlarmPermissionSettingCommand;
        checkConditonList: Array<CheckConditionCommand>;

        constructor(alarmPatternCD: string, alarmPatterName: string, alarmPerSet: AlarmPermissionSettingCommand, checkConditonList: Array<CheckConditionCommand>) {
            this.alarmPatternCD = alarmPatternCD;
            this.alarmPatterName = alarmPatterName;
            this.alarmPerSet = alarmPerSet;
            this.checkConditonList = checkConditonList;
        }
    }

    export class AlarmPermissionSettingCommand {
        authSetting: boolean;
        roleIds: Array<string>;
        constructor(authSetting: boolean, roleIds: Array<string>) {
            this.authSetting = authSetting;
            this.roleIds = roleIds;
        }

    }
    export class CheckConditionCommand {
        alarmCategory: number;
        extractionPeriodDaily: ExtractionPeriodDailyCommand;
        checkConditionCodes: Array<string>;
        extractionPeriodUnit: PeriodUnitCommand;
        listExtractionMonthly : Array<ExtractionPeriodMonthlyCommand>=[];
        extractionYear: ExtractionRangeYearCommand;
        extractionAverMonth: ExtractionAverageMonthCommand;
        
        constructor(alarmCategory: number, checkConditionCodes: Array<string>, extractionPeriodDaily: ExtractionPeriodDailyCommand,
            extractionPeriodUnit: PeriodUnitCommand, listExtractionMonthly: Array<ExtractionPeriodMonthlyCommand>, extractionYear:  ExtractionRangeYearCommand, extractionAverMonth: ExtractionAverageMonthCommand) {
            this.alarmCategory = alarmCategory;
            this.checkConditionCodes = checkConditionCodes;
            
            
            if (alarmCategory ==5  || alarmCategory == 13 || alarmCategory == 8) {
                if(nts.uk.util.isNullOrUndefined(extractionPeriodDaily)){
                    this.setDefaultDaily();
                } else {
                    this.extractionPeriodDaily = extractionPeriodDaily;
                }
            }
            
            
            
            if(alarmCategory == 2){
                if(nts.uk.util.isNullOrUndefined(extractionPeriodUnit)){
                    this.setDefaulttUnit();
                }else{
                    this.extractionPeriodUnit =  extractionPeriodUnit;
                }
            }
            
            
            if(alarmCategory == 7  || alarmCategory == 9){
                if(listExtractionMonthly.length==0){
                    this.setDefaultMonthly(3);
                }else{
                    this.listExtractionMonthly =  listExtractionMonthly;
                }
            }
            
            if(alarmCategory ==12){
                if(listExtractionMonthly.length ==0){                                        
                    this.setDefaultMonthly(0);
                    this.setDefaultMonthly(1);
                    this.setDefaultMonthly(2);        
                }else{
                    this.listExtractionMonthly =  listExtractionMonthly;    
                }
                
                if(extractionPeriodDaily==null){
                    this.setDefaultDaily();                        
                }else{
                    this.extractionPeriodDaily=    extractionPeriodDaily;                        
                }
                
                if(extractionYear ==null){
                    this.setDefaultYear();   
                }else{
                    this.extractionYear =    extractionYear; 
                }
                if(extractionAverMonth == null){
                 this.setDefaultMultiMonth();   
                }else{
                   this.extractionAverMonth = extractionAverMonth; 
                }
            }
            if(alarmCategory ==11){
                this.listExtractionMonthly = null;
            }
            
        }

        public setExtractPeriod(extractionPeriodDaily: ExtractionPeriodDailyCommand) {
            this.extractionPeriodDaily = extractionPeriodDaily;
        }
        
        public setExtractUnit(extractionPeriodUnit: PeriodUnitCommand) {
            this.extractionPeriodUnit = extractionPeriodUnit;
        } 
        
        public setExtractMonthly(listExtractionMonthly: Array<ExtractionPeriodMonthlyCommand>) {
            this.listExtractionMonthly = listExtractionMonthly;
        }
        public setExtractYearly( extractionYear : ExtractionRangeYearCommand){
            this.extractionYear = extractionYear;
        }
        public setExtractionAverMonth( extractionAverMonth : ExtractionAverageMonthCommand){
            this.extractionAverMonth = extractionAverMonth;
        }
        
        public setDefaultPer(){
            
        }
        
        public setDefaultDaily() {
               this.extractionPeriodDaily = new ExtractionPeriodDailyCommand({
                                                extractionId: "",
                                                extractionRange: 0,
                                                strSpecify: 1,
                                                strPreviousDay: null,
                                                strMakeToDay: null,
                                                strDay: null,
                                                strPreviousMonth: 0,
                                                strCurrentMonth: 1,
                                                strMonth: 0,
                                                endSpecify: 1,
                                                endPreviousDay: null,
                                                endMakeToDay: null,
                                                endDay: null,
                                                endPreviousMonth: 0,
                                                endCurrentMonth: 1,
                                                endMonth: 0
                                            });
        }
        
        public setDefaulttUnit() {
                this.extractionPeriodUnit = new PeriodUnitCommand({
                                                extractionId: "",
                                                extractionRange: 3,
                                                segmentationOfCycle: 1
                                            });            
        }
        
        public setDefaultMonthly(unit: number){
            this.listExtractionMonthly.push( new ExtractionPeriodMonthlyCommand({
                                                     extractionId: "",
                                                     extractionRange: 0,
                                                     unit: unit,       
                                                     strSpecify : 1,        
                                                     yearType: 2,
                                                     specifyMonth: 0,
                                                     strMonth: 0,
                                                     strCurrentMonth: 1,
                                                     strPreviousAtr: 0,
                                                     endSpecify: 2,
                                                     extractPeriod: 12,
                                                     endMonth: 0,
                                                     endCurrentMonth: 1,
                                                     endPreviousAtr: 0     
                                             }) );
           
        }
        
        public setDefaultYear(){
            this.extractionYear = new ExtractionRangeYearCommand({
                                        extractionId: "",
                                        extractionRange: 1,
                                        year: 0,
                                        thisYear: 1    
                                    });    
        }
        
        public setDefaultMultiMonth(){
            this.extractionAverMonth = new ExtractionAverageMonthCommand({
                                        extractionId: "",
                                        extractionRange: 4,
                                        strMonth: 0   
                                    });    
        }
    }

    export class ExtractionPeriodDailyCommand {
        extractionId: string;
        extractionRange: number;
        strSpecify: number;
        strPreviousDay: number;
        strMakeToDay: number;
        strDay: number;
        strPreviousMonth: number;
        strCurrentMonth: number;
        strMonth: number;
        endSpecify: number;
        endPreviousDay: number;
        endMakeToDay: number;
        endDay: number;
        endPreviousMonth: number;
        endCurrentMonth: number;
        endMonth: number;

        constructor(extractionDailyDto: ExtractionDailyDto) {
            this.extractionId = extractionDailyDto.extractionId;
            this.extractionRange = extractionDailyDto.extractionRange;
            this.strSpecify = extractionDailyDto.strSpecify;
            this.strPreviousDay = extractionDailyDto.strPreviousDay;
            this.strMakeToDay = extractionDailyDto.strMakeToDay;
            this.strDay = extractionDailyDto.strDay;
            this.strPreviousMonth = extractionDailyDto.strPreviousMonth;
            this.strCurrentMonth = extractionDailyDto.strCurrentMonth;
            this.strMonth = extractionDailyDto.strMonth;
            this.endSpecify = extractionDailyDto.endSpecify;
            this.endPreviousDay = extractionDailyDto.endPreviousDay;
            this.endMakeToDay = extractionDailyDto.endMakeToDay;
            this.endDay = extractionDailyDto.endDay;
            this.endPreviousMonth = extractionDailyDto.endPreviousMonth;
            this.endCurrentMonth = extractionDailyDto.endCurrentMonth;
            this.endMonth = extractionDailyDto.endMonth;
        }
    }
    
    export class PeriodUnitCommand {
        extractionId: string;
        extractionRange: number;
        segmentationOfCycle: number;
        constructor(periodUnitDto: PeriodUnitDto) {
            this.extractionId = periodUnitDto.extractionId;
            this.extractionRange = periodUnitDto.extractionRange;
            this.segmentationOfCycle = periodUnitDto.segmentationOfCycle;
            
        }
    }
    
    export class  ExtractionPeriodMonthlyCommand{
         extractionId: string;
         extractionRange: number;
         unit: number;        
         strSpecify : number;        
         yearType: number;
         specifyMonth: number;
         strMonth: number;
         strCurrentMonth: number;
         strPreviousAtr: number;
         endSpecify: number;
         extractPeriod: number;
         endMonth: number;
         endCurrentMonth: number;
         endPreviousAtr: number;     
        
        constructor(dto: ExtractionPeriodMonthlyDto){
            this.extractionId = dto.extractionId;
            this.extractionRange = dto.extractionRange;
            this.unit = dto.unit;
            this.strSpecify = dto.strSpecify;
            this.yearType = dto.yearType;
            this.specifyMonth = dto.specifyMonth;
            this.strMonth = dto.strMonth;
            this.strCurrentMonth = dto.strCurrentMonth;
            this.strPreviousAtr = dto.strPreviousAtr;
            this.endSpecify = dto.endSpecify;
            this.extractPeriod = dto.extractPeriod;
            this.endMonth = dto.endMonth;
            this.endCurrentMonth = dto.endCurrentMonth;
            this.endPreviousAtr = dto.endPreviousAtr;
            
        }
    }
    
    export class ExtractionRangeYearCommand{
        extractionId: string;
        extractionRange: number;
        year: number;
        thisYear: number;
        constructor(dto: ExtractionRangeYearDto){
            this.extractionId = dto.extractionId;
            this.extractionRange = dto.extractionRange;
            this.year = dto.year;
            this.thisYear = dto.thisYear;
        }
    }
    
    export class ExtractionAverageMonthCommand{
        extractionId: string;
        extractionRange: number;
        strMonth: number;
        constructor(dto: ExtractionAverMonthDto){
            this.extractionId = dto.extractionId;
            this.extractionRange = dto.extractionRange;
            this.strMonth = dto.strMonth;
        }
    }

}