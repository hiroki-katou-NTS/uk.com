var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm010;
                (function (qmm010) {
                    var b;
                    (function (b) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.selectLstSocialInsuranceOffice = ko.observable('');
                                    self.columnsLstSocialInsuranceOffice = ko.observableArray([
                                        { headerText: 'コード', prop: 'code', width: 100 },
                                        { headerText: '名称', prop: 'name', width: 150 }
                                    ]);
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.findAllInsuranceOffice().done(function (data) {
                                        dfd.resolve(self);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findAllInsuranceOffice = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    var data = nts.uk.ui.windows.getShared("dataInsuranceOffice");
                                    self.lstSocialInsuranceOffice = ko.observableArray(data);
                                    dfd.resolve(self);
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findCode = function (code) {
                                    var self = this;
                                    for (var _i = 0, _a = self.lstSocialInsuranceOffice(); _i < _a.length; _i++) {
                                        var itemOfLst = _a[_i];
                                        if (itemOfLst.code === code) {
                                            return itemOfLst;
                                        }
                                    }
                                    return null;
                                };
                                ScreenModel.prototype.importData = function () {
                                    var self = this;
                                    if (self.selectLstSocialInsuranceOffice() != null) {
                                        var socialInsuranceOfficeImport;
                                        socialInsuranceOfficeImport = self.findCode(self.selectLstSocialInsuranceOffice());
                                        nts.uk.ui.windows.setShared('importData', socialInsuranceOfficeImport);
                                        nts.uk.ui.windows.close();
                                    }
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
                    })(b = qmm010.b || (qmm010.b = {}));
                })(qmm010 = view.qmm010 || (view.qmm010 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
