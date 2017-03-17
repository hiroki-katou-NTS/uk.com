var qpp008;
(function (qpp008) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.textLbl006 = ko.observable('2017/11/01');
                    self.paymentDateProcessingList = ko.observableArray([]);
                    self.selectedPaymentDate = ko.observable(null);
                    self.yearmontheditor = {
                        value: ko.observable(200001),
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TimeEditorOption({
                            inputFormat: 'yearmonth'
                        })),
                        required: ko.observable(false),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                    /*
                            GridList
                    */
                    self.items = ko.observableArray([
                        new ItemModel('基本給'),
                        new ItemModel('基本給3'),
                        new ItemModel('基本給2'),
                        new ItemModel('基本給1')
                    ]);
                    self.columns = ko.observableArray([
                        { headerText: '印刷内容', prop: 'name', width: 150 }
                    ]);
                    self.currentCode = ko.observable();
                    self.currentCodeList = ko.observableArray([]);
                    /* Label  */
                    self.inline = ko.observable(true);
                    self.required = ko.observable(true);
                    self.enable = ko.observable(true);
                    /**
                     * combobox
                      */
                    //combobox1
                    self.itemListCbb1 = ko.observableArray([
                        new ItemModelCbb1('1', '基本給'),
                        new ItemModelCbb1('2', '役職手当'),
                        new ItemModelCbb1('3', '基本給')
                    ]);
                    self.itemNameCbb1 = ko.observable('');
                    self.currentCodeCbb1 = ko.observable(3);
                    self.selectedCodeCbb1 = ko.observable('0002');
                    self.isEnableCbb1 = ko.observable(true);
                    self.isEditableCbb1 = ko.observable(true);
                    //combobox2
                    self.itemListCbb2 = ko.observableArray([
                        new ItemModelCbb2('基本給'),
                        new ItemModelCbb2('役職手当'),
                        new ItemModelCbb2('基本給2')
                    ]);
                    self.selectedCodeCbb2 = ko.observable('基本給');
                    self.isEnableCbb2 = ko.observable(true);
                    self.isEditableCbb2 = ko.observable(true);
                    //combobox3
                    self.itemListCbb3 = ko.observableArray([
                        new ItemModelCbb3('基本給1', '基本給'),
                        new ItemModelCbb3('基本給2', '役職手当'),
                        new ItemModelCbb3('0003', '基本給')
                    ]);
                    self.itemNameCbb3 = ko.observable('');
                    self.currentCodeCbb3 = ko.observable(3);
                    self.selectedCodeCbb3 = ko.observable('');
                    self.isEnableCbb3 = ko.observable(true);
                    self.isEditableCbb3 = ko.observable(true);
                    /**
                        Textediter
                    */
                    self.texteditor1 = {
                        value: ko.observable(''),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "2016/03",
                            width: "50px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                    self.texteditor2 = {
                        value: ko.observable(''),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "2016/03",
                            width: "50px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                }
                ScreenModel.prototype.openBDialog = function () {
                    var self = this;
                    nts.uk.ui.windows.sub.modal('/view/qpp/008/b/index.xhtml', { title: '印刷設定', dialogClass: 'no-close' }).onClosed(function () {
                    });
                };
                ScreenModel.prototype.openCDialog = function () {
                    var self = this;
                    nts.uk.ui.windows.sub.modal('/view/qpp/008/c/index.xhtml', { title: '出力項目の設定（共通）', dialogClass: 'no-close' }).onClosed(function () {
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModelCbb1 = (function () {
                function ItemModelCbb1(codeCbb1, nameCbb1) {
                    this.codeCbb1 = codeCbb1;
                    this.nameCbb1 = nameCbb1;
                }
                return ItemModelCbb1;
            }());
            var ItemModelCbb2 = (function () {
                function ItemModelCbb2(nameCbb2) {
                    this.nameCbb2 = nameCbb2;
                    this.labelCbb2 = nameCbb2;
                }
                return ItemModelCbb2;
            }());
            var ItemModelCbb3 = (function () {
                function ItemModelCbb3(codeCbb3, nameCbb3) {
                    this.codeCbb3 = codeCbb3;
                    this.nameCbb3 = nameCbb3;
                }
                return ItemModelCbb3;
            }());
            var ItemModel = (function () {
                function ItemModel(name) {
                    this.name = name;
                }
                return ItemModel;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qpp008.a || (qpp008.a = {}));
})(qpp008 || (qpp008 = {}));
