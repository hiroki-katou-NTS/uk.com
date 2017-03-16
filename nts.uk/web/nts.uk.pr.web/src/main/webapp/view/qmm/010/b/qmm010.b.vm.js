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
                        var LaborInsuranceOfficeImportDto = b.service.model.LaborInsuranceOfficeImportDto;
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
                                ScreenModel.prototype.showConfirm = function (laborInsuranceOfficeCheckImportDto, socialInsuranceOfficeImport) {
                                    var self = this;
                                    if (laborInsuranceOfficeCheckImportDto.code === "1") {
                                        nts.uk.ui.dialog.confirm("Duplicate Code ! Do you replace All?")
                                            .ifYes(function () {
                                            self.importData(0, socialInsuranceOfficeImport);
                                        }).ifNo(function () {
                                            self.importData(1, socialInsuranceOfficeImport);
                                        });
                                    }
                                    else {
                                        self.importData(0, socialInsuranceOfficeImport);
                                    }
                                };
                                ScreenModel.prototype.importData = function (checkUpdateDuplicateCode, socialInsuranceOfficeImport) {
                                    var laborInsuranceOfficeImportDto;
                                    laborInsuranceOfficeImportDto = new LaborInsuranceOfficeImportDto();
                                    laborInsuranceOfficeImportDto.socialInsuranceOfficeImport = socialInsuranceOfficeImport;
                                    laborInsuranceOfficeImportDto.checkUpdateDuplicateCode = checkUpdateDuplicateCode;
                                    b.service.importData(laborInsuranceOfficeImportDto).done(function (data) {
                                        nts.uk.ui.windows.close();
                                    });
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
                                ScreenModel.prototype.checkDuplicateCodeByImportData = function () {
                                    var self = this;
                                    if (self.selectLstSocialInsuranceOffice() != null) {
                                        var socialInsuranceOfficeImport;
                                        socialInsuranceOfficeImport = self.findCode(self.selectLstSocialInsuranceOffice());
                                        b.service.checkDuplicateCodeByImportData(socialInsuranceOfficeImport).done(function (data) {
                                            self.showConfirm(data, socialInsuranceOfficeImport);
                                        });
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
//# sourceMappingURL=qmm010.b.vm.js.map