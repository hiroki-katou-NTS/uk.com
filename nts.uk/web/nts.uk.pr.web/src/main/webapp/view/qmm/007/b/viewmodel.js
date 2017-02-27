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
                                    var unitPriceHistoryDto = nts.uk.ui.windows.getShared('unitPriceHistoryModel');
                                    self.unitPriceHistoryModel = ko.mapping.fromJS(unitPriceHistoryDto);
                                    self.historyTakeOver = ko.observable('lastest');
                                }
                                ScreenModel.prototype.btnApplyClicked = function () {
                                    var self = this;
                                    nts.uk.ui.windows.setShared("childValue", ko.toJS(self.unitPriceHistoryModel));
                                    service.create(ko.toJS(self.unitPriceHistoryModel)).done(function () {
                                        nts.uk.ui.windows.close();
                                    });
                                };
                                ScreenModel.prototype.btnCancelClicked = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var UnitPriceHistoryModel = (function () {
                                function UnitPriceHistoryModel(unitPriceCode, unitPriceName, startMonth, endMonth) {
                                    this.unitPriceCode = unitPriceCode;
                                    this.unitPriceName = unitPriceName;
                                    this.startMonth = ko.observable(startMonth);
                                    this.endMonth = ko.observable(endMonth);
                                }
                                ;
                                return UnitPriceHistoryModel;
                            }());
                            viewmodel.UnitPriceHistoryModel = UnitPriceHistoryModel;
                        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
                    })(b = qmm007.b || (qmm007.b = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
