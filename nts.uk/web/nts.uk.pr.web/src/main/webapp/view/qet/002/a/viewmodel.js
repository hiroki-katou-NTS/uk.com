var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qet002;
                (function (qet002) {
                    var a;
                    (function (a) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    this.targetYear = ko.observable(2016);
                                    this.isLowerLimit = ko.observable(true);
                                    this.isUpperLimit = ko.observable(true);
                                    this.lowerLimitValue = ko.observable(null);
                                    this.upperLimitValue = ko.observable(null);
                                }
                                ScreenModel.prototype.start = function () {
                                    var dfd = $.Deferred();
                                    dfd.resolve();
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.printData = function () {
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
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var AccumulatedPaymentResultViewModel = (function () {
                                function AccumulatedPaymentResultViewModel() {
                                }
                                return AccumulatedPaymentResultViewModel;
                            }());
                            viewmodel.AccumulatedPaymentResultViewModel = AccumulatedPaymentResultViewModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qet002.a || (qet002.a = {}));
                })(qet002 = view.qet002 || (view.qet002 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
