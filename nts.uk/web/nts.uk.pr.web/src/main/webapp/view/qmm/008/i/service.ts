module nts.uk.pr.view.qmm008.i {
    export module service {

        /**
         *  Service paths
         */
        var paths: any = {
            updatePensionAvgearn: "ctx/pr/core/insurance/social/pensionavgearn/update",
            findPensionAvgearn: "ctx/pr/core/insurance/social/pensionavgearn/find"
        };

        /**
         *  Save List Health Insurance Average Earn
         */
        export function updatePensionAvgearn(list: Array<HealthInsuranceAvgEarnDto>): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updatePensionAvgearn, list);
        }

        /**
        *  Find PensionAvgearn by historyId
        */
        export function findPensionAvgearn(id: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.findHealthInsuranceAvgEarn + '/' + id)
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
            export class PensionAvgearnValue {
                maleAmount: number;
                femaleAmount: number;
                unknownAmount: number;
            }
            export class HealthInsuranceAvgEarnDto {
                historyId: string;
                childContributionAmount: number;
                companyFund: PensionAvgearnValue;
                companyFundExemption: PensionAvgearnValue;
                companyPension: PensionAvgearnValue;
                personalFund: PensionAvgearnValue;
                personalPension: PensionAvgearnValue;
            }
        }
    }

}
