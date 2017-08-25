module nts.uk.at.view.ksm001.f {
    export module service {
        var paths = {
            findCommonGuidelineSetting: "ctx/at/schedule/shift/estimate/guideline/find",
            save: "ctx/at/schedule/shift/pattern/estimate/usagesetting/save"
        }

        /**
         * call service find setting
         */
        export function findCommonGuidelineSetting(): JQueryPromise<model.CommonGuidelineSettingDto> {
            return nts.uk.request.ajax('at', paths.findCommonGuidelineSetting);
        }

        export function saveUsageSetting(command: model.UsageSettingDto): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.save, command);
        }
        
        export function getUseClsEnum(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(paths.useclassification);
        }

        export module model {
            
            
            export interface EstimatedAlarmColorDto {
                guidelineCondition: number;
                color: string;
            }
            
            export interface ReferenceConditionDto {
                yearlyDisplayCondition: number;
                monthlyDisplayCondition: number;
                alarmCheckCondition: number;
            }
            
            export interface CommonGuidelineSettingDto {
                /** The alarm colors. */
                alarmColors: EstimatedAlarmColorDto[];

                /** The estimate time. */
                estimateTime: ReferenceConditionDto;

                /** The estimate price. */
                estimatePrice: ReferenceConditionDto;

                /** The estimate number of days. */
                estimateNumberOfDays: ReferenceConditionDto;
            }
            
            export class UsageSettingDto {
                employmentSetting: number;
                personalSetting: number;
            
                constructor(employmentSetting: number, personalSetting: number) {
                    this.employmentSetting = employmentSetting;
                    this.personalSetting = personalSetting;
                }
            }
            
            export class Enum {
                value: number;
                fieldName: string;
                localizedName: string;
    
                constructor(value: number, fieldName: string, localizedName: string) {
                    this.value = value;
                    this.fieldName = fieldName;
                    this.localizedName = localizedName;
                }
            }
        }
    }
}