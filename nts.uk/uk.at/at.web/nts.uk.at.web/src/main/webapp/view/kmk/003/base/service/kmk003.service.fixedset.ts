module nts.uk.at.view.kmk003.a {
    import common = nts.uk.at.view.kmk003.a.service.model.common;
    
    export module service {
        export module model {
            export module fixedset {
                
                export interface FixOffdayWorkTimezoneDto {
                    restTimezone: FixRestTimezoneSetDto;
                    lstWorkTimezone: common.HDWorkTimeSheetSettingDto[];
                }
                
                export interface FixHalfDayWorkTimezoneDto {
                    restTimezone: FixRestTimezoneSetDto;
                    workTimezone: FixedWorkTimezoneSetDto;
                    dayAtr: number;
                }
                
                export interface FixRestTimezoneSetDto {
                    lstTimezone: common.DeductionTimeDto[];
                }
                
                export interface FixedWorkTimezoneSetDto {
                    lstWorkingTimezone: common.EmTimeZoneSetDto[];
                    lstOTTimezone: common.OverTimeOfTimeZoneSetDto[];
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
                
                export interface FixedWorkSettingDto {
                    workTimeCode: string;
                    offdayWorkTimezone: FixOffdayWorkTimezoneDto;
                    commonSetting: common.WorkTimezoneCommonSetDto;
                    useHalfDayShift: boolean;
                    fixedWorkRestSetting: common.FixedWorkRestSetDto;
                    lstHalfDayWorkTimezone: FixHalfDayWorkTimezoneDto[];
                    lstStampReflectTimezone: common.StampReflectTimezoneDto[];
                    legalOTSetting: number;
                    fixedWorkCalcSetting: FixedWorkCalcSettingDto;
                }
                
            }
        }
    }
}