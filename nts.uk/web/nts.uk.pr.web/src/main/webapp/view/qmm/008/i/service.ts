module nts.uk.pr.view.qmm008.i {
    export module service {

        /**
         *  Service paths
         */
        var paths: any = {
            saveListHealthInsuranceAvgEarn: "ctx/pr/core/insurance/social/healthrate",
            getListHealthInsuranceAvgEarn: "ctx/pr/core/insurance/social/healthrate",
        };

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
