var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm007;
                (function (qmm007) {
                    var b;
                    (function (b) {
                        var viewmodel;
                        (function (viewmodel) {
                            var service = nts.uk.pr.view.qmm007.a.service;
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    var unitPriceHistoryModel = nts.uk.ui.windows.getShared('unitPriceHistoryModel');
                                    self.code = unitPriceHistoryModel.unitPriceCode();
                                    self.name = unitPriceHistoryModel.unitPriceName();
                                    self.startMonth = ko.observable(unitPriceHistoryModel.startMonth());
                                    self.endMonth = ko.observable(unitPriceHistoryModel.endMonth());
                                    self.historyTakeOver = ko.observable('1');
                                }
                                ScreenModel.prototype.btnApplyClicked = function () {
                                    var self = this;
                                    var unitPriceHistoryModel = nts.uk.ui.windows.getShared('unitPriceHistoryModel');
                                    unitPriceHistoryModel.startMonth(self.startMonth());
                                    service.create(service.collectData(unitPriceHistoryModel));
                                    nts.uk.ui.windows.close();
                                };
                                ScreenModel.prototype.btnCancelClicked = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
                    })(b = qmm007.b || (qmm007.b = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
