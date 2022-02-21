module nts.uk.at.view.kmk003.a {

    export module service {
        export module model {
            export module common {

                export interface HalfDayWorkSetDto {
                    workingTimes: boolean,
                    overTime: boolean,
                    breakTime: boolean
                }

                export interface SettingFlexWorkDto {
                    flexWorkManaging: number;
                }

                export interface SettingWorkMultipleDto {
                    workMultiple: number;
                }

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

                export interface DeductionTimeDto {
                    start: number;
                    end: number;
                }

                export interface DesignatedTimeDto {
                    oneDayTime: number;
                    halfDayTime: number;
                }

                export interface EmTimezoneLateEarlyCommonSetDto {
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

                export interface ExtraordWorkOTFrameSetDto {
                    otFrameNo: number;
                    inLegalWorkFrameNo: number;
                    settlementOrder: number;
                }

                export interface FixedWorkRestSetDto {
                    commonRestSet: CommonRestSettingDto;
                    isPlanActualNotMatchMasterRefer: boolean;
                    fixedRestCalculateMethod: number;
                }

                export interface FlowFixedRestSetDto {
                    calculateMethod: number;
                   // calculateFromSchedule: ScheduleBreakCalculationDto;
                    calculateFromStamp: StampBreakCalculationDto;
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
                    roundingBreakMultipleWork: TimeRoundingSettingDto;
                }

                export interface FlowWorkRestSettingDto {
                    commonRestSetting: CommonRestSettingDto;
                    flowRestSetting: FlowWorkRestSettingDetailDto;
                }

                export interface TimezoneOfFixedRestTimeSetDto {
                    timezones: DeductionTimeDto[];
                }

                export interface FlowWorkRestTimezoneDto {
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
                    ottimezone: GoOutTypeRoundingSetDto;
                }

                export interface GraceTimeSettingDto {
                    includeWorkingHour: boolean;
                    graceTime: number;
                }

                export interface HDWorkTimeSheetSettingDto {
                    workTimeNo: number;
                    timezone: TimeZoneRoundingDto;
                    isLegalHolidayConstraintTime: boolean;
                    inLegalBreakFrameNo: number;
                    isNonStatutoryDayoffConstraintTime: boolean;
                    outLegalBreakFrameNo: number;
                    isNonStatutoryHolidayConstraintTime: boolean;
                    outLegalPubHDFrameNo: number;
                }

                export interface HolidayFramsetDto {
                    inLegalBreakoutFrameNo: number;
                    outLegalBreakoutFrameNo: number;
                    outLegalPubHolFrameNo: number;
                }

                export interface InstantRoundingDto {
                    fontRearSection: number;
                    roundingTimeUnit: number;
                }

                export interface IntervalTimeDto {
                    intervalTime: number;
                    rounding: TimeRoundingSettingDto;
                }

                export interface OtherEmTimezoneLateEarlySetDto {
                    delTimeRoundingSet: TimeRoundingSettingDto;
                    stampExactlyTimeIsLateEarly: boolean;
                    graceTimeSet: GraceTimeSettingDto;
                    recordTimeRoundingSet: TimeRoundingSettingDto;
                    lateEarlyAtr: number;
                }

                export interface OverTimeOfTimeZoneSetDto {
                    workTimezoneNo: number;
                    restraintTimeUse: boolean;
                    earlyOTUse: boolean;
                    timezone: TimeZoneRoundingDto;
                    otFrameNo: number;
                    legalOTframeNo: number;
                    settlementOrder: number;
                }

                export interface PrioritySettingDto {
                    priorityAtr: number;
                    stampAtr: number;
                }

                export interface RoundingSetDto {
                    roundingSet: InstantRoundingDto;
                    section: number;
                }

                export interface StampReflectTimezoneDto {
                    workNo: number;
                    classification: number;
                    endTime: number;
                    startTime: number;
                }

                export interface SubHolTransferSetDto {
                    certainTime: number;
                    useDivision: boolean;
                    designatedTime: DesignatedTimeDto;
                    subHolTransferSetAtr: number;
                }

                export interface TotalRoundingSetDto {
                    setSameFrameRounding: number;
                    frameStraddRoundingSet: number;
                }

                export interface WorkTimezoneOtherSubHolTimeSetDto {
                    subHolTimeSet: SubHolTransferSetDto;
                    workTimeCode: string;
                    originAtr: number;
                }

                export interface WorkTimezoneMedicalSetDto {
                    roundingSet: TimeRoundingSettingDto;
                    workSystemAtr: number;
                    applicationTime: number;
                }

                export interface WorkTimezoneGoOutSetDto {
                    roundingMethod: number;
                    diffTimezoneSetting: GoOutTimezoneRoundingSetDto;
                }

                export interface WorkTimezoneStampSetDto {
                    roundingTime: RoundingTimeDto;
                    prioritySets: PrioritySettingDto[];
                }

                export interface RoundingTimeDto {
                    attendanceMinuteLaterCalculate: number;
                    leaveWorkMinuteAgoCalculate: number;
                    roundingSets: RoundingSetDto[];
                }

                export interface WorkTimezoneLateNightTimeSetDto {
                    roundingSetting: TimeRoundingSettingDto;
                }

                export interface WorkTimezoneShortTimeWorkSetDto {
                    nursTimezoneWorkUse: boolean;
                    employmentTimeDeduct: boolean;
                    childCareWorkUse: boolean;
                }

                export interface WorkTimezoneExtraordTimeSetDto {
                    holidayFrameSet: HolidayFramsetDto;
                    timeRoundingSet: TimeRoundingSettingDto;
                    otFrameSet: ExtraordWorkOTFrameSetDto;
                    calculateMethod: number;
                }

                export interface WorkTimezoneLateEarlySetDto {
                    commonSet: EmTimezoneLateEarlyCommonSetDto;
                    otherClassSets: OtherEmTimezoneLateEarlySetDto[];
                }

                export interface WorkTimezoneCommonSetDto {
                    zeroHStraddCalculateSet: boolean;
                    subHolTimeSet: WorkTimezoneOtherSubHolTimeSetDto[];
                    raisingSalarySet: string;
                    medicalSet: WorkTimezoneMedicalSetDto[];
                    goOutSet: WorkTimezoneGoOutSetDto;
                    stampSet: WorkTimezoneStampSetDto;
                    lateNightTimeSet: WorkTimezoneLateNightTimeSetDto;
                    shortTimeWorkSet: WorkTimezoneShortTimeWorkSetDto;
                    extraordTimeSet: WorkTimezoneExtraordTimeSetDto;
                    lateEarlySet: WorkTimezoneLateEarlySetDto;
                    holidayCalculation: HolidayCalculationDto;
                }

                export interface WorkTimeCommonDto {
                    predseting: predset.PredetemineTimeSettingDto;
                    worktimeSetting: worktimeset.WorkTimeSettingDto;
                    displayMode: worktimeset.WorkTimeDisplayModeDto;
                    manageEntryExit: worktimeset.ManageEntryExitDto;
                }

                export interface WorkTimeSettingInfoDto extends WorkTimeCommonDto {
                    flexWorkSetting: flexset.FlexWorkSettingDto;
                    fixedWorkSetting: fixedset.FixedWorkSettingDto;
                    flowWorkSetting: flowset.FlWorkSettingDto;
                    diffTimeWorkSetting: difftimeset.DiffTimeWorkSettingDto;
                }

                export interface FixedWorkTimezoneSetDto {
                    lstWorkingTimezone: EmTimeZoneSetDto[];
                    lstOTTimezone: OverTimeOfTimeZoneSetDto[];
                }

                export interface HolidayCalculationDto {
                    isCalculate: number;
                }

                export interface OverTimeCalcNoBreakDto {
                    calcMethod: number;
                    inLawOT: number;
                    notInLawOT: number;
                }

                export interface ExceededPredAddVacationCalcDto {
                    calcMethod: number;
                    otFrameNo: number;
                }

                export interface FixedWorkCalcSettingDto {
                    exceededPredAddVacationCalc: ExceededPredAddVacationCalcDto;
                    overTimeCalcNoBreak: OverTimeCalcNoBreakDto;
                }

                export interface ScheduleBreakCalculationDto {
                    isReferRestTime: boolean;
                    isCalcFromSchedule: boolean;
                }
                
                export interface StampBreakCalculationDto {
                    usePrivateGoOutRest: boolean;
                    useAssoGoOutRest: boolean;
                }
                
                // Common Enum
                export enum LateEarlyAtr {
                    LATE,
                    EARLY
                }

                export enum SubHolidayOriginAtr {
                    FROM_OVER_TIME,
                    WORK_DAY_OFF_TIME
                }

                export enum SubHolTransferSetAtr {
                    SPECIFIED_TIME_SUB_HOL,
                    CERTAIN_TIME_EXC_SUB_HOL
                }

                export enum WorkSystemAtr {
                    DAY_SHIFT,
                    NIGHT_SHIFT
                }
                
                export enum StampPiorityAtr {
                    GOING_WORK,
                    LEAVE_WORK,
                    ENTERING,
                    EXIT,
                    PCLOGIN,
                    PC_LOGOUT
                }
                
                export enum Superiority {
                    ATTENDANCE,
                    OFFICE_WORK,
                    GO_OUT,
                    TURN_BACK
                }
            }
        }
    }
}