module nts.uk.pr.view.qmm008.h {
    export module service {

        /**
         *  Service paths
         */
        var paths: any = {
            updateHealthInsuranceAvgearn: "ctx/pr/core/insurance/social/healthavgearn/update",
            findHealthInsuranceAvgEarn: "ctx/pr/core/insurance/social/healthavgearn/find",
            findHealthInsuranceRate: "ctx/pr/core/insurance/social/healthrate/find",
        };

        /**
         *  Save List Health Insurance Average Earn
         */
        export function updateHealthInsuranceAvgearn(list: Array<HealthInsuranceAvgEarnDto>): JQueryPromise<any> {
            var data = {listHealthInsuranceAvgearn: list};
            return nts.uk.request.ajax(paths.updateHealthInsuranceAvgearn, data);
        }

        /**
         *  Find HealthInsuranceRate by historyId
         */
        export function findHealthInsuranceRate(id: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.findHealthInsuranceRate + '/' + id)
                .done(res => {
                    dfd.resolve(res);
                })
                .fail(res => {
                    dfd.reject(res);
                })
            return dfd.promise();;
        }

        /**
         *  Find HealthInsuranceAvgEarn by historyId
         */
        export function findHealthInsuranceAvgEarn(id: string): JQueryPromise<any> {
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

            export class HealthInsuranceRate {
                companyCode: string;
                officeCode: string;
                startMonth: string;
                endMonth: string;
                autoCalculate: boolean;
                maxAmount: number;
                rateItems: Array<InsuranceRateItem>;
                roundingMethods: Array<HealthInsuranceRounding>;
            }

            export class InsuranceRateItem {
                companyRate: number;
                personalRate: number;
                PaymentType: string;
                HealthInsuranceType: string;
            }

            export class HealthInsuranceRounding {
                PaymentType: string;
                companyRoundAtr: string;
                personalRoundAtr: string;
            }

        }
    }
}