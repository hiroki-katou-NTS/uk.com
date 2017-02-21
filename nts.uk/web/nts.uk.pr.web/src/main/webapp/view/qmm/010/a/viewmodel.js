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
                    var a;
                    (function (a) {
                        var option = nts.uk.ui.option;
                        var LaborInsuranceOfficeDto = a.service.model.LaborInsuranceOfficeDto;
                        var TypeActionLaborInsuranceOffice = a.service.model.TypeActionLaborInsuranceOffice;
                        var LaborInsuranceOfficeDeleteDto = a.service.model.LaborInsuranceOfficeDeleteDto;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.textSearch = {
                                        valueSearch: ko.observable(""),
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            placeholder: "コード・名称で検索・・・",
                                            width: "270",
                                            textalign: "left"
                                        }))
                                    };
                                    self.columnsLstlaborInsuranceOffice = ko.observableArray([
                                        { headerText: 'コード', prop: 'code', width: 120 },
                                        { headerText: '名称', prop: 'name', width: 120 }
                                    ]);
                                    self.enableButton = ko.observable(true);
                                    self.typeAction = ko.observable(TypeActionLaborInsuranceOffice.update);
                                    self.isEmpty = ko.observable(true);
                                }
                                ScreenModel.prototype.resetValueLaborInsurance = function () {
                                    var self = this;
                                    self.laborInsuranceOfficeModel().resetAllValue();
                                    self.typeAction(TypeActionLaborInsuranceOffice.add);
                                    self.selectCodeLstlaborInsuranceOffice('');
                                };
                                ScreenModel.prototype.readFromSocialTnsuranceOffice = function () {
                                    var self = this;
                                    self.enableButton(false);
                                    nts.uk.ui.windows.sub.modal("/view/qmm/010/b/index.xhtml", { height: 800, width: 500, title: "社会保険事業所から読み込み" }).onClosed(function () {
                                        self.enableButton(true);
                                    });
                                };
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
                                    a.service.findAllLaborInsuranceOffice().done(function (data) {
                                        if (data != null && data.length > 0) {
                                            self.lstlaborInsuranceOfficeModel = ko.observableArray(data);
                                            self.selectCodeLstlaborInsuranceOffice = ko.observable(data[0].code);
                                            self.selectCodeLstlaborInsuranceOffice.subscribe(function (selectCodeLstlaborInsuranceOffice) {
                                                self.detailLaborInsuranceOffice(selectCodeLstlaborInsuranceOffice);
                                            });
                                            a.service.findLaborInsuranceOffice(data[0].code).done(function (data) {
                                                self.laborInsuranceOfficeModel = ko.observable(new LaborInsuranceOfficeModel(data));
                                                dfd.resolve(self);
                                            });
                                        }
                                        else {
                                            self.newmodelEmptyData();
                                            dfd.resolve(self);
                                        }
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findInsuranceOffice = function (code) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    if (code != null && code != undefined && code != '') {
                                        a.service.findLaborInsuranceOffice(code).done(function (data) {
                                            self.laborInsuranceOfficeModel = ko.observable(new LaborInsuranceOfficeModel(data));
                                            dfd.resolve(null);
                                        });
                                    }
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.showInsuranceOffice = function (code) {
                                    if (code != null && code != undefined && code != '') {
                                        var self = this;
                                        a.service.findLaborInsuranceOffice(code).done(function (data) {
                                            self.laborInsuranceOfficeModel(new LaborInsuranceOfficeModel(data));
                                        });
                                    }
                                };
                                ScreenModel.prototype.saveLaborInsuranceOffice = function () {
                                    var self = this;
                                    if (self.typeAction() == TypeActionLaborInsuranceOffice.add) {
                                        a.service.addLaborInsuranceOffice(self.collectData()).done(function (data) {
                                            self.reloadDataByAction(self.laborInsuranceOfficeModel().code());
                                        });
                                    }
                                    else {
                                        a.service.updateLaborInsuranceOffice(self.collectData()).done(function (data) {
                                            self.reloadDataByAction(self.laborInsuranceOfficeModel().code());
                                        });
                                    }
                                };
                                ScreenModel.prototype.showchangeLaborInsuranceOfficep = function (selectionCodeLstLstLaborInsuranceOffice) {
                                    var self = this;
                                    self.detailLaborInsuranceOffice(selectionCodeLstLstLaborInsuranceOffice);
                                };
                                ScreenModel.prototype.detailLaborInsuranceOffice = function (code) {
                                    if (code != null && code != undefined && code != '') {
                                        var self = this;
                                        a.service.findLaborInsuranceOffice(code).done(function (data) {
                                            if (self.isEmpty()) {
                                                self.selectCodeLstlaborInsuranceOffice.subscribe(function (selectionCodeLstLstLaborInsuranceOffice) {
                                                    self.showchangeLaborInsuranceOfficep(selectionCodeLstLstLaborInsuranceOffice);
                                                });
                                                self.isEmpty(false);
                                            }
                                            self.selectCodeLstlaborInsuranceOffice(code);
                                            self.laborInsuranceOfficeModel().updateData(data);
                                        });
                                    }
                                };
                                ScreenModel.prototype.reloadDataByAction = function (code) {
                                    var self = this;
                                    a.service.findAllLaborInsuranceOffice().done(function (data) {
                                        if (self.lstlaborInsuranceOfficeModel == null || self.lstlaborInsuranceOfficeModel == undefined) {
                                            self.lstlaborInsuranceOfficeModel = ko.observableArray(data);
                                        }
                                        else {
                                            self.lstlaborInsuranceOfficeModel(data);
                                        }
                                        if (code != null && code != undefined && code != '') {
                                            self.detailLaborInsuranceOffice(code);
                                        }
                                        else {
                                            if (data != null && data.length > 0) {
                                                self.detailLaborInsuranceOffice(data[0].code);
                                            }
                                            else {
                                                self.newmodelEmptyData();
                                            }
                                        }
                                    });
                                };
                                ScreenModel.prototype.newmodelEmptyData = function () {
                                    var self = this;
                                    if (self.lstlaborInsuranceOfficeModel == null || self.lstlaborInsuranceOfficeModel == undefined) {
                                        self.lstlaborInsuranceOfficeModel = ko.observableArray([]);
                                    }
                                    else {
                                        self.lstlaborInsuranceOfficeModel([]);
                                    }
                                    if (self.laborInsuranceOfficeModel == null || self.laborInsuranceOfficeModel == undefined) {
                                        self.laborInsuranceOfficeModel = ko.observable(new LaborInsuranceOfficeModel(new LaborInsuranceOfficeDto()));
                                    }
                                    else {
                                        self.resetValueLaborInsurance();
                                    }
                                    self.selectCodeLstlaborInsuranceOffice = ko.observable('');
                                    self.isEmpty(true);
                                };
                                ScreenModel.prototype.deleteLaborInsuranceOffice = function () {
                                    var self = this;
                                    var laborInsuranceOfficeDeleteDto = new LaborInsuranceOfficeDeleteDto();
                                    laborInsuranceOfficeDeleteDto.code = self.laborInsuranceOfficeModel().code();
                                    laborInsuranceOfficeDeleteDto.version = 11;
                                    a.service.deleteLaborInsuranceOffice(laborInsuranceOfficeDeleteDto).done(function (data) {
                                        self.reloadDataByAction('');
                                    });
                                };
                                ScreenModel.prototype.collectData = function () {
                                    var self = this;
                                    var laborInsuranceOffice;
                                    laborInsuranceOffice = new LaborInsuranceOfficeDto();
                                    laborInsuranceOffice.code = self.laborInsuranceOfficeModel().code();
                                    laborInsuranceOffice.name = self.laborInsuranceOfficeModel().name();
                                    laborInsuranceOffice.shortName = self.laborInsuranceOfficeModel().shortName();
                                    laborInsuranceOffice.picName = self.laborInsuranceOfficeModel().picName();
                                    laborInsuranceOffice.picPosition = self.laborInsuranceOfficeModel().picPosition();
                                    laborInsuranceOffice.potalCode = self.laborInsuranceOfficeModel().postalCode();
                                    laborInsuranceOffice.address1st = self.laborInsuranceOfficeModel().address1st();
                                    laborInsuranceOffice.address2nd = self.laborInsuranceOfficeModel().address2nd();
                                    laborInsuranceOffice.kanaAddress1st = self.laborInsuranceOfficeModel().kanaAddress1st();
                                    laborInsuranceOffice.kanaAddress2nd = self.laborInsuranceOfficeModel().kanaAddress2nd();
                                    laborInsuranceOffice.phoneNumber = self.laborInsuranceOfficeModel().phoneNumber();
                                    laborInsuranceOffice.citySign = self.laborInsuranceOfficeModel().citySign();
                                    laborInsuranceOffice.officeMark = self.laborInsuranceOfficeModel().officeMark();
                                    laborInsuranceOffice.officeNoA = self.laborInsuranceOfficeModel().officeNoA();
                                    laborInsuranceOffice.officeNoB = self.laborInsuranceOfficeModel().officeNoB();
                                    laborInsuranceOffice.officeNoC = self.laborInsuranceOfficeModel().officeNoC();
                                    laborInsuranceOffice.memo = self.laborInsuranceOfficeModel().multilineeditor().memo();
                                    return laborInsuranceOffice;
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var LaborInsuranceOfficeModel = (function () {
                                function LaborInsuranceOfficeModel(officeInfo) {
                                    this.code = ko.observable(officeInfo.code);
                                    this.name = ko.observable(officeInfo.name);
                                    this.shortName = ko.observable(officeInfo.shortName);
                                    this.picName = ko.observable(officeInfo.picName);
                                    this.picPosition = ko.observable(officeInfo.picPosition);
                                    this.postalCode = ko.observable(officeInfo.potalCode);
                                    this.address1st = ko.observable(officeInfo.address1st);
                                    this.kanaAddress1st = ko.observable(officeInfo.kanaAddress1st);
                                    this.address2nd = ko.observable(officeInfo.address2nd);
                                    this.kanaAddress2nd = ko.observable(officeInfo.kanaAddress2nd);
                                    this.phoneNumber = ko.observable(officeInfo.phoneNumber);
                                    this.citySign = ko.observable(officeInfo.citySign);
                                    this.officeMark = ko.observable(officeInfo.officeMark);
                                    this.officeNoA = ko.observable(officeInfo.officeNoA);
                                    this.officeNoB = ko.observable(officeInfo.officeNoB);
                                    this.officeNoC = ko.observable(officeInfo.officeNoC);
                                    this.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                    this.multilineeditor = ko.observable({
                                        memo: ko.observable(officeInfo.memo),
                                        readonly: false,
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                                            resizeable: true,
                                            placeholder: "Placeholder for text editor",
                                            width: "",
                                            textalign: "left"
                                        })),
                                    });
                                }
                                LaborInsuranceOfficeModel.prototype.resetAllValue = function () {
                                    this.code('');
                                    this.name('');
                                    this.shortName('');
                                    this.picName('');
                                    this.picPosition('');
                                    this.postalCode('');
                                    this.address1st('');
                                    this.kanaAddress1st('');
                                    this.address2nd('');
                                    this.kanaAddress2nd('');
                                    this.phoneNumber('');
                                    this.citySign('');
                                    this.officeMark('');
                                    this.officeNoA('');
                                    this.officeNoB('');
                                    this.officeNoC('');
                                    this.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                    this.multilineeditor({
                                        memo: ko.observable(''),
                                        readonly: false,
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                                            resizeable: true,
                                            placeholder: "Placeholder for text editor",
                                            width: "",
                                            textalign: "left"
                                        })),
                                    });
                                };
                                LaborInsuranceOfficeModel.prototype.updateData = function (officeInfo) {
                                    this.code(officeInfo.code);
                                    this.name(officeInfo.name);
                                    this.shortName(officeInfo.shortName);
                                    this.picName(officeInfo.picName);
                                    this.picPosition(officeInfo.picPosition);
                                    this.postalCode(officeInfo.potalCode);
                                    this.address1st(officeInfo.address1st);
                                    this.kanaAddress1st(officeInfo.kanaAddress1st);
                                    this.address2nd(officeInfo.address2nd);
                                    this.kanaAddress2nd(officeInfo.kanaAddress2nd);
                                    this.phoneNumber(officeInfo.phoneNumber);
                                    this.citySign(officeInfo.citySign);
                                    this.officeMark(officeInfo.officeMark);
                                    this.officeNoA(officeInfo.officeNoA);
                                    this.officeNoB(officeInfo.officeNoB);
                                    this.officeNoC(officeInfo.officeNoC);
                                    this.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                    this.multilineeditor({
                                        memo: ko.observable(officeInfo.memo),
                                        readonly: false,
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                                            resizeable: true,
                                            placeholder: "Placeholder for text editor",
                                            width: "",
                                            textalign: "left"
                                        })),
                                    });
                                };
                                return LaborInsuranceOfficeModel;
                            }());
                            viewmodel.LaborInsuranceOfficeModel = LaborInsuranceOfficeModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm010.a || (qmm010.a = {}));
                })(qmm010 = view.qmm010 || (view.qmm010 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
