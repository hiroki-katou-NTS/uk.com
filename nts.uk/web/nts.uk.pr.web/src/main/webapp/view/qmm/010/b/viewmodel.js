var qmm010;
(function (qmm010) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var option = nts.uk.ui.option;
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.ainp001 = ko.observable("");
                    self.ainp002 = ko.observable("");
                    self.ainp003 = ko.observable("");
                    self.ainp004 = ko.observable("");
                    self.ainp005 = ko.observable("");
                    self.ainp006 = ko.observable("");
                    self.ainp007 = ko.observable("");
                    self.ainp008 = ko.observable("");
                    self.ainp009 = ko.observable("");
                    self.ainp010 = ko.observable("");
                    self.ainp011 = ko.observable("");
                    self.ainp012 = ko.observable("");
                    self.ainp013 = ko.observable("");
                    self.ainp014 = ko.observable("");
                    self.ainp015 = ko.observable("");
                    self.ainp016 = ko.observable("");
                    self.ainp017 = ko.observable("");
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
                    self.blst001 = ko.observableArray([
                        new BItemModelLST001('001', '基本給'),
                        new BItemModelLST001('150', '役職手当'),
                        new BItemModelLST001('ABC', '基12本ghj給')
                    ]);
                    self.blstsel001 = ko.observable("");
                    self.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 100 },
                        { headerText: '名称', prop: 'name', width: 150 }
                    ]);
                    self.currentCode = ko.observable();
                    self.currentCodeList = ko.observableArray([]);
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
            var BItemModelLST001 = (function () {
                function BItemModelLST001(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return BItemModelLST001;
            }());
            viewmodel.BItemModelLST001 = BItemModelLST001;
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qmm010.b || (qmm010.b = {}));
})(qmm010 || (qmm010 = {}));
