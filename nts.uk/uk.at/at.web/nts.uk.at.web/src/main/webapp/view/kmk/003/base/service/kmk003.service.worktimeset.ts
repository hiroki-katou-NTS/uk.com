module nts.uk.at.view.kmk003.a {
    export module service {
        export module model {

            export module worktimeset {

                export interface WorkTimeSettingCondition {
                    workTimeDailyAtr: Object;
                    workTimeMethodSet: Object;
                    isAbolish: Object;
                }

                export interface WorkTimeDivisionDto {
                    workTimeDailyAtr: number;
                    workTimeMethodSet: number;
                }

                export interface WorkTimeDisplayNameDto {
                    workTimeName: string;
                    workTimeAbName: string;
                    workTimeSymbol: string;
                }

                export interface WorkTimeSettingDto {
                    worktimeCode: string;
                    workTimeDivision: WorkTimeDivisionDto;
                    isAbolish: boolean;
                    colorCode: string;
                    workTimeDisplayName: WorkTimeDisplayNameDto;
                    memo: string;
                    note: string;
                }

                export interface WorkTimeDisplayModeDto {
                    worktimeCode: string;
                    displayMode: number;
                }
                
                export interface SimpleWorkTimeSettingDto {
                    worktimeCode: string;
                    workTimeName: string;
                    isAbolish: boolean;
                }

                export interface EnumConstantDto {
                    value: number;
                    fieldName: string;
                    localizedName: string;
                }
                
                export interface ManageEntryExitDto {
                    useClassification: number;
                }

                export interface WorkTimeSettingEnumDto {
                    workTimeDailyAtr: EnumConstantDto[];
                    workTimeMethodSet: EnumConstantDto[];
                    roundingBreakTimezone: EnumConstantDto[];
                    roundingBreakTime: EnumConstantDto[];
                    roundingTime: EnumConstantDto[];
                    rounding: EnumConstantDto[];
                    roundingSimple: EnumConstantDto[];
                    lstLateEarlyAtr: EnumConstantDto[];
                    workSystemAtr: EnumConstantDto[];
                    applyAtr: EnumConstantDto[];
                    lstFixedChangeAtr: EnumConstantDto[];
                    lstAmPmAtr: EnumConstantDto[];
                    notUseAtr: EnumConstantDto[];
                    calcMethodNoBreak: EnumConstantDto[];
                    calcMethodExceededPredAddVacation: EnumConstantDto[];
                    roundingTimeUnit: EnumConstantDto[];
                }


                
                // Worktime Setting Enum
                export enum WorkTimeDailyAtr {
                    REGULAR_WORK,
                    FLEX_WORK
                }
                
                export enum WorkTimeMethodSet {
                    FIXED_WORK,
                    DIFFTIME_WORK,
                    FLOW_WORK
                }
            }
        }
    }
}