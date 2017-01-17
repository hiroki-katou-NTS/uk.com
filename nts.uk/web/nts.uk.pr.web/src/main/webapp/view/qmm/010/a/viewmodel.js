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
                        var LaborInsuranceOffice = a.service.model.LaborInsuranceOffice;
                        var LaborInsuranceOfficeInDTO = a.service.model.LaborInsuranceOfficeInDTO;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    var officeInfo001 = new LaborInsuranceOffice('companyCode001', '000000000001', 'A事業所', 'shortName', 'picName', 'picPosition', 'potalCode', 'prefecture', 'address1st', 'address2nd', 'kanaAddress1st', 'kanaAddress2nd', 'phoneNumber', '01', 'officeMark', '1234', '567890', '1', 'memo');
                                    var officeInfo003 = new LaborInsuranceOffice('companyCode001', '000000000003', 'C事業所', 'shortName', 'picName', 'picPosition', 'potalCode', 'prefecture', 'address1st', 'address2nd', 'kanaAddress1st', 'kanaAddress2nd', 'phoneNumber', '01', 'officeMark', '1234', '567890', '1', 'memo');
                                    var officeInfo002 = new LaborInsuranceOffice('companyCode001', '000000000002', 'B事業所', 'shortName', 'picName', 'picPosition', 'potalCode', 'prefecture', 'address1st', 'address2nd', 'kanaAddress1st', 'kanaAddress2nd', 'phoneNumber', '01', 'officeMark', '1234', '567890', '1', 'memo');
                                    self.lstlaborInsuranceOffice = [officeInfo001, officeInfo002, officeInfo003];
                                    self.laborInsuranceOffice = ko.observable(new LaborInsuranceOfficeModel(officeInfo001));
                                    self.lstlaborInsuranceOfficeModel = ko.observableArray([new LaborInsuranceOfficeInDTO(officeInfo001),
                                        new LaborInsuranceOfficeInDTO(officeInfo002), new LaborInsuranceOfficeInDTO(officeInfo003)]);
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
                                    self.selectCodeLstlaborInsuranceOffice = ko.observable(officeInfo001.code);
                                    self.selectCodeLstlaborInsuranceOffice.subscribe(function (selectCodeLstlaborInsuranceOffice) {
                                        self.showLaborInsuranceOffice(selectCodeLstlaborInsuranceOffice);
                                    });
                                }
                                ScreenModel.prototype.showLaborInsuranceOffice = function (selectCodeLstlaborInsuranceOffice) {
                                    var self = this;
                                    if (selectCodeLstlaborInsuranceOffice != null && selectCodeLstlaborInsuranceOffice != undefined) {
                                        for (var index = 0; index < self.lstlaborInsuranceOffice.length; index++) {
                                            if (selectCodeLstlaborInsuranceOffice === self.lstlaborInsuranceOffice[index].code) {
                                                self.laborInsuranceOffice(new LaborInsuranceOfficeModel(self.lstlaborInsuranceOffice[index]));
                                            }
                                        }
                                    }
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
                                    this.multilineeditor = {
                                        memo: ko.observable(officeInfo.memo),
                                        readonly: false,
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                                            resizeable: true,
                                            placeholder: "Placeholder for text editor",
                                            width: "",
                                            textalign: "left"
                                        })),
                                    };
                                }
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
