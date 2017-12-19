module nts.uk.at.view.kmk003.a {
    export module service {
        export module model {

            export module worktimeset {

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
                    roundingTime: EnumConstantDto[];
                    rounding: EnumConstantDto[];
                }

            }
        }
    }
}