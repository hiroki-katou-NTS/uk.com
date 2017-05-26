module nts.uk.pr.view.kmf001.d {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            findRetentionYearlyByCompany: 'ctx/at/core/vacation/setting/retentionyearly/find',
            saveRetentionYearly: 'ctx/at/core/vacation/setting/retentionyearly/save'
        };

        /**
         * Normal service.
         */
//        export class Service {
//            constructor() {
//            }
//        }
        
        export function findRetentionYearly(): JQueryPromise<model.RetentionYearlyFindDto> {
            return nts.uk.request.ajax(paths.findRetentionYearlyByCompany);
        }
        
        export function saveRetentionYearly(retentionYearly: model.RetentionYearlyDto):  JQueryPromise<void> {
            var data = {retentionYearly: retentionYearly};
            return nts.uk.request.ajax(paths.saveRetentionYearly, data);
        }

        /**
        * Model namespace.
        */
        export module model {
            
            export class UpperLimitSettingFindDto{
                retentionYearsAmount: number;
                maxDaysCumulation: number; 
            }
            
            export class RetentionYearlyFindDto {
                upperLimitSetting: UpperLimitSettingFindDto;
            }
            
            export class UpperLimitSettingDto {
                retentionYearsAmount: number;
                maxDaysCumulation: number;
            }
            
            export class RetentionYearlyDto {
                upperLimitSettingDto: UpperLimitSettingDto;
            }
        }
    }
}
