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
                    var c;
                    (function (c) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    var unitPriceHistoryModel = nts.uk.ui.windows.getShared('unitPriceHistoryModel');
                                    self.code = ko.observable(unitPriceHistoryModel.unitPriceCode());
                                    self.name = ko.observable(unitPriceHistoryModel.unitPriceName());
                                    self.startMonth = ko.observable(unitPriceHistoryModel.startMonth());
                                    self.endMonth = ko.observable(unitPriceHistoryModel.endMonth());
                                    self.edittingMethod = ko.observable('Edit');
                                    self.isEditMode = ko.observable(true);
                                    self.edittingMethod.subscribe(function (val) {
                                        val == 'Edit' ? self.isEditMode(true) : self.isEditMode(false);
                                    });
                                }
                                ScreenModel.prototype.btnApplyClicked = function () {
                                    nts.uk.ui.windows.close();
                                };
                                ScreenModel.prototype.btnCancelClicked = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
                    })(c = qmm007.c || (qmm007.c = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
