module nts.uk.pr.view.qmm008.d {
    export module service {

        /**
         *  Service paths
         */
        var paths: any = {
            saveListHealthInsuranceAvgEarn: "ctx/pr/core/insurance/social/healthrate/",
            getListHealthInsuranceAvgEarn: "ctx/pr/core/insurance/social/healthrate/findAllHealthInsuranceAvgearn",
        };

        /**
         *  Save 
         */
        export function save(list: Array<HealthInsuranceAvgEarnDto>): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.saveListHealthInsuranceAvgEarn, list);
        }

        /**
         *  Get ListHealthInsuranceAvgEarn
         */
        export function getListHealthInsuranceAvgEarn(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListHealthInsuranceAvgEarn);
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