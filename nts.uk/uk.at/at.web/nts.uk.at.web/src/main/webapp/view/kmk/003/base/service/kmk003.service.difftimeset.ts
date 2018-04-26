module nts.uk.at.view.kmk003.a {

    import HDWorkTimeSheetSettingDto = nts.uk.at.view.kmk003.a.service.model.common.HDWorkTimeSheetSettingDto;
    import DeductionTimeDto = nts.uk.at.view.kmk003.a.service.model.common.DeductionTimeDto;
    import OverTimeOfTimeZoneSetDto = nts.uk.at.view.kmk003.a.service.model.common.OverTimeOfTimeZoneSetDto;
    import EmTimeZoneSetDto = nts.uk.at.view.kmk003.a.service.model.common.EmTimeZoneSetDto;
    import StampReflectTimezoneDto = nts.uk.at.view.kmk003.a.service.model.common.StampReflectTimezoneDto;
    import InstantRoundingDto = nts.uk.at.view.kmk003.a.service.model.common.InstantRoundingDto;
    import FixedWorkRestSetDto = nts.uk.at.view.kmk003.a.service.model.common.FixedWorkRestSetDto;
    import WorkTimezoneCommonSetDto = nts.uk.at.view.kmk003.a.service.model.common.WorkTimezoneCommonSetDto;

    export module service {
        export module model {
            export module difftimeset {

                export interface DayOffTimezoneSettingDto extends HDWorkTimeSheetSettingDto {
                    isUpdateStartTime: boolean;
                }

                export interface DiffTimeDeductTimezoneDto extends DeductionTimeDto {
                    updateStartTime: boolean;
                }

                export interface DiffTimeRestTimezoneDto {
                    restTimezones: DiffTimeDeductTimezoneDto[];
                }

                export interface DiffTimeDayOffWorkTimezoneDto {
                    restTimezone: DiffTimeRestTimezoneDto;
                    workTimezones: DayOffTimezoneSettingDto[];
                }

                export interface DiffTimeOTTimezoneSetDto extends OverTimeOfTimeZoneSetDto {
                    updateStartTime: boolean;
                }

                export interface DiffTimezoneSettingDto {
                    employmentTimezones: EmTimeZoneSetDto[];
                    lstOtTimezone: DiffTimeOTTimezoneSetDto[];

                }

                export interface DiffTimeHalfDayWorkTimezoneDto {
                    restTimezone: DiffTimeRestTimezoneDto;
                    workTimezone: DiffTimezoneSettingDto;
                    amPmAtr: number;
                }

                export interface DiffTimeWorkStampReflectTimezoneDto {
                    stampReflectTimezone: StampReflectTimezoneDto[];
                    updateStartTime: boolean;
                }

                export interface EmTimezoneChangeExtentDto {
                    aheadChange: number;
                    unit: InstantRoundingDto;
                    behindChange: number;
                }
                
                export interface DiffTimeWorkSettingDto {
                    workTimeCode: string;
                    restSet: FixedWorkRestSetDto;
                    dayoffWorkTimezone: DiffTimeDayOffWorkTimezoneDto;
                    commonSet: WorkTimezoneCommonSetDto;
                    useHalfDayShift: boolean;
                    changeExtent: EmTimezoneChangeExtentDto;
                    halfDayWorkTimezones: DiffTimeHalfDayWorkTimezoneDto[];
                    stampReflectTimezone: DiffTimeWorkStampReflectTimezoneDto;
                    overtimeSetting: number;
                    calculationSetting: common.FixedWorkCalcSettingDto;
                }

            }
        }
    }
}