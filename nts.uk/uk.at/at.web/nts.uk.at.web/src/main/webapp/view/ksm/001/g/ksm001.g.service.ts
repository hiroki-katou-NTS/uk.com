module nts.uk.at.view.ksm001.g {
    
    import UsageSettingDto = nts.uk.at.view.ksm001.a.service.model.UsageSettingDto;
    
    export module service {
        var paths = {
            findCommonGuidelineSetting: "ctx/at/schedule/shift/estimate/guideline/find",
            saveCommonGuidelineSetting: "ctx/at/schedule/shift/estimate/guideline/save"
        }

        /**
         * call service find setting
         */
        export function findCommonGuidelineSetting(): JQueryPromise<model.CommonGuidelineSettingDto> {
            return nts.uk.request.ajax('at', paths.findCommonGuidelineSetting);
        }

        export function saveCommonGuidelineSetting(command: model.CommonGuidelineSettingDto): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.saveCommonGuidelineSetting, command);
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
            
            
            export interface EstimatedConditionDto {
                code: number;
                name: string;
            }
        }
    }
}