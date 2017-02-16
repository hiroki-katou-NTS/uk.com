var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm016;
                (function (qmm016) {
                    var l;
                    (function (l) {
                        var option = nts.uk.ui.option;
                        var MultipleTargetSettingDto = l.service.model.MultipleTargetSettingDto;
                        var MultipleTargetSetting = l.service.model.MultipleTargetSetting;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.items = ko.observableArray([]);
                                    var str = ['a0', 'b0', 'c0', 'd0'];
                                    for (var j = 0; j < 4; j++) {
                                        for (var i = 1; i < 51; i++) {
                                            var code = i < 10 ? str[j] + '0' + i : str[j] + i;
                                            this.items.push(new ItemModel(code, code, code, code));
                                        }
                                    }
                                    self.columns = ko.observableArray([
                                        { headerText: 'コード', prop: 'code', width: 20 },
                                        { headerText: '名称', prop: 'name', width: 40 },
                                        { headerText: '説明', prop: 'description', width: 60 },
                                        { headerText: '説明1', prop: 'other1', width: 100 },
                                        { headerText: '説明2', prop: 'other2', width: 100 }
                                    ]);
                                    self.currentCode = ko.observable();
                                    self.currentCodeList = ko.observableArray([]);
                                    self.selectionMultipleTargetSetting = ko.observableArray([new MultipleTargetSettingDto(MultipleTargetSetting.BigestMethod, "BigestMethod"),
                                        new MultipleTargetSettingDto(MultipleTargetSetting.TotalMethod, "TotalMethod")
                                    ]);
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
                                    self.selectedMultipleTargetSetting = ko.observable(MultipleTargetSetting.BigestMethod);
                                }
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
                                        self.findInsuranceOffice(self.selectCodeLstlaborInsuranceOffice()).done(function (data) { dfd.resolve(self); });
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findAllInsuranceOffice = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    l.service.findAllLaborInsuranceOffice("companyCode").done(function (data) {
                                        self.lstlaborInsuranceOfficeModel = ko.observableArray(data);
                                        self.selectCodeLstlaborInsuranceOffice = ko.observable(data[0].code);
                                        self.selectCodeLstlaborInsuranceOffice.subscribe(function (selectCodeLstlaborInsuranceOffice) {
                                            self.showInsuranceOffice(selectCodeLstlaborInsuranceOffice);
                                        });
                                        dfd.resolve(self);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findInsuranceOffice = function (code) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    var laborInsuranceOfficeFindInDto;
                                    laborInsuranceOfficeFindInDto = new LaborInsuranceOfficeFindInDto();
                                    laborInsuranceOfficeFindInDto.code = code;
                                    laborInsuranceOfficeFindInDto.companyCode = "companyCode001";
                                    l.service.findLaborInsuranceOffice(laborInsuranceOfficeFindInDto).done(function (data) {
                                        self.laborInsuranceOfficeModel = ko.observable(new LaborInsuranceOfficeModel(data));
                                        dfd.resolve(null);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.showInsuranceOffice = function (code) {
                                    var self = this;
                                    var laborInsuranceOfficeFindInDto;
                                    laborInsuranceOfficeFindInDto = new LaborInsuranceOfficeFindInDto();
                                    laborInsuranceOfficeFindInDto.code = code;
                                    laborInsuranceOfficeFindInDto.companyCode = "companyCode001";
                                    l.service.findLaborInsuranceOffice(laborInsuranceOfficeFindInDto).done(function (data) {
                                        self.laborInsuranceOfficeModel(new LaborInsuranceOfficeModel(data));
                                    });
                                };
                                ScreenModel.prototype.addLaborInsuranceOffice = function () {
                                    var self = this;
                                    l.service.addLaborInsuranceOffice(self.collectData(), "000001");
                                };
                                ScreenModel.prototype.updateLaborInsuranceOffice = function () {
                                    var self = this;
                                    l.service.updateLaborInsuranceOffice(self.collectData(), "000001");
                                };
                                ScreenModel.prototype.deleteLaborInsuranceOffice = function () {
                                    var self = this;
                                    l.service.deleteLaborInsuranceOffice(self.laborInsuranceOfficeModel().code(), "00001");
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
                                    laborInsuranceOffice.memo = self.laborInsuranceOfficeModel().memo();
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
                                    this.memo = ko.observable(officeInfo.memo);
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
                                    this.memo('');
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
                                return LaborInsuranceOfficeModel;
                            }());
                            viewmodel.LaborInsuranceOfficeModel = LaborInsuranceOfficeModel;
                            var ItemModel = (function () {
                                function ItemModel(code, name, description, other1, other2) {
                                    this.code = code;
                                    this.name = name;
                                    this.description = description;
                                    this.other1 = other1;
                                    this.other2 = other2 || other1;
                                }
                                return ItemModel;
                            }());
                            viewmodel.ItemModel = ItemModel;
                        })(viewmodel = l.viewmodel || (l.viewmodel = {}));
                    })(l = qmm016.l || (qmm016.l = {}));
                })(qmm016 = view.qmm016 || (view.qmm016 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
