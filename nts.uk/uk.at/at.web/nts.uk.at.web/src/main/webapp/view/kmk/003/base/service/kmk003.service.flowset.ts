module nts.uk.at.view.kmk003.a {
    import common = nts.uk.at.view.kmk003.a.service.model.common;
    
    export module service {
        export module model {
            export module flowset {

                export interface FlCalcSetDto {
                    calcStartTimeSet: number;
                }

                export interface FlTimeSettingDto {
                    rounding: common.TimeRoundingSettingDto;
                    elapsedTime: number;
                }

                export interface FlOTTimezoneDto {
                    worktimeNo: number;
                    restrictTime: boolean;
                    otFrameNo: number;
                    flowTimeSetting: FlTimeSettingDto;
                    inLegalOTFrameNo: number;
                    settlementOrder: number;
                }

                export interface FlWorkTzSettingDto {
                    workTimeRounding: common.TimeRoundingSettingDto;
                    lstOTTimezone: FlOTTimezoneDto[];
                }

                export interface FlHalfDayWorkTzDto {
                    restTimezone: common.FlowWorkRestTimezoneDto;
                    workTimeZone: FlWorkTzSettingDto;
                }

                export interface FlWorkHdTimeZoneDto {
                    worktimeNo: number;
                    useInLegalBreakRestrictTime: boolean;
                    inLegalBreakFrameNo: number;
                    useOutLegalBreakRestrictTime: boolean;
                    outLegalBreakFrameNo: number;
                    useOutLegalPubHolRestrictTime: boolean;
                    outLegalPubHolFrameNo: number;
                    flowTimeSetting: FlTimeSettingDto;
                }

                export interface FlOffdayWorkTzDto {
                    restTimeZone: common.FlowWorkRestTimezoneDto;
                    lstWorkTimezone: FlWorkHdTimeZoneDto[];
                }

                export interface FlOTSetDto {
                    fixedChangeAtr: number;
                }

                export interface FlStampReflectTzDto {
                    twoTimesWorkReflectBasicTime: number;
                    stampReflectTimezones: common.StampReflectTimezoneDto[];
                }

                export interface FlWorkDedSettingDto {
                    overtimeSetting: FlOTSetDto;
                    calculateSetting: FlCalcSetDto;
                }

                export interface FlWorkSettingDto {
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
        }
    }
}