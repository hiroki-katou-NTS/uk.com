module nts.uk.pr.view.qmm008.e {
    export module service {

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
