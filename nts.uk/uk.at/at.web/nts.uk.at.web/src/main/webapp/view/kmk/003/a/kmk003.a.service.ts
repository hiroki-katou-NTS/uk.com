module nts.uk.at.view.kmk003.a {
    export module service {

        /**
        *  Service paths
        */
        let servicePath: any = {
            findAllWorktime: "at/shared/worktimeset/findAll",
            findWorktimeSetingInfoByCode: "at/shared/worktimeset/findByCode",
            getEnumWorktimeSeting: "at/shared/worktimeset/getenum",
            getPredByWorkTimeCode: "at/shared/pred/findByWorkTimeCode",
            savePred: "at/shared/pred/save",
            findByCodeFlexWorkSetting: "ctx/at/shared/worktime/flexset/findByCode",
            saveFlexWorkSetting: "ctx/at/shared/worktime/flexset/save"
        };

        /**
         * function find all work time set
         */
        export function findAllWorkTimeSet(): JQueryPromise<model.worktimeset.SimpleWorkTimeSettingDto[]> {
            return nts.uk.request.ajax(servicePath.findAllWorktime);
        }
        
         /**
         * function find work time set by code
         */
        export function findWorktimeSetingInfoByCode(workTimeCode: string): JQueryPromise<model.common.WorkTimeSettingInfoDto> {
            return nts.uk.request.ajax(servicePath.findWorktimeSetingInfoByCode + '/' + workTimeCode);
        }
         /**
         * function get all enum by setting work time
         */
        export function getEnumWorktimeSeting(): JQueryPromise<model.worktimeset.WorkTimeSettingEnumDto> {
            return nts.uk.request.ajax(servicePath.getEnumWorktimeSeting);
        }

        export function getPredByWorkTimeCode(workTimeCode: string): JQueryPromise<model.worktimeset.WorkTimeSettingDto> {
            return nts.uk.request.ajax(servicePath.getPredByWorkTimeCode + "/" + workTimeCode);
        }
        
        export function savePred(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.savePred, data);
        }
        /**
         * function find by work time code flex work setting data by call service
         */
        export function findByCodeFlexWorkSetting(worktimeCode: string): JQueryPromise<model.flexset.FlexWorkSettingDto> {
            return nts.uk.request.ajax(servicePath.findByCodeFlexWorkSetting + '/' + worktimeCode);
        } 
        
        /**
         * function save flex work setting by call service
         */
        export function saveFlexWorkSetting(command: model.command.FlexWorkSettingSaveCommand): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.saveFlexWorkSetting, command);
        }

        /**
         * Data Model
         */
        export module model {

            export module common {
                
                export interface CommonRestSettingDto {
                    calculateMethod: number;
                }
                
                export interface TimeRoundingSettingDto {
                    roundingTime: number;
                    rounding: number;
                }
                
                export interface GoOutTimeRoundingSettingDto {
                    roundingMethod: number;
                    roundingSetting: TimeRoundingSettingDto;
                }
                export interface DeductGoOutRoundingSetDto {
                    deductTimeRoundingSetting: GoOutTimeRoundingSettingDto;
                    approTimeRoundingSetting: GoOutTimeRoundingSettingDto;
                }
                
                export interface DeductionTimeDto{
                    start: number;
                    end: number;    
                }
                
                export interface DesignatedTimeDto{
                    oneDayTime: number;
                    halfDayTime: number;    
                }
                
                export interface EmTimezoneLateEarlyCommonSetDto{
                    delFromEmTime: boolean;    
                }
                
                export interface TimeZoneRoundingDto {
                    rounding: TimeRoundingSettingDto;
                    start: number;
                    end: number;
                }
                
                export interface EmTimeZoneSetDto {
                    employmentTimeFrameNo: number;
                    timezone: TimeZoneRoundingDto;
                }
                
                export interface ExtraordWorkOTFrameSetDto{
                    oTFrameNo: number;
                    inLegalWorkFrameNo: number;
                    settlementOrder: number;    
                }
                
                export interface FixedWorkRestSetDto {
                    commonRestSet: CommonRestSettingDto;
                    fixedRestCalculateMethod: number;
                }
                
                export interface FlowFixedRestSetDto{
                    isReferRestTime: boolean;
                    usePrivateGoOutRest: boolean;
                    useAssoGoOutRest: boolean;
                    calculateMethod: number;
                }
                
                export interface FlowRestSetDto {
                    useStamp: boolean;
                    useStampCalcMethod: number;
                    timeManagerSetAtr: number;
                    calculateMethod: number;
                }
                
                export interface FlowRestSettingDto {
                    flowRestTime: number;
                    flowPassageTime: number;
                }
                
                export interface FlowRestTimezoneDto {
                    flowRestSets: FlowRestSettingDto[];
                    useHereAfterRestSet: boolean;
                    hereAfterRestSet: FlowRestSettingDto;
                }
                
                export interface FlowWorkRestSettingDetailDto {
                    flowRestSetting: FlowRestSetDto;
                    flowFixedRestSetting: FlowFixedRestSetDto;
                    usePluralWorkRestTime: boolean;
                }
                
                export interface FlowWorkRestSettingDto{
                    commonRestSetting: CommonRestSettingDto;
                    flowRestSetting: FlowWorkRestSettingDetailDto;    
                }
                
                export interface TimezoneOfFixedRestTimeSetDto{
                    timezones: DeductionTimeDto[];    
                }
                
                export interface FlowWorkRestTimezoneDto{
                    fixRestTime: boolean;
                    fixedRestTimezone: TimezoneOfFixedRestTimeSetDto;
                    flowRestTimezone: FlowRestTimezoneDto;
                }
                
                export interface GoOutTypeRoundingSetDto {
                    officalUseCompenGoOut: DeductGoOutRoundingSetDto;
                    privateUnionGoOut: DeductGoOutRoundingSetDto;
                }

                export interface GoOutTimezoneRoundingSetDto {
                    pubHolWorkTimezone: GoOutTypeRoundingSetDto;
                    workTimezone: GoOutTypeRoundingSetDto;
                    oTTimezone: GoOutTypeRoundingSetDto;
                }
                
                export interface GraceTimeSettingDto{
                    includeWorkingHour: boolean;
                    graceTime: number;
                }
                
                export interface HDWorkTimeSheetSettingDto {
                    workTimeNo: number;
                    timezone: TimeZoneRoundingDto;
                    isLegalHolidayConstraintTime: boolean;
                    inLegalBreakFrameNo: boolean;
                    isNonStatutoryDayoffConstraintTime: boolean;
                    outLegalBreakFrameNo: number;
                    isNonStatutoryHolidayConstraintTime: boolean;
                    outLegalPubHDFrameNo: number;
                }
                
                export interface HolidayFramsetDto{
                    inLegalBreakoutFrameNo: number;
                    outLegalBreakoutFrameNo: number;
                    outLegalPubHolFrameNo: number;    
                }
                
                export interface InstantRoundingDto{
                    fontRearSection: number;
                    roundingTimeUnit: number;
                }
                
                export interface IntervalTimeDto {
                    intervalTime: number;
                    rounding: TimeRoundingSettingDto;
                }
                
                export interface IntervalTimeSettingDto {
                    useIntervalExemptionTime: boolean;
                    intervalExemptionTimeRound: TimeRoundingSettingDto;
                    intervalTime: IntervalTimeDto;
                    useIntervalTime: boolean;
                }
                
                export interface OtherEmTimezoneLateEarlySetDto {
                    delTimeRoundingSet: TimeRoundingSettingDto;
                    stampExactlyTimeIsLateEarly: boolean;
                    graceTimeSet: GraceTimeSettingDto;
                    recordTimeRoundingSet: TimeRoundingSettingDto;
                    lateEarlyAtr: number;
                }
                
                export interface OverTimeOfTimeZoneSetDto{
                    workTimezoneNo: number;
                    restraintTimeUse: boolean;
                    earlyOTUse: boolean;
                    timezone: TimeZoneRoundingDto;
                    oTFrameNo: number;
                    legalOTframeNo: number;
                    settlementOrder: number;
                }
                
                export interface PrioritySettingDto{
                    priorityAtr: number;
                    stampAtr: number;
                }
                
                export interface RoundingSetDto{
                    roundingSet: InstantRoundingDto;
                    section: number;    
                }
                
                export interface StampReflectTimezoneDto{
                    workNo: number;
                    classification: number;
                    endTime: number;
                    startTime: number;
                }
                
                export interface SubHolTransferSetDto{
                    certainTime: number;
                    useDivision: boolean;
                    designatedTime: DesignatedTimeDto;
                    subHolTransferSetAtr: number;    
                }
                
                export interface TotalRoundingSetDto{
                    setSameFrameRounding: number;
                    frameStraddRoundingSet: number;
                }
                
                export interface WorkTimezoneOtherSubHolTimeSetDto{
                    subHolTimeSet: SubHolTransferSetDto;
                    workTimeCode: string;
                    originAtr: number;    
                }
                
                export interface WorkTimezoneMedicalSetDto{
                    roundingSet: TimeRoundingSettingDto;
                    workSystemAtr: number;
                    applicationTime: number;
                }
                
                export interface WorkTimezoneGoOutSetDto{
                    totalRoundingSet: TotalRoundingSetDto;
                    diffTimezoneSetting: GoOutTimezoneRoundingSetDto;    
                }
                                
                export interface WorkTimezoneStampSetDto{
                    roundingSets: RoundingSetDto[];
                    prioritySets: PrioritySettingDto[];    
                }
                
                export interface WorkTimezoneLateNightTimeSetDto{
                    roundingSetting: TimeRoundingSettingDto;
                }
                
                export interface WorkTimezoneShortTimeWorkSetDto{
                    nursTimezoneWorkUse: boolean;    
                    employmentTimeDeduct: boolean;    
                    childCareWorkUse: boolean;    
                }
                
                export interface WorkTimezoneExtraordTimeSetDto{
                    holidayFrameSet: HolidayFramsetDto;
                    timeRoundingSet: TimeRoundingSettingDto;
                    oTFrameSet: ExtraordWorkOTFrameSetDto;
                    calculateMethod: number;    
                }
                
                export interface WorkTimezoneLateEarlySetDto {
                    commonSet: EmTimezoneLateEarlyCommonSetDto;
                    otherClassSets: OtherEmTimezoneLateEarlySetDto[];
                }
                
                export interface WorkTimezoneCommonSetDto{
                    zeroHStraddCalculateSet: boolean;
                    intervalSet: IntervalTimeSettingDto;
                    subHolTimeSet: WorkTimezoneOtherSubHolTimeSetDto;
                    raisingSalarySet: string;
                    medicalSet: WorkTimezoneMedicalSetDto[];
                    goOutSet: WorkTimezoneGoOutSetDto;
                    stampSet: WorkTimezoneStampSetDto;
                    lateNightTimeSet: WorkTimezoneLateNightTimeSetDto;
                    shortTimeWorkSet: WorkTimezoneShortTimeWorkSetDto;
                    extraordTimeSet: WorkTimezoneExtraordTimeSetDto;
                    lateEarlySet: WorkTimezoneLateEarlySetDto;
                }
                
                export interface WorkTimeCommonDto {
                    predseting: predset.PredetemineTimeSettingDto;
                    worktimeSetting: worktimeset.WorkTimeSettingDto;
                }
                
                export interface WorkTimeSettingInfoDto extends WorkTimeCommonDto {
                    flexWorkSetting: flexset.FlexWorkSettingDto;
                    /*fixedWorkSetting:FixedWorkSettingDto ;
                     flowWorkSetting: FlWorkSettingDto;
                    diffTimeWorkSetting: DiffTimeWorkSettingDto ;
                    */
                }
                
            }
            export module flexset {
                
                export interface TimeSheetDto{
                    startTime: number;
                    endTime: number;
                }
                
                
                export interface CoreTimeSettingDto{
                    coreTimeSheet: TimeSheetDto;
                    timesheet: number;
                    minWorkTime: number;
                }
                
                export interface FixedWorkTimezoneSetDto{
                    lstWorkingTimezone: common.EmTimeZoneSetDto[];
                    lstOTTimezone: common.OverTimeOfTimeZoneSetDto[];
                }
                
                export interface FlexCalcSettingDto{
                    removeFromWorkTime: number;    
                    calculateSharing: number;    
                }
                
                export interface FlexHalfDayWorkTimeDto{
                    lstRestTimezone: common.FlowWorkRestTimezoneDto[];
                    workTimezone: FixedWorkTimezoneSetDto;
                    ampmAtr: number;    
                }
                
                export interface FlexOffdayWorkTimeDto{
                    lstWorkTimezone: common.HDWorkTimeSheetSettingDto[];
                    restTimezone: common.FlowWorkRestTimezoneDto;    
                }
                
                export interface FlexWorkSettingDto{
                    workTimeCode: string;
                    coreTimeSetting: CoreTimeSettingDto;
                    restSetting: common.FlowWorkRestSettingDto;
                    offdayWorkTime: FlexOffdayWorkTimeDto;
                    commonSetting: common.WorkTimezoneCommonSetDto;
                    useHalfDayShift: boolean;
                    lstHalfDayWorkTimezone: FlexHalfDayWorkTimeDto[];
                    lstStampReflectTimezone: common.StampReflectTimezoneDto[];
                    calculateSetting: FlexCalcSettingDto;    
                }
                
            }
            export module flowset {
                
                export interface FlCalcSetDto{
                    calcStartTimeSet: number;    
                }
                
                export interface FlTimeSettingDto {
                    rounding: common.TimeRoundingSettingDto;
                    passageTime: number;
                }
                
                export interface FlOTTimezoneDto{
                    worktimeNo: number;
                    restrictTime: boolean;
                    overtimeFrameNo: number;
                    flowTimeSetting: FlTimeSettingDto;
                    inLegalOTFrameNo: number;
                    settlementOrder: number;
                }
                
                export interface FlWorkTzSettingDto{
                    workTimeRounding: common.TimeRoundingSettingDto;
                    lstOTTimezone: FlOTTimezoneDto[];    
                }
                
                export interface FlHalfDayWorkTzDto {
                    restTimezone: common.FlowWorkRestTimezoneDto;
                    workTimeZone: FlWorkTzSettingDto;
                }
                
                export interface FlWorkHdTimeZoneDto{
                    worktimeNo: number;   
                    useInLegalBreakRestrictTime: boolean;
                    inLegalBreakFrameNo: number;
                    useOutLegalBreakRestrictTime: boolean;
                    outLegalBreakFrameNo: number;
                    useOutLegalPubHolRestrictTime: boolean;
                    outLegalPubHolFrameNo: number;
                    flowTimeSetting: FlTimeSettingDto; 
                }
                
                export interface FlOffdayWorkTzDto{
                    restTimeZone: common.FlowWorkRestTimezoneDto;
                    lstWorkTimezone: FlWorkHdTimeZoneDto[];    
                }
                
                export interface FlOTSetDto{
                    fixedChangeAtr: number;    
                }
                
                export interface FlStampReflectTzDto{
                    twoTimesWorkReflectBasicTime: number;
                    stampReflectTimezone: common.StampReflectTimezoneDto[];    
                }
                
                export interface FlWorkDedSettingDto{
                    overtimeSetting: FlOTSetDto;
                    calculateSetting: FlCalcSetDto;    
                }
                
                export interface FlWorkSettingDto{
                    workingCode: string;
                    restSetting: common.FlowWorkRestSettingDto;
                    offdayWorkTimezone: FlOffdayWorkTzDto;
                    commonSetting: common.WorkTimezoneCommonSetDto;
                    halfDayWorkTimezone: FlHalfDayWorkTzDto;
                    stampReflectTimezone: FlStampReflectTzDto;
                    designatedSetting: number;
                    flowSetting: FlWorkDedSettingDto;    
                }
                
            }
            
            export module predset{
                
                export interface BreakDownTimeDayDto {
                    oneDay: number;
                    morning: number;
                    afternoon: number;
                }

                export interface PredetermineTimeDto {
                    addTime: BreakDownTimeDayDto;
                    predTime: BreakDownTimeDayDto;
                }
                
                export interface TimezoneDto{
                    useAtr: boolean;
                    workNo: number;
                    start: number;
                    end: number;
                }
                
                export interface PrescribedTimezoneSettingDto{
                    morningEndTime: number;
                    afternoonStartTime: number;
                    lstTimezone: TimezoneDto[];
                }
                
                export interface PredetemineTimeSettingDto{
                    rangeTimeDay: number;
                    workTimeCode: string;
                    predTime: PredetermineTimeDto;    
                    nightShift: number;
                    prescribedTimezoneSetting: PrescribedTimezoneSettingDto;
                    startDateClock: number;
                    predetermine: boolean;
                }   
                                
            }
            
            export module worktimeset{
                
                export interface WorkTimeDivisionDto{
                    workTimeDailyAtr: number;
                    workTimeMethodSet: number;
                }
                
                export interface WorkTimeDisplayNameDto{
                    workTimeName: string;
                    workTimeAbName: string;
                    workTimeSymbol: string;
                }
                
                export interface WorkTimeSettingDto{
                    worktimeCode: string;
                    workTimeDivision: WorkTimeDivisionDto;    
                    isAbolish: boolean;
                    colorCode: string;
                    workTimeDisplayName: WorkTimeDisplayNameDto;
                    memo: string;
                    note: string;
                }    
                
                export interface SimpleWorkTimeSettingDto {
                    worktimeCode: string;
                    workTimeName: string;
                }
                
                export interface EnumConstantDto {
                    value: number;
                    fieldName: string;
                    localizedName: string;
                }
                
                export interface WorkTimeSettingEnumDto {
                    workTimeDailyAtr: EnumConstantDto[];
                    workTimeMethodSet: EnumConstantDto[];
                }
            
            }
            
            export module command {
                export interface FlexWorkSettingSaveCommand {
                    flexWorkSetting: flexset.FlexWorkSettingDto;
                    predseting: predset.PredetemineTimeSettingDto;
                    worktimeSetting: worktimeset.WorkTimeSettingDto;
                }
            }
            
        }
    }
}
