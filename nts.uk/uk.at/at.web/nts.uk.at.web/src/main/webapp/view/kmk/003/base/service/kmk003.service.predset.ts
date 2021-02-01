module nts.uk.at.view.kmk003.a {
    
    export module service {
        export module model {
            export module predset {

                export interface BreakDownTimeDayDto {
                    oneDay: number;
                    morning: number;
                    afternoon: number;
                }

                export interface PredetermineTimeDto {
                    addTime: BreakDownTimeDayDto;
                    predTime: BreakDownTimeDayDto;
                }

                export interface TimezoneDto {
                    useAtr: boolean;
                    workNo: number;
                    start: number;
                    end: number;
                }

                export interface PrescribedTimezoneSettingDto {
                    morningEndTime: number;
                    afternoonStartTime: number;
                    lstTimezone: TimezoneDto[];
                }

                export interface PredetemineTimeSettingDto {
                    rangeTimeDay: number;
                    workTimeCode: string;
                    predTime: PredetermineTimeDto;

                    prescribedTimezoneSetting: PrescribedTimezoneSettingDto;
                    startDateClock: number;
                    predetermine: boolean;
                }

            }
        }
    }
}