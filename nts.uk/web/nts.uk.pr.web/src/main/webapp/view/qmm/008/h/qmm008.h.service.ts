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
        export function updateHealthInsuranceAvgearn(list: model.ListHealthInsuranceAvgEarnDto, officeCode: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = { listHealthInsuranceAvgearnDto: list.listHealthInsuranceAvgearnDto, 
                historyId: list.historyId, 
                officeCode: officeCode };
            nts.uk.request.ajax(paths.updateHealthInsuranceAvgearn, data).done(() =>
                dfd.resolve());
            return dfd.promise();
        }

        /**
         *  Find list HealthInsuranceAvgEarn by historyId
         */
        export function findHealthInsuranceAvgEarn(historyId: string): JQueryPromise<model.ListHealthInsuranceAvgEarnDto> {
            var dfd = $.Deferred<model.ListHealthInsuranceAvgEarnDto>();
            nts.uk.request.ajax(paths.findHealthInsuranceAvgEarn + '/' + historyId)
                .done(function(res: model.ListHealthInsuranceAvgEarnDto) {
                    dfd.resolve(res);
                })
                .fail(function(res: any) {
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
                levelCode: number;
                companyAvg: HealthInsuranceAvgEarnValue;
                personalAvg: HealthInsuranceAvgEarnValue;
            }

            export interface ListHealthInsuranceAvgEarnDto {
                historyId: string;
                listHealthInsuranceAvgearnDto: HealthInsuranceAvgEarnDto[];
            }

        }
    }
}