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
        export function updateHealthInsuranceAvgearn(list: Array<HealthInsuranceAvgEarnDto>): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = { listHealthInsuranceAvgearn: list };
            nts.uk.request.ajax(paths.updateHealthInsuranceAvgearn, data).done(() =>
                dfd.resolve());
            return dfd.promise();
        }

        /**
         *  Find list HealthInsuranceAvgEarn by historyId
         */
        export function findHealthInsuranceAvgEarn(historyId: string): JQueryPromise<Array<model.HealthInsuranceAvgEarnDto>> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.findHealthInsuranceAvgEarn + '/' + historyId)
                .done(res => {
                    dfd.resolve(res);
                })
                .fail(res => {
                    dfd.reject(res);
                })
            return dfd.promise();;
        }

        /**
        * Model namespace.
        */
        export module model {
            export class HealthInsuranceAvgEarnValue {
                general: number;
                nursing: number;
                basic: number;
                specific: number;
            }

            export class HealthInsuranceAvgEarnDto {
                historyId: string;
                levelCode: number;
                companyAvg: HealthInsuranceAvgEarnValue;
                personalAvg: HealthInsuranceAvgEarnValue;
                constructor(historyId: string, levelCode: number, companyAvg: HealthInsuranceAvgEarnValue, personalAvg: HealthInsuranceAvgEarnValue) {
                    this.historyId = historyId;
                    this.levelCode = levelCode;
                    this.companyAvg = companyAvg;
                    this.personalAvg = personalAvg;
                };
            }

        }
    }
}