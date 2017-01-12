var qmm010;
(function (qmm010) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var option = nts.uk.ui.option;
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.laborInsuranceOffice = ko.observable(new LaborInsuranceOffice('code', 'name', 'postalCode', 'address1st', 'address2nd', 'kanaAddress1st', 'kanaAddress2nd', 'phoneNumber', 'officeNoA', 'officeNoB', 'officeNoC', 'memo'));
                    self.ainp001 = ko.observable("");
                    self.code = ko.observable(self.laborInsuranceOffice().code);
                    self.name = ko.observable(self.laborInsuranceOffice().name);
                    self.ainp004 = ko.observable("");
                    self.ainp005 = ko.observable("");
                    self.postalCode = ko.observable(self.laborInsuranceOffice().postalCode);
                    self.ainp007 = ko.observable("");
                    self.address1st = ko.observable(self.laborInsuranceOffice().address1st);
                    self.kanaAddress1st = ko.observable(self.laborInsuranceOffice().kanaAddress1st);
                    self.address2nd = ko.observable(self.laborInsuranceOffice().address2nd);
                    self.kanaAddress2nd = ko.observable(self.laborInsuranceOffice().kanaAddress2nd);
                    self.phoneNumber = ko.observable(self.laborInsuranceOffice().phoneNumber);
                    self.ainp013 = ko.observable("");
                    self.ainp014 = ko.observable("");
                    self.officeNoA = ko.observable(self.laborInsuranceOffice().officeNoA);
                    self.officeNoB = ko.observable(self.laborInsuranceOffice().officeNoB);
                    self.officeNoC = ko.observable(self.laborInsuranceOffice().officeNoC);
                    self.memo = ko.observable(self.laborInsuranceOffice().memo);
                    self.employmentName = ko.observable("");
                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                    self.textSearch = {
                        valueSearch: ko.observable(""),
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "コード・名称で検索・・・",
                            width: "75%",
                            textalign: "left"
                        }))
                    };
                    self.items = ko.observableArray([
                        new ItemModel('001', '基本給'),
                        new ItemModel('150', '役職手当'),
                        new ItemModel('ABC', '基12本ghj給')
                    ]);
                    self.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 100 },
                        { headerText: '名称', prop: 'name', width: 150 }
                    ]);
                    self.currentCode = ko.observable();
                    self.currentCodeList = ko.observableArray([]);
                    self.multilineeditor = {
                        memo: ko.observable(self.laborInsuranceOffice().memo),
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
                ScreenModel.prototype.selectSomeItems = function () {
                    this.currentCode('150');
                    this.currentCodeList.removeAll();
                    this.currentCodeList.push('001');
                    this.currentCodeList.push('ABC');
                };
                ScreenModel.prototype.deselectAll = function () {
                    this.currentCode(null);
                    this.currentCodeList.removeAll();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemCloseDate = (function () {
                function ItemCloseDate(closeDateCode, closeDatename) {
                    this.closeDateCode = closeDateCode;
                    this.closeDatename = closeDatename;
                }
                return ItemCloseDate;
            }());
            viewmodel.ItemCloseDate = ItemCloseDate;
            var ItemProcessingDate = (function () {
                function ItemProcessingDate(processingDateCode, processingDatename) {
                    this.processingDateCode = processingDateCode;
                    this.processingDatename = processingDatename;
                }
                return ItemProcessingDate;
            }());
            viewmodel.ItemProcessingDate = ItemProcessingDate;
            var ItemModel = (function () {
                function ItemModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ItemModel;
            }());
            viewmodel.ItemModel = ItemModel;
            var LaborInsuranceOfficeDTO = (function () {
                function LaborInsuranceOfficeDTO(code, name, postalCode, address1st, address2nd, kanaAddress1st, kanaAddress2nd, phoneNumber, officeNoA, officeNoB, officeNoC, memo) {
                    this.String = prefecture;
                    this.Address = address1st;
                    this.Address = address2nd;
                    this.KanaAddress = kanaAddress1st;
                    this.KanaAddress = kanaAddress2nd;
                    this.String = phoneNumber;
                    this.String = citySign;
                    this.String = officeMark;
                    this.String = officeNoA;
                    this.String = officeNoB;
                    this.String = officeNoC;
                    this.Memo = memo;
                    this.code = code;
                    this.name = name;
                    this.postalCode = postalCode;
                    this.address1st = address1st;
                    this.address2nd = address2nd;
                    this.kanaAddress1st = kanaAddress1st;
                    this.kanaAddress2nd = kanaAddress2nd;
                    this.phoneNumber = phoneNumber;
                    this.officeNoA = officeNoA;
                    this.officeNoB = officeNoB;
                    this.officeNoC = officeNoC;
                    this.memo = memo;
                }
                return LaborInsuranceOfficeDTO;
            }());
            viewmodel.LaborInsuranceOfficeDTO = LaborInsuranceOfficeDTO;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm010.a || (qmm010.a = {}));
})(qmm010 || (qmm010 = {}));
