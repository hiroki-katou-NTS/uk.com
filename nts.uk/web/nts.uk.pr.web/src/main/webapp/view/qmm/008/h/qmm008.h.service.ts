module nts.uk.pr.view.qmm008.h {
    export module service {

        /**
         *  Service paths
         */
        var paths: any = {
            updateHealthInsuranceAvgearn: "ctx/pr/core/insurance/social/healthavgearn/update",
            findHealthInsuranceAvgEarn: "ctx/pr/core/insurance/social/healthavgearn/find",
        };

        /**
         *  Save List Health Insurance Average Earn
         */
        export function updateHealthInsuranceAvgearn(list: Array<model.HealthInsuranceAvgEarnDto>, officeCode: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = { listHealthInsuranceAvgearn: list, officeCode: officeCode };
            nts.uk.request.ajax(paths.updateHealthInsuranceAvgearn, data).done(() =>
                dfd.resolve());
            return dfd.promise();
        }

        /**
         *  Find list HealthInsuranceAvgEarn by historyId
         */
        export function findHealthInsuranceAvgEarn(historyId: string): JQueryPromise<Array<model.HealthInsuranceAvgEarnDto>> {
            var dfd = $.Deferred<Array<model.HealthInsuranceAvgEarnDto>>();
            nts.uk.request.ajax(paths.findHealthInsuranceAvgEarn + '/' + historyId)
                .done(function(res: Array<model.HealthInsuranceAvgEarnDto>) {
                    dfd.resolve(res);
                })
                .fail(function(res: Array<model.HealthInsuranceAvgEarnDto>) {
                    dfd.reject(res);
                });
            return dfd.promise();
        }

        /**
        * Model namespace.
        */
        export module model {

            export interface HealthInsuranceAvgEarnValue {
                healthGeneralMny: number;
                healthNursingMny: number;
                healthBasicMny: number;
                healthSpecificMny: number;
            }

            export interface HealthInsuranceAvgEarnDto {
                historyId: string;
                levelCode: number;
                companyAvg: HealthInsuranceAvgEarnValue;
                personalAvg: HealthInsuranceAvgEarnValue;
            }

        }
    }
}