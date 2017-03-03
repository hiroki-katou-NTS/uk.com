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
                            var service = nts.uk.pr.view.qmm007.a.service;
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.unitPriceHistoryModel = ko.mapping.fromJS(nts.uk.ui.windows.getShared('unitPriceHistoryModel'));
                                    self.isLatestHistory = ko.observable(nts.uk.ui.windows.getShared('isLatestHistory'));
                                    self.edittingMethod = ko.observable('Edit');
                                    self.isEditMode = ko.observable(true);
                                    self.edittingMethod.subscribe(function (val) {
                                        val == 'Edit' ? self.isEditMode(true) : self.isEditMode(false);
                                    });
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    dfd.resolve();
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.btnApplyClicked = function () {
                                    var self = this;
                                    if (self.isEditMode()) {
                                        service.update(ko.toJS(self.unitPriceHistoryModel)).done(function () {
                                            nts.uk.ui.windows.setShared('isUpdated', true);
                                            nts.uk.ui.windows.close();
                                        });
                                    }
                                    else {
                                        service.remove(self.unitPriceHistoryModel.id(), self.unitPriceHistoryModel.unitPriceCode()).done(function () {
                                            nts.uk.ui.windows.setShared('isRemoved', true);
                                            nts.uk.ui.windows.close();
                                        });
                                    }
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
