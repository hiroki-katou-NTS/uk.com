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
                        var LaborInsuranceOfficeDTO = a.service.model.LaborInsuranceOfficeDTO;
                        var LaborInsuranceOfficeInDTO = a.service.model.LaborInsuranceOfficeInDTO;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    var officeInfo = new LaborInsuranceOfficeDTO('companyCode002', '000000000002', 'B事業所', 'shortName', 'picName', 'picPosition', 'potalCode', 'address1st', 'address2nd', 'kanaAddress1st', 'kanaAddress2nd', 'phoneNumber', '01', 'officeMark', '1234', '567890', '1', 'memo');
                                    self.laborInsuranceOffice = ko.observable(new LaborInsuranceOfficeModel(officeInfo));
                                    self.lstlaborInsuranceOffice = ko.observableArray([new LaborInsuranceOfficeInDTO('companyCode001', '000000000001', 'A事業所'),
                                        new LaborInsuranceOfficeInDTO('companyCode002', '000000000002', 'B事業所'), new LaborInsuranceOfficeInDTO('companyCode003', '000000000003', 'C事業所')]);
                                    self.ainp001 = ko.observable("");
                                    self.employmentName = ko.observable("");
                                    self.textSearch = {
                                        valueSearch: ko.observable(""),
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            placeholder: "コード・名称で検索・・・",
                                            width: "75%",
                                            textalign: "left"
                                        }))
                                    };
                                    self.columnsLstlaborInsuranceOffice = ko.observableArray([
                                        { headerText: 'コード', prop: 'code', width: 120 },
                                        { headerText: '名称', prop: 'name', width: 120 }
                                    ]);
                                    self.selectCodeLstlaborInsuranceOffice = ko.observable('');
                                }
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var LaborInsuranceOfficeModel = (function () {
                                function LaborInsuranceOfficeModel(officeInfo) {
                                    this.code = ko.observable(officeInfo.code);
                                    this.name = ko.observable(officeInfo.name);
                                    this.shortName = ko.observable(officeInfo.shortName);
                                    this.picName = ko.observable(officeInfo.picName);
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
