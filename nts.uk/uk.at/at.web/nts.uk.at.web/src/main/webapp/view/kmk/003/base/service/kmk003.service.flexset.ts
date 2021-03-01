module nts.uk.at.view.kmk003.a {
    export module service {
        export module model {
            export module flexset {

                export interface TimeSheetDto {
                    startTime: number;
                    endTime: number;
                }


                export interface CoreTimeSettingDto {
                    coreTimeSheet: TimeSheetDto;
                    timesheet: number;
                    minWorkTime: number;
                    goOutCalc: OutingCalcDto
                }

                export interface OutingCalcDto {
                    removeFromWorkTime: number;
                    especialCalc: number;
                }

                export interface FlexCalcSettingDto {
                    removeFromWorkTime: number;
                    calculateSharing: number;
                }

                export interface FlexHalfDayWorkTimeDto {
                    restTimezone: common.FlowWorkRestTimezoneDto;
                    workTimezone: common.FixedWorkTimezoneSetDto;
                    ampmAtr: number;
                }

                export interface FlexOffdayWorkTimeDto {
                    lstWorkTimezone: common.HDWorkTimeSheetSettingDto[];
                    restTimezone: common.FlowWorkRestTimezoneDto;
                }

                export interface FlexWorkSettingDto {
                    workTimeCode: string;
                    coreTimeSetting: CoreTimeSettingDto;
                    restSetting: common.FlowWorkRestSettingDto;
                    offdayWorkTime: FlexOffdayWorkTimeDto;
                    commonSetting: common.WorkTimezoneCommonSetDto;
                    useHalfDayShift: common.HalfDayWorkSetDto;
                    lstHalfDayWorkTimezone: FlexHalfDayWorkTimeDto[];
                    lstStampReflectTimezone: common.StampReflectTimezoneDto[];
                    calculateSetting: FlexCalcSettingDto;
                }

            }
        }
    }
}