var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm008;
                (function (qmm008) {
                    var d;
                    (function (d) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.listAvgEarnLevelMasterSetting = ko.observableArray([
                                        { code: 1, healthLevel: 1, pensionLevel: 1, avgEarn: 58000, salMin: 0, salMax: 63000 },
                                        { code: 2, healthLevel: 2, pensionLevel: 1, avgEarn: 68000, salMin: 63000, salMax: 73000 }
                                    ]);
                                    self.listHealthInsuranceAvgearn = ko.observableArray([
                                        { historyId: 1, levelCode: 1, companyAvg: { general: 123, nursing: 345, basic: 567, specific: 678 }, personalAvg: { general: 123, nursing: 345, basic: 567, specific: 678 } },
                                        { historyId: 2, levelCode: 2, companyAvg: { general: 444, nursing: 222, basic: 111, specific: 333 }, personalAvg: { general: 222, nursing: 444, basic: 555, specific: 666 } }
                                    ]);
                                }
                                ScreenModel.prototype.closeDialog = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var HealthInsuranceAvgEarnValue = (function () {
                                function HealthInsuranceAvgEarnValue() {
                                }
                                return HealthInsuranceAvgEarnValue;
                            }());
                            viewmodel.HealthInsuranceAvgEarnValue = HealthInsuranceAvgEarnValue;
                            var HealthInsuranceAvgEarn = (function () {
                                function HealthInsuranceAvgEarn() {
                                }
                                return HealthInsuranceAvgEarn;
                            }());
                            viewmodel.HealthInsuranceAvgEarn = HealthInsuranceAvgEarn;
                        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
                    })(d = qmm008.d || (qmm008.d = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
