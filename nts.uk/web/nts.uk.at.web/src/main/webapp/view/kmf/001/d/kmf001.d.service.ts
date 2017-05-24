module nts.uk.pr.view.kmf001 {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            findRetentionYearlyByCompany: 'ctx/pr/core/vacation/setting/retentionyearly/find',
            saveRetentionYearly: 'ctx/pr/core/vacation/setting/retentionyearly/save'
        };

        /**
         * Normal service.
         */
        export class Service {
            constructor() {
            }
        }
        
        export function findRetentionYearly(): JQueryPromise<model.RetentionYearlyModel> {
            return nts.uk.request.ajax(paths.findRetentionYearlyByCompany);
        }
        
        export function saveRetentionYearly(retentionYearlyModel: model.RetentionYearlyModel):  JQueryPromise<void> {
            var data = {retentionYearly: retentionYearlyModel};
            return nts.uk.request.ajax(paths.saveRetentionYearly, data);
        }

        /**
        * Model namespace.
        */
        export module model {
            export class RetentionYearlyModel {
                retentionYearsAmount: number;
                maxDaysCumulation: number;
            }
        }
    }
}
