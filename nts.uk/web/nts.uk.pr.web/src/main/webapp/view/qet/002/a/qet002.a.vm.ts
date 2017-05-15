module qet002.a.viewmodel {
    export class ScreenModel {
        targetYear: KnockoutObservable<number>;
        isLowerLimit: KnockoutObservable<boolean>;
        lowerLimitValue: KnockoutObservable<number>;
        isUpperLimit: KnockoutObservable<boolean>;
        upperLimitValue: KnockoutObservable<number>;
        japanYear: KnockoutObservable<string>;

        constructor() {
            var self = this;
            self.targetYear = ko.observable(new Date().getFullYear());
            self.isLowerLimit = ko.observable(false);
            self.isUpperLimit = ko.observable(false);
            self.lowerLimitValue = ko.observable(null);
            self.japanYear = ko.observable('(' + nts.uk.time.yearInJapanEmpire(self.targetYear().toString()) + ')');
            self.upperLimitValue = ko.observable(null);           
            self.targetYear.subscribe(function(val: number){
               self.japanYear(""+nts.uk.time.yearInJapanEmpire(val)); 
            });
        }

        /**
         * Start screen.
         */
        public start(): JQueryPromise<void> {
            var dfd = $.Deferred<void>();
            dfd.resolve();
            return dfd.promise();
        }


        /**
         * Print Report
         */
       public printData(){  
            var self = this;
            self.clearAllError();
           // Validate
           if (self.validateData()) {
                    return;
            }
           //Print Report
           service.printService(this).done(function(data: any) {
            }).fail(function(res) {
                nts.uk.ui.dialog.alert(res.message);
            })

        }

        
        private clearAllError(): void {
           $('#target-year-input').ntsError('clear');
           $('#lower-limit-input').ntsError('clear');
           $('#upper-limit-input').ntsError('clear');
       }
        
       private validateData(): boolean {
           var self = this;
           var hasError = false;

           // Validate target year
           $('#target-year-input').ntsEditor('validate');

           // Validate 
           if (self.isLowerLimit() && self.lowerLimitValue() == null) {
               $('#lower-limit-input').ntsError('set', '金額範囲下限額 が入力されていません。');
               hasError = true;
           }
           if (self.isUpperLimit() && self.upperLimitValue() == null) {
               $('#upper-limit-input').ntsError('set', '金額範囲上限額 が入力されていません。');
               hasError = true;
           }
           if (self.isLowerLimit() && self.isUpperLimit() && self.lowerLimitValue() > self.upperLimitValue()) {
               $('#lower-limit-input').ntsError('set', '金額の範囲が正しく指定されていません。');
               hasError = true;
           }
           // TODO: Validation relate to employee list.
           return hasError || $('.nts-input').ntsError('hasError');
       }
    }
       
    
    /**
     * Accumulated Payment Result Dto
     */
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