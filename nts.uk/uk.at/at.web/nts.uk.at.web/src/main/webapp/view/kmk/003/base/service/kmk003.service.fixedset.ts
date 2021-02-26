module nts.uk.at.view.kmk003.a {
    import common = nts.uk.at.view.kmk003.a.service.model.common;
    import HalfDayWorkSetDto = nts.uk.at.view.kmk003.a.service.model.common.HalfDayWorkSetDto;

    export module service {
        export module model {
            export module fixedset {

                export interface FixOffdayWorkTimezoneDto {
                    restTimezone: common.TimezoneOfFixedRestTimeSetDto;
                    lstWorkTimezone: common.HDWorkTimeSheetSettingDto[];
                }
                
                export interface FixHalfDayWorkTimezoneDto {
                    restTimezone: common.TimezoneOfFixedRestTimeSetDto;
                    workTimezone: FixedWorkTimezoneSetDto;
                    dayAtr: number;
                }
                
                export interface FixedWorkTimezoneSetDto {
                    lstWorkingTimezone: common.EmTimeZoneSetDto[];
                    lstOTTimezone: common.OverTimeOfTimeZoneSetDto[];
                }
                
                export interface FixedWorkSettingDto {
                    workTimeCode: string;
                    offdayWorkTimezone: FixOffdayWorkTimezoneDto;
                    commonSetting: common.WorkTimezoneCommonSetDto;
                    halfDayWorkSet: HalfDayWorkSetDto;
                    commonRestSet: common.CommonRestSettingDto;
                    // fixedWorkRestSetting: common.FixedWorkRestSetDto;
                    lstHalfDayWorkTimezone: FixHalfDayWorkTimezoneDto[];
                    lstStampReflectTimezone: common.StampReflectTimezoneDto[];
                    legalOTSetting: number;
                    calculationSetting: common.FixedWorkCalcSettingDto;
                }
                
            }
        }
    }
}