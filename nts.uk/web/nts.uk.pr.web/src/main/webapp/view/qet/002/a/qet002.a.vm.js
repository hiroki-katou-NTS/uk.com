var qet002;
(function (qet002) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            class ScreenModel {
                constructor() {
                    this.targetYear = ko.observable(2016);
                    this.isLowerLimit = ko.observable(true);
                    this.isUpperLimit = ko.observable(true);
                    this.lowerLimitValue = ko.observable(null);
                    this.upperLimitValue = ko.observable(null);
                }
                /**
                 * Start screen.
                 */
                start() {
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                }
                /**
                 * Print Report
                 */
                printData() {
                    // Validate
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
                    if (this.isLowerLimit() == false) {
                        $('#lower-limit-input').ntsError('clear');
                    }
                    if (this.isUpperLimit() == false) {
                        $('#upper-limit-input').ntsError('clear');
                    }
                    if (hasError) {
                        return;
                    }
                    //Print Report
                    a.service.printService(this).done(function (data) {
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                    });
                }
            }
            viewmodel.ScreenModel = ScreenModel;
            /**
             * Accumulated Payment Result Dto
             */
            class AccumulatedPaymentResultViewModel {
                constructor(empDesignation, empCode, empName, taxAmount, socialInsuranceAmount, widthHoldingTaxAmount, enrollmentStatus, directionalStatus, amountAfterTaxDeduction) {
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
            viewmodel.AccumulatedPaymentResultViewModel = AccumulatedPaymentResultViewModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qet002.a || (qet002.a = {}));
})(qet002 || (qet002 = {}));
