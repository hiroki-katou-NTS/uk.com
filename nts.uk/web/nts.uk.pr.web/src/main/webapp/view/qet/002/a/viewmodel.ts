module qet002.a.viewmodel {
    export class ScreenModel {
        targetYear: KnockoutObservable<number>;
        isLowerLimit: KnockoutObservable<boolean>;
        lowerLimitValue: KnockoutObservable<number>;
        isUpperLimit: KnockoutObservable<boolean>;
        upperLimitValue: KnockoutObservable<number>;

        constructor() {
            this.targetYear = ko.observable(2016);
            this.isLowerLimit = ko.observable(true);
            this.isUpperLimit = ko.observable(true);
            this.lowerLimitValue = ko.observable(null);
            this.upperLimitValue = ko.observable(null);
            //this.printData();

        }

        /**
         * Start screen.
         */
        public start(): JQueryPromise<void> {
            let self = this;

            var dfd = $.Deferred<void>();
//            let query: any;
//            qet002.a.service.printService().done(function() {
//                // self.printData();
//                //console.log("jejejejjejejeje");
//            });
            dfd.resolve();
            return dfd.promise();
        }


        /**
         * Print Report
         */
       public printData(){
            //console.log("hehe");
            let query: string;
            var hasError = false;
            if (this.targetYear() == null) {
                hasError = true;
                $('#target-year-input').ntsError('set', '未入力エラー');
            }
            if (this.isLowerLimit() == true) {
                if (this.lowerLimitValue() == null) {
                    hasError = true;
                    $('#lower-limit-input').ntsError('set', '未入力エラー');
                }
            }
            if (this.isUpperLimit() == true) {
                if (this.upperLimitValue() == null) {
                    hasError = true;
                    $('#upper-limit-input').ntsError('set', '未入力エラー');
                }
            }
            if ((this.isLowerLimit() == true) && (this.isUpperLimit() == true)) {
                if (this.lowerLimitValue() > this.upperLimitValue()) {
                    hasError = true;
                    $('#lower-limit-input').ntsError('set', '未入力エラー');
                }
            }
            if (hasError) {
                return;
            }
           
           //Print Report
           service.printService(query).done(function() {}).done(function(data: any) {                
            }).fail(function(res) {
                nts.uk.ui.dialog.alert(res.message);
            })

        }
    }
    export class AccumulatedPaymentResultViewModel {
        empDesignation: string;
        empCode: string;
        empName: string;
        taxAmount: number;
        socialInsuranceAmount: number;
        widthHoldingTaxAmount: number;
        enrollmentStatus: string;
        directionalStatus: string;
        amountAfterTaxDeduction: number;

        constructor(empDesignation: string, empCode: string, empName: string, taxAmount: number, socialInsuranceAmount: number,
            widthHoldingTaxAmount: number, enrollmentStatus: string, directionalStatus: string, amountAfterTaxDeduction: number) {
            var self = this;
            self.empDesignation = empDesignation;
            self.empCode = empCode;
            self.empName = empName;
            self.taxAmount = taxAmount;
            self.socialInsuranceAmount = socialInsuranceAmount;
            self.widthHoldingTaxAmount = widthHoldingTaxAmount;
            self.enrollmentStatus = enrollmentStatus;
            self.directionalStatus = directionalStatus;
            self.amountAfterTaxDeduction = amountAfterTaxDeduction;
        }

    }
}