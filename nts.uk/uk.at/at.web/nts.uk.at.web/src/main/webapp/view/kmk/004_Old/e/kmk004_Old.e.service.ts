module nts.uk.at.view.kmk004_Old.e {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findUsageUnitSetting: 'ctx/at/shared/employment/statutory/worktime/usage/unit/setting/find',
            saveUsageUnitSetting: 'ctx/at/shared/employment/statutory/worktime/usage/unit/setting/save'
        };

        export function findUsageUnitSetting(): JQueryPromise<model.UsageUnitSettingDto> {
            return nts.uk.request.ajax(servicePath.findUsageUnitSetting);
        }
        
        export function saveUsageUnitSetting(dto: model.UsageUnitSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.saveUsageUnitSetting, dto);
        }


        export module model {

            export class UsageUnitSettingDto {
                
                /** The employee. */
                employee: boolean;

                /** The work place. */
                workPlace: boolean;
                
                /** The employment. */
                employment: boolean;
            }

        }
    }
}
