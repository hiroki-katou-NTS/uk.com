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
                                    self.employmentName = ko.observable("");
                                    self.textSearch = {
                                        valueSearch: ko.observable(""),
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            placeholder: "コード・名称で検索・・・",
                                            width: "340",
                                            textalign: "left"
                                        }))
                                    };
                                    self.selectLstSocialInsuranceOffice = ko.observableArray([]);
                                    self.columnsLstSocialInsuranceOffice = ko.observableArray([
                                        { headerText: 'コード', prop: 'code', width: 120 },
                                        { headerText: '名称', prop: 'name', width: 120 }
                                    ]);
                                    self.multilineeditor = {
                                        value: ko.observable(''),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                                            resizeable: true,
                                            placeholder: "Placeholder for text editor",
                                            width: "",
                                            textalign: "left"
                                        })),
                                        required: ko.observable(true),
                                        enable: ko.observable(true),
                                        readonly: ko.observable(false)
                                    };
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
                                    b.service.findAllSocialInsuranceOffice().done(function (data) {
                                        self.lstSocialInsuranceOffice = ko.observableArray(data);
                                        dfd.resolve(self);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.showConfirm = function (laborInsuranceOfficeCheckImportDto, lstSocialInsuranceOfficeImport) {
                                    var self = this;
                                    if (laborInsuranceOfficeCheckImportDto.code === "1") {
                                        nts.uk.ui.dialog.confirm("Duplicate Code ! Do you replace All?").ifYes(function () {
                                            self.importData(0, lstSocialInsuranceOfficeImport);
                                        }).ifNo(function () {
                                            self.importData(1, lstSocialInsuranceOfficeImport);
                                        }).ifCancel(function () {
                                            self.importData(1, lstSocialInsuranceOfficeImport);
                                        }).then(function () {
                                            self.importData(1, lstSocialInsuranceOfficeImport);
                                        });
                                    }
                                    else {
                                        self.importData(0, lstSocialInsuranceOfficeImport);
                                    }
                                };
                                ScreenModel.prototype.importData = function (checkUpdateDuplicateCode, lstSocialInsuranceOfficeImport) {
                                    var laborInsuranceOfficeImportDto;
                                    laborInsuranceOfficeImportDto = new LaborInsuranceOfficeImportDto();
                                    laborInsuranceOfficeImportDto.lstSocialInsuranceOfficeImport = lstSocialInsuranceOfficeImport;
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
                                    if (self.selectLstSocialInsuranceOffice() != null && self.selectLstSocialInsuranceOffice().length > 0) {
                                        var lstSocialInsuranceOfficeImport;
                                        lstSocialInsuranceOfficeImport = [];
                                        for (var _i = 0, _a = self.selectLstSocialInsuranceOffice(); _i < _a.length; _i++) {
                                            var item = _a[_i];
                                            lstSocialInsuranceOfficeImport.push(self.findCode(item));
                                        }
                                        var laborInsuranceOfficeImportDto;
                                        laborInsuranceOfficeImportDto = new LaborInsuranceOfficeImportDto();
                                        b.service.checkDuplicateCodeByImportData(lstSocialInsuranceOfficeImport).done(function (data) {
                                            self.showConfirm(data, lstSocialInsuranceOfficeImport);
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
