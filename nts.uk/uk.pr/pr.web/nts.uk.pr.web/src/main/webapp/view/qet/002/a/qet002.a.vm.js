var qet002;
(function (qet002) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.targetYear = ko.observable(2017);
                    self.isLowerLimit = ko.observable(true);
                    self.isUpperLimit = ko.observable(true);
                    self.lowerLimitValue = ko.observable(null);
                    self.japanYear = ko.observable('(' + nts.uk.time.yearInJapanEmpire('2017') + ')');
                    self.upperLimitValue = ko.observable(null);
                    self.targetYear.subscribe(function (val) {
                        self.japanYear("" + nts.uk.time.yearInJapanEmpire(val));
                    });
                }
                /**
                 * Start screen.
                 */
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                };
                /**
                 * Print Report
                 */
                ScreenModel.prototype.printData = function () {
                    // Validate
                    var hasError = false;
                    if (this.targetYear() == null) {
                        hasError = true;
                        $('#target-year-input').ntsError('set', '対象年 が入力されていません。');
                    }
                    if (this.isLowerLimit() && this.lowerLimitValue() == null) {
                        hasError = true;
                        $('#lower-limit-input').ntsError('set', '金額範囲下限額 が入力されていません。');
                    }
                    if (this.isUpperLimit() && this.upperLimitValue() == null) {
                        hasError = true;
                        $('#upper-limit-input').ntsError('set', '金額範囲上限額 が入力されていません。');
                    }
                    if ((this.isLowerLimit()) && (this.isUpperLimit())) {
                        if (this.lowerLimitValue() > this.upperLimitValue()) {
                            hasError = true;
                            $('#lower-limit-input').ntsError('set', '金額の範囲が正しく指定されていません。');
                        }
                    }
                    if (!this.isLowerLimit()) {
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
                        console.log("YES");
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            /**
             * Accumulated Payment Result Dto
             */
            var AccumulatedPaymentResultViewModel = (function () {
                function AccumulatedPaymentResultViewModel(empDesignation, empCode, empName, taxAmount, socialInsuranceAmount, widthHoldingTaxAmount, enrollmentStatus, directionalStatus, amountAfterTaxDeduction) {
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
                return AccumulatedPaymentResultViewModel;
            }());
            viewmodel.AccumulatedPaymentResultViewModel = AccumulatedPaymentResultViewModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qet002.a || (qet002.a = {}));
})(qet002 || (qet002 = {}));
//# sourceMappingURL=qet002.a.vm.js.map